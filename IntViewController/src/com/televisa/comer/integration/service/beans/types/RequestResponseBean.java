/**
* Project: Integraton Paradigm - EveTV
*
* File: RequestResponseBean.java
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
public class RequestResponseBean {
    private String lsRequest;
    private String lsResponse;

    public void setLsRequest(String lsRequest) {
        this.lsRequest = lsRequest;
    }

    public String getLsRequest() {
        return lsRequest;
    }

    public void setLsResponse(String lsResponse) {
        this.lsResponse = lsResponse;
    }

    public String getLsResponse() {
        return lsResponse;
    }
}
