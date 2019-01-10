/**
* Project: Paradigm - eVeTV Integration
*
* File: ParametersConfigBean.java
*
* Created on:  Septiembre 6, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package com.televisa.integration.view.front.beans;

import com.televisa.integration.model.AppModuleIntergrationImpl;
import com.televisa.integration.view.front.beans.types.SelectOneItemBean;
import com.televisa.integration.view.front.daos.ViewObjectDao;
import com.televisa.integration.view.front.util.UtilFaces;

import java.io.IOException;

import java.util.ArrayList;
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
import oracle.jbo.client.Configuration;

/** Esta clase es un bean que enlaza la pantalla de Configuracion de Parametros Generales<br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Septiembre 14, 2017, 12:00 pm
 */
public class ParametersConfigBean {
    private RichInputText             poFilterParameterValue;
    private RichTable                 poTblParameters;
    private RichSelectOneChoice       poFilterStatusSel;
    private RichPopup                 poPopupSaveParameter;
    private RichInputText             poPopSaveParameter;
    private RichInputText             poPopSaveValue;
    private RichSelectBooleanCheckbox poPopSaveStatus;
    private RichPanelLabelAndMessage  poDeleteMsgLbl;
    private RichPopup                 poPopupDelete;
    private RichPopup                 poPopupUpdateParameter;
    private RichInputText             poPopUpdateIdParameter;
    private RichInputText             poPopUpdateParameter;
    private RichInputText             poPopUpdateDescription;
    private RichInputText             poPopUpdateUsedBy;
    private RichInputText             poPopUpdateValue;
    private RichSelectBooleanCheckbox poPopUpdateStatus;
    private RichOutputText            poDeleteIdBinding;
    private RichSelectOneChoice       poFilterNomServiceSel;
    private RichInputText             poFilterParameter;
    private RichSelectOneChoice       poSaveNomServiceSel;
    private RichInputText             poPopSaveIdService;
    private RichInputText             poFilterIdService;
    private RichOutputText            poDeleteNomBinding;
    private RichOutputText            poDeleteValBinding;
    private RichInputText             poPopUpdateIdService;
    private RichInputText             poPopUpdateNomService;
    private RichInputText             poPopUpdateParameterStat;
    private RichInputText             poPopUpdateValueStat;
    String                            gsEntityView = "EvetvIntServicesParamsTblVwView1";
    String                            gsEntityIterator = "EvetvIntServicesParamsTblVwView1Iterator";
    String                            gsAppModule = "AppModuleIntergrationDataControl";
    String                            gsAmDef = "com.televisa.integration.model.AppModuleIntergrationImpl";
    String                            gsConfig = "AppModuleIntergrationLocal";
    private RichInputText             poPopUpdateParameterKey;
    private RichInputText             poPopUpdateValueKey;

    /**
     * Ejecuta la busqueda en base a los parametros
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String searchFilterAction() {
        String lsQuery = " 1 = 1 ";
        String lsNomServiceSelected = 
            getPoFilterNomServiceSel().getValue() == null ? "" : 
            getPoFilterNomServiceSel().getValue().toString();        
        if(!lsNomServiceSelected.equalsIgnoreCase("")){
            lsQuery += " AND ID_SERVICE = "+lsNomServiceSelected;
        }
        String lsParameter = 
            getPoFilterParameter().getValue() == null ? "" : 
            getPoFilterParameter().getValue().toString();        
        if(!lsParameter.equalsIgnoreCase("")){
            lsQuery += " AND UPPER(IND_PARAMETER) LIKE '" +
                       lsParameter.toUpperCase() + "%'";
        }        
        String lsValParameter = 
            getPoFilterParameterValue().getValue() == null ? "" :
            getPoFilterParameterValue().getValue().toString();        
        if(!lsValParameter.equalsIgnoreCase("")){
            lsQuery += " AND UPPER(IND_VAL_PARAMETER) LIKE '" + 
                       lsValParameter.toUpperCase() + "%'";
        }
        String lsIndEstatus = 
            getPoFilterStatusSel().getValue() == null ? "" : 
            getPoFilterStatusSel().getValue().toString();               
        if(!lsIndEstatus.equalsIgnoreCase("")){
            lsQuery += " AND IND_ESTATUS = '" + lsIndEstatus + "'";
        }
        new UtilFaces().refreshTableWhereIterator(lsQuery,
                                                  gsEntityIterator,
                                                  getPoTblParameters()
                                                  );
        return null;
    }

    /**
     * Muestra popup que solicita datos para guardar el parametro
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     * @return String
     */
    public void showSavePopupParameter(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        getPoPopSaveIdService().setValue(null);
        getPoPopSaveParameter().setValue("");
        getPoPopSaveStatus().setValue("");
        getPoPopSaveValue().setValue("");
        getPoPopSaveStatus().setValue("");
        
        new UtilFaces().showPopup(getPoPopupSaveParameter());
    }

    /**
     * Muestra popup que solicita datos para editar parametro
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     * @return void
     */
    public void showEditPopupParameter(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblParameters().getSelectedRowData();
        String                   lsIdServicio = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();                 
        String                   lsNomServicio = 
            loNode.getAttribute("NomService") == null ? "" : 
            loNode.getAttribute("NomService").toString();                 
        String                   lsParameter = 
            loNode.getAttribute("IndParameter") == null ? "" : 
            loNode.getAttribute("IndParameter").toString();
        String                   lsParameterValue = 
            loNode.getAttribute("IndValParameter") == null ? "" : 
            loNode.getAttribute("IndValParameter").toString();
        String                   lsIndEstatus = 
            loNode.getAttribute("IndEstatus") == null ? "" : 
            loNode.getAttribute("IndEstatus").toString();
        // Settear valores al popup
        getPoPopUpdateIdService().setValue(lsIdServicio);                
        getPoPopUpdateNomService().setValue(lsNomServicio);                
        getPoPopUpdateParameter().setValue(lsParameter);        
        getPoPopUpdateValue().setValue(lsParameterValue);
        getPoPopUpdateParameterKey().setValue(lsParameter);        
        getPoPopUpdateValueKey().setValue(lsParameterValue);
        if(lsIndEstatus.equalsIgnoreCase("1")){
            getPoPopUpdateStatus().setSelected(true);
        }else{
            getPoPopUpdateStatus().setSelected(false);
        }
                    
        getPoPopUpdateParameterStat().setValue(lsParameter);   
        getPoPopUpdateValueStat().setValue(lsParameterValue);
        new UtilFaces().showPopup(getPoPopupUpdateParameter());
    }

    /**
     * Muestra popup que solicita datos para eliminar el parametro
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     * @return void
     */
    public void showDeletePopupParameter(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblParameters().getSelectedRowData();
        
        String                   lsIdServicio = 
            loNode.getAttribute("IdService") == null ? "" : 
            loNode.getAttribute("IdService").toString();                 
        String                   lsParameter = 
            loNode.getAttribute("IndParameter") == null ? "" : 
            loNode.getAttribute("IndParameter").toString();
        String                   lsParameterValue = 
            loNode.getAttribute("IndValParameter") == null ? "" : 
            loNode.getAttribute("IndValParameter").toString();
        getPoDeleteIdBinding().setValue(lsIdServicio);
        getPoDeleteNomBinding().setValue(lsParameter);
        getPoDeleteValBinding().setValue(lsParameterValue);        
        getPoDeleteMsgLbl().setLabel("Eliminar a " + lsParameter + " : " + lsParameterValue);
        new UtilFaces().showPopup(getPoPopupDelete());
    }

    /**
     * Accion que guarda los parametros del parametro general
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String saveParameterAction() {
        String lsIdService = 
            getPoSaveNomServiceSel().getValue() == null ? "" : 
            getPoSaveNomServiceSel().getValue().toString();        
        String lsParameter = 
            getPoPopSaveParameter().getValue() == null ? "" :
            getPoPopSaveParameter().getValue().toString();
        String lsValueParameter = 
            getPoPopSaveValue().getValue() == null ? "" :
            getPoPopSaveValue().getValue().toString();
        String lsIndEstatus = 
            getPoPopSaveStatus().getValue() == null ? "" :
            getPoPopSaveStatus().getValue().toString();
        String lsStatusTab = "0";
        if(lsIndEstatus.equalsIgnoreCase("true")){
            lsStatusTab = "1";
        }
       ApplicationModule         loAm =
           Configuration.createRootApplicationModule(gsAmDef, gsConfig);
       AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
       try{
           loService.insertServicesParamsModel(Integer.parseInt(lsIdService),
                                              lsParameter,   
                                              lsValueParameter,
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
        new UtilFaces().refreshTableWhereIterator("1 = 1",
                                                  gsEntityIterator,
                                                  getPoTblParameters()
                                                  );
        new UtilFaces().hidePopup(getPoPopupSaveParameter());
        return null;
    }

     /**
      * Oculta popup que solicita datos para guardar parametro
      * @autor Jorge Luis Bautista Santiago  
      * @return String
      */
    public String cancelSaveParameterAction() {
        new UtilFaces().hidePopup(getPoPopupSaveParameter());
        return null;
    }

    /**
     * Elimina de la base de datos el parametro general
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String deleteParameterAction() {
        String                    lsIdService = 
        getPoDeleteIdBinding().getValue() == null ? "" : 
        getPoDeleteIdBinding().getValue().toString();
        String                    lsNomParameter = 
        getPoDeleteNomBinding().getValue() == null ? "" : 
        getPoDeleteNomBinding().getValue().toString();
        String                    lsValParameter = 
        getPoDeleteValBinding().getValue() == null ? "" : 
        getPoDeleteValBinding().getValue().toString();        
        ApplicationModule         loAm = 
            Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
        try{
            loService.deleteServicesParamsModel(Integer.parseInt(lsIdService),
                                                lsNomParameter,
                                                lsValParameter
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
                                                  getPoTblParameters()
                                                  );
        new UtilFaces().hidePopup(getPoPopupDelete());
        return null;
    }
        
    /**
     * Oculta popup de eliminar servicio
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String cancelDeleteParameter() {
        new UtilFaces().hidePopup(getPoPopupDelete());
        return null;
    }
    
    /**
     * Actualiza en base de datos el servicio seleccionado
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */  
    public String updateParameterAction() {
        String lsIdService = 
            getPoPopUpdateIdService().getValue() == null ? "" : 
            getPoPopUpdateIdService().getValue().toString();        
        String lsParameter = 
            getPoPopUpdateParameter().getValue() == null ? "" :
            getPoPopUpdateParameter().getValue().toString();
        String lsValueParameter = 
            getPoPopUpdateValue().getValue() == null ? "" :
            getPoPopUpdateValue().getValue().toString();        
        String lsIndEstatus = 
            getPoPopUpdateStatus().getValue() == null ? "" :
            getPoPopUpdateStatus().getValue().toString();
        String lsStatusTab = "0";
        if(lsIndEstatus.equalsIgnoreCase("true")){
            lsStatusTab = "1";
        }
        
        String lsParameterKey = 
            getPoPopUpdateParameterKey().getValue() == null ? "" :
            getPoPopUpdateParameterKey().getValue().toString();
        String lsValueParameterKey = 
            getPoPopUpdateValueKey().getValue() == null ? "" :
            getPoPopUpdateValueKey().getValue().toString();
        
        ApplicationModule         loAm = Configuration.createRootApplicationModule(gsAmDef, 
                                                                                   gsConfig);
        AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
        try{
            loService.updateServicesParamsModel(Integer.parseInt(lsIdService),                                               
                                               lsParameter,
                                               lsValueParameter,
                                               lsStatusTab,
            lsParameterKey,lsValueParameterKey
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
                                                  getPoTblParameters()
                                                  );
        new UtilFaces().hidePopup(getPoPopupUpdateParameter());
        
        return null;
    }

    /**
     * Oculta popup de Actualizacion
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String cancelUpdateParameterAction() {
        new UtilFaces().hidePopup(getPoPopupUpdateParameter());
        FacesContext       loContext = FacesContext.getCurrentInstance();
        ExternalContext    loEctx = loContext.getExternalContext();
        String             lsUrl = 
            loEctx.getRequestContextPath() + "/faces/parametersConfigPage";
        
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
            getPoPopSaveIdService().setValue(lsIdService);
        }else{
            getPoPopSaveIdService().setValue(null);
        }
    }
    
    /**
     * Reinicia los valores de busqueda
     * @autor Jorge Luis Bautista Santiago  
     * @param toActionEvent
     * @return void
     */
    public void resetFilterValues(ActionEvent toActionEvent) {
        toActionEvent.getSource();
        getPoFilterNomServiceSel().setValue("");
        getPoFilterParameter().setValue("");
        getPoFilterParameterValue().setValue("");
    }
    
    /**
     * Actualiza la tabla principal a su estado inicial
     * @autor Jorge Luis Bautista Santiago 
     * @param toValueChangeEvent
     * @return String
     */
    public void selectServiceValueFilter(ValueChangeEvent toValueChangeEvent) {
        String             lsNomServiceSelected = 
            toValueChangeEvent.getNewValue() == null ? null :
            toValueChangeEvent.getNewValue().toString();
        String             lsIdService = "0";
        if(lsNomServiceSelected != null){
            lsIdService = 
                new ViewObjectDao().getIdServiceByNomService(lsNomServiceSelected);            
            getPoFilterIdService().setValue(lsIdService);
        }else{
            getPoFilterIdService().setValue(null);
        }                       
    }

    /**
     * Actualiza la tabla principal a su estado inicial
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String refreshMainTable() {
        new UtilFaces().refreshTableWhereIterator("1 = 1 ",
                                                  gsEntityIterator,
                                                  getPoTblParameters()
                                                  );
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
      
    /****************** SETTERS AND GETTERS ******************************/    
      
    public void setPoFilterParameterValue(RichInputText poFilterParameterValue) {
        this.poFilterParameterValue = poFilterParameterValue;
    }

    public RichInputText getPoFilterParameterValue() {
        return poFilterParameterValue;
    }
    
    public void setPoTblParameters(RichTable poTblParameters) {
        this.poTblParameters = poTblParameters;
    }

    public RichTable getPoTblParameters() {
        return poTblParameters;
    }

    
    public void setPoFilterStatusSel(RichSelectOneChoice poFilterStatusSel) {
        this.poFilterStatusSel = poFilterStatusSel;
    }

    public RichSelectOneChoice getPoFilterStatusSel() {
        return poFilterStatusSel;
    }

    public void setPoPopupSaveParameter(RichPopup poPopupSaveParameter) {
        this.poPopupSaveParameter = poPopupSaveParameter;
    }

    public RichPopup getPoPopupSaveParameter() {
        return poPopupSaveParameter;
    }

    public void setPoPopSaveParameter(RichInputText poPopSaveParameter) {
        this.poPopSaveParameter = poPopSaveParameter;
    }

    public RichInputText getPoPopSaveParameter() {
        return poPopSaveParameter;
    }

    public void setPoPopSaveValue(RichInputText poPopSaveValue) {
        this.poPopSaveValue = poPopSaveValue;
    }

    public RichInputText getPoPopSaveValue() {
        return poPopSaveValue;
    }

    public void setPoPopSaveStatus(RichSelectBooleanCheckbox poPopSaveStatus) {
        this.poPopSaveStatus = poPopSaveStatus;
    }

    public RichSelectBooleanCheckbox getPoPopSaveStatus() {
        return poPopSaveStatus;
    }
    
    public void setPoDeleteMsgLbl(RichPanelLabelAndMessage poDeleteMsgLbl) {
        this.poDeleteMsgLbl = poDeleteMsgLbl;
    }

    public RichPanelLabelAndMessage getPoDeleteMsgLbl() {
        return poDeleteMsgLbl;
    }
       
    public void setPoPopupDelete(RichPopup poPopupDelete) {
        this.poPopupDelete = poPopupDelete;
    }

    public RichPopup getPoPopupDelete() {
        return poPopupDelete;
    }

    public void setPoPopupUpdateParameter(RichPopup poPopupUpdateParameter) {
        this.poPopupUpdateParameter = poPopupUpdateParameter;
    }

    public RichPopup getPoPopupUpdateParameter() {
        return poPopupUpdateParameter;
    }

    public void setPoPopUpdateIdParameter(RichInputText poPopUpdateIdParameter) {
        this.poPopUpdateIdParameter = poPopUpdateIdParameter;
    }

    public RichInputText getPoPopUpdateIdParameter() {
        return poPopUpdateIdParameter;
    }

    public void setPoPopUpdateParameter(RichInputText poPopUpdateParameter) {
        this.poPopUpdateParameter = poPopUpdateParameter;
    }

    public RichInputText getPoPopUpdateParameter() {
        return poPopUpdateParameter;
    }

    public void setPoPopUpdateDescription(RichInputText poPopUpdateDescription) {
        this.poPopUpdateDescription = poPopUpdateDescription;
    }

    public RichInputText getPoPopUpdateDescription() {
        return poPopUpdateDescription;
    }

    public void setPoPopUpdateUsedBy(RichInputText poPopUpdateUsedBy) {
        this.poPopUpdateUsedBy = poPopUpdateUsedBy;
    }

    public RichInputText getPoPopUpdateUsedBy() {
        return poPopUpdateUsedBy;
    }

    public void setPoPopUpdateValue(RichInputText poPopUpdateValue) {
        this.poPopUpdateValue = poPopUpdateValue;
    }

    public RichInputText getPoPopUpdateValue() {
        return poPopUpdateValue;
    }

    public void setPoPopUpdateStatus(RichSelectBooleanCheckbox poPopUpdateStatus) {
        this.poPopUpdateStatus = poPopUpdateStatus;
    }

    public RichSelectBooleanCheckbox getPoPopUpdateStatus() {
        return poPopUpdateStatus;
    }

    public void setPoDeleteIdBinding(RichOutputText poDeleteIdBinding) {
        this.poDeleteIdBinding = poDeleteIdBinding;
    }

    public RichOutputText getPoDeleteIdBinding() {
        return poDeleteIdBinding;
    }

    public void setPoFilterNomServiceSel(RichSelectOneChoice poFilterNomServiceSel) {
        this.poFilterNomServiceSel = poFilterNomServiceSel;
    }

    public RichSelectOneChoice getPoFilterNomServiceSel() {
        return poFilterNomServiceSel;
    }

    public void setPoFilterParameter(RichInputText poFilterParameter) {
        this.poFilterParameter = poFilterParameter;
    }

    public RichInputText getPoFilterParameter() {
        return poFilterParameter;
    }

    public void setPoSaveNomServiceSel(RichSelectOneChoice poSaveNomServiceSel) {
        this.poSaveNomServiceSel = poSaveNomServiceSel;
    }

    public RichSelectOneChoice getPoSaveNomServiceSel() {
        return poSaveNomServiceSel;
    }    

    public void setPoPopSaveIdService(RichInputText poPopSaveIdService) {
        this.poPopSaveIdService = poPopSaveIdService;
    }

    public RichInputText getPoPopSaveIdService() {
        return poPopSaveIdService;
    }

    public void setPoFilterIdService(RichInputText poFilterIdService) {
        this.poFilterIdService = poFilterIdService;
    }

    public RichInputText getPoFilterIdService() {
        return poFilterIdService;
    }
    

    public void setPoDeleteNomBinding(RichOutputText poDeleteNomBinding) {
        this.poDeleteNomBinding = poDeleteNomBinding;
    }

    public RichOutputText getPoDeleteNomBinding() {
        return poDeleteNomBinding;
    }

    public void setPoDeleteValBinding(RichOutputText poDeleteValBinding) {
        this.poDeleteValBinding = poDeleteValBinding;
    }

    public RichOutputText getPoDeleteValBinding() {
        return poDeleteValBinding;
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

    public void setPoPopUpdateParameterStat(RichInputText poPopUpdateParameterStat) {
        this.poPopUpdateParameterStat = poPopUpdateParameterStat;
    }

    public RichInputText getPoPopUpdateParameterStat() {
        return poPopUpdateParameterStat;
    }

    public void setPoPopUpdateValueStat(RichInputText poPopUpdateValueStat) {
        this.poPopUpdateValueStat = poPopUpdateValueStat;
    }

    public RichInputText getPoPopUpdateValueStat() {
        return poPopUpdateValueStat;
    }

    public void setPoPopUpdateParameterKey(RichInputText poPopUpdateParameterKey) {
        this.poPopUpdateParameterKey = poPopUpdateParameterKey;
    }

    public RichInputText getPoPopUpdateParameterKey() {
        return poPopUpdateParameterKey;
    }

    public void setPoPopUpdateValueKey(RichInputText poPopUpdateValueKey) {
        this.poPopUpdateValueKey = poPopUpdateValueKey;
    }

    public RichInputText getPoPopUpdateValueKey() {
        return poPopUpdateValueKey;
    }
}
