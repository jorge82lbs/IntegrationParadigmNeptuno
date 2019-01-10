/**
* Project: Integraton Paradigm - EveTV
*
* File: LogCertificadoInterface.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.model.interfaces;

import com.televisa.comer.integration.model.beans.RsstLogCertificadoBean;

import java.util.List;

/** Interface que define los metodos a utilizar para el modulo de Log Certificado
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public interface LogCertificadoInterface {
    public List<RsstLogCertificadoBean> getLogCertificadoFromParadigm(String tsDate, 
                                                                      String tsChannels);
    public Integer getFlagInsertLogCertificado(String tsDate, 
                                               String tsChannels);
    public String getQueryLogCertificado(String tsDate, 
                                         String tsChannels);
    public String getQueryInsertLogCertificado(String tsDate, 
                                               String tsChannels);
    public Integer getUpdateLogCertificado(String tsDate, 
                                           String tsChannels);
    public String getQueryUpdateLogCertificado(String tsDate, 
                                               String tsChannels);
}
