package com.televisa.integration.view.front.beans;

import com.televisa.integration.model.AppModuleIntergrationImpl;
import com.televisa.integration.view.front.beans.types.SelectOneItemBean;
import com.televisa.integration.view.front.daos.ViewObjectDao;

import com.televisa.integration.view.front.util.UtilFaces;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.input.RichInputNumberSpinbox;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

import oracle.jbo.ApplicationModule;
import oracle.jbo.client.Configuration;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class TaskConfigBean {
    private RichSelectOneChoice poFilterNomServiceSel;
    private RichInputText poIdTabSelected;
    private RichPanelTabbed poPanelTabbed;
    private RichShowDetailItem poTabMinutos;
    private RichShowDetailItem poTabHoras;
    private RichInputNumberSpinbox poCadaMinutos;
    private RichSelectBooleanCheckbox poLunMinutos;
    private RichSelectBooleanCheckbox poMarMinutos;
    private RichSelectBooleanCheckbox poMieMinutos;
    private RichSelectBooleanCheckbox poJueMinutos;
    private RichSelectBooleanCheckbox poVieMinutos;
    private RichSelectBooleanCheckbox poSabMinutos;
    private RichSelectBooleanCheckbox poDomMinutos;
    private RichInputNumberSpinbox poCadaHoras;
    private RichSelectBooleanCheckbox poLunHoras;
    private RichSelectBooleanCheckbox poMarHoras;
    private RichSelectBooleanCheckbox poMieHoras;
    private RichInputNumberSpinbox poBeginHoras;
    
    String              lsAppModule = "AppModuleIntergrationDataControl";
    String              lsAmDef = "com.televisa.integration.model.AppModuleIntergrationImpl";
    String              lsConfig = "AppModuleIntergrationLocal";

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
    private RichSelectBooleanCheckbox poJueHoras;
    private RichSelectBooleanCheckbox poVieHoras;
    private RichSelectBooleanCheckbox poSabHoras;
    private RichSelectBooleanCheckbox poDomHoras;
    

    public TaskConfigBean() {
    }

    public void setPoFilterNomServiceSel(RichSelectOneChoice poFilterNomServiceSel) {
        this.poFilterNomServiceSel = poFilterNomServiceSel;
    }

    public RichSelectOneChoice getPoFilterNomServiceSel() {
        return poFilterNomServiceSel;
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
        ViewObjectDao              loMd = new ViewObjectDao();
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

    /**
     * Guarda en base de datos la configuracion realizada
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String saveConfigurationAction() {
        RichPanelTabbed richPanelTabbed = getPoPanelTabbed();
        for (UIComponent child : richPanelTabbed.getChildren()) {
            RichShowDetailItem sdi = (RichShowDetailItem)child;            
            System.out.println("Nombre ["+sdi.getText()+"]");
            //sdi.setDisclosed(isThisItemToBeDisclosed(sdi));
        }        
        
        Integer liIdConfiguration = null;
        Integer liIdService = null;
        String lsIndPeriodicity = null;
        String lsIndBeginSchedule = null;
        String lsIndTypeSchedule = null;    
        String lsIndValTypeSchedule = null;    
        Integer liIndDayMonth = null;
        Integer liIndWeekMonth = null;
        String lsIndCronExpression   = null;         
        Integer liIndMonday = null;
        Integer liIndTuesday = null;
        Integer liIndWednesday = null;
        Integer liIndThursday = null;
        Integer liIndFriday = null;
        Integer liIndSaturday = null;
        Integer liIndSunday = null;
        String lsStatusTab = "1";
        
        liIdService = getPoFilterNomServiceSel().getValue() == null ? 0 : 
            Integer.parseInt(getPoFilterNomServiceSel().getValue().toString());
        
        lsIndPeriodicity = 
            getPoIdTabSelected().getValue() == null ? "" : 
            getPoIdTabSelected().getValue().toString();        
        System.out.println("Seleccionado ["+lsIndPeriodicity+"]");
        
        if(liIdService > 0){
            
            if(lsIndPeriodicity.equalsIgnoreCase("MINUTOS")){
                //Obtener ID_CONFIGURATION
                liIdConfiguration = new ViewObjectDao().getMaxIdParadigm("Cron") + 1;
                lsIndBeginSchedule = null;
                lsIndTypeSchedule = "CADA";                
                lsIndValTypeSchedule = 
                    getPoCadaMinutos().getValue() == null ? null : 
                    getPoCadaMinutos().getValue().toString();
                liIndDayMonth = null;
                liIndWeekMonth = null;
                lsIndCronExpression   = new UtilFaces().getCronExpression("http://www.cronmaker.com/rest/minutes/2"); 
                
                String lsLunes = getPoLunMinutos().getValue() == null ? "" : getPoLunMinutos().getValue().toString();
                if(lsLunes.equalsIgnoreCase("true")){liIndMonday = 1;}else{liIndMonday = 0;}
                
                String lsMartes = getPoMarMinutos().getValue() == null ? "" : getPoMarMinutos().getValue().toString();
                if(lsMartes.equalsIgnoreCase("true")){liIndTuesday = 1;}else{liIndTuesday = 0;}
                
                String lsMiercoles = getPoMieMinutos().getValue() == null ? "" : getPoMieMinutos().getValue().toString();
                if(lsMiercoles.equalsIgnoreCase("true")){liIndWednesday = 1;}else{liIndWednesday = 0;}
                
                String lsJueves = getPoJueMinutos().getValue() == null ? "" : getPoJueMinutos().getValue().toString();
                if(lsJueves.equalsIgnoreCase("true")){liIndThursday = 1;}else{liIndThursday = 0;}
                
                String lsViernes = getPoVieMinutos().getValue() == null ? "" : getPoVieMinutos().getValue().toString();
                if(lsViernes.equalsIgnoreCase("true")){liIndFriday = 1;}else{liIndFriday = 0;}
                
                String lsSabado = getPoSabMinutos().getValue() == null ? "" : getPoSabMinutos().getValue().toString();
                if(lsSabado.equalsIgnoreCase("true")){liIndSaturday = 1;}else{liIndSaturday = 0;}
                
                String lsDomingo = getPoDomMinutos().getValue() == null ? "" : getPoDomMinutos().getValue().toString();
                if(lsDomingo.equalsIgnoreCase("true")){liIndSunday = 1;}else{liIndSunday = 0;}
                                
            }
            
        }
        
        
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
            System.out.println("minutos");
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
            System.out.println("horas");
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
            System.out.println("DIAS");
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
            System.out.println("Semanas");
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
            System.out.println("meses");
            getPoIdTabSelected().setValue("MESES");
        }
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
}
