/**
* Project: Integraton Paradigm - EveTV
*
* File: TargetsDao.java
*
* Created on: Enero 23, 2019 at 11:00
*
* Copyright (c) - OMW - 2019
*/
package com.televisa.comer.integration.model.daos;

import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.beans.RsstTargetsBean;
import com.televisa.comer.integration.model.connection.ConnectionAs400;
import com.televisa.comer.integration.model.interfaces.TargetsInterfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/** Objeto que accede a base de datos para consultar Targets
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Enero 23, 2019, 12:00 pm
 */
public class TargetsDao implements TargetsInterfaces {
    public TargetsDao() {
        super();
    }

    /**
     * Obtiene la lista de targets guardados en la tabla auxiliar
     * @autor Jorge Luis Bautista Santiago
     * @return Lists
     */
    @Override
    public List<RsstTargetsBean> getTargetsFromDataBase() {
        List<RsstTargetsBean> loPrograms = 
            new ArrayList<RsstTargetsBean>();
        Connection              loCnn = new ConnectionAs400().getConnection();
        ResultSet               loRs = null;
        String                  lsQueryParadigm = 
            getQueryTargetsFromDataBase();        
        try {
            Statement loStmt = loCnn.createStatement();
            loRs = loStmt.executeQuery(lsQueryParadigm);  
            while(loRs.next()){
                RsstTargetsBean loTargetsBean = new RsstTargetsBean();             
                loTargetsBean.setLsDescripcion(loRs.getString("DESCRIPCION") == null ? null : 
                                          loRs.getString("DESCRIPCION").trim());
                loTargetsBean.setLsDescripcionCorta(loRs.getString("DESCRIPCION_CORTA") == null ? null :
                                         loRs.getString("DESCRIPCION_CORTA").trim());
                loTargetsBean.setLsDescripcionAudicencia(loRs.getString("DESCRIPCION_AUDICENCIA") == null ? null : 
                                          loRs.getString("DESCRIPCION_AUDICENCIA").trim());
                loTargetsBean.setLsCodigoExt(loRs.getString("CODIGO_EXT") == null ? null : 
                                          loRs.getString("CODIGO_EXT").trim());
                loTargetsBean.setLsStatus(loRs.getString("STATUS") == null ? null : 
                                          loRs.getString("STATUS").trim());
                loPrograms.add(loTargetsBean);
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
     * Regresa cadena para Obtiener la lista de targets guardados 
     * en la tabla auxiliar
     * @autor Jorge Luis Bautista Santiago
     * @return Lists
     */
    @Override
    public String getQueryTargetsFromDataBase() {
        String lsQuery = "SELECT DESCRIPCION,\n" + 
        "       DESCRIPCION_CORTA,\n" + 
        "       DESCRIPCION_AUDICENCIA,\n" + 
        "       CODIGO_EXT,\n" + 
        "       STATUS\n" + 
        "FROM EVENTAS.EVETV_TARGETS";
        return lsQuery;
    }

    @Override
    public ResponseUpdDao updateTargetsParadigm(RsstTargetsBean loRsstTargetsBean) {
        ResponseUpdDao    loReturn = new ResponseUpdDao();
        Connection loCnn = new ConnectionAs400().getConnection();
        String     lsQueryParadigm = 
            getQueryupdateTargetsParadigm(loRsstTargetsBean);
        System.out.println(lsQueryParadigm);
        try {
            Statement loStmt = loCnn.createStatement();
            loReturn.setLiAffected(loStmt.executeUpdate(lsQueryParadigm));
            loReturn.setLsResponse("OK");
            loReturn.setLsMessage("OK");
        } catch (SQLException loExSql) {
            System.out.println("Error en Update ################"+loExSql.getMessage());
            loReturn.setLiAffected(0);
            loReturn.setLsResponse("ERROR");
            loReturn.setLsMessage(loExSql.getMessage());
        }
        finally{
            try {
                loCnn.close();
            } catch (SQLException loEx) {
                loEx.printStackTrace();
            }
        }
        return loReturn;
    }

    @Override
    public String getQueryupdateTargetsParadigm(RsstTargetsBean loRsstTargetsBean) {
        String lsQuery = "UPDATE EVENTAS.EVETV_TARGETS\n" + 
        "   SET STATUS = " + loRsstTargetsBean.getLsStatus() + "\n" + 
        " WHERE CODIGO_EXT = '" + loRsstTargetsBean.getLsCodigoExt() + "'";
        return lsQuery;
    }
}
