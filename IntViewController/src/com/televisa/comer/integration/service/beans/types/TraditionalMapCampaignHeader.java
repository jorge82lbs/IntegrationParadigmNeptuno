/**
* Project: Integraton Paradigm - EveTV
*
* File: TraditionalMapCampaignHeader.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/

package com.televisa.comer.integration.service.beans.types;

/** Clase Bean para almacenrar la respuesta de la invocacion a los servicios de netptuno
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class TraditionalMapCampaignHeader {
    private String lsActionC;    
    private String lsOrderID;
    private String lsAgencyID;
    private String lsAdvertirserID;
    private String lsInitialDate;
    private String lsEndDate;
    private String lsMasterContract;
    private String lsRateCard;
    private String lsTargetID;

    public void setLsActionC(String lsActionC) {
        this.lsActionC = lsActionC;
    }

    public String getLsActionC() {
        return lsActionC;
    }

    public void setLsOrderID(String lsOrderID) {
        this.lsOrderID = lsOrderID;
    }

    public String getLsOrderID() {
        return lsOrderID;
    }

    public void setLsAgencyID(String lsAgencyID) {
        this.lsAgencyID = lsAgencyID;
    }

    public String getLsAgencyID() {
        return lsAgencyID;
    }

    public void setLsAdvertirserID(String lsAdvertirserID) {
        this.lsAdvertirserID = lsAdvertirserID;
    }

    public String getLsAdvertirserID() {
        return lsAdvertirserID;
    }

    public void setLsInitialDate(String lsInitialDate) {
        this.lsInitialDate = lsInitialDate;
    }

    public String getLsInitialDate() {
        return lsInitialDate;
    }

    public void setLsEndDate(String lsEndDate) {
        this.lsEndDate = lsEndDate;
    }

    public String getLsEndDate() {
        return lsEndDate;
    }

    public void setLsMasterContract(String lsMasterContract) {
        this.lsMasterContract = lsMasterContract;
    }

    public String getLsMasterContract() {
        return lsMasterContract;
    }

    public void setLsRateCard(String lsRateCard) {
        this.lsRateCard = lsRateCard;
    }

    public String getLsRateCard() {
        return lsRateCard;
    }

    public void setLsTargetID(String lsTargetID) {
        this.lsTargetID = lsTargetID;
    }

    public String getLsTargetID() {
        return lsTargetID;
    }    
}
