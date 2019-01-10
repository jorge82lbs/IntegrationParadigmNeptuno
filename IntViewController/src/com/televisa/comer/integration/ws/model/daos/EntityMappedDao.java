package com.televisa.comer.integration.ws.model.daos;

import com.televisa.comer.integration.model.beans.EvetvIntRequestBean;
import com.televisa.comer.integration.model.beans.RsstLogCertificadoBean;
import com.televisa.comer.integration.model.beans.pgm.EvetvIntServiceBitacoraTab;
import com.televisa.comer.integration.model.connection.ConnectionAs400;
import com.televisa.comer.integration.ws.model.beans.EvetvIntConfigParamTabBean;
import com.televisa.comer.integration.ws.model.beans.EvetvIntServicesLogBean;

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

public class EntityMappedDao {
    /**
     * Obtiene row de parametro general en base al nombre y uso
     * @autor Jorge Luis Bautista Santiago
     * @param tsName
     * @param tsUsedBy
     * @return EvetvIntConfigParamTabBean
     */
    public EvetvIntConfigParamTabBean getGeneralParameterBean(String tsName, String tsUsedBy){
        EvetvIntConfigParamTabBean loEcPar = new EvetvIntConfigParamTabBean();
        Connection                 loCnn = new ConnectionAs400().getConnection();
        ResultSet                  loRs = null;
        String                     lsQueryParadigm = getQueryGeneralParameterBean(tsName, tsUsedBy);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loEcPar.setLsIdParameter(loRs.getString("ID_PARAMETER"));
                loEcPar.setLsNomParameter(loRs.getString("NOM_PARAMETER"));
                loEcPar.setLsIndDescParameter(loRs.getString("IND_DESC_PARAMETER"));
                loEcPar.setLsIndUsedBy(loRs.getString("IND_USED_BY"));
                loEcPar.setLsIndValueParameter(loRs.getString("IND_VALUE_PARAMETER"));
                loEcPar.setLsIndEstatus(loRs.getString("IND_ESTATUS"));
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
        return loEcPar;
    }
    
    /**
     * Genera instruccion para obtener row de parametro general en base al nombre y uso
     * @autor Jorge Luis Bautista Santiago
     * @param tsName
     * @param tsUsedBy
     * @return EvetvIntConfigParamTabBean
     */
    public String getQueryGeneralParameterBean(String tsName, String tsUsedBy){
        String lsQuery = 
            "SELECT ID_PARAMETER,\n" + 
            "       NOM_PARAMETER,\n" + 
            "       IND_DESC_PARAMETER,\n" + 
            "       IND_USED_BY,\n" + 
            "       IND_VALUE_PARAMETER,\n" + 
            "       IND_ESTATUS\n" + 
            "  FROM EVENTAS.EVETV_INT_CONFIG_PARAM_TAB\n" + 
            "  WHERE IND_USED_BY   = '" + tsUsedBy + "'\n" + 
            "    AND NOM_PARAMETER = '" + tsName + "'";
        return lsQuery;
    }
    /**
     * Obtiene identificador maximo ingresado en tabla
     * @autor Jorge Luis Bautista Santiago     
     * @param tsTable
     * @return Integer
     */
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
                System.out.println("err sleep");
            }
            
        }
        if(tsTable.equalsIgnoreCase("RstRequest")){
            lsTable = "EVENTAS.EVETV_INT_REQUESTS_TAB";
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
     * Genera instruccion para obtener identificador maximo ingresado en tabla
     * @autor Jorge Luis Bautista Santiago
     * @param tsTable
     * @param tsField
     * @return Integer
     */
    public String getQueryMaxParadigm(String tsTable, String tsField) {
        String lsQuery = 
        "SELECT coalesce(MAX(" + tsField + "),0) \n" + 
        "  FROM " + tsTable;
        return lsQuery;
    }
    
    /**
     * Obtiene identificador del servicio en base al nombre 
     * @autor Jorge Luis Bautista Santiago
     * @param tsName
     * @return String
     */
    public String getWsParadigmOrigin(String tsName) {
       String     loValue = "";
       Connection loCnn = new ConnectionAs400().getConnection();
       ResultSet  loRs = null;
       String     lsQueryParadigm = getQueryWsParadimOrigin(tsName);
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
    * Genera instruccion para Obtiener identificador del servicio en base al nombre 
    * @autor Jorge Luis Bautista Santiago
    * @param tsName
    * @return String
    */
    public String getQueryWsParadimOrigin(String tsName){
       String lsQuery = 
           "SELECT ID_SERVICE \n" + 
           "   FROM EVENTAS.EVETV_INT_SERVICES_CAT_TAB\n" + 
           "  WHERE NOM_SERVICE = '" +  tsName + "'";
       return lsQuery;
    }
    
    /**
     * Insertar en la tabla de archivos fisicos de request y response xml
     * @autor Jorge Luis Bautista Santiago
     * @param tiIdRequest
     * @param tiIdService
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
        boolean    liResponse = false;
        Connection loCnn = new ConnectionAs400().getConnection();
        Integer    liIdLogCert = getMaxIdParadigm("RstXmlFiles") + 1;
        String     lsFecha = String.valueOf(new Date());
        try {
            String lsSql = "INSERT INTO EVENTAS.EVETV_INT_XML_FILES_TAB(ID_FILE_XML,\n" + 
            "                                            ID_REQUEST,\n" + 
            "                                            ID_SERVICE,\n" + 
            "                                            NOM_FILE,\n" + 
            "                                            IND_FILE_TYPE,\n" + 
            "                                            IND_SERVICE_TYPE,                                           \n" + 
            "                                            IND_ESTATUS,\n" + 
            "                                            FEC_CREATION_DATE,\n" + 
            "                                            NOM_USER_NAME,\n" + 
            "                                            IND_FILE_STREAM\n" + 
            "                                           )\n" + 
            "                                   VALUES (?,?,?,?,?,?,?,?,?,?)";
            //Creamos una cadena para despues prepararla
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
            liResponse = loStmt.execute();
            
        } catch (SQLException loExSql) {
            System.out.println("ERROR XML_FILE: "+loExSql.getMessage());
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liResponse;
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
    
    /**
     * Insertar en la tabla de log de servicios
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvIntServicesLogBean     
     * @return Integer
     */
    public Integer insertServicesLogWs(EvetvIntServicesLogBean toEvetvIntServicesLogBean) {
        Integer    loValue = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryInsertServicesLog(toEvetvIntServicesLogBean);
        try {
            Statement loStmt = loCnn.createStatement();
            loValue = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println("ERR_02(servicesLog): "+loExSql.getMessage());
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
     * Genera instruccion para insertar en la tabla de log de servicios
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvIntServicesLogBean     
     * @return String
     */
    public String getQueryInsertServicesLog(EvetvIntServicesLogBean toEvetvIntServicesLogBean) {
        String lsQuery = 
            "INSERT INTO EVENTAS.EVETV_INT_SERVICES_LOG_TAB(ID_LOG_SERVICES,\n" + 
            "                                                ID_SERVICE,\n" + 
            "                                                IND_PROCESS,\n" + 
            "                                                IND_RESPONSE,\n" + 
            "                                                FEC_RESPONSE,\n" + 
            "                                                FEC_REQUEST,\n" + 
            "                                                IND_ESTATUS,\n" + 
            "                                                ATTRIBUTE9,\n" + 
            "                                                ATTRIBUTE10,\n" + 
            "                                                ATTRIBUTE15,\n" + 
            "                                                FEC_CREATION_DATE,\n" + 
            "                                                NUM_CREATED_BY,\n" + 
            "                                                FEC_LAST_UPDATE_DATE,\n" + 
            "                                                NUM_LAST_UPDATED_BY\n" + 
            "                                               )\n" + 
            "                                        VALUES (" + toEvetvIntServicesLogBean.getLiIdLogServices() + ",\n" + 
            "                                                " + toEvetvIntServicesLogBean.getLiIdService() + ",\n" + 
            "                                                " + toEvetvIntServicesLogBean.getLiIndProcess() + ",\n" + 
            "                                                '" + toEvetvIntServicesLogBean.getLsIndResponse() + "',\n" + 
            "                                                CURRENT_TIMESTAMP,\n" + 
            "                                                CURRENT_TIMESTAMP,\n" + 
            "                                                '" + toEvetvIntServicesLogBean.getLsIndEstatus() + "',\n" + 
            "                                                '" + toEvetvIntServicesLogBean.getLsAttribute9() + "',\n" + 
            "                                                '" + toEvetvIntServicesLogBean.getLsAttribute10() + "',\n" + 
            "                                                '" + toEvetvIntServicesLogBean.getLsAttribute15() + "',\n" + 
            "                                                CURRENT_TIMESTAMP,\n" + 
            "                                                0,\n" + 
            "                                                CURRENT_TIMESTAMP,\n" + 
            "                                                0\n" + 
            "                                               )";
        
        return lsQuery;
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
     * Inserta en la tabla de bitacora en base de datos
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvIntServiceBitacoraTab
     * @return Integer
     */
    public Integer insertBitacoraWs(EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTab) {
        Integer    loValue = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryInsertBitacora(toEvetvIntServiceBitacoraTab);
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
    public String getQueryInsertBitacora(EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTab){
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
            "                            'neptuno',\n" + 
            "                            CURRENT_TIMESTAMP,\n" + 
            "                            1,\n" + 
            "                            CURRENT_TIMESTAMP,\n" + 
            "                            1,\n" + 
            "                            1\n" + 
            "                           )";
        return lsQuery;
    }
    
    /**
     * Geneara instruccion para obtener parametro para validar si el servicio tiene seguridad implicita
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdService
     * @return String
     */ 
    public String getQuerySecurityService(String tsIdService){
        String lsQuery = 
            "SELECT IND_VAL_PARAMETER\n" + 
            "  FROM EVENTAS.EVETV_INT_SERVICES_PARAMS_TAB\n" + 
            " WHERE ID_SERVICE    = " + tsIdService + "\n" + 
            "   AND IND_PARAMETER = 'SECURITY'\n" + 
            "   AND IND_ESTATUS   = '1'";
        return lsQuery;
    }
    
    /**
     * Obtiene parametro para validar si el servicio tiene seguridad implicita
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdService
     * @return String
     */    
    public String getSecurityService(String tsIdService) {
        String      loValue = "";
        Connection  loCnn = new ConnectionAs400().getConnection();
        ResultSet   loRs = null;
        String      lsQueryParadigm = getQuerySecurityService(tsIdService);
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
     * Obtiene parametro general en base al nombre y al uso
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
     * Genera instruccion para Obtiener parametro general en base al nombre y al uso
     * @autor Jorge Luis Bautista Santiago
     * @param tsName
     * @param tsUsedBy
     * @return String
     */    
    public String getQueryGeneralParameter(String tsName, String tsUsedBy){
        String lsQuery = 
            " SELECT IND_VALUE_PARAMETER \n" + 
            "   FROM EVENTAS.EVETV_INT_CONFIG_PARAM_TAB\n" + 
            "  WHERE IND_USED_BY   = '" + tsUsedBy + "'\n" + 
            "    AND NOM_PARAMETER = '" + tsName + "'";
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
     * Genera instruccion para obtener instruccion para verificar si existen servicios en ejecucion
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String getQueryValidateExecution(String tsStatus) {
        String lsQuery = 
            "SELECT COUNT(1)\n" + 
            "  FROM EVENTAS.EVETV_INT_REQUESTS_TAB \n" + 
            " WHERE IND_SERVICE_TYPE = 'WsVtaTradicionalClient'\n" + 
            "   AND IND_ESTATUS = '"+tsStatus+"'";
        return lsQuery;
    }
    
    /**
     * Obtiene count para verificar si existen servicios en ejecucion
     * @autor Jorge Luis Bautista Santiago
     * @param tsServiceType
     * @return Integer
     */
    public Integer validateExecutionVtaTradicional() {
        Integer     liReturn = 0; 
        try{
            Connection loCnn = new ConnectionAs400().getConnection();
            ResultSet  loRs = null;
            String     lsQueryParadigm = getQueryValidateExecution("E");
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
     * Insertar en la tabla de log de servicios
     * @autor Jorge Luis Bautista Santiago
     * @param toEvetvIntServicesLogBean     
     * @return Integer
     */
    public Integer updateServiceRequestById(Integer tiIdRequest, 
                                            String tsParameters,
                                            String tsStatus) {
        Integer    loValue = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        //System.out.println("Actualizando EVETV_INT_REQUESTS_TAB idRequest["+tiIdRequest+"] estatus["+tsStatus+"]");
        String     lsQueryParadigm = 
            "UPDATE EVENTAS.EVETV_INT_REQUESTS_TAB\n" + 
            "   SET IND_ESTATUS = '" + tsStatus + "'";
            if(tsParameters != null){
                lsQueryParadigm +=  ",\n" + 
                "       NOM_USER_NAME = '" + tsParameters + "'\n";       
            }
            lsQueryParadigm += " WHERE ID_REQUEST = " + tiIdRequest;
        //System.out.println(lsQueryParadigm);
        try {
            Statement loStmt = loCnn.createStatement();
            loValue = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println("ERR_07(upd EVENTAS.EVETV_INT_REQUESTS_TAB): "+loExSql.getMessage());
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
    //queue
    /**
     * Obtiene count para verificar si existen servicios en ejecucion
     * @autor Jorge Luis Bautista Santiago
     * @param tsServiceType
     * @return Integer
     */
    public Integer validateQueueVtaTradicional() {
        Integer     liReturn = 0; 
        try{
            Connection loCnn = new ConnectionAs400().getConnection();
            ResultSet  loRs = null;
            String     lsQueryParadigm = getQueryValidateExecution("D");
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
     * Obtiene registros necesarios para enviar a neptuno en Log Certificado
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return List
     */    
    public List<EvetvIntRequestBean> getRequestQueuedVtraditional() {
        List<EvetvIntRequestBean> loRequestQueued = 
            new ArrayList<EvetvIntRequestBean>();
        Connection                   loCnn = new ConnectionAs400().getConnection();
        ResultSet                    loRs = null;
        Statement                    loStmt = null;
        String                       lsQueryParadigm = 
            "SELECT ID_REQUEST,\n" + 
            "		   ID_SERVICE,\n" + 
            "		   IND_SERVICE_TYPE,\n" + 
            "		   IND_ESTATUS,\n" + 
            "		   FEC_CREATION_DATE,\n" + 
            "		   NOM_USER_NAME\n" + 
            "	  FROM EVENTAS.EVETV_INT_REQUESTS_TAB\n" + 
            "	 WHERE IND_SERVICE_TYPE = 'WsVtaTradicionalClient'\n" + 
            "	   AND IND_ESTATUS = 'D'\n" + 
            "  ORDER BY FEC_CREATION_DATE DESC";  
        //System.out.println(lsQueryParadigm);
        try {
            loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);
            while(loRs.next()){
                EvetvIntRequestBean loRequest = new EvetvIntRequestBean(); 
                /*System.out.println(loRs.getString("ID_REQUEST")+" "+
                                   loRs.getString("ID_SERVICE")+" "+
                loRs.getString("IND_SERVICE_TYPE")+" "+
                loRs.getString("IND_ESTATUS")+" "+
                loRs.getString("FEC_CREATION_DATE")+" "+
                loRs.getString("NOM_USER_NAME")
                                   );*/
                loRequest.setLsIdRequest(loRs.getString("ID_REQUEST"));
                loRequest.setLsIdService(loRs.getString("ID_SERVICE"));
                loRequest.setLsIndServiceType(loRs.getString("IND_SERVICE_TYPE"));
                loRequest.setLsIndStatus(loRs.getString("IND_ESTATUS"));                
                //loLogCertificado.setLsFecCreationDate(loRs.getString("FEC_CREATION_DATE"));
                loRequest.setLsNomUserName(loRs.getString("NOM_USER_NAME"));
                loRequestQueued.add(loRequest);
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
        return loRequestQueued;
    }
    
}
