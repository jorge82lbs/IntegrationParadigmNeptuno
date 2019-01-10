/**
* Project: Paradigm - eVeTV Integration
*
* File: GeneralConfigBean.java
*
* Created on:  Septiembre 6, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package com.televisa.integration.view.front.beans;

import com.televisa.integration.model.AppModuleIntergrationImpl;
import com.televisa.integration.view.front.daos.ViewObjectDao;
import com.televisa.integration.view.front.util.UtilFaces;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

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

public class GeneralConfigBean {
    private RichInputText             poFilterParameter;
    private RichInputText             poFilterDescParameter;
    private RichInputText             poFilterUsedBy;
    private RichInputText             poFilterParameterValue;
    private RichTable                 poTblParameters;
    private RichSelectOneChoice       poFilterStatusSel;
    private RichPopup                 poPopupSaveParameter;
    private RichInputText             poPopSaveParameter;
    private RichInputText             poPopSaveDescription;
    private RichInputText             poPopSaveUsedBy;
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
    private RichSelectBooleanCheckbox poPopSaveEncrypt;
    private RichInputText             poPopSaveKey;
    private RichSelectBooleanCheckbox poPopUpdateEncrypt;
    private RichInputText             poPopUpdateKey;
    String                            gsEntityView = "EvetvIntConfigParamTabView1";
    String                            gsEntityIterator = "EvetvIntConfigParamTabView1Iterator";
    String                            gsAppModule = "AppModuleIntergrationDataControl";
    String                            gsAmDef = "com.televisa.integration.model.AppModuleIntergrationImpl";
    String                            gsConfig = "AppModuleIntergrationLocal";
    
    /**
     * Ejecuta la busqueda en base a los parametros
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String searchFilterAction() {
        String lsQuery = " 1 = 1 ";
        String lsNomParameter = 
            getPoFilterParameter().getValue() == null ? "" : 
            getPoFilterParameter().getValue().toString();        
        if(!lsNomParameter.equalsIgnoreCase("")){
            lsQuery += " AND UPPER(NOM_PARAMETER) LIKE '" +
                       lsNomParameter.toUpperCase() + "%'";
        }
        String lsDesParameter = 
            getPoFilterDescParameter().getValue() == null ? "" : 
            getPoFilterDescParameter().getValue().toString();        
        if(!lsDesParameter.equalsIgnoreCase("")){
            lsQuery += " AND UPPER(IND_DESC_PARAMETER) LIKE '" +
                       lsDesParameter.toUpperCase() + "%'";
        }
        String lsUsedBy = 
            getPoFilterUsedBy().getValue() == null ? "" : 
            getPoFilterUsedBy().getValue().toString();        
        if(!lsUsedBy.equalsIgnoreCase("")){
            lsQuery += " AND UPPER(IND_USED_BY) LIKE '"+
                       lsUsedBy.toUpperCase() + "%'";
        }
        String lsValParameter = 
            getPoFilterParameterValue().getValue() == null ? "" :
            getPoFilterParameterValue().getValue().toString();        
        if(!lsValParameter.equalsIgnoreCase("")){
            lsQuery += " AND UPPER(IND_VALUE_PARAMETER) LIKE '" +
                       lsValParameter.toUpperCase() + "%'";
        }
        String lsIndEstatus = 
            getPoFilterStatusSel().getValue() == null ? "" : 
            getPoFilterStatusSel().getValue().toString();        
        if(!lsIndEstatus.equalsIgnoreCase("")){
            lsQuery += " AND IND_ESTATUS = '" + lsIndEstatus + "'";
        }
        //Refresh and close
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
        ApplicationModule         loAm = 
            Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        AppModuleIntergrationImpl loService = 
            (AppModuleIntergrationImpl)loAm;                        
        toActionEvent.getSource();
        getPoPopSaveDescription().setValue("");
        getPoPopSaveParameter().setValue("");
        getPoPopSaveStatus().setValue("");
        getPoPopSaveUsedBy().setValue("");
        getPoPopSaveValue().setValue("");
        getPoPopSaveStatus().setValue("");
        try{            
            getPoPopSaveKey().setValue(loService.getKeyDecoderUserFromDb());
        } catch (Exception loEx) {
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx);
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
        }
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
        ApplicationModule         loAm = 
            Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        AppModuleIntergrationImpl loService = 
            (AppModuleIntergrationImpl)loAm;                        
        FacesCtrlHierNodeBinding loNode = 
            (FacesCtrlHierNodeBinding) getPoTblParameters().getSelectedRowData();
        String                   lsIdParameter = 
            loNode.getAttribute("IdParameter") == null ? "" : 
            loNode.getAttribute("IdParameter").toString();
        String                   lsNomParameter = 
            loNode.getAttribute("NomParameter") == null ? "" : 
            loNode.getAttribute("NomParameter").toString();
        String                   lsIndDescParameter = 
            loNode.getAttribute("IndDescParameter") == null ? "" : 
            loNode.getAttribute("IndDescParameter").toString();
        String                   lsIndUsedBy = 
            loNode.getAttribute("IndUsedBy") == null ? "" : 
            loNode.getAttribute("IndUsedBy").toString();
        String                   lsIndValueParameter = 
            loNode.getAttribute("IndValueParameter") == null ? "" : 
            loNode.getAttribute("IndValueParameter").toString();
        String                   lsIndEstatus = 
            loNode.getAttribute("IndEstatus") == null ? "" : 
            loNode.getAttribute("IndEstatus").toString();
        // Settear valores al popup
        getPoPopUpdateIdParameter().setValue(lsIdParameter);                
        getPoPopUpdateParameter().setValue(lsNomParameter);
        getPoPopUpdateDescription().setValue(lsIndDescParameter);        
        getPoPopUpdateUsedBy().setValue(lsIndUsedBy);        
        getPoPopUpdateValue().setValue(lsIndValueParameter);
        if(lsIndEstatus.equalsIgnoreCase("1")){
            getPoPopUpdateStatus().setSelected(true);
        }else{
            getPoPopUpdateStatus().setSelected(false);
        }        
        try{            
            getPoPopUpdateKey().setValue(loService.getKeyDecoderUserFromDb());
        } catch (Exception loEx) {
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx);
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        } finally {
            Configuration.releaseRootApplicationModule(loAm, true);
        }
        
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
        String                   lsIdParameter = 
            loNode.getAttribute("IdParameter") == null ? "" : 
            loNode.getAttribute("IdParameter").toString();        
        String                   lsParameter = 
            loNode.getAttribute("NomParameter") == null ? "" : 
            loNode.getAttribute("NomParameter").toString();
        getPoDeleteIdBinding().setValue(lsIdParameter);
        getPoDeleteMsgLbl().setLabel("Eliminar a " + lsParameter);
        new UtilFaces().showPopup(getPoPopupDelete());
    }

    /**
     * Accion que guarda los parametros del parametro general
     * @autor Jorge Luis Bautista Santiago  
     * @return String
     */
    public String saveParameterAction() {
        String                     lsFieldErrorRq = "";
        boolean                    lbProcess = true;
        Integer                    liIdParameter = 
            new ViewObjectDao().getMaxIdParadigm("Parameters") + 1;
        String                     lsNomParameter = 
            getPoPopSaveParameter().getValue() == null ? "" : 
            getPoPopSaveParameter().getValue().toString();
        String                    lsDescription = 
            getPoPopSaveDescription().getValue() == null ? "" : 
            getPoPopSaveDescription().getValue().toString();
        String                     lsUsedBy = 
            getPoPopSaveUsedBy().getValue() == null ? "" : 
            getPoPopSaveUsedBy().getValue().toString();
        String                     lsValueParameter = 
            getPoPopSaveValue().getValue() == null ? "" :
            getPoPopSaveValue().getValue().toString();
        String                     lsIndEstatus = 
            getPoPopSaveStatus().getValue() == null ? "":
            getPoPopSaveStatus().getValue().toString();
        String                     lsStatusTab = "0";
        if(lsIndEstatus.equalsIgnoreCase("true")){
            lsStatusTab = "1";
        }
        
        if(lsNomParameter.length() < 1){
            lsFieldErrorRq += "Nombre del Parametro,";
            lbProcess = false;   
        }
        if(lsValueParameter.length() < 1){
            lsFieldErrorRq += "Valor del Parametro,";
            lbProcess = false;   
        }
        
        if(lbProcess){
            ApplicationModule         loAm = 
                Configuration.createRootApplicationModule(gsAmDef, gsConfig);
            AppModuleIntergrationImpl loService = 
                (AppModuleIntergrationImpl)loAm;                
            try{
                
                String lsKeySave = 
                    getPoPopSaveEncrypt().getValue() == null ? "" : 
                    getPoPopSaveEncrypt().getValue().toString();    
                String lsKeyString = 
                    getPoPopSaveKey().getValue() == null ? null : getPoPopSaveKey().getValue().toString();                     
                
                boolean lbInsert = true;
                if(lsKeySave.equalsIgnoreCase("true")){
                    if(lsKeyString == null){
                        lsKeyString = loService.getKeyDecoderUserFromDb();
                    }
                    try{
                        lsValueParameter = 
                            new UtilFaces().encryptObject(lsValueParameter, lsKeyString);
                    }catch(Exception loExEnc){
                        lbInsert = false;
                    }
                }
                if(lbInsert){
                    loService.insertGeneralParameterModel(liIdParameter,
                                                          lsNomParameter,
                                                          lsDescription,
                                                          lsUsedBy,
                                                          lsValueParameter,
                                                          lsStatusTab
                                                         );            
                }else{
                    FacesMessage loMsg;
                    loMsg = new FacesMessage("No es Posible Encriptar " + lsValueParameter + 
                                             " con Llave ["+lsKeyString+"]");
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
            
            //Refresh and close
            new UtilFaces().refreshTableWhereIterator("1 = 1",
                                                      gsEntityIterator,
                                                      getPoTblParameters()
                                                      );
            new UtilFaces().hidePopup(getPoPopupSaveParameter());
        }else{
            FacesMessage loMsg;
            loMsg = new FacesMessage("Los Siguiente Campos son Requeridos " + 
                                     lsFieldErrorRq.substring(0, lsFieldErrorRq.length()-1));
            loMsg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        }
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
        String                   lsIdParameter = 
        getPoDeleteIdBinding().getValue() == null ? "" : 
        getPoDeleteIdBinding().getValue().toString();
        ApplicationModule        loAm = 
            Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        AppModuleIntergrationImpl loService = 
            (AppModuleIntergrationImpl)loAm;
        try{
            loService.deleteGeneralParameterModel(Integer.parseInt(lsIdParameter));
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
        String                   lsFieldErrorRq = "";
        boolean                  lbProcess = true;
        String                   lsIdParameter = 
            getPoPopUpdateIdParameter().getValue() == null ? "" : 
            getPoPopUpdateIdParameter().getValue().toString();        
        String                   lsNomParameter = 
            getPoPopUpdateParameter().getValue() == null ? "" : 
            getPoPopUpdateParameter().getValue().toString();
        String                   lsDescription = 
            getPoPopUpdateDescription().getValue() == null ? "" : 
            getPoPopUpdateDescription().getValue().toString();
        String                   lsUsedBy = 
            getPoPopUpdateUsedBy().getValue() == null ? "" : 
            getPoPopUpdateUsedBy().getValue().toString();
        String                   lsValueParameter = 
            getPoPopUpdateValue().getValue() == null ? "" : 
            getPoPopUpdateValue().getValue().toString();                                
        String                   lsIndEstatus = 
            getPoPopUpdateStatus().getValue() == null ? "" :
            getPoPopUpdateStatus().getValue().toString();
        String lsStatusTab = "0";
        if(lsIndEstatus.equalsIgnoreCase("true")){
            lsStatusTab = "1";
        }    
        if(lsNomParameter.length() < 1){
            lsFieldErrorRq += "Nombre del Parametro,";
            lbProcess = false;   
        }
        if(lsValueParameter.length() < 1){
            lsFieldErrorRq += "Valor del Parametro,";
            lbProcess = false;   
        }
        
        if(lbProcess){
            ApplicationModule         loAm = 
                Configuration.createRootApplicationModule(gsAmDef,
                                                          gsConfig);
            AppModuleIntergrationImpl loService = 
                (AppModuleIntergrationImpl)loAm;
            try{
                String lsKeySave = 
                    getPoPopUpdateEncrypt().getValue() == null ? "" : 
                    getPoPopUpdateEncrypt().getValue().toString();     
                String lsKeyString = 
                    getPoPopUpdateKey().getValue() == null ? null : 
                    getPoPopUpdateKey().getValue().toString();                     
                
                boolean lbUpdate = true;
                if(lsKeySave.equalsIgnoreCase("true")){
                    if(lsKeyString == null){
                        lsKeyString = loService.getKeyDecoderUserFromDb();
                    }
                    try{
                        lsValueParameter = 
                            new UtilFaces().encryptObject(lsValueParameter, lsKeyString);
                    }catch(Exception loExEnc){
                        lbUpdate = false;
                    }
                }
                if(lbUpdate){
                    loService.updateGeneralParameterModel(Integer.parseInt(lsIdParameter),
                                                          lsNomParameter,
                                                          lsDescription,
                                                          lsUsedBy,
                                                          lsValueParameter,
                                                          lsStatusTab
                                                         );    
                }else{
                    FacesMessage loMsg;
                    loMsg = new FacesMessage("No es Posible Encriptar " + lsValueParameter + 
                                             " con Llave ["+lsKeyString+"]");
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
            
            //Refresh and close
            new UtilFaces().refreshTableWhereIterator("1 = 1",
                                                      gsEntityIterator,
                                                      getPoTblParameters()
                                                      );
            new UtilFaces().hidePopup(getPoPopupUpdateParameter());
        }else{
            FacesMessage loMsg;
            loMsg = new FacesMessage("Los Siguiente Campos son Requeridos " + 
                                     lsFieldErrorRq.substring(0, lsFieldErrorRq.length()-1));
            loMsg.setSeverity(FacesMessage.SEVERITY_WARN);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        }
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
            loEctx.getRequestContextPath() + "/faces/generalConfigPage";        
        try {
            loEctx.redirect(lsUrl);
        } catch (IOException loEx) {
            ;
        }
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
        getPoFilterParameter().setValue("");
        getPoFilterDescParameter().setValue("");
        getPoFilterParameterValue().setValue("");
        getPoFilterUsedBy().setValue("");
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

    /****************** SETTERS AND GETTERS ******************************/    
    
    public void setPoFilterParameter(RichInputText poFilterParameter) {
        this.poFilterParameter = poFilterParameter;
    }

    public RichInputText getPoFilterParameter() {
        return poFilterParameter;
    }

    public void setPoFilterDescParameter(RichInputText poFilterDescParameter) {
        this.poFilterDescParameter = poFilterDescParameter;
    }

    public RichInputText getPoFilterDescParameter() {
        return poFilterDescParameter;
    }

    public void setPoFilterUsedBy(RichInputText poFilterUsedBy) {
        this.poFilterUsedBy = poFilterUsedBy;
    }

    public RichInputText getPoFilterUsedBy() {
        return poFilterUsedBy;
    }

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

    public void setPoPopSaveDescription(RichInputText poPopSaveDescription) {
        this.poPopSaveDescription = poPopSaveDescription;
    }

    public RichInputText getPoPopSaveDescription() {
        return poPopSaveDescription;
    }

    public void setPoPopSaveUsedBy(RichInputText poPopSaveUsedBy) {
        this.poPopSaveUsedBy = poPopSaveUsedBy;
    }

    public RichInputText getPoPopSaveUsedBy() {
        return poPopSaveUsedBy;
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

    public void setPoPopSaveEncrypt(RichSelectBooleanCheckbox poPopSaveEncrypt) {
        this.poPopSaveEncrypt = poPopSaveEncrypt;
    }

    public RichSelectBooleanCheckbox getPoPopSaveEncrypt() {
        return poPopSaveEncrypt;
    }

    public void setPoPopSaveKey(RichInputText poPopSaveKey) {
        this.poPopSaveKey = poPopSaveKey;
    }

    public RichInputText getPoPopSaveKey() {
        return poPopSaveKey;
    }

    public void setPoPopUpdateEncrypt(RichSelectBooleanCheckbox poPopUpdateEncrypt) {
        this.poPopUpdateEncrypt = poPopUpdateEncrypt;
    }

    public RichSelectBooleanCheckbox getPoPopUpdateEncrypt() {
        return poPopUpdateEncrypt;
    }

    public void setPoPopUpdateKey(RichInputText poPopUpdateKey) {
        this.poPopUpdateKey = poPopUpdateKey;
    }

    public RichInputText getPoPopUpdateKey() {
        return poPopUpdateKey;
    }
}
