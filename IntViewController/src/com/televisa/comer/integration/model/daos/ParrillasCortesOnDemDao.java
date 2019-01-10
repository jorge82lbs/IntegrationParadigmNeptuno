/**
* Project: Integraton Paradigm - EveTV
*
* File: ParrillasCortesOnDemDao.java
*
* Created on: Febrero 16, 2018 at 11:00
*
* Copyright (c) - OMW - 2018
*/
package com.televisa.comer.integration.model.daos;

import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.beans.RsstParrillasCortesBean;
import com.televisa.comer.integration.model.connection.ConnectionAs400;
import com.televisa.comer.integration.model.interfaces.ParrillasCortesOnDemInterface;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** Objeto que accede a base de datos para consultar Parrillas de Programas y Cortes
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Febrero 14, 2018, 12:00 pm
 */
public class ParrillasCortesOnDemDao implements ParrillasCortesOnDemInterface {

    /**
     * Obtiene cadena de consulta de Parrillas de programas y Cortes
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return String
     */
    @Override
    public String getQueryOnDemandParadigm(String tsChannels,
                                           String tsDateStrt,
                                           String tsDateEnd,
                                           String tsBuyunit) {
        String lsQuery = 
            "                        SELECT \n" + 
            "                            LOGEDT.BCSTTIM, \n" + 
            "                            LOGEDT.SEQNUM, \n" + 
            "                           LOGEDT.FMTTYP, \n" + 
            "                            -1 AS UNTNUM, \n" + 
            "                            LOGEDT.STNID AS CHANNELID, \n" + 
            "                            LOGEDT.BCSTDT AS DATEVALUE, \n" + 
            "                            CASE LENGTH(RTRIM(CHAR(PARADB.PGMSCH.SCHTIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,1)||':'||\n" + 
            "                SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),2,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),4,2) WHEN 8 THEN\n" + 
            "                CASE SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25' WHEN '26' THEN '26'\n" + 
            "                WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,2)\n" + 
            "                END ||':'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),3,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),5,2) ELSE ' ' END AS HOURSTART,\n" + 
            "                CASE LENGTH(RTRIM(CHAR(PARADB.PGMSCH.ETIM))) WHEN 7 THEN\n" + 
            "                    CASE WHEN SUBSTR(CHAR(PARADB.PGMSCH.ETIM),1,1) = '2' THEN '26:'||SUBSTR(CHAR(PARADB.PGMSCH.ETIM),2,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.ETIM),4,2)\n" + 
            "                        ELSE '0'||SUBSTR(CHAR(PARADB.PGMSCH.ETIM),1,1)||':'||\n" + 
            "                             SUBSTR(CHAR(PARADB.PGMSCH.ETIM),2,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.ETIM),4,2)\n" + 
            "                    END\n" + 
            "                             WHEN 8 THEN\n" + 
            "                             CASE SUBSTR(CHAR(PARADB.PGMSCH.ETIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25' WHEN '26' THEN '26'\n" + 
            "                            WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(PARADB.PGMSCH.ETIM),1,2)\n" + 
            "                            END ||':'||SUBSTR(CHAR(PARADB.PGMSCH.ETIM),3,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.ETIM),5,2) ELSE ' ' END AS HOUREND, \n" + 
            "                                CASE LENGTH(RTRIM(CHAR(PARADB.PGMSCH.SCHTIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,1)||':'|| \n" + 
            "                            SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),2,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),4,2) WHEN 8 THEN \n" + 
            "                            CASE SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25' WHEN '26' THEN '26' \n" + 
            "                            WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,2) \n" + 
            "                            END ||':'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),3,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),5,2) ELSE ' ' END AS HOUR, \n" + 
            "                            CASE WHEN PARADB.PGMTTLDSC.SERIESNAME = '' THEN PARADB.PGMTTLDSC.TITLESCHNAME \n" + 
            "                                 WHEN PARADB.PGMTTLDSC.TITLESCHNAME IS NULL THEN PARADB.BUYUNTHDR.BUYUNTID \n" + 
            "                                 ELSE PARADB.PGMTTLDSC.SERIESNAME END AS TITLE, \n" + 
            "                          CASE WHEN PARADB.PGMSCH.PGMDSCID IS NULL THEN PARADB.BUYUNTHDR.BUYUNTID \n" + 
            "                                 WHEN PARADB.PGMTTLDSC.TITLEREALNAME = '' THEN PARADB.PGMTTLDSC.TITLESCHNAME \n" + 
            "                                 ELSE PARADB.BUYUNTHDR.BUYUNTID \n" + 
            "                             END AS EPISODENAME, \n" + 
            "                            PARADB.PGMTTLDSC.EPISODE AS EPNO, \n" + 
            "                            CASE LENGTH(RTRIM(CHAR(PARADB.PGMSCH.NOMLEN)))\n" + 
            "                WHEN 5 THEN '00:0'||SUBSTR(CHAR(PARADB.PGMSCH.NOMLEN),1,1)||':'||\n" + 
            "                SUBSTR(CHAR(PARADB.PGMSCH.NOMLEN),2,2)\n" + 
            "                WHEN 6 THEN '00:'||SUBSTR(CHAR(PARADB.PGMSCH.NOMLEN),1,2)||':'||\n" + 
            "                SUBSTR(CHAR(PARADB.PGMSCH.NOMLEN),3,2)\n" + 
            "                WHEN 7 THEN '0'||SUBSTR(CHAR(PARADB.PGMSCH.NOMLEN),1,1)||':'||\n" + 
            "                SUBSTR(CHAR(PARADB.PGMSCH.NOMLEN),2,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.NOMLEN),4,2)\n" + 
            "                WHEN 8 THEN CASE SUBSTR(CHAR(PARADB.PGMSCH.NOMLEN),1,2) WHEN '24' THEN '00' WHEN '25' THEN '25' WHEN '26' THEN '26'\n" + 
            "                WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(PARADB.PGMSCH.NOMLEN),1,2)\n" + 
            "                END ||':'||SUBSTR(CHAR(PARADB.PGMSCH.NOMLEN),3,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.NOMLEN),5,2) ELSE ' ' END AS SLOTDURATION,\n" + 
            "                            CASE WHEN PARADB.PGMSCH.PGMDSCID IS NULL THEN PARADB.BUYUNTHDR.BUYUNTID ELSE CHAR(PARADB.PGMSCH.TITLEID) END AS TITLEID, \n" + 
            "                            CASE WHEN PARADB.PGMSCH.PGMDSCID IS NULL THEN PARADB.BUYUNTHDR.BUYUNTID ELSE PARADB.PGMSCH.PGMDSCID END AS EPISODENAMEID, \n" + 
            "                            LOGEDT.PGMID AS BUYUNITID, \n" + 
            "                            PARADB.BUYUNTHDR.BUYLNAM AS BUYUNIT, \n" + 
            "                            CASE WHEN PARADB.PGMSCH.SPECIAL = 0 THEN 'N' ELSE 'S' END AS EXCEPTCOFEPRIS, \n" + 
            "                            CASE WHEN (SELECT B.ID_TIPO FROM \n" + 
            "                                                        EVENTAS.CA_PGM_BUYUNTID_ESP A, EVENTAS.CA_PGM_BUYUNTID_ESP_TIPO B \n" + 
            "                                                        WHERE A.ID_TIPO = B.ID_TIPO \n" + 
        "                                                        AND A.STNID = " + tsChannels + " \n" + // Cambio 20180619 19:55
            "                                                        AND PARADB.PGMSCH.PGMID = (CASE WHEN (A.BUYUNTID = '' AND A.PGMID IS NOT NULL) THEN A.PGMID \n" + 
            "                                                                           WHEN (A.PGMID = '' AND A.BUYUNTID IS NOT NULL) THEN A.BUYUNTID END) \n" + 
            "                                                        AND PARADB.PGMSCH.SCHDT BETWEEN A.STRDT AND A.EDT) = 1 THEN 'E' \n" + 
            "                        WHEN (SELECT B.ID_TIPO FROM \n" + 
            "                                                       EVENTAS.CA_PGM_BUYUNTID_ESP A, EVENTAS.CA_PGM_BUYUNTID_ESP_TIPO B \n" + 
            "                                                        WHERE A.ID_TIPO = B.ID_TIPO \n" + 
        "                                                        AND A.STNID = " + tsChannels + " \n" + // Cambio 20180619 19:55
            "                                                        AND PARADB.PGMSCH.PGMID = (CASE WHEN (A.BUYUNTID = '' AND A.PGMID IS NOT NULL) THEN A.PGMID \n" + 
            "                                                                           WHEN (A.PGMID = '' AND A.BUYUNTID IS NOT NULL) THEN A.BUYUNTID END) \n" + 
            "                                                       AND PARADB.PGMSCH.SCHDT BETWEEN A.STRDT AND A.EDT) = 2 THEN 'P' \n" + 
            "                            ELSE 'R' \n" + 
            "                                END AS EVENTSPECIAL, \n" + 
            "                            CASE WHEN PARADB.PGMSCH.PROTECT = 0 THEN 'N' ELSE 'S' END AS EVENTBEST, \n" +             
/*CH*/        "                            PARADB.PGMTTLDSC.PGMCAT1 AS GENEROID,\n" + 
/*CH*/        "                            (SELECT Y.DSCR FROM PARADB.USRREF Y WHERE PARADB.PGMTTLDSC.PGMCAT1 = Y.REFCD AND Y.REFTYP = 'PGMCAT')  AS GENERONAME,\n" + 
            "                            '' AS BUYUNTID_CORTE, \n" + 
            "                            LOGEDT.BCSTDT AS DATE_CORTE, \n" + 
            "                            '' AS HOUR_CORTE, \n" + 
            "                            -1 AS CORTEID, \n" + 
            "                            '' AS DESCORTE, \n" + 
            "                            '' AS OVERLAY, \n" + 
            "                            -1 AS BREAKID, \n" + 
            "                            '' AS DESCRIPTION, \n" + 
            "                            LOGEDT.BCSTDT AS DATE_BREAK, \n" + 
            "                            '' AS HOUR_BREAK, \n" + 
            "                            '' AS TOTALDURATION, \n" + 
            "                            '' AS COMERCIALDURATION, \n" +  // Agregue la coma, por el from 
/*CH*/        "                            '' AS TYPEBREAK \n" + 
            "                        FROM    PARADB.LOGEDT LOGEDT \n" + 
            "                                LEFT OUTER JOIN PARADB.LOGLINE LOGLINE \n" + 
            "                                    ON LOGEDT.LOGEDTID = LOGLINE.LOGEDTID \n" + 
            "                                LEFT OUTER JOIN PARADB.BRKDT BRKDT \n" + 
            "                                    ON  LOGEDT.LOGEDTID = BRKDT.LOGEDTID \n" + 
            "                                LEFT OUTER JOIN PARADB.LOGCMT LOGCMT \n" + 
            "                                    ON  LOGEDT.LOGEDTID = LOGCMT.LOGEDTID \n" + 
            "                                LEFT OUTER JOIN PARADB.PGMSCH ON PARADB.PGMSCH.STNID = LOGEDT.STNID AND PARADB.PGMSCH.SCHDT = LOGEDT.BCSTDT \n" + 
/*CH*/      "                                AND PARADB.PGMSCH.PGMID = LOGEDT.PGMID\n" + 
/*CH*/        "                              AND LOGEDT.BCSTTIM = PARADB.PGMSCH.SCHTIM\n" + 
//        "                                AND LOGEDT.BCSTTIM BETWEEN\n" + 
//        "                                (CASE WHEN LOGEDT.BCSTTIM-PARADB.PGMSCH.SCHTIM = 0 THEN  PARADB.PGMSCH.SCHTIM ELSE PARADB.PGMSCH.SCHTIM END)\n" + 
//        "                                AND (CASE WHEN LOGEDT.BCSTTIM-PARADB.PGMSCH.SCHTIM = 0 THEN  PARADB.PGMSCH.SCHTIM ELSE LOGEDT.BCSTTIM END)\n" + 
/*CH*///"                                AND PARADB.PGMSCH.PGMID = LOGEDT.PGMID AND LOGEDT.BCSTTIM = PARADB.PGMSCH.SCHTIM \n" +             
//            "                                AND PARADB.PGMSCH.PGMID = LOGEDT.PGMID AND PARADB.PGMSCH.TITLEID = LOGLINE.TITLEID AND PARADB.PGMSCH.PGMDSCID = LOGLINE.PGMDSCID \n" + 
            "                               LEFT OUTER JOIN PARADB.PGMTTLDSC ON PARADB.PGMTTLDSC.PGMDSCID = LOGLINE.PGMDSCID AND PARADB.PGMTTLDSC.PGMDSCID = PARADB.PGMSCH.PGMDSCID \n" + 
            "                                LEFT OUTER JOIN PARADB.BUYUNTHDR ON PARADB.BUYUNTHDR.BUYUNTID = PARADB.PGMSCH.PGMID AND PARADB.BUYUNTHDR.BUYUNTID = LOGEDT.PGMID, \n" + 
            "                                (SELECT X.STNID, X.BCSTDT, X.PGMID,X.CHGTIM \n" + 
            "                                        FROM EVENTAS.EVETV_PARRILLAS_PROCESADAS X \n" + 
            "                                        WHERE \n" + 
            "                                        X.STATUS = 1 \n" + 
            "                                        AND X.STNID =  " + tsChannels + "  \n" + 
            "                                        GROUP BY X.STNID, X.BCSTDT, X.PGMID, X.CHGTIM) Z \n" + 
            "                        WHERE LOGEDT.ALTLOG = 0 \n" + 
            "                        AND LOGEDT.STNID = Z.STNID \n" + 
            "                        AND LOGEDT.BCSTDT = CAST(Z.BCSTDT AS DATE) \n" + 
            "                        AND LOGEDT.PGMID = Z.PGMID \n" + 
            "                        AND PARADB.PGMSCH.STNID = Z.STNID \n" + 
            "                        AND PARADB.PGMSCH.SCHDT = CAST(Z.BCSTDT AS DATE) \n" + 
            "                        AND PARADB.PGMSCH.PGMID = Z.PGMID \n" + 
            "                        AND LOGEDT.MSTLOGEDTID = Z.CHGTIM \n" + 
            "                        AND LOGEDT.FMTTYP in (1) \n" + 
            "                        AND LOGEDT.FMTTYP <> 0 \n" + 
            "                        AND LOGEDT.PGMID NOT IN ('INNOVA','VEREDIMD','ESPRTC','SOYSANO','FARMACIA','PARESUFR', \n" + 
            "                        'VEREDIMA', \n" + 
            "                        'MARCASRE', \n" + 
            "                        'CJGRANDS', \n" + 
            "                        'MEJAQUID', \n" + 
            "                        'MEJAQUIE', \n" + 
            "                        'SERIEMD', \n" + 
            "                        'SERIEME', \n" + 
            "                        'CUIDAIN', \n" + 
            "                        'SERIEMA', \n" + 
            "                        'MISADOM', \n" + 
            "                        'MEJAQU24', \n" + 
            "                        'MEJAQUIA', \n" + 
            "                        'MEJAQUIF', \n" + 
            "                        'MEJORTV' \n" + 
            "                        ) \n" + 
            "                         AND LOGEDT.STNID IN ( " + tsChannels + " ) \n" + 
            "                        UNION ALL \n" + 
            "                        SELECT DISTINCT\n" + 
            "                            CASE WHEN LOGCMT.LOGCMTID = (SELECT J.LOGCMTID FROM EVENTAS.EVETV_CORTINILLAS J WHERE LOGCMT.LOGCMTID = J.LOGCMTID AND J.STNID =  " + tsChannels + " ) \n" + 
            "                            THEN (SELECT J.BCSTTIM FROM EVENTAS.EVETV_CORTINILLAS J WHERE LOGCMT.LOGCMTID = J.LOGCMTID AND J.STNID = " + tsChannels + " ) ELSE LOGEDT.BCSTTIM END AS BCSTTIM, \n" + 
            "                            LOGEDT.SEQNUM, \n" + 
            "                            LOGEDT.FMTTYP, \n" + 
            "                            -1 AS UNTNUM, \n" + 
            "                            LOGEDT.STNID AS CHANNELID, \n" + 
            "                            LOGEDT.BCSTDT AS DATEVALUE, \n" + 
            "                            '' AS HOURSTART, \n" + 
            "                            '' AS HOUREND, \n" + 
            "                            '' AS HOUR, \n" + 
            "                            '' AS TITLE, \n" + 
            "                            '' AS EPISODENAME, \n" + 
            "                            '' AS EPNO, \n" + 
            "                            '' AS SLOTDURATION, \n" + 
            "                           '0'  AS TITLEID, \n" + 
            "                            '' AS EPISODENAMEID, \n" + 
            "                            '' AS BUYUNITID, \n" + 
            "                            '' AS BUYUNIT, \n" + 
            "                            '' AS EXCEPTCOFEPRIS, \n" + 
            "                            '' AS EVENTSPECIAL, \n" + 
            "                            '' AS EVENTBEST, \n" + 
/*CH*/        "                          '' AS GENEROID,\n" + 
/*CH*/        "                          '' AS GENERONAME,\n" + 
            "                            CASE WHEN LOGCMT.LOGCMTID = (SELECT J.LOGCMTID FROM EVENTAS.EVETV_CORTINILLAS J WHERE LOGCMT.LOGCMTID = J.LOGCMTID AND J.STNID =  " + tsChannels + " ) \n" + 
            "                            THEN (SELECT J.PGMID FROM EVENTAS.EVETV_CORTINILLAS J WHERE LOGCMT.LOGCMTID = J.LOGCMTID AND J.STNID = " + tsChannels + " ) ELSE LOGEDT.PGMID END AS BUYUNTID_CORTE, \n" + 
            "                            LOGEDT.BCSTDT AS DATE_CORTE, \n" + 
            "                            CASE WHEN LOGCMT.LOGCMTID = (SELECT J.LOGCMTID FROM EVENTAS.EVETV_CORTINILLAS J WHERE LOGCMT.LOGCMTID = J.LOGCMTID AND J.STNID =  " + tsChannels + " ) \n" + 
            "                            THEN \n" + 
            "                            (SELECT CASE LENGTH(RTRIM(CHAR(J.BCSTTIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(J.BCSTTIM),1,1)||':'|| \n" + 
            "                            SUBSTR(CHAR(J.BCSTTIM),2,2)||':'||SUBSTR(CHAR(J.BCSTTIM),4,2) WHEN 8 THEN \n" + 
            "                            CASE SUBSTR(CHAR(J.BCSTTIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25' WHEN '26' THEN '26' \n" + 
            "                            WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(J.BCSTTIM),1,2) \n" + 
            "                            END ||':'||SUBSTR(CHAR(J.BCSTTIM),3,2)||':'||SUBSTR(CHAR(J.BCSTTIM),5,2) ELSE ' ' END \n" + 
            "                            FROM EVENTAS.EVETV_CORTINILLAS J \n" + 
            "                            WHERE LOGCMT.LOGCMTID = J.LOGCMTID \n" + 
            "                            AND J.STNID =  " + tsChannels + " ) \n" + 
            "                            ELSE \n" + 
            "                            CASE LENGTH(RTRIM(CHAR(LOGEDT.BCSTTIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(LOGEDT.BCSTTIM),1,1)||':'|| \n" + 
            "                            SUBSTR(CHAR(LOGEDT.BCSTTIM),2,2)||':'||SUBSTR(CHAR(LOGEDT.BCSTTIM),4,2) WHEN 8 THEN \n" + 
            "                            CASE SUBSTR(CHAR(LOGEDT.BCSTTIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25' WHEN '26' THEN '26' \n" + 
            "                            WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(LOGEDT.BCSTTIM),1,2) \n" + 
            "                            END ||':'||SUBSTR(CHAR(LOGEDT.BCSTTIM),3,2)||':'||SUBSTR(CHAR(LOGEDT.BCSTTIM),5,2) ELSE ' ' END \n" + 
            "                            END AS HOUR_CORTE, \n" + 
            "                            LOGCMT.LOGCMTID AS CORTEID, \n" + 
            "                            LOGCMT.CMT AS DESCORTE, \n" + 
            "                            'N' AS OVERLAY, \n" + 
            "                            -1 AS BREAKID, \n" + 
            "                            '' AS DESCRIPTION, \n" + 
            "                            LOGEDT.BCSTDT AS DATE_CORTE, \n" + 
            "                            '' AS HOUR_CORTE, \n" + 
            "                            '' AS TOTALDURATION, \n" + 
            "                            '' AS COMERCIALDURATION, \n" + //agregue la coma
/*CH*/            "                      '' AS TYPEBREAK \n" + 
            "                        FROM    PARADB.LOGEDT LOGEDT \n" + 
            "                                LEFT OUTER JOIN PARADB.LOGLINE LOGLINE \n" + 
            "                                    ON LOGEDT.LOGEDTID = LOGLINE.LOGEDTID \n" + 
            "                                LEFT OUTER JOIN PARADB.BRKDT BRKDT \n" + 
            "                                    ON  LOGEDT.LOGEDTID = BRKDT.LOGEDTID \n" + 
            "                                LEFT OUTER JOIN PARADB.LOGCMT LOGCMT \n" + 
            "                                    ON  LOGEDT.LOGEDTID = LOGCMT.LOGEDTID, \n" + 
            "                                (SELECT X.STNID, X.BCSTDT, X.PGMID, Y.SCHTIM, Y.ETIM \n" + 
            "                                        FROM EVENTAS.EVETV_PARRILLAS_PROCESADAS X, PARADB.PGMSCH Y \n" + 
            "                                        WHERE X.STNID = Y.STNID \n" + 
            "                                        AND X.BCSTDT = Y.SCHDT \n" + 
            "                                        AND X.PGMID = Y.PGMID \n" + 
            "                                        AND X.STATUS = 1 \n" + 
            "                                        GROUP BY X.STNID, X.BCSTDT, X.PGMID, Y.SCHTIM, Y.ETIM) Z \n" + 
            "                        WHERE LOGEDT.ALTLOG = 0 \n" + 
            "                        AND LOGEDT.STNID = Z.STNID \n" + 
            "                        AND LOGEDT.BCSTDT = Z.BCSTDT \n" + 
            "                        AND LOGEDT.PGMID = Z.PGMID \n" + 
            "                        AND LOGEDT.BCSTTIM BETWEEN Z.SCHTIM AND (CASE WHEN Z.ETIM = 2000000 THEN 26000000 ELSE Z.ETIM END)\n" + 
            "                        AND LOGEDT.FMTTYP in (5) \n" + 
            "                  AND (LOGCMT.CMT LIKE '*%CORTE%' OR LOGCMT.CMT LIKE '%BREA%' OR LOGCMT.CMT LIKE '%CORTE%') \n" + 
            "                        AND LOGEDT.PGMID NOT IN ('INNOVA','VEREDIMD','ESPRTC','SOYSANO','FARMACIA','PARESUFR', \n" + 
            "                        'VEREDIMA', \n" + 
            "                        'MARCASRE', \n" + 
            "                        'CJGRANDS', \n" + 
            "                        'MEJAQUID', \n" + 
            "                        'MEJAQUIE', \n" + 
            "                        'SERIEMD', \n" + 
            "                        'SERIEME', \n" + 
            "                        'CUIDAIN', \n" + 
            "                        'SERIEMA', \n" + 
            "                        'MISADOM', \n" + 
            "                        'MEJAQU24', \n" + 
            "                        'MEJAQUIA', \n" + 
            "                        'MEJAQUIF', \n" + 
            "                        'MEJORTV' \n" + 
            "                        ) \n" + 
            "                     AND LOGEDT.STNID IN ( " + tsChannels + " ) \n" + 
            "                        UNION ALL \n" + 
            "                        SELECT \n" + 
            "                            CASE WHEN BRKDT.BRKDTID = (SELECT J.BRKDTID FROM EVENTAS.EVETV_CORTINILLAS J WHERE BRKDT.BRKDTID = J.BRKDTID AND J.STNID =  " + tsChannels + " ) \n" + 
            "                            THEN (SELECT J.BCSTTIM FROM EVENTAS.EVETV_CORTINILLAS J WHERE BRKDT.BRKDTID = J.BRKDTID AND J.STNID = " + tsChannels + " ) ELSE LOGEDT.BCSTTIM END AS BCSTTIM, \n" + 
            "                            LOGEDT.SEQNUM, \n" + 
            "                            LOGEDT.FMTTYP, \n" + 
            "                            -1 AS UNTNUM, \n" + 
            "                            LOGEDT.STNID AS CHANNELID, \n" + 
            "                            LOGEDT.BCSTDT AS DATEVALUE, \n" + 
            "                            '' AS HOURSTART, \n" + 
            "                            '' AS HOUREND, \n" + 
            "                            '' AS HOUR, \n" + 
            "                            '' AS TITLE, \n" + 
            "                            '' AS EPISODENAME, \n" + 
            "                            '' AS EPNO, \n" + 
            "                            '' AS SLOTDURATION, \n" + 
            "                           '0'  AS TITLEID, \n" + 
            "                            '' AS EPISODENAMEID, \n" + 
            "                            '' AS BUYUNITID, \n" + 
            "                            '' AS BUYUNIT, \n" + 
            "                            '' AS EXCEPTCOFEPRIS, \n" + 
            "                            '' AS EVENTSPECIAL, \n" + 
            "                            '' AS EVENTBEST, \n" +             
/*CH*/        "                            '' AS GENEROID,\n" + 
/*CH*/        "                            '' AS GENERONAME,\n" + 
            "                            '' AS BUYUNTID_CORTE, \n" + 
            "                            LOGEDT.BCSTDT AS DATE_CORTE, \n" + 
            "                            '' AS HOUR_CORTE, \n" + 
            "                            -1 AS CORTEID, \n" + 
            "                            '' AS DESCORTE, \n" + 
            "                            '' AS OVERLAY, \n" + 
            "                            BRKDT.BRKDTID AS BREAKID, \n" + 
            "                            BRKDT.BRKDSCR AS DESCRIPTION, \n" + 
            "                            LOGEDT.BCSTDT AS DATE_BREAK, \n" + 
            "                            CASE WHEN BRKDT.BRKDTID = (SELECT J.BRKDTID FROM EVENTAS.EVETV_CORTINILLAS J WHERE BRKDT.BRKDTID = J.BRKDTID AND J.STNID =  " + tsChannels + " ) \n" + 
            "                            THEN \n" + 
            "                            (SELECT CASE LENGTH(RTRIM(CHAR(J.BCSTTIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(J.BCSTTIM),1,1)||':'|| \n" + 
            "                            SUBSTR(CHAR(J.BCSTTIM),2,2)||':'||SUBSTR(CHAR(J.BCSTTIM),4,2) WHEN 8 THEN \n" + 
            "                            CASE SUBSTR(CHAR(J.BCSTTIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25' WHEN '26' THEN '26' \n" + 
            "                            WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(J.BCSTTIM),1,2) \n" + 
            "                            END ||':'||SUBSTR(CHAR(J.BCSTTIM),3,2)||':'||SUBSTR(CHAR(J.BCSTTIM),5,2) ELSE ' ' END \n" + 
            "                            FROM EVENTAS.EVETV_CORTINILLAS J \n" + 
            "                            WHERE BRKDT.BRKDTID = J.BRKDTID \n" + 
            "                            AND J.STNID =  " + tsChannels + " ) \n" + 
            "                            ELSE \n" + 
            "                            CASE LENGTH(RTRIM(CHAR(LOGEDT.BCSTTIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(LOGEDT.BCSTTIM),1,1)||':'|| \n" + 
            "                            SUBSTR(CHAR(LOGEDT.BCSTTIM),2,2)||':'||SUBSTR(CHAR(LOGEDT.BCSTTIM),4,2) WHEN 8 THEN \n" + 
            "                            CASE SUBSTR(CHAR(LOGEDT.BCSTTIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25' WHEN '26' THEN '26' \n" + 
            "                            WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(LOGEDT.BCSTTIM),1,2) \n" + 
            "                            END ||':'||SUBSTR(CHAR(LOGEDT.BCSTTIM),3,2)||':'||SUBSTR(CHAR(LOGEDT.BCSTTIM),5,2) ELSE ' ' END \n" + 
            "                            END AS HOUR_BREAK, \n" + 
            "                                CASE LENGTH(RTRIM(CHAR(LOGEDT.NOMLEN))) \n" + 
            "                            WHEN 5 THEN '00:0'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,1)||':'|| \n" + 
            "                            SUBSTR(CHAR(LOGEDT.NOMLEN),2,2) \n" + 
            "                            WHEN 6 THEN '00:'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,2)||':'|| \n" + 
            "                            SUBSTR(CHAR(LOGEDT.NOMLEN),3,2) \n" + 
            "                            WHEN 1 THEN '00:00:01' \n" + 
            "                            WHEN 4 THEN '00:00:'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,2) \n" + 
            "                        WHEN 3 THEN '00:00:0'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,1) \n" + 
            "                            WHEN 7 THEN '0'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,1)||':'|| \n" + 
            "                            SUBSTR(CHAR(LOGEDT.NOMLEN),2,2)||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),4,2) \n" + 
            "                            WHEN 8 THEN CASE SUBSTR(CHAR(LOGEDT.NOMLEN),1,2) WHEN '24' THEN '00' WHEN '25' THEN '01' WHEN '26' THEN '26' \n" + 
            "                            WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(LOGEDT.NOMLEN),1,2) \n" + 
            "                            END ||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),3,2)||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),5,2) ELSE ' ' END AS TOTALDURATION, \n" + 
            "                                CASE LENGTH(RTRIM(CHAR(LOGEDT.NOMLEN))) \n" + 
            "                            WHEN 5 THEN '00:0'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,1)||':'|| \n" + 
            "                            SUBSTR(CHAR(LOGEDT.NOMLEN),2,2) \n" + 
            "                            WHEN 6 THEN '00:'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,2)||':'|| \n" + 
            "                            SUBSTR(CHAR(LOGEDT.NOMLEN),3,2) \n" + 
            "                            WHEN 1 THEN '00:00:01' \n" + 
            "                            WHEN 4 THEN '00:00:'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,2) \n" + 
            "                        WHEN 3 THEN '00:00:0'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,1) \n" + 
            "                            WHEN 7 THEN '0'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,1)||':'|| \n" + 
            "                            SUBSTR(CHAR(LOGEDT.NOMLEN),2,2)||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),4,2) \n" + 
            "                            WHEN 8 THEN CASE SUBSTR(CHAR(LOGEDT.NOMLEN),1,2) WHEN '24' THEN '00' WHEN '25' THEN '01' WHEN '26' THEN '26' \n" + 
            "                            WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(LOGEDT.NOMLEN),1,2) \n" + 
            "                            END ||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),3,2)||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),5,2) ELSE ' ' END AS COMERCIALDURATION, \n" + //Agregue la coma
/*CH*/            "                        BRKDT.BRKCHRRULE AS TYPEBREAK\n" + 
            "                        FROM    PARADB.LOGEDT LOGEDT \n" + 
            "                                LEFT OUTER JOIN PARADB.LOGLINE LOGLINE \n" + 
            "                                    ON LOGEDT.LOGEDTID = LOGLINE.LOGEDTID \n" + 
            "                                LEFT OUTER JOIN PARADB.BRKDT BRKDT \n" + 
            "                                    ON  LOGEDT.LOGEDTID = BRKDT.LOGEDTID \n" + 
            "                                LEFT OUTER JOIN PARADB.LOGCMT LOGCMT \n" + 
            "                                    ON  LOGEDT.LOGEDTID = LOGCMT.LOGEDTID, \n" + 
            "                                (SELECT X.STNID, X.BCSTDT, X.PGMID, X.BRKDTID,X.SCHTIM \n" + 
            "                                        FROM EVENTAS.EVETV_PARRILLAS_PROCESADAS X \n" + 
            "                                        WHERE X.STATUS = 1 \n" + 
            "                                        GROUP BY X.STNID, X.BCSTDT, X.PGMID, X.BRKDTID,X.SCHTIM) Z \n" + 
            "                        WHERE LOGEDT.ALTLOG = 0 \n" + 
            "                        AND LOGEDT.STNID = Z.STNID \n" + 
            "                        AND LOGEDT.BCSTDT = Z.BCSTDT \n" + 
            "                        AND LOGEDT.PGMID = Z.PGMID \n" + 
            "                        AND LOGEDT.BCSTTIM  = Z.SCHTIM \n" + 
            "                        AND BRKDT.BRKDTID = Z.BRKDTID \n" + 
            "                        AND LOGEDT.FMTTYP in (2) \n" + 
/*CH*/            "                        AND BRKDT.BRKCHRRULE IN ('CO','', 'BI', 'BF') \n" + 
            "                        AND BRKDT.INVFL = 1 \n" + 
            "                        AND LOGEDT.PGMID NOT IN ('INNOVA','VEREDIMD','ESPRTC','SOYSANO','FARMACIA','PARESUFR', \n" + 
            "                        'VEREDIMA', \n" + 
            "                        'MARCASRE', \n" + 
            "                        'CJGRANDS', \n" + 
            "                        'MEJAQUID', \n" + 
            "                        'MEJAQUIE', \n" + 
            "                        'SERIEMD', \n" + 
            "                        'SERIEME', \n" + 
            "                        'CUIDAIN', \n" + 
            "                        'SERIEMA', \n" + 
            "                       'MISADOM', \n" + 
            "                        'MEJAQU24', \n" + 
            "                        'MEJAQUIA', \n" + 
            "                        'MEJAQUIF', \n" + 
            "                        'MEJORTV' \n" + 
            "                        ) \n" + 
            "                     AND LOGEDT.STNID IN ( " + tsChannels + " ) \n" + 
            "                        ORDER BY CHANNELID, DATEVALUE,BCSTTIM, SEQNUM, FMTTYP, UNTNUM";
        //System.out.println("******* Parrillas OnDemand OD ****************");
        //System.out.println(lsQuery);
        //System.out.println("*******************************************");
        return lsQuery;
    }

    /**
     * Obtiene lista de Parrillas de Programas y Cortes
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return List
     */
    @Override
    public List<RsstParrillasCortesBean> getParrillasOnDemandParadigmDb(String tsChannels,
                                                                        String tsDateStrt,
                                                                        String tsDateEnd,
                                                                        String tsBuyunit
                                                                      ) throws SQLException {
        List<RsstParrillasCortesBean> laParrillasCortes = 
            new ArrayList<RsstParrillasCortesBean>();
        Connection                    loCnn = 
            new ConnectionAs400().getConnection();
        SimpleDateFormat              loSDF = new SimpleDateFormat("dd/MM/yyyy");
        ResultSet                     loRs = null;
        String                        lsQueryParadigm = 
            getQueryOnDemandParadigm(tsChannels,
                                     tsDateStrt,
                                     tsDateEnd,
                                     tsBuyunit
                                     );
        
        System.out.println("*********** Query Parrillas OnDemand al ("+new Date()+")*************** ");
        System.out.println(lsQueryParadigm);
        System.out.println("*****************************************************");
        
        int liCounter = 0;
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);
            while(loRs.next()){
                liCounter ++;
                RsstParrillasCortesBean loParrillaCorteBean = new RsstParrillasCortesBean(); 
                loParrillaCorteBean.setLsBcsttim(loRs.getString("BCSTTIM"));                
                loParrillaCorteBean.setLsSeqnum(loRs.getString("SEQNUM"));
                loParrillaCorteBean.setLsFmttyp(loRs.getString("FMTTYP"));
                loParrillaCorteBean.setLsUntnum(loRs.getString("UNTNUM"));
                loParrillaCorteBean.setLsChannelid(loRs.getString("CHANNELID"));
                Date ldDt = loRs.getDate("DATEVALUE");
                //parrillaCorteBean.setLsDatevalue(loRs.getString("DATEVALUE"));
                loParrillaCorteBean.setLsDatevalue(loSDF.format(ldDt));
                loParrillaCorteBean.setLsHourstart(loRs.getString("HOURSTART"));
                loParrillaCorteBean.setLsHourend(loRs.getString("HOUREND"));
                loParrillaCorteBean.setLsHour(loRs.getString("HOUR"));
                //String lsTitle = loRs.getString("TITLE");
                loParrillaCorteBean.setLsTitle(loRs.getString("TITLE"));
                loParrillaCorteBean.setLsEpisodename(loRs.getString("EPISODENAME"));
                loParrillaCorteBean.setLsEpno(loRs.getString("EPNO"));
                loParrillaCorteBean.setLsSlotduration(loRs.getString("SLOTDURATION"));
                loParrillaCorteBean.setLsTitleid(loRs.getString("TITLEID"));
                loParrillaCorteBean.setLsEpisodenameid(loRs.getString("EPISODENAMEID"));                
                loParrillaCorteBean.setLsBuyunitid(loRs.getString("BUYUNITID"));
                loParrillaCorteBean.setLsBuyunit(loRs.getString("BUYUNIT"));
                loParrillaCorteBean.setLsExceptcofepris(loRs.getString("EXCEPTCOFEPRIS"));
                loParrillaCorteBean.setLsEventspecial(loRs.getString("EVENTSPECIAL"));
                loParrillaCorteBean.setLsEventbest(loRs.getString("EVENTBEST"));
                
                loParrillaCorteBean.setLsGeneroID(loRs.getString("GENEROID"));
                loParrillaCorteBean.setLsGeneroName(loRs.getString("GENERONAME"));
                
                loParrillaCorteBean.setLsBuyuntidCorte(loRs.getString("BUYUNTID_CORTE"));
                Date ldDtCorte = loRs.getDate("DATE_CORTE");
                //parrillaCorteBean.setLsDateCorte(loRs.getString("DATE_CORTE"));
                loParrillaCorteBean.setLsDateCorte(loSDF.format(ldDtCorte));
                loParrillaCorteBean.setLsHourCorte(loRs.getString("HOUR_CORTE"));
                loParrillaCorteBean.setLsCorteid(loRs.getString("CORTEID"));
                loParrillaCorteBean.setLsDescorte(loRs.getString("DESCORTE"));
                loParrillaCorteBean.setLsOverlay(loRs.getString("OVERLAY"));
                loParrillaCorteBean.setLsBreakid(loRs.getString("BREAKID"));
                loParrillaCorteBean.setLsDescription(loRs.getString("DESCRIPTION"));
                Date ldDtBreak = loRs.getDate("DATE_BREAK");
                //parrillaCorteBean.setLsDateBreak(loRs.getString("DATE_BREAK"));
                loParrillaCorteBean.setLsDateBreak(loSDF.format(ldDtBreak));
                loParrillaCorteBean.setLsHourBreak(loRs.getString("HOUR_BREAK"));
                loParrillaCorteBean.setLsTotalduration(loRs.getString("TOTALDURATION"));
                loParrillaCorteBean.setLsComercialduration(loRs.getString("COMERCIALDURATION"));
                
                loParrillaCorteBean.setLsTypeBreak(loRs.getString("TYPEBREAK"));
                
                //System.out.println("Agregando ["+liCounter+"] Registros");
                laParrillasCortes.add(loParrillaCorteBean);
                
            }
            System.out.println("Query ejecutado ok..........["+liCounter+"]");
            
        } catch (SQLException loExSql) {
            System.out.println("Error al extraer: "+loExSql.getMessage());
            throw loExSql;
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return laParrillasCortes;
    }

    /**
     * Obtiene bandera de parrillas procesadas
     * @autor Jorge Luis Bautista Santiago
     * @return Integer
     */
    @Override
    public Integer getFlagParrillasProcesadas() {
        Integer    liReturn = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = getQueryFlagParadigm();
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
     * Obtiene consulta de bandera de parrillas procesadas
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    @Override
    public String getQueryFlagParadigm() {
        String lsQuery =
            "SELECT COUNT(1) FROM EVENTAS.EVETV_PARRILLAS_PROCESADAS";
        return lsQuery;
    }

    /**
     * Obtiene cadena de consulta de Parrillas de programas y Cortes
     * @autor Jorge Luis Bautista Santiago
     * @return void
     */
    @Override
    public ResponseUpdDao insertParrillasProcesadas(String tsChannels) {
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection loCnn = new ConnectionAs400().getConnection();
        String lsQueryParadigm = getQueryInsertParadigm(tsChannels);
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
     * Obtiene cadena de consulta de Parrillas de programas y Cortes
     * @autor Jorge Luis Bautista Santiago
     * @return void
     */
    public ResponseUpdDao insertParrillasProcesadasBreaks(String tsChannels) {
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection loCnn = new ConnectionAs400().getConnection();
        String lsQueryParadigm = getQueryInsertParadigmBreaks(tsChannels);
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
     * Obtiene consulta para insertar parrillas
     * @autor Jorge Luis Bautista Santiago
     * @param tsChannels
     * @return String
     */
    @Override
    public String getQueryInsertParadigm(String tsChannels) {
        /*
        String lsQuery = "insert INTO EVENTAS.EVETV_PARRILLAS_PROCESADAS(STNID,BCSTDT,PGMID,STATUS)\n" + 
        "select distinct c.stnid, c.bcstdt, c.pgmid,1\n" + 
        "from (\n" + 
        "select b.stnid, b.pgmid, b.schdt as bcstdt, max(chgdt), max(chgtim)\n" + 
        "from paradb.pgmtxrntel a, paradb.pgmsch b\n" + 
        "where a.stnid = b.stnid\n" + 
        "and a.pgmid = b.pgmid\n" + 
        "and a.schdt = b.schdt\n" + 
        "and actcd = 2\n" + 
        "and b.syncfl = 1\n" + 
        "and b.schdt >= CURRENT DATE\n" + 
        "group by b.stnid, b.pgmid, b.schdt\n" + 
        "union all\n" + 
        "select b.stnid, b.pgmid, b.bcstdt,max(a.chgdt), max(a.chgtim)\n" + 
        "FROM paradb.A_BRKDTTEL A, paradb.LOGEDT B, paradb.BRKDT C\n" + 
        "WHERE A.LOGEDTID = B.LOGEDTID\n" + 
        "AND A.STNID = B.STNID\n" + 
        "AND A.BRKDTID = C.BRKDTID\n" + 
        "AND A.CHGCD IN (0,1)\n" + 
        "AND C.BRKCHRRULE IN ('CO','')\n" + 
        "AND B.BCSTDT >= CURRENT DATE\n" + 
        "GROUP BY b.stnid, b.pgmid, b.bcstdt) c";
        */
        
        String lsQuery = 
            "INSERT INTO EVENTAS.EVETV_PARRILLAS_PROCESADAS(STNID,BCSTDT,PGMID,STATUS,BRKDTID,SCHTIM,CHGTIM)\n" + 
            "select b.stnid, b.schdt as bcstdt,b.pgmid,1,d.brkdtid,a.schtim,max(chgtim)\n" + 
            "from paradb.pgmtxrntel a, paradb.pgmsch b, paradb.logedt c, paradb.brkdt d\n" + 
            "where a.stnid = b.stnid\n" + 
            "and a.pgmid = b.pgmid\n" + 
            "and a.schdt = b.schdt\n" + 
            "and a.schtim = b.schtim\n" + 
            //"and c.bcsttim >= a.schtim\n" + 
/*CH*/      " and c.bcsttim between a.schtim and a.etim \n" + 
            "and a.etim = b.etim\n" + 
            "and c.logedtid = d.logedtid\n" + 
            "and a.pgmid = c.pgmid\n" + 
            "and b.pgmid = c.pgmid\n" + 
            "and a.schdt = c.bcstdt\n" + 
            "and b.schdt = c.bcstdt\n" + 
            "and a.stnid = c.stnid\n" + 
            "and b.stnid = c.stnid\n" + 
/*CH*/        " and a.stnid IN (" + tsChannels + ")\n" + 
/*CH*/        " and b.stnid IN (" + tsChannels + ")\n" +             
            "and c.fmttyp in (2)\n" + 
            "AND d.BRKCHRRULE IN ('CO','')\n" + 
            "AND d.INVFL = 1\n" + 
            "and actcd = 2\n" + 
            "and b.syncfl = 1\n" + 
            "and b.schdt >= CURRENT DATE\n" + 
            "group by b.stnid, b.pgmid, b.schdt, d.brkdtid, a.schtim, c.bcsttim\n" + 
            "order by b.stnid, b.schdt, b.pgmid,d.brkdtid, a.schtim";
        return lsQuery;
    }
    
    /**
     * Obtiene consulta para insertar parrillas
     * @autor Jorge Luis Bautista Santiago
     * @param tsChannels
     * @return String
     */
    public String getQueryInsertParadigmBreaks(String tsChannels) {
        String lsQuery = 
            "INSERT INTO EVENTAS.EVETV_PARRILLAS_PROCESADAS(STNID,BCSTDT,PGMID,STATUS,BRKDTID,SCHTIM,CHGTIM)\n" + 
            "select b.stnid, b.bcstdt, b.pgmid, 1, c.brkdtid,  (select j.schtim\n" + 
            "                                                  from paradb.pgmsch j\n" + 
            "                                                  where j.pgmid = b.pgmid\n" + 
            "                                                  and j.schdt = b.bcstdt\n" + 
            "                                                  and j.stnid = b.stnid\n" + 
/*ch*/        "                                                  and j.stnid IN (" + tsChannels + ")\n" + 
/*ch*/        "                                                  and b.stnid IN (" + tsChannels + ")\n" +             
            "                                                  and b.bcsttim between j.schtim and j.etim\n" + 
            "                                                  group by j.schtim),\n" + 
            "max(a.chgtim)\n" + 
            "FROM PARADB.A_BRKDTTEL A, PARADB.LOGEDT B, PARADB.BRKDT C\n" + 
            "WHERE A.LOGEDTID = B.LOGEDTID\n" + 
            "AND A.STNID = B.STNID\n" + 
/*CH*/        " AND A.STNID IN (" + tsChannels + ")\n" + 
/*CH*/        " AND B.STNID IN (" + tsChannels + ")\n" +             
            "AND A.BRKDTID = C.BRKDTID\n" + 
            "AND C.BRKDTID NOT IN (SELECT D.BRKDTID FROM EVENTAS.EVETV_PARRILLAS_PROCESADAS D WHERE C.BRKDTID = D.BRKDTID AND D.STATUS = 1)\n" + 
            "AND A.CHGCD IN (0,1,2)\n" + 
            "AND C.BRKCHRRULE IN ('CO','')\n" + 
            "AND B.BCSTDT >= CURRENT DATE\n" + 
            "GROUP BY b.stnid, b.pgmid, b.bcstdt, c.brkdtid, (select j.schtim\n" + 
            "                                                  from paradb.pgmsch j\n" + 
            "                                                  where j.pgmid = b.pgmid\n" + 
            "                                                  and j.schdt = b.bcstdt\n" + 
            "                                                  and j.stnid = b.stnid\n" + 
/*CH*/        "                                                  and j.stnid IN (" + tsChannels + ")\n" +             
/*CH*/        "                                                  and b.stnid IN (" + tsChannels + ")\n" +                         
            "                                                  and b.bcsttim between j.schtim and j.etim\n" + 
            "                                                  group by j.schtim)";
        
        return lsQuery;
    }

    /**
     * Actualiza parrillas procesadas
     * @autor Jorge Luis Bautista Santiago
     * @param tsChannels
     * @return ResponseUpdDao
     */
    @Override
    public ResponseUpdDao updateParrillasProcesadas(String tsChannels) {
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection loCnn = new ConnectionAs400().getConnection();
        String lsQueryParadigm = getQueryUpdateParadigm(tsChannels);
        try {
            Statement loStmt = loCnn.createStatement();
            int liRs = loStmt.executeUpdate(lsQueryParadigm);            
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLiAffected(liRs);
        } catch (SQLException loExSql) {
            loResponseUpdDao.setLsResponse("ERROR");
            loResponseUpdDao.setLiAffected(0);
            loResponseUpdDao.setLsMessage(loExSql.getMessage());
            loExSql.printStackTrace();
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
     * Obtiene cadena de consulta actualizacion de parrillas
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    @Override
    public String getQueryUpdateParadigm(String tsChannels) {
        String lsQuery = "UPDATE EVENTAS.EVETV_PARRILLAS_PROCESADAS\n" + 
        "SET STATUS = 1\n" + 
        "WHERE STATUS = 2\n" + 
        "AND BCSTDT >= CURRENT DATE\n" +
        "AND STNID IN (" + tsChannels + ")\n";
        return lsQuery;
    }

    /**
     * Actualiza tabla de control
     * @autor Jorge Luis Bautista Santiago
     * @return void
     */
    
    @Override
    public ResponseUpdDao updateFlagsPgmtxrntei() {
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection loCnn = new ConnectionAs400().getConnection();
        String lsQueryParadigm = getQueryUpdateFlagsPgmtxrntei();
        try {
            Statement loStmt = loCnn.createStatement();
            int liRs = loStmt.executeUpdate(lsQueryParadigm);            
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLiAffected(liRs);
        } catch (SQLException loExSql) {
            loResponseUpdDao.setLsResponse("ERROR");
            loResponseUpdDao.setLiAffected(0);
            loResponseUpdDao.setLsMessage(loExSql.getMessage());
            loExSql.printStackTrace();
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
     * Obtiene cadena de consulta para actualizacion de tabla control
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return String
     */
    @Override
    public String getQueryUpdateFlagsPgmtxrntei() {
        String lsQuery = "UPDATE PARADB.PGMTXRNTEL A\n" + 
        "SET A.ACTCD = 0\n" + 
        "WHERE (A.STNID,A.SCHDT,A.PGMID) IN (SELECT B.STNID, B.BCSTDT,B.PGMID FROM EVENTAS.EVETV_PARRILLAS_PROCESADAS B\n" + 
        "                                     WHERE A.STNID = B.STNID\n" + 
        "                                     AND A.SCHDT = B.BCSTDT\n" + 
        "                                    AND A.PGMID = B.PGMID\n" + 
        "                                     AND B.STATUS = 1)\n";
        return lsQuery;
    }

    /**
     * Actualiza tabla de contgrol
     * @autor Jorge Luis Bautista Santiago
     * @return void
     */
    @Override
    public ResponseUpdDao updateStstusParrillasProcesadas(String tsStnid, 
                                                          String tsBcstdt, 
                                                          String tsPgmid, 
                                                          String tsBreakId,
                                                          String tsStatus
                                                          ) {
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = 
            getQueryUpdateStstusParadigm(tsStnid, 
                                         tsBcstdt, 
                                         tsPgmid, 
        tsBreakId,
                                         tsStatus
                                         );
        //System.out.println(lsQueryParadigm);
        try {
            Statement loStmt = loCnn.createStatement();
            Integer liRes = loStmt.executeUpdate(lsQueryParadigm);
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLiAffected(liRes);
        } catch (SQLException loExSql) {            
            loExSql.printStackTrace();
            loResponseUpdDao.setLsResponse("ERROR");
            loResponseUpdDao.setLiAffected(0);
            loResponseUpdDao.setLsMessage(loExSql.getMessage());
            //System.out.println(lsQueryParadigm);
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
     * Obtiene cadena de consulta de Actualizacion 
     * @autor Jorge Luis Bautista Santiago
     * @param tsStnid
     * @param tsBcstdt
     * @param tsPgmid
     * @param tsStatus
     * @return String
     */
    @Override
    public String getQueryUpdateStstusParadigm(String tsStnid, 
                                               String tsBcstdt, 
                                               String tsPgmid, 
                                               String tsBreakId,
                                               String tsStatus
                                               ) {
        String lsQuery = "UPDATE EVENTAS.EVETV_PARRILLAS_PROCESADAS\n" + 
        "   SET STATUS = " + tsStatus + "\n" + 
        " WHERE STNID = '" + tsStnid + "'\n";
        if(tsBcstdt != null){
            lsQuery += "   AND BCSTDT = '" + tsBcstdt + "'\n";
        }
        if(tsPgmid != null){
            lsQuery += "   AND PGMID = '" + tsPgmid + "'";
        }
        lsQuery +=
        "   AND BRKDTID = '" + tsBreakId + "'\n";
        
        return lsQuery;
    }

    /**
     * Actualiza tabla de Control
     * @autor Jorge Luis Bautista Santiago
     * @return void
     */
    @Override
    public ResponseUpdDao updateFlagsABrkdttel() {
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection loCnn = 
            new ConnectionAs400().getConnection();
        String     lsQueryParadigm = 
            getQueryUpdateFlagsABrkdttel();
        try {
            Statement loStmt = loCnn.createStatement();
            Integer liRes = loStmt.executeUpdate(lsQueryParadigm);   
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLiAffected(liRes);
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
     * Obtiene cadena de consulta de Actualizacion 
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    @Override
    public String getQueryUpdateFlagsABrkdttel() {
        String lsQuery = "UPDATE PARADB.A_BRKDTTEL A\n" + 
        "SET A.CHGCD = 3\n" + 
        "WHERE (A.STNID,(SELECT B.BCSTDT FROM PARADB.LOGEDT B WHERE A.LOGEDTID = B.LOGEDTID GROUP BY BCSTDT),\n" + 
        "(SELECT C.PGMID FROM PARADB.LOGEDT C WHERE A.LOGEDTID = C.LOGEDTID)) IN (SELECT B.STNID, B.BCSTDT,B.PGMID FROM EVENTAS.EVETV_PARRILLAS_PROCESADAS B\n" + 
        "                                     WHERE A.STNID = B.STNID\n" + 
        "                                     AND (SELECT B.BCSTDT FROM PARADB.LOGEDT B WHERE A.LOGEDTID = B.LOGEDTID GROUP BY BCSTDT) = B.BCSTDT\n" + 
        "                                     AND (SELECT C.PGMID FROM PARADB.LOGEDT C WHERE A.LOGEDTID = C.LOGEDTID) = B.PGMID\n" + 
        "                                     AND B.STATUS = 1)\n" + 
        "AND CHGCD = 0\n";
        return lsQuery;
    }
    
    /**
     * Obtiene bean de mapeo
     * @autor Jorge Luis Bautista Santiago
     * @param tsChannel
     * @param tsBreakId
     * @return Object
     */
    @Override
    public RsstParrillasCortesBean getBreakInfo(String tsChannel,
                                                String tsBreakId) {
        RsstParrillasCortesBean loRes = null;
        String lsQuery = "" +
            "           SELECT  \n" + 
            "                BRKDT.BRKDTID AS BREAKID,  \n" + 
            "                LOGEDT.PGMID AS DESCRIPTION,  \n" + 
            "                LOGEDT.BCSTDT AS DATE_BREAK\n" + 
            "            FROM    PARADB.LOGEDT LOGEDT  \n" + 
            "                    LEFT OUTER JOIN PARADB.LOGLINE LOGLINE  \n" + 
            "                        ON LOGEDT.LOGEDTID = LOGLINE.LOGEDTID  \n" + 
            "                    LEFT OUTER JOIN PARADB.BRKDT BRKDT  \n" + 
            "                        ON  LOGEDT.LOGEDTID = BRKDT.LOGEDTID  \n" + 
            "                    LEFT OUTER JOIN PARADB.LOGCMT LOGCMT  \n" + 
            "                        ON  LOGEDT.LOGEDTID = LOGCMT.LOGEDTID  \n" + 
            "            WHERE LOGEDT.ALTLOG = 0  \n" + 
            "            AND LOGEDT.STNID IN ('" + tsChannel + "')  \n" + 
            "            AND LOGEDT.BCSTDT >= CURRENT DATE  \n" + 
            "            AND LOGEDT.FMTTYP in (2)  \n" + 
            "            AND  BRKDT.BRKCHRRULE IN ('CO','')  \n" + 
            "            AND BRKDT.INVFL = 1  \n" + 
            "            AND BRKDT.BRKDTID = '" + tsBreakId + "'";
        Connection loCon = new ConnectionAs400().getConnection();
        ResultSet  loRS = null;
        try {
            Statement loSt = loCon.createStatement();
            loRS = loSt.executeQuery(lsQuery);
            while(loRS.next()) {
                loRes = new RsstParrillasCortesBean();
                loRes.setLsChannelid(tsChannel);
                loRes.setLsBreakid(tsBreakId);
                loRes.setLsDescription(loRS.getString(2));
                loRes.setLsDateBreak(loRS.getString(3));
            }
        } catch(SQLException loSQLEx) {
            System.out.println("Error al obtener el break info "+loSQLEx.getMessage());
            loSQLEx.printStackTrace();
        } finally {
            try {
                loCon.close();
                loRS.close();
            } catch(Exception loEx) { 
                loEx.printStackTrace(); 
            }
        }
        return loRes;
    }

    /**
     * Obtiene cadena de consulta
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return String
     */
    @Override
    public String getQueryParadigmFlagZero(String tsDate, 
                                           String tsChannels
                                           ) {
        String lsNotInPgmid = "'INNOVA','VEREDIMD','ESPRTC','SOYSANO','FARMACIA','PARESUFR',\n" + 
        "'VEREDIMA',\n" + 
        "'MARCASRE',\n" + 
        "'CJGRANDS',\n" + 
        "'MEJAQUID',\n" + 
        "'MEJAQUIE',\n" + 
        "'SERIEMD',\n" + 
        "'SERIEME',\n" + 
        "'CUIDAIN',\n" + 
        "'SERIEMA',\n" + 
        "'MISADOM',\n" + 
        "'MEJAQU24',\n" + 
        "'MEJAQUIA',\n" + 
        "'MEJAQUIF',\n" + 
        "'MEJORTV' \n";
        
        String lsQuery = "SELECT\n" + 
        "    LOGEDT.BCSTTIM,\n" + 
        "    LOGEDT.SEQNUM,\n" + 
        "   LOGEDT.FMTTYP,\n" + 
        "    -1 AS UNTNUM,\n" + 
        "    LOGEDT.STNID AS CHANNELID,\n" + 
        "    LOGEDT.BCSTDT AS DATEVALUE,\n" + 
        "    CASE LENGTH(RTRIM(CHAR(PARADB.PGMSCH.SCHTIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,1)||':'||\n" + 
        "    SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),2,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),4,2) WHEN 8 THEN\n" + 
//      "    CASE SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,2) WHEN '24' THEN '00' WHEN '25' THEN '01' WHEN '26' THEN '26'\n" + 
/*CH*/  "    CASE SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25' WHEN '26' THEN '26'\n" + 
        "    WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,2)\n" + 
        "    END ||':'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),3,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),5,2) ELSE ' ' END AS HOURSTART,\n" + 
        "    CASE LENGTH(RTRIM(CHAR(PARADB.PGMSCH.ETIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(PARADB.PGMSCH.ETIM),1,1)||':'||\n" + 
        "    SUBSTR(CHAR(PARADB.PGMSCH.ETIM),2,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.ETIM),4,2) WHEN 8 THEN\n" + 
//      "    CASE SUBSTR(CHAR(PARADB.PGMSCH.ETIM),1,2) WHEN '24' THEN '00' WHEN '25' THEN '01' WHEN '26' THEN '26'\n" + 
/*CH*/  "    CASE SUBSTR(CHAR(PARADB.PGMSCH.ETIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25' WHEN '26' THEN '26'\n" + 
        "    WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(PARADB.PGMSCH.ETIM),1,2)\n" + 
        "    END ||':'||SUBSTR(CHAR(PARADB.PGMSCH.ETIM),3,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.ETIM),5,2) ELSE ' ' END AS HOUREND,\n" + 
        "        CASE LENGTH(RTRIM(CHAR(PARADB.PGMSCH.SCHTIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,1)||':'||\n" + 
        "    SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),2,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),4,2) WHEN 8 THEN\n" + 
//      "    CASE SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,2) WHEN '24' THEN '00' WHEN '25' THEN '01' WHEN '26' THEN '26'\n" + 
/*CH*/  "    CASE SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25'  WHEN '26' THEN '26'\n" + 
        "    WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),1,2)\n" + 
        "    END ||':'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),3,2)||':'||SUBSTR(CHAR(PARADB.PGMSCH.SCHTIM),5,2) ELSE ' ' END AS HOUR,\n" + 
//      "    CASE WHEN PARADB.PGMSCH.PGMDSCID IS NULL THEN PARADB.BUYUNTHDR.BUYUNTID ELSE\n" + 
//      "    CASE WHEN PARADB.PGMTTLDSC.SERIESNAME = '' THEN PARADB.PGMTTLDSC.TITLESCHNAME ELSE PARADB.PGMTTLDSC.SERIESNAME END END AS TITLE,\n" + 
/*CH*/  "    CASE WHEN PARADB.PGMTTLDSC.SERIESNAME = '' THEN PARADB.PGMTTLDSC.TITLESCHNAME\n" + 
/*CH*/  "         WHEN PARADB.PGMTTLDSC.TITLESCHNAME IS NULL THEN PARADB.BUYUNTHDR.BUYUNTID\n" + 
/*CH*/  "         ELSE PARADB.PGMTTLDSC.SERIESNAME END AS TITLE,\n"+
//      "    CASE WHEN PARADB.PGMSCH.PGMDSCID IS NULL THEN PARADB.BUYUNTHDR.BUYUNTID ELSE\n" + 
//      "    PARADB.PGMTTLDSC.TITLEREALNAME END AS EPISODENAME," +
/*CH*/  "  CASE WHEN PARADB.PGMSCH.PGMDSCID IS NULL THEN PARADB.BUYUNTHDR.BUYUNTID\n" + 
/*CH*/  "         WHEN PARADB.PGMTTLDSC.TITLEREALNAME = '' THEN PARADB.PGMTTLDSC.TITLESCHNAME\n" + 
/*CH*/  "         ELSE PARADB.BUYUNTHDR.BUYUNTID\n" + 
/*CH*/  "     END AS EPISODENAME,\n"+
        "    PARADB.PGMTTLDSC.EPISODE AS EPNO,\n" + 
        "    CASE LENGTH(RTRIM(CHAR(LOGEDT.NOMLEN)))\n" + 
        "    WHEN 5 THEN '00:0'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,1)||':'||\n" + 
        "    SUBSTR(CHAR(LOGEDT.NOMLEN),2,2)\n" + 
        "    WHEN 6 THEN '00:'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,2)||':'||\n" + 
        "    SUBSTR(CHAR(LOGEDT.NOMLEN),3,2)\n" + 
        "    WHEN 7 THEN '0'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,1)||':'||\n" + 
        "    SUBSTR(CHAR(LOGEDT.NOMLEN),2,2)||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),4,2)\n" + 
        "    WHEN 8 THEN CASE SUBSTR(CHAR(LOGEDT.NOMLEN),1,2) WHEN '24' THEN '00' WHEN '25' THEN '01' WHEN '26' THEN '26'\n" + 
        "    WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(LOGEDT.NOMLEN),1,2)\n" + 
        "    END ||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),3,2)||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),5,2) ELSE ' ' END AS SLOTDURATION,\n" + 
        "    CASE WHEN PARADB.PGMSCH.PGMDSCID IS NULL THEN PARADB.BUYUNTHDR.BUYUNTID ELSE CHAR(PARADB.PGMSCH.TITLEID) END AS TITLEID,\n" + 
        "    CASE WHEN PARADB.PGMSCH.PGMDSCID IS NULL THEN PARADB.BUYUNTHDR.BUYUNTID ELSE PARADB.PGMSCH.PGMDSCID END AS EPISODENAMEID,\n" + 
        "    LOGEDT.PGMID AS BUYUNITID,\n" + 
        "    PARADB.BUYUNTHDR.BUYLNAM AS BUYUNIT,\n" + 
//        "    CASE WHEN PARADB.PGMSCH.SPECIAL = 0 THEN 'N' ELSE 'S' END AS EXCEPTCOFEPRIS,\n" + 
/*CH*/    //"    CASE WHEN PARADB.PGMSCH.SPECIAL = 0 THEN 'S' ELSE 'N' END AS EXCEPTCOFEPRIS,\n" + 
/*CH*/        " CASE WHEN PARADB.PGMSCH.SPECIAL = 0 THEN 'N' ELSE 'S' END AS EXCEPTCOFEPRIS,\n" + 
        "    CASE WHEN (SELECT B.ID_TIPO FROM\n" + 
        "                                EVENTAS.CA_PGM_BUYUNTID_ESP A, EVENTAS.CA_PGM_BUYUNTID_ESP_TIPO B\n" + 
        "                                WHERE A.ID_TIPO = B.ID_TIPO\n" + 
        "                                AND PARADB.PGMSCH.PGMID = (CASE WHEN (A.BUYUNTID = '' AND A.PGMID IS NOT NULL) THEN A.PGMID\n" + 
        "                                                   WHEN (A.PGMID = '' AND A.BUYUNTID IS NOT NULL) THEN A.BUYUNTID END)\n" + 
        "                                AND PARADB.PGMSCH.SCHDT BETWEEN A.STRDT AND A.EDT) = 1 THEN 'E'\n" + 
        "WHEN (SELECT B.ID_TIPO FROM\n" + 
        "                                EVENTAS.CA_PGM_BUYUNTID_ESP A, EVENTAS.CA_PGM_BUYUNTID_ESP_TIPO B\n" + 
        "                                WHERE A.ID_TIPO = B.ID_TIPO\n" + 
        "                                AND PARADB.PGMSCH.PGMID = (CASE WHEN (A.BUYUNTID = '' AND A.PGMID IS NOT NULL) THEN A.PGMID\n" + 
        "                                                   WHEN (A.PGMID = '' AND A.BUYUNTID IS NOT NULL) THEN A.BUYUNTID END)\n" + 
        "                                AND PARADB.PGMSCH.SCHDT BETWEEN A.STRDT AND A.EDT) = 2 THEN 'P'\n" + 
        "    ELSE 'R'\n" + 
        "        END AS EVENTSPECIAL,\n" + 
        "    CASE WHEN PARADB.PGMSCH.PROTECT = 0 THEN 'N' ELSE 'S' END AS EVENTBEST,\n" + 
        "    '' AS BUYUNTID_CORTE,\n" + 
        "    LOGEDT.BCSTDT AS DATE_CORTE,\n" + 
        "    '' AS HOUR_CORTE,\n" + 
        "    -1 AS CORTEID,\n" + 
        "    '' AS DESCORTE,\n" + 
        "    '' AS OVERLAY,\n" + 
        "    -1 AS BREAKID,\n" + 
        "    '' AS DESCRIPTION,\n" + 
        "    LOGEDT.BCSTDT AS DATE_BREAK,\n" + 
        "    '' AS HOUR_BREAK,\n" + 
        "    '' AS TOTALDURATION,\n" + 
        "    '' AS COMERCIALDURATION\n" + 
        "FROM    PARADB.LOGEDT LOGEDT\n" + 
        "        LEFT OUTER JOIN PARADB.LOGLINE LOGLINE\n" + 
        "            ON LOGEDT.LOGEDTID = LOGLINE.LOGEDTID\n" + 
        "        LEFT OUTER JOIN PARADB.BRKDT BRKDT\n" + 
        "            ON  LOGEDT.LOGEDTID = BRKDT.LOGEDTID\n" + 
        "        LEFT OUTER JOIN PARADB.LOGCMT LOGCMT\n" + 
        "            ON  LOGEDT.LOGEDTID = LOGCMT.LOGEDTID\n" + 
        "        LEFT OUTER JOIN PARADB.PGMSCH ON PARADB.PGMSCH.STNID = LOGEDT.STNID AND PARADB.PGMSCH.SCHDT = LOGEDT.BCSTDT\n" + 
        "        AND PARADB.PGMSCH.PGMID = LOGEDT.PGMID AND LOGEDT.BCSTTIM = PARADB.PGMSCH.SCHTIM\n" + 
        "        LEFT OUTER JOIN PARADB.PGMTTLDSC ON PARADB.PGMTTLDSC.PGMDSCID = LOGLINE.PGMDSCID AND PARADB.PGMTTLDSC.PGMDSCID = PARADB.PGMSCH.PGMDSCID\n" + 
        "        LEFT OUTER JOIN PARADB.BUYUNTHDR ON PARADB.BUYUNTHDR.BUYUNTID = PARADB.PGMSCH.PGMID AND PARADB.BUYUNTHDR.BUYUNTID = LOGEDT.PGMID\n" + 
        "WHERE LOGEDT.ALTLOG = 0\n" + 
        "AND LOGEDT.STNID IN ("+tsChannels+")\n" + 
//        "AND LOGEDT.BCSTDT >= CURRENT DATE\n" + 
/*CH*/  "AND LOGEDT.BCSTDT >= '2018-01-01'\n" + 
//        " AND LOGEDT.PGMID = 'ELCHAVO' \n" + 
        "AND LOGEDT.FMTTYP in (1)\n" + 
        "AND LOGEDT.FMTTYP <> 0\n" + 
/*CH*/        " AND LOGEDT.PGMID NOT IN (" + lsNotInPgmid + ")\n" + 
        "UNION ALL\n" + 
        "SELECT\n" + 
        "    LOGEDT.BCSTTIM,\n" + 
        "    LOGEDT.SEQNUM,\n" + 
        "    LOGEDT.FMTTYP,\n" + 
        "    -1 AS UNTNUM,\n" + 
        "    LOGEDT.STNID AS CHANNELID,\n" + 
        "    LOGEDT.BCSTDT AS DATEVALUE,\n" + 
        "    '' AS HOURSTART,\n" + 
        "    '' AS HOUREND,\n" + 
        "    '' AS HOUR,\n" + 
        "    '' AS TITLE,\n" + 
        "    '' AS EPISODENAME,\n" + 
        "    '' AS EPNO,\n" + 
        "    '' AS SLOTDURATION,\n" + 
        "    '0'  AS TITLEID,\n" + 
        "    '' AS EPISODENAMEID,\n" + 
        "    '' AS BUYUNITID,\n" + 
        "    '' AS BUYUNIT,\n" + 
        "    '' AS EXCEPTCOFEPRIS,\n" + 
        "    '' AS EVENTSPECIAL,\n" + 
        "    '' AS EVENTBEST,\n" + 
        "    LOGEDT.PGMID AS BUYUNTID_CORTE,\n" + 
        "    LOGEDT.BCSTDT AS DATE_CORTE,\n" + 
        "        CASE LENGTH(RTRIM(CHAR(LOGEDT.BCSTTIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(LOGEDT.BCSTTIM),1,1)||':'||\n" + 
        "    SUBSTR(CHAR(LOGEDT.BCSTTIM),2,2)||':'||SUBSTR(CHAR(LOGEDT.BCSTTIM),4,2) WHEN 8 THEN\n" + 
        "    CASE SUBSTR(CHAR(LOGEDT.BCSTTIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25' WHEN '26' THEN '26'\n" + 
        "    WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(LOGEDT.BCSTTIM),1,2)\n" + 
        "    END ||':'||SUBSTR(CHAR(LOGEDT.BCSTTIM),3,2)||':'||SUBSTR(CHAR(LOGEDT.BCSTTIM),5,2) ELSE ' ' END AS HOUR_CORTE,\n" + 
        "    LOGCMT.LOGCMTID AS CORTEID,\n" + 
        "    LOGCMT.CMT AS DESCORTE,\n" + 
        "    'N' AS OVERLAY,\n" + 
        "    -1 AS BREAKID,\n" + 
        "    '' AS DESCRIPTION,\n" + 
        "    LOGEDT.BCSTDT AS DATE_CORTE,\n" + 
        "    '' AS HOUR_CORTE,\n" + 
        "    '' AS TOTALDURATION,\n" + 
        "    '' AS COMERCIALDURATION\n" + 
        "FROM    PARADB.LOGEDT LOGEDT\n" + 
        "        LEFT OUTER JOIN PARADB.LOGLINE LOGLINE\n" + 
        "            ON LOGEDT.LOGEDTID = LOGLINE.LOGEDTID\n" + 
        "        LEFT OUTER JOIN PARADB.BRKDT BRKDT\n" + 
        "            ON  LOGEDT.LOGEDTID = BRKDT.LOGEDTID\n" + 
        "        LEFT OUTER JOIN PARADB.LOGCMT LOGCMT\n" + 
        "            ON  LOGEDT.LOGEDTID = LOGCMT.LOGEDTID\n" + 
        "WHERE LOGEDT.ALTLOG = 0\n" + 
        "AND LOGEDT.STNID IN ("+tsChannels+")\n" + 
//        "AND LOGEDT.BCSTDT >= CURRENT DATE\n" + 
/*CH*/  "AND LOGEDT.BCSTDT >= '2018-01-01'\n" +                          
        "AND LOGEDT.FMTTYP in (5)\n" + 
//        " AND LOGEDT.PGMID = 'ELCHAVO' \n" + 
        //"AND (LOGCMT.CMT LIKE '*%CORTE%' OR LOGCMT.CMT LIKE 'BREA%')\n" + 
/*CH*/        //" AND (LOGCMT.CMT LIKE '*%CORTE%' OR LOGCMT.CMT LIKE 'BREA%' OR LOGCMT.CMT LIKE '%CORTE%')\n" +
/*CH*/        " AND (LOGCMT.CMT LIKE '*%CORTE%' OR LOGCMT.CMT LIKE '%BREA%' OR LOGCMT.CMT LIKE '%CORTE%')\n" +
/*CH*/        " AND LOGEDT.PGMID NOT IN (" + lsNotInPgmid + ")\n" +                          
        "UNION ALL\n" + 
        "SELECT\n" + 
        "    LOGEDT.BCSTTIM,\n" + 
        "    LOGEDT.SEQNUM,\n" + 
        "    LOGEDT.FMTTYP,\n" + 
        "    -1 AS UNTNUM,\n" + 
        "    LOGEDT.STNID AS CHANNELID,\n" + 
        "    LOGEDT.BCSTDT AS DATEVALUE,\n" + 
        "    '' AS HOURSTART,\n" + 
        "    '' AS HOUREND,\n" + 
        "    '' AS HOUR,\n" + 
        "    '' AS TITLE,\n" + 
        "    '' AS EPISODENAME,\n" + 
        "    '' AS EPNO,\n" + 
        "    '' AS SLOTDURATION,\n" + 
        "    '0'  AS TITLEID,\n" + 
        "    '' AS EPISODENAMEID,\n" + 
        "    '' AS BUYUNITID,\n" + 
        "    '' AS BUYUNIT,\n" + 
        "    '' AS EXCEPTCOFEPRIS,\n" + 
        "    '' AS EVENTSPECIAL,\n" + 
        "    '' AS EVENTBEST,\n" + 
        "    '' AS BUYUNTID_CORTE,\n" + 
        "    LOGEDT.BCSTDT AS DATE_CORTE,\n" + 
        "    '' AS HOUR_CORTE,\n" + 
        "    -1 AS CORTEID,\n" + 
        "    '' AS DESCORTE,\n" + 
        "    '' AS OVERLAY,\n" + 
        "    BRKDT.BRKDTID AS BREAKID,\n" + 
        "    BRKDT.BRKDSCR AS DESCRIPTION,\n" + 
        "    LOGEDT.BCSTDT AS DATE_BREAK,\n" + 
        "    CASE LENGTH(RTRIM(CHAR(LOGEDT.BCSTTIM))) WHEN 7 THEN '0'||SUBSTR(CHAR(LOGEDT.BCSTTIM),1,1)||':'||\n" + 
        "    SUBSTR(CHAR(LOGEDT.BCSTTIM),2,2)||':'||SUBSTR(CHAR(LOGEDT.BCSTTIM),4,2) WHEN 8 THEN\n" + 
        "    CASE SUBSTR(CHAR(LOGEDT.BCSTTIM),1,2) WHEN '24' THEN '24' WHEN '25' THEN '25' WHEN '26' THEN '26'\n" + 
        "    WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(LOGEDT.BCSTTIM),1,2)\n" + 
        "    END ||':'||SUBSTR(CHAR(LOGEDT.BCSTTIM),3,2)||':'||SUBSTR(CHAR(LOGEDT.BCSTTIM),5,2) ELSE ' ' END AS HOUR_BREAK,\n" + 
        "        CASE LENGTH(RTRIM(CHAR(LOGEDT.NOMLEN)))\n" + 
        "    WHEN 5 THEN '00:0'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,1)||':'||\n" + 
        "    SUBSTR(CHAR(LOGEDT.NOMLEN),2,2)\n" + 
        "    WHEN 6 THEN '00:'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,2)||':'||\n" + 
        "    SUBSTR(CHAR(LOGEDT.NOMLEN),3,2)\n" + 
/*CH*/        "    WHEN 1 THEN '00:00:01' \n" +
/*CH*/        "    WHEN 4 THEN '00:00:'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,2) \n" +                         
        "    WHEN 7 THEN '0'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,1)||':'||\n" + 
        "    SUBSTR(CHAR(LOGEDT.NOMLEN),2,2)||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),4,2)\n" + 
        "    WHEN 8 THEN CASE SUBSTR(CHAR(LOGEDT.NOMLEN),1,2) WHEN '24' THEN '00' WHEN '25' THEN '01' WHEN '26' THEN '26'\n" + 
        "    WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(LOGEDT.NOMLEN),1,2)\n" + 
        "    END ||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),3,2)||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),5,2) ELSE ' ' END AS TOTALDURATION,\n" + 
        "        CASE LENGTH(RTRIM(CHAR(LOGEDT.NOMLEN)))\n" + 
        "    WHEN 5 THEN '00:0'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,1)||':'||\n" + 
        "    SUBSTR(CHAR(LOGEDT.NOMLEN),2,2)\n" + 
        "    WHEN 6 THEN '00:'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,2)||':'||\n" + 
        "    SUBSTR(CHAR(LOGEDT.NOMLEN),3,2)\n" + 
/*CH*/        "    WHEN 1 THEN '00:00:01' \n" + 
/*CH*/        "    WHEN 4 THEN '00:00:'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,2) \n" +
        "    WHEN 7 THEN '0'||SUBSTR(CHAR(LOGEDT.NOMLEN),1,1)||':'||\n" + 
        "    SUBSTR(CHAR(LOGEDT.NOMLEN),2,2)||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),4,2)\n" + 
        "    WHEN 8 THEN CASE SUBSTR(CHAR(LOGEDT.NOMLEN),1,2) WHEN '24' THEN '00' WHEN '25' THEN '01' WHEN '26' THEN '26'\n" + 
        "    WHEN '27' THEN '27' WHEN '28' THEN '28' WHEN '29' THEN '29' ELSE SUBSTR(CHAR(LOGEDT.NOMLEN),1,2)\n" + 
        "    END ||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),3,2)||':'||SUBSTR(CHAR(LOGEDT.NOMLEN),5,2) ELSE ' ' END AS COMERCIALDURATION\n" + 
        "FROM    PARADB.LOGEDT LOGEDT\n" + 
        "        LEFT OUTER JOIN PARADB.LOGLINE LOGLINE\n" + 
        "            ON LOGEDT.LOGEDTID = LOGLINE.LOGEDTID\n" + 
        "        LEFT OUTER JOIN PARADB.BRKDT BRKDT\n" + 
        "            ON  LOGEDT.LOGEDTID = BRKDT.LOGEDTID\n" + 
        "        LEFT OUTER JOIN PARADB.LOGCMT LOGCMT\n" + 
        "            ON  LOGEDT.LOGEDTID = LOGCMT.LOGEDTID\n" + 
        "WHERE LOGEDT.ALTLOG = 0\n" + 
        "AND LOGEDT.STNID IN (" + tsChannels + ")\n" + 
//        "AND LOGEDT.BCSTDT >= CURRENT DATE\n" + 
/*CH*/  "AND LOGEDT.BCSTDT >= '2018-01-01'\n" + 
        "AND LOGEDT.FMTTYP in (2)\n" + 
        "AND  BRKDT.BRKCHRRULE IN ('CO','')\n" + 
        "AND BRKDT.INVFL = 1\n" + 
/*CH*/        " AND LOGEDT.PGMID NOT IN (" + lsNotInPgmid + ")\n" +                          
//        " AND LOGEDT.PGMID = 'ELCHAVO' \n" + 
        "ORDER BY CHANNELID, DATEVALUE,BCSTTIM, SEQNUM, FMTTYP, UNTNUM";
        //System.out.println(lsQuery);
        return lsQuery;
    }
    /**
     * Obtiene cadena de consulta cuando bandera es cero
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return String
     */
    @Override
    public String getQueryInsertParadigmFlagZero(String tsDate, 
                                                 String tsChannels) {
        /*
        String lsQuery = "INSERT INTO EVENTAS.EVETV_PARRILLAS_PROCESADAS(STNID,BCSTDT,PGMID,STATUS)\n" + 
        "         SELECT LOGEDT.STNID,\n" + 
        "                LOGEDT.BCSTDT,\n" + 
        "                LOGEDT.PGMID,\n" + 
        "                '1'\n" + 
        "           FROM PARADB.LOGEDT LOGEDT\n" + 
        "LEFT OUTER JOIN PARADB.LOGLINE LOGLINE\n" + 
        "            ON LOGEDT.LOGEDTID = LOGLINE.LOGEDTID\n" + 
        "LEFT OUTER JOIN PARADB.BRKDT BRKDT\n" + 
        "             ON LOGEDT.LOGEDTID = BRKDT.LOGEDTID\n" + 
        "LEFT OUTER JOIN PARADB.LOGCMT LOGCMT\n" + 
        "             ON LOGEDT.LOGEDTID = LOGCMT.LOGEDTID\n" + 
        "LEFT OUTER JOIN PARADB.PGMSCH ON PARADB.PGMSCH.STNID = LOGEDT.STNID \n" + 
        "                AND PARADB.PGMSCH.SCHDT = LOGEDT.BCSTDT\n" + 
        "                AND PARADB.PGMSCH.PGMID = LOGEDT.PGMID \n" + 
        "                AND PARADB.PGMSCH.TITLEID = LOGLINE.TITLEID \n" + 
        "                AND PARADB.PGMSCH.PGMDSCID = LOGLINE.PGMDSCID\n" + 
        "LEFT OUTER JOIN PARADB.PGMTTLDSC ON PARADB.PGMTTLDSC.PGMDSCID = LOGLINE.PGMDSCID \n" + 
        "                AND PARADB.PGMTTLDSC.PGMDSCID = PARADB.PGMSCH.PGMDSCID\n" + 
        "LEFT OUTER JOIN PARADB.BUYUNTHDR ON PARADB.BUYUNTHDR.BUYUNTID = PARADB.PGMSCH.PGMID \n" + 
        "                AND PARADB.BUYUNTHDR.BUYUNTID = LOGEDT.PGMID\n" + 
        "          WHERE LOGEDT.ALTLOG = 0\n" + 
        "            AND LOGEDT.STNID IN ("+tsChannels+")\n" + 
        "            AND LOGEDT.BCSTDT >= CURRENT DATE\n" + 
        "            AND LOGEDT.FMTTYP in (1)\n" + 
        "            AND LOGEDT.FMTTYP <> 0\n" + 
        "      ORDER BY STNID, BCSTDT";
        */
        String lsQuery = 
            "INSERT INTO EVENTAS.EVETV_PARRILLAS_PROCESADAS(STNID,BCSTDT,PGMID,STATUS,BRKDTID)\n" + 
            "SELECT\n" + 
            "    LOGEDT.STNID,\n" + 
            "    LOGEDT.BCSTDT,\n" + 
            "    LOGEDT.PGMID,\n" + 
            "    '1',\n" + 
            "    BRKDT.BRKDTID\n" + 
            "FROM    PARADB.LOGEDT LOGEDT\n" + 
            "        LEFT OUTER JOIN PARADB.LOGLINE LOGLINE\n" + 
            "            ON LOGEDT.LOGEDTID = LOGLINE.LOGEDTID\n" + 
            "        LEFT OUTER JOIN PARADB.BRKDT BRKDT\n" + 
            "            ON  LOGEDT.LOGEDTID = BRKDT.LOGEDTID\n" + 
            "        LEFT OUTER JOIN PARADB.LOGCMT LOGCMT\n" + 
            "            ON  LOGEDT.LOGEDTID = LOGCMT.LOGEDTID\n" + 
            "        LEFT OUTER JOIN PARADB.PGMSCH PGMSCH ON PGMSCH.STNID = LOGEDT.STNID AND PGMSCH.SCHDT = LOGEDT.BCSTDT\n" + 
            "        AND PGMSCH.PGMID = LOGEDT.PGMID AND PGMSCH.TITLEID = LOGLINE.TITLEID AND PGMSCH.PGMDSCID = LOGLINE.PGMDSCID\n" + 
            "        LEFT OUTER JOIN PARADB.PGMTTLDSC PGMTTLDSC ON PGMTTLDSC.PGMDSCID = LOGLINE.PGMDSCID AND PGMTTLDSC.PGMDSCID = PGMSCH.PGMDSCID\n" + 
            "        LEFT OUTER JOIN PARADB.BUYUNTHDR BUYUNTHDR ON BUYUNTHDR.BUYUNTID = PGMSCH.PGMID AND BUYUNTHDR.BUYUNTID = LOGEDT.PGMID\n" + 
            "WHERE LOGEDT.ALTLOG = 0\n" + 
            "AND LOGEDT.STNID IN (" + tsChannels + ")\n" + 
//            "AND LOGEDT.BCSTDT >= CURRENT DATE\n" + 
/*CH*/      "AND LOGEDT.BCSTDT >= '2018-01-01'\n" + 
            "AND LOGEDT.FMTTYP in (2)\n" + 
            "AND BRKDT.BRKCHRRULE IN ('CO','')\n" + 
            "AND BRKDT.INVFL = 1\n" + 
            "AND LOGEDT.FMTTYP <> 0\n" + 
            "ORDER BY STNID, BCSTDT";
        
        return lsQuery;
    }

    /**
     * Obtiene conjunto de registros cuando bandera es cero
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return String
     */
    @Override
    public List<RsstParrillasCortesBean> getParrillasCortesParadigmDbFlagZero(String tsDate, 
                                                                              String tsChannels
                                                                              ) throws SQLException {
        List<RsstParrillasCortesBean> laParrillasCortes = 
            new ArrayList<RsstParrillasCortesBean>();
        Connection                    loCnn = new ConnectionAs400().getConnection();
        SimpleDateFormat              loSDF = new SimpleDateFormat("dd/MM/yyyy");
        ResultSet                     loRs = null;
        String                        lsQueryParadigm = 
            getQueryParadigmFlagZero(tsDate, tsChannels);
        System.out.println(lsQueryParadigm);
        int                           liCounter = 0;
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);
            while(loRs.next()){
                liCounter ++;
                RsstParrillasCortesBean parrillaCorteBean =
                    new RsstParrillasCortesBean(); 
                parrillaCorteBean.setLsBcsttim(loRs.getString("BCSTTIM"));                
                parrillaCorteBean.setLsSeqnum(loRs.getString("SEQNUM"));
                parrillaCorteBean.setLsFmttyp(loRs.getString("FMTTYP"));
                parrillaCorteBean.setLsUntnum(loRs.getString("UNTNUM"));
                parrillaCorteBean.setLsChannelid(loRs.getString("CHANNELID"));
                Date ldDt = loRs.getDate("DATEVALUE");
                //parrillaCorteBean.setLsDatevalue(loRs.getString("DATEVALUE"));
                parrillaCorteBean.setLsDatevalue(loSDF.format(ldDt));
                parrillaCorteBean.setLsHourstart(loRs.getString("HOURSTART"));
                parrillaCorteBean.setLsHourend(loRs.getString("HOUREND"));
                parrillaCorteBean.setLsHour(loRs.getString("HOUR"));
                parrillaCorteBean.setLsTitle(loRs.getString("TITLE"));
                parrillaCorteBean.setLsEpisodename(loRs.getString("EPISODENAME"));
                parrillaCorteBean.setLsEpno(loRs.getString("EPNO"));
                parrillaCorteBean.setLsSlotduration(loRs.getString("SLOTDURATION"));
                parrillaCorteBean.setLsTitleid(loRs.getString("TITLEID"));
                parrillaCorteBean.setLsEpisodenameid(loRs.getString("EPISODENAMEID"));                
                parrillaCorteBean.setLsBuyunitid(loRs.getString("BUYUNITID"));
                parrillaCorteBean.setLsBuyunit(loRs.getString("BUYUNIT"));
                parrillaCorteBean.setLsExceptcofepris(loRs.getString("EXCEPTCOFEPRIS"));
                parrillaCorteBean.setLsEventspecial(loRs.getString("EVENTSPECIAL"));
                parrillaCorteBean.setLsEventbest(loRs.getString("EVENTBEST"));
                parrillaCorteBean.setLsBuyuntidCorte(loRs.getString("BUYUNTID_CORTE"));
                Date ldDtCorte = loRs.getDate("DATE_CORTE");
                //parrillaCorteBean.setLsDateCorte(loRs.getString("DATE_CORTE"));
                parrillaCorteBean.setLsDateCorte(loSDF.format(ldDtCorte));
                parrillaCorteBean.setLsHourCorte(loRs.getString("HOUR_CORTE"));
                parrillaCorteBean.setLsCorteid(loRs.getString("CORTEID"));
                parrillaCorteBean.setLsDescorte(loRs.getString("DESCORTE"));
                parrillaCorteBean.setLsOverlay(loRs.getString("OVERLAY"));
                parrillaCorteBean.setLsBreakid(loRs.getString("BREAKID"));
                parrillaCorteBean.setLsDescription(loRs.getString("DESCRIPTION"));
                Date ldDtBreak = loRs.getDate("DATE_BREAK");
                //parrillaCorteBean.setLsDateBreak(loRs.getString("DATE_BREAK"));
                parrillaCorteBean.setLsDateBreak(loSDF.format(ldDtBreak));
                parrillaCorteBean.setLsHourBreak(loRs.getString("HOUR_BREAK"));
                parrillaCorteBean.setLsTotalduration(loRs.getString("TOTALDURATION"));
                parrillaCorteBean.setLsComercialduration(loRs.getString("COMERCIALDURATION"));
                
                laParrillasCortes.add(parrillaCorteBean);
            }
            
        } catch (SQLException loExSql) {
            System.out.println("ERROR EN EJECUCION SQL "+loExSql.getMessage());
            throw loExSql;
        }
        finally{
            try {
                loCnn.close();
                loRs.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return laParrillasCortes;
    }

    /**
     * Inserta Parrillas cuando la bandera es cero
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return void
     */
    @Override
    public ResponseUpdDao insertParrillasProcesadasFlagZero(String tsDate, 
                                                            String tsChannels
                                                            ) {
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection     loCnn = new ConnectionAs400().getConnection();
        String         lsQueryParadigm = 
            getQueryInsertParadigmFlagZero(tsDate,tsChannels);
        try {
            Statement loStmt = loCnn.createStatement();
            Integer liRes = loStmt.executeUpdate(lsQueryParadigm);     
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLiAffected(liRes);
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
     * Inserta en tabla de control para parrillas
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsEstatus
     * @param tsUserName
     * @param tsIdUser
     * @param toParrilla
     * @return Integer
     */
    @Override
    public Integer insertEveTvParrillas(String tsIdRequest, 
                                        String tsIdService, 
                                        String tsEstatus,
                                        String tsUserName,
                                        String tsIdUser,
                                        RsstParrillasCortesBean toParrilla) {
        Integer    liReturn = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = 
            getQueryInsertParrillas(tsIdRequest, 
                                   tsIdService, 
                                   tsEstatus,
                                   tsUserName,
                                   tsIdUser,
                                   toParrilla
                                   );
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
     * Obtiene instruccion para Insertar en tabla de control para parrillas
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsEstatus
     * @param tsUserName
     * @param tsIdUser
     * @param toParrilla
     * @return Integer
     */
    @Override
    public String getQueryInsertParrillas(String psIdRequest, 
                                         String psIdService, 
                                         String psEstatus,
                                         String psUserName,
                                         String psIdUser,
                                         RsstParrillasCortesBean poParrilla) {
        String lsQuery = 
            "INSERT INTO EVENTAS.EVETV_INT_RST_PARRILLAS_TAB\n" + 
            "       (ID_REQUEST,\n" + 
            "		ID_SERVICE,\n" + 
            "		BCSTTIM,\n" + 
            "		SEQNUM,\n" + 
            "		FMTTYP,\n" + 
            "		UNTNUM,\n" + 
            "		CHANNELID,\n" + 
            "		DATEVALUE,\n" + 
            "		HOURSTART,\n" + 
            "		HOUREND,\n" + 
            "		HOUR,\n" + 
            "		TITLE,\n" + 
            "		EPISODENAME,\n" + 
            "		EPNO,\n" + 
            "		SLOTDURATION,\n" + 
            "		TITLEID,\n" + 
            "		EPISODENAMEID,\n" + 
            "		BUYUNITID,\n" + 
            "		BUYUNIT,\n" + 
            "		EXCEPTCOFEPRIS,\n" + 
            "		EVENTSPECIAL,\n" + 
            "		BUYUNTID_CORTE,\n" + 
            "		DATE_CORTE,\n" + 
            "		HOUR_CORTE,\n" + 
            "		CORTEID,\n" + 
            "		DESCORTE,\n" + 
            "		OVERLAY,\n" + 
            "		BREAKID,\n" + 
            "		DESCRIPTION,\n" + 
            "		DATE_BREAK,\n" + 
            "		HOUR_BREAK,\n" + 
            "		TOTALDURATION,\n" + 
            "		COMERCIALDURATION,\n" + 
            "		IND_ESTATUS,\n" + 
            "		IND_DESC_NEPTUNO,\n" + 
            "		ID_PROCESS_NEPTUNO,\n" + 
            "		ATTRIBUTE15,\n" + 
            "		FEC_CREATION_DATE,\n" + 
            "		NUM_CREATED_BY,\n" + 
            "		FEC_LAST_UPDATE_DATE,\n" + 
            "		NUM_LAST_UPDATED_BY\n" + 
            "	   )\n" + 
            "VALUES (" + psIdRequest + ",\n" + 
            "		" + psIdService + ",\n" + 
            "		'" + poParrilla.getLsBcsttim() + "',\n" + 
            "		'" + poParrilla.getLsSeqnum() + "',\n" + 
            "		'" + poParrilla.getLsFmttyp() + "',\n" + 
            "		'" + poParrilla.getLsUntnum() + "',\n" + 
            "		'" + poParrilla.getLsChannelid() + "',\n" + 
            "		'" + poParrilla.getLsDatevalue() + "',\n" + 
            "		'" + poParrilla.getLsHourstart() + "',\n" + 
            "		'" + poParrilla.getLsHourend() + "',\n" + 
            "		'" + poParrilla.getLsHour() + "',\n" + 
            "		'" + poParrilla.getLsTitle() + "',\n" + 
            "		'" + poParrilla.getLsEpisodename() + "',\n" + 
            "		'" + poParrilla.getLsEpno() + "',\n" + 
            "		'" + poParrilla.getLsSlotduration() + "',\n" + 
            "		'" + poParrilla.getLsTitleid() + "',\n" + 
            "		'" + poParrilla.getLsEpisodename() + "',\n" + 
            "		'" + poParrilla.getLsBuyunitid() + "',\n" + 
            "		'" + poParrilla.getLsBuyunit() + "',\n" + 
            "		'" + poParrilla.getLsExceptcofepris() + "',\n" + 
            "		'" + poParrilla.getLsEventspecial() + "',\n" + 
            "		'" + poParrilla.getLsBuyuntidCorte() + "',\n" + 
            "		'" + poParrilla.getLsDateCorte() + "',\n" + 
            "		'" + poParrilla.getLsHourCorte() + "',\n" + 
            "		'" + poParrilla.getLsCorteid() + "',\n" + 
            "		'" + poParrilla.getLsDescorte() + "',\n" + 
            "		'" + poParrilla.getLsOverlay() + "',\n" + 
            "		'" + poParrilla.getLsBreakid() + "',\n" + 
            "		'" + poParrilla.getLsDescription() + "',\n" + 
            "		'" + poParrilla.getLsDateBreak() + "',\n" + 
            "		'" + poParrilla.getLsHourBreak() + "',\n" + 
            "		'" + poParrilla.getLsTotalduration() + "',\n" + 
            "		'" + poParrilla.getLsComercialduration() + "',\n" + 
            "		'" + psEstatus + "',\n" + 
            "		'',\n" + 
            "		0,\n" + 
            "		'" + psUserName + "',\n" + 
            "		CURRENT_TIMESTAMP,\n" + 
            "		" + psIdUser + ",\n" + 
            "		CURRENT_TIMESTAMP,\n" + 
            "		" + psIdUser + "\n" + 
            "       )";
        return lsQuery;
    }

    /**
     * Actualiza en tabla de control para parrillas
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsEstatus
     * @param tsUserName
     * @param tsIdUser
     * @param tsCorteId
     * @param tsBreakId
     * @return Integer
     */
    @Override
    public Integer updateEveTvParrillas(String tsIdRequest, 
                                        String tsIdService, 
                                        String tsEstatus,
                                        String tsUserName,
                                        String tsIdUser,
                                        String tsCorteId,
                                        String tsBreakId) {
        Integer    liReturn = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = 
            getQueryUpdateParrillas(tsIdRequest, 
                                    tsIdService, 
                                    tsEstatus,
                                    tsUserName,
                                    tsIdUser,
                                    tsCorteId,
                                    tsBreakId
                                   );
        
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
     * Obtiene instruccion para actualizar en tabla de control para parrillas
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsEstatus
     * @param tsUserName
     * @param tsIdUser
     * @param tsCorteId
     * @param tsBreakId
     * @return Integer
     */
    @Override
    public String getQueryUpdateParrillas(String tsIdRequest, 
                                          String tsIdService, 
                                          String tsEstatus,
                                          String tsUserName,
                                          String tsIdUser,
                                          String tsCorteId,
                                          String tsBreakId) {
        String lsQuery = 
            "UPDATE EVENTAS.EVETV_INT_RST_PARRILLAS_TAB\n" + 
            "   SET IND_ESTATUS = '" + tsEstatus + "',\n" + 
            "       FEC_LAST_UPDATE_DATE = CURRENT_TIMESTAMP\n" + 
            " WHERE ID_REQUEST = " + tsIdRequest + "\n" + 
            "   AND ID_SERVICE = " + tsIdService + "\n" + 
            "   AND BREAKID = '" + tsBreakId + "'";
        return lsQuery;
    }

    /**
     * Actualiza todos los registros a error
     * @autor Jorge Luis Bautista Santiago
     * @return ResponseUpdDao
     */
    @Override
    public ResponseUpdDao updateAllParrillasProcesadas() {
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = "UPDATE EVENTAS.EVETV_PARRILLAS_PROCESADAS SET STATUS = 2";
        try {
            Statement loStmt = loCnn.createStatement();
            Integer liRes = loStmt.executeUpdate(lsQueryParadigm);
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLiAffected(liRes);
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
     * Ejecuta llamado a Procedimiento Almacenado en Paradigm
     * @autor Jorge Luis Bautista Santiago
     * @return ResponseUpdDao
     */
    public ResponseUpdDao callParrillasPr(String tsChannels) {
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection        loCnn = new ConnectionAs400().getConnection();
        CallableStatement loCallStmt = null;
        String            lsQueryParadigm = "call EVENTAS.EVETV_PARRILLAS(?)";
        try {
            loCallStmt = loCnn.prepareCall(lsQueryParadigm);
            loCallStmt.setString(1, tsChannels);
            loCallStmt.executeUpdate();
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLsMessage("EVENTAS.EVETV_PARRILLAS("+tsChannels+") Execute Success!");
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
     * Ejecuta llamado a Procedimiento Almacenado en Paradigm para parrillas onDemand
     * @autor Jorge Luis Bautista Santiago
     * @return ResponseUpdDao
     */
    public ResponseUpdDao callParrillasOnDemandPr(String tsStnid, 
                                                  String tsStrdt, 
                                                  String tsEdt, 
                                                  String tsBuyunitid) {
        System.out.println("Dentro de callParrillasOnDemandPr");
        System.out.println("**** setteando EVETV_PARRILLAS ******");
        System.out.println("tsStnid["+tsStnid+"]");
        System.out.println("tsStrdt["+tsStrdt+"]");
        System.out.println("tsEdt["+tsEdt+"]");
        System.out.println("tsBuyunitid["+tsBuyunitid+"]");
        ResponseUpdDao loResponseUpdDao = new ResponseUpdDao();
        Connection        loCnn = new ConnectionAs400().getConnection();
        CallableStatement loCallStmt = null;
        String            lsQueryParadigm = "call EVENTAS.EVETV_PARRILLAS(?,?,?,?)";
        try {
            loCallStmt = loCnn.prepareCall(lsQueryParadigm);
            loCallStmt.setString(1, tsStnid);
            if(tsStrdt != null){
                //Puede ser que sea un valor vacio, entonces
                if(!tsStrdt.trim().equalsIgnoreCase("")){
                    java.sql.Date     ltDateStrt = getDateYYYYMMDD(tsStrdt);
                    loCallStmt.setDate(2, ltDateStrt);    
                }else{
                    loCallStmt.setDate(2, null);
                }
                
            }else{
                loCallStmt.setDate(2, null);
            }
            if(tsEdt != null){
                //Puede ser que sea un valor vacio, entonces
                if(!tsEdt.trim().equalsIgnoreCase("")){
                    java.sql.Date     ltDateEnd = getDateYYYYMMDD(tsEdt);
                    loCallStmt.setDate(3, ltDateEnd); 
                }else{
                    loCallStmt.setDate(3, null);
                }
            }else{
                loCallStmt.setDate(3, null);
            }
            if(tsBuyunitid != null){
                //Puede ser que sea un valor vacio, entonces
                if(!tsBuyunitid.trim().equalsIgnoreCase("")){
                    loCallStmt.setString(4, tsBuyunitid);    
                }else{
                    loCallStmt.setString(4, null);
                }
                
            }else{
                loCallStmt.setString(4, null);
            }
            loCallStmt.executeUpdate();
            loResponseUpdDao.setLsResponse("OK");
            loResponseUpdDao.setLsMessage("EVENTAS.EVETV_PARRILLAS("+tsStnid+","+tsStrdt+","+tsEdt+","+tsBuyunitid+") Execute Success!");
        } catch (SQLException loExSql) {
            System.out.println("ERROR AL EJECUTAR>>>: "+loExSql.getMessage());
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
