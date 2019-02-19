 /**
 * Project: Integraton Paradigm - EveTV
 *
 * File: ViewObjectDao.java
 *
 * Created on: Septiembre 23, 2017 at 11:00
 *
 * Copyright (c) - OMW - 2017
 */
package com.televisa.integration.view.front.daos;

import com.televisa.integration.model.types.EvetvIntCronConfigTabRowBean;
import com.televisa.integration.model.types.EvetvIntServicesParamTabBean;
import com.televisa.integration.view.front.beans.types.SelectOneItemBean;
import com.televisa.integration.view.front.interfaces.ViewObjectInterface;

import com.televisa.integration.view.front.util.ConnectionAs400;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Esta clase acede al modelo para funciones genericas<br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class ViewObjectDao implements ViewObjectInterface {
   
    /**
     * Obtiene el numero maximo de la tabla de servicio de catalogos
     * @autor Jorge Luis Bautista Santiago
     * @return Integer
     */
    @Override
    public Integer getMaxIdServicesCatalog() {
        Integer    liReturn = 0; 
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = getQueryMaxServicesCatalog();
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                liReturn = loRs.getInt(1);
            }
        } catch (SQLException loExSql) {
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        
        return liReturn;
    }

    /**
     * Genera intruccion para obtenter el numero maximo de la tabla de servicio de catalogos
     * @autor Jorge Luis Bautista Santiago
     * @return String 
     */
    @Override
    public String getQueryMaxServicesCatalog() {
        String lsQuery = 
        "SELECT coalesce(MAX(ID_SERVICE),0) \n" + 
        "  FROM EVENTAS.EVETV_INT_SERVICES_CAT_TAB";
        return lsQuery;
    }

    /**
     * Obtiene dinamicamente el numero maximo de la tabla por parametro
     * @autor Jorge Luis Bautista Santiago
     * @param tsTable
     * @return Integer
     */
    @Override
    public Integer getMaxIdParadigm(String tsTable) {
        Integer    liReturn = 0; 
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsTable = "";
        String     lsField = "";
        if(tsTable.equalsIgnoreCase("Parameters")){
            lsTable = "EVENTAS.EVETV_INT_CONFIG_PARAM_TAB";
            lsField = "ID_PARAMETER";
        }
        if(tsTable.equalsIgnoreCase("Notifications")){
            lsTable = "EVENTAS.EVETV_INT_NOTIFICATIONS_TAB";
            lsField = "ID_NOTIFICATION";
        }
        if(tsTable.equalsIgnoreCase("Cron")){
            lsTable = "EVENTAS.EVETV_INT_CRON_CONFIG_TAB";
            lsField = "ID_CONFIGURATION";
        }
        if(tsTable.equalsIgnoreCase("Log")){
            lsTable = "EVENTAS.EVETV_INT_SERVICES_LOG_TAB";
            lsField = "ID_LOG_SERVICES";
        }
        if(tsTable.equalsIgnoreCase("Bit")){
            lsTable = "EVENTAS.EVETV_INT_SERVICE_BITACORA_TAB";
            lsField = "ID_BITACORA";
        }
        if(tsTable.equalsIgnoreCase("RstProgramas")){
            lsTable = "EVENTAS.EVETV_INT_RST_PROGRAMAS_TAB";
            lsField = "ID_REQUEST";
        }
        if(tsTable.equalsIgnoreCase("RstParrillas")){
            lsTable = "EVENTAS.EVETV_INT_RST_PARRILLAS_TAB";
            lsField = "ID_REQUEST";
        }
        if(tsTable.equalsIgnoreCase("RstLogCertificado")){
            lsTable = "EVENTAS.EVETV_INT_RST_LOG_CERT_TAB";
            lsField = "ID_REQUEST";
        }
        if(tsTable.equalsIgnoreCase("RstVtaTradicional")){
            lsTable = "EVENTAS.EVETV_INT_RST_VTRADICIONAL_TAB";
            lsField = "ID_REQUEST";
        }
        if(tsTable.equalsIgnoreCase("RstXmlFiles")){
            try {
                lsTable = "EVENTAS.EVETV_INT_XML_FILES_TAB";
                lsField = "ID_FILE_XML";
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("err sleep22");
            }
        }
        if(tsTable.equalsIgnoreCase("Request")){
            lsTable = "EVENTAS.EVETV_INT_REQUESTS_TAB";
            //lsTable = "EVENTAS.EVETV_INT_RST_TARGETS_TAB";
            lsField = "ID_REQUEST";
        }
        
        
        String lsQueryParadigm = getQueryMaxParadigm(lsTable,lsField);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                liReturn = loRs.getInt(1);
            }
        } catch (SQLException loExSql) {
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liReturn;
    }

    /**
     * Genera dinamicamente intruccion para obtenter el numero maximo de la tabla por parametro
     * @autor Jorge Luis Bautista Santiago
     * @param tsTable
     * @param tsField
     * @return String 
     */
    @Override
    public String getQueryMaxParadigm(String tsTable, String tsField) {
        String lsQuery = 
        "SELECT coalesce(MAX(" + tsField + "),0) \n" + 
        "  FROM " + tsTable;
        return lsQuery;
    }
    
    /**
     * Obtiene identificador del servicio en base al nombre del servicio
     * @autor Jorge Luis Bautista Santiago
     * @param tsNomService
     * @return String 
     */
    @Override
    public String getIdServiceByNomService(String tsNomService) {
        String     liReturn = "0"; 
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = 
        "     SELECT ID_SERVICE\n" + 
        "       FROM EVENTAS.EVETV_INT_SERVICES_CAT_TAB       \n" + 
        "      WHERE NOM_SERVICE = '" + tsNomService + "'";
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                liReturn = loRs.getString(1);
            }
        } catch (SQLException loExSql) {
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liReturn;
    }

    /**
     * Obtiene identificador del proceso en base al nombre del proceso
     * @autor Jorge Luis Bautista Santiago
     * @param tsNomParameter
     * @return String 
     */
    @Override
    public String getProcessIdByNomParameter(String tsNomParameter) {
        String     liReturn = "0"; 
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = "SELECT ID_PARAMETER         \n" + 
        "     FROM EVENTAS.EVETV_INT_CONFIG_PARAM_TAB\n" + 
        "    WHERE IND_USED_BY = 'PROCESS_INTEGRATION'\n" + 
        "      AND NOM_PARAMETER  = '" + tsNomParameter + "'";
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                liReturn = loRs.getString(1);
            }
        } catch (SQLException loExSql) {
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liReturn;
    }

    /**
     * Obtiene grupo de usuarios en base a la descripcion en parametros generales
     * @autor Jorge Luis Bautista Santiago
     * @param tsDescParameter
     * @return String 
     */
    @Override
    public String getUsersGroupByDescParameter(String tsDescParameter) {
        String     liReturn = "0"; 
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = "  SELECT NOM_PARAMETER          \n" + 
        "     FROM EVENTAS.EVETV_INT_CONFIG_PARAM_TAB\n" + 
        "    WHERE IND_USED_BY        = 'USERS_GROUP_INTEGRATION'\n" + 
        "      AND IND_DESC_PARAMETER = '" + tsDescParameter + "'";
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                liReturn = loRs.getString(1);
            }
        } catch (SQLException loExSql) {
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liReturn;
    }

    /**
     * Obtiene todos los servicios configurados
     * @autor Jorge Luis Bautista Santiago
     * @return List
     */
    @Override
    public List<SelectOneItemBean> getListAllWebServicesModel() {
        List<SelectOneItemBean> laReturn = new ArrayList<SelectOneItemBean>();
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        String                  lsQueryParadigm = getQueryAllWebServices();        
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                SelectOneItemBean loItem = new SelectOneItemBean();
                loItem.setLsId(loRs.getString("ID_SERVICE"));
                loItem.setLsValue(loRs.getString("IND_DESC_SERVICE"));
                loItem.setLsDescription(loRs.getString("NOM_SERVICE"));
                laReturn.add(loItem);
            }
        } catch (SQLException loExSql) {
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return laReturn;
    }

    /**
     * Genera instruccion para obtener todos los servicios configurados
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    @Override
    public String getQueryAllWebServices() {
        String lsQuery = "SELECT ID_SERVICE,\n" + 
        "       NOM_SERVICE,\n" + 
        "       IND_DESC_SERVICE,\n" + 
        "       IND_SERVICE_WSDL\n" + 
        "  FROM EVENTAS.EVETV_INT_SERVICES_CAT_TAB";
        return lsQuery;
    }

    /**
     * Obtiene liesta de Parametros generales configurados
     * @autor Jorge Luis Bautista Santiago
     * @param tsArgs
     * @return List
     */
    @Override
    public List<SelectOneItemBean> getListGeneralParametersModelFilter(String tsArgs) {
        List<SelectOneItemBean> laReturn = new ArrayList<SelectOneItemBean>();
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        String                  lsQueryParadigm = getQueryGeneralParameters(tsArgs);        
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                SelectOneItemBean loItem = new SelectOneItemBean();
                loItem.setLsId(loRs.getString("ID_PARAMETER"));
                loItem.setLsValue(loRs.getString("NOM_PARAMETER"));
                loItem.setLsDescription(loRs.getString("IND_DESC_PARAMETER"));
                laReturn.add(loItem);
            }
        } catch (SQLException loExSql) {
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return laReturn;
    }

    /**
     * Genera instruccion para obtener todos los servicios configurados
     * @autor Jorge Luis Bautista Santiago
     * @param tsArgs
     * @return String
     */
    @Override
    public String getQueryGeneralParameters(String tsArgs) {
        String lsQuery = " SELECT ID_PARAMETER,\n" + 
                "          NOM_PARAMETER,\n" + 
                "          IND_DESC_PARAMETER,      \n" + 
                "          IND_VALUE_PARAMETER\n" + 
                "     FROM EVENTAS.EVETV_INT_CONFIG_PARAM_TAB     \n" + 
                "    WHERE IND_USED_BY = '" + tsArgs + "'";
        return lsQuery;
    }
    
    /**
     * Obtiene count de parametros generales
     * @autor Jorge Luis Bautista Santiago
     * @return Integer
     */
    public Integer getCountGeneralParam() {
        Integer     liReturn = 0; 
        try{
            Connection loCnn = new ConnectionAs400().getConnection();
            ResultSet  loRs = null;
            String     lsQueryParadigm = 
            "SELECT COUNT(1) FROM EVENTAS.EVETV_INT_CONFIG_PARAM_TAB";
            try {
                Statement loStmt = loCnn.createStatement();
                loRs = loStmt.executeQuery(lsQueryParadigm);  
                while(loRs.next()){
                    liReturn = loRs.getInt(1);
                }
            } catch (SQLException loExSql) {
                liReturn = -1;
                //loExSql.printStackTrace();
            }
            finally{
                try {
                    loCnn.close();
                    loRs.close();
                } catch (SQLException loEx) {
                    loEx.printStackTrace();
                }
            }
        }catch(Exception loEx){
            liReturn = -1; 
        }
        return liReturn;
    }
    
    
    /**
     * Obtiene configuracion cron de un servicio
     * @autor Jorge Luis Bautista Santiago
     * @return EvetvIntCronConfigTabRowBean
     */
    public EvetvIntCronConfigTabRowBean getRowCronConfigByServiceModel(Integer tiService){
        EvetvIntCronConfigTabRowBean  loServiceBean = new EvetvIntCronConfigTabRowBean();
        Connection                    loCnn = new ConnectionAs400().getConnection();
        ResultSet                     loRs = null;
        Integer                       liFlag = getRowCronConfigByServiceModelCount(tiService);
        if(liFlag > 0){
            String lsQueryParadigm = getQueryServicesExecution(tiService);
            try {
                Statement loStmt = loCnn.createStatement();
                loRs = loStmt.executeQuery(lsQueryParadigm);  
                while(loRs.next()){      
                    
                    loServiceBean.setIdConfiguration(loRs.getInt("ID_CONFIGURATION"));
                    
                    loServiceBean.setIdService(loRs.getInt("ID_SERVICE"));
                    
                    loServiceBean.setIndPeriodicity(loRs.getString("IND_PERIODICITY"));  
                    
                    loServiceBean.setIndBeginSchedule(loRs.getString("IND_BEGIN_SCHEDULE"));  
                    
                    loServiceBean.setIndTypeSchedule(loRs.getString("IND_TYPE_SCHEDULE"));  
                    loServiceBean.setIndValTypeSchedule(loRs.getString("IND_VAL_TYPE_SCHEDULE"));  
                    loServiceBean.setIndMonday(loRs.getInt("IND_MONDAY"));
                    loServiceBean.setIndTuesday(loRs.getInt("IND_TUESDAY"));
                    
                    loServiceBean.setIndWednesday(loRs.getInt("IND_WEDNESDAY"));
                    
                    loServiceBean.setIndThursday(loRs.getInt("IND_THURSDAY"));
                    
                    loServiceBean.setIndFriday(loRs.getInt("IND_FRIDAY"));
                    
                    loServiceBean.setIndSaturday(loRs.getInt("IND_SATURDAY"));
                    
                    loServiceBean.setIndSunday(loRs.getInt("IND_SUNDAY"));
                    
                    loServiceBean.setIndDayMonth(loRs.getInt("IND_DAY_MONTH"));
                    
                    loServiceBean.setIndWeekMonth(loRs.getInt("IND_WEEK_MONTH"));
                    
                    loServiceBean.setIndCronExpression(loRs.getString("IND_CRON_EXPRESSION"));                
                    
                    loServiceBean.setIndEstatus(loRs.getString("IND_ESTATUS")); 
                    
                    loServiceBean.setAttribute1(loRs.getString("ATTRIBUTE1"));  
                    
                    loServiceBean.setAttribute14(loRs.getString("ATTRIBUTE14"));  
                    loServiceBean.setAttribute13(loRs.getString("ATTRIBUTE13"));  
                    loServiceBean.setNumLastUpdateBy(loRs.getInt("NUM_LAST_UPDATED_BY")); 
                }
            } catch (SQLException loExSql) {
                System.out.println("ERROR AL EJECUTAR: ");
                System.out.println(lsQueryParadigm);
                loExSql.printStackTrace();
            }
            finally{
                try {
                    loCnn.close();
                    loRs.close();
                } catch (SQLException loEx) {
                    loEx.printStackTrace();
                }
            }
        }else{
            loServiceBean = null;
        }
        return loServiceBean;
    }
    
    /**
     * Genera instruccion  para obtener configuracion cron de un servicio
     * @autor Jorge Luis Bautista Santiago
     * @return EvetvIntCronConfigTabRowBean
     */
    public String getQueryServicesExecution(Integer tiService){
        String lsQuery = " SELECT ID_CONFIGURATION,\n" + 
        "        ID_SERVICE,\n" + 
        "        IND_PERIODICITY,\n" + 
        "        IND_BEGIN_SCHEDULE,\n" + 
        "        IND_TYPE_SCHEDULE,\n" + 
        "        IND_VAL_TYPE_SCHEDULE,\n" + 
        "        IND_MONDAY,\n" + 
        "        IND_TUESDAY,\n" + 
        "        IND_WEDNESDAY,\n" + 
        "        IND_THURSDAY,\n" + 
        "        IND_FRIDAY,\n" + 
        "        IND_SATURDAY,\n" + 
        "        IND_SUNDAY,\n" + 
        "        IND_DAY_MONTH,\n" + 
        "        IND_WEEK_MONTH,\n" + 
        "        IND_CRON_EXPRESSION,\n" + 
        "        IND_ESTATUS,\n" + 
        "        ATTRIBUTE_CATEGORY,\n" + 
        "        ATTRIBUTE1,\n" + 
        "        ATTRIBUTE2,\n" + 
        "        ATTRIBUTE3,\n" + 
        "        ATTRIBUTE4,\n" + 
        "        ATTRIBUTE5,\n" + 
        "        ATTRIBUTE6,\n" + 
        "        ATTRIBUTE7,\n" + 
        "        ATTRIBUTE8,\n" + 
        "        ATTRIBUTE9,\n" + 
        "        ATTRIBUTE10,\n" + 
        "        ATTRIBUTE11,\n" + 
        "        ATTRIBUTE12,\n" + 
        "        ATTRIBUTE13,\n" + 
        "        ATTRIBUTE14,\n" + 
        "        ATTRIBUTE15,\n" + 
        "        NUM_LAST_UPDATED_BY\n" +         
        "   FROM EVENTAS.EVETV_INT_CRON_CONFIG_TAB\n" + 
        "  WHERE ID_SERVICE = " + tiService;
        return lsQuery;
    }
    
    /**
     * Obtiene configuracion cron de un servicio
     * @autor Jorge Luis Bautista Santiago
     * @return EvetvIntCronConfigTabRowBean
     */
    public Integer getRowCronConfigByServiceModelCount(Integer tiService){
        Integer                       lbFlag = 0;
        Connection                    loCnn = new ConnectionAs400().getConnection();
        ResultSet                     loRs = null;
        String                        lsQueryParadigm = getQueryCountServicesCron(tiService);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){          
                lbFlag = loRs.getInt(1);
            }
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        System.out.println("Existe configuracion cron? ["+lbFlag+"]");
        return lbFlag;
    }
    
    /**
     * Genera instruccion  para obtener configuracion cron de un servicio
     * @autor Jorge Luis Bautista Santiago
     * @return EvetvIntCronConfigTabRowBean
     */
    public String getQueryCountServicesCron(Integer tiService){
        String lsQuery = " SELECT COUNT(1)\n" + 
        "   FROM EVENTAS.EVETV_INT_CRON_CONFIG_TAB\n" + 
        "  WHERE ID_SERVICE = " + tiService;
        return lsQuery;
    }
    
    /**
     * Obtiene los parametros configurados del servicio
     * @autor Jorge Luis Bautista Santiago
     * @return List
     */
    public List<EvetvIntServicesParamTabBean> getParametersServices(Integer tiService){
        List<EvetvIntServicesParamTabBean> laResponse = new ArrayList<EvetvIntServicesParamTabBean>();
        Connection                    loCnn = new ConnectionAs400().getConnection();
        ResultSet                     loRs = null;
        String                        lsQueryParadigm = getQueryParametersServices(tiService);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){      
                EvetvIntServicesParamTabBean loSvBean = new EvetvIntServicesParamTabBean();
                loSvBean.setLsIdService(loRs.getString("ID_SERVICE"));
                loSvBean.setIndParameter(loRs.getString("IND_PARAMETER") == null ? null : 
                                         loRs.getString("IND_PARAMETER").trim());                
                loSvBean.setIndValParameter(loRs.getString("IND_VAL_PARAMETER") == null ? null : 
                                         loRs.getString("IND_VAL_PARAMETER").trim());                
                loSvBean.setIndEstatus(loRs.getString("IND_ESTATUS") == null ? null : 
                                          loRs.getString("IND_ESTATUS").trim());                
                laResponse.add(loSvBean);
            }
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR: ");
            System.out.println(lsQueryParadigm);
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }                
    
        return laResponse;
    }
    
    /**
     * Genera instruccion para obtener los parametros configurados del servicio
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String getQueryParametersServices(Integer tiService){
        String lsQuery = "SELECT ID_SERVICE,\n" + 
        "       IND_PARAMETER,\n" + 
        "       IND_VAL_PARAMETER,\n" + 
        "       IND_ESTATUS \n" + 
        "  FROM EVENTAS.EVETV_INT_SERVICES_PARAMS_TAB\n" + 
        " WHERE ID_SERVICE = " + tiService;
        return lsQuery;
    }
}
