/**
* Project: Integraton Paradigm - EveTV
*
* File: RsstProgramasBean.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.model.beans;

/** Bean para resultset de consulta de Programas
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class RsstProgramasBean {
    private String lsIdRequest;
    private String lsBuyuntid;
    private String lsBuylnam;
    private String lsIndactivo;
    private String lsIndtarifaprograma;

    public void setLsIdRequest(String lsIdRequest) {
        this.lsIdRequest = lsIdRequest;
    }

    public String getLsIdRequest() {
        return lsIdRequest;
    }

    public void setLsBuyuntid(String lsBuyuntid) {
        this.lsBuyuntid = lsBuyuntid;
    }

    public String getLsBuyuntid() {
        return lsBuyuntid;
    }

    public void setLsBuylnam(String lsBuylnam) {
        this.lsBuylnam = lsBuylnam;
    }

    public String getLsBuylnam() {
        return lsBuylnam;
    }

    public void setLsIndactivo(String lsIndactivo) {
        this.lsIndactivo = lsIndactivo;
    }

    public String getLsIndactivo() {
        return lsIndactivo;
    }

    public void setLsIndtarifaprograma(String lsIndtarifaprograma) {
        this.lsIndtarifaprograma = lsIndtarifaprograma;
    }

    public String getLsIndtarifaprograma() {
        return lsIndtarifaprograma;
    }
}
