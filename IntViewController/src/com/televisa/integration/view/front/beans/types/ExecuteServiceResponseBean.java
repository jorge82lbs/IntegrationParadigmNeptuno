 /**
 * Project: Integraton Paradigm - EveTV
 *
 * File: ExecuteServiceResponseBean.java
 *
 * Created on: Septiembre 23, 2017 at 11:00
 *
 * Copyright (c) - OMW - 2017
 */
package com.televisa.integration.view.front.beans.types;

/** Bean representativo de respuesta id-description<br/><br/>
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class ExecuteServiceResponseBean {
    private String lsColor;
    private String lsMessage;

    public void setLsColor(String lsColor) {
        this.lsColor = lsColor;
    }

    public String getLsColor() {
        return lsColor;
    }

    public void setLsMessage(String lsMessage) {
        this.lsMessage = lsMessage;
    }

    public String getLsMessage() {
        return lsMessage;
    }
}
