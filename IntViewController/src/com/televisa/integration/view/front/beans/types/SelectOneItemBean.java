/**
* Project: Paradigm - eVeTV Integration
*
* File: SelectOneItemBean.java
*
* Created on:  Septiembre 6, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package com.televisa.integration.view.front.beans.types;

/** Esta clase es un bean que representa un lista de valores<br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Septiembre 14, 2017, 12:00 pm
 */
public class SelectOneItemBean {
    private String lsId;
    private String lsValue;
    private String lsDescription;

    public void setLsId(String lsId) {
        this.lsId = lsId;
    }

    public String getLsId() {
        return lsId;
    }

    public void setLsValue(String lsValue) {
        this.lsValue = lsValue;
    }

    public String getLsValue() {
        return lsValue;
    }

    public void setLsDescription(String lsDescription) {
        this.lsDescription = lsDescription;
    }

    public String getLsDescription() {
        return lsDescription;
    }

}
