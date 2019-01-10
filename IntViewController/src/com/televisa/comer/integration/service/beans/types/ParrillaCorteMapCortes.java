/**
* Project: Integraton Paradigm - EveTV
*
* File: ParrillaCorteMapCortes.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.service.beans.types;

/** Clase Bean para almacenrar el corte en el modulo de Parrillas de Programas y Cortes
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class ParrillaCorteMapCortes {
    /* KEY */
    private String lsChannel;
    private String lsDateValue;
    private String lsHourStart;
    private String lsHourEnd;
    /* ***** */
    private String lsBuyuntidCorte;    
    private String lsDateCorte;
    private String lsHourCorte;
    private String lsCorteid;

    public void setLsChannel(String lsChannel) {
        this.lsChannel = lsChannel;
    }

    public String getLsChannel() {
        return lsChannel;
    }

    public void setLsDateValue(String lsDateValue) {
        this.lsDateValue = lsDateValue;
    }

    public String getLsDateValue() {
        return lsDateValue;
    }

    public void setLsHourStart(String lsHourStart) {
        this.lsHourStart = lsHourStart;
    }

    public String getLsHourStart() {
        return lsHourStart;
    }

    public void setLsHourEnd(String lsHourEnd) {
        this.lsHourEnd = lsHourEnd;
    }

    public String getLsHourEnd() {
        return lsHourEnd;
    }

    public void setLsBuyuntidCorte(String lsBuyuntidCorte) {
        this.lsBuyuntidCorte = lsBuyuntidCorte;
    }

    public String getLsBuyuntidCorte() {
        return lsBuyuntidCorte;
    }

    public void setLsDateCorte(String lsDateCorte) {
        this.lsDateCorte = lsDateCorte;
    }

    public String getLsDateCorte() {
        return lsDateCorte;
    }

    public void setLsHourCorte(String lsHourCorte) {
        this.lsHourCorte = lsHourCorte;
    }

    public String getLsHourCorte() {
        return lsHourCorte;
    }

    public void setLsCorteid(String lsCorteid) {
        this.lsCorteid = lsCorteid;
    }

    public String getLsCorteid() {
        return lsCorteid;
    }

    public void setLsDescorte(String lsDescorte) {
        this.lsDescorte = lsDescorte;
    }

    public String getLsDescorte() {
        return lsDescorte;
    }

    public void setLsOverlay(String lsOverlay) {
        this.lsOverlay = lsOverlay;
    }

    public String getLsOverlay() {
        return lsOverlay;
    }
    private String lsDescorte;
    private String lsOverlay;
}
