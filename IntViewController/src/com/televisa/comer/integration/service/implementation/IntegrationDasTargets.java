/**
* Project: Integraton Paradigm - EveTV
*
* File: IntegrationDasTargets.java
*
* Created on: Enero 09, 2019 at 11:00
*
* Copyright (c) - OMW - 2019
*/

package com.televisa.comer.integration.service.implementation;


import com.televisa.comer.integration.model.beans.RsstTargetsBean;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.model.daos.TargetsDao;
import com.televisa.comer.integration.service.beans.targets.ParameterMap;
import com.televisa.comer.integration.service.beans.targets.TargetsInputParameters;
import com.televisa.comer.integration.service.beans.targets.TargetsResponse;
import com.televisa.comer.integration.service.beans.targets.XmlMessageReqRes;
import com.televisa.comer.integration.service.beans.targets.XmlMessageResponseCollection;
import com.televisa.comer.integration.service.interfaces.TargetsInterface;

import com.televisa.comer.integration.service.utils.UtilsIntegrationService;
import com.televisa.comer.integration.service.xmlutils.HeaderHandlerResolver;
import com.televisa.integration.view.front.util.UtilFaces;

import es.com.evendor.RecibirDatosExternos;
import es.com.evendor.RecibirDatosExternosResponse;
import es.com.evendor.RecibirDatosExternosResponse.RecibirDatosExternosResult;
import es.com.evendor.WSNeptuno;
import es.com.evendor.WSNeptunoSoap;
import es.com.evendor.types.CodRespuesta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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

/** Esta clase consulta targets en Paradigm y consume servicio web
 * expuesto por eVeTV <br/><br/>
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Enero 09, 2019, 12:00 pm
 */

public class IntegrationDasTargets implements TargetsInterface {
    public IntegrationDasTargets() {
        super();
    }

    @Override
    public TargetsResponse invokeTargets(TargetsInputParameters toTargetsInputParameters) {
        TargetsResponse loTargetsResponse = new TargetsResponse();
        System.out.println("Ejecutando cliente Targets ===> ["+new Date()+"]");                
        String lsIdRequest = toTargetsInputParameters.getIdRequestTargetsReq();
        
        String lsIdService = "0";
        String lsIdUser = "0";
        String lsUserName = null;
        
        for(ParameterMap loParameterMap : toTargetsInputParameters.getParametersList().getParameterMap()){
            if(loParameterMap.getParameterName().equalsIgnoreCase("lsIdService")){
                lsIdService = loParameterMap.getParameterValue();
            }
            if(loParameterMap.getParameterName().equalsIgnoreCase("lsIdUser")){
                lsIdUser = loParameterMap.getParameterValue();
            }
            if(loParameterMap.getParameterName().equalsIgnoreCase("lsUserName")){
                lsUserName = loParameterMap.getParameterValue();
            }
        } 
        System.out.println("(Servicio)lsIdService: "+lsIdService);
        System.out.println("(Servicio)lsParamUserName: "+lsUserName);
        System.out.println("(Servicio)lsParamIdUser: "+lsIdUser);
        System.out.println("(Servicio)liIdParameter: "+lsIdRequest);
        
        
        TargetsDao loTgDao = new TargetsDao();
        List<RsstTargetsBean> laTargetsAll = loTgDao.getTargetsFromDataBase();
        System.out.println("Targets Extraidos: "+laTargetsAll.size());
        if(laTargetsAll.size() > 0){
            System.out.println("Extraer info");
            Integer piIndProcess = new UtilFaces().getIdConfigParameterByName("ExtractData");
            new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequest), 
                                                         Integer.parseInt(lsIdService), 
                                                         piIndProcess, 
                                                         "Extraccion de Informacion para Servicio de Targets",
                                                         0, 
                                                         Integer.parseInt(lsIdRequest), 
                                                         Integer.parseInt(lsIdUser),
                                                         lsUserName
                                                         );     
            
            
            DocumentBuilderFactory loDocFactory = DocumentBuilderFactory.newInstance();
            
            try {
                DocumentBuilder loDocBuilder = loDocFactory.newDocumentBuilder();
                Document        loDocument = loDocBuilder.newDocument();           
                Element         loRoot = loDocument.createElement("RAIZ");
                Element         loTargets = null;
                for(RsstTargetsBean loTarget: laTargetsAll){
                    loTargets = loDocument.createElement("Target");
                    Element loAction = loDocument.createElement("Accion");
                    loAction.appendChild(loDocument.createTextNode("I"));
                    Element loDescription = loDocument.createElement("Descripcion");
                    loDescription.appendChild(loDocument.createTextNode(loTarget.getLsDescripcion()));
                    Element loShortDescription = loDocument.createElement("Descripcion_Corta");
                    loShortDescription.appendChild(loDocument.createTextNode(loTarget.getLsDescripcionCorta()));
                    Element loAudDescription = loDocument.createElement("Descripcion_Audiencia");
                    loAudDescription.appendChild(loDocument.createTextNode(loTarget.getLsDescripcionAudicencia()));
                    Element loCodCodExt = loDocument.createElement("Cod_Codigo_Ext");
                    loCodCodExt.appendChild(loDocument.createTextNode(loTarget.getLsCodigoExt()));
                    
                    loTargets.appendChild(loAction);
                    loTargets.appendChild(loDescription);
                    loTargets.appendChild(loShortDescription);
                    loTargets.appendChild(loAudDescription);
                    loTargets.appendChild(loCodCodExt);
                    loRoot.appendChild(loTargets);
                    
                    //############ Insertar en tabla control: #################
                    //--> No existe por ahora
                    
                }
                
                try{
                    loDocument.appendChild(loRoot);
                    TransformerFactory loTransformerFactory =
                    TransformerFactory.newInstance();
                    Transformer loTransformer =
                    loTransformerFactory.newTransformer();
                    loTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    DOMSource loSource = new DOMSource(loDocument);
                    System.out.println("Creando archivo local ");
                    
                    StreamResult result =
                    new StreamResult(new File("C:\\Users\\Jorge-OMW\\Desktop\\TargetsXml.xml"));
                    loTransformer.transform(loSource, result);
                    
                    ByteArrayOutputStream loBaos = new ByteArrayOutputStream();
                    loTransformer.transform(loSource, new StreamResult(loBaos));
                    InputStream           loFileXml = new ByteArrayInputStream(loBaos.toByteArray()); 
                    EntityMappedDao loEntityMappedDao = new EntityMappedDao();
                    String                lsNomFile = "Targets-" + getId() + ".xml";
                    loEntityMappedDao.insertEvetvIntXmlFilesTab(Integer.parseInt(lsIdRequest), 
                                                                Integer.parseInt(lsIdService), 
                                                                lsNomFile, 
                                                                "REQUEST", 
                                                                "WsTargets", 
                                                                "A", 
                                                                lsUserName, 
                                                                loFileXml);
                } catch (TransformerException loEtfe) {
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                    new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequest), 
                                                                 Integer.parseInt(lsIdService), 
                                                                 liIndProcess, 
                                                                 "Error al generar Archivo XML-Request (Targets)",
                                                                 0, 
                                                                 Integer.parseInt(lsIdRequest), 
                                                                 Integer.parseInt(lsIdUser),
                                                                 lsUserName
                                                                 );
                    
                }catch (Exception loEx) {
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                    new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequest), 
                                                                 Integer.parseInt(lsIdService), 
                                                                 liIndProcess, 
                                                                 "Error al generar Archivo XML-Request (Targets)",
                                                                 0, 
                                                                 Integer.parseInt(lsIdRequest), 
                                                                 Integer.parseInt(lsIdUser),
                                                                 lsUserName
                                                                 );
                }
                //######### Invocar a Neptuno ##########   
                new UtilFaces().insertLogServiceService(Integer.parseInt(lsIdRequest), 
                                                        Integer.parseInt(lsIdService), 
                                                        0, 
                                                        "N", 
                                                        0, 
                                                        Integer.parseInt(lsIdRequest), 
                                                        "Neptuno Execution Request", 
                                                        Integer.parseInt(lsIdUser), 
                                                        lsUserName, 
                                                        "WsTargets"
                                                        );
                
                WSNeptuno             loWsNeptuno = new UtilsIntegrationService().getServiceNeptunoDynamic();
                HeaderHandlerResolver loHandlerResolver = new HeaderHandlerResolver();
                loWsNeptuno.setHandlerResolver(loHandlerResolver);
                WSNeptunoSoap         loWsNeptunoSoap = loWsNeptuno.getWSNeptunoSoap();
                RecibirDatosExternos.Xmlentrada loXmlentrada = new RecibirDatosExternos.Xmlentrada();
                boolean               lbIsOffline = false;               
                loXmlentrada.getContent().add(loRoot);            
                RecibirDatosExternosResponse.RecibirDatosExternosResult loRder = 
                    loWsNeptunoSoap.recibirDatosExternos(loXmlentrada, lbIsOffline);
                new UtilFaces().updateLogServiceService(Integer.parseInt(lsIdRequest), 
                                                        Integer.parseInt(lsIdService), 
                                                        0, 
                                                        "N", 
                                                        0, 
                                                        Integer.parseInt(lsIdUser), 
                                                        Integer.parseInt(lsIdRequest), 
                                                        "Neptuno Execution Response", 
                                                        Integer.parseInt(lsIdUser), 
                                                        lsUserName
                                                        );
                
                piIndProcess = new UtilFaces().getIdConfigParameterByName("FinishResponseNeptuno");
                new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequest), 
                                                              Integer.parseInt(lsIdService), 
                                                              piIndProcess, 
                                                              "Respuesta de Neptuno Obtenida para Targets",
                                                              0, 
                                                              Integer.parseInt(lsIdRequest), 
                                                              Integer.parseInt(lsIdUser),
                                                              lsUserName
                                                              );  
                
                
               CodRespuesta loResp;
                System.out.println("Leer respuesta - Inicio");
               try {
                   loResp = convertResponse(loRder);
                   List<CodRespuesta.ItemCabecera> laCabs = loResp.getItemCabecera();
                   System.out.println("Numer de cabeceras: "+laCabs.size());
                   for(CodRespuesta.ItemCabecera loCab : laCabs) {
                       System.out.println("loCab.getProcessID(): "+loCab.getProcessID().intValue());
                       if(loCab.getProcessID().intValue() > 0){
                           CodRespuesta.ItemCabecera.ItemRespuesta loIRes = loCab.getItemRespuesta();
                           if(loIRes != null) {                                
                               //5.- Por cada ItemCabecera Actualizar Estatus
                               //System.out.println("Extraer valores....");
                               String lsElemento = loIRes.getElemento();
                               //System.out.println("lsElemento "+lsElemento);
                               String lsIdElemento = loIRes.getIdElemento();
                               //System.out.println("lsIdElemento "+lsIdElemento);
                               String lsResultado = loIRes.getResultado();
                               //System.out.println("lsResultado "+lsResultado);
                               
                               /*
                               List<CodRespuesta.ItemCabecera.ItemRespuesta.ListaMensaje> loLm = loIRes.getListaMensaje();
                               String lsDescription = "12 34 345 56456, 86";
                               for(CodRespuesta.ItemCabecera.ItemRespuesta.ListaMensaje loObj : loLm){
                                   System.out.println("getIdError "+loObj.getIdError());    
                                   System.out.println("getDescripcion "+loObj.getDescripcion());    
                                   lsDescription = loObj.getDescripcion();
                               }*/
                               
                               System.out.println("lsIdElemento["+lsIdElemento+"] - lsResultado["+lsResultado+"] ");
                               RsstTargetsBean loRsstTargetsBean = new RsstTargetsBean();
                               loRsstTargetsBean.setLsCodigoExt(lsIdElemento);
                               loRsstTargetsBean.setLsDescripcion("lsDescripcion");
                               loRsstTargetsBean.setLsDescripcionAudicencia("lsDescripcionAudicencia");
                               loRsstTargetsBean.setLsDescripcionCorta("lsDescripcionCorta");
                               if(lsResultado.equalsIgnoreCase("OK")){                                   
                                   loRsstTargetsBean.setLsStatus("1");
                               }else{
                                   loRsstTargetsBean.setLsStatus("2");
                               }
                               loTgDao.updateTargetsParadigm(loRsstTargetsBean);
                           }
                           else{
                               System.out.println("ItemRespuesta es null.");
                           }
                       }
                   }
                   piIndProcess = new UtilFaces().getIdConfigParameterByName("UpdateCtrlStatus");
                   new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequest), 
                                                                Integer.parseInt(lsIdService), 
                                                                piIndProcess, 
                                                                "Actualizacion de Status en Tablas de Control " +
                                                                " para Targets",
                                                                0, 
                                                                Integer.parseInt(lsIdRequest), 
                                                                Integer.parseInt(lsIdUser),
                                                                lsUserName
                                                                );     
               } catch (Exception loEx) {
                   System.out.println("Error JAXBException " + loEx.getMessage());
               }

                try{
                    ByteArrayOutputStream loBaosRes = new ByteArrayOutputStream();
                    JAXB.marshal(loRder, loBaosRes);
                    InputStream loFileXmlRes = new ByteArrayInputStream(loBaosRes.toByteArray()); 
                    String lsNomFile = "Targets-" + getId() + ".xml";
                    EntityMappedDao loEntityMappedDao = new EntityMappedDao();
                    loEntityMappedDao.insertEvetvIntXmlFilesTab(Integer.parseInt(lsIdRequest), 
                                                                Integer.parseInt(lsIdService), 
                                                                lsNomFile, 
                                                                "RESPONSE", 
                                                                "WsTargets", 
                                                                "A", 
                                                                lsIdUser, 
                                                                loFileXmlRes);
                }catch(Exception loEx){
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                    new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequest), 
                                                                 Integer.parseInt(lsIdService), 
                                                                 liIndProcess, 
                                                                 "Error al generar Archivo XML-Response",
                                                                 0, 
                                                                 Integer.parseInt(lsIdRequest), 
                                                                 Integer.parseInt(lsIdUser),
                                                                 lsUserName
                                                                 );
                }
            } catch (ParserConfigurationException loEx) {                
                loTargetsResponse.setError(loEx.getMessage());
            }                        
        }
                        
        
        loTargetsResponse.setIdRequestTargetsRes(lsIdRequest);
        loTargetsResponse.setIdService(lsIdService);
        loTargetsResponse.setIdUser(lsIdUser);
        loTargetsResponse.setIdUser(lsUserName);
        XmlMessageResponseCollection laColl = new XmlMessageResponseCollection();
        XmlMessageReqRes loAmb = new XmlMessageReqRes();
        loAmb.setXmlMessageRequest("request");
        loAmb.setXmlMessageResponse("response");
        laColl.getXmlMessageReqRes().add(loAmb);
        loTargetsResponse.setXmlMessageResponse(laColl);
        return loTargetsResponse;
    }
    
    /**
     * Transofrma objeto respuesta a Clase xml leible
     * @autor Jorge Luis Bautista Santiago
     * @param toResp
     * @return CodRespuesta
     */
    private CodRespuesta convertResponse(RecibirDatosExternosResult toResp) 
                                                       throws JAXBException {
        JAXBContext   loJAXBContext = JAXBContext.newInstance(RecibirDatosExternosResult.class);
        Marshaller    loJAXBMarshaller = loJAXBContext.createMarshaller();
        loJAXBMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter  loSW = new StringWriter();
        loJAXBMarshaller.marshal(toResp, loSW);
        CodRespuesta  loResp = JAXB.unmarshal(new StringReader(loSW.toString()), CodRespuesta.class );
        return loResp;
    }
    
    /**
     * Obtiene identificador en base al momento capturado en tiempo
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String getId(){
        String     lsResponse = null;
        DateFormat loDf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        lsResponse = loDf.format(new java.util.Date(System.currentTimeMillis()));
        return lsResponse;
    }
}
