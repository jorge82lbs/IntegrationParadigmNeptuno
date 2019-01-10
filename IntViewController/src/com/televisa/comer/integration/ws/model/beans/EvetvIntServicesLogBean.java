/**
* Project: Integraton Paradigm - EveTV
*
* File: EvetvIntServicesLogBean.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/

package com.televisa.comer.integration.ws.model.beans;

 /** Clase Bean para almacenar mapeo con Log de Servicios
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class EvetvIntServicesLogBean {
    private Integer liIdLogServices;
    private Integer liIdService;
    private Integer liIndProcess;
    private String lsIndResponse;
    private Integer liNumUser;
    private Integer liNumEvtbProcessId;
    private Integer liNumPgmProcessId;
    private String lsIndEstatus;  
    private String lsAttribute9;
    private String lsAttribute10;
    private String lsAttribute15;

    public void setLiIdLogServices(Integer liIdLogServices) {
        this.liIdLogServices = liIdLogServices;
    }

    public Integer getLiIdLogServices() {
        return liIdLogServices;
    }

    public void setLiIdService(Integer liIdService) {
        this.liIdService = liIdService;
    }

    public Integer getLiIdService() {
        return liIdService;
    }

    public void setLiIndProcess(Integer liIndProcess) {
        this.liIndProcess = liIndProcess;
    }

    public Integer getLiIndProcess() {
        return liIndProcess;
    }

    public void setLsIndResponse(String lsIndResponse) {
        this.lsIndResponse = lsIndResponse;
    }

    public String getLsIndResponse() {
        return lsIndResponse;
    }

    public void setLiNumUser(Integer liNumUser) {
        this.liNumUser = liNumUser;
    }

    public Integer getLiNumUser() {
        return liNumUser;
    }

    public void setLiNumEvtbProcessId(Integer liNumEvtbProcessId) {
        this.liNumEvtbProcessId = liNumEvtbProcessId;
    }

    public Integer getLiNumEvtbProcessId() {
        return liNumEvtbProcessId;
    }

    public void setLiNumPgmProcessId(Integer liNumPgmProcessId) {
        this.liNumPgmProcessId = liNumPgmProcessId;
    }

    public Integer getLiNumPgmProcessId() {
        return liNumPgmProcessId;
    }

    public void setLsIndEstatus(String lsIndEstatus) {
        this.lsIndEstatus = lsIndEstatus;
    }

    public String getLsIndEstatus() {
        return lsIndEstatus;
    }

    public void setLsAttribute9(String lsAttribute9) {
        this.lsAttribute9 = lsAttribute9;
    }

    public String getLsAttribute9() {
        return lsAttribute9;
    }

    public void setLsAttribute10(String lsAttribute10) {
        this.lsAttribute10 = lsAttribute10;
    }

    public String getLsAttribute10() {
        return lsAttribute10;
    }

    public void setLsAttribute15(String lsAttribute15) {
        this.lsAttribute15 = lsAttribute15;
    }

    public String getLsAttribute15() {
        return lsAttribute15;
    }
}
