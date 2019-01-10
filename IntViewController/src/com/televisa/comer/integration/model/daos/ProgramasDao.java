/**
* Project: Integraton Paradigm - EveTV
*
* File: ProgramasDao.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.model.daos;

import com.televisa.comer.integration.model.beans.RsstProgramasBean;
import com.televisa.comer.integration.model.connection.ConnectionAs400;
import com.televisa.comer.integration.model.interfaces.ProgramasInterfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/** Objeto que accede a base de datos para consultar Programas
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class ProgramasDao implements ProgramasInterfaces{
    
    /**
     * Obtiene Numero de registros para determinar el procedimiento a seguir en 
     * modulo de programas
     * @autor Jorge Luis Bautista Santiago
     * @return Integer
     */
    @Override
    public Integer getCountEveTvProgramas() {
        Integer    loFlagProgram = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        ResultSet  loRs = null;
        String     lsQueryParadigm = getQueryFlagPrograms();
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                loFlagProgram = loRs.getInt(1);
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
        return loFlagProgram;
    }

    /**
     * Obtiene lista de Programas
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return List
     */
    @Override
    public List<RsstProgramasBean> getProgramsFromParadigmService(String tsDate, 
                                                                  String tsChannels
                                                                  ) {
        List<RsstProgramasBean> loPrograms = 
            new ArrayList<RsstProgramasBean>();
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        
        //Validar bandera tabla EVENTAS.EVETV_PROGRAMAS tiene 0 registros 
        String                  lsQueryParadigm = 
            getQueryPrograms(tsDate, tsChannels);        
    
        if(getCountEveTvProgramas() > 0){
            lsQueryParadigm = getQueryPrograms();        
        }
                              
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                RsstProgramasBean loProgramBean = new RsstProgramasBean();             
                loProgramBean.setLsBuyuntid(loRs.getString("BUYUNTID") == null ? null : 
                                          loRs.getString("BUYUNTID").trim());
                loProgramBean.setLsBuylnam(loRs.getString("BUYLNAM") == null ? null :
                                         loRs.getString("BUYLNAM").trim());
                loProgramBean.setLsIndactivo(loRs.getString("INDACTIVO"));
                loProgramBean.setLsIndtarifaprograma(loRs.getString("INDTARIFAPROGRAMA"));
                loPrograms.add(loProgramBean);
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
        return loPrograms;
    }

    /**
     * Obtiene consulta sql para revisar el numero de registros de la tabla
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    @Override
    public String getQueryFlagPrograms() {
        String lsQuery = 
            "SELECT COUNT(1) FROM EVENTAS.EVETV_PROGRAMAS";
        return lsQuery;
    }

    /**
     * Obtiene consulta sql para programas a enviar a eveTv
     * @autor Jorge Luis Bautista Santiago
     * @param tsDate
     * @param tsChannels
     * @return String
     */
    @Override
    public String getQueryPrograms(String tsDate, 
                                   String tsChannels
                                   ) {
        String lsQuery = 
            "SELECT DISTINCT A.BUYUNTID,\n" + 
            "               A.BUYLNAM,\n" + 
            "               'S' INDACTIVO,\n" + 
            "               'N' INDTARIFAPROGRAMA\n" + 
            "          FROM PARADB.BUYUNTHDR A,\n" + 
            "               PARADB.PGMSCH C\n" + 
            "         WHERE A.BUYUNTID = C.PGMID\n" + 
            "           AND C.STNID IN (" + tsChannels + ")\n" + 
            "           AND C.SCHDT >= '" + tsDate + "'\n";
        return lsQuery;
    }

    /**
     * Obtiene consulta sql para programas a enviar a eveTv
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    @Override
    public String getQueryPrograms() {
        String lsQuery = 
        "    SELECT A.BUYUNTID,\n" + 
        "           A.BUYLNAM,\n" + 
        "           B.INDACTIVO,\n" + 
        "           B.INDTARIFAPROGRAMA\n" + 
        "      FROM PARADB.BUYUNTHDR A\n" + 
        " LEFT JOIN EVENTAS.EVETV_PROGRAMAS B \n" + 
        "        ON A.BUYUNTID = B.BUYUNTID\n" + 
        "     WHERE B.STATUS = 1";
        return lsQuery;
    }

    /**
     * Inserta en tabla de control para programas
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsBuyuntid
     * @param tsBuylnam
     * @param tsActivo
     * @param tsTarifaProgramada
     * @param tsEstatus
     * @param tsUserName
     * @param tsIdUser
     * @return Integer
     */
    @Override
    public Integer insertEveTvProgramas(String tsIdRequest, 
                                        String tsIdService, 
                                        String tsBuyuntid, 
                                        String tsBuylnam,
                                        String tsActivo, 
                                        String tsTarifaProgramada, 
                                        String tsEstatus,
                                        String tsUserName,
                                        String tsIdUser
                                        ) {
        Integer    liReturn = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = 
            getQueryInsertPrograms(tsIdRequest, 
                                   tsIdService, 
                                   tsBuyuntid, 
                                   tsBuylnam,
                                   tsActivo, 
                                   tsTarifaProgramada, 
                                   tsEstatus,
                                   tsUserName,
                                   tsIdUser
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
     * Obtiene consulta para Insertar en tabla de control para programas
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsBuyuntid
     * @param tsBuylnam
     * @param tsActivo
     * @param tsTarifaProgramada
     * @param tsEstatus
     * @param tsUserName
     * @param tsIdUser
     * @return Integer
     */
    @Override
    public String getQueryInsertPrograms(String tsIdRequest, 
                                         String tsIdService, 
                                         String tsBuyuntid, 
                                         String tsBuylnam,
                                         String tsActivo, 
                                         String tsTarifaProgramada, 
                                         String tsEstatus,
                                         String tsUserName,
                                         String tsIdUser
                                         ) {
        
        
        String lsQuery = 
        " INSERT INTO EVENTAS.EVETV_INT_RST_PROGRAMAS_TAB (ID_REQUEST,\n" + 
        "                                                 ID_SERVICE,\n" + 
        "                                                 BUYUNTID,\n" + 
        "                                                 BUYLNAM,\n" + 
        "                                                 INDACTIVO,\n" + 
        "                                                 INDTARIFAPROGRAMA,\n" + 
        "                                                 IND_ESTATUS,\n" + 
        "                                                 ATTRIBUTE15,\n" + 
        "                                                 FEC_CREATION_DATE,\n" + 
        "                                                 NUM_CREATED_BY,\n" + 
        "                                                 FEC_LAST_UPDATE_DATE,\n" + 
        "                                                 NUM_LAST_UPDATED_BY\n" + 
        "                                                 )\n" + 
        "                                          VALUES (\n" + 
        "                                          "+tsIdRequest+",\n" + 
        "                                          "+tsIdService+",\n" + 
        "                                          '"+tsBuyuntid+"',\n" + 
        "                                          '"+tsBuylnam+"',\n" + 
        "                                          '"+tsActivo+"',\n" + 
        "                                          '"+tsTarifaProgramada+"',\n" + 
        "                                          '"+tsEstatus+"',\n" + 
        "                                          '"+tsUserName+"',\n" + 
        "                                          CURRENT_TIMESTAMP,\n" + 
        "                                          "+tsIdUser+",\n" + 
        "                                          CURRENT_TIMESTAMP,\n" + 
        "                                          "+tsIdUser+"\n" + 
        "                                          )";
        return lsQuery;
    }

    /**
     * Actualiza en tabla de control para programas
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsBuyuntid
     * @param tsBuylnam
     * @param tsActivo
     * @param tsTarifaProgramada
     * @param tsEstatus
     * @param tsUserName
     * @param tsIdUser
     * @return Integer
     */
    @Override
    public Integer updateEveTvProgramas(String tsIdRequest, 
                                        String tsIdService, 
                                        String tsBuyuntid,                                         
                                        String tsDescNeptuno,
                                        String tsIdError,
                                        String tsEstatus, 
                                        String tsUserName,
                                        String tsIdUser) {
        Integer    liReturn = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = 
            getQueryUpdatePrograms(tsIdRequest, 
                                   tsIdService, 
                                   tsBuyuntid, 
                                   tsDescNeptuno.replaceAll("'", "''"),
                                   tsIdError,
                                   tsEstatus,
                                   tsUserName,
                                   tsIdUser
                                   );
        try {
            Statement loStmt = loCnn.createStatement();
            liReturn = loStmt.executeUpdate(lsQueryParadigm);
        } catch (SQLException loExSql) {
            System.out.println("Error en Update ################"+loExSql.getMessage());
            //loExSql.printStackTrace();
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
     * Obtiene consulta para Actualizar en tabla de control para programas
     * @autor Jorge Luis Bautista Santiago
     * @param tsIdRequest
     * @param tsIdService
     * @param tsBuyuntid
     * @param tsBuylnam
     * @param tsActivo
     * @param tsTarifaProgramada
     * @param tsEstatus
     * @param tsUserName
     * @param tsIdUser
     * @return Integer
     */
    @Override
    public String getQueryUpdatePrograms(String tsIdRequest, 
                                         String tsIdService, 
                                         String tsBuyuntid, 
                                         String tsDescNeptuno,
                                         String tsIdError,
                                         String tsEstatus, 
                                         String tsUserName,
                                         String tsIdUser
                                         ) {
        String lsQuery = 
        "UPDATE EVENTAS.EVETV_INT_RST_PROGRAMAS_TAB\n" + 
        "   SET IND_ESTATUS          = '"+tsEstatus+"',\n" + 
        "       IND_DESC_NEPTUNO     = '"+tsDescNeptuno+"',\n" + 
        "       FEC_LAST_UPDATE_DATE = CURRENT_TIMESTAMP,\n" + 
        "       NUM_LAST_UPDATED_BY  = "+tsIdUser+",\n" + 
        "       ATTRIBUTE10          = '"+tsIdError+"',\n" + 
        "       ATTRIBUTE14          = '"+tsUserName+"'\n" + 
        " WHERE ID_REQUEST           = "+tsIdRequest+"\n" + 
        "   AND ID_SERVICE           = "+tsIdService+"\n" + 
        "   AND BUYUNTID             = '"+tsBuyuntid+"'";
        if(tsBuyuntid.equalsIgnoreCase("ALL_ERROR")){
            lsQuery = 
                    "UPDATE EVENTAS.EVETV_INT_RST_PROGRAMAS_TAB\n" + 
                    "   SET IND_ESTATUS          = '"+tsEstatus+"',\n" + 
                    "       IND_DESC_NEPTUNO     = '"+tsDescNeptuno+"',\n" + 
                    "       FEC_LAST_UPDATE_DATE = CURRENT_TIMESTAMP,\n" + 
                    "       NUM_LAST_UPDATED_BY  = "+tsIdUser+",\n" + 
                    "       ATTRIBUTE10          = '"+tsIdError+"',\n" + 
                    "       ATTRIBUTE14          = '"+tsUserName+"'\n" + 
                    " WHERE ID_REQUEST           = "+tsIdRequest+"\n" + 
                    "   AND ID_SERVICE           = "+tsIdService;
        }
        return lsQuery;
    }

    @Override
    public Integer updateProgramsParadigm(String psBuyuntid, String psStatus) {
        Integer    liReturn = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = 
            getQueryUpdProgramsParadigm(psBuyuntid, psStatus);
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

    @Override
    public String getQueryUpdProgramsParadigm(String psBuyuntid, String psStatus) {
        String lsQuery = "UPDATE EVENTAS.EVETV_PROGRAMAS\n" + 
        "   SET STATUS = " + psStatus + "\n" + 
        " WHERE TRIM(BUYUNTID) = '" + psBuyuntid + "'";
        return lsQuery;
    }
    
    public Integer insertTransaction(){
        Integer    liReturn = 0;
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = "INSERT INTO EVENTAS.EVETV_INT_RST_PROGRAMAS_TAB (ID_REQUEST,\n" + 
        "                                                  ID_SERVICE,\n" + 
        "                                                  BUYUNTID,\n" + 
        "                                                  BUYLNAM,\n" + 
        "                                                  IND_DESC_NEPTUNO,\n" + 
        "                                                  FEC_CREATION_DATE,\n" + 
        "                                                  NUM_CREATED_BY,\n" + 
        "                                                  FEC_LAST_UPDATE_DATE,\n" + 
        "                                                  NUM_LAST_UPDATED_BY\n" + 
        "                                                 )\n" + 
        "                                          VALUES (1,\n" + 
        "                                                  2,\n" + 
        "                                                  'BAMBI',\n" + 
        "                                                  'ES UN VENADO',\n" + 
        "                                                  'TEST TRX',\n" + 
        "                                                  CURRENT_TIMESTAMP,\n" + 
        "                                                  1,\n" + 
        "                                                  CURRENT_TIMESTAMP,\n" + 
        "                                                  1\n" + 
        "                                                 )";
        
        
        try {
            loCnn.setAutoCommit(false);
            Statement loStmt = loCnn.createStatement();
            liReturn = loStmt.executeUpdate(lsQueryParadigm);
            if(1 == 2){
                loCnn.commit();    
            }else{
                loCnn.rollback();
            }
            
        } catch (SQLException loExSql) {
            loExSql.printStackTrace();
            try {
                loCnn.rollback();
            } catch (SQLException e) {
                ;
            }
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
}
