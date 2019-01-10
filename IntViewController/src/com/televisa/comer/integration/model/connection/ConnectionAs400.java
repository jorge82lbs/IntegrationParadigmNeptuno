/**
* Project: Integraton Paradigm - EveTV
*
* File: ConnectionAs400.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;

import javax.sql.DataSource;

/** Clase para conectar a base de datos, las clases cliente deben instanciar este objeto
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class ConnectionAs400 {
    public ConnectionAs400() {
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
