/**
* Project: Paradigm - eVeTV Integration
*
* File: BitacoraBean.java
*
* Created on: Septiembre 6, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/

package com.televisa.integration.view.front.beans;

import com.televisa.integration.view.front.beans.types.SelectOneItemBean;
import com.televisa.integration.view.front.daos.ViewObjectDao;
import com.televisa.integration.view.front.util.UtilFaces;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

/** Esta clase es un bean que enlaza la pantalla de monitoreo de servicios<br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Septiembre 14, 2017, 12:00 pm
 */
public class BitacoraBean {
    private RichSelectOneChoice poFilterServiceSel;
    private RichInputText       poFilterIdService;
    private RichSelectOneChoice poFilterProcessSel;
    private RichInputDate       poInitialDate;
    private RichInputDate       poFinalDate;
    private RichTable           poTblLog;
    String  gsEntityIterator    = "EvetvIntServicesBitacoraVwView1Iterator";
    
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
        new UtilFaces().refreshTableWhereIterator("1 = 1 ", gsEntityIterator,getPoTblLog());        
        return null;
    }
    
    /**
     * Valida integridad entre Fecha inicial y fecha final
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateDateListener(ValueChangeEvent toValueChangeEvent) {
        int            liResValue;
        toValueChangeEvent.getNewValue();
        if(getPoInitialDate().getValue() != null && 
           getPoFinalDate().getValue() != null){
            java.util.Date ltDateIni = 
                (java.util.Date)getPoInitialDate().getValue();
            java.util.Date ltDateFin = 
                (java.util.Date)getPoFinalDate().getValue();            
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
        String lsQuery = " 1 = 1 ";
        String lsNomServiceSelected = 
            getPoFilterServiceSel().getValue() == null ? "": 
            getPoFilterServiceSel().getValue().toString();        
        if(!lsNomServiceSelected.equalsIgnoreCase("")){
            lsQuery += " AND ID_SERVICE = "+lsNomServiceSelected;
        }
        String lsProcess = 
            getPoFilterProcessSel().getValue() == null ? "": 
            getPoFilterProcessSel().getValue().toString();  
        if(!lsProcess.equalsIgnoreCase("")){
            lsQuery += " AND IND_PROCESS = '"+lsProcess+"'";
        }
        
        /* Fechas */
        java.util.Date ltDateIni = 
            getPoInitialDate().getValue() == null ? null : 
            (java.util.Date)getPoInitialDate().getValue();        
        java.util.Date ltDateFin = 
            getPoFinalDate().getValue() == null ? null : 
            (java.util.Date)getPoFinalDate().getValue(); 
        
        if(ltDateIni != null){
            lsQuery += " AND FEC_CREATION_DATE >= '" + 
                       convertDateMask(ltDateIni, "yyyy-MM-dd") + "'";
        }
        if(ltDateFin != null){
            lsQuery += " AND FEC_CREATION_DATE <= '" + 
                       convertDateMask(ltDateFin, "yyyy-MM-dd") + "'";                
        }
        
        new UtilFaces().refreshTableWhereIterator(lsQuery, gsEntityIterator, getPoTblLog());
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

    /****************** SETTERS AND GETTERS ******************************/    
    
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

    public void setPoTblLog(RichTable poTblLog) {
        this.poTblLog = poTblLog;
    }

    public RichTable getPoTblLog() {
        return poTblLog;
    }

    public void setLsEntityIterator(String lsEntityIterator) {
        this.gsEntityIterator = lsEntityIterator;
    }

    public String getLsEntityIterator() {
        return gsEntityIterator;
    }

}
