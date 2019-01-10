package com.televisa.integration.model.classic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CnnSourceImpl {
    public CnnSourceImpl() {
        super();
    }
    
    public String getStatusCronByIdService(Integer tiIdService) {
        String      loValue = "";
        Connection  loCnn = new CnnSource().getConnection();
        ResultSet   loRs = null;
        String      lsQueryParadigm = "SELECT IND_ESTATUS\n" + 
        "  FROM EVENTAS.EVETV_INT_CRON_CONFIG_TAB\n" + 
        " WHERE ID_SERVICE = "+tiIdService;
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
}
