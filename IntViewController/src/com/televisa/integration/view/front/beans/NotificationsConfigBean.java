/**
* Project: Paradigm - eVeTV Integration
*
* File: NotificationsConfigBean.java
*
* Created on:  Septiembre 6, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package com.televisa.integration.view.front.beans;

import com.televisa.integration.model.AppModuleIntergrationImpl;
import com.televisa.integration.view.front.beans.types.SelectOneItemBean;
import com.televisa.integration.view.front.daos.ViewObjectDao;
import com.televisa.integration.view.front.util.ADFUtils;
import com.televisa.integration.view.front.util.UtilFaces;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlHierNodeBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.client.Configuration;

/** Esta clase es un bean que enlaza la pantalla de Configuracion de Notificaciones<br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Septiembre 14, 2017, 12:00 pm
 */
public class NotificationsConfigBean {
    private RichSelectOneChoice       poFilterServiceSel;
    private RichInputText             poFilterIdService;
    private RichSelectOneChoice       poFilterProcessSel;
    private RichSelectOneChoice       poFilterUserGroupSel;
    private RichInputText             poFilterIdUserGroup;
    private RichInputText             poFilterSubject;
    private RichInputText             poFilterMessage;
    private RichSelectOneChoice       poFilterStatusSel;
    private RichTable                 poTblNotifications;
    private RichPopup                 poPopupSaveNotification;
    private RichSelectOneChoice       poSaveServiceSel;
    private RichInputText             poSaveIdService;
    private RichSelectOneChoice       poSaveProcessSel;
    private RichSelectOneChoice       poSaveUserGroupSel;
    private RichInputText             poSaveIdUserGroup;
    private RichInputText             poSaveSubject;
    private RichInputText             poSaveMessage;
    private RichPopup                 poPopupDeleteNotification;
    private RichOutputText            poDeleteIdBinding;
    private RichPopup                 poPopupUpdateNotification;
    private RichInputText             poUpdateIdService;
    private RichInputText             poUpdateService;
    private RichSelectOneChoice       poUpdateProcessSel;
    private RichSelectOneChoice       poUpdateUserGroupSel;
    private RichInputText             poUpdateIdUserGroup;
    private RichInputText             poUpdateSubject;
    private RichInputText             poUpdateMessage;
    private RichSelectBooleanCheckbox poSaveStatus;
    private RichSelectBooleanCheckbox poUpdateStatus;
    private RichInputText             poUpdateNotification;
    private RichInputText             poUpdateIdProcess;
    private RichPanelLabelAndMessage  poDeleteMsgLbl;
    private RichInputText             poSaveIdProcess;    
    String                            lsEntityView = "EvetvIntNotificationsTblVwView1";
    String                            lsEntityIterator = "EvetvIntNotificationsTblVwView1Iterator";
    String                            lsAppModule = "AppModuleIntergrationDataControl";
    String                            lsAmDef = "com.televisa.integration.model.AppModuleIntergrationImpl";
    String                            lsConfig = "AppModuleIntergrationLocal";

    /**
     * Reinicia los valores de busqueda
     * @autor Jorge Luis Bautista Santiago
     * @param toActionEvent
     * @return void
     */
    public void resetFilterValues(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        getPoFilterIdService().setValue(null);
        getPoFilterServiceSel().setValue(null);
        getPoFilterIdUserGroup().setValue(null);
        getPoFilterUserGroupSel().setValue(null);
        getPoFilterMessage().setValue(null);
        getPoFilterProcessSel().setValue(null);
        getPoFilterServiceSel().setValue(null);
        getPoFilterStatusSel().setValue(null);
        getPoFilterSubject().setValue(null);
        getPoFilterUserGroupSel().setValue(null);
    }

    /**
     * Reinicia la tabla al estado inicial
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String refreshMainTable() {
        new UtilFaces().refreshTableWhereIterator("1 = 1 ",
                                                  lsEntityIterator,
                                                  getPoTblNotifications());        
        return null;
    }

    /**
     * Ejecuta la busqueda en base a los parametros
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String searchFilterNotificationsAction() {
        String lsQuery = " 1 = 1 ";
        String lsIdService = 
            getPoFilterServiceSel().getValue() == null ? "" : 
            getPoFilterServiceSel().getValue().toString();   
        if(!lsIdService.equalsIgnoreCase("")){
            lsQuery += " AND ID_SERVICE = "+lsIdService;
        }        
        String lsProcess = 
            getPoFilterProcessSel().getValue() == null ? "" : 
            getPoFilterProcessSel().getValue().toString();          
        
        if(!lsProcess.equalsIgnoreCase("")){
            lsQuery += " AND IND_PROCESS = '"+lsProcess+"'";
        }        
        String lsUserGroup = 
            getPoFilterUserGroupSel().getValue() == null ? "": getPoFilterUserGroupSel().getValue().toString();       
        
        if(!lsUserGroup.equalsIgnoreCase("")){
            lsQuery += " AND IND_USERS_GROUP = '" + lsUserGroup + "'";
        }
        String lsFilterSubject = 
            getPoFilterSubject().getValue() == null ? "" : 
            getPoFilterSubject().getValue().toString();    
        if(!lsFilterSubject.equalsIgnoreCase("")){
            lsQuery += " AND UPPER(IND_SUBJECT) LIKE '" + 
                       lsFilterSubject.toUpperCase() + "%'";
        }
        String lsFilterMessage = 
            getPoFilterMessage().getValue() == null ? "" : 
            getPoFilterMessage().getValue().toString();      
        if(!lsFilterMessage.equalsIgnoreCase("")){
            lsQuery += " AND UPPER(IND_MESSAGE) LIKE '" + 
                       lsFilterMessage.toUpperCase() + "%'";
        }
        String lsIndEstatus = 
            getPoFilterStatusSel().getValue() == null ? "" : 
            getPoFilterStatusSel().getValue().toString();        
        if(!lsIndEstatus.equalsIgnoreCase("")){
            lsQuery += " AND IND_ESTATUS = '" + lsIndEstatus + "'";
        }
        new UtilFaces().refreshTableWhereIterator(lsQuery,
                                                  lsEntityIterator,
                                                  getPoTblNotifications()
                                                  );
        return null;
    }

    /**
     * Muestra popup que solicita datos para guardar el parametro
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     * @return String
     */
    public void showSavePopupNotifications(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        getPoSaveIdService().setValue(null);
        getPoSaveIdUserGroup().setValue(null);
        getPoSaveMessage().setValue(null);
        getPoSaveProcessSel().setValue(null);
        getPoSaveServiceSel().setValue(null);
        getPoSaveStatus().setValue(null);
        getPoSaveSubject().setValue(null);
        getPoSaveUserGroupSel().setValue(null);
        new UtilFaces().showPopup(getPoPopupSaveNotification());
    }

    /**
     * Muestra popup que solicita datos para editar parametro
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     * @return void
     */
    public void showEditPopupNotifications(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblNotifications().getSelectedRowData();
        String                   lsIdNotification = 
            loNode.getAttribute("IdNotification") == null ? "" : 
            loNode.getAttribute("IdNotification").toString();                 
        String                   lsIdServicio = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();     
        String                   lsNomServicio = 
            loNode.getAttribute("NomService") == null ? "" : 
            loNode.getAttribute("NomService").toString();   
        String                   lsIndProcess = 
            loNode.getAttribute("IndProcess") == null ? "" : 
            loNode.getAttribute("IndProcess").toString();   
        String                   lsIndProcessNom = 
            loNode.getAttribute("IndProcessNom") == null ? "" : 
            loNode.getAttribute("IndProcessNom").toString();  
        String                   lsIndUsersGroup = 
            loNode.getAttribute("IndUsersGroup") == null ? "" : 
            loNode.getAttribute("IndUsersGroup").toString();  
        String                   lsDesUsersGroup = 
            loNode.getAttribute("DesUsersGroup") == null ? "" : 
            loNode.getAttribute("DesUsersGroup").toString();  
        String                   lsIndSubject = 
            loNode.getAttribute("IndSubject") == null ? "" : 
            loNode.getAttribute("IndSubject").toString();  
        String                   lsIndMessage = 
            loNode.getAttribute("IndMessage") == null ? "" : 
            loNode.getAttribute("IndMessage").toString();          
        String                   lsIndEstatus = 
            loNode.getAttribute("IndEstatus") == null ? "" : 
            loNode.getAttribute("IndEstatus").toString();
        // Settear valores al popup
        getPoUpdateNotification().setValue(lsIdNotification);                
        getPoUpdateIdService().setValue(lsIdServicio);                
        getPoUpdateService().setValue(lsNomServicio);
        getPoUpdateIdProcess().setValue(lsIndProcess);
        getPoUpdateProcessSel().setValue(lsIndProcess);
       
        getPoUpdateIdUserGroup().setValue(lsIndUsersGroup);
        getPoUpdateUserGroupSel().setValue(lsIndUsersGroup);
        
        getPoUpdateSubject().setValue(lsIndSubject);
        getPoUpdateMessage().setValue(lsIndMessage);
        
        if(lsIndEstatus.equalsIgnoreCase("1")){
            getPoUpdateStatus().setSelected(true);
        }else{
            getPoUpdateStatus().setSelected(false);
        }
        new UtilFaces().showPopup(getPoPopupUpdateNotification());
    }

    /**
     * Muestra popup que solicita datos para eliminar el parametro
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     * @return void
     */
    public void showDeletePopupNotifications(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblNotifications().getSelectedRowData();
        
        String                   lsIdNotification = 
            loNode.getAttribute("IdNotification") == null ? "" : 
            loNode.getAttribute("IdNotification").toString();                         
        String                   lsNomServicio = 
            loNode.getAttribute("NomService") == null ? "" : 
            loNode.getAttribute("NomService").toString();           
        String                   lsIndProcessNom = 
            loNode.getAttribute("IndProcessNom") == null ? "" : 
            loNode.getAttribute("IndProcessNom").toString();          
        String                   lsDesUsersGroup = 
            loNode.getAttribute("DesUsersGroup") == null ? "" : 
            loNode.getAttribute("DesUsersGroup").toString();  
        
        getPoDeleteIdBinding().setValue(lsIdNotification);
        getPoDeleteMsgLbl().setLabel("Eliminar a " + lsNomServicio + " : " + 
                                     lsIndProcessNom + " : " + lsDesUsersGroup
                                     );
        new UtilFaces().showPopup(getPoPopupDeleteNotification());
    }

    /**
     * Guarda la configuracion de notificacion
     * @autor Jorge Luis Bautista Santiago  
     * @return void
     */    
    public String saveNotificationAction() {
        Integer lsIdNotification = 
            new ViewObjectDao().getMaxIdParadigm("Notifications") + 1;
        
        String lsIdService = 
            getPoSaveServiceSel().getValue() == null ? "" : 
            getPoSaveServiceSel().getValue().toString();
        String lsIdProcess = 
            getPoSaveProcessSel().getValue() == null ? "" : 
            getPoSaveProcessSel().getValue().toString();  
        String lsIdUsersGroup = 
            getPoSaveUserGroupSel().getValue() == null ? "" : 
            getPoSaveUserGroupSel().getValue().toString();   
        String lsSubject = 
            getPoSaveSubject().getValue() == null ? "" : 
            getPoSaveSubject().getValue().toString();  
        String lsMessage = 
            getPoSaveMessage().getValue() == null ? "" :
            getPoSaveMessage().getValue().toString();  
        String lsIndEstatus = 
            getPoSaveStatus().getValue() == null ? "" :
            getPoSaveStatus().getValue().toString();
        String lsStatusTab = "0";
        if(lsIndEstatus.equalsIgnoreCase("true")){
            lsStatusTab = "1";
        }
        ApplicationModule   loAm = 
            Configuration.createRootApplicationModule(lsAmDef, lsConfig);
        AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
        try{
            loService.insertNotificationsModel(lsIdNotification,
                                               Integer.parseInt(lsIdService),
                                               Integer.parseInt(lsIdProcess),   
                                               lsIdUsersGroup,
                                               lsSubject,
                                               lsMessage,
                                               lsStatusTab
                                               );            
        } catch (Exception loEx) {
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx);
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
        }
        //Refresh and close
        new UtilFaces().refreshTableWhereIterator("1 = 1",
                                                  lsEntityIterator,
                                                  getPoTblNotifications()
                                                  );
        new UtilFaces().hidePopup(getPoPopupSaveNotification());
        return null;
    }

    /**
     * Cancela el cuardado de la notificacion
     * @autor Jorge Luis Bautista Santiago  
     * @return void
     */
    public String cancelSaveNotificationAction() {
        new UtilFaces().hidePopup(getPoPopupSaveNotification());
        return null;
    }

    public void setPoPopupDeleteNotification(RichPopup poPopupDeleteNotification) {
        this.poPopupDeleteNotification = poPopupDeleteNotification;
    }

    public RichPopup getPoPopupDeleteNotification() {
        return poPopupDeleteNotification;
    }

    public void setPoDeleteIdBinding(RichOutputText poDeleteIdBinding) {
        this.poDeleteIdBinding = poDeleteIdBinding;
    }

    public RichOutputText getPoDeleteIdBinding() {
        return poDeleteIdBinding;
    }

    /**
     * Accion que guarda los parametros del parametro general
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String deleteNotificationAction() {
        String                    lsIdNotification = 
        getPoDeleteIdBinding().getValue() == null ? "" : 
        getPoDeleteIdBinding().getValue().toString();
        ApplicationModule         loAm = 
            Configuration.createRootApplicationModule(lsAmDef, lsConfig);
        AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
        try{
            loService.deleteNotificationsModel(Integer.parseInt(lsIdNotification));            
        } catch (Exception loEx) {
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx);
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
        }
        
        //Refresh and close
        new UtilFaces().refreshTableWhereIterator("1 = 1",
                                                  lsEntityIterator,
                                                  getPoTblNotifications()
                                                  );
        new UtilFaces().hidePopup(getPoPopupDeleteNotification());
        return null;
    }

    /**
     * Oculta popup que solicita datos para guardar parametro
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String cancelDeleteNotification() {
        new UtilFaces().hidePopup(getPoPopupDeleteNotification());
        return null;
    }

    /**
     * Actualiza la configuracion  de la notificacion
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     */
    public String updateNotificationAction() {
        
        String lsIdNotification = 
            getPoUpdateNotification().getValue() == null ? "" : 
            getPoUpdateNotification().getValue().toString();        
        String lsIdService = 
            getPoUpdateIdService().getValue() == null ? "" :
            getPoUpdateIdService().getValue().toString();        
        String lsIdProcess = 
            getPoUpdateProcessSel().getValue() == null ? "" : 
            getPoUpdateProcessSel().getValue().toString();  
        String lsIdUsersGroup = 
            getPoUpdateUserGroupSel().getValue() == null ? "" :
            getPoUpdateUserGroupSel().getValue().toString(); 
        String lsSubject = 
            getPoUpdateSubject().getValue() == null ? "" :
            getPoUpdateSubject().getValue().toString(); 
        String lsMessage = 
            getPoUpdateMessage().getValue() == null ? "" :
            getPoUpdateMessage().getValue().toString(); 
       
        String lsIndEstatus = 
            getPoUpdateStatus().getValue() == null ? "" : 
            getPoUpdateStatus().getValue().toString();
        String lsStatusTab = "0";
        if(lsIndEstatus.equalsIgnoreCase("true")){
            lsStatusTab = "1";
        }
        ApplicationModule         loAm = 
            Configuration.createRootApplicationModule(lsAmDef, lsConfig);
        AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
        try{
            loService.updateNotificationsModel(Integer.parseInt(lsIdNotification),
                                               Integer.parseInt(lsIdService),
                                               Integer.parseInt(lsIdProcess),   
                                               lsIdUsersGroup,
                                               lsSubject,
                                               lsMessage,
                                               lsStatusTab
                                               );            
        } catch (Exception loEx) {
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx);
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
        }
        //Refresh and close
        new UtilFaces().refreshTableWhereIterator("1 = 1",
                                                  lsEntityIterator,
                                                  getPoTblNotifications()
                                                  );
        new UtilFaces().hidePopup(getPoPopupUpdateNotification());
        return null;
    }

    /**
     * Cancela la actualizacion de la notificacion
     * @autor Jorge Luis Bautista Santiago  
     * @return void
     */
    public String cancelUpdateNotificationAction() {
        new UtilFaces().hidePopup(getPoPopupUpdateNotification());
        FacesContext       loContext = FacesContext.getCurrentInstance();
        ExternalContext    loEctx = loContext.getExternalContext();
        String             lsUrl = 
            loEctx.getRequestContextPath() + "/faces/notificationsConfigPage";
        try {
            loEctx.redirect(lsUrl);
        } catch (IOException loEx) {
            ;
        }
        return null;
    }

    /**
     * Ejecuta evento al cambiar la lista de valores de servicio
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void selectServiceValueSave(ValueChangeEvent toValueChangeEvent) {
        String lsNomServiceSelected = 
            toValueChangeEvent.getNewValue() == null ? null :
            toValueChangeEvent.getNewValue().toString();
        String             lsIdService = "0";
        if(lsNomServiceSelected != null){
            lsIdService = 
                new ViewObjectDao().getIdServiceByNomService(lsNomServiceSelected);            
            getPoSaveIdService().setValue(lsIdService);
        }else{
            getPoSaveIdService().setValue(null);
        }
    }
    /**
     * Ejecuta evento al cambiar la lista de valores de servicio
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void selectProcessValueSave(ValueChangeEvent toValueChangeEvent) {
        String             lsNomProcessSelected = 
            toValueChangeEvent.getNewValue() == null ? null :
            toValueChangeEvent.getNewValue().toString();
        String             lsIdProcess = "0";
        if(lsNomProcessSelected != null){
            lsIdProcess = 
                new ViewObjectDao().getProcessIdByNomParameter(lsNomProcessSelected);      
            getPoSaveIdProcess().setValue(lsIdProcess);
        }else{
            getPoSaveIdProcess().setValue(null);
        }
    }

    /**
     * Ejecuta evento al cambiar la lista de valores de servicio
     * @autor Jorge Luis Bautista Santiago
     * @param toValueChangeEvent
     * @return void
     */
    public void selectUsersGroupValueSave(ValueChangeEvent toValueChangeEvent) {        
        String lsUsersGroupSelected = 
            toValueChangeEvent.getNewValue() == null ? null :
            toValueChangeEvent.getNewValue().toString();
        String             lsIdUsersGroup = "0";
        if(lsUsersGroupSelected != null){
            lsIdUsersGroup = 
                new ViewObjectDao().getUsersGroupByDescParameter(lsUsersGroupSelected); 
            getPoSaveIdUserGroup().setValue(lsIdUsersGroup);
        }else{
            getPoSaveIdUserGroup().setValue(null);
        }
    }
    
    /**
     * Evento disparado al seleccionar proceso
     * @autor Jorge Luis Bautista Santiago  
     * @param toValueChangeEvent
     * @return void
     */
    public void selectProcessValueUpdate(ValueChangeEvent toValueChangeEvent) {
        String             lsNomProcessSelected = 
            toValueChangeEvent.getNewValue() == null ? null :
            toValueChangeEvent.getNewValue().toString();
        String             lsIdProcess = "0";
        if(lsNomProcessSelected != null){
            lsIdProcess = 
                new ViewObjectDao().getProcessIdByNomParameter(lsNomProcessSelected);      
            getPoUpdateIdProcess().setValue(lsIdProcess);
        }else{
            getPoUpdateIdProcess().setValue(null);
        }
    }

    /**
     * Evento disparado al seleccionar proceso
     * @autor Jorge Luis Bautista Santiago  
     * @param toValueChangeEvent
     * @return void
     */
    public void selectUsersGroupValueUpdate(ValueChangeEvent toValueChangeEvent) {
        String             lsUsersGroupSelected = 
            toValueChangeEvent.getNewValue() == null ? null :
            toValueChangeEvent.getNewValue().toString();
        String             lsIdUsersGroup = "0";
        if(lsUsersGroupSelected != null){
            lsIdUsersGroup = 
                new ViewObjectDao().getUsersGroupByDescParameter(lsUsersGroupSelected); 
            getPoUpdateIdUserGroup().setValue(lsIdUsersGroup);
        }else{
            getPoUpdateIdUserGroup().setValue(null);
        }
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
     * Regresa un conjunto de servicios web 
     * @autor Jorge Luis Bautista Santiago
     * @param tsStrSearch
     * @return List
     */
    public List getListUsersGroup() {
        List<SelectItem>        laList = 
            new ArrayList<SelectItem>();
        ViewObjectDao           loMd = new ViewObjectDao();
        List<SelectOneItemBean> laAllWs = 
            loMd.getListGeneralParametersModelFilter("USERS_GROUP_INTEGRATION");
        for(SelectOneItemBean loWs: laAllWs){
            SelectItem loItm = new SelectItem();           
            loItm.setValue(loWs.getLsId());
            loItm.setDescription(loWs.getLsValue());
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

    public void setPoFilterUserGroupSel(RichSelectOneChoice poFilterUserGroupSel) {
        this.poFilterUserGroupSel = poFilterUserGroupSel;
    }

    public RichSelectOneChoice getPoFilterUserGroupSel() {
        return poFilterUserGroupSel;
    }

    public void setPoFilterIdUserGroup(RichInputText poFilterIdUserGroup) {
        this.poFilterIdUserGroup = poFilterIdUserGroup;
    }

    public RichInputText getPoFilterIdUserGroup() {
        return poFilterIdUserGroup;
    }

    public void setPoFilterSubject(RichInputText poFilterSubject) {
        this.poFilterSubject = poFilterSubject;
    }

    public RichInputText getPoFilterSubject() {
        return poFilterSubject;
    }

    public void setPoFilterMessage(RichInputText poFilterMessage) {
        this.poFilterMessage = poFilterMessage;
    }

    public RichInputText getPoFilterMessage() {
        return poFilterMessage;
    }

    public void setPoFilterStatusSel(RichSelectOneChoice poFilterStatusSel) {
        this.poFilterStatusSel = poFilterStatusSel;
    }

    public RichSelectOneChoice getPoFilterStatusSel() {
        return poFilterStatusSel;
    }

    public void setPoTblNotifications(RichTable poTblNotifications) {
        this.poTblNotifications = poTblNotifications;
    }

    public RichTable getPoTblNotifications() {
        return poTblNotifications;
    }

    public void setPoPopupSaveNotification(RichPopup poPopupSaveNotification) {
        this.poPopupSaveNotification = poPopupSaveNotification;
    }

    public RichPopup getPoPopupSaveNotification() {
        return poPopupSaveNotification;
    }

    public void setPoSaveServiceSel(RichSelectOneChoice poSaveServiceSel) {
        this.poSaveServiceSel = poSaveServiceSel;
    }

    public RichSelectOneChoice getPoSaveServiceSel() {
        return poSaveServiceSel;
    }

    public void setPoSaveIdService(RichInputText poSaveIdService) {
        this.poSaveIdService = poSaveIdService;
    }

    public RichInputText getPoSaveIdService() {
        return poSaveIdService;
    }

    public void setPoSaveProcessSel(RichSelectOneChoice poSaveProcessSel) {
        this.poSaveProcessSel = poSaveProcessSel;
    }

    public RichSelectOneChoice getPoSaveProcessSel() {
        return poSaveProcessSel;
    }

    public void setPoSaveUserGroupSel(RichSelectOneChoice poSaveUserGroupSel) {
        this.poSaveUserGroupSel = poSaveUserGroupSel;
    }

    public RichSelectOneChoice getPoSaveUserGroupSel() {
        return poSaveUserGroupSel;
    }

    public void setPoSaveIdUserGroup(RichInputText poSaveIdUserGroup) {
        this.poSaveIdUserGroup = poSaveIdUserGroup;
    }

    public RichInputText getPoSaveIdUserGroup() {
        return poSaveIdUserGroup;
    }

    public void setPoSaveSubject(RichInputText poSaveSubject) {
        this.poSaveSubject = poSaveSubject;
    }

    public RichInputText getPoSaveSubject() {
        return poSaveSubject;
    }

    public void setPoSaveMessage(RichInputText poSaveMessage) {
        this.poSaveMessage = poSaveMessage;
    }

    public RichInputText getPoSaveMessage() {
        return poSaveMessage;
    }

    public void setPoPopupUpdateNotification(RichPopup poPopupUpdateNotification) {
        this.poPopupUpdateNotification = poPopupUpdateNotification;
    }

    public RichPopup getPoPopupUpdateNotification() {
        return poPopupUpdateNotification;
    }

    public void setPoUpdateIdService(RichInputText poUpdateIdService) {
        this.poUpdateIdService = poUpdateIdService;
    }

    public RichInputText getPoUpdateIdService() {
        return poUpdateIdService;
    }

    public void setPoUpdateService(RichInputText poUpdateService) {
        this.poUpdateService = poUpdateService;
    }

    public RichInputText getPoUpdateService() {
        return poUpdateService;
    }

    public void setPoUpdateProcessSel(RichSelectOneChoice poUpdateProcessSel) {
        this.poUpdateProcessSel = poUpdateProcessSel;
    }

    public RichSelectOneChoice getPoUpdateProcessSel() {
        return poUpdateProcessSel;
    }

    public void setPoUpdateUserGroupSel(RichSelectOneChoice poUpdateUserGroupSel) {
        this.poUpdateUserGroupSel = poUpdateUserGroupSel;
    }

    public RichSelectOneChoice getPoUpdateUserGroupSel() {
        return poUpdateUserGroupSel;
    }

    public void setPoUpdateIdUserGroup(RichInputText poUpdateIdUserGroup) {
        this.poUpdateIdUserGroup = poUpdateIdUserGroup;
    }

    public RichInputText getPoUpdateIdUserGroup() {
        return poUpdateIdUserGroup;
    }

    public void setPoUpdateSubject(RichInputText poUpdateSubject) {
        this.poUpdateSubject = poUpdateSubject;
    }

    public RichInputText getPoUpdateSubject() {
        return poUpdateSubject;
    }

    public void setPoUpdateMessage(RichInputText poUpdateMessage) {
        this.poUpdateMessage = poUpdateMessage;
    }

    public RichInputText getPoUpdateMessage() {
        return poUpdateMessage;
    }

    public void setPoSaveStatus(RichSelectBooleanCheckbox poSaveStatus) {
        this.poSaveStatus = poSaveStatus;
    }

    public RichSelectBooleanCheckbox getPoSaveStatus() {
        return poSaveStatus;
    }

    public void setPoUpdateStatus(RichSelectBooleanCheckbox poUpdateStatus) {
        this.poUpdateStatus = poUpdateStatus;
    }

    public RichSelectBooleanCheckbox getPoUpdateStatus() {
        return poUpdateStatus;
    }

    public void setPoUpdateNotification(RichInputText poUpdateNotification) {
        this.poUpdateNotification = poUpdateNotification;
    }

    public RichInputText getPoUpdateNotification() {
        return poUpdateNotification;
    }

    public void setPoUpdateIdProcess(RichInputText poUpdateIdProcess) {
        this.poUpdateIdProcess = poUpdateIdProcess;
    }

    public RichInputText getPoUpdateIdProcess() {
        return poUpdateIdProcess;
    }

    public void setPoDeleteMsgLbl(RichPanelLabelAndMessage poDeleteMsgLbl) {
        this.poDeleteMsgLbl = poDeleteMsgLbl;
    }

    public RichPanelLabelAndMessage getPoDeleteMsgLbl() {
        return poDeleteMsgLbl;
    }

    public void setPoSaveIdProcess(RichInputText poSaveIdProcess) {
        this.poSaveIdProcess = poSaveIdProcess;
    }

    public RichInputText getPoSaveIdProcess() {
        return poSaveIdProcess;
    }


}
