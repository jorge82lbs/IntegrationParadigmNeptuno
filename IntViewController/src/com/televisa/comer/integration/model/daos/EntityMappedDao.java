/**
* Project: Integraton Paradigm - EveTV
*
* File: EntityMappedDao.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.model.daos;

import com.televisa.comer.integration.model.beans.pgm.EvetvIntServiceBitacoraTab;
import com.televisa.comer.integration.model.connection.ConnectionAs400;
import com.televisa.comer.integration.service.beans.types.EmailDestinationAddress;
import com.televisa.integration.model.types.EvetvIntCronConfigTabRowBean;
import com.televisa.integration.view.front.daos.ViewObjectDao;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** Clase que conecta de forma tradicional a bd
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class EntityMappedDao {

    /**
     * Obtiene Valor de Parametro General
     * @autor Jorge Luis Bautista Santiago
     * @param tsName
     * @param tsUsedBy
     * @return String
     */    
    public String getGeneralParameter(String tsName, String tsUsedBy) {
        String      loValue = "";
        Connection  loCnn = new ConnectionAs400().getConnection();
        ResultSet   loRs = null;
        String      lsQueryParadigm = getQueryGeneralParameter(tsName, tsUsedBy);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loValue = loRs.getString(1);
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
        return loValue;
    }
    
    /**
     * Obtiene Consulta para Valor de Parametro General
     * @autor Jorge Luis Bautista Santiago
     * @param tsName
     * @param tsUsedBy
     * @return String
     */
    public String getQueryGeneralParameter(String psName, String psUsedBy){
        String lsQuery = 
            "SELECT IND_VALUE_PARAMETER \n" + 
            "   FROM EVENTAS.EVETV_INT_CONFIG_PARAM_TAB\n" + 
            "  WHERE IND_USED_BY   = '" + psUsedBy + "'\n" + 
            "    AND NOM_PARAMETER = '" + psName + "'";
        return lsQuery;
    }
    
    /**
     * Inserta archivo xml en base de datos
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsNomFile
     * @param tsIndFileType
     * @param tsIndServiceType
     * @param tsIndEstatus
     * @param tsNomUserName
     * @param toFile
     * @return boolean
     */
    public boolean insertEvetvIntXmlFilesTab(Integer tsIdRequest,
                                             Integer tsIdService,
                                             String tsNomFile,
                                             String tsIndFileType,
                                             String tsIndServiceType,
                                             String tsIndEstatus, 
                                             String tsNomUserName, 
                                             InputStream toFile) {
        boolean     lbResponse = false;
        Connection  loCnn = new ConnectionAs400().getConnection();
        Integer     liIdLogCert = 
            new ViewObjectDao().getMaxIdParadigm("RstXmlFiles") + 1;        
        String      lsFecha = String.valueOf(new Date());                                
        
        try {
            String lsSql = "INSERT INTO EVENTAS.EVETV_INT_XML_FILES_TAB(ID_FILE_XML,\n" + 
            "                                            ID_REQUEST,\n" + 
            "                                            ID_SERVICE,\n" + 
            "                                            NOM_FILE,\n" + 
            "                                            IND_FILE_TYPE,\n" + 
            "                                            IND_SERVICE_TYPE,\n" + 
            "                                            IND_ESTATUS,\n" + 
            "                                            FEC_CREATION_DATE,\n" + 
            "                                            NOM_USER_NAME,\n" + 
            "                                            IND_FILE_STREAM\n" + 
            "                                           )\n" + 
            "                                   VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement loStmt = loCnn.prepareStatement(lsSql);
            loStmt.setInt(1, liIdLogCert);
            loStmt.setInt(2, tsIdRequest);
            loStmt.setInt(3, tsIdService);
            loStmt.setString(4, tsNomFile);
            loStmt.setString(5, tsIndFileType);
            loStmt.setString(6, tsIndServiceType);
            loStmt.setString(7, tsIndEstatus);
            loStmt.setString(8, lsFecha);
            loStmt.setString(9, tsNomUserName);
            loStmt.setBinaryStream(10, toFile);
            lbResponse = loStmt.execute();
            
            
            
            
        } catch (SQLException loExSql) {
            System.out.println("ERROR XML_FILE: "+loExSql.getMessage());
            //loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return lbResponse;
    }
    
    /**
     * Obtiene Valor de Configuracion Sincrona o Asincrona del servicio en cuestion
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdService
     * @return String
     */
    public String getAsyncService(String tsIdService) {
        String     lsValue = "";
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = getQueryAsyncService(tsIdService);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                lsValue = loRs.getString(1);
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
        return lsValue;
    }
    
    /**
     * Obtiene Consulta Valor de Configuracion Sincrona o Asincrona del servicio en cuestion
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdService
     * @return String
     */
    public String getQueryAsyncService(String tsIdService){
        String lsQuery = 
            "SELECT IND_SYNCHRONOUS\n" + 
            "  FROM EVENTAS.EVETV_INT_SERVICES_CAT_TAB \n" + 
            " WHERE ID_SERVICE = "+tsIdService;
        return lsQuery;
    }
    
    public String getQueryEmails(){
        String lsQuery = 
            "SELECT NOM_PARAMETER, IND_VALUE_PARAMETER\n" + 
            "  FROM EVENTAS.EVETV_INT_CONFIG_PARAM_TAB\n" + 
            " WHERE IND_USED_BY   = 'EMAIL_WSINTEGRATION'\n" + 
            "   AND IND_ESTATUS   = '1'";
        return lsQuery;
    }
    
    public String getQueryEmailsDyna(String tsIdService, String tsMailType){
        String lsQuery = 
            "SELECT CPT.NOM_PARAMETER,\n" + 
            "       CPT.IND_VALUE_PARAMETER \n" + 
            "  FROM EVENTAS.EVETV_INT_SERVICES_PARAMS_TAB SVP,\n" + 
            "       EVENTAS.EVETV_INT_CONFIG_PARAM_TAB    CPT\n" + 
            " WHERE SVP.IND_VAL_PARAMETER = CPT.IND_VALUE_PARAMETER\n" + 
            "   AND SVP.IND_ESTATUS       = '1'\n" + 
            "   AND CPT.IND_ESTATUS       = '1'\n" + 
            "   AND SVP.ID_SERVICE        = "+tsIdService+" \n" + 
            "   AND SVP.IND_PARAMETER     = '"+tsMailType+"'\n" + 
            "   AND CPT.IND_USED_BY       = 'EMAIL_WSINTEGRATION'";
        return lsQuery;
    }
    
    /**
     * Obtiene direcciones de correo como destinatarios
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdService
     * @param tsMailType
     * @return List
     */
    public List<EmailDestinationAddress> getDestinationAddress(String tsIdService, String tsMailType){
        List<EmailDestinationAddress> loEmails = new ArrayList<EmailDestinationAddress>();
        Connection                    loCnn = new ConnectionAs400().getConnection();
        ResultSet                     loRs = null;
        String                        lsQueryParadigm = getQueryEmailsDyna(tsIdService,tsMailType);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                EmailDestinationAddress loEmailBean = new EmailDestinationAddress();             
                loEmailBean.setLsNameTo(loRs.getString("NOM_PARAMETER") == null ? null : 
                                          loRs.getString("NOM_PARAMETER").trim());
                loEmailBean.setLsAddressTo(loRs.getString("NOM_PARAMETER") == null ? null : 
                                          loRs.getString("NOM_PARAMETER").trim());                
                loEmails.add(loEmailBean);
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
        return loEmails;
    }
    
    /**
     * Obtiene direcciones de correo como destinatarios
     * @autor Jorge Luis Bautista Santiago
     * @return List
     */
    public List<EvetvIntCronConfigTabRowBean> getServicesCronExecution(){
        List<EvetvIntCronConfigTabRowBean> laServices = new ArrayList<EvetvIntCronConfigTabRowBean>();
        Connection                    loCnn = new ConnectionAs400().getConnection();
        ResultSet                     loRs = null;
        String                        lsQueryParadigm = getQueryServicesExecution();
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                EvetvIntCronConfigTabRowBean loServiceBean = new EvetvIntCronConfigTabRowBean();             
                loServiceBean.setIdService(loRs.getInt("ID_SERVICE"));
                loServiceBean.setIndBeginSchedule(loRs.getString("IND_BEGIN_SCHEDULE"));  
                loServiceBean.setIndCronExpression(loRs.getString("IND_CRON_EXPRESSION"));                
                loServiceBean.setIndEstatus(loRs.getString("IND_ESTATUS"));                
                loServiceBean.setAttribute14(loRs.getString("ATTRIBUTE14"));  
                loServiceBean.setAttribute1(loRs.getString("ATTRIBUTE1"));  
                loServiceBean.setIndPeriodicity(loRs.getString("IND_PERIODICITY"));  
                loServiceBean.setIndValTypeSchedule(loRs.getString("IND_VAL_TYPE_SCHEDULE"));  
                loServiceBean.setNumLastUpdateBy(loRs.getInt("NUM_LAST_UPDATED_BY")); 
                loServiceBean.setAttribute12(loRs.getString("ATTRIBUTE12"));
                loServiceBean.setFecLastUpdateDate(loRs.getTimestamp("FEC_LAST_UPDATE_DATE"));  
                laServices.add(loServiceBean);
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
        return laServices;
    }
    
    public String getQueryServicesExecution(){
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
        "        FEC_LAST_UPDATE_DATE,\n" +         
        "        NUM_LAST_UPDATED_BY\n" +         
        "   FROM EVENTAS.EVETV_INT_CRON_CONFIG_TAB\n" + 
        "  WHERE IND_ESTATUS = '2'";
        return lsQuery;
    }
    
    /**
     * Obtiene Valor de Configuracion Sincrona o Asincrona del servicio en cuestion
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdService
     * @return String
     */
    public String getTypeService(String tsIdService) {
        String     lsValue = "";
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = getQueryTypeService(tsIdService);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                lsValue = loRs.getString(1);
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
        return lsValue;
    }
    
    /**
     * Obtiene Consulta Valor de Configuracion Sincrona o Asincrona del servicio en cuestion
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdService
     * @return String
     */
    public String getQueryTypeService(String tsIdService){
        String lsQuery = 
            "SELECT NOM_SERVICE\n" + 
            "  FROM EVENTAS.EVETV_INT_SERVICES_CAT_TAB \n" + 
            " WHERE ID_SERVICE = " + tsIdService;
        return lsQuery;
    }
    
    /**
     * Obtiene Valor de Configuracion Sincrona o Asincrona del servicio en cuestion
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdService
     * @return String
     */
    public String getIndEstatusService(String tsIdService) {
        String     lsValue = "";
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = getQueryEstatusService(tsIdService);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                lsValue = loRs.getString(1);
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
        return lsValue;
    }
    
    /**
     * Obtiene Consulta Valor de Configuracion Sincrona o Asincrona del servicio en cuestion
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdService
     * @return String
     */
    public String getQueryEstatusService(String tsIdService){
        String lsQuery = 
            "SELECT IND_ESTATUS\n" + 
            "  FROM EVENTAS.EVETV_INT_SERVICES_CAT_TAB \n" + 
            " WHERE ID_SERVICE = " + tsIdService;
        return lsQuery;
    }
    
    /**
     * Inserta en la tabla de bitacora en base de datos
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvIntServiceBitacoraTab
     * @return Integer
     */
    public Integer insertBitacoraWs(EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTab, 
                                    Integer tiIdUser,
                                    String tsUserName) {
        Integer    loValue = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = 
            getQueryInsertBitacora(toEvetvIntServiceBitacoraTab, tiIdUser, tsUserName);
        try {
            Statement loStmt = loCnn.createStatement();
            loValue = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println(loExSql.getMessage());
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loValue;
    }
    
    /**
     * Genera instruccion para Insertar en la tabla de bitacora en base de datos
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvIntServiceBitacoraTab
     * @return Integer
     */
    public String getQueryInsertBitacora(EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTab, 
                                         Integer tiIdUser,
                                         String tsUserName){
        String lsQuery = 
            "INSERT INTO EVENTAS.EVETV_INT_SERVICE_BITACORA_TAB " +
            "                           (ID_BITACORA,\n" + 
            "                            ID_LOG_SERVICES,\n" + 
            "                            ID_SERVICE,\n" + 
            "                            IND_PROCESS,\n" + 
            "                            NUM_EVTB_PROCESS_ID,\n" + 
            "                            NUM_PGM_PROCESS_ID,\n" + 
            "                            IND_EVENTO,\n" + 
            "                            IND_ESTATUS,\n" + 
            /*"                            ATTRIBUTE_CATEGORY,\n" + 
            "                            ATTRIBUTE1,\n" + 
            "                            ATTRIBUTE2,\n" + 
            "                            ATTRIBUTE3,\n" + 
            "                            ATTRIBUTE4,\n" + 
            "                            ATTRIBUTE5,\n" + 
            "                            ATTRIBUTE6,\n" + 
            "                            ATTRIBUTE7,\n" + 
            "                            ATTRIBUTE8,\n" + 
            "                            ATTRIBUTE9,\n" + 
            "                            ATTRIBUTE10,\n" + 
            "                            ATTRIBUTE11,\n" + 
            "                            ATTRIBUTE12,\n" + 
            "                            ATTRIBUTE13,\n" + 
            "                            ATTRIBUTE14,\n" + */
            "                            ATTRIBUTE15,\n" + 
            "                            FEC_CREATION_DATE,\n" + 
            "                            NUM_CREATED_BY,\n" + 
            "                            FEC_LAST_UPDATE_DATE,\n" + 
            "                            NUM_LAST_UPDATED_BY,\n" + 
            "                            NUM_LAST_UPDATE_LOGIN\n" + 
            "                           )\n" + 
            "                    VALUES ('" + getIdBitacora() + "',\n" + 
            "                            " + toEvetvIntServiceBitacoraTab.getLsIdLogServices() + ",\n" + 
            "                            " + toEvetvIntServiceBitacoraTab.getLsIdService() + ",\n" + 
            "                            " + toEvetvIntServiceBitacoraTab.getLsIndProcess() + ",\n" + 
            "                            " + toEvetvIntServiceBitacoraTab.getLsNumEvtbProcessId() + ",\n" + 
            "                            " + toEvetvIntServiceBitacoraTab.getLsNumPgmProcessId() + ",\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsIndEvento() + "',\n" + 
            "                            'A',\n" + 
            /*"                            '" + toEvetvIntServiceBitacoraTab.getLsAttributeCategory() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute1() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute2() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute3() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute4() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute5() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute6() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute7() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute8() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute9() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute10() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute11() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute12() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute13() + "',\n" + 
            "                            '" + toEvetvIntServiceBitacoraTab.getLsAttribute14() + "',\n" + */
            "                            '" + tsUserName + "',\n" + 
            "                            CURRENT_TIMESTAMP,\n" + 
            "                            " + tiIdUser + ",\n" + 
            "                            CURRENT_TIMESTAMP,\n" + 
            "                            " + tiIdUser + ",\n" + 
            "                            " + tiIdUser + "\n" + 
            "                           )";
        return lsQuery;
    }
    
    
    /**
     * Actualiza en la tabla de crones en base de datos
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvIntServiceBitacoraTab
     * @return Integer
     */
    public Integer initFieldExeCron() {
        Integer    loValue = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = 
            getQueryInitCron();
        try {
            Statement loStmt = loCnn.createStatement();
            loValue = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println(loExSql.getMessage());
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loValue;
    }
    
    /**
     * Genera instruccion para actualizar en la tabla de crones
     * @autor Jorge Luis Bautista Santiago
     * @return Integer
     */
    public String getQueryInitCron(){
        String lsQuery = 
            "UPDATE EVENTAS.EVETV_INT_CRON_CONFIG_TAB" +
            "   SET ATTRIBUTE12 = 'ready'" +
            " WHERE IND_ESTATUS = '2'";
        return lsQuery;
    }
    
    /**
     * Actualiza en la tabla de crones en base de datos
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvIntServiceBitacoraTab
     * @return Integer
     */
    public Integer updateExeFieldByService(Integer tiIdService) {
        Integer    loValue = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryUpdateExeField(tiIdService);
        try {
            Statement loStmt = loCnn.createStatement();
            loValue = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println(loExSql.getMessage());
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loValue;
    }
    
    /**
     * Genera instruccion para actualizar en la tabla de crones
     * @autor Jorge Luis Bautista Santiago
     * @return Integer
     */
    public String getQueryUpdateExeField(Integer tiIdService){
        String lsQuery = 
            "UPDATE EVENTAS.EVETV_INT_CRON_CONFIG_TAB" +
            "   SET ATTRIBUTE12 = 'exe', " +
            "       FEC_LAST_UPDATE_DATE = CURRENT_TIMESTAMP " +
            " WHERE ID_SERVICE = " + tiIdService;
        return lsQuery;
    }
    
    /**
     * Obtiene, en base a la fecha, el id_paradigm a manejar en intergracion
     * @autor Jorge Luis Bautista Santiago     
     * @return String
     */
    public String getIdBitacora(){
        String     lsResponse = null;
        DateFormat loDf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        lsResponse = loDf.format(new java.util.Date(System.currentTimeMillis()));
        return lsResponse;
    }
    
    /**
     * Obtiene identificador de parametro general en base al nombre y al uso
     * @autor Jorge Luis Bautista Santiago
     * @param tsName
     * @param tsUsedBy
     * @return String
     */     
    public String getGeneralParameterID(String tsName, String tsUsedBy) {
        String     loValue = "";
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = getQueryGeneralParameterID(tsName, tsUsedBy);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loValue = loRs.getString(1);
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
        return loValue;
    }
    
    /**
     * Genera instruccion para obtener parametro general en base al nombre y al uso
     * @autor Jorge Luis Bautista Santiago
     * @param tsName
     * @param tsUsedBy
     * @return String
     */ 
    public String getQueryGeneralParameterID(String tsName, String tsUsedBy){
        String lsQuery = 
            "SELECT ID_PARAMETER \n" + 
            "   FROM EVENTAS.EVETV_INT_CONFIG_PARAM_TAB\n" + 
            "  WHERE IND_USED_BY   = '" + tsUsedBy + "'\n" + 
            "    AND NOM_PARAMETER = '" + tsName + "'";
        return lsQuery;
    }
    
    /**
     * Genera instruccion para actualizar en la tabla de crones
     * @autor Jorge Luis Bautista Santiago
     * @return Integer
     */
    public String getQueryDisableCron(){
        String lsQuery = 
            "UPDATE EVENTAS.EVETV_INT_CRON_CONFIG_TAB" +
            "   SET IND_ESTATUS = '4'" +
            " WHERE IND_ESTATUS = '2'";
        return lsQuery;
    }
    
    /**
     * Actualiza en la tabla de crones en base de datos
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvIntServiceBitacoraTab
     * @return Integer
     */
    public Integer disableInitializedCron() {
        Integer    loValue = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryDisableCron();
        try {
            Statement loStmt = loCnn.createStatement();
            loValue = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println(loExSql.getMessage());
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loValue;
    }
    
    /**
     * Actualiza en la tabla de crones en base de datos
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvIntServiceBitacoraTab
     * @return Integer
     */
    public Integer enableInitializedCron() {
        Integer    loValue = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = 
            "UPDATE EVENTAS.EVETV_INT_CRON_CONFIG_TAB" +
            "   SET IND_ESTATUS = '2'" +
            " WHERE IND_ESTATUS = '4'";
        try {
            Statement loStmt = loCnn.createStatement();
            loValue = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println(loExSql.getMessage());
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loValue;
    }
    
    /**
     * Insertar en la tabla de log de servicios
     * @autor Jorge Luis Bautista Santiago
     * @param tiIdRequest
     * @param tiIdService
     * @param tsServiceType
     * @param tsUserName
     * @return Integer
     */
    public Integer insertLogServicesRequest(Integer tiIdRequest,
                                            Integer tiIdService,
                                            String tsServiceType,
                                            String tsUserName){
        Integer    loValue = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = 
            getQueryInsertLogService(tiIdRequest,
                                     tiIdService,
                                     tsServiceType,
                                     tsUserName
                                    );
        try {
            Statement loStmt = loCnn.createStatement();
            loValue = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println("ERR_668: "+loExSql.getMessage());
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loValue;        
    }
    
    /**
     * Genera instrucion para insertar en la tabla de log de servicios
     * @autor Jorge Luis Bautista Santiago
     * @param tiIdRequest
     * @param tiIdService
     * @param tsServiceType
     * @param tsUserName
     * @return Integer
     */
    public String getQueryInsertLogService(Integer tiIdRequest,
                                           Integer tiIdService,
                                           String tsServiceType,
                                           String tsUserName
                                           ){
        String lsQuery = 
            " INSERT INTO EVENTAS.EVETV_INT_REQUESTS_TAB(ID_REQUEST," +
        "                                                ID_SERVICE," +
        "                                                IND_SERVICE_TYPE," +
        "                                                IND_ESTATUS," +
        "                                                FEC_CREATION_DATE," +
        "                                                NOM_USER_NAME" +
        "                                               ) " + 
        "                                        VALUES (" + tiIdRequest + "," +
        "                                                " + tiIdService + "," +
        "                                                '" + tsServiceType + "'," +
        "                                                'A'," +
        "                                                CURRENT_TIMESTAMP," +
        "                                                '" + tsUserName + "'" +
        "                                                )";
        return lsQuery;
    }
    
}
