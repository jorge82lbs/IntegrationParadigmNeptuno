// A continuacion se realiza un cambio para el branch branch-Targets
/**
* Project: Integraton Paradigm - EveTV
*
* File: IntegrationDasProgramas.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/

package com.televisa.comer.integration.service.implementation;

import com.televisa.comer.integration.model.beans.RsstProgramasBean;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.model.daos.ProgramasDao;
import com.televisa.comer.integration.service.beans.programs.Channel;
import com.televisa.comer.integration.service.beans.programs.ProgramsInputParameters;
import com.televisa.comer.integration.service.beans.programs.ProgramsResponse;
import com.televisa.comer.integration.service.beans.programs.XmlMessageReqRes;
import com.televisa.comer.integration.service.beans.programs.XmlMessageResponseCollection;
import com.televisa.comer.integration.service.interfaces.ProgramasInterface;
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
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.List;

import javax.jws.WebMethod;

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

/** Esta clase consulta programas en Paradigm y consume servicio web
 * expuesto por eVeTV <br/><br/>
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */

public class IntegrationDasProgramas implements ProgramasInterface{

    /**
     * Obtiene informacion de Programas en Paradigm
     * @autor Jorge Luis Bautista Santiago
     * @param toPrograms
     * @return ProgramsResponse
     */
    @Override
    @WebMethod
    public ProgramsResponse invokePrograms(ProgramsInputParameters toPrograms) {
        ProgramsResponse loProgramsRes = new ProgramsResponse();
        String           lsIdRequestProgramsReq = toPrograms.getIdRequestProgramsReq();
        String           lsIdServiceReq = toPrograms.getIdService();
        String           lsIdUserNameReq = toPrograms.getUserName();
        String           lsIdUserReq = toPrograms.getIdUser();
        String           lsDate = toPrograms.getDateQuery();
        String           lsChannelAll = "";
        List<Channel>    loChnllCol = toPrograms.getChannelList().getChannels();
        for(Channel loChannel: loChnllCol){
            lsChannelAll = lsChannelAll + "'"+loChannel.getChannel()+"',";
        }
        ProgramasDao            loPpDao = new ProgramasDao();
        List<RsstProgramasBean> loProgramsDB = 
            loPpDao.getProgramsFromParadigmService(lsDate, lsChannelAll.substring(0, lsChannelAll.length()-1));
        if(loProgramsDB.size() > 0){
            Integer piIndProcess = new UtilFaces().getIdConfigParameterByName("ExtractData");
            new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                         Integer.parseInt(lsIdServiceReq), 
                                                         piIndProcess, 
                                                         "Extraccion de Informacion para Servicio de Programas",
                                                         0, 
                                                         Integer.parseInt(lsIdRequestProgramsReq), 
                                                         Integer.parseInt(lsIdUserReq),
                                                         lsIdUserNameReq
                                                         );     
            DocumentBuilderFactory loDocFactory = DocumentBuilderFactory.newInstance();
            
            try {
                DocumentBuilder loDocBuilder = loDocFactory.newDocumentBuilder();
                Document        loDocument = loDocBuilder.newDocument();           
                Element         loRoot = loDocument.createElement("RAIZ");
                Element         loProgram = null;
                for(RsstProgramasBean loProgramDB: loProgramsDB){
                    loProgram = loDocument.createElement("programa");
                    Element loExternoIDPrograma = loDocument.createElement("externoIDPrograma");
                    loExternoIDPrograma.appendChild(loDocument.createTextNode(loProgramDB.getLsBuyuntid()));
                    Element loDescripcionPrograma = loDocument.createElement("descripcionPrograma");
                    loDescripcionPrograma.appendChild(loDocument.createTextNode(loProgramDB.getLsBuylnam()));
                    Element loIndActivo = loDocument.createElement("indActivo");
                    loIndActivo.appendChild(loDocument.createTextNode(loProgramDB.getLsIndactivo()));
                    Element loIndTarifaPrograma = loDocument.createElement("indTarifaPrograma");
                    loIndTarifaPrograma.appendChild(loDocument.createTextNode(loProgramDB.getLsIndtarifaprograma()));
                    loProgram.appendChild(loExternoIDPrograma);
                    loProgram.appendChild(loDescripcionPrograma);
                    loProgram.appendChild(loIndActivo);
                    loProgram.appendChild(loIndTarifaPrograma);
                    loRoot.appendChild(loProgram);
                    //############ Insertar en tabla control: #################
                    loPpDao.insertEveTvProgramas(lsIdRequestProgramsReq, 
                                                 lsIdServiceReq, 
                                                 loProgramDB.getLsBuyuntid().replaceAll("'", "''"), 
                                                 loProgramDB.getLsBuylnam().replaceAll("'", "''"), 
                                                 loProgramDB.getLsIndactivo(), 
                                                 loProgramDB.getLsIndtarifaprograma(), 
                                                 "A", 
                                                 lsIdUserNameReq, 
                                                 lsIdUserReq
                                                 );
                    /*
                    piIndProcess = new UtilFaces().getIdConfigParameterByName("InsertCtrlTable");
                    new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                                 Integer.parseInt(lsIdServiceReq), 
                                                                 piIndProcess, 
                                                            "Insertar Registros en Tabla de Control Para Programas",
                                                                 0, 
                                                                 Integer.parseInt(lsIdRequestProgramsReq), 
                                                                 Integer.parseInt(lsIdUserReq),
                                                                 lsIdUserNameReq
                                                                 );     */
                }
                
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
                    new StreamResult(new File("C:\\Users\\JorgeOWM\\Desktop\\ProgramasXml.xml"));
                    transformer.transform(source, result);
                    */
                    ByteArrayOutputStream loBaos = new ByteArrayOutputStream();
                    loTransformer.transform(loSource, new StreamResult(loBaos));
                    InputStream           loFileXml = new ByteArrayInputStream(loBaos.toByteArray()); 
                    EntityMappedDao loEntityMappedDao = new EntityMappedDao();
                    String                lsNomFile = "Programas-" + getId() + ".xml";
                    loEntityMappedDao.insertEvetvIntXmlFilesTab(Integer.parseInt(lsIdRequestProgramsReq), 
                                                                Integer.parseInt(lsIdServiceReq), 
                                                                lsNomFile, 
                                                                "REQUEST", 
                                                                "WsProgramas", 
                                                                "A", 
                                                                lsIdUserNameReq, 
                                                                loFileXml);
                } catch (TransformerException loEtfe) {
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                    new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                                 Integer.parseInt(lsIdServiceReq), 
                                                                 liIndProcess, 
                                                                 "Error al generar Archivo XML-Request",
                                                                 0, 
                                                                 Integer.parseInt(lsIdRequestProgramsReq), 
                                                                 Integer.parseInt(lsIdUserReq),
                                                                 lsIdUserNameReq
                                                                 );
                }catch (Exception loEx) {
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                    new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                                 Integer.parseInt(lsIdServiceReq), 
                                                                 liIndProcess, 
                                                                 "Error al generar Archivo XML-Request",
                                                                 0, 
                                                                 Integer.parseInt(lsIdRequestProgramsReq), 
                                                                 Integer.parseInt(lsIdUserReq),
                                                                 lsIdUserNameReq
                                                                 );
                }
                   
                //######### Invocar a Neptuno ##########          
                new UtilFaces().insertLogServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                        Integer.parseInt(lsIdServiceReq), 
                                                        0, 
                                                        "N", 
                                                        0, 
                                                        Integer.parseInt(lsIdRequestProgramsReq), 
                                                        "Neptuno Execution Request", 
                                                        Integer.parseInt(lsIdUserReq), 
                                                        lsIdUserNameReq, 
                                                        "WsProgramas"
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
                
                piIndProcess = new UtilFaces().getIdConfigParameterByName("FinishResponseNeptuno");
                new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                              Integer.parseInt(lsIdServiceReq), 
                                                              piIndProcess, 
                                                              "Respuesta de Neptuno Obtenida para Programas",
                                                              0, 
                                                              Integer.parseInt(lsIdRequestProgramsReq), 
                                                              Integer.parseInt(lsIdUserReq),
                                                              lsIdUserNameReq
                                                              );                 
                
                CodRespuesta loResp;
                try {
                    loResp = convertResponse(loRder);
                    List<CodRespuesta.ItemCabecera> laCabs = loResp.getItemCabecera();
                    for(CodRespuesta.ItemCabecera loCab : laCabs) {
                        if(loCab.getProcessID().intValue() == 0){
                            String lsIdError = 
                                loCab.getItemRespuesta().getListaMensaje().get(0).getIdError();
                            String lsIdErrorDes = 
                                loCab.getItemRespuesta().getListaMensaje().get(0).getDescripcion();
                            loPpDao.updateEveTvProgramas(lsIdRequestProgramsReq, 
                                                         lsIdServiceReq, 
                                                         "ALL_ERROR",                                                          
                                                         lsIdErrorDes,
                                                         lsIdError,
                                                         "E", 
                                                         lsIdUserNameReq,
                                                         lsIdUserReq
                                                         );
                            
                            piIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                            new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                                          Integer.parseInt(lsIdServiceReq), 
                                                                          piIndProcess, 
                                                                          "Error General Devuelto por Neptuno",
                                                                          0, 
                                                                          Integer.parseInt(lsIdRequestProgramsReq), 
                                                                          Integer.parseInt(lsIdUserReq),
                                                                          lsIdUserNameReq
                                                                          );     
                        }else{
                            CodRespuesta.ItemCabecera.ItemRespuesta loIRes = loCab.getItemRespuesta();
                            if(loIRes != null) {                                
                                String lsElemento = loIRes.getElemento();
                                if(lsElemento != null && lsElemento.equalsIgnoreCase("externoIDPrograma")) {
                                    //5.- Por cada ItemCabecera Actualizar Estatus
                                    String lsIdElemento = loIRes.getIdElemento();
                                    String lsResultado = loIRes.getResultado();
                                    //System.out.println("lsIdElemento["+lsIdElemento+"] - lsResultado["+lsResultado+"]");
                                    if(lsResultado.equalsIgnoreCase("OK")){
                                        //Actualizar status a ZERO
                                        loPpDao.updateProgramsParadigm(lsIdElemento.trim(), "0");
                                        loPpDao.updateEveTvProgramas(lsIdRequestProgramsReq, 
                                                                     lsIdServiceReq, 
                                                                     lsIdElemento.trim(),
                                                                     "", 
                                                                     "", 
                                                                     "T",
                                                                     lsIdUserNameReq, 
                                                                     lsIdUserReq);
                                    }else{
                                        String lsIdError = loIRes.getListaMensaje().get(0).getIdError();
                                        String lsDescError = loIRes.getListaMensaje().get(0).getDescripcion();
                                        loPpDao.updateEveTvProgramas(lsIdRequestProgramsReq, 
                                                                     lsIdServiceReq, 
                                                                     lsIdElemento.trim(),
                                                                     lsDescError, 
                                                                     lsIdError, 
                                                                     "E",
                                                                     lsIdUserNameReq, 
                                                                     lsIdUserReq);
                                    }
                                }
                            }
                        }
                    }
                    piIndProcess = new UtilFaces().getIdConfigParameterByName("UpdateCtrlStatus");
                    new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                                 Integer.parseInt(lsIdServiceReq), 
                                                                 piIndProcess, 
                                                                 "Actualizacion de Status en Tablas de Control " +
                                                                 " para Programas",
                                                                 0, 
                                                                 Integer.parseInt(lsIdRequestProgramsReq), 
                                                                 Integer.parseInt(lsIdUserReq),
                                                                 lsIdUserNameReq
                                                                 );     
                } catch (Exception loEx) {
                    System.out.println("Error JAXBException " + loEx.getMessage());
                }
                loProgramsRes.setIdRequestProgramsRes(lsIdRequestProgramsReq);
                XmlMessageResponseCollection loXmlMsCol = new XmlMessageResponseCollection();
                XmlMessageReqRes             loReqRes = new XmlMessageReqRes();                           
                loReqRes.setXmlMessageRequest("FIN_REQUEST");                
                try{
                    ByteArrayOutputStream loBaosRes = new ByteArrayOutputStream();
                    JAXB.marshal(loRder, loBaosRes);
                    InputStream loFileXmlRes = new ByteArrayInputStream(loBaosRes.toByteArray()); 
                    String lsNomFile = "Programas-" + getId() + ".xml";
                    EntityMappedDao loEntityMappedDao = new EntityMappedDao();
                    loEntityMappedDao.insertEvetvIntXmlFilesTab(Integer.parseInt(lsIdRequestProgramsReq), 
                                                                Integer.parseInt(lsIdServiceReq), 
                                                                lsNomFile, 
                                                                "RESPONSE", 
                                                                "WsProgramas", 
                                                                "A", 
                                                                lsIdUserNameReq, 
                                                                loFileXmlRes);
                }catch(Exception loEx){
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                    new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                                 Integer.parseInt(lsIdServiceReq), 
                                                                 liIndProcess, 
                                                                 "Error al generar Archivo XML-Response",
                                                                 0, 
                                                                 Integer.parseInt(lsIdRequestProgramsReq), 
                                                                 Integer.parseInt(lsIdUserReq),
                                                                 lsIdUserNameReq
                                                                 );
                }
                loReqRes.setXmlMessageResponse("FIN_RESPONSE");
                loXmlMsCol.getXmlMessageReqRes().add(loReqRes);                    
                loProgramsRes.setXmlMessageResponse(loXmlMsCol);
            } catch (ParserConfigurationException loEx) {                
                loProgramsRes.setError(loEx.getMessage());
            }
                                            
        }else{
            loProgramsRes.setError("No Information To Send");
            Integer piIndProcess = new UtilFaces().getIdConfigParameterByName("NoDataToSend");
            new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestProgramsReq), 
                                                         Integer.parseInt(lsIdServiceReq), 
                                                         piIndProcess, 
                                                         "Sin Informacion para Enviar a Neptuno",
                                                         0, 
                                                         Integer.parseInt(lsIdRequestProgramsReq), 
                                                         Integer.parseInt(lsIdUserReq),
                                                         lsIdUserNameReq
                                                         );     
        }
        return loProgramsRes;
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
