/**
* Project: Integraton Paradigm - EveTV
*
* File: ParrillaCorteMapChannels.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.service.beans.types;

/** Clase Bean para almacenar el break del corte en el modulo de Parrillas de Programas y Cortes
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class ParrillaCorteMapCorteBreaks {
    /* Key */
    private String lsChannel;
    private String lsDateValue;
    private String lsHourStart;
    private String lsHourEnd;
    private String lsBuyuntidCorte;    
    private String lsDateCorte;
    private String lsHourCorte;
    private String lsCorteid;
    /* **** */
    private String lsBreakid;    
    private String lsDescription;    
    private String lsDateBreak; 
    private String lsHourBreak;   
    private String lsTotalduration;
    private String lsComercialduration;
    private String lsTypeBreak;

    public void setLsTypeBreak(String lsTypeBreak) {
        this.lsTypeBreak = lsTypeBreak;
    }

    public String getLsTypeBreak() {
        return lsTypeBreak;
    }

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

    public void setLsBreakid(String lsBreakid) {
        this.lsBreakid = lsBreakid;
    }

    public String getLsBreakid() {
        return lsBreakid;
    }

    public void setLsDescription(String lsDescription) {
        this.lsDescription = lsDescription;
    }

    public String getLsDescription() {
        return lsDescription;
    }

    public void setLsDateBreak(String lsDateBreak) {
        this.lsDateBreak = lsDateBreak;
    }

    public String getLsDateBreak() {
        return lsDateBreak;
    }

    public void setLsHourBreak(String lsHourBreak) {
        this.lsHourBreak = lsHourBreak;
    }

    public String getLsHourBreak() {
        return lsHourBreak;
    }

    public void setLsTotalduration(String lsTotalduration) {
        this.lsTotalduration = lsTotalduration;
    }

    public String getLsTotalduration() {
        return lsTotalduration;
    }

    public void setLsComercialduration(String lsComercialduration) {
        this.lsComercialduration = lsComercialduration;
    }

    public String getLsComercialduration() {
        return lsComercialduration;
    }
    
    
    
}
