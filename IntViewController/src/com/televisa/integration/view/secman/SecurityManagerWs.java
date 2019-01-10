/**
* Project: Integration Paradigm-eVeTV
*
* File: SecurityManagerWs.java
*
* Created on:  Septiembre 6, 2013 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package com.televisa.integration.view.secman;

import com.televisa.integration.view.front.util.UtilFaces;

import com.tvsa.soin.xmlns.secman.servicios.secmandascomerautenticar.ProcessResponse;
import com.tvsa.soin.xmlns.secman.servicios.secmandascomerautenticar.UserLogin;
import com.tvsa.soin.xmlns.secman.servicios.usuariopermisos.PermisoOutputCollection;
import com.tvsa.soin.xmlns.secman.servicios.usuariopermisos.PermisosUsuarioInputParameters;

import mx.com.televisa.secman.view.webservices.*;
import com.tvsa.soin.xmlns.secman.servicios.usuariopermisos.Usuario;
import java.net.URL;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.soap.SOAPFaultException;


import mx.com.televisa.secman.view.webservices.SecmanDasOperacionesUsuario;
import mx.com.televisa.secman.view.webservices.SecmanDasOperacionesUsuarioService;

import org.tempuri.SecmanBsAutenticar;
import org.tempuri.SecmanBsAutenticar_Service;

/** Clase que hace uso de los servicios de Security Manager <br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Septiembre 14, 2017, 12:00 pm
 */
public class SecurityManagerWs {
    /**
     * Valida en Security Manager si el usuario y contraseña son permitidos en la
     * Aplicacion de Integracion
     * @autor Jorge Luis Bautista Santiago
     * @param tsUser
     * @param tsPassword
     * @param tsApplicationName
     * @return String
     */
    public String loginSecurityManager(String tsUser, 
                                       String tsPassword, 
                                       String tsApplicationName
                                      ) throws Exception {
        String    lsToken = null;
        String    lsUser = "";
        String    lsPassword = "";
        UtilFaces loUf = new UtilFaces();
        try {
            String lsKey= loUf.getKeyDecoder();
            lsUser = loUf.encryptObject(tsUser,lsKey);
            lsPassword = loUf.encryptObject(tsPassword, lsKey);
        } catch (Exception loEx) {
            lsToken = null;
        }
        SecmanBsAutenticar_Service loSecmanBsAuthService = 
            new SecmanBsAutenticar_Service();
   
        try{
          
            SecmanBsAutenticar         loSecmanBsAuth = 
                loSecmanBsAuthService.getSecmanBsAutenticarSoap12HttpPort();
            UserLogin                  loUserLogin = new UserLogin();
            loUserLogin.setUserName(lsUser);
            loUserLogin.setUserPassword(lsPassword);
            loUserLogin.setNomAplicacion(tsApplicationName);
            loUserLogin.setAccion("login");          
            loUserLogin.setToken("");
            ProcessResponse            loProcessResponse;
            loProcessResponse = loSecmanBsAuth.autenticarUsuario(loUserLogin);
            if (loProcessResponse.getResult().startsWith("FAILED")){                
                throw new Exception (loProcessResponse.getError());
            }
            else{
                lsToken = loProcessResponse.getResult();                
            }    
        }catch(SOAPFaultException loEx){
            ;
        } catch (Exception loEx) {
            throw new Exception (loEx.getMessage());
        }                        
        return lsToken;
    }
    
    /**
     * Valida en Security Manager si el usuario y contraseña son permitidos en la
     * Aplicacion de Integracion
     * @autor Jorge Luis Bautista Santiago
     * @param tsUser
     * @param psPassword
     * @param tsApplicationName
     * @return void
     */
    public void logoutSecurityManager(String tsUser, 
                                      String tsApplicationName
                                      ) throws Exception {    
        String    lsUser = "";
        UtilFaces loUf = new UtilFaces();
        try {
            String lsKey= loUf.getKeyDecoder();
            lsUser = loUf.encryptObject(tsUser,lsKey);
        } catch (Exception loEx) {
            ;
        }

        SecmanBsAutenticar_Service loSecmanBsAuthService = 
            new SecmanBsAutenticar_Service();
        try{
            SecmanBsAutenticar     loSecmanBsAuth = 
                loSecmanBsAuthService.getSecmanBsAutenticarSoap12HttpPort();
            UserLogin              loUserLogin = new UserLogin();
            loUserLogin.setUserName(lsUser);
            loUserLogin.setNomAplicacion(tsApplicationName);
            loUserLogin.setAccion("logout");          
            loUserLogin.setToken("");
            ProcessResponse        loProcessResponse;
            loProcessResponse = loSecmanBsAuth.autenticarUsuario(loUserLogin);
            if (loProcessResponse.getResult().startsWith("FAILED")){                
                throw new Exception (loProcessResponse.getError());
            }   
        }catch(SOAPFaultException loEx){
            ;
        } catch (Exception loEx) {
            throw new Exception (loEx.getMessage());
        }                        
    }
    
    /**
     * Valida los permisos del usuario en la aplicacion 
     * Aplicacion de Integracion
     * @autor Jorge Luis Bautista Santiago
     * @param tsUserName
     * @param toApplication
     * @return void
     */
    @WebServiceRef
    private static SecmanDasUsuarioPermisosService loSmnUserPermissionService;
    
    public Usuario getSecmanUserDataSession(String tsUserName,
                                            String toApplication){
        Usuario loSecmanUser = new Usuario();
        loSmnUserPermissionService = new SecmanDasUsuarioPermisosService();
        try{
    
            SecmanDasUsuarioPermisos       loSmsUserPermission = 
                loSmnUserPermissionService.getSecmanDasUsuarioPermisosPort();
                
            PermisosUsuarioInputParameters loUserInput = new PermisosUsuarioInputParameters();
            loUserInput.setNomAplicacion(toApplication);
            loUserInput.setUserName(tsUserName);
            PermisoOutputCollection        loUserOutput = 
                loSmsUserPermission.obtenerRolTareaPermisos(loUserInput);
            loSecmanUser = loUserOutput.getUsuario();
        }catch(Exception loEx){
            System.out.println("ERROR " + loEx.getMessage());
            loSecmanUser = null;
        }
        return loSecmanUser;
    }
    
    /**
     * Devuelve la lista de operaciones que tiene el usuario en la aplicacion solicitada
     * @autor Jorge Luis Bautista Santiago
     * @param tsUserName
     * @param tsApplication
     * @return List
    */  
    @WebServiceRef
    private static SecmanDasOperacionesUsuarioService poSecmanDasOperacionesUsuarioService;    
    public List<String> getUserOperations(String tsUserName, 
                                          String tsApplication
                                          ) {
        List<String> laOperations = new ArrayList<String>();
        try {
            poSecmanDasOperacionesUsuarioService = 
                new SecmanDasOperacionesUsuarioService();
            SecmanDasOperacionesUsuario              loSecmanDasOperacionesUsuario = 
                poSecmanDasOperacionesUsuarioService.getSecmanDasOperacionesUsuarioPort();
            
            com.tvsa.soin.xmlns.secman.servicios.usuariopermisos.PermisosUsuarioInputParameters     loUserPermissions = 
                new com.tvsa.soin.xmlns.secman.servicios.usuariopermisos.PermisosUsuarioInputParameters();
            
            loUserPermissions.setNomAplicacion(tsApplication);
            loUserPermissions.setUserName(tsUserName);
            
            com.tvsa.soin.xmlns.secman.servicios.usuariopermisos.OperacionesUsuarioOutputParameters loUserOperations = 
                loSecmanDasOperacionesUsuario.obtenerOperacionesUsuario(loUserPermissions);
            com.tvsa.soin.xmlns.secman.servicios.usuariopermisos.OperacionesCollection              loOpCollection = 
                loUserOperations.getOperacionesCollection();
            Iterator                                                                                     loIterator;
            loIterator = loOpCollection.getOperacion().iterator();
            int                                                                                          liI = 0;
            while (loIterator.hasNext()) {
                com.tvsa.soin.xmlns.secman.servicios.usuariopermisos.Operacion psOperacion = 
                    (com.tvsa.soin.xmlns.secman.servicios.usuariopermisos.Operacion)loIterator.next();
                laOperations.add(psOperacion.getOperacionName().getOperacionName());
                liI++;
            }
        } catch (Exception loEx) {
            System.out.println ("Error al obtener Operaciones "+loEx.getMessage());
            ;
        }
        return laOperations;
    }
}
