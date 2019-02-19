/**
* Project: Integraton Paradigm - EveTV
*
* File: RsstTargetsBean.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.model.beans;

/** Bean para resultset de consulta de Targets
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class RsstTargetsBean {
    public RsstTargetsBean() {
        super();
    }
    
    private String lsDescripcion;
    private String lsDescripcionCorta;
    private String lsDescripcionAudicencia;
    private String lsCodigoExt;
    private String lsStatus;

    public void setLsDescripcionAudicencia(String lsDescripcionAudicencia) {
        this.lsDescripcionAudicencia = lsDescripcionAudicencia;
    }

    public String getLsDescripcionAudicencia() {
        return lsDescripcionAudicencia;
    }

    public void setLsDescripcion(String lsDescripcion) {
        this.lsDescripcion = lsDescripcion;
    }

    public String getLsDescripcion() {
        return lsDescripcion;
    }

    public void setLsDescripcionCorta(String lsDescripcionCorta) {
        this.lsDescripcionCorta = lsDescripcionCorta;
    }

    public String getLsDescripcionCorta() {
        return lsDescripcionCorta;
    }

    public void setLsCodigoExt(String lsCodigoExt) {
        this.lsCodigoExt = lsCodigoExt;
    }

    public String getLsCodigoExt() {
        return lsCodigoExt;
    }

    public void setLsStatus(String lsStatus) {
        this.lsStatus = lsStatus;
    }

    public String getLsStatus() {
        return lsStatus;
    }

}
