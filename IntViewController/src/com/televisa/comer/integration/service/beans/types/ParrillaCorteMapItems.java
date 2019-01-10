/**
* Project: Integraton Paradigm - EveTV
*
* File: ParrillaCorteMapItems.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.service.beans.types;

/** Clase Bean para almacenrar el Item en el modulo de Parrillas de Programas y Cortes
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class ParrillaCorteMapItems {
    
    /* KEY */
    private String lsChannel;
    private String lsDateValue;
    private String lsHourStart;
    private String lsHourEnd;
    /* ***** */
    
    private String lsHour;    
    private String lsTitle;    
    private String lsEpisodename;    
    private String lsEpno;
    private String lsSlotduration;    
    private String lsTitleid;
    private String lsEpisodenameid;    
    private String lsBuyunitid;    
    private String lsBuyunit;    
    private String lsExceptcofepris;    
    private String lsEventspecial;
    private String lsEventbest;
    
    private String lsGeneroID;
    private String lsGeneroName;

    public void setLsGeneroID(String lsGeneroID) {
        this.lsGeneroID = lsGeneroID;
    }

    public String getLsGeneroID() {
        return lsGeneroID;
    }

    public void setLsGeneroName(String lsGeneroName) {
        this.lsGeneroName = lsGeneroName;
    }

    public String getLsGeneroName() {
        return lsGeneroName;
    }

    public void setLsEventbest(String lsEventbest) {
        this.lsEventbest = lsEventbest;
    }

    public String getLsEventbest() {
        return lsEventbest;
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

    public void setLsHour(String lsHour) {
        if(lsHour == null || lsHour.trim().equals("")) {
            lsHour = "22:30:00";
        }
        this.lsHour = lsHour;
    }

    public String getLsHour() {
        return lsHour;
    }

    public void setLsTitle(String lsTitle) {
        if(lsTitle == null || lsTitle.trim().equals("")) {
            lsTitle = "LA JUGADA 2013";
        }
        this.lsTitle = lsTitle;
    }

    public String getLsTitle() {
        return lsTitle;
    }

    public void setLsEpisodename(String lsEpisodename) {
        if(lsEpisodename == null || lsEpisodename.trim().equals("")) {
            lsEpisodename = "EPISODIO 260";
        }
        this.lsEpisodename = lsEpisodename;
    }

    public String getLsEpisodename() {
        return lsEpisodename;
    }

    public void setLsEpno(String lsEpno) {
        if(lsEpno == null || lsEpno.trim().equals("")) {
            lsEpno = "EPISODIO 260";
        }
        this.lsEpno = lsEpno;
    }

    public String getLsEpno() {
        return lsEpno;
    }

    public void setLsSlotduration(String lsSlotduration) {
        if(lsSlotduration == null || lsSlotduration.trim().equals("")) {
            lsSlotduration = "EPISODIO 260";
        }
        this.lsSlotduration = lsSlotduration;
    }

    public String getLsSlotduration() {
        return lsSlotduration;
    }

    public void setLsTitleid(String lsTitleid) {
        this.lsTitleid = lsTitleid;
    }

    public String getLsTitleid() {
        return lsTitleid;
    }

    public void setLsEpisodenameid(String lsEpisodenameid) {
        this.lsEpisodenameid = lsEpisodenameid;
    }

    public String getLsEpisodenameid() {
        return lsEpisodenameid;
    }

    public void setLsBuyunitid(String lsBuyunitid) {
        this.lsBuyunitid = lsBuyunitid;
    }

    public String getLsBuyunitid() {
        return lsBuyunitid;
    }

    public void setLsBuyunit(String lsBuyunit) {
        this.lsBuyunit = lsBuyunit;
    }

    public String getLsBuyunit() {
        return lsBuyunit;
    }

    public void setLsExceptcofepris(String lsExceptcofepris) {
        this.lsExceptcofepris = lsExceptcofepris;
    }

    public String getLsExceptcofepris() {
        return lsExceptcofepris;
    }

    public void setLsEventspecial(String lsEventspecial) {
        this.lsEventspecial = lsEventspecial;
    }

    public String getLsEventspecial() {
        return lsEventspecial;
    }

}
