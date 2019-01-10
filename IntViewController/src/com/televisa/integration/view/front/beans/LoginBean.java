/**
* Project: Paradigm - eVeTV Integration
*
* File: LoginBean.java
*
* Created on:  Septiembre 6, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package com.televisa.integration.view.front.beans;

import com.televisa.comer.integration.model.beans.RsstLogCertificadoBean;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.model.daos.LogCertificadoDao;
import com.televisa.comer.integration.service.beans.types.EmailDestinationAddress;
import com.televisa.comer.integration.service.email.MailManagement;
import com.televisa.integration.model.AppModuleIntergrationImpl;
import com.televisa.integration.view.front.daos.ViewObjectDao;
import com.televisa.integration.view.front.util.UtilFaces;
import com.televisa.integration.view.secman.SecurityManagerWs;
import com.televisa.integration.view.users.UserInfoBean;
import com.televisa.integration.view.users.UserMenuBean;

import com.tvsa.soin.xmlns.secman.servicios.usuariopermisos.Usuario;

import java.io.IOException;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import java.net.UnknownHostException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.jbo.ApplicationModule;
import oracle.jbo.client.Configuration;

/** Esta clase es un bean que enlaza la pantalla de Login<br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Septiembre 14, 2017, 12:00 pm
 */
public class LoginBean {
    private RichPopup     poPopupExitConfirm;
    private RichInputText poUserName;
    private RichInputText poPassword;

    /**
     * Metodo que dispara el boton de ingresar, valida el usuario y psPassword
     * para la aplicacion de Integracion
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String loginAction() throws IOException {
        String  lsUserName = 
            getPoUserName().getValue() == null ? null : 
            getPoUserName().getValue().toString();
        String  lsPassword = 
            getPoPassword().getValue() == null ? null : 
            getPoPassword().getValue().toString();      
        boolean lbFlagError = false;
        String  lsErrorMessage = null;
        String  lsTokenSecman;
        if (lsUserName != null && lsPassword != null) {
            try {
                //Validar conexion a la bd
                if(validateConnectionBd()){
                
                lsTokenSecman = 
                    validateSecmanUser(lsUserName, lsPassword); 
                    //lsTokenSecman = "123456789"; //TEMPORAL
                if (lsTokenSecman != null) {
                    //Asignacion de permisos 
                    Usuario loUserIntegration = 
                        getSecmanUserPermission(lsUserName);
                    if(loUserIntegration != null){                        
                        //Settear Datos--------------------------
                        FacesContext        loContext = FacesContext.getCurrentInstance();
                        ExternalContext     loEctx = loContext.getExternalContext();        
                        HttpServletRequest  loRequest = (HttpServletRequest)loEctx.getRequest();
                        HttpSession         loSession = loRequest.getSession(true);
                        //loSession.setAttribute("session.logged", "true");
                        loSession.setAttribute("session.integration", "true");
                        //### Settear Usuario Firmado
                        UserInfoBean        loUserInfo = 
                            (UserInfoBean) new UtilFaces().resolveExpression("#{UserInfoBean}");            
                        DateFormat          ldDateFormat = 
                            new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date                ldDate = new Date();                    
                        loUserInfo.setPsUserFullName(loUserIntegration.getNomMostrar().getNomMostrar());
                        loUserInfo.setPsEmail(loUserIntegration.getMailUsuario().getMailUsuario());
                        loUserInfo.setPsDateTimeLogin(ldDateFormat.format(ldDate));
                        loUserInfo.setPsIdUser(loUserIntegration.getIdUsuario().getIdUsuario());
                        loUserInfo.setPsUserName(loUserIntegration.getUserName().getUserName());
                        loUserInfo.setPsToken(lsTokenSecman);
                        loSession.setAttribute("loggedIntegrationUser", loUserInfo.getPsUserName());                             
                        loSession.setAttribute("loggedIntegrationIdUser", loUserInfo.getPsIdUser()); 
                        //### Asignar Operaciones a Usuario Firmado
                        UserMenuBean        loMenu = 
                            (UserMenuBean) new UtilFaces().resolveExpression("#{UserMenuBean}");
                        String              lsFlag = "false";
                        loMenu.setLsRolBitacora(lsFlag);
                        loMenu.setLsRolGeneralParams(lsFlag);
                        loMenu.setLsRolMonitor(lsFlag);
                        loMenu.setLsRolNotificationsConfig(lsFlag);
                        loMenu.setLsRolParametersConfig(lsFlag);
                        loMenu.setLsRolServicesConfig(lsFlag);
                        loMenu.setLsRolTaskConfig(lsFlag);                        
                        loMenu.setLsOprCrud(lsFlag);
                        loMenu.setLsOprDeleteCron(lsFlag);
                        loMenu.setLsOprExecuteCron(lsFlag);
                        loMenu.setLsOprInitStopCron(lsFlag);
                        loMenu.setLsOprInsertCron(lsFlag);
                        loMenu.setLsRolUsrVtaTradicional(lsFlag);
                        
                        List<String>        laOperaciones = 
                            getSecmanUserOperations(lsUserName);
                        for (int liI = 0; liI < laOperaciones.size(); liI++) {
                            lsFlag = "true";
                            if (laOperaciones.get(liI).equalsIgnoreCase("RolBitacora"))
                                loMenu.setLsRolBitacora(lsFlag);
                            
                            if (laOperaciones.get(liI).equalsIgnoreCase("RolGeneralParams"))
                                loMenu.setLsRolGeneralParams(lsFlag);                            
                                                        
                            if (laOperaciones.get(liI).equalsIgnoreCase("RolMonitor"))
                                loMenu.setLsRolMonitor(lsFlag);
                            
                            if (laOperaciones.get(liI).equalsIgnoreCase("RolNotificationsConfig"))
                                loMenu.setLsRolNotificationsConfig(lsFlag);
                            
                            if (laOperaciones.get(liI).equalsIgnoreCase("RolParametersConfig"))
                                loMenu.setLsRolParametersConfig(lsFlag);
                            
                            if (laOperaciones.get(liI).equalsIgnoreCase("RolServicesConfig"))
                                loMenu.setLsRolServicesConfig(lsFlag);
                            
                            if (laOperaciones.get(liI).equalsIgnoreCase("RolTaskConfig"))
                                loMenu.setLsRolTaskConfig(lsFlag);                        
                            
                            if (laOperaciones.get(liI).equalsIgnoreCase("OprCrud"))
                                loMenu.setLsOprCrud(lsFlag);
                            
                            if (laOperaciones.get(liI).equalsIgnoreCase("OprDeleteCron"))
                                loMenu.setLsOprDeleteCron(lsFlag);
                            
                            if (laOperaciones.get(liI).equalsIgnoreCase("OprExecuteCron"))
                                loMenu.setLsOprExecuteCron(lsFlag);
                            
                            if (laOperaciones.get(liI).equalsIgnoreCase("OprInitStopCron"))
                                loMenu.setLsOprInitStopCron(lsFlag);
                            
                            if (laOperaciones.get(liI).equalsIgnoreCase("OprInsertCron"))
                                loMenu.setLsOprInsertCron(lsFlag);
                            
                            if (laOperaciones.get(liI).equalsIgnoreCase("RolUsrVtaTradicional"))
                                loMenu.setLsRolUsrVtaTradicional(lsFlag);                                
                            
                        }
                        //instruccion temporal
                        //loMenu.setLsRolUsrVtaTradicional("true");
                        
                        loSession.setAttribute("RolUsrVtaTradicional", loMenu.getLsRolUsrVtaTradicional()); 
                        
                        String              lsUrl = 
                            loEctx.getRequestContextPath() + "/faces/homePage";
                        loEctx.redirect(lsUrl);
                    }else{
                        lbFlagError = true;
                        lsErrorMessage = "El Usuario No Posee Permisos";
                    }
                }else{
                    lbFlagError = true;
                    lsErrorMessage = "Credenciales Inv\u00E1lidas";
                }
                
                }else{
                    lbFlagError = true;
                    lsErrorMessage = "Sin Conexi\u00F3n a Paradigm";
                }
            } catch (Exception loEx) {
                lbFlagError = true;
                lsErrorMessage = loEx.getMessage();
            }            
        }else{
            lbFlagError = true;
            lsErrorMessage = "Los Campos Son Requeridos";
        }
        if(lbFlagError){
            FacesMessage loMsg = 
                new FacesMessage(lsErrorMessage);
            loMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        }
        return null;
    }
    
    /**
     * Sale de la aplicacion y elimina la session
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String exitAppIntegraion() {
        String              lsAmDef =
            "com.televisa.integration.model.AppModuleIntergrationImpl";
        String              lsConfig = "AppModuleIntergrationLocal";
        ExternalContext     loEctx = 
            FacesContext.getCurrentInstance().getExternalContext();
        String              lsUrl = loEctx.getRequestContextPath() + "/faces/indexPage";
        HttpSession         loSession = (HttpSession) loEctx.getSession(false);
        HttpServletResponse loResponse = (HttpServletResponse) loEctx.getResponse();
        SecurityManagerWs   loSecMan = new SecurityManagerWs();
        try {
            ApplicationModule   loAm =
                Configuration.createRootApplicationModule(lsAmDef, lsConfig);
            AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
            try {                
                String lsUserName = 
                    loService.getValueSessionFromAttribute("loggedIntegrationUser") == null ? null :
                    loService.getValueSessionFromAttribute("loggedIntegrationUser").toString();                
                loSecMan.logoutSecurityManager(lsUserName, "IntegrationEveTv");
            } catch (Exception loEx) {
                System.out.println("Util-ERROR: "+loEx.getMessage());
            } finally {
                Configuration.releaseRootApplicationModule(loAm, true);
                loAm.remove();
            }
        } catch (Exception loEx) {
            ;
        }
        
        loSession.invalidate();
        try {
            loResponse.sendRedirect(lsUrl);
            FacesContext.getCurrentInstance().responseComplete();
        }
        catch (Exception loEx) {
            System.out.println("Error al salir "+loEx.getMessage());
            loEx.printStackTrace();
        }
        return null;
    }

    /**
     * Cancela accion salir de la aplicacion
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String cancelExitAppIntegraion() {
        new UtilFaces().hidePopup(getPoPopupExitConfirm());
        return null;
    }

    /**
     * Limpia campos de captura en pantalla
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String clearAction() {
        getPoUserName().setValue(null);
        getPoPassword().setValue(null);
        return null;
    }
    
    /**
     * Valida usuario y password en Security Manager y verifica <br>
     * si el usuario tiene permiso de acceder con la aplicacion deseada
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String validateSecmanUser(String tsUserName, 
                                     String tsPassword
                                    ) throws Exception {
        String            lsResponse = null;        
        SecurityManagerWs loSecMan = new SecurityManagerWs();
        try {
            lsResponse = 
                loSecMan.loginSecurityManager(tsUserName,
                                              tsPassword, 
                                              "IntegrationEveTv");

        } catch (Exception loEx) {
            throw new Exception(loEx.getMessage());
        }
        return lsResponse;
    }
    
    /**
     * Obtiene permisos de Usuario en Security Manager 
     * @autor Jorge Luis Bautista Santiago
     * @return Usuario
     */
    public Usuario getSecmanUserPermission(String tsUserName) 
    throws Exception {
        Usuario          lsResponse = null;        
        SecurityManagerWs loSecMan = new SecurityManagerWs();
        try {
            lsResponse = 
                loSecMan.getSecmanUserDataSession(tsUserName,
                                              "IntegrationEveTv");
        } catch (Exception loEx) {
            throw new Exception(loEx.getMessage());
        }
        return lsResponse;
    }
    
    /**
     * Obtiene operaciones de Usuario en Security Manager 
     * @autor Jorge Luis Bautista Santiago
     * @return List
     */
    public List<String> getSecmanUserOperations(String tsUserName) throws Exception {
        List<String>  lsResponse = new ArrayList<String>();        
        SecurityManagerWs loSecMan = new SecurityManagerWs();
        try {
            lsResponse = 
                loSecMan.getUserOperations(tsUserName,
                                          "IntegrationEveTv");
        } catch (Exception loEx) {
            throw new Exception(loEx.getMessage());
        }
        return lsResponse;
    }
    
    public boolean validateConnectionBd(){
        boolean lbResponse = true;
        ViewObjectDao loViewObjectDao = new ViewObjectDao();
        if(loViewObjectDao.getCountGeneralParam() < 0){
            lbResponse = false;
        }
        return lbResponse;
    }
    
    
    public String testAction(){
        System.out.println("Dentro de test");
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();        
            System.out.println("IP Local :"+address.getHostAddress());
            System.out.println("CanonicalHostName :"+address.getCanonicalHostName());
            System.out.println("HostName :"+address.getHostName());
            
        } catch (UnknownHostException loEx) {
            System.out.println("Error: "+loEx.getMessage());
        }
        return null;
    }
    
    public String testAction2(){
        System.out.println("Dentro de test - Enviar correo");
        try {
            String lsIdServiceReq = "20";
            Integer liRequest = 0;
            MailManagement loMailManagement = new MailManagement();
            EntityMappedDao loEntityMappedDao = new EntityMappedDao();
            System.out.println("Invocando MailManagement - sendEmailLogCertificado");
            String lsSubject = new UtilFaces().getConfigParameterByName("SubjectLogCertificado");
            System.out.println("lsSubject["+lsSubject+"]");
            String lsTypeMail = "MAIL_OK";
            System.out.println("lsTypeMail["+lsTypeMail+"]");
            List<EmailDestinationAddress> toEmailDestinationAddress = 
                loEntityMappedDao.getDestinationAddress(lsIdServiceReq, lsTypeMail);
            System.out.println("Destinatarios["+toEmailDestinationAddress.size()+"]");
            for(EmailDestinationAddress toMail : toEmailDestinationAddress){
                System.out.println("Destinatario["+toMail.getLsAddressTo()+"]["+toMail.getLsNameTo()+"]");
            }
            String lsDateYyyyMmDd = "2018-04-15";
            String lsChannelTmp = "9CAN";
            System.out.println("CANAL["+lsChannelTmp+"] FECHA["+lsDateYyyyMmDd+"]");
            LogCertificadoDao loLcertDao = new LogCertificadoDao();
            List<RsstLogCertificadoBean> laLogsCertificados = 
            loLcertDao.getLogCertificadoFromParadigmTMP(lsDateYyyyMmDd,//lsDate, 
                                                     lsChannelTmp);            
            boolean lbFlagMail = loMailManagement.sendEmailLogCertificado(lsSubject, 
                                                     toEmailDestinationAddress, 
                                                     laLogsCertificados,
                                                     liRequest,
                                                     Integer.parseInt(lsIdServiceReq)
                                                    );
            
            if(lbFlagMail){
                System.out.println("Correo enviado OK");
                FacesMessage loMsg;
                loMsg = new FacesMessage("Correo enviado satisfactoriamente");
                loMsg.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, loMsg);
            }else{
                System.out.println("Fallo al enviar correo");
                FacesMessage loMsg;
                loMsg = new FacesMessage("Error al enviar correos ");
                loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
                FacesContext.getCurrentInstance().addMessage(null, loMsg);
            }
            
        } catch (Exception loEx) {
            System.out.println("Error: "+loEx.getMessage());
            FacesMessage loMsg;
            loMsg = new FacesMessage("Error de Comunicacion " + loEx.getMessage());
            loMsg.setSeverity(FacesMessage.SEVERITY_FATAL);
            FacesContext.getCurrentInstance().addMessage(null, loMsg);
        }
        return null;
    }
    
    /****************** SETTERS AND GETTERS ******************************/    

    public void setPoPopupExitConfirm(RichPopup poPopupExitConfirm) {
        this.poPopupExitConfirm = poPopupExitConfirm;
    }

    public RichPopup getPoPopupExitConfirm() {
        return poPopupExitConfirm;
    }

    public void setPoUserName(RichInputText poUserName) {
        this.poUserName = poUserName;
    }

    public RichInputText getPoUserName() {
        return poUserName;
    }

    public void setPoPassword(RichInputText poPassword) {
        this.poPassword = poPassword;
    }

    public RichInputText getPoPassword() {
        return poPassword;
    }
    
}
