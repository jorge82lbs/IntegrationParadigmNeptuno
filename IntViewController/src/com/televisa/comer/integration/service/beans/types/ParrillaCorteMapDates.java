/**
* Project: Integraton Paradigm - EveTV
*
* File: ParrillaCorteMapDates.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.service.beans.types;

/** Clase Bean para almacenrar el Date en el modulo de Parrillas de Programas y Cortes
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class ParrillaCorteMapDates {
    private String lsChannel;
    private String lsDateValue;
    private String lsHourStart;
    private String lsHourEnd;

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
        if(lsHourStart == null || lsHourStart.trim().equals("")) {
            lsHourStart = "22:30:00";
        }
        this.lsHourStart = lsHourStart;
    }

    public String getLsHourStart() {
        return lsHourStart;
    }

    public void setLsHourEnd(String lsHourEnd) {
        if(lsHourEnd == null || lsHourEnd.trim().equals("")) {
            lsHourEnd = "00:00:00";
        }
        this.lsHourEnd = lsHourEnd;
    }

    public String getLsHourEnd() {
        return lsHourEnd;
    }
}
