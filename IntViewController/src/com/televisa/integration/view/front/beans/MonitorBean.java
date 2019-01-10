/**
* Project: Paradigm - eVeTV Integration
*
* File: MonitorBean.java
*
* Created on: Septiembre 6, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/

package com.televisa.integration.view.front.beans;

import com.televisa.integration.model.AppModuleIntergrationImpl;
import com.televisa.integration.model.types.EvetvIntXmlFilesRowBean;
import com.televisa.integration.view.front.beans.types.SelectOneItemBean;
import com.televisa.integration.view.front.daos.ViewObjectDao;
import com.televisa.integration.view.front.util.UtilFaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Blob;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlHierNodeBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.client.Configuration;

import org.apache.poi.util.IOUtils;

/** Esta clase es un bean que enlaza la pantalla de monitoreo de servicios<br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Septiembre 14, 2017, 12:00 pm
 */
public class MonitorBean {
    private RichSelectOneChoice poFilterServiceSel;
    private RichInputText       poFilterIdService;
    private RichSelectOneChoice poFilterProcessSel;
    private RichInputDate       poInitialDate;
    private RichInputDate       poFinalDate;
    private RichTable           poTblLog;
    String                      lsEntityIterator = "EvetvIntServicesLogVwView1Iterator";
    private                     RichPopup poParrillasPopup;
    private                     RichTable poTblParrillas;
    private                     RichPopup poPopupProgramas;
    private                     RichTable poTblProgramas;
    private                     RichPopup poPopupTradicional;
    private                     RichTable poTblTradicional;
    String                      lsAmDef = "com.televisa.integration.model.AppModuleIntergrationImpl";
    String                      lsConfig = "AppModuleIntergrationLocal";
    private RichPopup poPopupNoDisponible;

    /**
     * Reinicia los valores de busqueda
     * @autor Jorge Luis Bautista Santiago
     * @param toActionEvent
     * @return void
     */
    public void resetFilterValues(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        getPoFilterProcessSel().setValue(null);
        getPoFilterIdService().setValue(null);
        getPoFilterServiceSel().setValue(null);
        getPoInitialDate().setValue(null);
        getPoFinalDate().setValue(null);
    }

    /**
     * Reinicia la tabla al estado inicial
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String refreshMainTable() {
        new UtilFaces().refreshTableWhereIterator("1 = 1 ", lsEntityIterator, getPoTblLog());        
        return null;
    }

    /**
     * Valida integridad entre Fecha inicial y fecha final
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateDateListener(ValueChangeEvent toValueChangeEvent) {
        toValueChangeEvent.getNewValue();
        if(getPoInitialDate().getValue() != null && 
           getPoFinalDate().getValue() != null){
            java.util.Date ltDateIni = 
                (java.util.Date)getPoInitialDate().getValue();
            java.util.Date ltDateFin = 
                (java.util.Date)getPoFinalDate().getValue();            
            int            liResValue;
            liResValue = calculateDifference(ltDateIni, ltDateFin);
            if(liResValue < 0){
                getPoInitialDate().setValue(null);
                getPoFinalDate().setValue(null);                
                AdfFacesContext.getCurrentInstance().addPartialTarget(getPoInitialDate());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getPoFinalDate());                 
                FacesMessage loMsg =
                    new FacesMessage("La fecha final debe ser mayor o " +
                    "igual a la fecha inicio");
                loMsg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, loMsg);
            }
        }
    }
    
    /**
     * Calcula la diferecia entre dos fechas
     * @autor Jorge Luis Bautista Santiago
     * @param toInitialDate
     * @param toFinalDate
     * @return int
     */
    public int calculateDifference(java.util.Date toInitialDate, 
                                   java.util.Date toFinalDate){
        int      liTempDifference = 0;
        int      liDifference = 0;
        int      liMenor = 1;
        Calendar loEarlier = Calendar.getInstance();
        Calendar loLater = Calendar.getInstance();
        if (toInitialDate.compareTo(toFinalDate) < 0){
            loEarlier.setTime(toInitialDate);
            loLater.setTime(toFinalDate);
        }
        else{
            liMenor = -1;
            loEarlier.setTime(toFinalDate);
            loLater.setTime(toInitialDate);
        }
        while (loEarlier.get(Calendar.YEAR) != loLater.get(Calendar.YEAR)){
            liTempDifference = 365 * 
                               (loLater.get(Calendar.YEAR) - 
                                loEarlier.get(Calendar.YEAR));
            liDifference += liTempDifference;
            loEarlier.add(Calendar.DAY_OF_YEAR, liTempDifference);
        }
        if (loEarlier.get(Calendar.DAY_OF_YEAR) 
            != loLater.get(Calendar.DAY_OF_YEAR)){
            liTempDifference = 
                loLater.get(Calendar.DAY_OF_YEAR) - 
                loEarlier.get(Calendar.DAY_OF_YEAR);
            liDifference += liTempDifference;
            loEarlier.add(Calendar.DAY_OF_YEAR, liTempDifference);
        }
        return liDifference * liMenor;
    }

    /**
     * Convierte a cadena una fecha con la mascara deseada
     * @autor Jorge Luis Bautista Santiago
     * @param ttObjectDate
     * @param tsOutputMask
     * @return String
     */
    public static String convertDateMask(java.util.Date ttObjectDate,
                                        String tsOutputMask) {
        SimpleDateFormat loSdfOut = new SimpleDateFormat(tsOutputMask);
        java.util.Date   ltDate = ttObjectDate;
        String           lsFormatted = "";
                         lsFormatted = loSdfOut.format(ltDate);
        return lsFormatted;
    }

    /**
     * Valida integridad entre Fecha inicial y fecha final
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateFinalDateListener(ValueChangeEvent toValueChangeEvent) {
        toValueChangeEvent.getNewValue();
        if(getPoInitialDate().getValue() != null && 
           getPoFinalDate().getValue() != null){
            java.util.Date ltDateIni = 
                (java.util.Date)getPoInitialDate().getValue();
            java.util.Date ltDateFin = 
                (java.util.Date)getPoFinalDate().getValue();            
            int            liResValue;
            liResValue = calculateDifference(ltDateIni, ltDateFin);
            if(liResValue < 0){
                getPoInitialDate().setValue(null);
                getPoFinalDate().setValue(null);                
                AdfFacesContext.getCurrentInstance().addPartialTarget(getPoInitialDate());
                AdfFacesContext.getCurrentInstance().addPartialTarget(getPoFinalDate());                 
                FacesMessage loMsg =
                    new FacesMessage("La fecha final debe ser mayor o " +
                    "igual a la fecha inicio");
                loMsg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, loMsg);
            }
        }
    }

    /**
     * Ejecuta la busqueda en base a los parametros
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String searchFilterLogServicesAction() {
        String         lsQuery = " 1 = 1 ";
        String         lsNomServiceSelected = 
            getPoFilterServiceSel().getValue() == null ? "" : 
            getPoFilterServiceSel().getValue().toString();        
        if(!lsNomServiceSelected.equalsIgnoreCase("")){
            lsQuery += " AND ID_SERVICE = " + lsNomServiceSelected;
        }
        String         lsProcess = 
            getPoFilterProcessSel().getValue() == null ? "" : 
            getPoFilterProcessSel().getValue().toString();  
        if(!lsProcess.equalsIgnoreCase("")){
            lsQuery += " AND IND_PROCESS = '" + lsProcess + "'";
        }
        
        /* Fechas */
        java.util.Date ltDateIni = 
            getPoInitialDate().getValue() == null ? null : 
            (java.util.Date)getPoInitialDate().getValue();        
        java.util.Date ltDateFin = 
            getPoFinalDate().getValue() == null ? null : 
            (java.util.Date)getPoFinalDate().getValue(); 
        
        if(ltDateIni != null){
            lsQuery += " AND FEC_REQUEST >= '" + 
                       convertDateMask(ltDateIni, "yyyy-MM-dd") + "'";
        }
        if(ltDateFin != null){
            lsQuery += " AND FEC_REQUEST <= '" + 
                       convertDateMask(ltDateFin, "yyyy-MM-dd") + "'";                
        }
        
        new UtilFaces().refreshTableWhereIterator(lsQuery, lsEntityIterator, getPoTblLog());
        return null;
    }    
    
    /**
     * Regresa un conjunto de servicios web 
     * @autor Jorge Luis Bautista Santiago
     * @param tsStrSearch
     * @return List
     */
    public List getListWebServices() {
        List<SelectItem>        laList = 
            new ArrayList<SelectItem>();
        ViewObjectDao           loMd = new ViewObjectDao();
        List<SelectOneItemBean> laAllWs = loMd.getListAllWebServicesModel();
        for(SelectOneItemBean loWs: laAllWs){
            SelectItem loItm = new SelectItem();            
            loItm.setValue(loWs.getLsId());
            loItm.setDescription(loWs.getLsValue());
            loItm.setLabel(loWs.getLsValue());
            laList.add(loItm);
        }
        return laList;
    }
    
    /**
     * Regresa un conjunto de servicios web 
     * @autor Jorge Luis Bautista Santiago
     * @param tsStrSearch
     * @return List
     */
    public List getListProcess() {
        List<SelectItem>        laList = 
            new ArrayList<SelectItem>();
        ViewObjectDao           loMd = new ViewObjectDao();
        List<SelectOneItemBean> laAllWs = 
            loMd.getListGeneralParametersModelFilter("PROCESS_INTEGRATION");
        for(SelectOneItemBean loWs: laAllWs){
            SelectItem loItm = new SelectItem();           
            loItm.setValue(loWs.getLsId());
            loItm.setDescription(loWs.getLsDescription());
            loItm.setLabel(loWs.getLsValue());
            laList.add(loItm);
        }
        return laList;
    }

    /**
     * Descarga archivo xml de la solicitud
     * @autor Jorge Luis Bautista Santiago
     * @param toFacesContext
     * @param toOutputStream
     * @return void
     */    
    public void downloadFileRequestAction(FacesContext toFacesContext, OutputStream toOutputStream) {
        FacesContext             loCtx = null;
        ExternalContext          loExctx = null;
        HttpServletResponse      loResponse = null;
        FacesMessage             loMsg = null;
        loCtx = FacesContext.getCurrentInstance();        
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblLog().getSelectedRowData();
        String                   lsIdRequest = 
            loNode.getAttribute("IdLogServices") == null ? "" : 
            loNode.getAttribute("IdLogServices").toString();                 
        String                   lsIdService = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();                 
        String lsTypeService = "REQUEST";
        
        loExctx = loCtx.getExternalContext();
        loResponse = (HttpServletResponse)loExctx.getResponse();
        try{
            ApplicationModule         loAm = 
                Configuration.createRootApplicationModule(lsAmDef, lsConfig);
            AppModuleIntergrationImpl  loService = (AppModuleIntergrationImpl)loAm;
            try{
                EvetvIntXmlFilesRowBean loEvetvIntXmlFilesRowBean = 
                    loService.getRowXmlFilesModel(Integer.parseInt(lsIdRequest), 
                                                  Integer.parseInt(lsIdService), 
                                                  lsTypeService
                                                );
                if(loEvetvIntXmlFilesRowBean != null){
                    if(loEvetvIntXmlFilesRowBean.getLiIdRequest() > 0){                    
                        Blob loFileBlob = loService.getBlobFileXml(lsIdRequest,
                                                              lsIdService,
                                                              lsTypeService);      
                        if(loFileBlob != null){
                            InputStream loIS = loFileBlob.getBinaryStream();        
                            if(loIS == null){
                                loMsg = new FacesMessage("El archivo no existe");
                                loMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                                FacesContext.getCurrentInstance().addMessage(null, loMsg);
                            }else{
                                loResponse.setHeader("Content-Disposition",
                                                     "attachment; filename=\"" +
                                                     loEvetvIntXmlFilesRowBean.getLsNomFile() + "\"");
                                ServletOutputStream loOS =loResponse.getOutputStream();        
                                try{
                                    IOUtils.copy(loIS, loOS);    
                                }catch(Exception loExp){
                                    System.out.println(loExp.getMessage());
                                }
                                loOS.flush();
                                loIS.close();
                            }
                        }else{                            
                            
                            loMsg = new FacesMessage("El archivo no existe");
                            loMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                            toFacesContext.addMessage(null, loMsg);
                            
                        }
                    }else{
                        
                        toOutputStream.flush();
                        toOutputStream.close();
                        new UtilFaces().showPopup(poPopupProgramas);
                        ExternalContext    loEctx = toFacesContext.getExternalContext();
                        String             lsUrl = 
                            loEctx.getRequestContextPath() + "/faces/monitorPage";
                        try {
                            loEctx.redirect(lsUrl);
                        } catch (IOException loEx) {
                            ;
                        }
                        loMsg = new FacesMessage("El archivo no existe");
                        loMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                        toFacesContext.addMessage(null, loMsg);
                    }
                }else{
                    loMsg = new FacesMessage("El archivo no existe");
                    loMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext.getCurrentInstance().addMessage(null, loMsg);
                }
            } catch (Exception loEx) {
                loMsg = new FacesMessage("Error de Comunicacion " + loEx);
                loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
                FacesContext.getCurrentInstance().addMessage(null, loMsg);
            } finally {
                Configuration.releaseRootApplicationModule(loAm, true);
            }
        } catch (Exception loEx) {
            loEx.printStackTrace();
            loMsg =
                new FacesMessage("Error al descargar el archivo.");
            loMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null,
                                                         loMsg);
        }
    }
    
    /**
     * Descarga archivo xml de la solicitud
     * @autor Jorge Luis Bautista Santiago
     * @param toFacesContext
     * @param toOutputStream
     * @return void
     */    
    public void downloadFileResponseAction(FacesContext toFacesContext, OutputStream toOutputStream) {
        FacesContext             loCtx = null;
        ExternalContext          loExctx = null;
        HttpServletResponse      loResponse = null;
        FacesMessage             loMsg = null;        
        loCtx = FacesContext.getCurrentInstance();        
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblLog().getSelectedRowData();
        String                   lsIdRequest = 
            loNode.getAttribute("IdLogServices") == null ? "" : 
            loNode.getAttribute("IdLogServices").toString();                 
        String                   lsIdService = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();                 
        String lsTypeService = "RESPONSE";
        
        loExctx = loCtx.getExternalContext();
        loResponse = (HttpServletResponse)loExctx.getResponse();
        try{
            ApplicationModule         loAm = 
                Configuration.createRootApplicationModule(lsAmDef, lsConfig);
            AppModuleIntergrationImpl  loService = (AppModuleIntergrationImpl)loAm;
            try{
                //Crear bean front de la tabla, minimo con nomArch y con el stream
                EvetvIntXmlFilesRowBean loEvetvIntXmlFilesRowBean = 
                    loService.getRowXmlFilesModel(Integer.parseInt(lsIdRequest), 
                                                  Integer.parseInt(lsIdService), 
                                                  lsTypeService
                                                );
                if(loEvetvIntXmlFilesRowBean != null){
                    if(loEvetvIntXmlFilesRowBean.getLiIdRequest() > 0){
                        Blob loFileBlob = loService.getBlobFileXml(lsIdRequest,
                                                              lsIdService,
                                                              lsTypeService);       
                        if(loFileBlob != null){
                            InputStream loIS = loFileBlob.getBinaryStream();                
                            if(loIS == null){
                                loMsg = new FacesMessage("El archivo no existe");
                                loMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                                FacesContext.getCurrentInstance().addMessage(null, loMsg);
                            }else{
                                loResponse.setHeader("Content-Disposition",
                                                     "attachment; filename=\"" +
                                                     loEvetvIntXmlFilesRowBean.getLsNomFile() + "\"");
                                ServletOutputStream loOS =loResponse.getOutputStream();        
                                try{
                                    IOUtils.copy(loIS, loOS);    
                                }catch(Exception loExp){
                                    System.out.println(loExp.getMessage());
                                }
                                loOS.flush();
                                loIS.close();
                            }
                        }else{
                            loMsg = new FacesMessage("El archivo no existe");
                            loMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                            FacesContext.getCurrentInstance().addMessage(null, loMsg);
                        }
                    
                    }else{
                        
                        toOutputStream.flush();
                        toOutputStream.close();
                        new UtilFaces().showPopup(poPopupProgramas);
                        ExternalContext    loEctx = toFacesContext.getExternalContext();
                        String             lsUrl = 
                            loEctx.getRequestContextPath() + "/faces/monitorPage";
                        try {
                            loEctx.redirect(lsUrl);
                        } catch (IOException loEx) {
                            ;
                        }
                        loMsg = new FacesMessage("El archivo no existe");
                        loMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                        toFacesContext.addMessage(null, loMsg);
                    }
                }else{
                    loMsg = new FacesMessage("El archivo no existe");
                    loMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                    FacesContext.getCurrentInstance().addMessage(null, loMsg);
                }
            } catch (Exception loEx) {
                loMsg = new FacesMessage("Error de Comunicacion " + loEx);
                loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
                FacesContext.getCurrentInstance().addMessage(null, loMsg);
            } finally {
                Configuration.releaseRootApplicationModule(loAm, true);
            }
        } catch (Exception loEx) {
            loEx.printStackTrace();
            loMsg =
                new FacesMessage("Error al descargar el archivo.");
            loMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null,
                                                         loMsg);
        }
    }
    
    /**
     * Muestra popup de detalle del request del servicio en cuestion
     * @autor Jorge Luis Bautista Santiago
     * @param toFacesContext
     * @param toOutputStream
     * @return void
     */    
    public void showParrillasDetails(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        boolean                  lbFlagRef = true;
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblLog().getSelectedRowData();
        String                   lsIdRequest = 
            loNode.getAttribute("IdLogServices") == null ? "" : 
            loNode.getAttribute("IdLogServices").toString();
        String                   lsServiceType = 
            loNode.getAttribute("Attribute9") == null ? "" : 
            loNode.getAttribute("Attribute9").toString();
        
        String lsWhere = "ID_REQUEST = " + lsIdRequest;
        String lsEntityIteratorType = "EvetvIntRstParrillasTabView1Iterator";
        RichTable loTablePop = getPoTblParrillas();
        RichPopup loPopupService = getPoParrillasPopup();
        if(lsServiceType.equalsIgnoreCase("WsBreaks")){
            lsEntityIteratorType = "EvetvIntRstParrillasTabView1Iterator";
            loTablePop = getPoTblParrillas();            
            loPopupService = getPoParrillasPopup();
        }
        if(lsServiceType.equalsIgnoreCase("WsProgramas")){
            lsEntityIteratorType = "EvetvIntRstProgramasTabView1Iterator";
            loTablePop = getPoTblProgramas();
            loPopupService = getPoPopupProgramas();
        }
        if(lsServiceType.equalsIgnoreCase("WsVentaTradicional")){
            lsEntityIteratorType = "EvetvIntRstVtradicionalTabView1Iterator";
            loTablePop = getPoTblTradicional();
            loPopupService = getPoPopupTradicional();
        }
        if(lsServiceType.equalsIgnoreCase("WsLogCertificado")){
            lsEntityIteratorType = "";
            loTablePop = getPoTblParrillas();
            loPopupService = getPoPopupProgramas();
        }
        if(lsServiceType.equalsIgnoreCase("WsVentaModulos")){
            lsEntityIteratorType = "";
            loTablePop = getPoTblProgramas();
            loPopupService = getPoPopupNoDisponible();
            lbFlagRef = false;
        }
        if(lsServiceType.equalsIgnoreCase("WsLogComercial	")){
            lsEntityIteratorType = "";
            loTablePop = getPoTblProgramas();
            loPopupService = getPoPopupNoDisponible();
            lbFlagRef = false;
        }
        
        if(lbFlagRef){
            new UtilFaces().refreshTableWhereIterator(lsWhere,
                                                      lsEntityIteratorType,
                                                      loTablePop
                                                      );
        }
        new UtilFaces().showPopup(loPopupService);
    }
     
    /****************** SETTERS AND GETTERS ******************************/    
    
    public void setPoTblLog(RichTable poTblLog) {
        this.poTblLog = poTblLog;
    }

    public RichTable getPoTblLog() {
        return poTblLog;
    }

    public void setPoFilterServiceSel(RichSelectOneChoice poFilterServiceSel) {
        this.poFilterServiceSel = poFilterServiceSel;
    }

    public RichSelectOneChoice getPoFilterServiceSel() {
        return poFilterServiceSel;
    }

    public void setPoFilterIdService(RichInputText poFilterIdService) {
        this.poFilterIdService = poFilterIdService;
    }

    public RichInputText getPoFilterIdService() {
        return poFilterIdService;
    }

    public void setPoFilterProcessSel(RichSelectOneChoice poFilterProcessSel) {
        this.poFilterProcessSel = poFilterProcessSel;
    }

    public RichSelectOneChoice getPoFilterProcessSel() {
        return poFilterProcessSel;
    }

    public void setPoInitialDate(RichInputDate poInitialDate) {
        this.poInitialDate = poInitialDate;
    }

    public RichInputDate getPoInitialDate() {
        return poInitialDate;
    }
    
    public void setPoFinalDate(RichInputDate poFinalDate) {
        this.poFinalDate = poFinalDate;
    }

    public RichInputDate getPoFinalDate() {
        return poFinalDate;
    }


    public void setPoParrillasPopup(RichPopup poParrillasPopup) {
        this.poParrillasPopup = poParrillasPopup;
    }

    public RichPopup getPoParrillasPopup() {
        return poParrillasPopup;
    }
    
    public void setPoTblParrillas(RichTable poTblParrillas) {
        this.poTblParrillas = poTblParrillas;
    }

    public RichTable getPoTblParrillas() {
        return poTblParrillas;
    }

    public void setPoPopupProgramas(RichPopup poPopupProgramas) {
        this.poPopupProgramas = poPopupProgramas;
    }

    public RichPopup getPoPopupProgramas() {
        return poPopupProgramas;
    }

    public void setPoTblProgramas(RichTable poTblProgramas) {
        this.poTblProgramas = poTblProgramas;
    }

    public RichTable getPoTblProgramas() {
        return poTblProgramas;
    }

    public void setPoPopupTradicional(RichPopup poPopupTradicional) {
        this.poPopupTradicional = poPopupTradicional;
    }

    public RichPopup getPoPopupTradicional() {
        return poPopupTradicional;
    }

    public void setPoTblTradicional(RichTable poTblTradicional) {
        this.poTblTradicional = poTblTradicional;
    }

    public RichTable getPoTblTradicional() {
        return poTblTradicional;
    }

    public void setPoPopupNoDisponible(RichPopup poPopupNoDisponible) {
        this.poPopupNoDisponible = poPopupNoDisponible;
    }

    public RichPopup getPoPopupNoDisponible() {
        return poPopupNoDisponible;
    }
}
