/**
* Project: Paradigm - eVeTV Integration
*
* File: UserInfoBean.java
*
* Created on:  Septiembre 6, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/

package com.televisa.integration.view.users;

/** Esta clase es un bean para almacenar los datos informativos del usuario en 
  * sesion <br/><br/>  
  * 
  * @author Jorge Luis Bautista - Omw
  * 
  * @version 01.00.01
  * 
  * @date Septiembre 14, 2017, 12:00 pm
  */
public class UserMenuBean {
    public UserMenuBean() {
        super();
    }
    
    private String lsRolMonitor;
    private String lsRolServicesConfig;
    private String lsRolTaskConfig;
    private String lsRolNotificationsConfig;
    private String lsRolParametersConfig;
    private String lsRolGeneralParams;
    private String lsOprCrud;
    private String lsRolBitacora;
    private String lsRolUsrVtaTradicional;

    public void setLsRolUsrVtaTradicional(String lsRolUsrVtaTradicional) {
        this.lsRolUsrVtaTradicional = lsRolUsrVtaTradicional;
    }

    public String getLsRolUsrVtaTradicional() {
        return lsRolUsrVtaTradicional;
    }


    public void setLsRolBitacora(String lsRolBitacora) {
        this.lsRolBitacora = lsRolBitacora;
    }

    public String getLsRolBitacora() {
        return lsRolBitacora;
    }

    public void setLsOprCrud(String lsOprCrud) {
        this.lsOprCrud = lsOprCrud;
    }

    public String getLsOprCrud() {
        return lsOprCrud;
    }

    public void setLsOprInsertCron(String lsOprInsertCron) {
        this.lsOprInsertCron = lsOprInsertCron;
    }

    public String getLsOprInsertCron() {
        return lsOprInsertCron;
    }

    public void setLsOprDeleteCron(String lsOprDeleteCron) {
        this.lsOprDeleteCron = lsOprDeleteCron;
    }

    public String getLsOprDeleteCron() {
        return lsOprDeleteCron;
    }

    public void setLsOprExecuteCron(String lsOprExecuteCron) {
        this.lsOprExecuteCron = lsOprExecuteCron;
    }

    public String getLsOprExecuteCron() {
        return lsOprExecuteCron;
    }

    public void setLsOprInitStopCron(String lsOprInitStopCron) {
        this.lsOprInitStopCron = lsOprInitStopCron;
    }

    public String getLsOprInitStopCron() {
        return lsOprInitStopCron;
    }
    private String lsOprInsertCron;
    private String lsOprDeleteCron;
    private String lsOprExecuteCron;
    private String lsOprInitStopCron;
    
    public void setLsRolMonitor(String lsRolMonitor) {
        this.lsRolMonitor = lsRolMonitor;
    }

    public String getLsRolMonitor() {
        return lsRolMonitor;
    }

    public void setLsRolServicesConfig(String lsRolServicesConfig) {
        this.lsRolServicesConfig = lsRolServicesConfig;
    }

    public String getLsRolServicesConfig() {
        return lsRolServicesConfig;
    }

    public void setLsRolTaskConfig(String lsRolTaskConfig) {
        this.lsRolTaskConfig = lsRolTaskConfig;
    }

    public String getLsRolTaskConfig() {
        return lsRolTaskConfig;
    }

    public void setLsRolNotificationsConfig(String lsRolNotificationsConfig) {
        this.lsRolNotificationsConfig = lsRolNotificationsConfig;
    }

    public String getLsRolNotificationsConfig() {
        return lsRolNotificationsConfig;
    }

    public void setLsRolParametersConfig(String lsRolParametersConfig) {
        this.lsRolParametersConfig = lsRolParametersConfig;
    }

    public String getLsRolParametersConfig() {
        return lsRolParametersConfig;
    }

    public void setLsRolGeneralParams(String lsRolGeneralParams) {
        this.lsRolGeneralParams = lsRolGeneralParams;
    }

    public String getLsRolGeneralParams() {
        return lsRolGeneralParams;
    }
    
    
}
