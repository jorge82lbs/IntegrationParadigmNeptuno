/**
* Project: Paradigm - eVeTV Integration
*
* File: ViewObjectInterface.java
*
* Created on:  Septiembre 6, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package com.televisa.integration.view.front.interfaces;

import com.televisa.integration.view.front.beans.types.SelectOneItemBean;

import java.util.List;


/** Interfaz para implemetarse en conexion a la base de datos
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Septiembre 14, 2017, 12:00 pm
 */
public interface ViewObjectInterface {
    public Integer getMaxIdServicesCatalog();
    public String getQueryMaxServicesCatalog();
    public Integer getMaxIdParadigm(String psTable);
    public String getQueryMaxParadigm(String psTable, String psField);
    public String getIdServiceByNomService(String psNomService);
    public String getProcessIdByNomParameter(String psNomParameter);
    public String getUsersGroupByDescParameter(String psDescParameter);
    public List<SelectOneItemBean> getListAllWebServicesModel();
    public String getQueryAllWebServices();
    public List<SelectOneItemBean> getListGeneralParametersModelFilter(String psArgs);
    public String getQueryGeneralParameters(String psArgs);
}
