/**
* Project: Integraton Paradigm - EveTV
*
* File: LogCertificadoDao.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.model.daos;

import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.beans.RsstLogCertificadoBean;
import com.televisa.comer.integration.model.connection.ConnectionAs400;
import com.televisa.comer.integration.model.interfaces.LogCertificadoInterface;

import com.televisa.comer.integration.service.beans.types.EvetvLogCertificadoProcesadoBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** Objeto que accede a base de datos para consultar Log Certificado
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class LogCertificadoDao implements LogCertificadoInterface {
    
    /**
     * Obtiene registros necesarios para enviar a neptuno en Log Certificado
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return List
     */    
    @Override
    public List<RsstLogCertificadoBean> getLogCertificadoFromParadigm(String tsDate, 
                                                                      String tsChannels) {
        List<RsstLogCertificadoBean> loLogsCertificados = 
            new ArrayList<RsstLogCertificadoBean>();
        Connection                   loCnn = new ConnectionAs400().getConnection();
        ResultSet                    loRs = null;
        Statement                    loStmt = null;
        String                       lsQueryParadigm = getQueryLogCertificado(tsDate, tsChannels);  
        //System.out.println("["+new Date()+"]**Parrills: Query NORMAL (es ondemand sin strtDate, endDate ni Buyunit) ************ ");
        //System.out.println(lsQueryParadigm);
        //System.out.println("*************************************************************************************");
        
        try {
            loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);
            while(loRs.next()){
                RsstLogCertificadoBean loLogCertificado = new RsstLogCertificadoBean(); 
                loLogCertificado.setLsChannelid(loRs.getString("CHANNELID"));
                loLogCertificado.setLsDate(loRs.getString("DATE"));
                loLogCertificado.setLsOrderid(loRs.getString("ORDERID"));
                loLogCertificado.setLsSpotid(loRs.getString("SPOTID"));                
                loLogCertificado.setLsBuyunitid(loRs.getString("BUYUNTID"));
                loLogCertificado.setLsBreakid(loRs.getString("BREAKID"));
                loLogCertificado.setLsHour(loRs.getString("HOUR"));
                loLogCertificado.setLsDuration(loRs.getString("DURATION"));
                loLogCertificado.setLsSpotformatid(loRs.getString("SPOTFORMATID"));
                loLogCertificado.setLsMediaid(loRs.getString("MEDIAID"));
                loLogCertificado.setLsProductid(loRs.getString("PRODUCTID"));
                loLogCertificado.setLsSpotprice(loRs.getString("SPOTPRICE"));
                loLogsCertificados.add(loLogCertificado);
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
        return loLogsCertificados;
    }
   
    /**
     * Genera instruccion para obtener registros necesarios para enviar a neptuno en Log Certificado
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return List
     */  
    @Override
    public String getQueryLogCertificado(String tsDate, String tsChannels) {
       String lsQuery = 
       "                SELECT LOGEDT.STNID AS CHANNELID,\n" + 
       "					LOGEDT.BCSTDT AS DATE,\n" + 
       "					CASE  WHEN  SPTMST.ORDID    IS NULL THEN 0 ELSE     SPTMST.ORDID    END AS ORDERID,\n" + 
       "					SPTMST.SPTMSTID AS SPOTID,\n" + 
       "					SPTMST.PGMID AS BUYUNTID,\n" + 
       "					SPTMST.BRKDTID AS BREAKID,\n" + 
       "					CASE LENGTH(RTRIM(CHAR(SPTMST.ACTTIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(SPTMST.ACTTIM),1,1)||':'||\n" + 
       "					SUBSTR(CHAR(SPTMST.ACTTIM),2,2)||':'||SUBSTR(CHAR(SPTMST.ACTTIM),4,2) WHEN 8 THEN\n" + 
       "					CASE SUBSTR(CHAR(SPTMST.ACTTIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25' WHEN '26' THEN '26'\n" + 
       "					WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(SPTMST.ACTTIM),1,2)\n" + 
       "					END ||':'||SUBSTR(CHAR(SPTMST.ACTTIM),3,2)||':'||SUBSTR(CHAR(SPTMST.ACTTIM),5,2) ELSE ' ' END AS HOUR,\n" + 
       "					CASE WHEN SPTMST.SPTLEN <=59 THEN '00:00:'||case when length(RTRIM(CHAR(MOD(SPTMST.SPTLEN,60)))) = 1 then '0'||RTRIM(CHAR(MOD(SPTMST.SPTLEN,60)))\n" + 
       "					else RTRIM(CHAR(MOD(SPTMST.SPTLEN,60))) end\n" + 
       "					WHEN SPTMST.SPTLEN BETWEEN 60 AND 599 THEN '00:0'||RTRIM(CHAR(SPTMST.SPTLEN/60))||':'||\n" + 
       "					case when length(RTRIM(CHAR(MOD(SPTMST.SPTLEN,60)))) = 1 then '0'||RTRIM(CHAR(MOD(SPTMST.SPTLEN,60)))\n" + 
       "					else RTRIM(CHAR(MOD(SPTMST.SPTLEN,60))) end\n" + 
       "					END AS DURATION,\n" + 
       "					CASE WHEN (SPTMST.USRCHR IN ('',' ') OR SPTMST.USRCHR IS NULL) THEN '0' ELSE SPTMST.USRCHR END AS SPOTFORMATID,\n" + 
       "					CPYANC.AUTOID AS MEDIAID,\n" + 
       "					CPYANC.BRND AS PRODUCTID,\n" + 
       "					DECIMAL(SPTREV.SPTRT/100,10,2) AS SPOTPRICE\n" + 
       "			   FROM PARADB.SPTMST SPTMST\n" + 
       "    LEFT OUTER JOIN PARADB.SPTCPY SPTCPY ON\n" + 
       "                    SPTCPY.SPTMSTID = SPTMST.SPTMSTID\n" + 
       "    LEFT OUTER JOIN PARADB.SPTREV SPTREV ON\n" + 
       "                    SPTREV.SPTMSTID = SPTMST.SPTMSTID AND SPTREV.SPTMSTID = SPTCPY.SPTMSTID\n" + 
       "    LEFT OUTER JOIN PARADB.BRKDT BRKDT ON\n" + 
       "                    SPTMST.BRKDTID = BRKDT.BRKDTID\n" + 
       "    LEFT OUTER JOIN PARADB.LOGEDT LOGEDT ON\n" + 
       "                    BRKDT.LOGEDTID = LOGEDT.LOGEDTID\n" + 
       "    LEFT OUTER JOIN PARADB.LOGLINE LOGLINE ON\n" + 
       "                    LOGEDT.LOGEDTID = LOGLINE.LOGEDTID\n" + 
       "    LEFT OUTER JOIN PARADB.LOGCMT LOGCMT ON\n" + 
       "                    LOGEDT.LOGEDTID = LOGCMT.LOGEDTID\n" + 
       "    LEFT OUTER JOIN PARADB.CPYANC CPYANC ON\n" + 
       "                    SPTCPY.ADVID = CPYANC.ADVID AND\n" + 
       "                    SPTCPY.EXTCPYNUM = CPYANC.EXTCPYNUM\n" + 
       "    LEFT OUTER JOIN PARADB.ORDLN ORDLN ON\n" + 
       "                    SPTMST.ORDLNID = ORDLN.ORDLNID\n" + 
       "    LEFT OUTER JOIN PARADB.ORDHDR ORDHDR ON\n" + 
       "                    SPTMST.ORDID = ORDHDR.ORDID\n" + 
       "    LEFT OUTER JOIN PARADB.LOGHDR LOGHDR ON\n" + 
       "                    SPTMST.STNID = LOGHDR.STNID AND\n" + 
       "                    SPTMST.BCSTDT = LOGHDR.BCSTDT\n" + 
       "               JOIN EVENTAS.EVETV_LOG_CERTIFICADO_PROCESADO A ON\n" + 
       "                    LOGHDR.STNID = A.STNID\n" + 
       "                    AND LOGHDR.BCSTDT = A.BCSTDT\n" + 
       "			  WHERE SPTMST.EXCFL = 0\n" + 
       "				AND SPTMST.ALTLOG = 0\n" + 
       "				AND SPTMST.ACTSTS=1\n" + 
       "				AND (ORDLN.ORDLNTYP in(0,2) OR ORDLN.ORDLNTYP is null)\n" + 
       "				AND LOGEDT.FMTTYP  <> 0\n" + 
       "				AND BRKDT.BRKCHRRULE IN ('CO','BI','BF')\n" + //2018-06-18 cambio
/*CH*///        "                               AND BRKDT.BRKCHRRULE IN ('CO','')\n" + 
       "				AND BRKDT.INVFL = 1\n" + 
//       "				AND SPTMST.SPTCHR NOT IN (2,6,12)\n" + 
/*CH*/ "				AND SPTMST.SPTCHR NOT IN (1,2,5,6,12)\n" + 
       "				AND LOGHDR.LOGEDTLCK >= 7031 -- INDICATIVO DE QUE YA FUE RECON COMPLETE\n" + 
       "				AND A.STATUS = 1\n" + 
       "				AND A.STNID = '" + tsChannels + "'\n" + 
       "				AND SPTMST.STNID = '" + tsChannels + "'\n" + 
       //"				AND A.BCSTDT >= CURRENT DATE\n" + 
       //"				AND SPTMST.BCSTDT >= CURRENT DATE\n" + 
/*CH*/       "                              AND A.BCSTDT = '" + tsDate + "'\n" + 
/*CH*/       "                              AND SPTMST.BCSTDT = '" + tsDate + "'\n" +        
       /*"				AND A.STNID IN ('9CAN')\n" + 
       "				AND SPTMST.STNID IN ('9CAN')\n" + 
       "				AND A.BCSTDT = '2018-01-02'\n" + 
       "				AND SPTMST.BCSTDT = '2018-01-02'\n" + */
       "                               AND SPTMST.ORDID > 0\n" + 
       "           ORDER BY LOGEDT.STNID,LOGEDT.BCSTDT,SPTMST.ACTTIM";
        return lsQuery;
    }

    /**
     * Obtiene bandera para procesamiento en log certificado
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return List
     */  
    @Override
    public Integer getFlagInsertLogCertificado(String tsDate, 
                                               String tsChannels) {
        Integer    liReturn = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryInsertLogCertificado(tsDate, tsChannels);
        
        try {
            Statement loStmt = loCnn.createStatement();
            liReturn = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liReturn;
    }

    /**
     * Genera instruccion para obtener bandera para procesamiento en log certificado
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return List
     */  
    @Override
    public String getQueryInsertLogCertificado(String tsDate, String tsChannels) {
        
        String lsQuery = "INSERT INTO EVENTAS.EVETV_LOG_CERTIFICADO_PROCESADO(STNID,BCSTDT,STATUS)\n" + 
        "SELECT\n" + 
        "    LOGHDR.STNID,\n" + 
        "    LOGHDR.BCSTDT,\n" + 
        "    1\n" + 
        "FROM PARADB.LOGHDR LOGHDR\n" + 
        "WHERE LOGHDR.STNID = '" + tsChannels + "' \n" + 
        "AND LOGHDR.BCSTDT >= '"+tsDate+"' -- PARAMETRO INICIAL DE FECHA\n" + 
        "AND (LOGHDR.STNID,LOGHDR.BCSTDT) NOT IN (SELECT A.STNID, A.BCSTDT " +
            " FROM EVENTAS.EVETV_LOG_CERTIFICADO_PROCESADO A\n" + 
        "                                         WHERE LOGHDR.STNID = A.STNID\n" + 
        "                                         AND LOGHDR.BCSTDT = A.BCSTDT)\n" + 
        "AND LOGHDR.LOGEDTLCK >= 7031\n" + 
        "GROUP BY LOGHDR.STNID,LOGHDR.BCSTDT\n" + 
        "ORDER BY  LOGHDR.STNID,LOGHDR.BCSTDT\n";
                
        return lsQuery;
    }

    /**
     * Actualiza en base a la respuesta de netuno las tablas de control de log certificado
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return Integer
     */  
    @Override
    public Integer getUpdateLogCertificado(String tsDate, String tsChannels) {
        Integer    liReturn = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = getQueryUpdateLogCertificado(tsDate, tsChannels);
        try {
            Statement loStmt = loCnn.createStatement();
            liReturn = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {            
            loExSql.printStackTrace();
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return liReturn;
    }

    /**
     * Genera instruccion para actualizar en base a la respuesta de netuno las tablas de control de log certificado
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return Integer
     */
    @Override
    public String getQueryUpdateLogCertificado(String tsDate, 
                                               String tsChannels) {
        String lsQuery = "UPDATE EVENTAS.EVETV_LOG_CERTIFICADO_PROCESADO\n" + 
        "   SET STATUS = '0'\n" + 
        " WHERE STNID = '" + tsChannels + "'\n" + 
        "   AND BCSTDT = '" + tsDate + "'";
        return lsQuery;
    }
    
    /**
     * Genera instruccion para insertar en  tabla de control de Integracion el set de resultados de la extracion
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsUserName
     * @param tsIdUser
     * @param toRsstLogCertificadoBean
     * @return ResponseUpdDao
     */
    public String getQueryInsertRsstLcert(String lsIdRequest,
                                          String lsIdService,
                                          String lsUserName,
                                          String lsIdUser,
                                          RsstLogCertificadoBean toRsstLogCertificadoBean) {
        String lsQuery = "INSERT INTO EVENTAS.EVETV_INT_RST_LOG_CERT_TAB(ID_REQUEST,\n" + 
        "                                                ID_SERVICE,\n" + 
        "                                                CHANNELID,\n" + 
        "                                                DATE,\n" + 
        "                                                ORDERID,\n" + 
        "                                                SPOTID,\n" + 
        "                                                BUYUNTID,\n" + 
        "                                                BREAKID,\n" + 
        "                                                HOUR,\n" + 
        "                                                DURATION,\n" + 
        "                                                SPOTFORMATID,\n" + 
        "                                                MEDIAID,\n" + 
        "                                                PRODUCTID,\n" + 
        "                                                SPOTPRICE,\n" + 
        "                                                IND_ESTATUS,\n" + 
        "                                                ID_PROCESS_NEPTUNO,\n" + 
        "                                                ATTRIBUTE1,\n" + 
        "                                                ATTRIBUTE2,\n" + 
        "                                                ATTRIBUTE15,\n" + 
        "                                                FEC_CREATION_DATE,\n" + 
        "                                                NUM_CREATED_BY,\n" + 
        "                                                FEC_LAST_UPDATE_DATE,\n" + 
        "                                                NUM_LAST_UPDATED_BY\n" + 
        "                                               )\n" + 
        "                                        VALUES (" + lsIdRequest + ",\n" + 
        "                                                " + lsIdService + ",\n" + 
        "                                                '" + toRsstLogCertificadoBean.getLsChannelid() + "',\n" + 
        "                                                '" + toRsstLogCertificadoBean.getLsDate() + "',\n" + 
        "                                                '" + toRsstLogCertificadoBean.getLsOrderid() + "',\n" + 
        "                                                '" + toRsstLogCertificadoBean.getLsSpotid() + "',\n" + 
        "                                                '" + toRsstLogCertificadoBean.getLsBuyunitid() + "',\n" + 
        "                                                '" + toRsstLogCertificadoBean.getLsBreakid() + "',\n" + 
        "                                                '" + toRsstLogCertificadoBean.getLsHour() + "',\n" + 
        "                                                '" + toRsstLogCertificadoBean.getLsDuration() + "',\n" + 
        "                                                '" + toRsstLogCertificadoBean.getLsSpotformatid() + "',\n" + 
        "                                                '" + toRsstLogCertificadoBean.getLsMediaid() + "',\n" + 
        "                                                '" + toRsstLogCertificadoBean.getLsProductid() + "',\n" + 
        "                                                '" + toRsstLogCertificadoBean.getLsSpotprice() + "',\n" + 
        "                                                'A',\n" + 
        "                                                0,\n" + 
        "                                                'A',\n" + 
        "                                   '" + lsUserName + "',\n" + 
        "                                   '" + lsUserName + "',\n" + 
        "                              CURRENT_TIMESTAMP,\n" + 
        "                              " + lsIdUser + ",\n" + 
        "                              CURRENT_TIMESTAMP,\n" + 
        "                              " + lsIdUser + "\n" + 
        "                                               )";
                
        return lsQuery;
    }
    
    /**
     * Inserta en  tabla de control de Integracion el set de resultados de la extracion
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsUserName
     * @param tsIdUser
     * @param toRsstLogCertificadoBean
     * @return ResponseUpdDao
     */
    public ResponseUpdDao insertLogCertificadoCtrl(String tsIdRequest,
                                                   String tsIdService,
                                                   String tsUserName,
                                                   String tsIdUser,
                                                   RsstLogCertificadoBean toRsstLogCertificadoBean
                                                  ) {
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection     loCnn = new ConnectionAs400().getConnection();
        String         lsQueryParadigm = 
                getQueryInsertRsstLcert(tsIdRequest,
                                        tsIdService,
                                        tsUserName,
                                        tsIdUser,
                                        toRsstLogCertificadoBean);
        try {
            Statement loStmt = loCnn.createStatement();
            Integer   liRes = loStmt.executeUpdate(lsQueryParadigm);     
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLiAffected(liRes);
            loResponseUpdDao.setLsMessage(null);
        } catch (SQLException loExSql) {            
            loExSql.printStackTrace();
            loResponseUpdDao.setLsResponse("ERROR");
            loResponseUpdDao.setLiAffected(0);
            loResponseUpdDao.setLsMessage(loExSql.getMessage());
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loResponseUpdDao;
    }
    
    /**
     * Obtiene registros necesarios para enviar a neptuno en Log Certificado
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return List
     */    
    public List<EvetvLogCertificadoProcesadoBean> getLogCertificadoProcesados(String tsChannels, String tsDate) {
        List<EvetvLogCertificadoProcesadoBean> loLcertProcesados = 
            new ArrayList<EvetvLogCertificadoProcesadoBean>();
        Connection                   loCnn = new ConnectionAs400().getConnection();
        ResultSet                    loRs = null;
        Statement                    loStmt = null;
        String                       lsQueryParadigm = getQueryLogCertificadoProcesado(tsChannels, tsDate);  
        
        try {
            loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);
            while(loRs.next()){
                EvetvLogCertificadoProcesadoBean loLogCert = new EvetvLogCertificadoProcesadoBean(); 
                loLogCert.setLsStnid(loRs.getString("STNID"));
                loLogCert.setLsBcstdt(loRs.getString("BCSTDT"));
                loLogCert.setLsStatus(loRs.getString("STATUS"));
                loLcertProcesados.add(loLogCert);
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
        return loLcertProcesados;
    }
    
    /**
     * Genera instruccion para obtener registros necesarios para enviar a neptuno en Log Certificado
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return List
     */  
    public String getQueryLogCertificadoProcesado(String tsChannels, String tsDate) {
        String lsQuery = 
       "     SELECT STNID, \n" + 
       "            BCSTDT,\n" + 
       "            STATUS \n" + 
       "       FROM EVENTAS.EVETV_LOG_CERTIFICADO_PROCESADO\n" + 
       "      WHERE STNID = '" + tsChannels + "'\n" + 
       "        AND BCSTDT >= '" + tsDate + "'\n" + 
       "        AND STATUS = 1\n" + 
       "   ORDER BY BCSTDT";
        return lsQuery;
    }
    
    //Eliminar los siguientes dos metodos...........
    public List<RsstLogCertificadoBean> getLogCertificadoFromParadigmTMP(String tsDate, 
                                                                      String tsChannels) {
        List<RsstLogCertificadoBean> loLogsCertificados = 
            new ArrayList<RsstLogCertificadoBean>();
        Connection                   loCnn = new ConnectionAs400().getConnection();
        ResultSet                    loRs = null;
        Statement                    loStmt = null;
        String                       lsQueryParadigm = getQueryLogCertificadoTMP(tsDate, tsChannels);  
        
        try {
            loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);
            while(loRs.next()){
                RsstLogCertificadoBean loLogCertificado = new RsstLogCertificadoBean(); 
                loLogCertificado.setLsChannelid(loRs.getString("CHANNELID"));
                loLogCertificado.setLsDate(loRs.getString("DATE"));
                loLogCertificado.setLsOrderid(loRs.getString("ORDERID"));
                loLogCertificado.setLsSpotid(loRs.getString("SPOTID"));                
                loLogCertificado.setLsBuyunitid(loRs.getString("BUYUNTID"));
                loLogCertificado.setLsBreakid(loRs.getString("BREAKID"));
                loLogCertificado.setLsHour(loRs.getString("HOUR"));
                loLogCertificado.setLsDuration(loRs.getString("DURATION"));
                loLogCertificado.setLsSpotformatid(loRs.getString("SPOTFORMATID"));
                loLogCertificado.setLsMediaid(loRs.getString("MEDIAID"));
                loLogCertificado.setLsProductid(loRs.getString("PRODUCTID"));
                loLogCertificado.setLsSpotprice(loRs.getString("SPOTPRICE"));
                loLogsCertificados.add(loLogCertificado);
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
        return loLogsCertificados;
    }
    
    /**
     * Genera instruccion para obtener registros necesarios para enviar a neptuno en Log Certificado
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return List
     */  
    public String getQueryLogCertificadoTMP(String tsDate, String tsChannels) {
       String lsQuery = 
       "                SELECT LOGEDT.STNID AS CHANNELID,\n" + 
       "                                        LOGEDT.BCSTDT AS DATE,\n" + 
       "                                        CASE  WHEN  SPTMST.ORDID    IS NULL THEN 0 ELSE     SPTMST.ORDID    END AS ORDERID,\n" + 
       "                                        SPTMST.SPTMSTID AS SPOTID,\n" + 
       "                                        SPTMST.PGMID AS BUYUNTID,\n" + 
       "                                        SPTMST.BRKDTID AS BREAKID,\n" + 
       "                                        CASE LENGTH(RTRIM(CHAR(SPTMST.ACTTIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(SPTMST.ACTTIM),1,1)||':'||\n" + 
       "                                        SUBSTR(CHAR(SPTMST.ACTTIM),2,2)||':'||SUBSTR(CHAR(SPTMST.ACTTIM),4,2) WHEN 8 THEN\n" + 
       "                                        CASE SUBSTR(CHAR(SPTMST.ACTTIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25' WHEN '26' THEN '26'\n" + 
       "                                        WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(SPTMST.ACTTIM),1,2)\n" + 
       "                                        END ||':'||SUBSTR(CHAR(SPTMST.ACTTIM),3,2)||':'||SUBSTR(CHAR(SPTMST.ACTTIM),5,2) ELSE ' ' END AS HOUR,\n" + 
       "                                        CASE WHEN SPTMST.SPTLEN <=59 THEN '00:00:'||case when length(RTRIM(CHAR(MOD(SPTMST.SPTLEN,60)))) = 1 then '0'||RTRIM(CHAR(MOD(SPTMST.SPTLEN,60)))\n" + 
       "                                        else RTRIM(CHAR(MOD(SPTMST.SPTLEN,60))) end\n" + 
       "                                        WHEN SPTMST.SPTLEN BETWEEN 60 AND 599 THEN '00:0'||RTRIM(CHAR(SPTMST.SPTLEN/60))||':'||\n" + 
       "                                        case when length(RTRIM(CHAR(MOD(SPTMST.SPTLEN,60)))) = 1 then '0'||RTRIM(CHAR(MOD(SPTMST.SPTLEN,60)))\n" + 
       "                                        else RTRIM(CHAR(MOD(SPTMST.SPTLEN,60))) end\n" + 
       "                                        END AS DURATION,\n" + 
       "                                        CASE WHEN (SPTMST.USRCHR IN ('',' ') OR SPTMST.USRCHR IS NULL) THEN '0' ELSE SPTMST.USRCHR END AS SPOTFORMATID,\n" + 
       "                                        CPYANC.AUTOID AS MEDIAID,\n" + 
       "                                        CPYANC.BRND AS PRODUCTID,\n" + 
       "                                        DECIMAL(SPTREV.SPTRT/100,10,2) AS SPOTPRICE\n" + 
       "                           FROM PARADB.SPTMST SPTMST\n" + 
       "    LEFT OUTER JOIN PARADB.SPTCPY SPTCPY ON\n" + 
       "                    SPTCPY.SPTMSTID = SPTMST.SPTMSTID\n" + 
       "    LEFT OUTER JOIN PARADB.SPTREV SPTREV ON\n" + 
       "                    SPTREV.SPTMSTID = SPTMST.SPTMSTID AND SPTREV.SPTMSTID = SPTCPY.SPTMSTID\n" + 
       "    LEFT OUTER JOIN PARADB.BRKDT BRKDT ON\n" + 
       "                    SPTMST.BRKDTID = BRKDT.BRKDTID\n" + 
       "    LEFT OUTER JOIN PARADB.LOGEDT LOGEDT ON\n" + 
       "                    BRKDT.LOGEDTID = LOGEDT.LOGEDTID\n" + 
       "    LEFT OUTER JOIN PARADB.LOGLINE LOGLINE ON\n" + 
       "                    LOGEDT.LOGEDTID = LOGLINE.LOGEDTID\n" + 
       "    LEFT OUTER JOIN PARADB.LOGCMT LOGCMT ON\n" + 
       "                    LOGEDT.LOGEDTID = LOGCMT.LOGEDTID\n" + 
       "    LEFT OUTER JOIN PARADB.CPYANC CPYANC ON\n" + 
       "                    SPTCPY.ADVID = CPYANC.ADVID AND\n" + 
       "                    SPTCPY.EXTCPYNUM = CPYANC.EXTCPYNUM\n" + 
       "    LEFT OUTER JOIN PARADB.ORDLN ORDLN ON\n" + 
       "                    SPTMST.ORDLNID = ORDLN.ORDLNID\n" + 
       "    LEFT OUTER JOIN PARADB.ORDHDR ORDHDR ON\n" + 
       "                    SPTMST.ORDID = ORDHDR.ORDID\n" + 
       "    LEFT OUTER JOIN PARADB.LOGHDR LOGHDR ON\n" + 
       "                    SPTMST.STNID = LOGHDR.STNID AND\n" + 
       "                    SPTMST.BCSTDT = LOGHDR.BCSTDT\n" + 
       "               JOIN EVENTAS.EVETV_LOG_CERTIFICADO_PROCESADO A ON\n" + 
       "                    LOGHDR.STNID = A.STNID\n" + 
       "                    AND LOGHDR.BCSTDT = A.BCSTDT\n" + 
       "                          WHERE SPTMST.EXCFL = 0\n" + 
       "                                AND SPTMST.ALTLOG = 0\n" + 
       "                                AND SPTMST.ACTSTS=1\n" + 
       "                                AND (ORDLN.ORDLNTYP in(0,2) OR ORDLN.ORDLNTYP is null)\n" + 
       "                                AND LOGEDT.FMTTYP  <> 0\n" + 
/*CH*///       "                                AND BRKDT.BRKCHRRULE IN ('CO','')\n" + 
"                                AND BRKDT.BRKCHRRULE IN ('CO','BI','BF')\n" + //Cambio 2018-06-19 20:06
       "                                AND BRKDT.INVFL = 1\n" + 
    //       "                              AND SPTMST.SPTCHR NOT IN (2,6,12)\n" +
    /*CH*/ "                                AND SPTMST.SPTCHR NOT IN (1,2,5,6,12)\n" +
       "                                AND LOGHDR.LOGEDTLCK >= 7031 -- INDICATIVO DE QUE YA FUE RECON COMPLETE\n" + 
       //"                                AND A.STATUS = 1\n" + 
       "                                AND A.STNID = '" + tsChannels + "'\n" + 
       "                                AND SPTMST.STNID = '" + tsChannels + "'\n" + 
       //"                              AND A.BCSTDT >= CURRENT DATE\n" + 
       //"                              AND SPTMST.BCSTDT >= CURRENT DATE\n" + 
    /*CH*/       "                              AND A.BCSTDT = '" + tsDate + "'\n" +
    /*CH*/       "                              AND SPTMST.BCSTDT = '" + tsDate + "'\n" +
       /*"                              AND A.STNID IN ('9CAN')\n" + 
       "                                AND SPTMST.STNID IN ('9CAN')\n" + 
       "                                AND A.BCSTDT = '2018-01-02'\n" + 
       "                                AND SPTMST.BCSTDT = '2018-01-02'\n" + */
       "                               AND SPTMST.ORDID > 0\n" + 
       "           ORDER BY LOGEDT.STNID,LOGEDT.BCSTDT,SPTMST.ACTTIM";
        return lsQuery;
    }
    
    
}
