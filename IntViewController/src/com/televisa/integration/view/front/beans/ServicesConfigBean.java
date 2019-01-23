/**
* Project: Paradigm - eVeTV Integration
*
* File: ServicesConfigBean.java
*
* Created on:  Septiembre 6, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package com.televisa.integration.view.front.beans;

import com.televisa.comer.integration.model.beans.pgm.EvetvIntServiceBitacoraTab;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.integration.model.AppModuleIntergrationImpl;
import com.televisa.integration.model.types.EvetvIntCronConfigTabRowBean;
import com.televisa.integration.model.types.EvetvIntServicesParamTabBean;
import com.televisa.integration.view.front.beans.types.ExecuteServiceResponseBean;
import com.televisa.integration.view.front.beans.types.SelectOneItemBean;
import com.televisa.integration.view.front.daos.ViewObjectDao;
import com.televisa.integration.view.front.util.UtilFaces;
import com.televisa.integration.view.jobs.ExecuteLogCertificadoCron;
import com.televisa.integration.view.jobs.ExecuteParrillasCron;
import com.televisa.integration.view.jobs.ExecuteParrillasOnDemandCron;
import com.televisa.integration.view.jobs.ExecuteProgramasCron;
import com.televisa.integration.view.jobs.ExecuteTargetsCron;
import com.televisa.integration.view.jobs.ExecuteVtaTradicionalCron;
import com.televisa.integration.view.jobs.ExecuteVtaTradicionalUsrCron;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.RichPanelWindow;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputNumberSpinbox;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanRadio;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlHierNodeBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.client.Configuration;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

/** Esta clase es un bean que enlaza la pantalla de Configuracion de Servicios<br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Septiembre 14, 2017, 12:00 pm
 */
public class ServicesConfigBean {
    private RichPopup                 poPopupSaveService;
    private RichInputText             poPopSaveNomService;
    private RichInputText             poPopSaveWsdl;
    private RichSelectOneChoice       poPopSaveSystem;
    private RichSelectOneChoice       poPopSaveSystemOr;
    private RichSelectOneChoice       poPopSaveSystemDes;
    private RichSelectBooleanCheckbox poPopSaveAsyn;
    private RichSelectBooleanCheckbox poPopSaveStatus;
    private RichPopup                 poPopupDeleteService;
    private RichPanelLabelAndMessage  poDeleteMsgLbl;
    private RichTable                 poTblWebServices;
    private RichPopup                 poPopupUpdateService;
    private RichInputText             poPopUpdateIdService;
    private RichInputText             poPopUpdateNomService;
    private RichInputText             poPopUpdateWsdl;
    private RichSelectOneChoice       poPopUpdateSystem;
    private RichSelectOneChoice       poPopUpdateSystemOr;
    private RichSelectOneChoice       poPopUpdateSystemDes;
    private RichSelectBooleanCheckbox poPopUpdateAsyn;
    private RichSelectBooleanCheckbox poPopUpdateStatus;
    private RichSelectOneChoice       poFilterSystem;
    private RichSelectOneChoice       poFilterSystemOr;
    private RichSelectOneChoice       poFilterSystemDes;
    private RichInputText             poFilterWsdl;
    private RichInputText             poFilterService;
    private RichSelectOneChoice       poFilterAsynSel;
    private RichSelectOneChoice       poFilterStatusSel;
    private RichOutputText            poDeleteIdBinding;    
    String                            gsEntityView = "EvetvIntServicesCatTabView1";
    String                            gsEntityIterator = "EvetvIntServicesCatTabView1Iterator";                                            
    String                            gsAppModule = "AppModuleIntergrationDataControl";
    String                            gsAmDef = "com.televisa.integration.model.AppModuleIntergrationImpl";
    String                            gsConfig = "AppModuleIntergrationLocal";
    private RichPopup                 poPopupExecuteService;
    private RichPanelLabelAndMessage  poExecuteMsgLbl;
    private RichOutputText            poExecuteIdBinding;
    private RichOutputText            poExecuteNomBinding;
    private RichSelectOneChoice       poSaveNomServiceSel;
    private RichOutputText            poActionServiceBinding;
    private RichPanelWindow           poPopPanelExeBinding;
    private RichInputText             poPopUpdateDesService;
    private RichInputText             poIdTabSelected;
    private RichPanelTabbed           poPanelTabbed;
    private RichShowDetailItem        poTabMinutos;
    private RichShowDetailItem        poTabHoras;
    private RichInputNumberSpinbox    poCadaMinutos;
    private RichSelectBooleanCheckbox poLunMinutos;
    private RichSelectBooleanCheckbox poMarMinutos;
    private RichSelectBooleanCheckbox poMieMinutos;
    private RichSelectBooleanCheckbox poJueMinutos;
    private RichSelectBooleanCheckbox poVieMinutos;
    private RichSelectBooleanCheckbox poSabMinutos;
    private RichSelectBooleanCheckbox poDomMinutos;
    private RichInputNumberSpinbox    poCadaHoras;
    private RichSelectBooleanCheckbox poLunHoras;
    private RichSelectBooleanCheckbox poMarHoras;
    private RichSelectBooleanCheckbox poMieHoras;
    private RichInputNumberSpinbox    poBeginHoras;
    private RichSelectBooleanCheckbox poJueHoras;
    private RichSelectBooleanCheckbox poVieHoras;
    private RichSelectBooleanCheckbox poSabHoras;
    private RichSelectBooleanCheckbox poDomHoras;
    private RichInputText             poIdServiceSelected;
    private RichInputNumberSpinbox    poBeginMinutes;
    private RichSelectBooleanRadio    poRadioBegin;
    //private RichSelectBooleanRadio    poRadioHourEvery;
    //private RichSelectBooleanRadio    poRadioHourBegin;
    private RichSelectBooleanRadio    poRadioDayEvery;
    private RichInputNumberSpinbox    poCadaDias;
    private RichSelectBooleanRadio    poRadioDayBegin;
    private RichInputNumberSpinbox    poBeginDias;
    private RichInputNumberSpinbox    poBeginDayMinutes;
    private RichSelectBooleanCheckbox poLunDias;
    private RichSelectBooleanCheckbox poMarDias;
    private RichSelectBooleanCheckbox poMieDias;
    private RichSelectBooleanCheckbox poJueDias;
    private RichSelectBooleanCheckbox poVieDias;
    private RichSelectBooleanCheckbox poSabDias;
    private RichSelectBooleanCheckbox poDomDias;
    private RichInputNumberSpinbox    poBeginSemanas;
    private RichInputNumberSpinbox    poBeginDaySemanas;
    private RichSelectBooleanCheckbox poLunWeek;
    private RichSelectBooleanCheckbox poMarWeek;
    private RichSelectBooleanCheckbox poMieWeek;
    private RichSelectBooleanCheckbox poJueWeek;
    private RichSelectBooleanCheckbox poVieWeek;
    private RichSelectBooleanCheckbox poSabWeek;
    private RichSelectBooleanCheckbox poDomWeek;
    private RichInputNumberSpinbox    poSemanasHoraIni;
    private RichInputNumberSpinbox    poBeginSemanasMinutoIni;
    private RichSelectOneChoice       poDayOfSelected;
    private RichSelectBooleanRadio    poRadioTheDay;
    private RichInputNumberSpinbox    poMesTheDay;
    private RichInputNumberSpinbox    poMesEvery;
    private RichSelectBooleanRadio    poRadioTheDayOfWeek;
    private RichSelectOneChoice       poDayOfWeekSelected;
    private RichInputNumberSpinbox    poMesEveryOf;
    private RichInputNumberSpinbox    poMesHoraIni;
    private RichInputNumberSpinbox    poMesMinutoIni;
    private RichButton                poBtnSaveCronService;
    private RichButton                poBtnDeleteCronService;
    private RichShowDetailItem        poTabDias;
    private RichShowDetailItem        poTabMeses;
    private RichShowDetailItem        poTabSemanas;
    private RichInputText             poOperation;
    private RichInputText             poIdCronConfiguration;
    private RichPopup                 poPopupDeleteCronService;
    private RichPanelLabelAndMessage  poDeleteCronMsgLbl;
    private RichOutputText            poDeleteCronIdBinding;
    private RichInputText             poNomServicioCron;
    private RichLink                  poServiceToCron;
    private RichSelectOneChoice       poFilterDesServiceSel;
    private RichLink                  poServiceToCronStatus;
    private RichSelectOneChoice       poFilterServiceSel;
    private RichInputNumberSpinbox    poDeadLineHoras;
    private RichInputNumberSpinbox    poDeadLineMinutos;
    private RichInputNumberSpinbox    poDeadLineMinHoras;
    private RichInputNumberSpinbox    poDeadLineMinMinutos;
    private RichPopup poPopupSaveCronService;
    private RichPanelLabelAndMessage poSaveCronMsgLbl;
    private RichInputNumberSpinbox poBeginHorasMin;
    private RichInputNumberSpinbox poBeginMinutesMin;
    //private String glGroup = "Integration";
    private RichButton poBtnResetCronService;
    private RichPopup poPopupRstCronService;

    /**
     * Oculta popup que solicita datos para guardar servicio
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String cancelSaveServiceAction() {
        new UtilFaces().hidePopup(getPoPopupSaveService());
        return null;
    }

    /**
     * Actualiza en base de datos el servicio seleccionado
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String updateServiceAction() {        
        String                   lsIdService = 
            getPoPopUpdateIdService().getValue() == null ? "" : 
            getPoPopUpdateIdService().getValue().toString();
        String                   lsNomService = 
            getPoPopUpdateNomService().getValue() == null ? "" : 
            getPoPopUpdateNomService().getValue().toString();
        String                   lsDesService = 
            getPoPopUpdateDesService().getValue() == null ? "" : 
            getPoPopUpdateDesService().getValue().toString();
        String                   lsIndServiceWsdl = 
            getPoPopUpdateWsdl().getValue() == null ? "" :
            getPoPopUpdateWsdl().getValue().toString();
        String                   lsIndSystem = 
            getPoPopUpdateSystem().getValue() == null ? "" : 
            getPoPopUpdateSystem().getValue().toString();
        String                   lsIndOrigin = 
            getPoPopUpdateSystemOr().getValue() == null ? "" : 
            getPoPopUpdateSystemOr().getValue().toString();
        String                   lsIndDestiny = 
            getPoPopUpdateSystemDes().getValue() == null ? "" :
            getPoPopUpdateSystemDes().getValue().toString();
        String                   lsIndEstatus = 
            getPoPopUpdateStatus().getValue() == null ? "" : 
            getPoPopUpdateStatus().getValue().toString();
        String                   lsIndSynchronous = 
            getPoPopUpdateAsyn().getValue() == null ? "" :
            getPoPopUpdateAsyn().getValue().toString();
        String                   lsStatusTab = "0";
        if(lsIndEstatus.equalsIgnoreCase("true")){
            lsStatusTab = "1";
        }
        String                    lsAsynTab = "0";
        if(lsIndSynchronous.equalsIgnoreCase("true")){
            lsAsynTab = "1";
        }
        ApplicationModule          loAm = 
            Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        AppModuleIntergrationImpl  loService = (AppModuleIntergrationImpl)loAm;
        try{
            loService.updateServicesCatModel(Integer.parseInt(lsIdService),
                                             lsNomService,
                                             lsDesService,
                                             lsIndServiceWsdl,
                                             lsIndSystem,
                                             lsIndOrigin,
                                             lsIndDestiny,
                                             lsStatusTab,
                                             lsAsynTab
                                            );            
        } catch (Exception loEx) {
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx);
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
        }
        new UtilFaces().refreshTableWhereIterator("1 = 1",
                                                  gsEntityIterator,
                                                  getPoTblWebServices()
                                                  );
        new UtilFaces().hidePopup(getPoPopupUpdateService());
        
        return null;
    }

    /**
     * Oculta popup de Actualizacion
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String cancelUpdateServiceAction() {
        getPoPopUpdateNomService().setValue("");
        getPoPopUpdateWsdl().setValue("");
        getPoPopUpdateSystem().setValue("");
        getPoPopUpdateSystemOr().setValue("");
        getPoPopUpdateSystemDes().setValue("");
        getPoPopUpdateStatus().setValue("");
        getPoPopUpdateAsyn().setValue("");
        new UtilFaces().hidePopup(getPoPopupUpdateService());        
        FacesContext       loContext = FacesContext.getCurrentInstance();
        ExternalContext    loEctx = loContext.getExternalContext();
        String             lsUrl = 
            loEctx.getRequestContextPath() + "/faces/servicesConfigPage";
        try {
            loEctx.redirect(lsUrl);
        } catch (IOException loEx) {
            ;
        }
        return null;
    }

    /**
     * Ejecuta la busqueda en base a los parametros
     * @autor Jorge Luis Bautista Santiago       
     * @return String
     */
    public String searchFilterServicesAction() {
        String lsQuery = " 1 = 1 ";
        String lsNomServiceSelected = 
            getPoFilterDesServiceSel().getValue() == null ? "" : 
            getPoFilterDesServiceSel().getValue().toString();        
        if(!lsNomServiceSelected.equalsIgnoreCase("")){
            lsQuery += " AND ID_SERVICE = " + lsNomServiceSelected;
        }
        
        String lsNomService = 
            getPoFilterServiceSel().getValue() == null ? "" :
            getPoFilterServiceSel().getValue().toString();        
        if(!lsNomService.equalsIgnoreCase("")){
            lsQuery += " AND UPPER(NOM_SERVICE) LIKE '" + 
                       lsNomService.toUpperCase() + "%'";
        }
        
        String lsIndServiceWsdl = 
            getPoFilterWsdl().getValue() == null ? "" :
            getPoFilterWsdl().getValue().toString();
        if(!lsIndServiceWsdl.equalsIgnoreCase("")){
            lsQuery += " AND UPPER(IND_SERVICE_WSDL) LIKE '" +
                       lsIndServiceWsdl.toUpperCase() + "%'";
        }
                
        String lsIndSystem = 
            getPoFilterSystem().getValue() == null ? "" : 
            getPoFilterSystem().getValue().toString();
        if(!lsIndSystem.equalsIgnoreCase("")){
            lsQuery += " AND IND_SYSTEM = '" + lsIndSystem + "'";
        }
        String lsIndOrigin = 
            getPoFilterSystemOr().getValue() == null ? "" : 
            getPoFilterSystemOr().getValue().toString();
        if(!lsIndOrigin.equalsIgnoreCase("")){
            lsQuery += " AND IND_ORIGIN = '" + lsIndOrigin + "'";
        }
        
        String lsIndDestiny = 
            getPoFilterSystemDes().getValue() == null ? "" :
            getPoFilterSystemDes().getValue().toString();
        if(!lsIndDestiny.equalsIgnoreCase("")){
            lsQuery += " AND IND_DESTINY = '" + lsIndDestiny + "'";
        }
        
        String lsIndEstatus = 
            getPoFilterStatusSel().getValue() == null ? "" :
            getPoFilterStatusSel().getValue().toString();        
        if(!lsIndEstatus.equalsIgnoreCase("")){
            lsQuery += " AND IND_ESTATUS = '" + lsIndEstatus + "'";
        }
        
        String lsIndSynchronous = 
            getPoFilterAsynSel().getValue() == null ? "" :
            getPoFilterAsynSel().getValue().toString();
        if(!lsIndSynchronous.equalsIgnoreCase("")){
            lsQuery += " AND IND_SYNCHRONOUS = '" + lsIndSynchronous + "'";
        }                      
        
        new UtilFaces().refreshTableWhereIterator(lsQuery,
                                                  gsEntityIterator,
                                                  getPoTblWebServices()
                                                  );
        return null;
    }

    /**
     * Actualiza la tabla principal a su estado inicial
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String refreshMainTable() {
        new UtilFaces().refreshTableWhereIterator("1 = 1 ",
                                                  gsEntityIterator,
                                                  getPoTblWebServices()
                                                  );
        return null;
    }

    /**
     * Reinicia los valores de busqueda
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     * @return void
     */
    public void resetFilterValues(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        getPoFilterService().setValue("");
        getPoFilterWsdl().setValue("");
        getPoFilterSystem().setValue("");
        getPoFilterSystemOr().setValue("");
        getPoFilterSystemDes().setValue("");
        getPoFilterStatusSel().setValue("");
        getPoFilterAsynSel().setValue("");
    }
    
    /**
     * Muestra popup que solicita datos para editar servicio
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     * @return void
     */
    public void showEditPopupService(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblWebServices().getSelectedRowData();
        String                   lsIdService = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();
        String                   lsNomService = 
            loNode.getAttribute("NomService") == null ? "" : 
            loNode.getAttribute("NomService").toString();
        String                   lsServiceDesc = 
            loNode.getAttribute("IndDescService") == null ? "" : 
            loNode.getAttribute("IndDescService").toString();
        String                   lsIndServiceWsdl = 
            loNode.getAttribute("IndServiceWsdl") == null ? "" : 
            loNode.getAttribute("IndServiceWsdl").toString();
        String                   lsIndSystem = 
            loNode.getAttribute("IndSystem") == null ? "" : 
            loNode.getAttribute("IndSystem").toString();
        String                   lsIndOrigin = 
            loNode.getAttribute("IndOrigin") == null ? "" : 
            loNode.getAttribute("IndOrigin").toString();
        String                   lsIndDestiny = 
            loNode.getAttribute("IndDestiny") == null ? "" : 
            loNode.getAttribute("IndDestiny").toString();
        String                   lsIndSynchronous = 
            loNode.getAttribute("IndSynchronous") == null ? "" : 
            loNode.getAttribute("IndSynchronous").toString();
        String                   lsIndEstatus = 
            loNode.getAttribute("IndEstatus") == null ? "" : 
            loNode.getAttribute("IndEstatus").toString();

        getPoPopUpdateDesService().setValue(lsServiceDesc);
        getPoPopUpdateIdService().setValue(lsIdService);        
        getPoPopUpdateNomService().setValue(lsNomService);
        getPoPopUpdateWsdl().setValue(lsIndServiceWsdl);
        getPoPopUpdateSystem().setValue(lsIndSystem);
        getPoPopUpdateSystemOr().setValue(lsIndOrigin);
        getPoPopUpdateSystemDes().setValue(lsIndDestiny);
        if(lsIndSynchronous.equalsIgnoreCase("1")){
            getPoPopUpdateAsyn().setSelected(true);
        }else{
            getPoPopUpdateAsyn().setSelected(false);
        }
        if(lsIndEstatus.equalsIgnoreCase("1")){
            getPoPopUpdateStatus().setSelected(true);
        }else{
            getPoPopUpdateStatus().setSelected(false);
        }
        
        new UtilFaces().showPopup(getPoPopupUpdateService());
        
    }
    
    /**
     * Elimina el servicio web de la base de datos
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String deleteWebServiceAction() {
        String                    lsIdService = 
            getPoDeleteIdBinding().getValue() == null ? "" : 
            getPoDeleteIdBinding().getValue().toString();
        ApplicationModule         loAm = 
            Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
        try{
            loService.deleteServicesCatModel(Integer.parseInt(lsIdService));            
        } catch (Exception loEx) {
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx);
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
        }
        resetMinuteTab();resetHoursTab();resetDaysTab();resetWeeksTab();resetMonthsTab();
        resetDisableMinuteTab(true);resetDisableHoursTab(true);
        resetDisableDaysTab(true);resetDisableWeeksTab(true);resetDisableMonthsTab(true);
        resetDisableAllTab(true);
        resetDisableTabButtons(true);
        getPoIdTabSelected().setValue("MINUTOS");
        getPoIdServiceSelected().setValue(null);
        getPoOperation().setValue("INSERT");   
        setDisclosedFirstTab(getPoTabMinutos(),getPoPanelTabbed());
        getPoServiceToCron().setText(null);
        getPoServiceToCron().setVisible(false);
        getPoServiceToCronStatus().setText(null);
        getPoServiceToCronStatus().setVisible(false);
        new UtilFaces().refreshTableWhereIterator("1 = 1",
                                                  gsEntityIterator,
                                                  getPoTblWebServices()
                                                  );
        new UtilFaces().hidePopup(getPoPopupDeleteService());
        return null;
    }

    /**
     * Oculta popup de eliminar servicio
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String cancelDeleteWebService() {
        new UtilFaces().hidePopup(getPoPopupDeleteService());
        return null;
    }

    /**
     * Muestra popup que solicita datos para eliminar el servicio
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     * @return void
     */
    public void showDeletePopupService(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblWebServices().getSelectedRowData();
        String                   lsIdService = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();
        String                   lsServiceName = 
            loNode.getAttribute("NomService") == null ? "" : 
            loNode.getAttribute("NomService").toString();
        String                   lsServiceDesc = 
            loNode.getAttribute("IndDescService") == null ? "" : 
            loNode.getAttribute("IndDescService").toString();
        getPoDeleteIdBinding().setValue(lsIdService);
        getPoDeleteMsgLbl().setLabel("Eliminar a " + lsServiceDesc + " - " + lsServiceName + "?");
        new UtilFaces().showPopup(getPoPopupDeleteService());
    }

    /**
     * Muestra popup que solicita datos para guardar el servicio
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     * @return String
     */
    public void showSavePopupService(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        getPoPopSaveNomService().setValue("");
        getPoPopSaveWsdl().setValue("");
        getPoPopSaveSystem().setValue("");
        getPoPopSaveSystemOr().setValue("");
        getPoPopSaveSystemDes().setValue("");
        getPoPopSaveStatus().setValue("");
        getPoPopSaveAsyn().setValue("");
        new UtilFaces().showPopup(getPoPopupSaveService());
    }

    /**
     * Accion que guarda los parametros del servicio web
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String saveServiceAction() {
        Integer liIdService = new ViewObjectDao().getMaxIdServicesCatalog() + 1;
        String lsDescService = 
            getPoPopSaveNomService().getValue() == null ? "" : 
            getPoPopSaveNomService().getValue().toString();
        String lsNomService = 
            getPoSaveNomServiceSel().getValue() == null ? "" : 
            getPoSaveNomServiceSel().getValue().toString();
        String lsIndServiceWsdl = 
            getPoPopSaveWsdl().getValue() == null ? "" :
            getPoPopSaveWsdl().getValue().toString();
        String lsIndSystem = 
            getPoPopSaveSystem().getValue() == null ? "" : 
            getPoPopSaveSystem().getValue().toString();
        String lsIndOrigin = 
            getPoPopSaveSystemOr().getValue() == null ? "" :
            getPoPopSaveSystemOr().getValue().toString();
        String lsIndDestiny = 
            getPoPopSaveSystemDes().getValue() == null ? "" : 
            getPoPopSaveSystemDes().getValue().toString();
        String lsIndEstatus = 
            getPoPopSaveStatus().getValue() == null ? "" : 
            getPoPopSaveStatus().getValue().toString();
        String lsIndSynchronous = 
            getPoPopSaveAsyn().getValue() == null ? "" : 
            getPoPopSaveAsyn().getValue().toString();
        String lsStatusTab = "0";
        if(lsIndEstatus.equalsIgnoreCase("true")){
            lsStatusTab = "1";
        }
        String lsAsynTab = "0";
        if(lsIndSynchronous.equalsIgnoreCase("true")){
            lsAsynTab = "1";
        }
        ApplicationModule         loAm = Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;        
        try{
            boolean lbValidate = false;
            Integer liRes = 0;
            if(lsNomService.equalsIgnoreCase("WsLogComercial")){
                lbValidate = true;
            }
            if(lsNomService.equalsIgnoreCase("WsVentaModulos")){
                lbValidate = true;
            }
            if(lbValidate){
                liRes = loService.validateExistByNomServicesCatModel(lsNomService);
            }
            if(liRes == 0){
                loService.insertServicesCatModel(liIdService,
                                                 lsNomService,
                                                 lsDescService,
                                                 lsIndServiceWsdl,
                                                 lsIndSystem,
                                                 lsIndOrigin,
                                                 lsIndDestiny,
                                                 lsStatusTab,
                                                 lsAsynTab
                                                );  
            }else{
                FacesMessage loMsg;
                loMsg = new FacesMessage("El Servicio Expuesto Solo Puede Existir Una Vez");
                loMsg.setSeverity(FacesMessage.SEVERITY_WARN);
                FacesContext.getCurrentInstance().addMessage(null, loMsg);
            }
        } catch (Exception loEx) {
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx);
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
        }
        resetMinuteTab();resetHoursTab();resetDaysTab();resetWeeksTab();resetMonthsTab();
        resetDisableMinuteTab(true);resetDisableHoursTab(true);
        resetDisableDaysTab(true);resetDisableWeeksTab(true);resetDisableMonthsTab(true);
        resetDisableAllTab(true);
        resetDisableTabButtons(true);
        
        String lsIndPeriodicity = 
            getPoIdTabSelected().getValue() == null ? "MINUTOS" : 
            getPoIdTabSelected().getValue().toString();                   
        getPoIdTabSelected().setValue(lsIndPeriodicity);
        getPoIdServiceSelected().setValue(liIdService);
        getPoOperation().setValue("INSERT");   
        getPoServiceToCron().setText(null);
        getPoServiceToCron().setVisible(false);
        getPoServiceToCronStatus().setText(null);
        getPoServiceToCronStatus().setVisible(false);
        setDisclosedFirstTab(getPoTabMinutos(),getPoPanelTabbed());
        new UtilFaces().refreshTableWhereIterator("1 = 1",
                                                  gsEntityIterator,
                                                  getPoTblWebServices()
                                                  );
        new UtilFaces().hidePopup(getPoPopupSaveService());
        return null;
    }
        
    /**
     * Muesta valores para configurar cron por servicio seleccionado
     * @autor Jorge Luis Bautista Santiago
     * @param tsStrSearch
     * @return String
     */
    public String showEditPopupServiceAction() {
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblWebServices().getSelectedRowData();
        String                   lsIdService = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();
        String                   lsNomService = 
            loNode.getAttribute("NomService") == null ? "" : 
            loNode.getAttribute("NomService").toString();
        String                   lsIndServiceWsdl = 
            loNode.getAttribute("IndServiceWsdl") == null ? "" : 
            loNode.getAttribute("IndServiceWsdl").toString();
        String                   lsIndSystem = 
            loNode.getAttribute("IndSystem") == null ? "" : 
            loNode.getAttribute("IndSystem").toString();
        String                   lsIndOrigin = 
            loNode.getAttribute("IndOrigin") == null ? "" : 
            loNode.getAttribute("IndOrigin").toString();
        String                   lsIndDestiny = 
            loNode.getAttribute("IndDestiny") == null ? "" : 
            loNode.getAttribute("IndDestiny").toString();
        String                   lsIndSynchronous = 
            loNode.getAttribute("IndSynchronous") == null ? "" : 
            loNode.getAttribute("IndSynchronous").toString();
        String                   lsIndEstatus = 
            loNode.getAttribute("IndEstatus") == null ? "" : 
            loNode.getAttribute("IndEstatus").toString();

        getPoPopUpdateIdService().setValue(lsIdService);        
        getPoPopUpdateNomService().setValue(lsNomService);
        getPoPopUpdateWsdl().setValue(lsIndServiceWsdl);
        getPoPopUpdateSystem().setValue(lsIndSystem);
        getPoPopUpdateSystemOr().setValue(lsIndOrigin);
        getPoPopUpdateSystemDes().setValue(lsIndDestiny);
                
        if(lsIndSynchronous.equalsIgnoreCase("1")){
            getPoPopUpdateAsyn().setSelected(true);
        }else{
            getPoPopUpdateAsyn().setSelected(false);
        }
        if(lsIndEstatus.equalsIgnoreCase("1")){
            getPoPopUpdateStatus().setSelected(true);
        }else{
            getPoPopUpdateStatus().setSelected(false);
        }
        
        new UtilFaces().showPopup(getPoPopupUpdateService());        

        return null;
    }

    /**
     * Muestra popup para confirmar ejecucion de servicio seleccionado
     * @autor Jorge Luis Bautista Santiago
     * @param toActionEvent
     * @return void
     */
    public void showExecutePopupService(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblWebServices().getSelectedRowData();
        String                   lsIdService = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();        
        String                   lsDesService = 
            loNode.getAttribute("IndDescService") == null ? "" : 
            loNode.getAttribute("IndDescService").toString();
        String                   lsNomService = 
            loNode.getAttribute("NomService") == null ? "" : 
            loNode.getAttribute("NomService").toString();
        
        String                   lsEstatus = 
            loNode.getAttribute("IndEstatus") == null ? "" : 
            loNode.getAttribute("IndEstatus").toString();
        
        if(lsEstatus.equalsIgnoreCase("1")){
            getPoExecuteIdBinding().setValue(lsIdService);
            getPoExecuteNomBinding().setValue(lsNomService);
            getPoExecuteMsgLbl().setLabel("Desea Ejcutar el servicio " + lsDesService + " - " + lsNomService + "?");
            getPoPopPanelExeBinding().setTitle("Ejecutar Servicio Web");
            getPoPopPanelExeBinding().setTitleIconSource("/images/siguientePage.png");
            getPoActionServiceBinding().setValue("EXECUTE");
            new UtilFaces().showPopup(getPoPopupExecuteService());
        }else{
            FacesMessage loMsg =
                new FacesMessage("El Servicio se encuentra Inactivo");
            loMsg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        }
    }

    /**
     * Ejecuta la accion seleccionada, ejecutar, iniciar cron o detener cron
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String executeWebServiceAction() {
        String              lsFinalMessage = "";
        String              lsColorMessage = "blue";
        String              lsGeneralAction = "EXECUTE";
        Integer             liIdUser = null;
        String              lsUserName = null;
        String              lsAmDef =
            "com.televisa.integration.model.AppModuleIntergrationImpl";
        String              lsConfig = "AppModuleIntergrationLocal";
        ApplicationModule   loAm =
            Configuration.createRootApplicationModule(lsAmDef, lsConfig);
        AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
        try {
            liIdUser = 
                loService.getValueSessionFromAttribute("loggedIntegrationIdUser") == null ? null :
                Integer.parseInt(loService.getValueSessionFromAttribute("loggedIntegrationIdUser"));
            lsUserName = 
                loService.getValueSessionFromAttribute("loggedIntegrationUser") == null ? null :
                loService.getValueSessionFromAttribute("loggedIntegrationUser").toString();
            String lsIdService = 
                getPoExecuteIdBinding().getValue() == null ? null : 
                getPoExecuteIdBinding().getValue().toString();                                          
            String lsServiceToInvoke = 
                getPoExecuteNomBinding().getValue() == null ? null : 
                getPoExecuteNomBinding().getValue().toString();
            String lsServiceAction = 
                getPoActionServiceBinding().getValue() == null ? null : 
                getPoActionServiceBinding().getValue().toString();   
            String lsIdTrigger = lsIdService + "-" + lsServiceToInvoke;
            System.out.println("********** lsIdTrigger["+lsIdTrigger+"] ==> ["+lsServiceAction+"]*********");
            lsGeneralAction = lsServiceAction;
            if(lsServiceToInvoke != null){
                /*################ PROGRAMAS ############################*/
                if(lsServiceToInvoke.equalsIgnoreCase("WsProgramas")){
                    ExecuteServiceResponseBean loRes =
                        processServiceExecution(liIdUser,
                                                lsUserName,
                                                lsIdService,
                                                lsServiceToInvoke,
                                                lsServiceAction,
                                                lsIdTrigger,
                                                loService
                                               );
                    lsColorMessage = loRes.getLsColor();
                    lsFinalMessage = loRes.getLsMessage();
                    
                }
                /*################ PARRILLAS ############################*/
                if(lsServiceToInvoke.equalsIgnoreCase("WsBreaks")){
                    ExecuteServiceResponseBean loRes =
                        processServiceParrillasExecution(liIdUser,
                                                        lsUserName,
                                                        lsIdService,
                                                        lsServiceToInvoke,
                                                        lsServiceAction,
                                                        lsIdTrigger,
                                                        loService
                                                       );
                    lsColorMessage = loRes.getLsColor();
                    lsFinalMessage = loRes.getLsMessage();
                }
                /*################ LOG CERTIFICADO########################*/
                if(lsServiceToInvoke.equalsIgnoreCase("WsLogCertificado")){
                    ExecuteServiceResponseBean loRes =
                        processServiceLogCertExecution(liIdUser,
                                                       lsUserName,
                                                       lsIdService,
                                                       lsServiceToInvoke,
                                                       lsServiceAction,
                                                       lsIdTrigger,
                                                       loService
                                                      );
                    lsColorMessage = loRes.getLsColor();
                    lsFinalMessage = loRes.getLsMessage();
                }
                /*################ VENTA TRADICIONAL ########################*/
                if(lsServiceToInvoke.equalsIgnoreCase("WsVentaTradicional")){
                    if(proccessExecuting() && !lsServiceAction.equalsIgnoreCase("EXECUTE")){
                        lsColorMessage = "Red";
                        lsFinalMessage = "Existen Procesos en Ejecucion, Intente nuevamente";
                    }else{
                        ExecuteServiceResponseBean loRes =
                            processServiceVtaTradicionalExecution(liIdUser,
                                                                  lsUserName,
                                                                  lsIdService,
                                                                  lsServiceToInvoke,
                                                                  lsServiceAction,
                                                                  lsIdTrigger,
                                                                  loService
                                                                 );
                        lsColorMessage = loRes.getLsColor();
                        lsFinalMessage = loRes.getLsMessage();
                    }
                }
                
                /*################ PARRILLAS BAJO DEMANDA ############################*/
                if(lsServiceToInvoke.equalsIgnoreCase("WsBreaksOnDemand")){
                    ExecuteServiceResponseBean loRes =
                        processServiceParrillasOnDemExecution(liIdUser,
                                                        lsUserName,
                                                        lsIdService,
                                                        lsServiceToInvoke,
                                                        lsServiceAction,
                                                        lsIdTrigger,
                                                        loService
                                                       );
                    lsColorMessage = loRes.getLsColor();
                    lsFinalMessage = loRes.getLsMessage();
                }
                
                /*################ VENTA TRADICIONAL POR USUARIO ##############*/
                if(lsServiceToInvoke.equalsIgnoreCase("WsVentaTradicionalUsr")){
                    //Validar si existen procesos en Ejecucion
                    if(proccessExecuting()){
                        lsColorMessage = "Red";
                        lsFinalMessage = "Existen Procesos en Ejecucion, Intente nuevamente";
                    }else{
                        ExecuteServiceResponseBean loRes =
                            processServiceVtaTradicionalUsrExecution(liIdUser,
                                                                  lsUserName,
                                                                  lsIdService,
                                                                  lsServiceToInvoke,
                                                                  lsServiceAction,
                                                                  lsIdTrigger,
                                                                  loService
                                                                 );
                        lsColorMessage = loRes.getLsColor();
                        lsFinalMessage = loRes.getLsMessage();
                    }
                } 
                
                /*################ TARGETS ############################*/
                if(lsServiceToInvoke.equalsIgnoreCase("WsTargets")){
                    System.out.println("Se ha invocado Targets");
                    ExecuteServiceResponseBean loRes =
                        processServiceTargetsExecution(liIdUser,
                                                lsUserName,
                                                lsIdService,
                                                lsServiceToInvoke,
                                                lsServiceAction,
                                                lsIdTrigger,
                                                loService
                                               );
                    lsColorMessage = loRes.getLsColor();
                    lsFinalMessage = loRes.getLsMessage();
                    
                }
                
            }                        
        } catch (Exception loEx) {
            lsFinalMessage = loEx.getMessage();
            lsColorMessage = "red";
            System.out.println("Err 5874 "+loEx.getMessage());
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
            loAm.remove();            
            if(!lsGeneralAction.equalsIgnoreCase("EXECUTE")){
                System.out.println("Actualizando tabla principal");
                refreshMainTable();
            }
            
        }
        new UtilFaces().hidePopup(getPoPopupExecuteService());
        StringBuilder loMessage = new StringBuilder("<html><body>");
        loMessage.append("<p style='color:" + lsColorMessage + "'><b>" + lsFinalMessage + "</i></b></p>");
        loMessage.append("</body></html>");        
        FacesMessage loMsg = 
            new FacesMessage(loMessage.toString());     
        loMsg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext loFctx = FacesContext.getCurrentInstance();
        loFctx.addMessage(null, loMsg);
        
        return null;
    }
    
    /**
     * Ejecuta la logica de ejecucion de servicio para Programas
     * @autor Jorge Luis Bautista Santiago
     * @param piIdUser
     * @param psUserName
     * @param psIdService
     * @param psServiceToInvoke
     * @param psServiceAction
     * @param psIdTrigger
     * @param poService
     * @return ExecuteServiceResponseBean
     */
    public ExecuteServiceResponseBean processServiceExecution(Integer piIdUser,
                                                              String psUserName,
                                                              String psIdService,
                                                              String psServiceToInvoke,
                                                              String psServiceAction,
                                                              String psIdTrigger,
                                                              AppModuleIntergrationImpl poService
                                                              ){
        ExecuteServiceResponseBean         loRes = new ExecuteServiceResponseBean();
        String                             lsFinalMessage = "";
        String                             lsColorMessage = "red";
        Integer                            liIdParameter = 
            new ViewObjectDao().getMaxIdParadigm("RstProgramas") + 1;
        String                             lsIdRequestPgr = String.valueOf(liIdParameter);
        List<EvetvIntServicesParamTabBean> laList = 
            poService.getParametersServices(Integer.parseInt(psIdService)); 
        // FECHA//
        String                             lsDateQuery = null;
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("FECHA")){
                lsDateQuery = loParm.getIndValParameter();
            }                       
        }
        //CANALES//
        List<String>                       laChannels = new ArrayList<String>();
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("CANAL")){
                laChannels.add(loParm.getIndValParameter());
            }                       
        }
        if(lsDateQuery != null && laChannels.size() > 0){
            if(psServiceAction.equalsIgnoreCase("EXECUTE")){
                lsFinalMessage = 
                    "El Servicio de " + psServiceToInvoke + " ha sido ejecutado en segundo plano";
                lsColorMessage = "black";
                boolean lbPrExe = true;
                EvetvIntCronConfigTabRowBean loRowCron = 
                    poService.getRowCronConfigByServiceModel(Integer.parseInt(psIdService));
                if(loRowCron != null){
                    if(loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        lbPrExe = false;
                    }
                }
                if(lbPrExe){
                    new UtilFaces().updateStatusCronService(Integer.parseInt(psIdService),
                                                            "1",
                                                            null,
                                                            null,
                                                            null
                                                            );
                    
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("Execute");
                    Integer liNumPgmProcessID = liIdParameter;
                    Integer liNumEvtbProcessId = 0;
                    
                    new UtilFaces().insertBitacoraServiceService(0,
                                                      Integer.parseInt(psIdService), 
                                                      liIndProcess, 
                                                      "Ejecucion Manual de Servicio de Programas",
                                                      liNumEvtbProcessId, 
                                                      liNumPgmProcessID, 
                                                      piIdUser,
                                                      psUserName);
            
                    Scheduler loScheduler;
                    try {
                        loScheduler = new StdSchedulerFactory().getScheduler();
                        JobDetail loJob = 
                            JobBuilder.newJob(ExecuteProgramasCron.class).build();
                        Trigger   loTrigger = 
                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).build();
                        JobDataMap loJobDataMap=  loJob.getJobDataMap();
                        loJobDataMap.put("lsIdService", psIdService); 
                        loJobDataMap.put("liIdUser", String.valueOf(piIdUser)); 
                        loJobDataMap.put("lsUserName", psUserName); 
                        loJobDataMap.put("lsIdRequestPgr", lsIdRequestPgr); 
                        loJobDataMap.put("lsTypeRequest", "normal");
                        loScheduler.scheduleJob(loJob, loTrigger);
                        loScheduler.start();
                        
                    } catch (Exception loEx) {
                        
                        lsFinalMessage = loEx.getMessage();
                        lsColorMessage = "red";
                    } 
                }else{
                    lsFinalMessage = "ATENCION: Actualmente Existe en Ejecucion";
                    lsColorMessage = "red";
                }
            }
            
            if(psServiceAction.equalsIgnoreCase("BEGIN")){
                // Verificar si ya se encuentra en ejecucion
                
                EvetvIntCronConfigTabRowBean loRowCron = 
                    poService.getRowCronConfigByServiceModel(Integer.parseInt(psIdService));
                if(loRowCron != null){           
                    // Verificar si puede iniciar de acuerdo al estatus
                    if(!loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        Scheduler loScheduler;
                        try {
                            boolean lbSimple = false;
                            Trigger loTrigger = null;
                            String lsCronExpression = 
                                poService.getCronExpressionModel(Integer.parseInt(psIdService));
                            if(lsCronExpression != null){
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteProgramasCron.class).build();
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loRowCron.getIndBeginSchedule() == null ? "08:00" : 
                                        loRowCron.getIndBeginSchedule();
                                    String lsFin = 
                                        loRowCron.getAttribute1() == null ? "23:50" : 
                                        loRowCron.getAttribute1();
                                    String lsEvery =  
                                        loRowCron.getIndValTypeSchedule() == null ? "23" : 
                                        loRowCron.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    
                                    if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                    
                                }
                                
                                
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", psIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(piIdUser)); 
                                loJobDataMap.put("lsUserName", psUserName); 
                                loJobDataMap.put("lsIdRequestPgr", lsIdRequestPgr);      
                                loJobDataMap.put("lsTypeRequest", "normal");
                                loScheduler.scheduleJob(loJob, loTrigger);
                                Integer piIndProcess = 
                                    new UtilFaces().getIdConfigParameterByName("BeginCron");
                                Integer piNumPgmProcessID = 0;
                                Integer piNumEvtbProcessId = 0;
                                new UtilFaces().insertBitacoraServiceService(0,
                                                                  Integer.parseInt(psIdService), 
                                                                  piIndProcess, 
                                                                  "Inicio Programado del Servicio " +
                                    "de Programas",
                                                                  piNumEvtbProcessId, 
                                                                  piNumPgmProcessID, 
                                                                  piIdUser,
                                                                  psUserName);
                                
                                new UtilFaces().updateStatusCronService(Integer.parseInt(psIdService),
                                                                        "2",
                                                                        null,
                                                                        null,
                                                                        "exe"
                                                                        );
                                
                                loScheduler.start();
                                lsFinalMessage = 
                                    "El Servicio de " + psServiceToInvoke + 
                                    " ha sido Iniciado en segundo plano";
                                lsColorMessage = "black";
                                
                            }else{
                                lsFinalMessage = 
                                    "No Existe Configuracion Cron para " + psServiceToInvoke + " ";
                                lsColorMessage = "red";
                            }
                        } catch (Exception loEx) {
                            
                            lsFinalMessage = loEx.getMessage();
                            lsColorMessage = "red";
                        } 
                    }else{
                        lsFinalMessage = "ATENCION: Actualmente Existe en Ejecucion";
                        lsColorMessage = "red";
                    }
                }else{
                    lsFinalMessage = "ATENCION: No Existe Configuracion Para Iniciar Cron";
                    lsColorMessage = "red";
                }
            }
                                    
        }else{
            lsFinalMessage = "ATENCION: Insuficientes Parametros de Ejecucion";
            lsColorMessage = "red";
        }
        
        /*###### Detener Cron del Servicio de Programas #####*/
        if(psServiceAction.equalsIgnoreCase("STOP")){
            String    lsNewCronExpression = "0 0 1 1/1 * ? *"; 
            Scheduler loScheduler;
            try {                        
                loScheduler = new StdSchedulerFactory().getScheduler();
                Trigger loTrigger = 
                    TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                       CronScheduleBuilder.cronSchedule(lsNewCronExpression)).startNow().build();
                loScheduler.rescheduleJob(new TriggerKey(psIdTrigger), loTrigger);
                loScheduler.deleteJob(loTrigger.getJobKey());
                lsFinalMessage = "El Servicio de " + psServiceToInvoke + " ha sido Detenido";
                lsColorMessage = "black";
                Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("StopCron");
                Integer liNumPgmProcessID = 0;
                Integer liNumEvtbProcessId = 0;
                new UtilFaces().insertBitacoraServiceService(0,
                                                  Integer.parseInt(psIdService), 
                                                  liIndProcess, 
                                                  "Inicio Programado del Servicio de Programas",
                                                  liNumEvtbProcessId, 
                                                  liNumPgmProcessID, 
                                                  piIdUser,
                                                  psUserName);
                new UtilFaces().updateStatusCronService(Integer.parseInt(psIdService),
                                                        "3",
                                                        null,
                                                        null,
                                                        null
                                                        );
            } catch (SchedulerException loEx) {
                System.out.println("SchedulerException: " + loEx.getMessage());
                lsColorMessage = "red";
            } 
        }
        loRes.setLsColor(lsColorMessage);
        loRes.setLsMessage(lsFinalMessage);
        return loRes;
    }
    
    /**
     * Ejecuta la logica de ejecucion de servicio para Parrillas
     * @autor Jorge Luis Bautista Santiago
     * @param tiIdUser
     * @param tsUserName
     * @param tsIdService
     * @param tsServiceToInvoke
     * @param tsServiceAction
     * @param tsIdTrigger
     * @param toService
     * @return ExecuteServiceResponseBean
     */
    public ExecuteServiceResponseBean processServiceParrillasExecution(Integer tiIdUser,
                                                                       String tsUserName,
                                                                       String tsIdService,
                                                                       String tsServiceToInvoke,
                                                                       String tsServiceAction,
                                                                       String tsIdTrigger,
                                                                       AppModuleIntergrationImpl toService
                                                                      ){
        ExecuteServiceResponseBean         loRes = new ExecuteServiceResponseBean();
        String                             lsFinalMessage = "";
        String                             lsColorMessage = "";
        //System.out.println("Invocar Servicio de Breaks - Cortes");
        //Obtener Parametros
        Integer liIdParrillas = 
            new ViewObjectDao().getMaxIdParadigm("RstParrillas") + 1;
        //System.out.println("######### liIdParrillas obtenido de MAX: "+liIdParrillas);
        String lsIdRequestPrr = String.valueOf(liIdParrillas);
        List<EvetvIntServicesParamTabBean> laList = 
            toService.getParametersServices(Integer.parseInt(tsIdService)); 
        // FECHA//
        String lsDateQuery = null;
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("FECHA")){
                lsDateQuery = loParm.getIndValParameter();
            }                       
        }
        //CANALES//
        List<String> laChannels = new ArrayList<String>();
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("CANAL")){
                laChannels.add(loParm.getIndValParameter());
            }                       
        }
        if(laChannels.size() > 0){ //
            if(tsServiceAction.equalsIgnoreCase("EXECUTE")){
                lsFinalMessage = 
                    "El Servicio de " + tsServiceToInvoke + " ha sido ejecutado en segundo plano";
                lsColorMessage = "black";
                
                boolean lbPrExe = true;
                EvetvIntCronConfigTabRowBean loRowCron = 
                    toService.getRowCronConfigByServiceModel(Integer.parseInt(tsIdService));
                if(loRowCron != null){
                    if(loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        lbPrExe = false;
                    }
                }
                if(lbPrExe){
                    new UtilFaces().updateStatusCronService(Integer.parseInt(tsIdService),
                                                            "1",
                                                            null,
                                                            null,
                                                            null
                                                            );
                    Integer piIndProcess = new UtilFaces().getIdConfigParameterByName("Execute");
                    Integer piNumPgmProcessID = liIdParrillas;
                    Integer piNumEvtbProcessId = 0;
                    
                    new UtilFaces().insertBitacoraServiceService(0,
                                                      Integer.parseInt(tsIdService), 
                                                      piIndProcess, 
                                                      " Ejecucion Manual de Servicio de Parrillas",
                                                      piNumEvtbProcessId, 
                                                      piNumPgmProcessID, 
                                                      tiIdUser,
                                                      tsUserName);
                
                    Scheduler loScheduler;
                    try {
                        loScheduler = new StdSchedulerFactory().getScheduler();
                        JobDetail loJob = 
                            JobBuilder.newJob(ExecuteParrillasCron.class).build();
                        Trigger   loTrigger = 
                            TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).build();
                        JobDataMap loJobDataMap=  loJob.getJobDataMap();
                        loJobDataMap.put("lsIdService", tsIdService); 
                        loJobDataMap.put("liIdUser", String.valueOf(tiIdUser)); 
                        loJobDataMap.put("lsUserName", tsUserName); 
                        loJobDataMap.put("lsIdRequestPrr", lsIdRequestPrr); 
                        loJobDataMap.put("lsTypeRequest", "normal");
                        loScheduler.scheduleJob(loJob, loTrigger);                        
                        loScheduler.start();
                        
                    } catch (Exception loEx) {
                        lsFinalMessage = loEx.getMessage();
                        lsColorMessage = "red";
                    } 
                }
            }
            if(tsServiceAction.equalsIgnoreCase("BEGIN")){
                // Verificar si ya se encuentra en ejecucion
                EvetvIntCronConfigTabRowBean loRowCron = 
                    toService.getRowCronConfigByServiceModel(Integer.parseInt(tsIdService));
                if(loRowCron != null){           
                    // Verificar si puede iniciar de acuerdo al estatus
                    if(!loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        Scheduler loScheduler;
                        try {
                            boolean lbSimple = false;
                            Trigger loTrigger = null;
                            String lsCronExpression = 
                                toService.getCronExpressionModel(Integer.parseInt(tsIdService));
                            if(lsCronExpression != null){
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteParrillasCron.class).build();
                                
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loRowCron.getIndBeginSchedule() == null ? "08:00" : 
                                        loRowCron.getIndBeginSchedule();
                                    String lsFin = 
                                        loRowCron.getAttribute1() == null ? "23:50" : 
                                        loRowCron.getAttribute1();
                                    String lsEvery =  
                                        loRowCron.getIndValTypeSchedule() == null ? "23" : 
                                        loRowCron.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    
                                    if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                    
                                }
                                
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", tsIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(tiIdUser)); 
                                loJobDataMap.put("lsUserName", tsUserName); 
                                loJobDataMap.put("lsIdRequestPgr", lsIdRequestPrr);     
                                loJobDataMap.put("lsTypeRequest", "normal");
                                loScheduler.scheduleJob(loJob, loTrigger);
                                Integer piIndProcess = 
                                    new UtilFaces().getIdConfigParameterByName("BeginCron");
                                Integer piNumPgmProcessID = liIdParrillas;
                                Integer piNumEvtbProcessId = 0;
                                new UtilFaces().insertBitacoraServiceService(0,
                                                                  Integer.parseInt(tsIdService), 
                                                                  piIndProcess, 
                                                                  "Inicio Programado del Servicio " +
                                    "de Parrillas-Breaks",
                                                                  piNumEvtbProcessId, 
                                                                  piNumPgmProcessID, 
                                                                  tiIdUser,
                                                                  tsUserName);
                                
                                new UtilFaces().updateStatusCronService(Integer.parseInt(tsIdService),
                                                                        "2",
                                                                        null,
                                                                        null,
                                                                        "exe"
                                                                        );
                                
                                loScheduler.start();
                                lsFinalMessage = 
                                    "El Servicio de " + tsServiceToInvoke + 
                                    " ha sido Iniciado en segundo plano";
                                lsColorMessage = "black";
                                
                            }else{
                                lsFinalMessage = 
                                    "No Existe Configuracion Cron para " + tsServiceToInvoke + " ";
                                lsColorMessage = "red";
                            }
                        } catch (Exception loEx) {
                            
                            lsFinalMessage = loEx.getMessage();
                            lsColorMessage = "red";
                        } 
                    }else{
                        lsFinalMessage = "ATENCION: Actualmente Existe en Ejecucion";
                        lsColorMessage = "red";
                    }
                }else{
                    lsFinalMessage = "ATENCION: No Existe Configuracion Para Iniciar Cron";
                    lsColorMessage = "red";
                }
            }
        }else{
            lsFinalMessage = "ATENCION: Insuficientes Parametros de Ejecucion";
            lsColorMessage = "red";
        }
        
        /*###### Detener Cron del Servicio de Parrillas #####*/
        if(tsServiceAction.equalsIgnoreCase("STOP")){
            String    lsNewCronExpression = "0 0 1 1/1 * ? *"; // the desired cron expression
            Scheduler loScheduler;
            try {                        
                loScheduler = new StdSchedulerFactory().getScheduler();
                Trigger loTrigger = 
                    TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                       CronScheduleBuilder.cronSchedule(lsNewCronExpression)).startNow().build();
                loScheduler.rescheduleJob(new TriggerKey(tsIdTrigger), loTrigger);
                loScheduler.deleteJob(loTrigger.getJobKey());
                lsFinalMessage = "El Servicio de " + tsServiceToInvoke + " ha sido Detenido";
                lsColorMessage = "black";
                Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("StopCron");
                Integer liNumPgmProcessID = liIdParrillas;
                Integer liNumEvtbProcessId = 0;
                new UtilFaces().insertBitacoraServiceService(0,
                                                  Integer.parseInt(tsIdService), 
                                                  liIndProcess, 
                                                  "Servicio de Parrillas Programado se ha detenido",
                                                  liNumEvtbProcessId, 
                                                  liNumPgmProcessID, 
                                                  tiIdUser,
                                                  tsUserName);
                new UtilFaces().updateStatusCronService(Integer.parseInt(tsIdService),
                                                        "3",
                                                        null,
                                                        null,
                                                        null
                                                        );
            } catch (SchedulerException loEx) {
                System.out.println("SchedulerException: " + loEx.getMessage());
                lsColorMessage = "red";
            } 
        }
        loRes.setLsColor(lsColorMessage);
        loRes.setLsMessage(lsFinalMessage);
        return loRes;
    }

    /**
     * Ejecuta la logica de ejecucion de servicio para Parrillas
     * @autor Jorge Luis Bautista Santiago
     * @param tiIdUser
     * @param tsUserName
     * @param tsIdService
     * @param tsServiceToInvoke
     * @param tsServiceAction
     * @param tsIdTrigger
     * @param toService
     * @return ExecuteServiceResponseBean
     */
    public ExecuteServiceResponseBean processServiceParrillasOnDemExecution(Integer tiIdUser,
                                                                           String tsUserName,
                                                                           String tsIdService,
                                                                           String tsServiceToInvoke,
                                                                           String tsServiceAction,
                                                                           String tsIdTrigger,
                                                                           AppModuleIntergrationImpl toService
                                                                          ){
        ExecuteServiceResponseBean         loRes = new ExecuteServiceResponseBean();
        String                             lsFinalMessage = "";
        String                             lsColorMessage = "";
        //System.out.println("Invocar Servicio de Breaks - Cortes");
        //Obtener Parametros
        Integer liIdParrillas = 
            new ViewObjectDao().getMaxIdParadigm("RstParrillas") + 1;
        //System.out.println("######### liIdParrillas obtenido de MAX: "+liIdParrillas);
        String lsIdRequestPrr = String.valueOf(liIdParrillas);
        List<EvetvIntServicesParamTabBean> laList = 
            toService.getParametersServices(Integer.parseInt(tsIdService)); 
        // FECHA INICIO //
        String lsDateIni = null;
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("FECHA INICIO")){
                lsDateIni = loParm.getIndValParameter();
            }                       
        }
        // FECHA FIN //
        String lsDateFin = null;
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("FECHA FIN")){
                lsDateFin = loParm.getIndValParameter();
            }                       
        }
        // BUYUNIT  //
        String lsBuyunitid = null;
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("BUYUNIT")){
                lsBuyunitid = loParm.getIndValParameter();
            }                       
        }
        //CANALES//
        List<String> laChannels = new ArrayList<String>();
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("CANAL")){
                laChannels.add(loParm.getIndValParameter());
            }                       
        }
        
        boolean lbProcessCron = true;        
        if(!(laChannels.size() > 0)){
            lbProcessCron = false;
        }
        if(lsDateIni != null && lsDateFin == null){
            lbProcessCron = false;
        }
        if(lsDateIni == null && lsDateFin != null){
            lbProcessCron = false;
        }
                
        if(lbProcessCron){
            if(tsServiceAction.equalsIgnoreCase("EXECUTE")){
                lsFinalMessage = 
                    "El Servicio de " + tsServiceToInvoke + " ha sido ejecutado en segundo plano";
                lsColorMessage = "black";
                
                boolean lbPrExe = true;
                EvetvIntCronConfigTabRowBean loRowCron = 
                    toService.getRowCronConfigByServiceModel(Integer.parseInt(tsIdService));
                if(loRowCron != null){
                    if(loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        lbPrExe = false;
                    }
                }
                if(lbPrExe){
                    new UtilFaces().updateStatusCronService(Integer.parseInt(tsIdService),
                                                            "1",
                                                            null,
                                                            null,
                                                            null
                                                            );
                    Integer piIndProcess = new UtilFaces().getIdConfigParameterByName("Execute");
                    Integer piNumPgmProcessID = liIdParrillas;
                    Integer piNumEvtbProcessId = 0;
                    
                    new UtilFaces().insertBitacoraServiceService(0,
                                                      Integer.parseInt(tsIdService), 
                                                      piIndProcess, 
                                                      " Ejecucion Manual de Servicio de Parrillas",
                                                      piNumEvtbProcessId, 
                                                      piNumPgmProcessID, 
                                                      tiIdUser,
                                                      tsUserName);
                
                    Scheduler loScheduler;
                    try {
                        loScheduler = new StdSchedulerFactory().getScheduler();
                        JobDetail loJob = 
                            JobBuilder.newJob(ExecuteParrillasOnDemandCron.class).build();
                        Trigger   loTrigger = 
                            TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).build();
                        JobDataMap loJobDataMap=  loJob.getJobDataMap();
                        loJobDataMap.put("lsIdService", tsIdService); 
                        loJobDataMap.put("liIdUser", String.valueOf(tiIdUser)); 
                        loJobDataMap.put("lsUserName", tsUserName); 
                        loJobDataMap.put("lsIdRequestPrr", lsIdRequestPrr); 
                        loJobDataMap.put("lsTypeRequest", "normal");
                        loScheduler.scheduleJob(loJob, loTrigger);                        
                        loScheduler.start();
                        
                    } catch (Exception loEx) {
                        lsFinalMessage = loEx.getMessage();
                        lsColorMessage = "red";
                    } 
                }
            }
            if(tsServiceAction.equalsIgnoreCase("BEGIN")){
                // Verificar si ya se encuentra en ejecucion
                EvetvIntCronConfigTabRowBean loRowCron = 
                    toService.getRowCronConfigByServiceModel(Integer.parseInt(tsIdService));
                if(loRowCron != null){           
                    // Verificar si puede iniciar de acuerdo al estatus
                    if(!loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        Scheduler loScheduler;
                        try {
                            boolean lbSimple = false;
                            Trigger loTrigger = null;
                            String lsCronExpression = 
                                toService.getCronExpressionModel(Integer.parseInt(tsIdService));
                            if(lsCronExpression != null){
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteParrillasCron.class).build();
                                
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loRowCron.getIndBeginSchedule() == null ? "08:00" : 
                                        loRowCron.getIndBeginSchedule();
                                    String lsFin = 
                                        loRowCron.getAttribute1() == null ? "23:50" : 
                                        loRowCron.getAttribute1();
                                    String lsEvery =  
                                        loRowCron.getIndValTypeSchedule() == null ? "23" : 
                                        loRowCron.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    
                                    if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                    
                                }
                                
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", tsIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(tiIdUser)); 
                                loJobDataMap.put("lsUserName", tsUserName); 
                                loJobDataMap.put("lsIdRequestPgr", lsIdRequestPrr);     
                                loJobDataMap.put("lsTypeRequest", "normal");
                                loScheduler.scheduleJob(loJob, loTrigger);
                                Integer piIndProcess = 
                                    new UtilFaces().getIdConfigParameterByName("BeginCron");
                                Integer piNumPgmProcessID = liIdParrillas;
                                Integer piNumEvtbProcessId = 0;
                                new UtilFaces().insertBitacoraServiceService(0,
                                                                  Integer.parseInt(tsIdService), 
                                                                  piIndProcess, 
                                                                  "Inicio Programado del Servicio " +
                                    "de Parrillas-Breaks",
                                                                  piNumEvtbProcessId, 
                                                                  piNumPgmProcessID, 
                                                                  tiIdUser,
                                                                  tsUserName);
                                
                                new UtilFaces().updateStatusCronService(Integer.parseInt(tsIdService),
                                                                        "2",
                                                                        null,
                                                                        null,
                                                                        "exe"
                                                                        );
                                
                                loScheduler.start();
                                lsFinalMessage = 
                                    "El Servicio de " + tsServiceToInvoke + 
                                    " ha sido Iniciado en segundo plano";
                                lsColorMessage = "black";
                                
                            }else{
                                lsFinalMessage = 
                                    "No Existe Configuracion Cron para " + tsServiceToInvoke + " ";
                                lsColorMessage = "red";
                            }
                        } catch (Exception loEx) {
                            
                            lsFinalMessage = loEx.getMessage();
                            lsColorMessage = "red";
                        } 
                    }else{
                        lsFinalMessage = "ATENCION: Actualmente Existe en Ejecucion";
                        lsColorMessage = "red";
                    }
                }else{
                    lsFinalMessage = "ATENCION: No Existe Configuracion Para Iniciar Cron";
                    lsColorMessage = "red";
                }
            }
        }else{
            lsFinalMessage = "ATENCION: Insuficientes Parametros de Ejecucion";
            lsColorMessage = "red";
        }
        
        /*###### Detener Cron del Servicio de Parrillas #####*/
        if(tsServiceAction.equalsIgnoreCase("STOP")){
            String    lsNewCronExpression = "0 0 1 1/1 * ? *"; // the desired cron expression
            Scheduler loScheduler;
            try {                        
                loScheduler = new StdSchedulerFactory().getScheduler();
                Trigger loTrigger = 
                    TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                       CronScheduleBuilder.cronSchedule(lsNewCronExpression)).startNow().build();
                loScheduler.rescheduleJob(new TriggerKey(tsIdTrigger), loTrigger);
                loScheduler.deleteJob(loTrigger.getJobKey());
                lsFinalMessage = "El Servicio de " + tsServiceToInvoke + " ha sido Detenido";
                lsColorMessage = "black";
                Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("StopCron");
                Integer liNumPgmProcessID = liIdParrillas;
                Integer liNumEvtbProcessId = 0;
                new UtilFaces().insertBitacoraServiceService(0,
                                                  Integer.parseInt(tsIdService), 
                                                  liIndProcess, 
                                                  "Servicio de Parrillas Programado se ha detenido",
                                                  liNumEvtbProcessId, 
                                                  liNumPgmProcessID, 
                                                  tiIdUser,
                                                  tsUserName);
                new UtilFaces().updateStatusCronService(Integer.parseInt(tsIdService),
                                                        "3",
                                                        null,
                                                        null,
                                                        null
                                                        );
            } catch (SchedulerException loEx) {
                System.out.println("SchedulerException: " + loEx.getMessage());
                lsColorMessage = "red";
            } 
        }
        loRes.setLsColor(lsColorMessage);
        loRes.setLsMessage(lsFinalMessage);
        return loRes;
    }

    /**
     * Ejecuta la logica de ejecucion de servicio para Log Certificado
     * @autor Jorge Luis Bautista Santiago
     * @param piIdUser
     * @param psUserName
     * @param psIdService
     * @param psServiceToInvoke
     * @param psServiceAction
     * @param psIdTrigger
     * @param poService
     * @return ExecuteServiceResponseBean
     */
    public ExecuteServiceResponseBean processServiceLogCertExecution(Integer piIdUser,
                                                                     String psUserName,
                                                                     String psIdService,
                                                                     String psServiceToInvoke,
                                                                     String psServiceAction,
                                                                     String psIdTrigger,
                                                                     AppModuleIntergrationImpl poService
                                                                     ){
        ExecuteServiceResponseBean         loRes = new ExecuteServiceResponseBean();
        String                             lsFinalMessage = "";
        String                             lsColorMessage = "";
        Integer liIdLogCert = 
            new ViewObjectDao().getMaxIdParadigm("RstLogCertificado") + 1;
        String lsIdRequestPrr = String.valueOf(liIdLogCert);
        List<EvetvIntServicesParamTabBean> laList = 
            poService.getParametersServices(Integer.parseInt(psIdService)); 
        // FECHA//
        String lsDateQuery = null;
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("FECHA")){
                lsDateQuery = loParm.getIndValParameter();
            }                       
        }
        //CANALES//
        List<String> laChannels = new ArrayList<String>();
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("CANAL")){
                laChannels.add(loParm.getIndValParameter());
            }                       
        }
        if(lsDateQuery != null && laChannels.size() > 0){
            if(psServiceAction.equalsIgnoreCase("EXECUTE")){
                lsFinalMessage = 
                    "El Servicio de " + psServiceToInvoke + " ha sido ejecutado en segundo plano";
                lsColorMessage = "black";
                
                boolean lbPrExe = true;
                EvetvIntCronConfigTabRowBean loRowCron = 
                    poService.getRowCronConfigByServiceModel(Integer.parseInt(psIdService));
                if(loRowCron != null){
                    if(loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        lbPrExe = false;
                    }
                }
                if(lbPrExe){
                    new UtilFaces().updateStatusCronService(Integer.parseInt(psIdService),
                                                            "1",
                                                            null,
                                                            null,
                                                            null
                                                            );
                    Integer piIndProcess = new UtilFaces().getIdConfigParameterByName("Execute");
                    Integer piNumPgmProcessID = liIdLogCert;
                    Integer piNumEvtbProcessId = 0;
                    
                    new UtilFaces().insertBitacoraServiceService(0,
                                                      Integer.parseInt(psIdService), 
                                                      piIndProcess, 
                                                      "Ejecucion Manual de Servicio de Log Certificado",
                                                      piNumEvtbProcessId, 
                                                      piNumPgmProcessID, 
                                                      piIdUser,
                                                      psUserName);
                
                    Scheduler loScheduler;
                    try {
                        loScheduler = new StdSchedulerFactory().getScheduler();
                        JobDetail loJob = 
                            JobBuilder.newJob(ExecuteLogCertificadoCron.class).build();
                        Trigger   loTrigger = 
                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).build();                                
                        JobDataMap loJobDataMap=  loJob.getJobDataMap();
                        
                        loJobDataMap.put("lsIdService", psIdService); 
                        loJobDataMap.put("liIdUser", String.valueOf(piIdUser)); 
                        loJobDataMap.put("lsUserName", psUserName); 
                        loJobDataMap.put("lsIdRequestPgr", lsIdRequestPrr); 
                        loJobDataMap.put("lsTypeRequest", "normal");
                        loScheduler.scheduleJob(loJob, loTrigger);                        
                        loScheduler.start();
                        
                    } catch (Exception loEx) {
                        System.out.println("Error al inicializar tareas " + loEx.getMessage());
                        lsFinalMessage = loEx.getMessage();
                        lsColorMessage = "red";
                    }    
                }else{
                    lsFinalMessage = "ATENCION: Actualmente Existe en Ejecucion";
                    lsColorMessage = "red";
                }
            }
            if(psServiceAction.equalsIgnoreCase("BEGIN")){
                // Verificar si ya se encuentra en ejecucion
                EvetvIntCronConfigTabRowBean loRowCron = 
                    poService.getRowCronConfigByServiceModel(Integer.parseInt(psIdService));
                if(loRowCron != null){           
                    // Verificar si puede iniciar de acuerdo al estatus
                    if(!loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        Scheduler loScheduler;
                        try {
                            boolean lbSimple = false;
                            Trigger loTrigger = null;
                            String lsCronExpression = 
                                poService.getCronExpressionModel(Integer.parseInt(psIdService));
                            if(lsCronExpression != null){
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteLogCertificadoCron.class).build();
                                
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loRowCron.getIndBeginSchedule() == null ? "08:00" : 
                                        loRowCron.getIndBeginSchedule();
                                    String lsFin = 
                                        loRowCron.getAttribute1() == null ? "23:50" : 
                                        loRowCron.getAttribute1();
                                    String lsEvery =  
                                        loRowCron.getIndValTypeSchedule() == null ? "23" : 
                                        loRowCron.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    
                                    if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                    
                                }
                                
                                
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", psIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(piIdUser)); 
                                loJobDataMap.put("lsUserName", psUserName); 
                                loJobDataMap.put("lsIdRequestPgr", lsIdRequestPrr);                                             
                                loJobDataMap.put("lsTypeRequest", "normal");
                                loScheduler.scheduleJob(loJob, loTrigger);
                                Integer piIndProcess = 
                                    new UtilFaces().getIdConfigParameterByName("BeginCron");
                                Integer piNumPgmProcessID = liIdLogCert;
                                Integer piNumEvtbProcessId = 0;
                                new UtilFaces().insertBitacoraServiceService(0,
                                                                  Integer.parseInt(psIdService), 
                                                                  piIndProcess, 
                                                                  "Inicio Programado del Servicio " +
                                    "de Log Certificado",
                                                                  piNumEvtbProcessId, 
                                                                  piNumPgmProcessID, 
                                                                  piIdUser,
                                                                  psUserName);
                                
                                new UtilFaces().updateStatusCronService(Integer.parseInt(psIdService),
                                                                        "2",
                                                                        null,
                                                                        null,
                                                                        "exe"
                                                                        );
                                
                                loScheduler.start();
                                lsFinalMessage = 
                                    "El Servicio de " + psServiceToInvoke + 
                                    " ha sido Iniciado en segundo plano";
                                lsColorMessage = "black";
                                
                            }else{
                                lsFinalMessage = 
                                    "No Existe Configuracion Cron para " + psServiceToInvoke + " ";
                                lsColorMessage = "red";
                            }
                        } catch (Exception loEx) {
                            System.out.println("Error al inicializar tareas " + loEx.getMessage());
                            lsFinalMessage = loEx.getMessage();
                            lsColorMessage = "red";
                        } 
                    }else{
                        lsFinalMessage = "ATENCION: Actualmente Existe en Ejecucion";
                        lsColorMessage = "red";
                    }
                }else{
                    lsFinalMessage = "ATENCION: No Existe Configuracion Para Iniciar Cron";
                    lsColorMessage = "red";
                }
            }
        }else{
            lsFinalMessage = "ATENCION: Insuficientes Parametros de Ejecucion";
            lsColorMessage = "red";
        }
        
        /*###### Detener Cron del Servicio de Parrillas #####*/
        if(psServiceAction.equalsIgnoreCase("STOP")){
            String    lsNewCronExpression = "0 0 1 1/1 * ? *"; // the desired cron expression
            Scheduler loScheduler;
            try {                        
                loScheduler = new StdSchedulerFactory().getScheduler();
                Trigger loTrigger = 
                    TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                       CronScheduleBuilder.cronSchedule(lsNewCronExpression)).startNow().build();
                loScheduler.rescheduleJob(new TriggerKey(psIdTrigger), loTrigger);
                loScheduler.deleteJob(loTrigger.getJobKey());
                lsFinalMessage = "El Servicio de " + psServiceToInvoke + " ha sido Detenido";
                lsColorMessage = "black";
                Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("StopCron");
                Integer liNumPgmProcessID = liIdLogCert;
                Integer liNumEvtbProcessId = 0;
                new UtilFaces().insertBitacoraServiceService(0,
                                                  Integer.parseInt(psIdService), 
                                                  liIndProcess, 
                                                  "Servicio de Log Certificado se ha detenido",
                                                  liNumEvtbProcessId, 
                                                  liNumPgmProcessID, 
                                                  piIdUser,
                                                  psUserName);
                new UtilFaces().updateStatusCronService(Integer.parseInt(psIdService),
                                                        "3",
                                                        null,
                                                        null,
                                                        null
                                                        );
            } catch (SchedulerException loEx) {
                System.out.println("SchedulerException: " + loEx.getMessage());
                lsColorMessage = "red";
            } 
        }
        loRes.setLsColor(lsColorMessage);
        loRes.setLsMessage(lsFinalMessage);
        return loRes;
    }

    /**
     * Ejecuta la logica de ejecucion de servicio para Venta Tradicional
     * @autor Jorge Luis Bautista Santiago
     * @param tiIdUser
     * @param tsUserName
     * @param tsIdService
     * @param tsServiceToInvoke
     * @param tsServiceAction
     * @param tsIdTrigger
     * @param toService
     * @return ExecuteServiceResponseBean
     */
    public ExecuteServiceResponseBean processServiceVtaTradicionalExecution(Integer tiIdUser,
                                                                     String tsUserName,
                                                                     String tsIdService,
                                                                     String tsServiceToInvoke,
                                                                     String tsServiceAction,
                                                                     String tsIdTrigger,
                                                                     AppModuleIntergrationImpl toService
                                                                     ){
        ExecuteServiceResponseBean         loRes = new ExecuteServiceResponseBean();
        String                             lsFinalMessage = "";
        String                             lsColorMessage = "";
        String                             lsStatusType = "PN"; //Proceso Nocturno
        boolean                            lbFlagCi = false;
        Integer                            liIdLogCert = 
            new ViewObjectDao().getMaxIdParadigm("RstVtaTradicional") + 1;
        String lsIdRequestPrr = String.valueOf(liIdLogCert);
        List<EvetvIntServicesParamTabBean> laList = 
            toService.getParametersServices(Integer.parseInt(tsIdService)); 
        // Validar en Parametros del servicio, si se trata de carga inicial//
        
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("CARGA_INICIAL")){
                String lsValParameter = loParm.getIndValParameter();
                if(lsValParameter != null){
                    if(!lsValParameter.equalsIgnoreCase("NO")){
                        lbFlagCi = true;
                        lsStatusType = "CI";//Carga Inicial
                    }                    
                }
            }                       
        }
        
        // Obtener Configuracion Cron
        EvetvIntCronConfigTabRowBean loRowCron = 
            toService.getRowCronConfigByServiceModel(Integer.parseInt(tsIdService));
        
        if(!lbFlagCi){
            lsStatusType = "PN";
            String lsDeadLine = null;
            if(loRowCron != null){
                lsDeadLine = loRowCron.getAttribute1() == null ? "" : loRowCron.getAttribute1();
                if(lsDeadLine.length() > 1){
                    boolean lbFlagType = new UtilFaces().isCurrentHourValid(lsDeadLine);
                    if(lbFlagType){ //La hora actual es menor o igual a hora deadline
                        lsStatusType = "SC";//Semana Por Confirmar
                    }
                }
            }
        }
        
        //CANALES//
        List<String> laChannels = new ArrayList<String>();
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("CANAL")){
                laChannels.add(loParm.getIndValParameter());
            }                       
        }
        if(laChannels.size() > 0){
            if(tsServiceAction.equalsIgnoreCase("EXECUTE")){
                lsFinalMessage = 
                    "El Servicio de " + tsServiceToInvoke + " ha sido ejecutado en segundo plano";
                lsColorMessage = "black";
                
                
                boolean lbPrExe = true;
                
                if(loRowCron != null){
                    if(loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        lbPrExe = false;
                    }
                }
                if(lbPrExe){
                    new UtilFaces().updateStatusCronService(Integer.parseInt(tsIdService),
                                                            "1",
                                                            null,
                                                            null,
                                                            null
                                                            );
                    Integer piIndProcess = new UtilFaces().getIdConfigParameterByName("Execute");
                    Integer piNumPgmProcessID = liIdLogCert;
                    Integer piNumEvtbProcessId = 0;
                    
                    new UtilFaces().insertBitacoraServiceService(0,
                                                      Integer.parseInt(tsIdService), 
                                                      piIndProcess, 
                                            "Ejecucion Manual de Servicio de Venta Tradicional[" + lsStatusType + "]",
                                                      piNumEvtbProcessId, 
                                                      piNumPgmProcessID, 
                                                      tiIdUser,
                                                      tsUserName);
                
                    Scheduler loScheduler;
                    try {
                        loScheduler = new StdSchedulerFactory().getScheduler();
                        JobDetail loJob = 
                            JobBuilder.newJob(ExecuteVtaTradicionalCron.class).build();
                        Trigger   loTrigger = 
                            TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).build();                                
                        JobDataMap loJobDataMap=  loJob.getJobDataMap();
                        
                        loJobDataMap.put("lsIdService", tsIdService); 
                        loJobDataMap.put("liIdUser", String.valueOf(tiIdUser)); 
                        loJobDataMap.put("lsUserName", tsUserName); 
                        loJobDataMap.put("lsIdRequestPgr", lsIdRequestPrr); 
                        loJobDataMap.put("lsStatusType", lsStatusType); 
                        loJobDataMap.put("lsIdRequestOnDemand", "-1"); 
                        loJobDataMap.put("lsTypeRequest", "normal");
                        loScheduler.scheduleJob(loJob, loTrigger);
                        loScheduler.start();
                        
                    } catch (Exception loEx) {
                        System.out.println("Error al inicializar tareas " + loEx.getMessage());
                        lsFinalMessage = loEx.getMessage();
                        lsColorMessage = "red";
                    }     
                }else{
                    lsFinalMessage = "ATENCION: Actualmente Existe en Ejecucion";
                    lsColorMessage = "red";
                }
            }
            if(tsServiceAction.equalsIgnoreCase("BEGIN")){
                // Verificar si ya se encuentra en ejecucion
                if(loRowCron != null){           
                    // Verificar si puede iniciar de acuerdo al estatus
                    if(!loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        Scheduler loScheduler;
                        try {
                            boolean lbSimple = false;
                            Trigger loTrigger = null;
                            String lsCronExpression = 
                                toService.getCronExpressionModel(Integer.parseInt(tsIdService));
                            if(lsCronExpression != null){
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteVtaTradicionalCron.class).build();
                                
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loRowCron.getIndBeginSchedule() == null ? "08:00" : 
                                        loRowCron.getIndBeginSchedule();
                                    String lsFin = 
                                        loRowCron.getAttribute1() == null ? "23:50" : 
                                        loRowCron.getAttribute1();
                                    String lsEvery =  
                                        loRowCron.getIndValTypeSchedule() == null ? "23" : 
                                        loRowCron.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    
                                    if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                    
                                }
                                
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", tsIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(tiIdUser)); 
                                loJobDataMap.put("lsUserName", tsUserName); 
                                loJobDataMap.put("lsIdRequest", lsIdRequestPrr);   
                                loJobDataMap.put("lsTypeRequest", "normal");
                                loScheduler.scheduleJob(loJob, loTrigger);
                                Integer piIndProcess = 
                                    new UtilFaces().getIdConfigParameterByName("BeginCron");
                                Integer piNumPgmProcessID = liIdLogCert;
                                Integer piNumEvtbProcessId = 0;
                                new UtilFaces().insertBitacoraServiceService(0,
                                                                  Integer.parseInt(tsIdService), 
                                                                  piIndProcess, 
                                                                  "Inicio Programado del Servicio " +
                                    "de Venta Tradicional",
                                                                  piNumEvtbProcessId, 
                                                                  piNumPgmProcessID, 
                                                                  tiIdUser,
                                                                  tsUserName);
                                
                                new UtilFaces().updateStatusCronService(Integer.parseInt(tsIdService),
                                                                        "2",
                                                                        null,
                                                                        null,
                                                                        "exe"
                                                                        );
                                
                                loScheduler.start();
                                lsFinalMessage = 
                                    "El Servicio de " + tsServiceToInvoke + 
                                    " ha sido Iniciado en segundo plano";
                                lsColorMessage = "black";
                                
                            }else{
                                lsFinalMessage = 
                                    "No Existe Configuracion Cron para " + tsServiceToInvoke + " ";
                                lsColorMessage = "red";
                            }
                        } catch (Exception loEx) {
                            System.out.println("Error al inicializar tareas " + loEx.getMessage());
                            lsFinalMessage = loEx.getMessage();
                            lsColorMessage = "red";
                        } 
                    }else{
                        lsFinalMessage = "ATENCION: Actualmente Existe en Ejecucion";
                        lsColorMessage = "red";
                    }
                }else{
                    lsFinalMessage = "ATENCION: No Existe Configuracion Para Iniciar Cron";
                    lsColorMessage = "red";
                }
            }
        }else{
            lsFinalMessage = "ATENCION: Insuficientes Parametros de Ejecucion";
            lsColorMessage = "red";
        }
        
        /*###### Detener Cron del Servicio de Parrillas #####*/
        if(tsServiceAction.equalsIgnoreCase("STOP")){
            String    lsNewCronExpression = "0 0 1 1/1 * ? *"; // the desired cron expression
            Scheduler loScheduler;
            try {                        
                loScheduler = new StdSchedulerFactory().getScheduler();
                Trigger loTrigger = 
                    TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                       CronScheduleBuilder.cronSchedule(lsNewCronExpression)).startNow().build();
                loScheduler.rescheduleJob(new TriggerKey(tsIdTrigger), loTrigger);
                loScheduler.deleteJob(loTrigger.getJobKey());
                lsFinalMessage = "El Servicio de " + tsServiceToInvoke + " ha sido Detenido";
                lsColorMessage = "black";
                Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("StopCron");
                Integer liNumPgmProcessID = liIdLogCert;
                Integer liNumEvtbProcessId = 0;
                new UtilFaces().insertBitacoraServiceService(0,
                                                  Integer.parseInt(tsIdService), 
                                                  liIndProcess, 
                                                  "Servicio de Venta Tradicional se ha detenido",
                                                  liNumEvtbProcessId, 
                                                  liNumPgmProcessID, 
                                                  tiIdUser,
                                                  tsUserName);
                new UtilFaces().updateStatusCronService(Integer.parseInt(tsIdService),
                                                        "3",
                                                        null,
                                                        null,
                                                        null
                                                        );
            } catch (SchedulerException loEx) {
                System.out.println("SchedulerException: " + loEx.getMessage());
                lsColorMessage = "red";
            } 
        }
        loRes.setLsColor(lsColorMessage);
        loRes.setLsMessage(lsFinalMessage);
        return loRes;
    }
    
    /**
     * Ejecuta la logica de ejecucion de servicio para Venta Tradicional por Usuario
     * @autor Jorge Luis Bautista Santiago
     * @param tiIdUser
     * @param tsUserName
     * @param tsIdService
     * @param tsServiceToInvoke
     * @param tsServiceAction
     * @param tsIdTrigger
     * @param toService
     * @return ExecuteServiceResponseBean
     */
    public ExecuteServiceResponseBean processServiceVtaTradicionalUsrExecution(Integer tiIdUser,
                                                                     String tsUserName,
                                                                     String tsIdService,
                                                                     String tsServiceToInvoke,
                                                                     String tsServiceAction,
                                                                     String tsIdTrigger,
                                                                     AppModuleIntergrationImpl toService
                                                                     ){
        ExecuteServiceResponseBean         loRes = new ExecuteServiceResponseBean();
        String                             lsFinalMessage = "";
        String                             lsColorMessage = "";
        String                             lsStatusType = "PN"; //Proceso Nocturno
        boolean                            lbFlagCi = false;
        Integer                            liIdLogCert = 
            new ViewObjectDao().getMaxIdParadigm("RstVtaTradicional") + 1;
        String lsIdRequestPrr = String.valueOf(liIdLogCert);
        List<EvetvIntServicesParamTabBean> laList = 
            toService.getParametersServices(Integer.parseInt(tsIdService)); 
        // Validar en Parametros del servicio, si se trata de carga inicial//
        
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("CARGA_INICIAL")){
                String lsValParameter = loParm.getIndValParameter();
                if(lsValParameter != null){
                    if(!lsValParameter.equalsIgnoreCase("NO")){
                        lbFlagCi = true;
                        lsStatusType = "CI";//Carga Inicial
                    }                    
                }
            }                       
        }
        
        // Obtener Configuracion Cron
        EvetvIntCronConfigTabRowBean loRowCron = 
            toService.getRowCronConfigByServiceModel(Integer.parseInt(tsIdService));
        
        if(!lbFlagCi){
            lsStatusType = "PN";
            String lsDeadLine = null;
            if(loRowCron != null){
                lsDeadLine = loRowCron.getAttribute1() == null ? "" : loRowCron.getAttribute1();
                if(lsDeadLine.length() > 1){
                    boolean lbFlagType = new UtilFaces().isCurrentHourValid(lsDeadLine);
                    if(lbFlagType){ //La hora actual es menor o igual a hora deadline
                        lsStatusType = "SC";//Semana Por Confirmar
                    }
                }
            }
        }
        
        //CANALES//
        List<String> laChannels = new ArrayList<String>();
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("CANAL")){
                laChannels.add(loParm.getIndValParameter());
            }                       
        }
        if(laChannels.size() > 0){
            if(tsServiceAction.equalsIgnoreCase("EXECUTE")){
                lsFinalMessage = 
                    "El Servicio de " + tsServiceToInvoke + " ha sido ejecutado en segundo plano";
                lsColorMessage = "black";
                                
                boolean lbPrExe = true;
                
                if(loRowCron != null){
                    if(loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        lbPrExe = false;
                    }
                }
                if(lbPrExe){
                    new UtilFaces().updateStatusCronService(Integer.parseInt(tsIdService),
                                                            "1",
                                                            null,
                                                            null,
                                                            null
                                                            );
                    Integer piIndProcess = new UtilFaces().getIdConfigParameterByName("Execute");
                    Integer piNumPgmProcessID = liIdLogCert;
                    Integer piNumEvtbProcessId = 0;
                    
                    new UtilFaces().insertBitacoraServiceService(0,
                                                      Integer.parseInt(tsIdService), 
                                                      piIndProcess, 
                                            "Ejecucion Manual de Servicio de Venta Tradicional Por Usuario[" + lsStatusType + "]",
                                                      piNumEvtbProcessId, 
                                                      piNumPgmProcessID, 
                                                      tiIdUser,
                                                      tsUserName);
                    //Agregar Ciclo de espera con un Thread
                    boolean lbFlagExe = true;
                    int liI = 0;
                    do{
                        if(proccessExecuting()){
                            liI ++;
                            try{
                                Thread.sleep(6000);
                            }catch(Exception loExp){
                                System.out.println("Error en sleep: " +
                                                   loExp.getMessage());    
                            }
                        }else{
                            lbFlagExe = false;
                        }
                    }while(lbFlagExe && liI < 10);
                    
                    Scheduler loScheduler;
                    try {
                        loScheduler = new StdSchedulerFactory().getScheduler();
                        JobDetail loJob = 
                            JobBuilder.newJob(ExecuteVtaTradicionalUsrCron.class).build();
                        Trigger   loTrigger = 
                            TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).build();                                
                        JobDataMap loJobDataMap=  loJob.getJobDataMap();
                        
                        loJobDataMap.put("lsIdService", tsIdService); 
                        loJobDataMap.put("liIdUser", String.valueOf(tiIdUser)); 
                        loJobDataMap.put("lsUserName", tsUserName); 
                        loJobDataMap.put("lsIdRequestPgr", lsIdRequestPrr); 
                        loJobDataMap.put("lsIdRequestOnDemand", lsIdRequestPrr); 
                        loJobDataMap.put("lsStatusType", lsStatusType); 
                        loJobDataMap.put("lsTypeRequest", "normal");
                        loScheduler.scheduleJob(loJob, loTrigger);
                        loScheduler.start();
                        
                    } catch (Exception loEx) {
                        System.out.println("Error al inicializar tareas " + loEx.getMessage());
                        lsFinalMessage = loEx.getMessage();
                        lsColorMessage = "red";
                    }     
                }else{
                    lsFinalMessage = "ATENCION: Actualmente Existe en Ejecucion";
                    lsColorMessage = "red";
                }
            }
            if(tsServiceAction.equalsIgnoreCase("BEGIN")){
                // Verificar si ya se encuentra en ejecucion
                if(loRowCron != null){           
                    // Verificar si puede iniciar de acuerdo al estatus
                    if(!loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        Scheduler loScheduler;
                        try {
                            boolean lbSimple = false;
                            Trigger loTrigger = null;
                            String lsCronExpression = 
                                toService.getCronExpressionModel(Integer.parseInt(tsIdService));
                            if(lsCronExpression != null){
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteVtaTradicionalUsrCron.class).build();
                                
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loRowCron.getIndBeginSchedule() == null ? "08:00" : 
                                        loRowCron.getIndBeginSchedule();
                                    String lsFin = 
                                        loRowCron.getAttribute1() == null ? "23:50" : 
                                        loRowCron.getAttribute1();
                                    String lsEvery =  
                                        loRowCron.getIndValTypeSchedule() == null ? "23" : 
                                        loRowCron.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    
                                    if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                    
                                }
                                
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", tsIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(tiIdUser)); 
                                loJobDataMap.put("lsUserName", tsUserName); 
                                loJobDataMap.put("lsIdRequestPgr", lsIdRequestPrr);   
                                loJobDataMap.put("lsTypeRequest", "normal");
                                loScheduler.scheduleJob(loJob, loTrigger);
                                Integer piIndProcess = 
                                    new UtilFaces().getIdConfigParameterByName("BeginCron");
                                Integer piNumPgmProcessID = liIdLogCert;
                                Integer piNumEvtbProcessId = 0;
                                new UtilFaces().insertBitacoraServiceService(0,
                                                                  Integer.parseInt(tsIdService), 
                                                                  piIndProcess, 
                                                                  "Inicio Programado del Servicio " +
                                    "de Venta Tradicional Por Usuario",
                                                                  piNumEvtbProcessId, 
                                                                  piNumPgmProcessID, 
                                                                  tiIdUser,
                                                                  tsUserName);
                                
                                new UtilFaces().updateStatusCronService(Integer.parseInt(tsIdService),
                                                                        "2",
                                                                        null,
                                                                        null,
                                                                        "exe"
                                                                        );
                                
                                loScheduler.start();
                                lsFinalMessage = 
                                    "El Servicio de " + tsServiceToInvoke + 
                                    " ha sido Iniciado en segundo plano";
                                lsColorMessage = "black";
                                
                            }else{
                                lsFinalMessage = 
                                    "No Existe Configuracion Cron para " + tsServiceToInvoke + " ";
                                lsColorMessage = "red";
                            }
                        } catch (Exception loEx) {
                            System.out.println("Error al inicializar tareas " + loEx.getMessage());
                            lsFinalMessage = loEx.getMessage();
                            lsColorMessage = "red";
                        } 
                    }else{
                        lsFinalMessage = "ATENCION: Actualmente Existe en Ejecucion";
                        lsColorMessage = "red";
                    }
                }else{
                    lsFinalMessage = "ATENCION: No Existe Configuracion Para Iniciar Cron";
                    lsColorMessage = "red";
                }
            }
        }else{
            lsFinalMessage = "ATENCION: Insuficientes Parametros de Ejecucion";
            lsColorMessage = "red";
        }
        
        /*###### Detener Cron del Servicio de Parrillas #####*/
        if(tsServiceAction.equalsIgnoreCase("STOP")){
            String    lsNewCronExpression = "0 0 1 1/1 * ? *"; // the desired cron expression
            Scheduler loScheduler;
            try {                        
                loScheduler = new StdSchedulerFactory().getScheduler();
                Trigger loTrigger = 
                    TriggerBuilder.newTrigger().withIdentity(tsIdTrigger).withSchedule(
                       CronScheduleBuilder.cronSchedule(lsNewCronExpression)).startNow().build();
                loScheduler.rescheduleJob(new TriggerKey(tsIdTrigger), loTrigger);
                loScheduler.deleteJob(loTrigger.getJobKey());
                lsFinalMessage = "El Servicio de " + tsServiceToInvoke + " ha sido Detenido";
                lsColorMessage = "black";
                Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("StopCron");
                Integer liNumPgmProcessID = liIdLogCert;
                Integer liNumEvtbProcessId = 0;
                new UtilFaces().insertBitacoraServiceService(0,
                                                  Integer.parseInt(tsIdService), 
                                                  liIndProcess, 
                                                  "Servicio de Venta Tradicional Por Usuario se ha detenido",
                                                  liNumEvtbProcessId, 
                                                  liNumPgmProcessID, 
                                                  tiIdUser,
                                                  tsUserName);
                new UtilFaces().updateStatusCronService(Integer.parseInt(tsIdService),
                                                        "3",
                                                        null,
                                                        null,
                                                        null
                                                        );
            } catch (SchedulerException loEx) {
                System.out.println("SchedulerException: " + loEx.getMessage());
                lsColorMessage = "red";
            } 
        }
        loRes.setLsColor(lsColorMessage);
        loRes.setLsMessage(lsFinalMessage);
        return loRes;
    }

    
    /**
     * Ejecuta la logica de ejecucion de servicio para Targets
     * @autor Jorge Luis Bautista Santiago
     * @param piIdUser
     * @param psUserName
     * @param psIdService
     * @param psServiceToInvoke
     * @param psServiceAction
     * @param psIdTrigger
     * @param poService
     * @return ExecuteServiceResponseBean
     */
    public ExecuteServiceResponseBean processServiceTargetsExecution(Integer piIdUser,
                                                              String psUserName,
                                                              String psIdService,
                                                              String psServiceToInvoke,
                                                              String psServiceAction,
                                                              String psIdTrigger,
                                                              AppModuleIntergrationImpl poService
                                                              ){
        ExecuteServiceResponseBean         loRes = new ExecuteServiceResponseBean();
        String                             lsFinalMessage = "";
        String                             lsColorMessage = "red";
        Integer                            liIdParameter = 
            new ViewObjectDao().getMaxIdParadigm("RstTargets") + 1;
        String                             lsIdRequestTargets = String.valueOf(liIdParameter);
        
        //Obtener par�metros para este servicio, desde la base de datos -------
        List<EvetvIntServicesParamTabBean> laList = 
            poService.getParametersServices(Integer.parseInt(psIdService)); 
        // FECHA//
        String                             lsDateQuery = null;
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("FECHA")){
                lsDateQuery = loParm.getIndValParameter();
            }                       
        }
        //CANALES//
        List<String>                       laChannels = new ArrayList<String>();
        for(EvetvIntServicesParamTabBean loParm : laList){
            if(loParm.getIndParameter().equalsIgnoreCase("CANAL")){
                laChannels.add(loParm.getIndValParameter());
            }                       
        }
        //------------------------------------------------------------------------------
        if(lsDateQuery != null && laChannels.size() > 0){
            if(psServiceAction.equalsIgnoreCase("EXECUTE")){ //Ejecucion manual
                lsFinalMessage = 
                    "El Servicio de " + psServiceToInvoke + " ha sido ejecutado en segundo plano";
                lsColorMessage = "black";
                boolean lbPrExe = true;
                EvetvIntCronConfigTabRowBean loRowCron = 
                    poService.getRowCronConfigByServiceModel(Integer.parseInt(psIdService));
                if(loRowCron != null){
                    if(loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        lbPrExe = false;
                    }
                }
                if(lbPrExe){
                    new UtilFaces().updateStatusCronService(Integer.parseInt(psIdService),
                                                            "1",
                                                            null,
                                                            null,
                                                            null
                                                            );
                    
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("Execute");
                    Integer liNumPgmProcessID = liIdParameter;
                    Integer liNumEvtbProcessId = 0;
                    
                    new UtilFaces().insertBitacoraServiceService(0,
                                                      Integer.parseInt(psIdService), 
                                                      liIndProcess, 
                                                      "Ejecucion Manual de Servicio de Targets",
                                                      liNumEvtbProcessId, 
                                                      liNumPgmProcessID, 
                                                      piIdUser,
                                                      psUserName);
            
                    Scheduler loScheduler;
                    try {
                        loScheduler = new StdSchedulerFactory().getScheduler();
                        JobDetail loJob = 
                            JobBuilder.newJob(ExecuteTargetsCron.class).build();
                        Trigger   loTrigger = 
                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).build();
                        JobDataMap loJobDataMap=  loJob.getJobDataMap();
                        loJobDataMap.put("lsIdService", psIdService); 
                        loJobDataMap.put("liIdUser", String.valueOf(piIdUser)); 
                        loJobDataMap.put("lsUserName", psUserName); 
                        loJobDataMap.put("lsIdRequestTargets", lsIdRequestTargets); 
                        loJobDataMap.put("lsTypeRequest", "normal");
                        loScheduler.scheduleJob(loJob, loTrigger);
                        loScheduler.start();
                        
                    } catch (Exception loEx) {
                        
                        lsFinalMessage = loEx.getMessage();
                        lsColorMessage = "red";
                    } 
                }else{
                    lsFinalMessage = "ATENCION: Actualmente Existe en Ejecucion";
                    lsColorMessage = "red";
                }
            }
            
            if(psServiceAction.equalsIgnoreCase("BEGIN")){
                // Verificar si ya se encuentra en ejecucion
                
                EvetvIntCronConfigTabRowBean loRowCron = 
                    poService.getRowCronConfigByServiceModel(Integer.parseInt(psIdService));
                if(loRowCron != null){           
                    // Verificar si puede iniciar de acuerdo al estatus
                    if(!loRowCron.getIndEstatus().equalsIgnoreCase("2")){
                        Scheduler loScheduler;
                        try {
                            boolean lbSimple = false;
                            Trigger loTrigger = null;
                            String lsCronExpression = 
                                poService.getCronExpressionModel(Integer.parseInt(psIdService));
                            if(lsCronExpression != null){
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteTargetsCron.class).build();
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loRowCron.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loRowCron.getIndBeginSchedule() == null ? "08:00" : 
                                        loRowCron.getIndBeginSchedule();
                                    String lsFin = 
                                        loRowCron.getAttribute1() == null ? "23:50" : 
                                        loRowCron.getAttribute1();
                                    String lsEvery =  
                                        loRowCron.getIndValTypeSchedule() == null ? "23" : 
                                        loRowCron.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    
                                    if(loRowCron.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                    
                                }
                                                                
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", psIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(piIdUser)); 
                                loJobDataMap.put("lsUserName", psUserName); 
                                loJobDataMap.put("lsIdRequestTargets", lsIdRequestTargets); 
                                loJobDataMap.put("lsTypeRequest", "normal");
                                loScheduler.scheduleJob(loJob, loTrigger);
                                Integer piIndProcess = 
                                    new UtilFaces().getIdConfigParameterByName("BeginCron");
                                Integer piNumPgmProcessID = 0;
                                Integer piNumEvtbProcessId = 0;
                                new UtilFaces().insertBitacoraServiceService(0,
                                                                  Integer.parseInt(psIdService), 
                                                                  piIndProcess, 
                                                                  "Inicio Programado del Servicio " +
                                    "de Targets",
                                                                  piNumEvtbProcessId, 
                                                                  piNumPgmProcessID, 
                                                                  piIdUser,
                                                                  psUserName);
                                
                                new UtilFaces().updateStatusCronService(Integer.parseInt(psIdService),
                                                                        "2",
                                                                        null,
                                                                        null,
                                                                        "exe"
                                                                        );
                                
                                loScheduler.start();
                                lsFinalMessage = 
                                    "El Servicio de " + psServiceToInvoke + 
                                    " ha sido Iniciado en segundo plano";
                                lsColorMessage = "black";
                                
                            }else{
                                lsFinalMessage = 
                                    "No Existe Configuracion Cron para " + psServiceToInvoke + " ";
                                lsColorMessage = "red";
                            }
                        } catch (Exception loEx) {
                            
                            lsFinalMessage = loEx.getMessage();
                            lsColorMessage = "red";
                        } 
                    }else{
                        lsFinalMessage = "ATENCION: Actualmente Existe en Ejecucion";
                        lsColorMessage = "red";
                    }
                }else{
                    lsFinalMessage = "ATENCION: No Existe Configuracion Para Iniciar Cron";
                    lsColorMessage = "red";
                }
            }
                                    
        }else{
            lsFinalMessage = "ATENCION: Insuficientes Parametros de Ejecucion";
            lsColorMessage = "red";
        }
        
        /*###### Detener Cron del Servicio de Programas #####*/
        if(psServiceAction.equalsIgnoreCase("STOP")){
            String    lsNewCronExpression = "0 0 1 1/1 * ? *"; 
            Scheduler loScheduler;
            try {                        
                loScheduler = new StdSchedulerFactory().getScheduler();
                Trigger loTrigger = 
                    TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                       CronScheduleBuilder.cronSchedule(lsNewCronExpression)).startNow().build();
                loScheduler.rescheduleJob(new TriggerKey(psIdTrigger), loTrigger);
                loScheduler.deleteJob(loTrigger.getJobKey());
                lsFinalMessage = "El Servicio de " + psServiceToInvoke + " ha sido Detenido";
                lsColorMessage = "black";
                Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("StopCron");
                Integer liNumPgmProcessID = 0;
                Integer liNumEvtbProcessId = 0;
                new UtilFaces().insertBitacoraServiceService(0,
                                                  Integer.parseInt(psIdService), 
                                                  liIndProcess, 
                                                  "Inicio Programado del Servicio de Programas",
                                                  liNumEvtbProcessId, 
                                                  liNumPgmProcessID, 
                                                  piIdUser,
                                                  psUserName);
                new UtilFaces().updateStatusCronService(Integer.parseInt(psIdService),
                                                        "3",
                                                        null,
                                                        null,
                                                        null
                                                        );
            } catch (SchedulerException loEx) {
                System.out.println("SchedulerException: " + loEx.getMessage());
                lsColorMessage = "red";
            } 
        }
        loRes.setLsColor(lsColorMessage);
        loRes.setLsMessage(lsFinalMessage);
        return loRes;
    }
    
    
    /**
     * Cancela la ejecucion del servicio en cron
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String cancelExecuteWebService() {
        new UtilFaces().hidePopup(getPoPopupExecuteService());
        return null;
    }

    /**
     * Regresa un conjunto de servicios web 
     * @autor Jorge Luis Bautista Santiago
     * @param tsStrSearch
     * @return List
     */
    public List getListWebServicesValid() {
        List<SelectItem>        laList = 
            new ArrayList<SelectItem>();    
        ViewObjectDao           loMd = new ViewObjectDao();
        List<SelectOneItemBean> laAllWs = 
            loMd.getListGeneralParametersModelFilter("INTEGRATION_WSERVICES");
        for(SelectOneItemBean loWs: laAllWs){
            SelectItem loItm = new SelectItem();            
            loItm.setValue(loWs.getLsValue());
            loItm.setDescription(loWs.getLsValue());
            loItm.setLabel(loWs.getLsDescription());
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
    public List getListWebServicesValidSearch() {
        List<SelectItem>        laList = 
            new ArrayList<SelectItem>();    
        ViewObjectDao           loMd = new ViewObjectDao();
        List<SelectOneItemBean> laAllWs = 
            loMd.getListGeneralParametersModelFilter("INTEGRATION_WSERVICES");
        for(SelectOneItemBean loWs: laAllWs){
            SelectItem loItm = new SelectItem();            
            loItm.setValue(loWs.getLsValue());
            loItm.setDescription(loWs.getLsValue());
            loItm.setLabel(loWs.getLsDescription());
            laList.add(loItm);
        }
        return laList;
    }


    /**
     * Muestra popup para detener el servicio en cron
     * @autor Jorge Luis Bautista Santiago
     * @param toActionEvent
     * @return void
     */
    public void showStopPopupService(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblWebServices().getSelectedRowData();
        String                   lsIdService = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();
        String                   lsDesService = 
            loNode.getAttribute("IndDescService") == null ? "" : 
            loNode.getAttribute("IndDescService").toString();
        String                   lsNomService = 
            loNode.getAttribute("NomService") == null ? "" : 
            loNode.getAttribute("NomService").toString();
       
        getPoExecuteIdBinding().setValue(lsIdService);
        getPoExecuteNomBinding().setValue(lsNomService);
        getPoExecuteMsgLbl().setLabel("Desea Detener el Servicio " + lsDesService + "-" + lsNomService + "?");
        getPoPopPanelExeBinding().setTitle("Detener Servicio Web");
        getPoPopPanelExeBinding().setTitleIconSource("/images/page_deny.gif");
        getPoActionServiceBinding().setValue("STOP");
        new UtilFaces().showPopup(getPoPopupExecuteService());
        
    }
    
    /**
     * Muestra popup para iniciar cron de ejecucion del servicio
     * @autor Jorge Luis Bautista Santiago
     * @param toActionEvent
     * @return void
     */
    public void showBeginPopupService(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblWebServices().getSelectedRowData();
        String                   lsIdService = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();
        String                   lsDesService = 
            loNode.getAttribute("IndDescService") == null ? "" : 
            loNode.getAttribute("IndDescService").toString();
        String                   lsNomService = 
            loNode.getAttribute("NomService") == null ? "" : 
            loNode.getAttribute("NomService").toString();
        
        String                   lsEstatus = 
            loNode.getAttribute("IndEstatus") == null ? "" : 
            loNode.getAttribute("IndEstatus").toString();
        
        if(lsEstatus.equalsIgnoreCase("1")){      
            getPoExecuteIdBinding().setValue(lsIdService);
            getPoExecuteNomBinding().setValue(lsNomService);
            getPoExecuteMsgLbl().setLabel("Desea Iniciar Cron del Servicio " + lsDesService + "-" + lsNomService + "?");
            getPoPopPanelExeBinding().setTitle("Iniciar Cron Servicio Web");
            getPoPopPanelExeBinding().setTitleIconSource("/images/mnu_estatus_orden.gif");
            getPoActionServiceBinding().setValue("BEGIN");
            new UtilFaces().showPopup(getPoPopupExecuteService());
        }else{
            FacesMessage loMsg =
                new FacesMessage("El Servicio esta Inactivo");
            loMsg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        }
    }
    
    public String showPopupSaveConfirm() {
        boolean                   lbFlagProcess = true;
        ViewObjectDao             loService = new ViewObjectDao();
        Integer liIdService = getPoIdServiceSelected().getValue() == null ? 0 : 
            Integer.parseInt(getPoIdServiceSelected().getValue().toString());   
        EvetvIntCronConfigTabRowBean loCronConfig = 
            loService.getRowCronConfigByServiceModel(liIdService);
        if(loCronConfig != null){
            if(loCronConfig.getIndEstatus().equalsIgnoreCase("2")){
                lbFlagProcess = false;
            }
        }
        if(lbFlagProcess){
            saveConfigurationAction();
        }else{
            getPoSaveCronMsgLbl().setLabel("El Servicio se Encuentra en Ejecucion, " +
                "si se Modifica la Periodicidad, Primero Detenga el Cron, " +
                "de lo Contrario el Cambio Surtir� Efecto en el Reinicio de Servidor," +
                "Desea Continuar?");
            new UtilFaces().showPopup(getPoPopupSaveCronService());
        }
        return null;
    }
    
    public String saveCronAction() {
        saveConfigurationAction();
        return null;
    }

    public String cancelSaveCronAction() {
        new UtilFaces().hidePopup(getPoPopupSaveCronService());
        return null;
    }

    
    /**
     * Guarda en base de datos la configuracion realizada
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String saveConfigurationAction(){
        boolean   lbProcess = true;
        String    lsCronConfig = null;
        UtilFaces loUtilFaces = new UtilFaces();        
        Integer   liIdConfiguration = null;
        Integer   liIdService = null;
        String    lsIndPeriodicity = null;
        String    lsIndBeginSchedule = null;
        String    lsIndTypeSchedule = null;    
        String    lsIndValTypeSchedule = null;    
        String    lsIndHourDeadLine = null;
        String    lsIndMinuteDeadLine = null;
        
        String    lsIndHourBegin = null;
        String    lsIndMinuteBegin= null;
        
        Integer   liIndDayMonth = null;
        Integer   liIndWeekMonth = null;
        String    lsIndCronExpression   = null;         
        Integer   liIndMonday = null;
        Integer   liIndTuesday = null;
        Integer   liIndWednesday = null;
        Integer   liIndThursday = null;
        Integer   liIndFriday = null;
        Integer   liIndSaturday = null;
        Integer   liIndSunday = null;
        String    lsStatusTab = "1";

        liIdService = getPoIdServiceSelected().getValue() == null ? 0 : 
            Integer.parseInt(getPoIdServiceSelected().getValue().toString());      
        
        lsIndPeriodicity = 
            getPoIdTabSelected().getValue() == null ? "" : 
            getPoIdTabSelected().getValue().toString();                
        if(liIdService > 0){
            // ###################### MINUTOS ##########################
            if(lsIndPeriodicity.equalsIgnoreCase("MINUTOS")){
                //Obtener ID_CONFIGURATION                
                lsIndBeginSchedule = null;
                lsIndTypeSchedule = "EVERY";                
                lsIndValTypeSchedule = 
                    getPoCadaMinutos().getValue() == null ? null : 
                    getPoCadaMinutos().getValue().toString();                
                lsIndHourDeadLine = 
                    getPoDeadLineMinutos().getValue() == null ? null : 
                    getPoDeadLineMinutos().getValue().toString(); 
                lsIndMinuteDeadLine = 
                    getPoDeadLineMinMinutos().getValue() == null ? null : 
                    getPoDeadLineMinMinutos().getValue().toString(); 
                
                lsIndHourBegin = 
                    getPoBeginHorasMin().getValue() == null ? "" : 
                    getPoBeginHorasMin().getValue().toString(); 
                lsIndMinuteBegin = 
                    getPoBeginMinutesMin().getValue() == null ? "" : 
                    getPoBeginMinutesMin().getValue().toString(); 
                
                if(lsIndHourBegin.equalsIgnoreCase("")){
                    lsIndHourBegin = "23";
                }
                if(lsIndMinuteBegin.equalsIgnoreCase("")){
                    lsIndMinuteBegin = "59";
                }
                
                lsIndBeginSchedule = new UtilFaces().buildTimeCron(lsIndHourBegin, lsIndMinuteBegin);                
                
                if(lsIndValTypeSchedule != null){
                    lbProcess = true;
                    liIndDayMonth = null;
                    liIndWeekMonth = null;
                    lsCronConfig = 
                        loUtilFaces.getBuildUrlRestMinutes(lsIndValTypeSchedule);
                    lsIndCronExpression = "0 0/" + lsIndValTypeSchedule + " * 1/1 * ? *";
                        //new UtilFaces().getCronExpression(lsCronConfig); 
                    
                    String lsLunes = 
                        getPoLunMinutos().getValue() == null ? "" : 
                        getPoLunMinutos().getValue().toString();
                    if(lsLunes.equalsIgnoreCase("true")){liIndMonday = 1;}else{liIndMonday = 0;}
                    
                    String lsMartes = 
                        getPoMarMinutos().getValue() == null ? "" : 
                        getPoMarMinutos().getValue().toString();
                    if(lsMartes.equalsIgnoreCase("true")){liIndTuesday = 1;}else{liIndTuesday = 0;}
                    
                    String lsMiercoles = 
                        getPoMieMinutos().getValue() == null ? "" : 
                        getPoMieMinutos().getValue().toString();
                    if(lsMiercoles.equalsIgnoreCase("true")){liIndWednesday = 1;}else{liIndWednesday = 0;}
                    
                    String lsJueves = 
                        getPoJueMinutos().getValue() == null ? "" : 
                        getPoJueMinutos().getValue().toString();
                    if(lsJueves.equalsIgnoreCase("true")){liIndThursday = 1;}else{liIndThursday = 0;}
                    
                    String lsViernes = 
                        getPoVieMinutos().getValue() == null ? "" : 
                        getPoVieMinutos().getValue().toString();
                    if(lsViernes.equalsIgnoreCase("true")){liIndFriday = 1;}else{liIndFriday = 0;}
                    
                    String lsSabado = 
                        getPoSabMinutos().getValue() == null ? "" : 
                        getPoSabMinutos().getValue().toString();
                    if(lsSabado.equalsIgnoreCase("true")){liIndSaturday = 1;}else{liIndSaturday = 0;}
                    
                    String lsDomingo = 
                        getPoDomMinutos().getValue() == null ? "" :
                        getPoDomMinutos().getValue().toString();
                    if(lsDomingo.equalsIgnoreCase("true")){liIndSunday = 1;}else{liIndSunday = 0;}
                }else{
                    lbProcess = false;
                }
                resetHoursTab();
                resetDaysTab();                
                resetWeeksTab();
                resetMonthsTab();
                                
            }
            // ###################### HORAS ##########################
            if(lsIndPeriodicity.equalsIgnoreCase("HORAS")){
                //Obtener ID_CONFIGURATION                
                /*
                String lsRadioHourBegin = 
                    getPoRadioHourBegin().getValue() == null ? null : 
                    getPoRadioHourBegin().getValue().toString();
                String lsRadioHourEvery = 
                    getPoRadioHourEvery().getValue() == null ? null : 
                    getPoRadioHourEvery().getValue().toString();
                */
                
                String lsRadioHourBegin = "true";
                String lsRadioHourEvery = "false";
                
                lsIndHourDeadLine = 
                    getPoDeadLineHoras().getValue() == null ? null : 
                    getPoDeadLineHoras().getValue().toString(); 
                lsIndMinuteDeadLine = 
                    getPoDeadLineMinHoras().getValue() == null ? null : 
                    getPoDeadLineMinHoras().getValue().toString(); 
                
                if(lsRadioHourBegin == null && lsRadioHourEvery == null){
                    lbProcess = false;
                }else{
                    // Validar si es RadioBegin
                    String lsEveryValue = null;
                    String lsHours = null;
                    String lsMinutes = null;
                    if(lsRadioHourEvery.equalsIgnoreCase("true")){
                        lsIndTypeSchedule = "EVERY";                
                        lsIndValTypeSchedule = 
                            getPoCadaHoras().getValue() == null ? null : 
                            getPoCadaHoras().getValue().toString();
                        lsEveryValue = lsIndValTypeSchedule;
                        if(lsIndValTypeSchedule == null){
                            lbProcess = false;
                        }
                        lsHours = 
                            getPoBeginHoras().getValue() == null ? "" : 
                            getPoBeginHoras().getValue().toString();
                        lsMinutes = 
                            getPoBeginMinutes().getValue() == null ? "" : 
                            getPoBeginMinutes().getValue().toString();
                        if(lsHours.equalsIgnoreCase("")){
                            lsHours = "23";
                        }
                        if(lsMinutes.equalsIgnoreCase("")){
                            lsMinutes = "59";
                        }
                        lsIndBeginSchedule = new UtilFaces().buildTimeCron(lsHours, lsMinutes);
                    }                    
                    // Validar si es RadioBegin
                    
                    if(lsRadioHourBegin.equalsIgnoreCase("true")){
                        lsIndTypeSchedule = "AT";
                        
                        lsIndValTypeSchedule = 
                            getPoCadaHoras().getValue() == null ? null : 
                            getPoCadaHoras().getValue().toString();
                        lsEveryValue = lsIndValTypeSchedule;
                        
                        lsHours = 
                            getPoBeginHoras().getValue() == null ? "" : 
                            getPoBeginHoras().getValue().toString();
                        lsMinutes = 
                            getPoBeginMinutes().getValue() == null ? "" : 
                            getPoBeginMinutes().getValue().toString();
                        if(lsHours.equalsIgnoreCase("")){
                            lsHours = "23";
                        }
                        if(lsMinutes.equalsIgnoreCase("")){
                            lsMinutes = "59";
                        }
                        lsIndBeginSchedule = new UtilFaces().buildTimeCron(lsHours, lsMinutes);
                        if(lsIndBeginSchedule.equalsIgnoreCase("ERROR")){
                            lbProcess = false;
                        }
                    }                    
                    if(lbProcess){
                        liIndDayMonth = null;
                        liIndWeekMonth = null;
                        
                        lsCronConfig = loUtilFaces.getBuildUrlRestHours(lsRadioHourEvery,
                                                                               lsEveryValue,
                                                                               lsRadioHourBegin,
                                                                               lsHours,
                                                                               lsMinutes);
                        lsIndCronExpression = "0 0 " + lsIndValTypeSchedule + " 1/1 * ? *";
//                            new UtilFaces().getCronExpression(lsCronConfig); 
                        String lsLunes =
                            getPoLunHoras().getValue() == null ? "" : 
                            getPoLunHoras().getValue().toString();
                        if(lsLunes.equalsIgnoreCase("true")){liIndMonday = 1;}else{liIndMonday = 0;}
                        
                        String lsMartes = 
                            getPoMarHoras().getValue() == null ? "" :
                            getPoMarHoras().getValue().toString();
                        if(lsMartes.equalsIgnoreCase("true")){liIndTuesday = 1;}else{liIndTuesday = 0;}
                        
                        String lsMiercoles = 
                            getPoMieHoras().getValue() == null ? "" : 
                            getPoMieHoras().getValue().toString();
                        if(lsMiercoles.equalsIgnoreCase("true")){liIndWednesday = 1;}else{liIndWednesday = 0;}
                        
                        String lsJueves =
                            getPoJueHoras().getValue() == null ? "" :
                            getPoJueHoras().getValue().toString();
                        if(lsJueves.equalsIgnoreCase("true")){liIndThursday = 1;}else{liIndThursday = 0;}
                        
                        String lsViernes = 
                            getPoVieHoras().getValue() == null ? "" : 
                            getPoVieHoras().getValue().toString();
                        if(lsViernes.equalsIgnoreCase("true")){liIndFriday = 1;}else{liIndFriday = 0;}
                        
                        String lsSabado = 
                            getPoSabHoras().getValue() == null ? "" : 
                            getPoSabHoras().getValue().toString();
                        if(lsSabado.equalsIgnoreCase("true")){liIndSaturday = 1;}else{liIndSaturday = 0;}
                        
                        String lsDomingo = 
                            getPoDomHoras().getValue() == null ? "" : 
                            getPoDomHoras().getValue().toString();
                        if(lsDomingo.equalsIgnoreCase("true")){liIndSunday = 1;}else{liIndSunday = 0;}
                    }
                }
                resetMinuteTab();
                resetDaysTab();                
                resetWeeksTab();
                resetMonthsTab();
            }            
            // ###################### DIAS ##########################
            if(lsIndPeriodicity.equalsIgnoreCase("DIAS")){
                //Obtener ID_CONFIGURATION
                String lsHours = null;
                String lsMinutes = null;
                String lsRadioDayEvery = "true";

                lsIndTypeSchedule = "EVERY";   
                lsHours = 
                    getPoBeginDias().getValue() == null ? null : 
                    getPoBeginDias().getValue().toString();
                
                lsMinutes = 
                    getPoBeginDayMinutes().getValue() == null ? null : 
                    getPoBeginDayMinutes().getValue().toString();
                
                lsIndBeginSchedule = new UtilFaces().buildTimeCron(lsHours, lsMinutes);
                
                if(lsIndBeginSchedule.equalsIgnoreCase("ERROR")){
                    lbProcess = false;
                }
                if(lbProcess){
                    liIndDayMonth = null;
                    liIndWeekMonth = null;
                    
                    lsCronConfig = loUtilFaces.getBuildUrlRestDays(lsRadioDayEvery,
                                                                          "false",
                                                                          lsHours,
                                                                          lsMinutes);
                    
                    lsIndCronExpression   = new UtilFaces().getCronExpression(lsCronConfig); 
                    String lsLunes = 
                        getPoLunDias().getValue() == null ? "" : 
                        getPoLunDias().getValue().toString();
                    if(lsLunes.equalsIgnoreCase("true")){liIndMonday = 1;}else{liIndMonday = 0;}
                    
                    String lsMartes = 
                        getPoMarDias().getValue() == null ? "" : 
                        getPoMarDias().getValue().toString();
                    if(lsMartes.equalsIgnoreCase("true")){liIndTuesday = 1;}else{liIndTuesday = 0;}
                    
                    String lsMiercoles = 
                        getPoMieDias().getValue() == null ? "" : 
                        getPoMieDias().getValue().toString();
                    if(lsMiercoles.equalsIgnoreCase("true")){liIndWednesday = 1;}else{liIndWednesday = 0;}
                    
                    String lsJueves = 
                        getPoJueDias().getValue() == null ? "" : 
                        getPoJueDias().getValue().toString();
                    if(lsJueves.equalsIgnoreCase("true")){liIndThursday = 1;}else{liIndThursday = 0;}
                    
                    String lsViernes =
                        getPoVieDias().getValue() == null ? "" :
                        getPoVieDias().getValue().toString();
                    if(lsViernes.equalsIgnoreCase("true")){liIndFriday = 1;}else{liIndFriday = 0;}
                    
                    String lsSabado = 
                        getPoSabDias().getValue() == null ? "" : 
                        getPoSabDias().getValue().toString();
                    if(lsSabado.equalsIgnoreCase("true")){liIndSaturday = 1;}else{liIndSaturday = 0;}
                    
                    String lsDomingo = 
                        getPoDomDias().getValue() == null ? "" : 
                        getPoDomDias().getValue().toString();
                    if(lsDomingo.equalsIgnoreCase("true")){liIndSunday = 1;}else{liIndSunday = 0;}
                }
                resetMinuteTab();
                resetHoursTab();              
                resetWeeksTab();
                resetMonthsTab();                        
            }
            // ###################### SEMANAS ##########################
            if(lsIndPeriodicity.equalsIgnoreCase("SEMANAS")){
                lsIndTypeSchedule = "EVERY";
                String lsHours = null;
                String lsMinutes = null;
                //Validar que al menos este seleccionado un dia
                String lsLunes = 
                    getPoLunWeek().getValue() == null ? "" :
                    getPoLunWeek().getValue().toString();
                if(lsLunes.equalsIgnoreCase("true")){liIndMonday = 1;}else{liIndMonday = 0;}
                
                String lsMartes = 
                    getPoMarWeek().getValue() == null ? "" : 
                    getPoMarWeek().getValue().toString();
                if(lsMartes.equalsIgnoreCase("true")){liIndTuesday = 1;}else{liIndTuesday = 0;}
                
                String lsMiercoles = 
                    getPoMieWeek().getValue() == null ? "" :
                    getPoMieWeek().getValue().toString();
                if(lsMiercoles.equalsIgnoreCase("true")){liIndWednesday = 1;}else{liIndWednesday = 0;}
                
                String lsJueves = 
                    getPoJueWeek().getValue() == null ? "" :
                    getPoJueWeek().getValue().toString();
                if(lsJueves.equalsIgnoreCase("true")){liIndThursday = 1;}else{liIndThursday = 0;}
                
                String lsViernes = 
                    getPoVieWeek().getValue() == null ? "" : 
                    getPoVieWeek().getValue().toString();
                if(lsViernes.equalsIgnoreCase("true")){liIndFriday = 1;}else{liIndFriday = 0;}
                
                String lsSabado = 
                    getPoSabWeek().getValue() == null ? "" : 
                    getPoSabWeek().getValue().toString();
                if(lsSabado.equalsIgnoreCase("true")){liIndSaturday = 1;}else{liIndSaturday = 0;}
                
                String lsDomingo = 
                    getPoDomWeek().getValue() == null ? "" : 
                    getPoDomWeek().getValue().toString();
                if(lsDomingo.equalsIgnoreCase("true")){liIndSunday = 1;}else{liIndSunday = 0;}
                
                if(liIndMonday == 0 && liIndTuesday == 0 && liIndWednesday == 0 &&
                liIndThursday == 0 && liIndFriday == 0 && liIndSaturday == 0 && liIndSunday == 0){
                    lbProcess = false;                       
                }                            
                if(lbProcess){
                    lsHours = 
                        getPoSemanasHoraIni().getValue() == null ? null : 
                        getPoSemanasHoraIni().getValue().toString();
                    
                    lsMinutes = 
                        getPoBeginSemanasMinutoIni().getValue() == null ? null : 
                        getPoBeginSemanasMinutoIni().getValue().toString();
                    lsIndBeginSchedule = new UtilFaces().buildTimeCron(lsHours, lsMinutes);
                    if(lsIndBeginSchedule.equalsIgnoreCase("ERROR")){
                        lbProcess = false;
                    }
                }
                if(lbProcess){
                    String lsDays = "";
                    if(liIndMonday == 1){
                        lsDays += "Mon,";
                    }
                    if(liIndTuesday == 1){
                        lsDays += "Tue,";
                    }
                    if(liIndWednesday == 1){
                        lsDays += "Wed,";
                    }
                    if(liIndThursday == 1){
                        lsDays += "Thu,";
                    }
                    if(liIndFriday == 1){
                        lsDays += "Fri,";
                    }
                    if(liIndSaturday == 1){
                        lsDays += "Sat,";
                    }
                    if(liIndSunday == 1){
                        lsDays += "Sun,";
                    }
                    
                    lsCronConfig = 
                        loUtilFaces.getBuildUrlRestWeeks(lsDays.substring(0, lsDays.length()-1),
                                                                          lsHours,
                                                                          lsMinutes);
                    
                    lsIndCronExpression = new UtilFaces().getCronExpression(lsCronConfig); 
                }
                resetMinuteTab();
                resetHoursTab();
                resetDaysTab();
                resetMonthsTab();    
            }
            // ###################### MESES ##########################
            if(lsIndPeriodicity.equalsIgnoreCase("MESES")){
                
                if(getPoMesHoraIni().getValue() != null && getPoMesMinutoIni().getValue() != null){
                    String lsRadioTheDay = 
                        getPoRadioTheDay().getValue() == null ? null : 
                        getPoRadioTheDay().getValue().toString();                    
                    
                    String lsRadioTheDayOfWeek = 
                        getPoRadioTheDayOfWeek().getValue() == null ? null : 
                        getPoRadioTheDayOfWeek().getValue().toString();
                    
                    String lsHours = null;
                    String lsMinutes = null;
                    String lsMesTheDay = null;
                    String lsMesEvery = null;
                    String lsMesTheDayOfWeek = null;
                    String lsMesEveryOfWeek = null;
                    String lsMesEveryOf = null;
                    if(lsRadioTheDay == null && lsRadioTheDayOfWeek == null){
                        lbProcess = false;
                    }else{
                        if(lsRadioTheDay.equalsIgnoreCase("true")){
                            lsMesTheDay = 
                                getPoMesTheDay().getValue() == null ? null : 
                                getPoMesTheDay().getValue().toString();
                            
                            lsMesEvery = 
                                getPoMesEvery().getValue() == null ? null : 
                                getPoMesEvery().getValue().toString();
                            
                            if(lsMesTheDay != null && lsMesEvery != null){
                                lsIndTypeSchedule = "EVERY";
                                liIndDayMonth = Integer.parseInt(lsMesTheDay);
                                lsIndValTypeSchedule = lsMesEvery;
                                lsMesEveryOf = lsMesEvery;
                                
                                lsHours = 
                                    getPoMesHoraIni().getValue() == null ? null : 
                                    getPoMesHoraIni().getValue().toString();
                                
                                lsMinutes = 
                                    getPoMesMinutoIni().getValue() == null ? null : 
                                    getPoMesMinutoIni().getValue().toString();
                                
                                lsIndBeginSchedule = 
                                    new UtilFaces().buildTimeCron(lsHours, lsMinutes);
                                
                                if(lsIndBeginSchedule.equalsIgnoreCase("ERROR")){
                                    lbProcess = false;
                                }
                            }else{
                                lbProcess = false;
                            }
                        }
                        if(lsRadioTheDayOfWeek.equalsIgnoreCase("true")){
                            
                            lsMesTheDayOfWeek = 
                                getPoDayOfSelected().getValue() == null ? null : 
                                getPoDayOfSelected().getValue().toString();
                            
                            lsMesEveryOfWeek = 
                                getPoDayOfWeekSelected().getValue() == null ? null : 
                                getPoDayOfWeekSelected().getValue().toString();
                            
                            lsMesEveryOf = 
                                getPoMesEveryOf().getValue() == null ? null : 
                                getPoMesEveryOf().getValue().toString();
                            
                            if(lsMesTheDayOfWeek != null && lsMesEveryOfWeek != null && lsMesEveryOf != null){
                                lsIndTypeSchedule = "AT";
                                if(lsMesTheDayOfWeek.equalsIgnoreCase("first")){liIndDayMonth = 1;}
                                if(lsMesTheDayOfWeek.equalsIgnoreCase("second")){liIndDayMonth = 2;}
                                if(lsMesTheDayOfWeek.equalsIgnoreCase("third")){liIndDayMonth = 3;}
                                if(lsMesTheDayOfWeek.equalsIgnoreCase("first")){liIndDayMonth = 4;}
                                
                                if(lsMesEveryOfWeek.equalsIgnoreCase("mon")){liIndWeekMonth = 1;}
                                if(lsMesEveryOfWeek.equalsIgnoreCase("tue")){liIndWeekMonth = 2;}
                                if(lsMesEveryOfWeek.equalsIgnoreCase("wed")){liIndWeekMonth = 3;}
                                if(lsMesEveryOfWeek.equalsIgnoreCase("thu")){liIndWeekMonth = 4;}
                                if(lsMesEveryOfWeek.equalsIgnoreCase("fri")){liIndWeekMonth = 5;}
                                if(lsMesEveryOfWeek.equalsIgnoreCase("sat")){liIndWeekMonth = 6;}
                                if(lsMesEveryOfWeek.equalsIgnoreCase("sun")){liIndWeekMonth = 7;}
                                
                                lsHours = 
                                    getPoMesHoraIni().getValue() == null ? null : 
                                    getPoMesHoraIni().getValue().toString();
                                
                                lsMinutes = 
                                    getPoMesMinutoIni().getValue() == null ? null : 
                                    getPoMesMinutoIni().getValue().toString();
                                
                                lsIndBeginSchedule = 
                                    new UtilFaces().buildTimeCron(lsHours, lsMinutes);
                                
                                if(lsIndBeginSchedule.equalsIgnoreCase("ERROR")){
                                    lbProcess = false;
                                }
                                
                            }else{
                                lbProcess = false; 
                            }
                        }
                        
                        if(lbProcess){
                            
                            String lsDay = lsRadioTheDay;
                            String lsDayValue = lsMesTheDay;
                            String lsThePlace = lsMesTheDayOfWeek;
                            String lsThePlaceDay = lsMesEveryOfWeek;
                            String lsOfEveryValue = lsMesEveryOf;
                            lsIndValTypeSchedule =  lsMesEveryOf;
                            lsCronConfig = 
                                loUtilFaces.getBuildUrlRestMonths(lsDay,
                                                                lsDayValue,
                                                                "",
                                                                lsOfEveryValue,
                                                                lsThePlace,
                                                                lsThePlaceDay,
                                                                lsHours,
                                                                lsMinutes
                                                                );
                            
                            lsIndCronExpression = 
                                new UtilFaces().getCronExpression(lsCronConfig); 
                        }                
                    }
                }else{
                    lbProcess = false;
                }
                resetMinuteTab();
                resetHoursTab();
                resetDaysTab();                
                resetWeeksTab();
            }

            if(lbProcess){
                if(lsIndCronExpression != null){                                    
                    ApplicationModule         loAm = 
                        Configuration.createRootApplicationModule(gsAmDef, gsConfig);
                    AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
                    
                    try{
                        //validar si es un update o insert
                        Integer liIdConfigurationUpd = 
                            loService.searchIdCronConfigModel(liIdService);  
                        
                        if(lsIndMinuteDeadLine != null){
                            if(lsIndMinuteDeadLine.length()<2){
                                lsIndMinuteDeadLine = "0"+lsIndMinuteDeadLine;
                            }
                        }
                        if(liIdConfigurationUpd > 0){
                            System.out.println("UPDATE");
                            loService.updateCronConfigModel(liIdConfigurationUpd,
                                                            liIdService,
                                                            lsIndPeriodicity,
                                                            lsIndBeginSchedule,
                                                            lsIndTypeSchedule,
                                                            lsIndValTypeSchedule,
                                                            liIndDayMonth,
                                                            liIndWeekMonth,
                                                            lsIndCronExpression,
                                                            liIndMonday,
                                                            liIndTuesday,
                                                            liIndWednesday,
                                                            liIndThursday,
                                                            liIndFriday,
                                                            liIndSaturday,
                                                            liIndSunday,
                                                            lsStatusTab,
                                                            lsCronConfig,
                                                            lsIndHourDeadLine,
                                                            lsIndMinuteDeadLine
                                                            ); 
                        }else{
                            liIdConfiguration = 
                                new ViewObjectDao().getMaxIdParadigm("Cron") + 1;
                            loService.insertCronConfigModel(liIdConfiguration ,
                                                            liIdService,
                                                            lsIndPeriodicity,
                                                            lsIndBeginSchedule,
                                                            lsIndTypeSchedule,
                                                            lsIndValTypeSchedule,
                                                            liIndDayMonth,
                                                            liIndWeekMonth,
                                                            lsIndCronExpression,
                                                            liIndMonday,
                                                            liIndTuesday,
                                                            liIndWednesday,
                                                            liIndThursday,
                                                            liIndFriday,
                                                            liIndSaturday,
                                                            liIndSunday,
                                                            lsStatusTab,
                                                            lsCronConfig,
                                                            lsIndHourDeadLine,
                                                            lsIndMinuteDeadLine
                                                            ); 
                        }    
                        FacesMessage loMsg;
                        loMsg = new FacesMessage("Operacion Realizada Satisfactoriamente");
                        loMsg.setSeverity(FacesMessage.SEVERITY_INFO);
                        FacesContext.getCurrentInstance().addMessage(null, loMsg);
                    } catch (Exception loEx) {
                        FacesMessage loMsg;
                        loMsg = new FacesMessage("Error de Comunicacion " + loEx);
                        loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
                        FacesContext.getCurrentInstance().addMessage(null, loMsg);
                    } finally {
                        Configuration.releaseRootApplicationModule(loAm, true);
                    }                    
                }else{
                    FacesMessage loMsg;
                    loMsg = new FacesMessage("No es Posible asignar Periodicidad");
                    loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
                    FacesContext.getCurrentInstance().addMessage(null, loMsg);
                }
            }else{
                FacesMessage loMsg = 
                    new FacesMessage("No Es Posible Guardar la Configuracion, Revise los Parametros");
                loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
                FacesContext.getCurrentInstance().addMessage(null, loMsg);
            }
        }
        new UtilFaces().hidePopup(getPoPopupSaveCronService());
        return null;
    }

    /**
     * Indica al contolador la pestaña seleccionada
     * @autor Jorge Luis Bautista Santiago
     * @param psDisclosureEvent
     * @return void
     */    
    public void disMinutos(DisclosureEvent psDisclosureEvent) {
        if (psDisclosureEvent.isExpanded()) {
            getPoIdTabSelected().setValue("MINUTOS");
        }
    }
    
    /**
     * Indica al contolador la pestaña seleccionada
     * @autor Jorge Luis Bautista Santiago
     * @param psDisclosureEvent
     * @return void
     */   
    public void disHoras(DisclosureEvent psDisclosureEvent) {
        if (psDisclosureEvent.isExpanded()) {
            getPoIdTabSelected().setValue("HORAS");
        }
    }
    
    /**
     * Indica al contolador la pestaña seleccionada
     * @autor Jorge Luis Bautista Santiago
     * @param psDisclosureEvent
     * @return void
     */  
    public void disDias(DisclosureEvent psDisclosureEvent) {
        if (psDisclosureEvent.isExpanded()) {            
            getPoIdTabSelected().setValue("DIAS");
        }
    }
    
    /**
     * Indica al contolador la pestaña seleccionada
     * @autor Jorge Luis Bautista Santiago
     * @param psDisclosureEvent
     * @return void
     */  
    public void disSemanas(DisclosureEvent psDisclosureEvent) {
        if (psDisclosureEvent.isExpanded()) {            
            getPoIdTabSelected().setValue("SEMANAS");
        }
    }
    
    /**
     * Indica al contolador la pestaña seleccionada
     * @autor Jorge Luis Bautista Santiago
     * @param psDisclosureEvent
     * @return void
     */  
    public void disMeses(DisclosureEvent psDisclosureEvent) {
        if (psDisclosureEvent.isExpanded()) {
            getPoIdTabSelected().setValue("MESES");
        }
    }
    
    /**
     * Accion ejecuta al seleccionar una fila de la tabla en cuestion
     * @autor Jorge Luis Bautista Santiago
     * @param toSelectionEvent
     * @return void
     */
    public void rowSelectionListener(SelectionEvent toSelectionEvent) {
        toSelectionEvent.getSource();
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblWebServices().getSelectedRowData();
        String                   lsIdService = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();
        getPoIdServiceSelected().setValue(lsIdService);
    }
    
    /**
     * Valida la hora de comienzo respecto a la hora cron
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateHourBeginRadioh(ValueChangeEvent toValueChangeEvent) {
        toValueChangeEvent.getSource();
        //getPoRadioHourEvery().setValue(false);
        //getPoRadioHourBegin().setValue(true);
    }

    /**
     * Valida la hora de comienzo respecto a la hora cron
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateHourBeginRadiom(ValueChangeEvent toValueChangeEvent) {
        toValueChangeEvent.getSource();
        //getPoRadioHourEvery().setValue(false);
        //getPoRadioHourBegin().setValue(true);
    }

    /**
     * Valida la hora de comienzo respecto a la hora cron
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateHourEveryRadio(ValueChangeEvent toValueChangeEvent) {
        toValueChangeEvent.getSource();
        //getPoRadioHourEvery().setValue(true);
        //getPoRadioHourBegin().setValue(false);
    }
    
    /**
     * Valida la hora de comienzo respecto a la hora cron
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateDayEveryRadio(ValueChangeEvent toValueChangeEvent) {
        toValueChangeEvent.getSource();
        getPoRadioDayEvery().setValue(true);
        getPoRadioDayBegin().setValue(false);
    }
    
    /**
     * Valida el dia de comienzo respecto a la hora cron
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateDayBeginRadiod(ValueChangeEvent toValueChangeEvent) {
        toValueChangeEvent.getSource();
        getPoRadioDayEvery().setValue(false);
        getPoRadioDayBegin().setValue(true);
    }
    
    /**
     * Valida el dia de comienzo respecto a la hora cron
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateDayBeginRadiom(ValueChangeEvent toValueChangeEvent) {
        toValueChangeEvent.getSource();
        getPoRadioDayEvery().setValue(false);
        getPoRadioDayBegin().setValue(true);
    }
    
    /**
     * Valida el dia de comienzo respecto a la hora cron
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateMesTheDayRadio(ValueChangeEvent toValueChangeEvent) {
        toValueChangeEvent.getSource();
        getPoRadioTheDay().setValue(true);
        getPoRadioTheDayOfWeek().setValue(false);
    }

    /**
     * Valida el dia de comienzo respecto a la hora cron
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateMesEveryRadio(ValueChangeEvent toValueChangeEvent) {
        toValueChangeEvent.getSource();
        getPoRadioTheDay().setValue(true);
        getPoRadioTheDayOfWeek().setValue(false);
    }

    /**
     * Valida el dia de comienzo respecto a la hora cron
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateMesEveryOfRadio(ValueChangeEvent toValueChangeEvent) {
        toValueChangeEvent.getSource();
        getPoRadioTheDay().setValue(false);
        getPoRadioTheDayOfWeek().setValue(true);
    }

    /**
     * Valida el dia de comienzo respecto a la hora cron
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateMesDayInitListener(ValueChangeEvent toValueChangeEvent) {
        toValueChangeEvent.getSource();
        getPoRadioTheDay().setValue(false);
        getPoRadioTheDayOfWeek().setValue(true);
    }

    /**
     * Valida el dia de comienzo respecto a la hora cron
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void validateMesDayOfWeekInitListener(ValueChangeEvent toValueChangeEvent) {
        toValueChangeEvent.getSource();
        getPoRadioTheDay().setValue(false);
        getPoRadioTheDayOfWeek().setValue(true);
    }

    /**
     * Muestra panel de tab para edita
     * @autor Jorge Luis Bautista Santiago  
     * @param actionEvent, 
     * @return void
     */
    public void showTabCronService(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        resetDisableMinuteTab(false);resetDisableHoursTab(false);
        resetDisableDaysTab(false);resetDisableWeeksTab(false);
        resetDisableMonthsTab(false);
        resetDisableAllTab(false);
        resetDisableTabButtons(false);        
        FacesCtrlHierNodeBinding  loNode = 
            (FacesCtrlHierNodeBinding) getPoTblWebServices().getSelectedRowData();
        String                    lsIdService = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();
        getPoIdServiceSelected().setValue(lsIdService);
        
        String                    lsNomService = 
            loNode.getAttribute("NomService") == null ? "" : 
            loNode.getAttribute("NomService").toString();
        //No mostrar cuando el servicio sea Log Comercial o Venta por modulos        
        boolean lbValidate = false;
        if(lsNomService != null){
            if(lsNomService.equalsIgnoreCase("WsLogComercial")){
                lbValidate = true;
            }
            if(lsNomService.equalsIgnoreCase("WsVentaModulos")){
                lbValidate = true;
            }    
        }
        
        if(!lbValidate){
            ApplicationModule         loAm = 
                Configuration.createRootApplicationModule(gsAmDef, gsConfig);
            AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
            try{
                Integer liRes = loService.searchCronConfigModel(Integer.parseInt(lsIdService));  
                String                lsIdNomService = 
                    loNode.getAttribute("IndDescService") == null ? "" : 
                    loNode.getAttribute("IndDescService").toString();
                if(liRes == 0){
                    getPoOperation().setValue("INSERT");
                    resetMinuteTab();resetHoursTab();resetDaysTab();resetWeeksTab();
                    resetMonthsTab();                
                    getPoIdTabSelected().setValue("MINUTOS");
                    getPoOperation().setValue("INSERT");
                    setDisclosedFirstTab(getPoTabMinutos(),getPoPanelTabbed());
                    getPoServiceToCron().setText(lsIdNomService);
                    getPoServiceToCron().setVisible(true);
                    getPoServiceToCronStatus().setText("");
                    getPoServiceToCronStatus().setVisible(false);
                }else{
                    getPoOperation().setValue("UPDATE");
                    getPoNomServicioCron().setValue(lsIdNomService);
                    getPoServiceToCron().setText(lsIdNomService);
                    getPoServiceToCron().setVisible(true);
                    Integer liIdCronConf = loService.searchIdCronConfigModel(Integer.parseInt(lsIdService));  
                    getPoIdCronConfiguration().setValue(liIdCronConf);
                    
                    //SETTEAR PANEL DE TAB
                    EvetvIntCronConfigTabRowBean loRowCron = 
                        loService.getRowCronConfigModel(liIdCronConf);
                    setTabPanelToEdit(loRowCron);                    
                }
            } catch (Exception loEx) {
                FacesMessage loMsg;
                loMsg = new FacesMessage("Error de Comunicacion " + loEx);
                loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
                FacesContext.getCurrentInstance().addMessage(null, loMsg);
            } finally {
                Configuration.releaseRootApplicationModule(loAm, true);
            }     
        }else{
            FacesMessage loMsg;
            loMsg = new FacesMessage("El Servicio Expuesto Solo Puede Existir Una Vez");
            loMsg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        }
    }
    
    /**
     * settea valores en el tab panel para editar
     * @autor Jorge Luis Bautista Santiago  
     * @param toRowCron, 
     * @return void
     */
    public void setTabPanelToEdit(EvetvIntCronConfigTabRowBean toRowCron){
        String  lsIndPeriodicity = toRowCron.getIndPeriodicity();
        String  lsIndBeginSchedule = toRowCron.getIndBeginSchedule() == null ? "": toRowCron.getIndBeginSchedule();
        String  lsIndTypeSchedule = toRowCron.getIndTypeSchedule();           
        String  lsIndValTypeSchedule = toRowCron.getIndValTypeSchedule();           
        Integer liIndDayMonth = toRowCron.getIndDayMonth();
        Integer liIndWeekMonth = toRowCron.getIndWeekMonth();
        String  lsHourDl = null;
        String  lsMinuteDl = null;
        String  lsAttribute1 = toRowCron.getAttribute1() == null ? "" : toRowCron.getAttribute1();
        boolean lbMonday = false;
        boolean lbTuesday = false;
        boolean lbWednesday = false;
        boolean lbThursday = false;
        boolean lbFriday = false;
        boolean lbSaturday = false;
        boolean lbSunday = false;
        if(lsAttribute1.length() > 0){
            String[] laDl = lsAttribute1.split(":");
            lsHourDl = laDl[0];
            lsMinuteDl = laDl[1];
        }
        
        Integer piIndMonday = toRowCron.getIndMonday();
        if(piIndMonday != null){lbMonday = piIndMonday == 1 ? true : false;}        
        Integer piIndTuesday = toRowCron.getIndTuesday();
        if(piIndMonday != null){lbTuesday = piIndTuesday == 1 ? true : false;}
        Integer piIndWednesday = toRowCron.getIndWednesday();
        if(piIndMonday != null){lbWednesday = piIndWednesday == 1 ? true : false;}
        Integer piIndThursday = toRowCron.getIndThursday();
        if(piIndMonday != null){lbThursday = piIndThursday == 1 ? true : false;}
        Integer piIndFriday = toRowCron.getIndFriday();
        if(piIndMonday != null){lbFriday = piIndFriday == 1 ? true : false;}
        Integer piIndSaturday = toRowCron.getIndSaturday();
        if(piIndMonday != null){lbSaturday = piIndSaturday == 1 ? true : false;}
        Integer piIndSunday = toRowCron.getIndSunday();
        if(piIndMonday != null){lbSunday = piIndSunday == 1 ? true : false;}
        getPoIdTabSelected().setValue(lsIndPeriodicity);
        String lsStatusService = null;
        if(toRowCron.getIndEstatus() != null){
            if(toRowCron.getIndEstatus().equalsIgnoreCase("1")){lsStatusService = "Configurado";}
            if(toRowCron.getIndEstatus().equalsIgnoreCase("2")){lsStatusService = "En Ejecucion";}
            if(toRowCron.getIndEstatus().equalsIgnoreCase("3")){lsStatusService = "Detenido";}
        }else{
            lsStatusService = "Sin Configuracion";
        }
        getPoServiceToCronStatus().setText("Estado: "+lsStatusService);
        getPoServiceToCronStatus().setVisible(true);
        
        if(lsIndPeriodicity.equalsIgnoreCase("MINUTOS")){  
            resetHoursTab();
            resetDaysTab();
            resetWeeksTab();
            resetMonthsTab();
            
            getPoCadaMinutos().setValue(lsIndValTypeSchedule);
            getPoLunMinutos().setValue(lbMonday);
            getPoMarMinutos().setValue(lbTuesday);
            getPoMieMinutos().setValue(lbWednesday);
            getPoJueMinutos().setValue(lbThursday);
            getPoVieMinutos().setValue(lbFriday);
            getPoSabMinutos().setValue(lbSaturday);
            getPoDomMinutos().setValue(lbSunday);
            getPoDeadLineMinutos().setValue(lsHourDl);
            getPoDeadLineMinMinutos().setValue(lsMinuteDl);
            String[] laBegin = lsIndBeginSchedule.split(":");
            if(laBegin.length > 1){
                getPoBeginHorasMin().setValue(laBegin[0]);
                getPoBeginMinutesMin().setValue(laBegin[1]);    
            }                        
            setDisclosedFirstTab(getPoTabMinutos(),getPoPanelTabbed());
        }
        
        if(lsIndPeriodicity.equalsIgnoreCase("HORAS")){ 
            resetMinuteTab();
            resetDaysTab();
            resetWeeksTab();
            resetMonthsTab();
            if(lsIndTypeSchedule.equalsIgnoreCase("AT")){ //Configuracion por hora inicial
                //getPoRadioHourBegin().setValue(true);    
                //getPoRadioHourEvery().setValue(false);
                String[] laBegin = lsIndBeginSchedule.split(":");
                if(laBegin.length > 0){
                    getPoBeginHoras().setValue(laBegin[0]);
                    getPoBeginMinutes().setValue(laBegin[1]);
                }
                getPoCadaHoras().setValue(null);
            }else{
                //getPoRadioHourBegin().setValue(false);    
                //getPoRadioHourEvery().setValue(true);
                getPoCadaHoras().setValue(lsIndValTypeSchedule);            
                getPoBeginHoras().setValue(null);
                getPoBeginMinutes().setValue(null);
            }
            getPoLunHoras().setValue(lbMonday);
            getPoMarHoras().setValue(lbTuesday);
            getPoMieHoras().setValue(lbWednesday);
            getPoJueHoras().setValue(lbThursday);
            getPoVieHoras().setValue(lbFriday);
            getPoSabHoras().setValue(lbSaturday);
            getPoDomHoras().setValue(lbSunday);
            getPoDeadLineHoras().setValue(lsHourDl);
            getPoDeadLineMinHoras().setValue(lsMinuteDl);
            setDisclosedFirstTab(getPoTabHoras(),getPoPanelTabbed());
        }
        
        if(lsIndPeriodicity.equalsIgnoreCase("DIAS")){  
            resetHoursTab();
            resetMinuteTab();
            resetWeeksTab();
            resetMonthsTab();
            String[] laBegin = lsIndBeginSchedule.split(":");
            getPoCadaHoras().setValue(lsIndValTypeSchedule);                             
            getPoBeginDias().setValue(laBegin[0]);
            getPoBeginDayMinutes().setValue(laBegin[1]);
            getPoLunDias().setValue(lbMonday);
            getPoMarDias().setValue(lbTuesday);
            getPoMieDias().setValue(lbWednesday);
            getPoJueDias().setValue(lbThursday);
            getPoVieDias().setValue(lbFriday);
            getPoSabDias().setValue(lbSaturday);
            getPoDomDias().setValue(lbSunday);
            setDisclosedFirstTab(getPoTabDias(),getPoPanelTabbed());
        }
        
        if(lsIndPeriodicity.equalsIgnoreCase("SEMANAS")){  
            resetHoursTab();
            resetDaysTab();
            resetMinuteTab();
            resetMonthsTab();
            String[] laBegin = lsIndBeginSchedule.split(":");
            getPoSemanasHoraIni().setValue(laBegin[0]);
            getPoBeginSemanasMinutoIni().setValue(laBegin[1]);
            getPoLunWeek().setValue(lbMonday);
            getPoMarWeek().setValue(lbTuesday);
            getPoMieWeek().setValue(lbWednesday);
            getPoJueWeek().setValue(lbThursday);
            getPoVieWeek().setValue(lbFriday);
            getPoSabWeek().setValue(lbSaturday);
            getPoDomWeek().setValue(lbSunday);
            
            setDisclosedFirstTab(getPoTabSemanas(),getPoPanelTabbed());
            
        }
        
        if(lsIndPeriodicity.equalsIgnoreCase("MESES")){ 
            resetMinuteTab();
            resetDaysTab();
            resetWeeksTab();
            resetHoursTab();
            
            if(lsIndTypeSchedule.equalsIgnoreCase("AT")){ //Configuracion por hora inicial            
                getPoRadioTheDayOfWeek().setValue(true);    
                getPoRadioTheDay().setValue(false);
                // el rpimer segundo, etc
                String lsSelWeekMes = "";
                
                if(liIndWeekMonth == 1){lsSelWeekMes = "first";}
                if(liIndWeekMonth == 2){lsSelWeekMes = "second";}
                if(liIndWeekMonth == 3){lsSelWeekMes = "third";}
                if(liIndWeekMonth == 4){lsSelWeekMes = "fourth";}                                
                
                String lsSelDayMes = "";
                if(liIndDayMonth == 1){lsSelDayMes = "mon";}
                if(liIndDayMonth == 2){lsSelDayMes = "tue";}
                if(liIndDayMonth == 3){lsSelDayMes = "wed";}
                if(liIndDayMonth == 4){lsSelDayMes = "thu";}
                if(liIndDayMonth == 5){lsSelDayMes = "fri";}
                if(liIndDayMonth == 6){lsSelDayMes = "sat";}
                if(liIndDayMonth == 7){lsSelDayMes = "sun";}
                
                getPoDayOfSelected().setValue(lsSelWeekMes);                
                getPoDayOfWeekSelected().setValue(lsSelDayMes);
                getPoMesEveryOf().setValue(lsIndValTypeSchedule);                
                getPoMesTheDay().setValue(null);                
                getPoMesEvery().setValue(null);
            }else{
                getPoRadioTheDayOfWeek().setValue(false);    
                getPoRadioTheDay().setValue(true);                
                getPoMesTheDay().setValue(liIndDayMonth);            
                getPoMesEvery().setValue(lsIndValTypeSchedule);                
                getPoDayOfSelected().setValue(null);
                getPoDayOfWeekSelected().setValue(null);
                getPoMesEveryOf().setValue(null);
            }
            
            String[] laBegin = lsIndBeginSchedule.split(":");
            getPoMesHoraIni().setValue(laBegin[0]);
            getPoMesMinutoIni().setValue(laBegin[1]);
            setDisclosedFirstTab(getPoTabMeses(), getPoPanelTabbed());
        }
    }
    
    /**
     * Reinicia valores de configuracion Cron para Minutos
     * @autor Jorge Luis Bautista Santiago
     * @return void
     */
    public void resetMinuteTab(){
        getPoCadaMinutos().setValue(null);
        getPoLunMinutos().setValue(null);
        getPoMarMinutos().setValue(null);
        getPoMieMinutos().setValue(null);
        getPoJueMinutos().setValue(null);
        getPoVieMinutos().setValue(null);
        getPoSabMinutos().setValue(null);
        getPoDomMinutos().setValue(null);
        
        getPoDeadLineMinutos().setValue(null);
        getPoBeginHorasMin().setValue(null);
        getPoBeginMinutesMin().setValue(null);        
        getPoDeadLineMinMinutos().setValue(null);
        
    }
    
    /**
     * Reinicia valores de configuracion Cron para Horas
     * @autor Jorge Luis Bautista Santiago
     * @return void
     */
    public void resetHoursTab(){
        //getPoRadioHourEvery().setValue(null);
        getPoCadaHoras().setValue(null);
        //getPoRadioHourBegin().setValue(null);
        getPoBeginHoras().setValue(null);
        getPoBeginMinutes().setValue(null);
        getPoLunHoras().setValue(null);
        getPoMarHoras().setValue(null);
        getPoMieHoras().setValue(null);
        getPoJueHoras().setValue(null);
        getPoVieHoras().setValue(null);
        getPoSabHoras().setValue(null);
        getPoDomHoras().setValue(null);
        
        getPoDeadLineHoras().setValue(null);
        getPoDeadLineMinHoras().setValue(null);    
    }
    
    /**
     * Reinicia valores de configuracion Cron para Dias
     * @autor Jorge Luis Bautista Santiago
     * @return void
     */
    public void resetDaysTab(){
        getPoRadioDayEvery().setValue(null);
        getPoCadaDias().setValue(null);
        getPoRadioDayBegin().setValue(null);
        getPoBeginDias().setValue(null);
        getPoBeginDayMinutes().setValue(null);
        getPoLunDias().setValue(null);
        getPoMarDias().setValue(null);
        getPoMieDias().setValue(null);
        getPoJueDias().setValue(null);
        getPoVieDias().setValue(null);
        getPoSabDias().setValue(null);
        getPoDomDias().setValue(null);
    }
    
    /**
     * Reinicia valores de configuracion Cron para Semanas
     * @autor Jorge Luis Bautista Santiago
     * @return void
     */
    public void resetWeeksTab(){
        getPoSemanasHoraIni().setValue(null);
        getPoBeginSemanasMinutoIni().setValue(null);
        getPoLunWeek().setValue(null);
        getPoMarWeek().setValue(null);
        getPoMieWeek().setValue(null);
        getPoJueWeek().setValue(null);
        getPoVieWeek().setValue(null);
        getPoSabWeek().setValue(null);
        getPoDomWeek().setValue(null);
    }
    
    /**
     * Reinicia valores de configuracion Cron para Meses
     * @autor Jorge Luis Bautista Santiago
     * @return void
     */
    public void resetMonthsTab(){
        getPoRadioTheDayOfWeek().setValue(null);
        getPoDayOfSelected().setValue(null);
        getPoDayOfWeekSelected().setValue(null);
        getPoMesEveryOf().setValue(null);
        getPoMesHoraIni().setValue(null);
        getPoMesMinutoIni().setValue(null);
        getPoRadioTheDay().setValue(null);
        getPoMesTheDay().setValue(null);
        getPoMesEvery().setValue(null);
    }
    
    /**
     * Deshabilita valores de configuracion Cron para Minutos
     * @autor Jorge Luis Bautista Santiago
     * @param tbFlag
     * @return void
     */
    public void resetDisableMinuteTab(boolean tbFlag){
        getPoCadaMinutos().setDisabled(tbFlag);
        getPoLunMinutos().setDisabled(tbFlag);
        getPoMarMinutos().setDisabled(tbFlag);
        getPoMieMinutos().setDisabled(tbFlag);
        getPoJueMinutos().setDisabled(tbFlag);
        getPoVieMinutos().setDisabled(tbFlag);
        getPoSabMinutos().setDisabled(tbFlag);
        getPoDomMinutos().setDisabled(tbFlag);
        getPoDeadLineMinutos().setDisabled(tbFlag);        
        getPoDeadLineMinMinutos().setDisabled(tbFlag);
        getPoBeginHorasMin().setDisabled(tbFlag);
        getPoBeginMinutesMin().setDisabled(tbFlag);
    }
    
    /**
     * Deshabilita valores de configuracion Cron para Horas
     * @autor Jorge Luis Bautista Santiago
     * @param tbFlag
     * @return void
     */
    public void resetDisableHoursTab(boolean tbFlag){
        //getPoRadioHourEvery().setDisabled(tbFlag);
        getPoCadaHoras().setDisabled(tbFlag);
        //getPoRadioHourBegin().setDisabled(tbFlag);
        getPoBeginHoras().setDisabled(tbFlag);
        getPoBeginMinutes().setDisabled(tbFlag);
        getPoLunHoras().setDisabled(tbFlag);
        getPoMarHoras().setDisabled(tbFlag);
        getPoMieHoras().setDisabled(tbFlag);
        getPoJueHoras().setDisabled(tbFlag);
        getPoVieHoras().setDisabled(tbFlag);
        getPoSabHoras().setDisabled(tbFlag);
        getPoDomHoras().setDisabled(tbFlag);
        
        getPoDeadLineHoras().setDisabled(tbFlag);
        getPoDeadLineMinHoras().setDisabled(tbFlag);
    }
    
    /**
     * Deshabilita valores de configuracion Cron para Dias
     * @autor Jorge Luis Bautista Santiago
     * @param tbFlag
     * @return void
     */
    public void resetDisableDaysTab(boolean tbFlag){
        getPoRadioDayEvery().setDisabled(tbFlag);
        getPoCadaDias().setDisabled(tbFlag);
        getPoRadioDayBegin().setDisabled(tbFlag);
        getPoBeginDias().setDisabled(tbFlag);
        getPoBeginDayMinutes().setDisabled(tbFlag);
        getPoLunDias().setDisabled(tbFlag);
        getPoMarDias().setDisabled(tbFlag);
        getPoMieDias().setDisabled(tbFlag);
        getPoJueDias().setDisabled(tbFlag);
        getPoVieDias().setDisabled(tbFlag);
        getPoSabDias().setDisabled(tbFlag);
        getPoDomDias().setDisabled(tbFlag);
    }
    
    /**
     * Deshabilita valores de configuracion Cron para Semanas
     * @autor Jorge Luis Bautista Santiago
     * @param tbFlag
     * @return void
     */
    public void resetDisableWeeksTab(boolean tbFlag){
        getPoSemanasHoraIni().setDisabled(tbFlag);
        getPoBeginSemanasMinutoIni().setDisabled(tbFlag);
        getPoLunWeek().setDisabled(tbFlag);
        getPoMarWeek().setDisabled(tbFlag);
        getPoMieWeek().setDisabled(tbFlag);
        getPoJueWeek().setDisabled(tbFlag);
        getPoVieWeek().setDisabled(tbFlag);
        getPoSabWeek().setDisabled(tbFlag);
        getPoDomWeek().setDisabled(tbFlag);
    }
    
    /**
     * Deshabilita valores de configuracion Cron para Meses
     * @autor Jorge Luis Bautista Santiago
     * @param tbFlag
     * @return void
     */
    public void resetDisableMonthsTab(boolean tbFlag){
        getPoRadioTheDayOfWeek().setDisabled(tbFlag);
        getPoDayOfSelected().setDisabled(tbFlag);
        getPoDayOfWeekSelected().setDisabled(tbFlag);
        getPoMesEveryOf().setDisabled(tbFlag);
        getPoMesHoraIni().setDisabled(tbFlag);
        getPoMesMinutoIni().setDisabled(tbFlag);
        getPoRadioTheDay().setDisabled(tbFlag);
        getPoMesTheDay().setDisabled(tbFlag);
        getPoMesEvery().setDisabled(tbFlag);
    }
    
    /**
     * Deshabilita todas las pestañas
     * @autor Jorge Luis Bautista Santiago
     * @param tbFlag
     * @return void
     */
    public void resetDisableAllTab(boolean tbFlag){
        getPoTabMinutos().setDisabled(tbFlag);
        getPoTabHoras().setDisabled(tbFlag);
        getPoTabDias().setDisabled(tbFlag);
        getPoTabSemanas().setDisabled(tbFlag);
        getPoTabMeses().setDisabled(tbFlag);
    }
    
    /**
     * Deshabilita botones principales
     * @autor Jorge Luis Bautista Santiago
     * @param tbFlag
     * @return void
     */
    public void resetDisableTabButtons(boolean tbFlag){
        getPoBtnSaveCronService().setDisabled(tbFlag);
        getPoBtnDeleteCronService().setDisabled(tbFlag);        
          
    }

    /**
     * Elimina configuracion de cron por servicio
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String deleteCronServiceAction() {
        String                    lsIdCronService = 
            getPoDeleteCronIdBinding().getValue() == null ? "" : 
            getPoDeleteCronIdBinding().getValue().toString();
        
        ApplicationModule         loAm = 
            Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
        try{
            loService.deleteCronConfigModel(Integer.parseInt(lsIdCronService));    
            resetMinuteTab();resetHoursTab();resetDaysTab();resetWeeksTab();resetMonthsTab();
            resetDisableMinuteTab(true);resetDisableHoursTab(true);
            resetDisableDaysTab(true);resetDisableWeeksTab(true);resetDisableMonthsTab(true);
            resetDisableAllTab(true);
            resetDisableTabButtons(true);
            FacesMessage loMsg;
            loMsg = new FacesMessage("Operacion Ejecutada Satisfactoriamente");
            loMsg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } catch (Exception loEx) {
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx);
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
        }   
        new UtilFaces().hidePopup(getPoPopupDeleteCronService());
        return null;
    }

    /**
     * Cancela borrado de cron
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String cancelDeleteCronService() {
        new UtilFaces().hidePopup(getPoPopupDeleteCronService());
        return null;
    }
    
    /**
     * Muestra popup que solicita datos para eliminar el servicio CRON
     * @autor Jorge Luis Bautista Santiago       
     * @return String
     */
    public String showDeletePopupCronService() {
        String                   lsIdCronService = 
        getPoIdCronConfiguration().getValue() == null ? "" : 
        getPoIdCronConfiguration().getValue().toString();
        String                   lsServiceDesc = 
            getPoNomServicioCron().getValue() == null ? "" : 
            getPoNomServicioCron().getValue().toString();
        getPoDeleteCronIdBinding().setValue(lsIdCronService);
        getPoDeleteCronMsgLbl().setLabel("Eliminar Configuracion de " + lsServiceDesc + "?");
        new UtilFaces().showPopup(getPoPopupDeleteCronService());
        return null;
    }
    
    /**
     * Visualiza en pantalla la tabla deseada
     * @autor Jorge Luis Bautista Santiago
     * @param toTabBind
     * @param toRichPanelTabbed
     * @return void
     */
    public void setDisclosedFirstTab(RichShowDetailItem toTabBind,
                                     RichPanelTabbed toRichPanelTabbed) {
        for (UIComponent child : toRichPanelTabbed.getChildren()) {
            RichShowDetailItem sdi = (RichShowDetailItem) child;
            if (sdi.getClientId().equals(toTabBind.getClientId())) {
                sdi.setDisclosed(true);
            } else {
                sdi.setDisclosed(false);
            }
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(toRichPanelTabbed);
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

    public String listCronsAction() {
        EntityMappedDao loEntityMappedDao = new EntityMappedDao();
        Integer liRes = loEntityMappedDao.enableInitializedCron();
        if(liRes > 0){
            System.out.println(">>> Se han habilitado crones activos por reinicio - estatus Deshabilitado [4]");                                                
            EvetvIntServiceBitacoraTab loEvetvIntServiceBitacoraTab = new EvetvIntServiceBitacoraTab();
            loEvetvIntServiceBitacoraTab.setLsIdLogServices("0");
            loEvetvIntServiceBitacoraTab.setLsIdService("0");
            loEvetvIntServiceBitacoraTab.setLsIndProcess("0");
            loEvetvIntServiceBitacoraTab.setLsNumEvtbProcessId("0");
            loEvetvIntServiceBitacoraTab.setLsNumPgmProcessId("0");
            loEvetvIntServiceBitacoraTab.setLsIndEvento("Se han habilitado crones activos por reinicio");
            loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTab,0, "Server");
            listCronsActionBak();
        }
        //Refresh tabla principal
        new UtilFaces().refreshTableWhereIterator("1 = 1 ",
                                                  gsEntityIterator,
                                                  getPoTblWebServices()
                                                  );
        
        new UtilFaces().hidePopup(getPoPopupRstCronService());
        return null;
    }

    public String listCronsActionBak() {
        boolean lbSuccess = true;
        String lsMessage = "";
        Scheduler scheduler;
        System.out.println("*********************************CRONES EN EJECUCION ****************************************");
        try {
            scheduler = new StdSchedulerFactory().getScheduler();            
            for (String groupName : scheduler.getJobGroupNames()) {
             for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
              String jobName = jobKey.getName();
              String jobGroup = jobKey.getGroup();
        
              //get job's trigger
              List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
              Date nextFireTime = triggers.get(0).getNextFireTime(); 
        
                System.out.println("[jobName] : " + jobName + " [groupName] : "
                    + jobGroup + " - " + nextFireTime);
        
              }    
            }
        } catch (SchedulerException e) {
            System.out.println("Error al listar crones "+e.getMessage());
            lbSuccess = false;
            lsMessage += " " + e.getMessage();
        }
        System.out.println("********************************** ELIMINANDO DE MEMORIA *****************************************************");
        Scheduler schedulerDlt;
        try {
            schedulerDlt = new StdSchedulerFactory().getScheduler();            
            for (String groupName : schedulerDlt.getJobGroupNames()) {
             for (JobKey jobKey : schedulerDlt.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
              //String jobName = jobKey.getName();
              //String jobGroup = jobKey.getGroup();
                  schedulerDlt.deleteJob(jobKey);
              //get job's trigger
              //List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
              //Date nextFireTime = triggers.get(0).getNextFireTime(); 
        
                //System.out.println("[jobName] : " + jobName + " [groupName] : "
                //    + jobGroup + " - " + nextFireTime);
        
              }    
            }
        } catch (SchedulerException e) {
            System.out.println("Error al ELIMINAR crones "+e.getMessage());
            lbSuccess = false;
            lsMessage += " " + e.getMessage();
        }
        
        System.out.println("********************************** LISTANDO CRONES EN EJECUCION *****************************************************");
        Scheduler schedulerBd;
        try {
            schedulerBd = new StdSchedulerFactory().getScheduler();            
            for (String groupName : schedulerBd.getJobGroupNames()) {
             for (JobKey jobKey : schedulerBd.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
              String jobName = jobKey.getName();
              String jobGroup = jobKey.getGroup();
              //get job's trigger
              List<Trigger> triggers = (List<Trigger>) schedulerBd.getTriggersOfJob(jobKey);
              Date nextFireTime = triggers.get(0).getNextFireTime(); 
        
                System.out.println("[jobName] : " + jobName + " [groupName] : "
                    + jobGroup + " - " + nextFireTime);
        
              }    
            }
        } catch (SchedulerException e) {
            System.out.println("Error al listar crones "+e.getMessage());
            lbSuccess = false;
            lsMessage += " " + e.getMessage();
        }
        System.out.println("***************************************************************************************");
        
        List<EvetvIntCronConfigTabRowBean> laServsCron = 
            new ArrayList<EvetvIntCronConfigTabRowBean>();
        EntityMappedDao loEntityMappedDao = new EntityMappedDao();
        laServsCron = loEntityMappedDao.getServicesCronExecution();
        System.out.println("Crones inicializados ["+laServsCron.size()+"] CONFIGURADOS Eliminarlos todos..........");
        for(EvetvIntCronConfigTabRowBean loSerCronBean: laServsCron){
            //El nombre Key del servicio es idService y nomService
            System.out.println("IdService["+loSerCronBean.getIdService()+"]");
            String lsNomService = 
                loEntityMappedDao.getTypeService(String.valueOf(loSerCronBean.getIdService()));
            String psIdTrigger = loSerCronBean.getIdService() + "-" + lsNomService;
            Scheduler loSchedulerDel;                    
            //---------------------------------------------------------------------------------
            try {
                String    lsNewCronExpression = "0 0 1 1/1 * ? *"; 
                System.out.println("Eliminar cualquier cron con ID["+psIdTrigger+"]");
                loSchedulerDel = new StdSchedulerFactory().getScheduler();
                Trigger loTrigger = 
                    TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                       CronScheduleBuilder.cronSchedule(lsNewCronExpression)).startNow().build();
                loSchedulerDel.rescheduleJob(new TriggerKey(psIdTrigger), loTrigger);
                loSchedulerDel.deleteJob(loTrigger.getJobKey());                        
                System.out.println("Cron con ID["+psIdTrigger+"] Eliminado Satifactoriamente");
            } catch (SchedulerException loEx) {
                System.out.println("SchedulerException: " + loEx.getMessage());
                lbSuccess = false;
                lsMessage += " " + loEx.getMessage();
            }                                                            
        }
        
        System.out.println("--------------- Lista de Crones despues de ser eliminados: ---------------");        
        try {
            scheduler = new StdSchedulerFactory().getScheduler();            
            for (String groupName : scheduler.getJobGroupNames()) {
             for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
              String jobName = jobKey.getName();
              String jobGroup = jobKey.getGroup();
        
              //get job's trigger
              List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
              Date nextFireTime = triggers.get(0).getNextFireTime(); 
        
                System.out.println("[jobName] : " + jobName + " [groupName] : "
                    + jobGroup + " - " + nextFireTime);
        
              }    
            }
        } catch (SchedulerException e) {
            System.out.println("Error al listar crones "+e.getMessage());               
        }
        System.out.println("***************************************************************************************");
        if(lbSuccess){
            //System.out.println("Iniciar Crones(nuevos) en el actual despliegue....");
            for(EvetvIntCronConfigTabRowBean loSerCronBean: laServsCron){
                //El nombre Key del servicio es idService y nomService
                System.out.println("IdService["+loSerCronBean.getIdService()+"]");
                String lsNomService = 
                    loEntityMappedDao.getTypeService(String.valueOf(loSerCronBean.getIdService()));
                String psIdTrigger = loSerCronBean.getIdService() + "-" + lsNomService;
                System.out.print(" psIdTrigger["+psIdTrigger+"]");
                String lsIndEstatusService = 
                    loEntityMappedDao.getIndEstatusService(String.valueOf(loSerCronBean.getIdService()));
                if(lsIndEstatusService.equalsIgnoreCase("1")){
                    /////////////////// TARGETS //////////////////////////////////////////
                    if(lsNomService.equalsIgnoreCase("WsTargets")){
                        Scheduler loScheduler;                    
                        System.out.println("Iniciando Targets con ID["+psIdTrigger+"]");
                        try {
                            String lsCronExpression = loSerCronBean.getIndCronExpression();
                            if(lsCronExpression != null){
                                boolean lbSimple = false;
                                Trigger loTrigger = null;
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteTargetsCron.class).build();
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loSerCronBean.getIndBeginSchedule() == null ? "08:00" : 
                                        loSerCronBean.getIndBeginSchedule();
                                    String lsFin = 
                                        loSerCronBean.getAttribute1() == null ? "23:50" : 
                                        loSerCronBean.getAttribute1();
                                    String lsEvery =  
                                        loSerCronBean.getIndValTypeSchedule() == null ? "23" : 
                                        loSerCronBean.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    
                                    if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                }                        
                                String lsUserName = loSerCronBean.getAttribute14();
                                System.out.println("lsUserName["+lsUserName+"]");
                                Integer liIdUser = loSerCronBean.getNumLastUpdateBy();
                                System.out.println("liIdUser["+liIdUser+"]");
                                String lsIdService = String.valueOf(loSerCronBean.getIdService());
                                System.out.println("lsIdService["+lsIdService+"]");
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", lsIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(liIdUser)); 
                                loJobDataMap.put("lsUserName", lsUserName); 
                                loJobDataMap.put("lsIdRequestTargets", "0");
                                loJobDataMap.put("lsTypeRequest", "load");
                                loScheduler.scheduleJob(loJob, loTrigger);  
                                System.out.println("start");
                                loScheduler.start();
                            }
                        } catch (Exception loEx) {
                            System.out.println("Error al inicializar tareas - Targets " + loEx.getMessage());                    
                        } 
                    }
                    ///////////////////////////////////////////////////////////////////////
                    if(lsNomService.equalsIgnoreCase("WsProgramas")){
                        Scheduler loScheduler;                    
                        System.out.println("Iniciando Programas con ID["+psIdTrigger+"]");
                        try {
                            String lsCronExpression = loSerCronBean.getIndCronExpression();
                            if(lsCronExpression != null){
                                boolean lbSimple = false;
                                Trigger loTrigger = null;
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteProgramasCron.class).build();
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loSerCronBean.getIndBeginSchedule() == null ? "08:00" : 
                                        loSerCronBean.getIndBeginSchedule();
                                    String lsFin = 
                                        loSerCronBean.getAttribute1() == null ? "23:50" : 
                                        loSerCronBean.getAttribute1();
                                    String lsEvery =  
                                        loSerCronBean.getIndValTypeSchedule() == null ? "23" : 
                                        loSerCronBean.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    
                                    if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                }                        
                                String lsUserName = loSerCronBean.getAttribute14();
                                System.out.println("lsUserName["+lsUserName+"]");
                                Integer liIdUser = loSerCronBean.getNumLastUpdateBy();
                                System.out.println("liIdUser["+liIdUser+"]");
                                String lsIdService = String.valueOf(loSerCronBean.getIdService());
                                System.out.println("lsIdService["+lsIdService+"]");
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", lsIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(liIdUser)); 
                                loJobDataMap.put("lsUserName", lsUserName); 
                                loJobDataMap.put("lsIdRequestPgr", "0");
                                loJobDataMap.put("lsTypeRequest", "load");
                                loScheduler.scheduleJob(loJob, loTrigger);  
                                System.out.println("start");
                                loScheduler.start();
                            }
                        } catch (Exception loEx) {
                            System.out.println("Error al inicializar tareas " + loEx.getMessage());                    
                        } 
                    }
                    if(lsNomService.equalsIgnoreCase("WsLogCertificado")){
                        System.out.println("Iniciando Log Certificado con ID["+psIdTrigger+"]");
                        Scheduler loScheduler;
                        try {
                            boolean lbSimple = false;
                            Trigger loTrigger = null;
                            String lsCronExpression = loSerCronBean.getIndCronExpression();
                            if(lsCronExpression != null){
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteLogCertificadoCron.class).build();
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loSerCronBean.getIndBeginSchedule() == null ? "08:00" : 
                                        loSerCronBean.getIndBeginSchedule();
                                    String lsFin = 
                                        loSerCronBean.getAttribute1() == null ? "23:50" : 
                                        loSerCronBean.getAttribute1();
                                    String lsEvery =  
                                        loSerCronBean.getIndValTypeSchedule() == null ? "23" : 
                                        loSerCronBean.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                }
                                String lsUserName = loSerCronBean.getAttribute14();
                                Integer liIdUser = loSerCronBean.getNumLastUpdateBy();
                                String lsIdService = String.valueOf(loSerCronBean.getIdService());
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", lsIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(liIdUser)); 
                                loJobDataMap.put("lsUserName", lsUserName); 
                                loJobDataMap.put("lsIdRequestPgr", "0");    
                                loJobDataMap.put("lsTypeRequest", "load");
                                loScheduler.scheduleJob(loJob, loTrigger);                        
                                loScheduler.start();
                            }
                        } catch (Exception loEx) {
                            System.out.println("Error al inicializar tareas " + loEx.getMessage());                    
                        }
                    }
                    if(lsNomService.equalsIgnoreCase("WsVentaTradicional")){
                        System.out.println("Iniciando Venta Tradicional con ID["+psIdTrigger+"]");
                        Scheduler loScheduler;
                        try {
                            boolean lbSimple = false;
                            Trigger loTrigger = null;
                            String lsCronExpression = loSerCronBean.getIndCronExpression();
                            if(lsCronExpression != null){
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteVtaTradicionalCron.class).build();
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loSerCronBean.getIndBeginSchedule() == null ? "08:00" : 
                                        loSerCronBean.getIndBeginSchedule();
                                    String lsFin = 
                                        loSerCronBean.getAttribute1() == null ? "23:50" : 
                                        loSerCronBean.getAttribute1();
                                    String lsEvery =  
                                        loSerCronBean.getIndValTypeSchedule() == null ? "23" : 
                                        loSerCronBean.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    
                                    if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                }    
                                String lsUserName = loSerCronBean.getAttribute14();
                                Integer liIdUser = loSerCronBean.getNumLastUpdateBy();
                                String lsIdService = String.valueOf(loSerCronBean.getIdService());
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", lsIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(liIdUser)); 
                                loJobDataMap.put("lsUserName", lsUserName); 
                                loJobDataMap.put("lsIdRequest", "0"); 
                                loJobDataMap.put("lsStatusType", "PN");     
                                loJobDataMap.put("lsTypeRequest", "load");
                                loScheduler.scheduleJob(loJob, loTrigger);                        
                                loScheduler.start();
                            }
                        } catch (Exception loEx) {
                            System.out.println("Error al inicializar tareas " + loEx.getMessage());                    
                        }
                    }
                    if(lsNomService.equalsIgnoreCase("WsBreaks")){
                        System.out.println("Iniciando Parrillas Cortes con ID["+psIdTrigger+"]");
                        Scheduler loScheduler;
                        try {
                            boolean lbSimple = false;
                            Trigger loTrigger = null;
                            String lsCronExpression = loSerCronBean.getIndCronExpression();
                            if(lsCronExpression != null){
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteParrillasCron.class).build();
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loSerCronBean.getIndBeginSchedule() == null ? "08:00" : 
                                        loSerCronBean.getIndBeginSchedule();
                                    String lsFin = 
                                        loSerCronBean.getAttribute1() == null ? "23:50" : 
                                        loSerCronBean.getAttribute1();
                                    String lsEvery =  
                                        loSerCronBean.getIndValTypeSchedule() == null ? "23" : 
                                        loSerCronBean.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                }        
                                String lsUserName = loSerCronBean.getAttribute14();
                                Integer liIdUser = loSerCronBean.getNumLastUpdateBy();
                                String lsIdService = String.valueOf(loSerCronBean.getIdService());
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", lsIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(liIdUser)); 
                                loJobDataMap.put("lsUserName", lsUserName); 
                                loJobDataMap.put("lsIdRequestPgr", "0");       
                                loJobDataMap.put("lsTypeRequest", "load");
                                loScheduler.scheduleJob(loJob, loTrigger);                        
                                loScheduler.start();
                            }
                        } catch (Exception loEx) {
                            System.out.println("Error al inicializar tareas " + loEx.getMessage());                    
                        } 
                    }
                }
            }        
            
            System.out.println("------- Al final los crones en  ejecucion son: ------------");
            System.out.println("***************************************************************************************");
            try {
                scheduler = new StdSchedulerFactory().getScheduler();            
                for (String groupName : scheduler.getJobGroupNames()) {
                 for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                  String jobName = jobKey.getName();
                  String jobGroup = jobKey.getGroup();
            
                  //get job's trigger
                  List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                  Date nextFireTime = triggers.get(0).getNextFireTime(); 
            
                    System.out.println("[jobName] : " + jobName + " [groupName] : "
                        + jobGroup + " - " + nextFireTime);
            
                  }    
                }
            } catch (SchedulerException e) {
                System.out.println("Error al listar crones "+e.getMessage());               
            }   
            
            FacesMessage loMsg =
                new FacesMessage("Crones Reiniciciados Satisfactoriamente");
            loMsg.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        }else{
            FacesMessage loMsg =
                new FacesMessage(lsMessage);
            loMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        }
        new UtilFaces().hidePopup(getPoPopupRstCronService());
        return null;
    }

    public String showResetCronsAction() {
        new UtilFaces().showPopup(getPoPopupRstCronService());
        return null;
    }
    
    public String cancelResetCronAction() {
        new UtilFaces().hidePopup(getPoPopupRstCronService());
        return null;
    }  
    
    public boolean proccessExecuting(){
        boolean lbRes = false;
        com.televisa.comer.integration.ws.model.daos.EntityMappedDao loEntityMappedDao = 
            new com.televisa.comer.integration.ws.model.daos.EntityMappedDao();
        Integer liRes = loEntityMappedDao.validateExecutionVtaTradicional();
        System.out.println("Existen procesos en ejecucion? ["+liRes+"]");
        if(liRes > 0){
            lbRes = true;   
        }
        return lbRes;
    }

    /********************** SETTERS AND GETTERS *****************************/

    public void setPoNomServicioCron(RichInputText poNomServicioCron) {
        this.poNomServicioCron = poNomServicioCron;
    }

    public RichInputText getPoNomServicioCron() {
        return poNomServicioCron;
    }
   
    public void setPoServiceToCron(RichLink poServiceToCron) {
        this.poServiceToCron = poServiceToCron;
    }

    public RichLink getPoServiceToCron() {
        return poServiceToCron;
    }

    public void setPoFilterDesServiceSel(RichSelectOneChoice poFilterDesServiceSel) {
        this.poFilterDesServiceSel = poFilterDesServiceSel;
    }

    public RichSelectOneChoice getPoFilterDesServiceSel() {
        return poFilterDesServiceSel;
    }

    public void setPoServiceToCronStatus(RichLink poServiceToCronStatus) {
        this.poServiceToCronStatus = poServiceToCronStatus;
    }

    public RichLink getPoServiceToCronStatus() {
        return poServiceToCronStatus;
    }

    public void setPoFilterServiceSel(RichSelectOneChoice poFilterServiceSel) {
        this.poFilterServiceSel = poFilterServiceSel;
    }

    public RichSelectOneChoice getPoFilterServiceSel() {
        return poFilterServiceSel;
    }

    public void setPoDeadLineHoras(RichInputNumberSpinbox poDeadLineHoras) {
        this.poDeadLineHoras = poDeadLineHoras;
    }

    public RichInputNumberSpinbox getPoDeadLineHoras() {
        return poDeadLineHoras;
    }

    public void setPoDeadLineMinutos(RichInputNumberSpinbox poDeadLineMinutos) {
        this.poDeadLineMinutos = poDeadLineMinutos;
    }

    public RichInputNumberSpinbox getPoDeadLineMinutos() {
        return poDeadLineMinutos;
    }

    public void setPoDeadLineMinHoras(RichInputNumberSpinbox poDeadLineMinHoras) {
        this.poDeadLineMinHoras = poDeadLineMinHoras;
    }

    public RichInputNumberSpinbox getPoDeadLineMinHoras() {
        return poDeadLineMinHoras;
    }

    public void setPoDeadLineMinMinutos(RichInputNumberSpinbox poDeadLineMinMinutos) {
        this.poDeadLineMinMinutos = poDeadLineMinMinutos;
    }

    public RichInputNumberSpinbox getPoDeadLineMinMinutos() {
        return poDeadLineMinMinutos;
    }

    public void setPoIdCronConfiguration(RichInputText poIdCronConfiguration) {
        this.poIdCronConfiguration = poIdCronConfiguration;
    }

    public RichInputText getPoIdCronConfiguration() {
        return poIdCronConfiguration;
    }

    public void setPoPopupDeleteCronService(RichPopup poPopupDeleteCronService) {
        this.poPopupDeleteCronService = poPopupDeleteCronService;
    }

    public RichPopup getPoPopupDeleteCronService() {
        return poPopupDeleteCronService;
    }

    public void setPoDeleteCronMsgLbl(RichPanelLabelAndMessage poDeleteCronMsgLbl) {
        this.poDeleteCronMsgLbl = poDeleteCronMsgLbl;
    }

    public RichPanelLabelAndMessage getPoDeleteCronMsgLbl() {
        return poDeleteCronMsgLbl;
    }

    public void setPoDeleteCronIdBinding(RichOutputText poDeleteCronIdBinding) {
        this.poDeleteCronIdBinding = poDeleteCronIdBinding;
    }

    public RichOutputText getPoDeleteCronIdBinding() {
        return poDeleteCronIdBinding;
    }

    public void setPoBtnSaveCronService(RichButton poBtnSaveCronService) {
        this.poBtnSaveCronService = poBtnSaveCronService;
    }

    public RichButton getPoBtnSaveCronService() {
        return poBtnSaveCronService;
    }

    public void setPoBtnDeleteCronService(RichButton poBtnDeleteCronService) {
        this.poBtnDeleteCronService = poBtnDeleteCronService;
    }

    public RichButton getPoBtnDeleteCronService() {
        return poBtnDeleteCronService;
    }

    public void setPoTabDias(RichShowDetailItem poTabDias) {
        this.poTabDias = poTabDias;
    }

    public RichShowDetailItem getPoTabDias() {
        return poTabDias;
    }

    public void setPoTabMeses(RichShowDetailItem poTabMeses) {
        this.poTabMeses = poTabMeses;
    }

    public RichShowDetailItem getPoTabMeses() {
        return poTabMeses;
    }

    public void setPoTabSemanas(RichShowDetailItem poTabSemanas) {
        this.poTabSemanas = poTabSemanas;
    }

    public RichShowDetailItem getPoTabSemanas() {
        return poTabSemanas;
    }

    public void setPoOperation(RichInputText poOperation) {
        this.poOperation = poOperation;
    }

    public RichInputText getPoOperation() {
        return poOperation;
    }
    public void setPoCadaMinutos(RichInputNumberSpinbox poCadaMinutos) {
        this.poCadaMinutos = poCadaMinutos;
    }

    public RichInputNumberSpinbox getPoCadaMinutos() {
        return poCadaMinutos;
    }

    public void setPoLunMinutos(RichSelectBooleanCheckbox poLunMinutos) {
        this.poLunMinutos = poLunMinutos;
    }

    public RichSelectBooleanCheckbox getPoLunMinutos() {
        return poLunMinutos;
    }

    public void setPoMarMinutos(RichSelectBooleanCheckbox poMarMinutos) {
        this.poMarMinutos = poMarMinutos;
    }

    public RichSelectBooleanCheckbox getPoMarMinutos() {
        return poMarMinutos;
    }

    public void setPoMieMinutos(RichSelectBooleanCheckbox poMieMinutos) {
        this.poMieMinutos = poMieMinutos;
    }

    public RichSelectBooleanCheckbox getPoMieMinutos() {
        return poMieMinutos;
    }

    public void setPoJueMinutos(RichSelectBooleanCheckbox poJueMinutos) {
        this.poJueMinutos = poJueMinutos;
    }

    public RichSelectBooleanCheckbox getPoJueMinutos() {
        return poJueMinutos;
    }

    public void setPoVieMinutos(RichSelectBooleanCheckbox poVieMinutos) {
        this.poVieMinutos = poVieMinutos;
    }

    public RichSelectBooleanCheckbox getPoVieMinutos() {
        return poVieMinutos;
    }

    public void setPoSabMinutos(RichSelectBooleanCheckbox poSabMinutos) {
        this.poSabMinutos = poSabMinutos;
    }

    public RichSelectBooleanCheckbox getPoSabMinutos() {
        return poSabMinutos;
    }

    public void setPoDomMinutos(RichSelectBooleanCheckbox poDomMinutos) {
        this.poDomMinutos = poDomMinutos;
    }

    public RichSelectBooleanCheckbox getPoDomMinutos() {
        return poDomMinutos;
    }

    public void setPoBeginHoras(RichInputNumberSpinbox poBeginHoras) {
        this.poBeginHoras = poBeginHoras;
    }

    public RichInputNumberSpinbox getPoBeginHoras() {
        return poBeginHoras;
    }

    public void setPoIdServiceSelected(RichInputText poIdServiceSelected) {
        this.poIdServiceSelected = poIdServiceSelected;
    }

    public RichInputText getPoIdServiceSelected() {
        return poIdServiceSelected;
    }

    public void setPoBeginMinutes(RichInputNumberSpinbox poBeginMinutes) {
        this.poBeginMinutes = poBeginMinutes;
    }

    public RichInputNumberSpinbox getPoBeginMinutes() {
        return poBeginMinutes;
    }

    public void setPoRadioBegin(RichSelectBooleanRadio poRadioBegin) {
        this.poRadioBegin = poRadioBegin;
    }

    public RichSelectBooleanRadio getPoRadioBegin() {
        return poRadioBegin;
    }

/*
    public void setPoRadioHourEvery(RichSelectBooleanRadio poRadioHourEvery) {
        this.poRadioHourEvery = poRadioHourEvery;
    }

    public RichSelectBooleanRadio getPoRadioHourEvery() {
        return poRadioHourEvery;
    }

    public void setPoRadioHourBegin(RichSelectBooleanRadio poRadioHourBegin) {
        this.poRadioHourBegin = poRadioHourBegin;
    }

    public RichSelectBooleanRadio getPoRadioHourBegin() {
        return poRadioHourBegin;
    }
*/

    public void setPoRadioDayEvery(RichSelectBooleanRadio toRadioDayEvery) {
        this.poRadioDayEvery = toRadioDayEvery;
    }

    public RichSelectBooleanRadio getPoRadioDayEvery() {
        return poRadioDayEvery;
    }

    public void setPoCadaDias(RichInputNumberSpinbox poCadaDias) {
        this.poCadaDias = poCadaDias;
    }

    public RichInputNumberSpinbox getPoCadaDias() {
        return poCadaDias;
    }

    public void setPoRadioDayBegin(RichSelectBooleanRadio poRadioDayBegin) {
        this.poRadioDayBegin = poRadioDayBegin;
    }

    public RichSelectBooleanRadio getPoRadioDayBegin() {
        return poRadioDayBegin;
    }

    public void setPoBeginDias(RichInputNumberSpinbox poBeginDias) {
        this.poBeginDias = poBeginDias;
    }

    public RichInputNumberSpinbox getPoBeginDias() {
        return poBeginDias;
    }

    


    public void setPoBeginDayMinutes(RichInputNumberSpinbox poBeginDayMinutes) {
        this.poBeginDayMinutes = poBeginDayMinutes;
    }

    public RichInputNumberSpinbox getPoBeginDayMinutes() {
        return poBeginDayMinutes;
    }


    public void setPoLunDias(RichSelectBooleanCheckbox poLunDias) {
        this.poLunDias = poLunDias;
    }

    public RichSelectBooleanCheckbox getPoLunDias() {
        return poLunDias;
    }

    public void setPoMarDias(RichSelectBooleanCheckbox poMarDias) {
        this.poMarDias = poMarDias;
    }

    public RichSelectBooleanCheckbox getPoMarDias() {
        return poMarDias;
    }

    public void setPoMieDias(RichSelectBooleanCheckbox poMieDias) {
        this.poMieDias = poMieDias;
    }

    public RichSelectBooleanCheckbox getPoMieDias() {
        return poMieDias;
    }

    public void setPoJueDias(RichSelectBooleanCheckbox poJueDias) {
        this.poJueDias = poJueDias;
    }

    public RichSelectBooleanCheckbox getPoJueDias() {
        return poJueDias;
    }

    public void setPoVieDias(RichSelectBooleanCheckbox poVieDias) {
        this.poVieDias = poVieDias;
    }

    public RichSelectBooleanCheckbox getPoVieDias() {
        return poVieDias;
    }

    public void setPoSabDias(RichSelectBooleanCheckbox poSabDias) {
        this.poSabDias = poSabDias;
    }

    public RichSelectBooleanCheckbox getPoSabDias() {
        return poSabDias;
    }

    public void setPoDomDias(RichSelectBooleanCheckbox poDomDias) {
        this.poDomDias = poDomDias;
    }

    public RichSelectBooleanCheckbox getPoDomDias() {
        return poDomDias;
    }

    public void setPoBeginSemanas(RichInputNumberSpinbox poBeginSemanas) {
        this.poBeginSemanas = poBeginSemanas;
    }

    public RichInputNumberSpinbox getPoBeginSemanas() {
        return poBeginSemanas;
    }

    public void setPoBeginDaySemanas(RichInputNumberSpinbox poBeginDaySemanas) {
        this.poBeginDaySemanas = poBeginDaySemanas;
    }

    public RichInputNumberSpinbox getPoBeginDaySemanas() {
        return poBeginDaySemanas;
    }

    public void setPoLunWeek(RichSelectBooleanCheckbox poLunWeek) {
        this.poLunWeek = poLunWeek;
    }

    public RichSelectBooleanCheckbox getPoLunWeek() {
        return poLunWeek;
    }

    public void setPoMarWeek(RichSelectBooleanCheckbox poMarWeek) {
        this.poMarWeek = poMarWeek;
    }

    public RichSelectBooleanCheckbox getPoMarWeek() {
        return poMarWeek;
    }

    public void setPoMieWeek(RichSelectBooleanCheckbox poMieWeek) {
        this.poMieWeek = poMieWeek;
    }

    public RichSelectBooleanCheckbox getPoMieWeek() {
        return poMieWeek;
    }

    public void setPoJueWeek(RichSelectBooleanCheckbox poJueWeek) {
        this.poJueWeek = poJueWeek;
    }

    public RichSelectBooleanCheckbox getPoJueWeek() {
        return poJueWeek;
    }

    public void setPoVieWeek(RichSelectBooleanCheckbox poVieWeek) {
        this.poVieWeek = poVieWeek;
    }

    public RichSelectBooleanCheckbox getPoVieWeek() {
        return poVieWeek;
    }

    public void setPoSabWeek(RichSelectBooleanCheckbox poSabWeek) {
        this.poSabWeek = poSabWeek;
    }

    public RichSelectBooleanCheckbox getPoSabWeek() {
        return poSabWeek;
    }

    public void setPoDomWeek(RichSelectBooleanCheckbox poDomWeek) {
        this.poDomWeek = poDomWeek;
    }

    public RichSelectBooleanCheckbox getPoDomWeek() {
        return poDomWeek;
    }

    public void setPoSemanasHoraIni(RichInputNumberSpinbox poSemanasHoraIni) {
        this.poSemanasHoraIni = poSemanasHoraIni;
    }

    public RichInputNumberSpinbox getPoSemanasHoraIni() {
        return poSemanasHoraIni;
    }

    public void setPoBeginSemanasMinutoIni(RichInputNumberSpinbox poBeginSemanasMinutoIni) {
        this.poBeginSemanasMinutoIni = poBeginSemanasMinutoIni;
    }

    public RichInputNumberSpinbox getPoBeginSemanasMinutoIni() {
        return poBeginSemanasMinutoIni;
    }

    public void setPoDayOfSelected(RichSelectOneChoice poDayOfSelected) {
        this.poDayOfSelected = poDayOfSelected;
    }

    public RichSelectOneChoice getPoDayOfSelected() {
        return poDayOfSelected;
    }

    public void setPoRadioTheDay(RichSelectBooleanRadio poRadioTheDay) {
        this.poRadioTheDay = poRadioTheDay;
    }

    public RichSelectBooleanRadio getPoRadioTheDay() {
        return poRadioTheDay;
    }

    public void setPoMesTheDay(RichInputNumberSpinbox poMesTheDay) {
        this.poMesTheDay = poMesTheDay;
    }

    public RichInputNumberSpinbox getPoMesTheDay() {
        return poMesTheDay;
    }
    public void setPoMesEvery(RichInputNumberSpinbox poMesEvery) {
        this.poMesEvery = poMesEvery;
    }

    public RichInputNumberSpinbox getPoMesEvery() {
        return poMesEvery;
    }

    public void setPoRadioTheDayOfWeek(RichSelectBooleanRadio poRadioTheDayOfWeek) {
        this.poRadioTheDayOfWeek = poRadioTheDayOfWeek;
    }

    public RichSelectBooleanRadio getPoRadioTheDayOfWeek() {
        return poRadioTheDayOfWeek;
    }

    public void setPoDayOfWeekSelected(RichSelectOneChoice poDayOfWeekSelected) {
        this.poDayOfWeekSelected = poDayOfWeekSelected;
    }

    public RichSelectOneChoice getPoDayOfWeekSelected() {
        return poDayOfWeekSelected;
    }

    public void setPoMesEveryOf(RichInputNumberSpinbox poMesEveryOf) {
        this.poMesEveryOf = poMesEveryOf;
    }

    public RichInputNumberSpinbox getPoMesEveryOf() {
        return poMesEveryOf;
    }
    

    public void setPoMesHoraIni(RichInputNumberSpinbox poMesHoraIni) {
        this.poMesHoraIni = poMesHoraIni;
    }

    public RichInputNumberSpinbox getPoMesHoraIni() {
        return poMesHoraIni;
    }

    public void setPoMesMinutoIni(RichInputNumberSpinbox poMesMinutoIni) {
        this.poMesMinutoIni = poMesMinutoIni;
    }

    public RichInputNumberSpinbox getPoMesMinutoIni() {
        return poMesMinutoIni;
    }

    public void setPoCadaHoras(RichInputNumberSpinbox poCadaHoras) {
        this.poCadaHoras = poCadaHoras;
    }

    public RichInputNumberSpinbox getPoCadaHoras() {
        return poCadaHoras;
    }

    public void setPoLunHoras(RichSelectBooleanCheckbox poLunHoras) {
        this.poLunHoras = poLunHoras;
    }

    public RichSelectBooleanCheckbox getPoLunHoras() {
        return poLunHoras;
    }

    public void setPoMarHoras(RichSelectBooleanCheckbox poMarHoras) {
        this.poMarHoras = poMarHoras;
    }

    public RichSelectBooleanCheckbox getPoMarHoras() {
        return poMarHoras;
    }

    public void setPoMieHoras(RichSelectBooleanCheckbox poMieHoras) {
        this.poMieHoras = poMieHoras;
    }

    public RichSelectBooleanCheckbox getPoMieHoras() {
        return poMieHoras;
    }

    public void setPoJueHoras(RichSelectBooleanCheckbox poJueHoras) {
        this.poJueHoras = poJueHoras;
    }

    public RichSelectBooleanCheckbox getPoJueHoras() {
        return poJueHoras;
    }

    public void setPoVieHoras(RichSelectBooleanCheckbox poVieHoras) {
        this.poVieHoras = poVieHoras;
    }

    public RichSelectBooleanCheckbox getPoVieHoras() {
        return poVieHoras;
    }

    public void setPoSabHoras(RichSelectBooleanCheckbox poSabHoras) {
        this.poSabHoras = poSabHoras;
    }

    public RichSelectBooleanCheckbox getPoSabHoras() {
        return poSabHoras;
    }

    public void setPoDomHoras(RichSelectBooleanCheckbox poDomHoras) {
        this.poDomHoras = poDomHoras;
    }

    public RichSelectBooleanCheckbox getPoDomHoras() {
        return poDomHoras;
    }
    
    public void setPoIdTabSelected(RichInputText poIdTabSelected) {
        this.poIdTabSelected = poIdTabSelected;
    }

    public RichInputText getPoIdTabSelected() {
        return poIdTabSelected;
    }
    
    public void setPoTabMinutos(RichShowDetailItem poTabMinutos) {
        this.poTabMinutos = poTabMinutos;
    }

    public RichShowDetailItem getPoTabMinutos() {
        return poTabMinutos;
    }

    public void setPoTabHoras(RichShowDetailItem poTabHoras) {
        this.poTabHoras = poTabHoras;
    }

    public RichShowDetailItem getPoTabHoras() {
        return poTabHoras;
    }

    public void setPoPanelTabbed(RichPanelTabbed poPanelTabbed) {
        this.poPanelTabbed = poPanelTabbed;
    }

    public RichPanelTabbed getPoPanelTabbed() {
        return poPanelTabbed;
    }
    public void setPoActionServiceBinding(RichOutputText poActionServiceBinding) {
        this.poActionServiceBinding = poActionServiceBinding;
    }

    public RichOutputText getPoActionServiceBinding() {
        return poActionServiceBinding;
    }

    public void setPoPopPanelExeBinding(RichPanelWindow poPopPanelExeBinding) {
        this.poPopPanelExeBinding = poPopPanelExeBinding;
    }

    public RichPanelWindow getPoPopPanelExeBinding() {
        return poPopPanelExeBinding;
    }

    public void setPoPopUpdateDesService(RichInputText poPopUpdateDesService) {
        this.poPopUpdateDesService = poPopUpdateDesService;
    }

    public RichInputText getPoPopUpdateDesService() {
        return poPopUpdateDesService;
    }

    public void setPoSaveNomServiceSel(RichSelectOneChoice poSaveNomServiceSel) {
        this.poSaveNomServiceSel = poSaveNomServiceSel;
    }

    public RichSelectOneChoice getPoSaveNomServiceSel() {
        return poSaveNomServiceSel;
    }

    public void setPoPopupExecuteService(RichPopup poPopupExecuteService) {
        this.poPopupExecuteService = poPopupExecuteService;
    }

    public RichPopup getPoPopupExecuteService() {
        return poPopupExecuteService;
    }

    public void setPoExecuteMsgLbl(RichPanelLabelAndMessage poExecuteMsgLbl) {
        this.poExecuteMsgLbl = poExecuteMsgLbl;
    }

    public RichPanelLabelAndMessage getPoExecuteMsgLbl() {
        return poExecuteMsgLbl;
    }

    public void setPoExecuteIdBinding(RichOutputText poExecuteIdBinding) {
        this.poExecuteIdBinding = poExecuteIdBinding;
    }

    public RichOutputText getPoExecuteIdBinding() {
        return poExecuteIdBinding;
    }
    public void setPoPopupSaveService(RichPopup poPopupSaveService) {
        this.poPopupSaveService = poPopupSaveService;
    }

    public RichPopup getPoPopupSaveService() {
        return poPopupSaveService;
    }
        
    public void setPoPopSaveNomService(RichInputText poPopSaveNomService) {
        this.poPopSaveNomService = poPopSaveNomService;
    }

    public RichInputText getPoPopSaveNomService() {
        return poPopSaveNomService;
    }

    public void setPoPopSaveWsdl(RichInputText poPopSaveWsdl) {
        this.poPopSaveWsdl = poPopSaveWsdl;
    }

    public RichInputText getPoPopSaveWsdl() {
        return poPopSaveWsdl;
    }

    public void setPoPopSaveSystem(RichSelectOneChoice poPopSaveSystem) {
        this.poPopSaveSystem = poPopSaveSystem;
    }

    public RichSelectOneChoice getPoPopSaveSystem() {
        return poPopSaveSystem;
    }

    public void setPoPopSaveSystemOr(RichSelectOneChoice poPopSaveSystemOr) {
        this.poPopSaveSystemOr = poPopSaveSystemOr;
    }

    public RichSelectOneChoice getPoPopSaveSystemOr() {
        return poPopSaveSystemOr;
    }

    public void setPoPopSaveSystemDes(RichSelectOneChoice poPopSaveSystemDes) {
        this.poPopSaveSystemDes = poPopSaveSystemDes;
    }

    public RichSelectOneChoice getPoPopSaveSystemDes() {
        return poPopSaveSystemDes;
    }

    public void setPoPopSaveAsyn(RichSelectBooleanCheckbox poPopSaveAsyn) {
        this.poPopSaveAsyn = poPopSaveAsyn;
    }

    public RichSelectBooleanCheckbox getPoPopSaveAsyn() {
        return poPopSaveAsyn;
    }

    public void setPoPopSaveStatus(RichSelectBooleanCheckbox poPopSaveStatus) {
        this.poPopSaveStatus = poPopSaveStatus;
    }

    public RichSelectBooleanCheckbox getPoPopSaveStatus() {
        return poPopSaveStatus;
    }
    
    public void setPoPopupDeleteService(RichPopup poPopupDeleteService) {
        this.poPopupDeleteService = poPopupDeleteService;
    }

    public RichPopup getPoPopupDeleteService() {
        return poPopupDeleteService;
    }

    public void setPoDeleteMsgLbl(RichPanelLabelAndMessage poDeleteMsgLbl) {
        this.poDeleteMsgLbl = poDeleteMsgLbl;
    }

    public RichPanelLabelAndMessage getPoDeleteMsgLbl() {
        return poDeleteMsgLbl;
    }


    public void setPoTblWebServices(RichTable poTblWebServices) {
        this.poTblWebServices = poTblWebServices;
    }

    public RichTable getPoTblWebServices() {
        return poTblWebServices;
    }
    
    public void setPoPopupUpdateService(RichPopup poPopupUpdateService) {
        this.poPopupUpdateService = poPopupUpdateService;
    }

    public RichPopup getPoPopupUpdateService() {
        return poPopupUpdateService;
    }

    public void setPoPopUpdateIdService(RichInputText poPopUpdateIdService) {
        this.poPopUpdateIdService = poPopUpdateIdService;
    }

    public RichInputText getPoPopUpdateIdService() {
        return poPopUpdateIdService;
    }

    public void setPoPopUpdateNomService(RichInputText poPopUpdateNomService) {
        this.poPopUpdateNomService = poPopUpdateNomService;
    }

    public RichInputText getPoPopUpdateNomService() {
        return poPopUpdateNomService;
    }

    public void setPoPopUpdateWsdl(RichInputText poPopUpdateWsdl) {
        this.poPopUpdateWsdl = poPopUpdateWsdl;
    }

    public RichInputText getPoPopUpdateWsdl() {
        return poPopUpdateWsdl;
    }

    public void setPoPopUpdateSystem(RichSelectOneChoice poPopUpdateSystem) {
        this.poPopUpdateSystem = poPopUpdateSystem;
    }

    public RichSelectOneChoice getPoPopUpdateSystem() {
        return poPopUpdateSystem;
    }

    public void setPoPopUpdateSystemOr(RichSelectOneChoice poPopUpdateSystemOr) {
        this.poPopUpdateSystemOr = poPopUpdateSystemOr;
    }

    public RichSelectOneChoice getPoPopUpdateSystemOr() {
        return poPopUpdateSystemOr;
    }

    public void setPoPopUpdateSystemDes(RichSelectOneChoice poPopUpdateSystemDes) {
        this.poPopUpdateSystemDes = poPopUpdateSystemDes;
    }

    public RichSelectOneChoice getPoPopUpdateSystemDes() {
        return poPopUpdateSystemDes;
    }

    public void setPoPopUpdateAsyn(RichSelectBooleanCheckbox poPopUpdateAsyn) {
        this.poPopUpdateAsyn = poPopUpdateAsyn;
    }

    public RichSelectBooleanCheckbox getPoPopUpdateAsyn() {
        return poPopUpdateAsyn;
    }

    public void setPoPopUpdateStatus(RichSelectBooleanCheckbox poPopUpdateStatus) {
        this.poPopUpdateStatus = poPopUpdateStatus;
    }

    public RichSelectBooleanCheckbox getPoPopUpdateStatus() {
        return poPopUpdateStatus;
    }

    public void setPoFilterSystem(RichSelectOneChoice poFilterSystem) {
        this.poFilterSystem = poFilterSystem;
    }

    public RichSelectOneChoice getPoFilterSystem() {
        return poFilterSystem;
    }

    public void setPoFilterSystemOr(RichSelectOneChoice poFilterSystemOr) {
        this.poFilterSystemOr = poFilterSystemOr;
    }

    public RichSelectOneChoice getPoFilterSystemOr() {
        return poFilterSystemOr;
    }

    public void setPoFilterSystemDes(RichSelectOneChoice poFilterSystemDes) {
        this.poFilterSystemDes = poFilterSystemDes;
    }

    public RichSelectOneChoice getPoFilterSystemDes() {
        return poFilterSystemDes;
    }
    
    public void setPoFilterWsdl(RichInputText poFilterWsdl) {
        this.poFilterWsdl = poFilterWsdl;
    }

    public RichInputText getPoFilterWsdl() {
        return poFilterWsdl;
    }

    public void setPoFilterService(RichInputText poFilterService) {
        this.poFilterService = poFilterService;
    }

    public RichInputText getPoFilterService() {
        return poFilterService;
    }
    
    public void setPoFilterAsynSel(RichSelectOneChoice poFilterAsynSel) {
        this.poFilterAsynSel = poFilterAsynSel;
    }

    public RichSelectOneChoice getPoFilterAsynSel() {
        return poFilterAsynSel;
    }

    public void setPoFilterStatusSel(RichSelectOneChoice poFilterStatusSel) {
        this.poFilterStatusSel = poFilterStatusSel;
    }

    public RichSelectOneChoice getPoFilterStatusSel() {
        return poFilterStatusSel;
    }

    public void setPoDeleteIdBinding(RichOutputText poDeleteIdBinding) {
        this.poDeleteIdBinding = poDeleteIdBinding;
    }

    public RichOutputText getPoDeleteIdBinding() {
        return poDeleteIdBinding;
    }
    
    public void setPoExecuteNomBinding(RichOutputText poExecuteNomBinding) {
        this.poExecuteNomBinding = poExecuteNomBinding;
    }

    public RichOutputText getPoExecuteNomBinding() {
        return poExecuteNomBinding;
    }


    public void setPoPopupSaveCronService(RichPopup poPopupSaveCronService) {
        this.poPopupSaveCronService = poPopupSaveCronService;
    }

    public RichPopup getPoPopupSaveCronService() {
        return poPopupSaveCronService;
    }

    public void setPoSaveCronMsgLbl(RichPanelLabelAndMessage poSaveCronMsgLbl) {
        this.poSaveCronMsgLbl = poSaveCronMsgLbl;
    }

    public RichPanelLabelAndMessage getPoSaveCronMsgLbl() {
        return poSaveCronMsgLbl;
    }


    public void setPoBeginHorasMin(RichInputNumberSpinbox poBeginHorasMin) {
        this.poBeginHorasMin = poBeginHorasMin;
    }

    public RichInputNumberSpinbox getPoBeginHorasMin() {
        return poBeginHorasMin;
    }

    public void setPoBeginMinutesMin(RichInputNumberSpinbox poBeginMinutesMin) {
        this.poBeginMinutesMin = poBeginMinutesMin;
    }

    public RichInputNumberSpinbox getPoBeginMinutesMin() {
        return poBeginMinutesMin;
    }

    public void setPoBtnResetCronService(RichButton poBtnResetCronService) {
        this.poBtnResetCronService = poBtnResetCronService;
    }

    public RichButton getPoBtnResetCronService() {
        return poBtnResetCronService;
    }
    
    public void setPoPopupRstCronService(RichPopup poPopupRstCronService) {
        this.poPopupRstCronService = poPopupRstCronService;
    }

    public RichPopup getPoPopupRstCronService() {
        return poPopupRstCronService;
    }
   

}
