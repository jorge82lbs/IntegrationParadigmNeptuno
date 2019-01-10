/**
* Project: Integraton Paradigm - EveTV
*
* File: VentaTradicionalDao.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.model.daos;

import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.beans.RsstProgramasBean;
import com.televisa.comer.integration.model.beans.RsstVentaTradicionalBean;
import com.televisa.comer.integration.model.connection.ConnectionAs400;
import com.televisa.comer.integration.model.interfaces.VentaTradicionalInterface;

import com.televisa.integration.model.types.EvetvSpotsVtaTradicional;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Objeto que accede a base de datos para consultar Venta Tradicional
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class VentaTradicionalDao implements VentaTradicionalInterface {
  
    /**
     * Obtiene consulta para venta tradicional
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return Integer
     */
    @Override
    public String getQueryTraditionalSale(String tsDate, 
                                          String tsChannels
                                          ) {
        String lsQuery = "SELECT ORDLN.ORDID,\n" + 
        "		MAX(ACCTHDR.AGYID) AGYID,\n" + 
        "		MAX(ACCT.ACCTID) ADVID,\n" + 
        "		MAX(ORDHDR.STRDT) STRDT,\n" + 
        "		MAX(ORDHDR.EDT) EDT,\n" + 
        "		MAX(ORDHDR.MCONTID) MCONTID,\n" + 
        "		MAX(ORDHDR.RTCRD) RTCRD,\n" + 
        "		MAX(RATE.IDTARGET) AS IDTARGET,\n" + 
        "		SPTMST.STNID as STNID,\n" + 
        "		SPTMST.SPTMSTID,\n" + 
        "		SPTMST.BCSTDT,\n" + 
        "		MAX(SPTMST.PGMID) BUYUNTID,\n" + 
        "		MAX(SPTMST.BRKDTID) BRKDTID,\n" + 
        "		MAX(CASE LENGTH(RTRIM(CHAR(SPTMST.ACTTIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(SPTMST.ACTTIM),1,1)||':'||\n" + 
        "			SUBSTR(CHAR(SPTMST.ACTTIM),2,2)||':'||SUBSTR(CHAR(SPTMST.ACTTIM),4,2) WHEN 8 THEN\n" + 
        "			CASE SUBSTR(CHAR(SPTMST.ACTTIM),1,2) WHEN '24' THEN '00' WHEN '25' THEN '01' WHEN '26' THEN '26'\n" + 
        "			WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(SPTMST.ACTTIM),1,2)\n" + 
        "			END ||':'||SUBSTR(CHAR(SPTMST.ACTTIM),3,2)||':'||SUBSTR(CHAR(SPTMST.ACTTIM),5,2) ELSE ' ' END) AS ACTTIM,\n" + 
        "			MAX(CASE WHEN SPTMST.SPTLEN <=59 THEN '00:00:'||case when length(RTRIM(CHAR(MOD(SPTMST.SPTLEN,60)))) = 1 then '0'||RTRIM(CHAR(MOD(SPTMST.SPTLEN,60)))\n" + 
        "			else RTRIM(CHAR(MOD(SPTMST.SPTLEN,60))) end\n" + 
        "			WHEN SPTMST.SPTLEN BETWEEN 60 AND 599 THEN '00:0'||RTRIM(CHAR(SPTMST.SPTLEN/60))||':'||\n" + 
        "			case when length(RTRIM(CHAR(MOD(SPTMST.SPTLEN,60)))) = 1 then '0'||RTRIM(CHAR(MOD(SPTMST.SPTLEN,60)))\n" + 
        "			else RTRIM(CHAR(MOD(SPTMST.SPTLEN,60))) end\n" + 
        "			END) AS SPTLEN,\n" + 
        "			SPTMST.STNID as STNID_SPOT,\n" + 
        "			'' as TITLEID,\n" + 
        "			'' AS EPISODENAMEID,\n" + 
        "		( CASE   IFNULL(SPTMST.USRCHR, 'SPOTEO REGULAR')\n" + 
        "		when  ('A')  then  ('SPOTEO REGULAR')\n" + 
        "		when  (' ')  then  ('SPOTEO REGULAR')\n" + 
        "		when  ('D')  then  ('SPOTEO REGULAR')\n" + 
        "		when  ('F')  then  ('SPOTEO REGULAR')\n" + 
        "		when  ('H')  then  ('SPOTEO REGULAR')\n" + 
        "		when  ('I')  then  ('SPOTEO REGULAR')\n" + 
        "		when  ('L')  then  ('SPOTEO REGULAR')\n" + 
        "		when  ('M')  then  ('SPOTEO REGULAR')\n" + 
        "		when  ('S')  then  ('SPOTEO REGULAR')\n" + 
        "		when  ('$')  then  ('infomercial')\n" + 
        "		when  ('/')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('B')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('C')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('E')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('G')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('J')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('K')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('N')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('O')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('P')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('Q')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('R')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('T')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('U')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('V')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('W')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('X')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('Y')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		when  ('Z')  then  ('INTEGRACION DE PRODUCTO')\n" + 
        "		ELSE ('SPOTEO REGULAR') END ) AS TIPOPUBLICIDADID,\n" + 
        "		CASE WHEN (ORDLN.USRCHR IS NULL OR ORDLN.USRCHR = ' ') THEN '0' ELSE ORDLN.USRCHR END AS SPOTFORMATID,\n" + 
        "		'' AS SPOTCOMMENTS,\n" + 
        "		MAX(CPYANC.AUTOID) AS AUTOID,\n" + 
        "		CASE COUNT(DISTINCT(SPTCPY.EXTCPYNUM)) WHEN 0 THEN ' ' WHEN 1 THEN MAX(SPTCPY.EXTCPYNUM) ELSE 'CONSULTAR PAUTA' END EXTCPYNUM,\n" + 
        "		MAX(CPYANC.BRND) BRND,\n" + 
        "		CAST(MAX((((SPTREV.SPTRT)*(1-((MCONT.DISCVAL + ACCTHDR.STDDISC + ORDLN.LNDISC)/10000)))/100)) AS DOUBLE) SPTRT,\n" + 
        "		CAST(MAX((((SPTREV.SPTRT)*(1-((MCONT.DISCVAL + ACCTHDR.STDDISC + ORDLN.LNDISC)/10000)))/100)) AS DOUBLE) SPOTPRICE20,\n" + 
        "		CAST(MAX((((SPTREV.SPTRT)*(1-((MCONT.DISCVAL + ACCTHDR.STDDISC + ORDLN.LNDISC)/10000)))/100)) AS DOUBLE) SPOTPRICEPRODUCTION,\n" + 
        "		CASE CAST(ORDLN.REVSTS AS CHAR) WHEN 0 THEN 'S'\n" + 
        "												  WHEN 1 THEN 'N'\n" + 
        "												  WHEN 2 THEN 'S'\n" + 
        "												  WHEN 3 THEN 'N'\n" + 
        "												  WHEN 4 THEN 'S'\n" + 
        "												  WHEN 5 THEN 'S'\n" + 
        "												  WHEN 6 THEN 'S' END AS REVSTS,\n" + 
        "			1 AS STATUS,\n" + 
        "			MAX(SPTMST.PLOEXCREA) AS PLOEXCREA,\n" + 
        "			MAX(SPTMST.EXCFL) AS EXCFL\n" + 
        "    FROM PARADB.ACCT ACCT,\n" + 
        "		PARADB.ORDHDR ORDHDR,\n" + 
        "		PARADB.ORDLN ORDLN,\n" + 
        "		PARADB.MCONT MCONT,\n" + 
        "		PARADB.SPTCPY SPTCPY,\n" + 
        "		PARADB.SPTREV SPTREV,\n" + 
        "		PARADB.SPTMST SPTMST,\n" + 
        "		PARADB.ACCTHDR ACCTHDR,\n" + 
        "		PARADB.A_ORDLN LA\n" + 
        "			LEFT JOIN PARADB.CPYANC CPYANC\n" + 
        "			ON  SPTCPY.ADVID = CPYANC.ADVID\n" + 
        "			AND SPTCPY.EXTCPYNUM = CPYANC.EXTCPYNUM\n" + 
        "			LEFT JOIN PARADB.PRD PRD\n" + 
        "			ON  ORDLN.PRDID1 = PRD.PRDID\n" + 
        "			LEFT JOIN PARADB.BRANDS BRANDS\n" + 
        "				ON  CPYANC.ADVID = BRANDS.ADVID\n" + 
        "				AND CPYANC.BRND = BRANDS.BRND\n" + 
        "			LEFT JOIN EVENTAS.CA_RATECARDS RATE ON ORDHDR.RTCRD = RATE.RTCRD\n" + 
        "	WHERE ACCT.ACCTID = ORDHDR.ADVID\n" + 
        "			AND ORDHDR.ORDID = ORDLN.ORDID\n" + 
        "			AND ORDHDR.MCONTID = MCONT.MCONTID\n" + 
        "			AND ORDLN.ORDID = SPTMST.ORDID\n" + 
        "			AND ORDLN.ORDLNID = SPTMST.ORDLNID\n" + 
        "			AND SPTCPY.SPTMSTID = SPTMST.SPTMSTID\n" + 
        "			AND SPTREV.SPTMSTID = SPTMST.SPTMSTID\n" + 
        "			AND SPTREV.STNID = SPTMST.STNID\n" + 
        "			AND SPTCPY.ADVID = ACCTHDR.ADVID\n" + 
        "			AND ORDHDR.ACCTHDRID = ACCTHDR.ACCTHDRID\n" + 
        "			AND SPTMST.STNID IN (" + tsChannels + ")\n" +  //TODO .- Es temporal, para que no se tarde mucho
        "			AND SPTMST.ACTSTS = 1\n" + 
        "			AND ORDLN.ORDLNTYP IN (0, 2)\n" + 
        "			AND SPTMST.SPTCHR NOT IN (1,2,5,6,12)\n" + 
        "			AND SPTMST.USRCHR NOT IN ('$','@','#')\n" + 
        "			AND ORDLN.ORDID = LA.ORDID\n" + 
        "			AND ORDLN.ORDLNNUM = LA.ORDLNNUM\n" + 
        "			AND ORDLN.ORDLNTYP = LA.ORDLNTYP\n" + 
        "			AND ORDLN.ORDLNSEQ = LA.ORDLNSEQ\n" + 
        "			AND LA.CHGTIM IN\n" + 
        "			(SELECT  MIN(CHGTIM)\n" + 
        "			FROM    PARADB.A_ORDLN\n" + 
        "			WHERE   ORDLN.ORDID = ORDID\n" + 
        "			AND ORDLN.ORDLNNUM = ORDLNNUM\n" + 
        "			AND ORDLN.ORDLNTYP = ORDLNTYP\n" + 
        "			AND ORDLN.ORDLNSEQ = ORDLNSEQ\n" + 
        "			AND SPTMST.BKDT = CHGDT\n" + 
        "			AND SPTMST.BCSTDT >= CURRENT DATE \n" + 
        "			AND POSSTR(ORDHDR.MCONTID,'MC') < 1)\n" + 
        "	GROUP BY SPTMST.STNID, ORDLN.ORDID, SPTMST.BCSTDT, ORDLN.REVSTS, ORDLN.USRCHR, SPTMST.SPTMSTID, BRANDS.TBLA,SPTMST.USRCHR\n" + 
        "	ORDER BY SPTMST.STNID, ORDLN.ORDID";
        return lsQuery;
    }

    /**
     * Obtiene Lista de registros de Venta Tradicional
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return Integer
     */
    @Override
    public List<RsstVentaTradicionalBean> getTraditionalSaleFromParadigmService(String tsDate, 
                                                                                String tsChannels) {
        List<RsstVentaTradicionalBean> loTraditional =
            new ArrayList<RsstVentaTradicionalBean>();
        Connection                     loCnn = new ConnectionAs400().getConnection();
        ResultSet                      loRs = null;        
        String                         lsQueryParadigm = 
            getQueryTraditionalSale(tsDate, tsChannels);       
        //System.out.println(lsQueryParadigm);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                RsstVentaTradicionalBean loTraditionalBean = 
                    new RsstVentaTradicionalBean();                  
                loTraditionalBean.setLsOrdid(loRs.getString("ORDID"));
                loTraditionalBean.setLsAgyid(loRs.getString("AGYID"));
                loTraditionalBean.setLsAdvid(loRs.getString("ADVID"));
                loTraditionalBean.setLsStrdt(loRs.getString("STRDT"));
                loTraditionalBean.setLsEdt(loRs.getString("EDT"));
                loTraditionalBean.setLsMcontid(loRs.getString("MCONTID"));
                loTraditionalBean.setLsRtcrd(loRs.getString("RTCRD"));
                loTraditionalBean.setLsIdtarget(loRs.getString("IDTARGET"));
                loTraditionalBean.setLsStnid(loRs.getString("STNID"));
                loTraditionalBean.setLsSptmstid(loRs.getString("SPTMSTID"));
                loTraditionalBean.setLsBcstdt(loRs.getString("BCSTDT"));
                loTraditionalBean.setLsBuyuntid(loRs.getString("BUYUNTID"));                
                loTraditionalBean.setLsBrkdtid(loRs.getString("BRKDTID"));
                loTraditionalBean.setLsActtim(loRs.getString("ACTTIM"));
                loTraditionalBean.setLsSptlen(loRs.getString("SPTLEN"));
                loTraditionalBean.setLsStnid_Spot(loRs.getString("STNID_SPOT"));
                loTraditionalBean.setLsTitleid(loRs.getString("TITLEID"));
                loTraditionalBean.setLsEpisodenameid(loRs.getString("EPISODENAMEID"));
                loTraditionalBean.setLsTipopublicidadid(loRs.getString("TIPOPUBLICIDADID"));
                loTraditionalBean.setLsSpotformatid(loRs.getString("SPOTFORMATID"));
                loTraditionalBean.setLsSpotcomments(loRs.getString("SPOTCOMMENTS"));
                loTraditionalBean.setLsAutoid(loRs.getString("AUTOID"));
                loTraditionalBean.setLsExtcpynum(loRs.getString("EXTCPYNUM"));
                loTraditionalBean.setLsBrnd(loRs.getString("BRND"));
                loTraditionalBean.setLsSptrt(loRs.getString("SPTRT"));
                loTraditionalBean.setLsSpotprice20(loRs.getString("SPOTPRICE20"));
                loTraditionalBean.setLsSpotpriceproduction(loRs.getString("SPOTPRICEPRODUCTION"));
                loTraditionalBean.setLsRevsts(loRs.getString("REVSTS"));
                loTraditionalBean.setLsStatus(loRs.getString("STATUS"));
                loTraditionalBean.setLsPloexcrea(loRs.getString("PLOEXCREA"));
                loTraditionalBean.setLsExcfl(loRs.getString("EXCFL"));
                loTraditional.add(loTraditionalBean);
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
        return loTraditional;
    }

    /**
     * Ejecuta llamado a Procedimiento Almacenado en Paradigm
     * @autor Jorge Luis Bautista Santiago
     * @return ResponseUpdDao
     */
    @Override
    public ResponseUpdDao callTraditionalSalePr(String tsStnid, 
                                                String tsStrdt, 
                                                String tsEdt) {
        System.out.println("Dentro de callTraditionalSalePr");
        System.out.println("**** setteando EVETV_SPOTS_VTA_TRADICIONAL ******");
        System.out.println("tsStnid["+tsStnid+"]");
        System.out.println("tsStrdt["+tsStrdt+"]");
        System.out.println("tsEdt["+tsEdt+"]");
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        String lsResult = "EVETV_SPOTS_VTA_TRADICIONAL(";
        Connection        loCnn = new ConnectionAs400().getConnection();
        CallableStatement loCallStmt = null;
        String            lsQueryParadigm = "call EVENTAS.EVETV_SPOTS_VTA_TRADICIONAL(?,?,?)";
        try {
            //System.out.println("Dentro de callTraditionalSalePr llamando "+lsQueryParadigm);
            loCallStmt = loCnn.prepareCall(lsQueryParadigm);            
            
            if(tsStnid != null){
                if(!tsStnid.trim().equalsIgnoreCase("")){
                    loCallStmt.setString(1, tsStnid);
                    lsResult += ""+tsStnid+",";
                }else{
                    loCallStmt.setString(1, null);
                    lsResult += "null,";
                }
            }else{
                loCallStmt.setString(1, null);
                lsResult += "null,";
            }
            
            if(tsStrdt != null){
                if(!tsStrdt.trim().equalsIgnoreCase("")){
                    java.sql.Date     ltDateStrt = getDateYYYYMMDD(tsStrdt);
                    loCallStmt.setDate(2, ltDateStrt);    
                    lsResult += ""+ltDateStrt+",";
                }else{
                    loCallStmt.setDate(2, null);
                    lsResult += "null,";
                }
                
            }else{
                loCallStmt.setDate(2, null);
                lsResult += "null,";
            }
            
            
            if(tsEdt != null){
                if(!tsEdt.trim().equalsIgnoreCase("")){
                    java.sql.Date     ltDateEnd = getDateYYYYMMDD(tsEdt);
                    loCallStmt.setDate(3, ltDateEnd);
                    lsResult += ""+ltDateEnd+",";
                }else{
                    loCallStmt.setDate(3, null);
                    lsResult += "null,";
                }
                
            }else{
                loCallStmt.setDate(3, null);
                lsResult += "null,";
            }                                    
            loCallStmt.executeUpdate();
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLsMessage(""+lsResult.substring(0, lsResult.length()-1)+") Execute Success!!!");
            System.out.println(lsResult.substring(0, lsResult.length()-1)+") Execute Success!!!");
        } catch (SQLException loExSql) {
            //System.out.println("ERROR AL EJECUTAR: "+loExSql.getMessage());
            loResponseUpdDao.setLsResponse("ERROR");
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
     * Obtiene todos los registros a procesar para enviar a neptuno en venta tradicional
     * @autor Jorge Luis Bautista Santiago
     * @param tsType
     * @return List
     */
    @Override
    public List<EvetvSpotsVtaTradicional> getAllTraditionaSaleSpots(String tsType) {
        List<EvetvSpotsVtaTradicional> loTraditional =
            new ArrayList<EvetvSpotsVtaTradicional>();
        Connection                     loCnn = new ConnectionAs400().getConnection();
        ResultSet                      loRs = null;        
        String                         lsQueryParadigm = 
            getQueryAllTraditionalSaleSpots(tsType);       
        //System.out.println(lsQueryParadigm);
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            int liCount = 0;
            while(loRs.next()){
                EvetvSpotsVtaTradicional loTraditionalBean = 
                    new EvetvSpotsVtaTradicional();   
                loTraditionalBean.setLsActionC(loRs.getString("ACTIONC"));
                loTraditionalBean.setLsAction(loRs.getString("ACTION"));
                loTraditionalBean.setLsOrdid(loRs.getString("ORDID"));
                loTraditionalBean.setLsAgyid(loRs.getString("AGYID"));
                loTraditionalBean.setLsAdvid(loRs.getString("ADVID"));
                loTraditionalBean.setLsStrdt(loRs.getString("STRDT"));
                loTraditionalBean.setLsEdt(loRs.getString("EDT"));
                loTraditionalBean.setLsMcontid(loRs.getString("MCONTID"));
                loTraditionalBean.setLsRtcrd(loRs.getString("RTCRD"));
                loTraditionalBean.setLsIdtarget(loRs.getString("IDTARGET"));
                loTraditionalBean.setLsStnid(loRs.getString("STNID"));
                loTraditionalBean.setLsSptmstid(loRs.getString("SPTMSTID"));
                loTraditionalBean.setLsBcstdt(loRs.getString("BCSTDT"));
                loTraditionalBean.setLsBuyuntid(loRs.getString("BUYUNTID"));                
                loTraditionalBean.setLsBrkdtid(loRs.getString("BRKDTID"));
                loTraditionalBean.setLsActtim(loRs.getString("ACTTIM"));
                loTraditionalBean.setLsSptlen(loRs.getString("SPTLEN"));
                loTraditionalBean.setLsStnid_Spot(loRs.getString("STNID_SPOT"));
                loTraditionalBean.setLsTitleid(loRs.getString("TITLEID"));
                loTraditionalBean.setLsEpisodenameid(loRs.getString("EPISODENAMEID"));
                loTraditionalBean.setLsTipopublicidadid(loRs.getString("TIPOPUBLICIDADID"));
                loTraditionalBean.setLsSpotformatid(loRs.getString("SPOTFORMATID"));
                loTraditionalBean.setLsSpotcomments(loRs.getString("SPOTCOMMENTS"));
                loTraditionalBean.setLsAutoid(loRs.getString("AUTOID"));
                loTraditionalBean.setLsExtcpynum(loRs.getString("EXTCPYNUM"));
                loTraditionalBean.setLsBrnd(loRs.getString("BRND"));
                loTraditionalBean.setLsSptrt(loRs.getString("SPTRT"));
                loTraditionalBean.setLsSpotprice20(loRs.getString("SPOTPRICE20"));
                loTraditionalBean.setLsSpotpriceproduction(loRs.getString("SPOTPRICEPRODUCTION"));
                loTraditionalBean.setLsRevsts(loRs.getString("REVSTS"));
                loTraditionalBean.setLsStatus(loRs.getString("STATUS"));
                loTraditionalBean.setLsPloexcrea(loRs.getString("PLOEXCREA"));
                loTraditionalBean.setLsExcfl(loRs.getString("EXCFL"));
                
                loTraditional.add(loTraditionalBean);
                liCount++;
                /*if(liCount > 40){
                    break;
                }*/
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
        return loTraditional;
    }

    /**
     * Genera instruccion para obtener todos los registros a procesar para enviar a neptuno en venta tradicional
     * @autor Jorge Luis Bautista Santiago
     * @param tsType
     * @return List
     */
    @Override
    public String getQueryAllTraditionalSaleSpots(String tsType) {
        String lsQuery = 
            "\n" + 
            " SELECT ACTION,\n" + 
            "		ORDID,\n" + 
            "		AGYID,\n" + 
            "		ADVID,\n" + 
            "		STRDT,\n" + 
            "		EDT,\n" + 
            "		MCONTID,\n" + 
            "		RTCRD,\n" + 
            "		IDTARGET,\n" + 
            "		STNID,\n" + 
            "		SPTMSTID,\n" + 
            "		BCSTDT,\n" + 
            "		BUYUNTID,\n" + 
            "		BRKDTID,\n" + 
            "		ACTTIM,\n" + 
            "		SPTLEN,\n" + 
            "		STNID_SPOT,\n" + 
            "		TITLEID,\n" + 
            "		EPISODENAMEID,\n" + 
            "		TIPOPUBLICIDADID,\n" + 
            "		SPOTFORMATID,\n" + 
            "		SPOTCOMMENTS,\n" + 
            "		AUTOID,\n" + 
            "		EXTCPYNUM,\n" + 
            "		BRND,\n" + 
            "		SPTRT,\n" + 
            "		SPOTPRICE20,\n" + 
            "		SPOTPRICEPRODUCTION,\n" + 
            "		REVSTS,\n" + 
            "		STATUS,\n" + 
            "		PLOEXCREA,\n" + 
            "		EXCFL,\n" + 
        "               ACTIONC\n" + 
            "   FROM EVENTAS.EVETV_SPOTS_VTA_TRADICIONAL"+
            "  WHERE STATUS = '" + tsType + "'";
        return lsQuery;
    }
    
    /**
     * Actualiza tabla de control de Televisa en venta tradicional
     * @autor Jorge Luis Bautista Santiago
     * @param tsOrdIdEveTv
     * @param tsSptmtid
     * @param tsStatus
     * @return List
     */
    public Integer updateVtaTradicionalHdr(String tsOrdIdEveTv, 
                                           String tsSptmtid,
                                           String tsStatus
                                           ) {
        Integer    liReturn = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = "UPDATE EVENTAS.EVETV_SPOTS_VTA_TRADICIONAL " +
                                     "   SET STATUS = '" + tsStatus + "' " + 
                                     " WHERE ORDID  = " + tsOrdIdEveTv + " "+
                                     "   AND SPTMSTID = " + tsSptmtid ;
        try {
            Statement loStmt = loCnn.createStatement();
            liReturn = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println("Error en Update ################"+loExSql.getMessage());
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
     * Inserta en  tabla de control de Integracion el set de resultados de la extracion
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsUserName
     * @param tsIdUser
     * @param toEvetvSpotsVtaTradicional
     * @return ResponseUpdDao
     */
    public ResponseUpdDao insertVtaTradicionalCtrl(String tsIdRequest,
                                                   String tsIdService,
                                                   String tsUserName,
                                                   String tsIdUser,
                                                   EvetvSpotsVtaTradicional toEvetvSpotsVtaTradicional
                                                  ) {
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection     loCnn = new ConnectionAs400().getConnection();
        String         lsQueryParadigm = 
              getQueryInsertTraditional(tsIdRequest,
                                        tsIdService,
                                        tsUserName,
                                        tsIdUser,
                                        toEvetvSpotsVtaTradicional);
        try {
            Statement loStmt = loCnn.createStatement();
            Integer liRes = loStmt.executeUpdate(lsQueryParadigm);     
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
     * Obtiene intruccion para Insertar en  tabla de control de Integracion el set de resultados de la extracion
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsUserName
     * @param tsIdUser
     * @param toEvetvSpotsVtaTradicional
     * @return ResponseUpdDao
     */
    public String getQueryInsertTraditional(String lsIdRequest,
                                            String lsIdService,
                                            String lsUserName,
                                            String lsIdUser,
                                            EvetvSpotsVtaTradicional toEvetvSpotsVtaTradicional){
        String lsQuery = 
            "INSERT INTO EVENTAS.EVETV_INT_RST_VTRADICIONAL_TAB(ID_REQUEST,\n" + 
            "					ID_SERVICE,\n" + 
            "					ORDID,\n" + 
            "					AGYID,\n" + 
            "					ADVID,\n" + 
            "					STRDT,\n" + 
            "					EDT,\n" + 
            "					MCONTID,\n" + 
            "					RTCRD,\n" + 
            "					IDTARGET,\n" + 
            "					STNID,\n" + 
            "					SPTMSTID,\n" + 
            "					BCSTDT,\n" + 
            "					BUYUNTID,\n" + 
            "					BRKDTID,\n" + 
            "					ACTTIM,\n" + 
            "					SPTLEN,\n" + 
            "					STNID_SPOT,\n" + 
            "					TITLEID,\n" + 
            "					EPISODENAMEID,\n" + 
            "					TIPOPUBLICIDADID,\n" + 
            "					SPOTFORMATID,\n" + 
            "					SPOTCOMMENTS,\n" + 
            "					AUTOID,\n" + 
            "					EXTCPYNUM,\n" + 
            "					BRND,\n" + 
            "					SPTRT,\n" + 
            "					SPOTPRICE20,\n" + 
            "					SPOTPRICEPRODUCTION,\n" + 
            "					REVSTS,\n" + 
            "					STATUS,\n" + 
            "					PLOEXCREA,\n" + 
            "					EXCFL,\n" + 
            "					IND_ESTATUS,\n" + 
            "					ATTRIBUTE1,\n" + 
        "                                       ATTRIBUTE2,\n" + 
            "					ATTRIBUTE15,\n" + 
            "					FEC_CREATION_DATE,\n" + 
            "					NUM_CREATED_BY,\n" + 
            "					FEC_LAST_UPDATE_DATE,\n" + 
            "					NUM_LAST_UPDATED_BY\n" + 
            "					)\n" + 
            "	                	VALUES ( " + lsIdRequest+",\n" + 
            "					" + lsIdService+",\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsOrdid() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsAgyid() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsAdvid() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsStrdt() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsEdt() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsMcontid() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsRtcrd() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsIdtarget() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsStnid() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsSptmstid() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsBcstdt() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsBuyuntid() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsBrkdtid() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsActtim() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsSptlen() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsStnid_Spot() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsTitleid() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsEpisodenameid() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsTipopublicidadid() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsSpotformatid() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsSpotcomments() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsAutoid() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsExtcpynum() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsBrnd() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsSptrt() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsSpotprice20() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsSpotpriceproduction() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsRevsts() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsStatus() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsPloexcrea() + "',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsExcfl() + "',\n" + 
            "                                   'A',\n" + 
            "					'" + toEvetvSpotsVtaTradicional.getLsAction() + "',\n" + 
        "                                       '" + toEvetvSpotsVtaTradicional.getLsActionC() + "',\n" + 
            "					'" + lsUserName + "',\n" + 
            "				   CURRENT_TIMESTAMP,\n" + 
            "				   " + lsIdUser + ",\n" + 
            "				   CURRENT_TIMESTAMP,\n" + 
            "				   " + lsIdUser + "\n" + 
            "				  )";
        
        return lsQuery;
    }
    
    /**
     * Actualiza tabla de control de Integracion con la respuesta de neptuno
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsOrderId
     * @param tsSpotId
     * @param tsEstatus
     * @param tsDescErr
     * @return ResponseUpdDao
     */
    public ResponseUpdDao updateVtaTradicionalCtrl(String tsIdRequest,
                                                   String tsIdService,
                                                   String tsOrderId,
                                                   String tsSpotId,
                                                   String tsEstatus,
                                                   String tsDescErr) {
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection     loCnn = new ConnectionAs400().getConnection();
        String         lsQueryParadigm = 
              getQueryUpdateTraditional(tsIdRequest,
                                        tsIdService,                                        
                                        tsOrderId,
                                        tsSpotId,
                                        tsEstatus,
                                        tsDescErr
                                        );
        try {
            Statement loStmt = loCnn.createStatement();
            Integer liRes = loStmt.executeUpdate(lsQueryParadigm);     
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
     * Genera intruccion para Actualizar tabla de control de Integracion con la respuesta de neptuno
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsOrderId
     * @param tsSpotId
     * @param tsEstatus
     * @param tsDescErr
     * @return ResponseUpdDao
     */
    public String getQueryUpdateTraditional(String tsIdRequest,
                                            String tsIdService,                                            
                                            String tsOrderId,
                                            String tsSpotId,
                                            String tsEstatus,
                                            String tsDescErr
                                            ){
        String lsQuery = 
        "UPDATE EVENTAS.EVETV_INT_RST_VTRADICIONAL_TAB\n" + 
        "   SET IND_ESTATUS = '" + tsEstatus + "',\n" + 
        "       FEC_LAST_UPDATE_DATE = CURRENT_TIMESTAMP,\n" +
        "       IND_DESC_NEPTUNO = '" + tsDescErr + "'\n"+
        " WHERE ID_REQUEST = " + tsIdRequest + "\n" + 
        "   AND ID_SERVICE = " + tsIdService + "\n" + 
        "   AND ORDID = '" + tsOrderId + "'"+
        "   AND SPTMSTID = '" + tsSpotId + "'";
        return lsQuery;
    }
    /**
     * Convierte una cadena en formato fecha a una fecha real sql con ese mismo formato
     * @autor Jorge Luis Bautista Santiago
     * @param lsDateStr
     * @return java.sql.Date
     */
    private java.sql.Date getDateYYYYMMDD(String lsDateStr){
        SimpleDateFormat loFormatText = new SimpleDateFormat("yyyy-MM-dd");
        String           lsStrDate = lsDateStr;
        java.util.Date             ltDatePivot = null;
        try {
            ltDatePivot = loFormatText.parse(lsStrDate);
        } catch (ParseException loEx) {
            loEx.printStackTrace();
        }
        java.sql.Date ltDateResponse = new java.sql.Date(ltDatePivot.getTime());
        return ltDateResponse;
    }
    
}
