 /**
 * Project: Integraton Paradigm - EveTV
 *
 * File: IntegrationDasLogCertificado.java
 *
 * Created on: Septiembre 23, 2017 at 11:00
 *
 * Copyright (c) - OMW - 2017
 */
package com.televisa.comer.integration.service.implementation;

import com.televisa.comer.integration.model.beans.RsstLogCertificadoBean;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.model.daos.LogCertificadoDao;
import com.televisa.comer.integration.service.beans.logcertificado.ChannelLcert;
import com.televisa.comer.integration.service.beans.logcertificado.LogCertificadoInputParameters;
import com.televisa.comer.integration.service.beans.logcertificado.LogCertificadoResponse;
import com.televisa.comer.integration.service.beans.logcertificado.XmlMessageLcertReqRes;
import com.televisa.comer.integration.service.beans.logcertificado.XmlMessageLcertResponseCollection;
import com.televisa.comer.integration.service.beans.types.EmailDestinationAddress;
import com.televisa.comer.integration.service.beans.types.EvetvLogCertificadoProcesadoBean;
import com.televisa.comer.integration.service.email.MailManagement;
import com.televisa.comer.integration.service.interfaces.LogCertificadoInterface;
import com.televisa.comer.integration.service.utils.UtilsIntegrationService;
import com.televisa.comer.integration.service.xmlutils.HeaderHandlerResolver;
import com.televisa.integration.view.front.util.UtilFaces;

import es.com.evendor.RecibirDatosExternos;
import es.com.evendor.RecibirDatosExternosResponse;
import es.com.evendor.WSNeptuno;
import es.com.evendor.WSNeptunoSoap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;

import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** Esta clase extrae informacion de Paradigm y consume servicio
 * de log certificado expuesto por eVeTV <br/><br/>
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class IntegrationDasLogCertificado implements LogCertificadoInterface{

    /**
     * Obtiene parametros e invoca servicio de Log Certificado
     * @autor Jorge Luis Bautista Santiago
     * @param toLogCertificado
     * @return LogCertificadoResponse
     */     
    @Override
    @WebMethod
    public LogCertificadoResponse invokeLogCertificado(LogCertificadoInputParameters toLogCertificado) {
        LogCertificadoResponse            loLcertRes = new LogCertificadoResponse();
        String                            lsEmailMsgErr = "";
        String                            lsIdRequestLcertReq = toLogCertificado.getIdRequestLogCertificadoReq();
        XmlMessageLcertResponseCollection loXmlMsCol = new XmlMessageLcertResponseCollection();
        String                            lsDate = toLogCertificado.getDateQueryLcert();
        String                            lsIdServiceReq = toLogCertificado.getIdService();
        String                            lsIdUserNameReq = toLogCertificado.getUserName();
        String                            lsIdUserReq = toLogCertificado.getIdUser();
        String                            lsChannelAll = "";
        String                            lsChannelTmp = null;
        Integer                           liFlag = 0;
        Integer                           liIndProcess = 0;
        LogCertificadoDao                 loLcertDao = new LogCertificadoDao();
        List<ChannelLcert>                laChnllCol = toLogCertificado.getChannelLcertList().getChannelsLcert();
        EntityMappedDao                   loEntityMappedDao = new EntityMappedDao();
        Integer                           liRequest = Integer.parseInt(lsIdRequestLcertReq);
        
        for(ChannelLcert loChannel: laChnllCol){            
            lsChannelAll = lsChannelAll + "'" + loChannel.getChannelLcert() + "',";
            lsChannelTmp = loChannel.getChannelLcert();
        }
        
        String lsKey = lsChannelTmp + "-" + lsDate;        
        //Validar RECON COMPLETE
        //20180103.- Para insertar en parrillas procesada tomar fecha de BD Int
        liFlag = 
            loLcertDao.getFlagInsertLogCertificado(lsDate, 
                                                   lsChannelTmp
                                                   );     
        liIndProcess = new UtilFaces().getIdConfigParameterByName("FlagLcertReconComplete");
        new UtilFaces().insertBitacoraServiceService(liRequest, 
                                                     Integer.parseInt(lsIdServiceReq), 
                                                     liIndProcess, 
                                                     lsKey + ": Bandera Log Certificado RECON COMPLETE[" + liFlag + "]",
                                                     0, 
                                                     liRequest, 
                                                     Integer.parseInt(lsIdUserReq),
                                                     lsIdUserNameReq
                                                     );   
        if(liFlag > 0){   
        //if(true){   
            //System.out.println("Temporalmente deshabilitado flagReaconComplete");
            //20180103 Para obtener lista de la tabla
            List<EvetvLogCertificadoProcesadoBean> loLcertProcesados = 
                loLcertDao.getLogCertificadoProcesados(lsChannelTmp, lsDate);
            
            for(EvetvLogCertificadoProcesadoBean lCert : loLcertProcesados){
                
                String lsDateYyyyMmDd = buildDateYYYYMMDD(lCert.getLsBcstdt(), "MM/DD/YY");  
                lsKey = lsChannelTmp + "-" + lsDateYyyyMmDd;
            
                try {
                    //Set respuesta:
                    loLcertRes.setIdRequestLogCertificadoRes(String.valueOf(liRequest));
                    //Crear Documento XML
                    DocumentBuilderFactory loDocFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder        loDocBuilder = loDocFactory.newDocumentBuilder();
                    Document               loDocument = loDocBuilder.newDocument();
                    Element                loRoot = loDocument.createElement("RAIZ");
                    Element                loCommercialLog = null;
                    loCommercialLog = loDocument.createElement("CommercialLogTransmision");
                    for(ChannelLcert loChannel: laChnllCol){
                        lsChannelTmp = loChannel.getChannelLcert();
                        //20180103.- Para EXTRACCION tomar fecha de la lista
                        List<RsstLogCertificadoBean> laLogsCertificados = 
                        loLcertDao.getLogCertificadoFromParadigm(lsDateYyyyMmDd,//lsDate, 
                                                                 lsChannelTmp);
                        
                        liIndProcess = new UtilFaces().getIdConfigParameterByName("ExtractData");
                        new UtilFaces().insertBitacoraServiceService(liRequest, 
                                                                     Integer.parseInt(lsIdServiceReq), 
                                                                     liIndProcess, 
                                                                     lsKey + ": Extraccion de Informacion para Servicio de " +
                                                                     "Log Certificado",
                                                                     0, 
                                                                     liRequest, 
                                                                     Integer.parseInt(lsIdUserReq),
                                                                     lsIdUserNameReq
                                                                     );     
                        //############ Insert en tabla de control de omw ################## 
                        if(laLogsCertificados.size() > 0){
                            for(RsstLogCertificadoBean loLogCertificado: laLogsCertificados){
                                loLcertDao.insertLogCertificadoCtrl(String.valueOf(liRequest), 
                                                                    lsIdServiceReq, 
                                                                    lsIdUserNameReq, 
                                                                    lsIdUserReq, 
                                                                    loLogCertificado);
                            }
                        }
                        
                        Element loChannelIdHead = 
                            loDocument.createElement("ChannelID");
                        loChannelIdHead.appendChild(loDocument.createTextNode(lsChannelTmp));
                        Element loDateHead = loDocument.createElement("Date");
                        //loDateHead.appendChild(loDocument.createTextNode(lsDate));   
                        //20180103 En el request se settea la fecha de la lista
                        loDateHead.appendChild(loDocument.createTextNode(lsDateYyyyMmDd));   
                        
                        //JACOBOloDateHead.appendChild(loDocument.createTextNode(lsDateNeptuno));                    
                        loCommercialLog.appendChild(loChannelIdHead);
                        loCommercialLog.appendChild(loDateHead);
                        if(laLogsCertificados.size() > 0){
                            for(RsstLogCertificadoBean loLogCertificado: laLogsCertificados){
                                Element loSpot = loDocument.createElement("Spot");
                                Element loIndAction = loDocument.createElement("Action");
                                loIndAction.appendChild(loDocument.createTextNode("I"));                                
                                Element loOrderID = loDocument.createElement("OrderID");
                                loOrderID.appendChild(loDocument.createTextNode(loLogCertificado.getLsOrderid()));                                
                                Element loSpotID = loDocument.createElement("SpotID");
                                loSpotID.appendChild(loDocument.createTextNode(loLogCertificado.getLsSpotid()));                               
                                Element loBuyUnitID = loDocument.createElement("BuyUnitID");
                                loBuyUnitID.appendChild(loDocument.createTextNode(loLogCertificado.getLsBuyunitid()));                               
                                Element loBreakID = loDocument.createElement("BreakID");
                                loBreakID.appendChild(loDocument.createTextNode(loLogCertificado.getLsBreakid()));                               
                                Element loHour = loDocument.createElement("Hour");
                                loHour.appendChild(loDocument.createTextNode(loLogCertificado.getLsHour()));                               
                                Element loDuration = loDocument.createElement("Duration");
                                loDuration.appendChild(loDocument.createTextNode(loLogCertificado.getLsDuration()));                               
                                Element loSpotFormatID = loDocument.createElement("SpotFormatID");
                                loSpotFormatID.appendChild(loDocument.createTextNode(loLogCertificado.getLsSpotformatid()));                               
                                Element loMediaID = loDocument.createElement("MediaID");
                                loMediaID.appendChild(loDocument.createTextNode(loLogCertificado.getLsMediaid()));                               
                                Element loProductID = loDocument.createElement("ProductID");
                                loProductID.appendChild(loDocument.createTextNode(loLogCertificado.getLsProductid()));                               
                                Element loSpotPrice = loDocument.createElement("SpotPrice");
                                loSpotPrice.appendChild(loDocument.createTextNode(loLogCertificado.getLsSpotprice()));                               
                                loSpot.appendChild(loIndAction);
                                loSpot.appendChild(loOrderID);
                                loSpot.appendChild(loSpotID);
                                loSpot.appendChild(loBuyUnitID);
                                loSpot.appendChild(loBreakID);
                                loSpot.appendChild(loHour);
                                loSpot.appendChild(loDuration);
                                loSpot.appendChild(loSpotFormatID);
                                loSpot.appendChild(loMediaID);
                                loSpot.appendChild(loProductID);
                                loSpot.appendChild(loSpotPrice);
                                //loSpots.appendChild(loSpot);
                                loCommercialLog.appendChild(loSpot);
                                
                            }
                        }
                        loRoot.appendChild(loCommercialLog);
                        try{
                            loDocument.appendChild(loRoot);
                            TransformerFactory loTransformerFactory =
                            TransformerFactory.newInstance();
                            Transformer loTransformer =
                            loTransformerFactory.newTransformer();
                            loTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
                            DOMSource loSource = new DOMSource(loDocument);
                            /*
                            StreamResult result =
                            new StreamResult(new File("C:\\Users\\JorgeOWM\\Desktop\\LogCertificado-REQ"+getId()+".xml"));
                            loTransformer.transform(loSource, result);
                            */
                            ByteArrayOutputStream loBaos = new ByteArrayOutputStream();
                            loTransformer.transform(loSource, new StreamResult(loBaos));
                            InputStream           loFileXml = new ByteArrayInputStream(loBaos.toByteArray()); 
                            String                lsNomFile = "LogCertificado-" + getId() + ".xml";
                            loEntityMappedDao.insertEvetvIntXmlFilesTab(liRequest, 
                                                                        Integer.parseInt(lsIdServiceReq), 
                                                                        lsNomFile, 
                                                                        "REQUEST", 
                                                                        "WsLogCertificado", 
                                                                        "A", 
                                                                        lsIdUserNameReq, 
                                                                        loFileXml);
                            
                            liIndProcess = new UtilFaces().getIdConfigParameterByName("createXml");
                            new UtilFaces().insertBitacoraServiceService(liRequest, 
                                                                         Integer.parseInt(lsIdServiceReq), 
                                                                         liIndProcess, 
                                                                         "Archivo XML-Request Generado",
                                                                         0, 
                                                                         liRequest, 
                                                                         Integer.parseInt(lsIdUserReq),
                                                                         lsIdUserNameReq
                                                                         );
                            
                        } catch (TransformerException tfe) {
                            liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                            new UtilFaces().insertBitacoraServiceService(liRequest, 
                                                                         Integer.parseInt(lsIdServiceReq), 
                                                                         liIndProcess, 
                                                                         lsKey + ": Error al generar Archivo XML-Request",
                                                                         0, 
                                                                         liRequest, 
                                                                         Integer.parseInt(lsIdUserReq),
                                                                         lsIdUserNameReq
                                                                         );
                        }catch (Exception loEtfe) {
                            liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                            new UtilFaces().insertBitacoraServiceService(liRequest, 
                                                                         Integer.parseInt(lsIdServiceReq), 
                                                                         liIndProcess, 
                                                                         lsKey + ": Error al generar Archivo XML-Request",
                                                                         0, 
                                                                         liRequest, 
                                                                         Integer.parseInt(lsIdUserReq),
                                                                         lsIdUserNameReq
                                                                         );
                        }
                        
                        new UtilFaces().insertLogServiceService(liRequest, 
                                                                Integer.parseInt(lsIdServiceReq), 
                                                                0, 
                                                                "N", 
                                                                0, 
                                                                liRequest, 
                                                                "Neptuno Execution Request", 
                                                                Integer.parseInt(lsIdUserReq), 
                                                                lsIdUserNameReq, 
                                                                "WsLogCertificado"
                                                                );
                                           
                        WSNeptuno             loWsNeptuno = 
                            new UtilsIntegrationService().getServiceNeptunoDynamic();
                        
                        
                        new UtilFaces().updateLogServiceService(liRequest, 
                                                                Integer.parseInt(lsIdServiceReq), 
                                                                0, 
                                                                "N", 
                                                                0, 
                                                                Integer.parseInt(lsIdUserReq), 
                                                                liRequest, 
                                                                "Neptuno Execution Response", 
                                                                Integer.parseInt(lsIdUserReq), 
                                                                lsIdUserNameReq
                                                                );
                        
                        
                        
                        
                        HeaderHandlerResolver loHandlerResolver =
                            new HeaderHandlerResolver();
                        loWsNeptuno.setHandlerResolver(loHandlerResolver);
                        WSNeptunoSoap         loWsNeptunoSoap = loWsNeptuno.getWSNeptunoSoap();
                        RecibirDatosExternos.Xmlentrada loXmlentrada = 
                            new RecibirDatosExternos.Xmlentrada();
                        boolean               lbIsOsOffline = false;
                        loXmlentrada.getContent().add(loRoot);            
                        liIndProcess = new UtilFaces().getIdConfigParameterByName("InvokingNeptuno");
                        new UtilFaces().insertBitacoraServiceService(liRequest, 
                                                                      Integer.parseInt(lsIdServiceReq), 
                                                                      liIndProcess, 
                                                                      lsKey + ": Servicio Neptuno invocado para Log Certificado",
                                                                      0, 
                                                                      liRequest, 
                                                                      Integer.parseInt(lsIdUserReq),
                                                                      lsIdUserNameReq
                                                                      );
                        //################################## INVOCANDO A NEPTUNO ######################################
                        RecibirDatosExternosResponse.RecibirDatosExternosResult loRder = 
                            loWsNeptunoSoap.recibirDatosExternos(loXmlentrada, lbIsOsOffline);
                        //########################################## FIN ##############################################
                        liIndProcess = new UtilFaces().getIdConfigParameterByName("FinishResponseNeptuno");
                        new UtilFaces().insertBitacoraServiceService(liRequest, 
                                                                     Integer.parseInt(lsIdServiceReq), 
                                                                     liIndProcess, 
                                                                     lsKey + ": Respuesta de Neptuno Obtenida para Log Certificado",
                                                                     0, 
                                                                     liRequest, 
                                                                     Integer.parseInt(lsIdUserReq),
                                                                     lsIdUserNameReq
                                                                     );     
                        //################ Segunda fase (respuesta de evetv) ##############
                        //20180103 al actualizar procesados se toma la fecha de la lista
                        Integer liFlagUpdate = 
                            loLcertDao.getUpdateLogCertificado(lsDateYyyyMmDd,//lsDate, 
                                                               lsChannelTmp
                                                               );
                        
                        liIndProcess = new UtilFaces().getIdConfigParameterByName("UpdateCtrlStatus");
                        new UtilFaces().insertBitacoraServiceService(liRequest, 
                                                                     Integer.parseInt(lsIdServiceReq), 
                                                                     liIndProcess, 
                                                                     lsKey + ": Actualizacion de Status[" + liFlagUpdate + 
                                                                     "] Paradigm para Log Certificado",
                                                                     0, 
                                                                     liRequest, 
                                                                     Integer.parseInt(lsIdUserReq),
                                                                     lsIdUserNameReq
                                                                     );     
                        try{
                            ByteArrayOutputStream loBaosRes = new ByteArrayOutputStream();
                            JAXB.marshal(loRder, loBaosRes);
                            InputStream           loFileXmlRes = new ByteArrayInputStream(loBaosRes.toByteArray()); 
                            String                lsNomFile = "LogCertificado-" + getId() + ".xml";                        
                            loEntityMappedDao.insertEvetvIntXmlFilesTab(liRequest, 
                                                                        Integer.parseInt(lsIdServiceReq), 
                                                                        lsNomFile, 
                                                                        "RESPONSE", 
                                                                        "WsLogCertificado", 
                                                                        "A", 
                                                                        lsIdUserNameReq, 
                                                                        loFileXmlRes);
                        }catch(Exception loEx){
                            liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                            new UtilFaces().insertBitacoraServiceService(liRequest, 
                                                                         Integer.parseInt(lsIdServiceReq), 
                                                                         liIndProcess, 
                                                                         lsKey + ": Error al generar Archivo XML-Response",
                                                                         0, 
                                                                         liRequest, 
                                                                         Integer.parseInt(lsIdUserReq),
                                                                         lsIdUserNameReq
                                                                         );
                        }
                        try{
                            //############### Enviar Correo Electronico #########################
                            //System.out.println("############### Enviar Correo Electronico #########################");
                            liIndProcess = new UtilFaces().getIdConfigParameterByName("SendEmail");
                            new UtilFaces().insertBitacoraServiceService(liRequest, 
                                                                         Integer.parseInt(lsIdServiceReq), 
                                                                         liIndProcess, 
                                                                         lsKey + ": Enviando email del Procesamiento de " +
                                                                         "Log Certificado",
                                                                         0, 
                                                                         liRequest, 
                                                                         Integer.parseInt(lsIdUserReq),
                                                                         lsIdUserNameReq
                                                                         );     
                            String lsSubject = new UtilFaces().getConfigParameterByName("SubjectLogCertificado");
                            System.out.println("lsSubject["+lsSubject+"]");
                            String lsTypeMail = "MAIL_OK";
                            if(lsEmailMsgErr.length() > 0){
                                lsTypeMail = "MAIL_KO";
                            }
                            System.out.println("lsTypeMail["+lsTypeMail+"]");
                            List<EmailDestinationAddress> toEmailDestinationAddress = 
                                loEntityMappedDao.getDestinationAddress(lsIdServiceReq, lsTypeMail);
                            System.out.println("Destinatarios["+toEmailDestinationAddress.size()+"]");
                            for(EmailDestinationAddress toMail : toEmailDestinationAddress){
                                System.out.println("Destinatario["+toMail.getLsAddressTo()+"]["+toMail.getLsNameTo()+"]");
                            }
                            if(toEmailDestinationAddress.size() > 0){
                                MailManagement loMailManagement = new MailManagement();
                                System.out.println("Invocando MailManagement - sendEmailLogCertificado");
                                boolean lbFlagMail = loMailManagement.sendEmailLogCertificado(lsSubject, 
                                                                         toEmailDestinationAddress, 
                                                                         laLogsCertificados,
                                liRequest,Integer.parseInt(lsIdServiceReq)
                                                                        );
                                if(!lbFlagMail){
                                    liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                                    new UtilFaces().insertBitacoraServiceService(liRequest, 
                                                                                 Integer.parseInt(lsIdServiceReq), 
                                                                                 liIndProcess, 
                                                                                 lsKey + ": Error al Enviar Correo Log Certificado " + 
                                                                                 "Error al generar correo html ",
                                                                                 0, 
                                                                                 liRequest, 
                                                                                 Integer.parseInt(lsIdUserReq),
                                                                                 lsIdUserNameReq
                                                                                 ); 
                                }
                            }else{
                                liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                                new UtilFaces().insertBitacoraServiceService(liRequest, 
                                                                             Integer.parseInt(lsIdServiceReq), 
                                                                             liIndProcess, 
                                                                             lsKey + ": Error al Enviar Correo Log Certificado " + 
                                                                             "No existen Destinatarios",
                                                                             0, 
                                                                             liRequest, 
                                                                             Integer.parseInt(lsIdUserReq),
                                                                             lsIdUserNameReq
                                                                             ); 
                            }
                        }catch(Exception loEx){
                            liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                            new UtilFaces().insertBitacoraServiceService(liRequest, 
                                                                         Integer.parseInt(lsIdServiceReq), 
                                                                         liIndProcess, 
                                                                         lsKey + ": Error al Enviar Correo Log Certificado " + 
                                                                         loEx.getMessage(),
                                                                         0, 
                                                                         liRequest, 
                                                                         Integer.parseInt(lsIdUserReq),
                                                                         lsIdUserNameReq
                                                                         );
                        }
                                                
                        loLcertRes.setIdRequestLogCertificadoRes(String.valueOf(liRequest));
                        XmlMessageLcertReqRes loReqRes = new XmlMessageLcertReqRes();               
                        loReqRes.setXmlMessageLcertRequest("REQUEST");
                        loReqRes.setXmlMessageLcertResponse("RESPONSE");
                        loXmlMsCol.getXmlMessageLcertReqRes().add(loReqRes);                    
                        loLcertRes.setXmlMessageResponseLcert(loXmlMsCol); 
                    }
                                
                } catch (ParserConfigurationException loEx) {
                    System.out.println("Error al parsear" + loEx.getMessage());
                }
                                
                liRequest++;
            }
                
        }//END if de flag recon complete
            
        XmlMessageLcertReqRes loResReq = new XmlMessageLcertReqRes();
        loResReq.setXmlMessageLcertRequest("REQUEST");
        loResReq.setXmlMessageLcertResponse("RESPONSE");
        loXmlMsCol.getXmlMessageLcertReqRes().add(loResReq);
        loLcertRes.setXmlMessageResponseLcert(loXmlMsCol);
        return loLcertRes;
    } 
       
    /**
     * Obtiene identificador irrepetible mediante al tiempo actual
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */     
    public String getId(){
        String     lsResponse = null;
        DateFormat loDf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        lsResponse = loDf.format(new java.util.Date(System.currentTimeMillis()));
        return lsResponse;
    }
    
    private String buildDateDDMMYYYY(String tsDate){
        String lsRes = "";
        String[] laDate = tsDate.split("-");
        lsRes = laDate[2]+"/"+laDate[1]+"/"+laDate[0];
        return lsRes;
    }
    
    /**
     * Construye en formato de fecha aceptado por paradigm
     * @autor Jorge Luis Bautista Santiago  
     * @param tsDate
     * @param tsInMask
     * @return String
     */
    private String buildDateYYYYMMDD(String tsDate, String tsInMask){
        String   lsResponse = null;
        String[] laArrDate = tsDate.split("/");
        if(laArrDate.length > 0){
            String lsMonth = laArrDate[0];
            String lsDay = laArrDate[1];
            String lsYear = convierteFecha(tsDate,tsInMask,"YYYY");
            lsResponse = lsYear+"-"+lsMonth+"-"+lsDay;
        }
        return lsResponse;
    }
    
    /**
     * Convierte entre formatos de mascara de fechas
     * @autor Jorge Luis Bautista Santiago  
     * @param tsCadenaFecha
     * @param tsMascaraEntrada
     * @param tsMascaraSalida
     * @return String
     */
    private static String convierteFecha(String tsCadenaFecha, 
                                        String tsMascaraEntrada,
                                        String tsMascaraSalida) {

        SimpleDateFormat loSdfEntrada = new SimpleDateFormat(tsMascaraEntrada);
        SimpleDateFormat loSdfSalida = new SimpleDateFormat(tsMascaraSalida);
        Date             ltDatePiv = new Date();
        String           lsFechaFormateada = "";
        try {
            ltDatePiv = loSdfEntrada.parse(tsCadenaFecha);
            lsFechaFormateada = loSdfSalida.format(ltDatePiv);
        } 
        catch (ParseException loEx) {
            lsFechaFormateada = null;
        }
        return lsFechaFormateada;
    }
}
