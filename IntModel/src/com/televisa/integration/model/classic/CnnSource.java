package com.televisa.integration.model.classic;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;

import javax.sql.DataSource;

public class CnnSource {
    public CnnSource() {
        super();
    }
    private String psDataSource = "java:comp/env/jdbc/MOR_DS_PG1";
    
    /**
     * Obtiene Conexion a Base de Datos
     * @autor Jorge Luis Bautista Santiago
     * @return Connection
     */
    public Connection getConnection(){
        Connection loCnn = null;
        InitialContext                      loInitialContext;
        DataSource                          loDs;     
        try{
            
            loInitialContext = new InitialContext();
            loDs =
                (DataSource)loInitialContext.lookup(psDataSource);
            loCnn = loDs.getConnection();
                        
        }catch(SQLException loExSql){
            loExSql.printStackTrace();
            loCnn = null;
        }catch(Exception loEx){
            loEx.printStackTrace();
            loCnn = null;
        }        
        return loCnn;
    }
}
