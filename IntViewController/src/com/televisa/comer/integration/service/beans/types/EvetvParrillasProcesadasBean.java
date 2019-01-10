/**
* Project: Integraton Paradigm - EveTV
*
* File: EvetvParrillasProcesadasBean.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.service.beans.types;

/** Clase Bean para mapear la tabla de parrillas procesadas
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class EvetvParrillasProcesadasBean {
    private String lsStnid;
    private String lsBcstdt;
    private String lsPgmid;
    private String lsBrkdtid;

    public void setLsBrkdtid(String lsBrkdtid) {
        this.lsBrkdtid = lsBrkdtid;
    }

    public String getLsBrkdtid() {
        return lsBrkdtid;
    }

    public void setLsStnid(String lsStnid) {
        this.lsStnid = lsStnid;
    }

    public String getLsStnid() {
        return lsStnid;
    }

    public void setLsBcstdt(String lsBcstdt) {
        this.lsBcstdt = lsBcstdt;
    }

    public String getLsBcstdt() {
        return lsBcstdt;
    }

    public void setLsPgmid(String lsPgmid) {
        this.lsPgmid = lsPgmid;
    }

    public String getLsPgmid() {
        return lsPgmid;
    }

    public void setLsStatus(String lsStatus) {
        this.lsStatus = lsStatus;
    }

    public String getLsStatus() {
        return lsStatus;
    }
    private String lsStatus;
}
