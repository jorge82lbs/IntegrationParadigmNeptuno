package com.televisa.comer.integration.service.email;

import com.televisa.comer.integration.model.beans.RsstLogCertificadoBean;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.service.beans.types.EmailDestinationAddress;

import com.televisa.integration.view.front.util.UtilFaces;

import com.tvsa.soin.xmlns.secman.servicios.secmaninfsendmail.Mail;
import com.tvsa.soin.xmlns.secman.servicios.secmaninfsendmail.MailAddress;
import com.tvsa.soin.xmlns.secman.servicios.secmaninfsendmail.MailAddressCollection;
import com.tvsa.soin.xmlns.secman.servicios.secmaninfsendmail.MailBody;

import com.tvsa.soin.xmlns.secman.servicios.secmaninfsendmail.MailHeader;

import java.io.FileWriter;
import java.io.PrintWriter;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;

import org.tempuri.SecmanDasEnviarCorreo;
import org.tempuri.SecmanDasEnviarCorreo_Service;

import weblogic.wsee.security.unt.ClientUNTCredentialProvider;

import weblogic.xml.crypto.wss.WSSecurityContext;
import weblogic.xml.crypto.wss.provider.CredentialProvider;

public class MailManagement {
    public MailManagement() {
        super();
    }
    
    @WebServiceRef
    private static SecmanDasEnviarCorreo_Service loSecmanDasEnviarCorreo;
    
    /**
     * Obtiene dinamicamente la url del servicio web de correo
     * @autor Jorge Luis Bautista Santiago
     * @return SecmanDasEnviarCorreo
     */
    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
    public SecmanDasEnviarCorreo getDynaWebServiceURLEmail(){
        SecmanDasEnviarCorreo loSecmanDasSendMail = null;
        loSecmanDasEnviarCorreo =
                new SecmanDasEnviarCorreo_Service();
        QName                 loQname = loSecmanDasEnviarCorreo.getServiceName();
        URL                   loWsdlDocLocation;
        EntityMappedDao       loEntityMappedDao = new EntityMappedDao();
        String                lsName = "WsSecmanMail";
        String                lsUsedBy = "SECMAN_WS";
        String                lsDynaUrl = loEntityMappedDao.getGeneralParameter(lsName, lsUsedBy);
        if(!lsDynaUrl.equalsIgnoreCase("")){
            try{
                System.out.println("WSDL "+lsDynaUrl);
                loWsdlDocLocation = new URL(lsDynaUrl);
                loSecmanDasEnviarCorreo.create(loWsdlDocLocation, loQname);
                loSecmanDasSendMail = loSecmanDasEnviarCorreo.getSecmanDasEnviarCorreoSoap12HttpPort();    
            } catch (MalformedURLException loEx) {
                System.out.println("01"+loEx.getMessage());
            }catch(Exception loEx){
                System.out.println("02"+loEx.getMessage());
            }
        }else{
            System.out.println("No se obtuvo WSDL del Servicio de Correo");
        }        
        return loSecmanDasSendMail;
    }
    
    /**
     * Metodo que settea credenciales requeridas en secman
     * @autor Jorge Luis Bautista Santiago
     * @param tsRequestContext
     * @return void
     */
    @Generated("Oracle JDeveloper")
    public static void setPortCredentialProviderList(Map<String, Object> tsRequestContext) 
    throws Exception {    
        String                   lsUsername = "osbusrkey";
        String                   lsPassword = "password1";
        List<CredentialProvider> laCredList = 
            new ArrayList<CredentialProvider>();
        laCredList.add(getUNTCredentialProvider(lsUsername, lsPassword));
        tsRequestContext.put(WSSecurityContext.CREDENTIAL_PROVIDER_LIST, 
                             laCredList);
    }
    
    /**
     * Agrega al provider el usuario y contraseña requeridas en secman
     * @autor Jorge Luis Bautista Santiago
     * @param tsUsername
     * @param tsPassword
     * @return CredentialProvider
     */
    @Generated("Oracle JDeveloper")
    public static CredentialProvider getUNTCredentialProvider(String tsUsername,
                                                              String tsPassword) {
        return new ClientUNTCredentialProvider(tsUsername.getBytes(), 
                                               tsPassword.getBytes());
    }
    
    /**
     * Envia correo correspondiente al resultado de Log Comercial
     * @autor Jorge Luis Bautista Santiago
     * @param tsSubject
     * @param toEmailDestinationAddress
     * @param toOrderModulosBean
     * @return boolean
     */
    public boolean sendEmailLogCertificado(String tsSubject, 
                                         List<EmailDestinationAddress> toEmailDestinationAddress,
                                         List<RsstLogCertificadoBean> taRstLogComercialAll,
                                           Integer liIdRequest, Integer liIdService
                                        ) {
        boolean lbResponse = false;
        try{
            String lsHtml =
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-" +
                "strict.dtd\">\n" + 
                "       <html xmlns=\"http://www.w3.org/1999/xhtml\" dir=\"ltr\" lang=\"es-ES\">\n" + 
                "               <head>\n" + 
                "            <title>Log Certificado</title>\n" + 
                "            <meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\" />\n" + 
                "            <meta name=\"language\" content=\"es\" />\n" + 
                "        </head>\n" + 
                "        <body>\n" + 
                "            <div style='background-color:#0F2F62; height:25px;background:linear-gradient(#040545, " +
                "#0172AE);background:-webkit-linear-gradient(#040545, #0172AE);background: -ms-linear-background(" +
                "#040545, #0172AE);filter:progid:DXImageTransform.Microsoft.gradient(startColorStr='#040545', " +
                "EndColorStr='#0172AE');'></div>\n" + 
                "            <div style='background-color:#fff'>             \n" + 
                "            <br/>\n" + 
                "            <label style='font-family:Arial, Helvetica, sans-serif; font-size:16px; font-weight:" +
                "bold '>El Proceso de Integraci&oacute;n para Log Certificado ha Finalizado</label><p/>\n" + 
                "                       <label style='font-family:Arial, Helvetica, sans-serif; font-size:12px; " +
                "font-weight:bold '>A continuaci&oacuten se encuentra el detalle del Resultado</label><p/>\n" + 
                "            <table cellpadding='0' cellspacing='0'  style='border-bottom-color:#F79646;border-" +
                "bottom-width:3px;border-bottom-style:solid;border-top-color:#F79646;border-top-style:solid;border-" +
                "top-width:3px; '>             \n" + 
                "                               <tr>\n" + 
            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Bcsttim</th>\n" + 
            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Channelid</th>\n" + 
            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Date</th>\n" + 
            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Orderid</th>\n" + 
            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Spotid</th>\n" + 
            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Buyuntid</th>\n" + 
            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Breakid</th>\n" + 
            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Hour</th>\n" + 
            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Duration</th>\n" + 
            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Spotformatid</th>\n" + 
            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Mediaid</th>\n" + 
            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Productid</th>\n" + 
            "                                       <th height='10px' style='font-size:12px; background:#276A7C; " +
                "font-family:Arial, Helvetica, sans-serif;color:#FFFFFF;padding:3px;'>Spotprice</th>\n" +                "                               </tr>\n";
            for(RsstLogCertificadoBean loLogCertificadoBean : taRstLogComercialAll){            
                lsHtml += 
                "                               <tr>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;font-family:" +
                    "Arial, Helvetica, sans-serif; padding:3px;'> </td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;font-family:" +
                    "Arial, Helvetica, sans-serif; padding:3px;'>"+loLogCertificadoBean.getLsChannelid()+"</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;font-family:" +
                    "Arial, Helvetica, sans-serif; padding:3px;'>"+loLogCertificadoBean.getLsDate()+"</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;font-family:" +
                    "Arial, Helvetica, sans-serif; padding:3px;'>"+loLogCertificadoBean.getLsOrderid()+"</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;font-family:" +
                    "Arial, Helvetica, sans-serif; padding:3px;'>"+loLogCertificadoBean.getLsSpotid()+"</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;font-family:" +
                    "Arial, Helvetica, sans-serif; padding:3px;'>"+loLogCertificadoBean.getLsBuyunitid()+"</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;font-family:" +
                    "Arial, Helvetica, sans-serif; padding:3px;'>"+loLogCertificadoBean.getLsBreakid()+"</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;font-family:" +
                    "Arial, Helvetica, sans-serif; padding:3px;'>"+loLogCertificadoBean.getLsHour()+"</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;font-family:" +
                    "Arial, Helvetica, sans-serif; padding:3px;'>"+loLogCertificadoBean.getLsDuration()+"</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;font-family:" +
                    "Arial, Helvetica, sans-serif; padding:3px;'>"+loLogCertificadoBean.getLsSpotformatid()+"</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;font-family:" +
                    "Arial, Helvetica, sans-serif; padding:3px;'>"+loLogCertificadoBean.getLsMediaid()+"</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;font-family:" +
                    "Arial, Helvetica, sans-serif; padding:3px;'>"+loLogCertificadoBean.getLsProductid()+"</td>\n" + 
                "                                       <td style='font-size:12px; background:#B6DDE8;font-family:" +
                    "Arial, Helvetica, sans-serif; padding:3px;'>"+loLogCertificadoBean.getLsSpotprice()+"</td>\n" + 
                "                               </tr>\n";
            }
                lsHtml += "            </table>             \n" + 
                "            <br/>           \n" + 
                "            <br/>\n" + 
                "            <label style='font-family:Arial, Helvetica, sans-serif; font-size:12px; font-weight:" +
                    "bold '>Integraci&oacute;n Paradigm-Neptuno, 2017 Televisa S.A. de C.V</label>            \n" + 
                "            <br/>\n" + 
                "            </div>\n" + 
                "            <div style='float:left; background-color:#F58220;width:7%;height:25px;background:-moz-" +
                    "linear-gradient(#F1A345, #E17521); background:-webkit-linear-gradient(#F1A345, #E17521);" +
            "background: -ms-linear-background(#F1A345, #E17521);filter:progid:DXImageTransform.Microsoft.gradient(" +
                    "startColorStr='#F1A345', EndColorStr='#E17521');'>\n" + 
                "                       </div>             \n" + 
                "            <div style='float:left; ;width:86%;height:25px;background:linear-gradient(#040545, " +
                    "#0172AE);background:-webkit-linear-gradient(#040545, #0172AE);background: -ms-linear-" +
                    "background(#040545, #0172AE);filter:progid:DXImageTransform.Microsoft.gradient(startColorStr=" +
                    "'#040545', EndColorStr='#0172AE');'>\n" + 
                "                       </div>\n" + 
                "            <div style='float:left; background-color:#F58220;width:7%;height:25px;background:-" +
                    "moz-linear-gradient(#F1A345, #E17521);background:-webkit-linear-gradient(#F1A345, #E17521);" +
            "background: -ms-linear-background(#F1A345, #E17521);filter:progid:DXImageTransform.Microsoft.gradient(" +
                    "startColorStr='#F1A345', EndColorStr='#E17521');'></div>             \n" + 
                "               </body>\n" + 
                "       </html>";    
            System.out.println("Html generado para el correo ================>");
            //System.out.println(lsHtml);
            //writeFile(lsHtml);
            SecmanDasEnviarCorreo loSecmanDasSendMail = getDynaWebServiceURLEmail();
            if(loSecmanDasSendMail != null){
                System.out.println("loSecmanDasSendMail no es null");
                try{
                    Map<String, Object>   loRequestContext =
                        ((BindingProvider)loSecmanDasSendMail).getRequestContext();                    
                    setPortCredentialProviderList(loRequestContext);                    
                    Mail                  loEmail = new Mail();                    
                    MailHeader            loEmailHeader = new MailHeader();
                    MailAddress           loEmailAddresFrom = new MailAddress();                    
                    loEmailAddresFrom.setUserNameAdress("Paradigm-Neptuno Integration");
                    loEmailAddresFrom.setAddress("service_integration@televisa.com.mx");
                    System.out.println("setteando Address FROM");
                    loEmailHeader.setAddressFrom(loEmailAddresFrom);
                    MailAddressCollection loMailAddressCollection =
                        new MailAddressCollection();
                    System.out.println("setteando AddressTO");
                    for(EmailDestinationAddress loDest: toEmailDestinationAddress){                        
                        MailAddress           loEmailAdd = new MailAddress();
                        //System.out.println("AddressTO username["+loDest.getLsNameTo()+"] address["+loDest.getLsAddressTo()+"]");
                        loEmailAdd.setUserNameAdress(loDest.getLsNameTo());
                        loEmailAdd.setAddress(loDest.getLsAddressTo());    
                        loMailAddressCollection.getMailAddress().add(loEmailAdd);
                    }
                    System.out.println("setteando AddressTO collection");
                    loEmailHeader.setTo(loMailAddressCollection);
                    System.out.println("setteando subject");
                    loEmailHeader.setSubject(tsSubject);
                    System.out.println("setteando MailHeader");
                    loEmail.setMailHeader(loEmailHeader);
                    MailBody              loMailBody = new MailBody();
                    loMailBody.setBodyType("HTML");
                    System.out.println("setteando Mensaje");
                    loMailBody.setMessage(lsHtml);
                    System.out.println("setteando BODY");
                    loEmail.setMailBody(loMailBody);
                    System.out.println("Enviar correo (Secman)");
                    loSecmanDasSendMail.enviarCorreo(loEmail);
                    lbResponse = true;
                    System.out.println("Fin enviar correo - ok");
                } catch (Exception loException) {
                    System.out.println("Error al enviar correo log certifificado: "+loException.getMessage());
                    lbResponse = false;
                    
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                    String lsIdUserNameReq = "0";
                    String lsIdUserReq = "0";
                    String lsIdServiceReq = String.valueOf(liIdService);
                    new UtilFaces().insertBitacoraServiceService(liIdRequest, 
                                                                 Integer.parseInt(lsIdServiceReq), 
                                                                 liIndProcess, 
                                                                 "(sendMail): Error al Enviar Correo Log Certificado " + 
                                                                 loException.getMessage(),
                                                                 0, 
                                                                 0, 
                                                                 Integer.parseInt(lsIdUserReq),
                                                                 lsIdUserNameReq
                                                                 );
                }
            }
        } catch (Exception loException) {
            System.out.println("Error crear instancia del servicio de correo de secman: "+loException.getMessage());
            Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
            String lsIdUserNameReq = "0";
            String lsIdUserReq = "0";
            String lsIdServiceReq = String.valueOf(liIdService);
            new UtilFaces().insertBitacoraServiceService(liIdRequest, 
                                                         Integer.parseInt(lsIdServiceReq), 
                                                         liIndProcess, 
                                                         "(Instancia Secman): Error al Enviar Correo Log Certificado " + 
                                                         loException.getMessage(),
                                                         0, 
                                                         0, 
                                                         Integer.parseInt(lsIdUserReq),
                                                         lsIdUserNameReq
                                                         );
            lbResponse = false;            
        }
        return lbResponse;
    }
    
    public void writeFile(String tsText){
        FileWriter loFile = null;
        PrintWriter loPrWr = null;
        String lsPath = "C:\\Users\\JorgeOWM\\Desktop\\HTML-CORREO.html";
        try
        {
            loFile = new FileWriter(lsPath);
            loPrWr = new PrintWriter(loFile);
            loPrWr.println(tsText);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != loFile)
                    loFile.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
