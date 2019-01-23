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

import com.televisa.comer.integration.model.beans.RsstProgramasBean;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
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
        // TODO Implement this method
        TargetsResponse loTargetsResponse = new TargetsResponse();
        System.out.println("Ejecutando cliente Targets ===> ["+new Date()+"]");
        
        String lsIdRequest = toTargetsInputParameters.getIdRequestTargetsReq();
        
        DocumentBuilderFactory loDocFactory = DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder loDocBuilder = loDocFactory.newDocumentBuilder();
            Document        loDocument = loDocBuilder.newDocument();           
            Element         loRoot = loDocument.createElement("RAIZ");
            Element         loTargets = null;
            //JLBSfor(RsstProgramasBean loProgramDB: loProgramsDB){
                loTargets = loDocument.createElement("Target");
                Element loAction = loDocument.createElement("Accion");
                loAction.appendChild(loDocument.createTextNode("I"));
                Element loDescription = loDocument.createElement("Descripcion");
                loDescription.appendChild(loDocument.createTextNode("DescripcionTarget2"));
                Element loShortDescription = loDocument.createElement("Descripcion_Corta");
                loShortDescription.appendChild(loDocument.createTextNode("Descripcion corta2"));
                Element loAudDescription = loDocument.createElement("Descripcion_Audiencia");
                loAudDescription.appendChild(loDocument.createTextNode("Descripcion audiencia2"));
            Element loCodCodExt = loDocument.createElement("Cod_Codigo_Ext");
            loCodCodExt.appendChild(loDocument.createTextNode("89"));
                loTargets.appendChild(loAction);
                loTargets.appendChild(loDescription);
                loTargets.appendChild(loShortDescription);
                loTargets.appendChild(loAudDescription);
            loTargets.appendChild(loCodCodExt);
                loRoot.appendChild(loTargets);
                //############ Insertar en tabla control: #################
            //JLBS}
            
            try{
                loDocument.appendChild(loRoot);
                TransformerFactory loTransformerFactory =
                TransformerFactory.newInstance();
                Transformer loTransformer =
                loTransformerFactory.newTransformer();
                loTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource loSource = new DOMSource(loDocument);
                
                StreamResult result =
                new StreamResult(new File("C:\\Users\\Jorge-OMW\\Desktop\\TargetsXml.xml"));
                loTransformer.transform(loSource, result);
                
                ByteArrayOutputStream loBaos = new ByteArrayOutputStream();
                loTransformer.transform(loSource, new StreamResult(loBaos));
                
                //JLBSInputStream           loFileXml = new ByteArrayInputStream(loBaos.toByteArray()); 
                //JLBSEntityMappedDao loEntityMappedDao = new EntityMappedDao();
                //JLBSString                lsNomFile = "Targets-" + getId() + ".xml";
                /*
                loEntityMappedDao.insertEvetvIntXmlFilesTab(Integer.parseInt(lsIdRequestProgramsReq), 
                                                            Integer.parseInt(lsIdServiceReq), 
                                                            lsNomFile, 
                                                            "REQUEST", 
                                                            "WsProgramas", 
                                                            "A", 
                                                            lsIdUserNameReq, 
                                                            loFileXml);
                */
            } catch (TransformerException loEtfe) {
                Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                /*
                new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                             Integer.parseInt(lsIdServiceReq), 
                                                             liIndProcess, 
                                                             "Error al generar Archivo XML-Request",
                                                             0, 
                                                             Integer.parseInt(lsIdRequestProgramsReq), 
                                                             Integer.parseInt(lsIdUserReq),
                                                             lsIdUserNameReq
                                                             );
                */
            }catch (Exception loEx) {
                Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                /*new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                             Integer.parseInt(lsIdServiceReq), 
                                                             liIndProcess, 
                                                             "Error al generar Archivo XML-Request",
                                                             0, 
                                                             Integer.parseInt(lsIdRequestProgramsReq), 
                                                             Integer.parseInt(lsIdUserReq),
                                                             lsIdUserNameReq
                                                             );
*/
            }
               
            //######### Invocar a Neptuno ##########   
            /*
            new UtilFaces().insertLogServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                    Integer.parseInt(lsIdServiceReq), 
                                                    0, 
                                                    "N", 
                                                    0, 
                                                    Integer.parseInt(lsIdRequestProgramsReq), 
                                                    "Neptuno Execution Request", 
                                                    Integer.parseInt(lsIdUserReq), 
                                                    lsIdUserNameReq, 
                                                    "WsTargets"
                                                    );
            */
            
            System.out.println("Invocar a Neptuno ==> Deshabilitado");
            /*
            WSNeptuno             loWsNeptuno = new UtilsIntegrationService().getServiceNeptunoDynamic();
            HeaderHandlerResolver loHandlerResolver = new HeaderHandlerResolver();
            loWsNeptuno.setHandlerResolver(loHandlerResolver);
            WSNeptunoSoap         loWsNeptunoSoap = loWsNeptuno.getWSNeptunoSoap();
            RecibirDatosExternos.Xmlentrada loXmlentrada = new RecibirDatosExternos.Xmlentrada();
            boolean               lbIsOffline = false;               
            loXmlentrada.getContent().add(loRoot);            
            RecibirDatosExternosResponse.RecibirDatosExternosResult loRder = 
                loWsNeptunoSoap.recibirDatosExternos(loXmlentrada, lbIsOffline);
            
            */
            
            /*
            new UtilFaces().updateLogServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                    Integer.parseInt(lsIdServiceReq), 
                                                    0, 
                                                    "N", 
                                                    0, 
                                                    Integer.parseInt(lsIdUserReq), 
                                                    Integer.parseInt(lsIdRequestProgramsReq), 
                                                    "Neptuno Execution Response", 
                                                    Integer.parseInt(lsIdUserReq), 
                                                    lsIdUserNameReq
                                                    );
            */
            //piIndProcess = new UtilFaces().getIdConfigParameterByName("FinishResponseNeptuno");
            /*
            new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                          Integer.parseInt(lsIdServiceReq), 
                                                          piIndProcess, 
                                                          "Respuesta de Neptuno Obtenida para Programas",
                                                          0, 
                                                          Integer.parseInt(lsIdRequestProgramsReq), 
                                                          Integer.parseInt(lsIdUserReq),
                                                          lsIdUserNameReq
                                                          );  
            */
            
           //CodRespuesta loResp;

        } catch (ParserConfigurationException loEx) {                
            loTargetsResponse.setError(loEx.getMessage());
        }
        
        loTargetsResponse.setIdRequestTargetsRes("11");
        loTargetsResponse.setIdService("33");
        loTargetsResponse.setIdUser("1");
        loTargetsResponse.setIdUser("jorge");
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
