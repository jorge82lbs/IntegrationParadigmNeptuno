/**
* Project: Paradigm - eVeTV Integration
*
* File: IntegrationDasVentaTradicionalUsr.java
*
* Created on:  Marzo 10, 2018 at 11:00
*
* Copyright (c) - Omw - 2018
*/

package com.televisa.comer.integration.service.implementation;

import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.model.daos.VentaTradicionalDao;
import com.televisa.comer.integration.service.beans.traditionalsale.Channel;
import com.televisa.comer.integration.service.beans.traditionalsale.TraditionalInputParameters;
import com.televisa.comer.integration.service.beans.traditionalsale.TraditionalResponse;
import com.televisa.comer.integration.service.beans.traditionalsale.XmlMessageReqRes;
import com.televisa.comer.integration.service.beans.traditionalsale.XmlMessageResponseCollection;
import com.televisa.comer.integration.service.beans.types.TraditionalMapCampaignHeader;
import com.televisa.comer.integration.service.interfaces.VentaTradicionalInterface;
import com.televisa.comer.integration.service.utils.UtilsIntegrationService;
import com.televisa.comer.integration.service.xmlutils.HeaderHandlerResolver;
import com.televisa.integration.model.types.EvetvSpotsVtaTradicional;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
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
/** Esta clase implementa la logica de Venta Tradicional e invoca a neptuno<br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Marzo 10, 2018, 12:00 pm
 */
public class IntegrationDasVentaTradicionalUsr implements VentaTradicionalInterface {
    
    @Override
    @WebMethod
    public TraditionalResponse invokeTraditionalSale(TraditionalInputParameters psTraditionalSale, 
                                                     String tsDateStrt,
                                                     String tsDateEnd,
                                                     String tsCodeTrace,
                                                     Integer tiIdReqQue) {
        TraditionalResponse    loTraditionalResponse = new TraditionalResponse();
        String                 lsIdRequestReq = psTraditionalSale.getIdRequestTraditionalReq();
        String                 lsStatusType = psTraditionalSale.getDateQuery();//Funge como Status SC o PN
        String                 lsIdServiceReq = psTraditionalSale.getIdService();
        String                 lsIdUserNameReq = psTraditionalSale.getUserName();
        String                 lsIdUserReq = psTraditionalSale.getIdUser();
        String                 lsOrdIdEvetv = "0";
        Integer                liIndProcess = 0;    
        EntityMappedDao        loEntityMappedDao = new EntityMappedDao();
        VentaTradicionalDao    loVentaTradicionalDao = new VentaTradicionalDao();
        String                 lsChannelAll = "";
        List<Channel>          loChnllCol = psTraditionalSale.getChannelList().getChannels();
        for(Channel loChannel: loChnllCol){
            lsChannelAll = lsChannelAll + "'" + loChannel.getChannel() + "',";
        }
        
        String lsMessage = "";
        String lsResult = "";
        for (Channel loChannel : loChnllCol) {
            try{
                ResponseUpdDao loResponseUpdDao = 
                    loVentaTradicionalDao.callTraditionalSalePr(loChannel.getChannel(),
                                                                tsDateStrt,
                                                                tsDateEnd
                                                                );
                lsResult = loResponseUpdDao.getLsResponse();
                lsMessage = "callParrillasOnDemandPr Resultado: " + loResponseUpdDao.getLsMessage();
                liIndProcess = new UtilFaces().getIdConfigParameterByName("ExeProcedure");
            }catch(Exception loExp){
                lsMessage = "callParrillasOnDemandPr: " + loExp.getMessage();
                liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
            }
            new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestReq), 
                                                         Integer.parseInt(lsIdServiceReq), 
                                                         liIndProcess, 
                                                         lsMessage,
                                                         0, 
                                                         Integer.parseInt(lsIdRequestReq), 
                                                         Integer.parseInt(lsIdUserReq),
                                                         lsIdUserNameReq
                                                         );
        }
        
        //System.out.println("Llamando a Procedimiento callTraditionalSalePr......");
        //ResponseUpdDao loResCall = loVentaTradicionalDao.callTraditionalSalePr();        
        if(lsResult.equalsIgnoreCase("OK")){
           List<EvetvSpotsVtaTradicional> laTraditionalSale = 
                loVentaTradicionalDao.getAllTraditionaSaleSpots(lsStatusType);
            
            liIndProcess = new UtilFaces().getIdConfigParameterByName("ExtractData");
            new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestReq), 
                                                         Integer.parseInt(lsIdServiceReq), 
                                                         liIndProcess, 
                                                         "Extraccion de Informacion para Servicio de Venta " +
                                                         "Tradicional Por Usuario [" + lsStatusType + "]",
                                                         0, 
                                                         Integer.parseInt(lsIdRequestReq), 
                                                         Integer.parseInt(lsIdUserReq),
                                                         lsIdUserNameReq
                                                         );     
            for(EvetvSpotsVtaTradicional loVta: laTraditionalSale){
                loVentaTradicionalDao.insertVtaTradicionalCtrl(lsIdRequestReq, 
                                                               lsIdServiceReq, 
                                                               lsIdUserNameReq, 
                                                               lsIdUserReq, 
                                                               loVta
                                                               );
            }
            if(laTraditionalSale.size() > 0){
                DocumentBuilderFactory loDocFactory = DocumentBuilderFactory.newInstance();
                try {
                    DocumentBuilder loDocBuilder = loDocFactory.newDocumentBuilder();
                    Document        loDocument = loDocBuilder.newDocument();
                    Element         loRoot = loDocument.createElement("RAIZ");
                    Element         loOrdenesServicioVtaTra = loDocument.createElement("OrdenesServicioVtaTra");
                    Element         loMode = loDocument.createElement("Mode");
                    String          lsAsyn = 
                        loEntityMappedDao.getAsyncService(lsIdServiceReq) == "0" ? "N" : "S";
                    //loMode.appendChild(poDocument.createTextNode("S"));                    
                    loMode.appendChild(loDocument.createTextNode(lsAsyn));                    
                    loOrdenesServicioVtaTra.appendChild(loMode);         
                    
                    Element         loCodeTrace = loDocument.createElement("CodeTrace");
                    loCodeTrace.appendChild(loDocument.createTextNode(tsCodeTrace));  
                    loOrdenesServicioVtaTra.appendChild(loCodeTrace);         
                    
                    Element loCommercialList = loDocument.createElement("CommercialList");
                    //LA CAMPA�A SE PUEDE REPETIR............
                    List<TraditionalMapCampaignHeader> laVenTr = new ArrayList<TraditionalMapCampaignHeader>();
                    for(EvetvSpotsVtaTradicional loTraditionalSale : laTraditionalSale){
                        TraditionalMapCampaignHeader loVtr = new TraditionalMapCampaignHeader();
                        loVtr.setLsActionC(loTraditionalSale.getLsActionC());
                        loVtr.setLsOrderID(loTraditionalSale.getLsOrdid());
                        loVtr.setLsAgencyID(loTraditionalSale.getLsAgyid());
                        loVtr.setLsAdvertirserID(loTraditionalSale.getLsAdvid());
                        loVtr.setLsInitialDate(loTraditionalSale.getLsStrdt());
                        loVtr.setLsEndDate(loTraditionalSale.getLsEdt());
                        loVtr.setLsMasterContract(loTraditionalSale.getLsMcontid());
                        loVtr.setLsRateCard(loTraditionalSale.getLsRtcrd());
                        loVtr.setLsTargetID(loTraditionalSale.getLsIdtarget());
                        laVenTr.add(loVtr);
                    }
                    List<TraditionalMapCampaignHeader> laVenTrHdr = 
                        UtilsIntegrationService.removeTraditionalHeaderRepeated(laVenTr);
                    //Armar XML - REQUEST
                    for(int liI = 0; liI<laVenTrHdr.size(); liI++){
                        Element loCampaign = loDocument.createElement("Campaign");                    
                        String lsOrderID = 
                            laVenTrHdr.get(liI).getLsOrderID() == null ? "NULL" : 
                            laVenTrHdr.get(liI).getLsOrderID().toString();
                        lsOrdIdEvetv = lsOrderID;
                        String lsAgencyID = 
                            laVenTrHdr.get(liI).getLsAgencyID() == null ? "NULL" : 
                            laVenTrHdr.get(liI).getLsAgencyID().toString();
                        String lsAdvertirserID = 
                            laVenTrHdr.get(liI).getLsAdvertirserID() == null ? "NULL" :
                            laVenTrHdr.get(liI).getLsAdvertirserID().toString();
                        String lsInitialDate = 
                            laVenTrHdr.get(liI).getLsInitialDate() == null ? "NULL" : 
                            laVenTrHdr.get(liI).getLsInitialDate().toString();
                        String lsEndDate = 
                            laVenTrHdr.get(liI).getLsEndDate() == null ? "NULL" : 
                            laVenTrHdr.get(liI).getLsEndDate().toString();
                        String lsMasterContract = 
                            laVenTrHdr.get(liI).getLsMasterContract() == null ? "NULL" : 
                            laVenTrHdr.get(liI).getLsMasterContract().toString();
                        String lsRateCard = 
                            laVenTrHdr.get(liI).getLsRateCard() == null ? "NULL" :
                            laVenTrHdr.get(liI).getLsRateCard().toString();
                        String lsTargetID = 
                            laVenTrHdr.get(liI).getLsTargetID() == null ? "NULL" : 
                            laVenTrHdr.get(liI).getLsTargetID().toString();
                        String lsActionC = 
                            laVenTrHdr.get(liI).getLsActionC() == null ? "NULL" : 
                            laVenTrHdr.get(liI).getLsActionC().toString();
                        Element loActionC = loDocument.createElement("Action");
                        loActionC.appendChild(loDocument.createTextNode(laVenTrHdr.get(liI).getLsActionC()));
                        Element loOrderID = loDocument.createElement("OrderID");
                        loOrderID.appendChild(loDocument.createTextNode(laVenTrHdr.get(liI).getLsOrderID()));                    
                        Element loAgencyID = loDocument.createElement("AgencyID");
                        loAgencyID.appendChild(loDocument.createTextNode(laVenTrHdr.get(liI).getLsAgencyID()));                    
                        Element loAdvertirserID = loDocument.createElement("AdvertirserID");
                        loAdvertirserID.appendChild(loDocument.createTextNode(
                                                    laVenTrHdr.get(liI).getLsAdvertirserID()));
                        Element loInitialDate = loDocument.createElement("InitialDate");
                        String lsFecIni = buildDate(laVenTrHdr.get(liI).getLsInitialDate(),"MM/dd/YY");
                        loInitialDate.appendChild(loDocument.createTextNode(lsFecIni));                    
                        Element loEndDate = loDocument.createElement("EndDate");
                        String lsFecFin = buildDate(laVenTrHdr.get(liI).getLsEndDate(),"MM/dd/YY");
                        loEndDate.appendChild(loDocument.createTextNode(lsFecFin));
                        Element loMasterContract = loDocument.createElement("MasterContract");
                        loMasterContract.appendChild(loDocument.createTextNode(
                                                     laVenTrHdr.get(liI).getLsMasterContract()));
                        Element loRateCard = loDocument.createElement("RateCard");
                        loRateCard.appendChild(loDocument.createTextNode(laVenTrHdr.get(liI).getLsRateCard()));
                        Element loTargetID = loDocument.createElement("TargetID");
                        loTargetID.appendChild(loDocument.createTextNode(laVenTrHdr.get(liI).getLsTargetID()));
                        
                        loCampaign.appendChild(loActionC);
                        loCampaign.appendChild(loOrderID);
                        loCampaign.appendChild(loAgencyID);
                        loCampaign.appendChild(loAdvertirserID);                    
                        loCampaign.appendChild(loInitialDate);                    
                        loCampaign.appendChild(loEndDate);
                        loCampaign.appendChild(loMasterContract);
                        loCampaign.appendChild(loRateCard);
                        loCampaign.appendChild(loTargetID);
                        
                        Element loChannel = loDocument.createElement("Channel");                                               
                    
                        for(Channel loChannelBean: loChnllCol){
                            Element loChannelID = loDocument.createElement("ChannelID");
                            loChannelID.appendChild(loDocument.createTextNode(loChannelBean.getChannel()));
                            loChannel.appendChild(loChannelID);
                        }
                        loCampaign.appendChild(loChannel);
                                                
                        for(EvetvSpotsVtaTradicional loTraditionalSale : laTraditionalSale){                        
                            String lsOrderIDSpot = 
                                loTraditionalSale.getLsOrdid() == null ? "NULL" : 
                                loTraditionalSale.getLsOrdid().toString();
                            String lsAgencyIDSpot = 
                                loTraditionalSale.getLsAgyid() == null ? "NULL" : 
                                loTraditionalSale.getLsAgyid().toString();
                            String lsAdvertirserIDSpot = 
                                loTraditionalSale.getLsAdvid() == null ? "NULL" :
                                loTraditionalSale.getLsAdvid().toString();
                            String lsInitialDateSpot = 
                                loTraditionalSale.getLsStrdt() == null ? "NULL" : 
                                loTraditionalSale.getLsStrdt().toString();
                            String lsEndDateSpot = 
                                loTraditionalSale.getLsEdt() == null ? "NULL" : 
                                loTraditionalSale.getLsEdt().toString();
                            String lsMasterContractSpot = 
                                loTraditionalSale.getLsMcontid() == null ? "NULL" : 
                                loTraditionalSale.getLsMcontid().toString();
                            String lsRateCardSpot = 
                                loTraditionalSale.getLsRtcrd() == null ? "NULL" :
                                loTraditionalSale.getLsRtcrd().toString();
                            String lsTargetIDSpot = 
                                loTraditionalSale.getLsIdtarget() == null ? "NULL" : 
                                loTraditionalSale.getLsIdtarget().toString();
                            
                            String lsActionCSpot = 
                                loTraditionalSale.getLsActionC() == null ? "NULL" : 
                                loTraditionalSale.getLsActionC().toString();
                            
                            if(lsOrderIDSpot.equalsIgnoreCase(lsOrderID) &&
                                lsAgencyIDSpot.equalsIgnoreCase(lsAgencyID) &&
                                lsAdvertirserIDSpot.equalsIgnoreCase(lsAdvertirserID) &&
                                lsInitialDateSpot.equalsIgnoreCase(lsInitialDate) &&
                                lsEndDateSpot.equalsIgnoreCase(lsEndDate) &&
                                lsMasterContractSpot.equalsIgnoreCase(lsMasterContract) &&
                                lsRateCardSpot.equalsIgnoreCase(lsRateCard) &&
                                lsTargetIDSpot.equalsIgnoreCase(lsTargetID) &&
                                lsActionCSpot.equalsIgnoreCase(lsActionC)
                              ){
                                Element loSpot = loDocument.createElement("Spot");
                                Element loActionSpot = loDocument.createElement("Action");
                                loActionSpot.appendChild(loDocument.createTextNode(loTraditionalSale.getLsAction()));
                                Element loSpotID = loDocument.createElement("SpotID");
                                loSpotID.appendChild(loDocument.createTextNode(loTraditionalSale.getLsSptmstid()));
                                Element loDate = loDocument.createElement("Date");
                                String lsFecSpot = buildDate(loTraditionalSale.getLsBcstdt(),"MM/dd/YY");
                                loDate.appendChild(loDocument.createTextNode(lsFecSpot));
                                Element loBuyUnitID = loDocument.createElement("BuyUnitID");
                                loBuyUnitID.appendChild(loDocument.createTextNode(loTraditionalSale.getLsBuyuntid()));
                                Element loBreakID = loDocument.createElement("BreakID");
                                loBreakID.appendChild(loDocument.createTextNode(loTraditionalSale.getLsBrkdtid()));
                                Element loHour = loDocument.createElement("Hour");
                                loHour.appendChild(loDocument.createTextNode(loTraditionalSale.getLsActtim()));
                                Element loDuration = loDocument.createElement("Duration");
                                loDuration.appendChild(loDocument.createTextNode(loTraditionalSale.getLsSptlen()));
                                Element loChannelID = loDocument.createElement("ChannelID");
                                loChannelID.appendChild(loDocument.createTextNode(loTraditionalSale.getLsStnid_Spot()));
                                Element loTitleID = loDocument.createElement("TitleID");
                                loTitleID.appendChild(loDocument.createTextNode(loTraditionalSale.getLsTitleid()));
                                Element loEpisodeNameID = loDocument.createElement("EpisodeNameID");
                                loEpisodeNameID.appendChild(loDocument.createTextNode(
                                                            loTraditionalSale.getLsEpisodenameid()));
                                Element loTipoPublicidadID = loDocument.createElement("TipoPublicidadID");
                                loTipoPublicidadID.appendChild(loDocument.createTextNode(
                                                               loTraditionalSale.getLsTipopublicidadid()));
                                Element loSpotFormatID = loDocument.createElement("SpotFormatID");
                                loSpotFormatID.appendChild(loDocument.createTextNode(
                                                           loTraditionalSale.getLsSpotformatid()));
                                Element loSpotComments = loDocument.createElement("SpotComments");
                                loSpotComments.appendChild(loDocument.createTextNode(
                                                           loTraditionalSale.getLsSpotcomments()));
                                Element loMediaID = loDocument.createElement("MediaID");
                                loMediaID.appendChild(loDocument.createTextNode(loTraditionalSale.getLsAutoid()));
                                Element loProductID = loDocument.createElement("ProductID");
                                loProductID.appendChild(loDocument.createTextNode(loTraditionalSale.getLsBrnd()));
                                Element loSpotPrice = loDocument.createElement("SpotPrice");
                                loSpotPrice.appendChild(loDocument.createTextNode(loTraditionalSale.getLsSptrt()));
                                Element loSpotPrice20 = loDocument.createElement("SpotPrice20");
                                loSpotPrice20.appendChild(loDocument.createTextNode(
                                                          loTraditionalSale.getLsSpotprice20()));
                                Element loSpotPriceProduction = loDocument.createElement("SpotPriceProduction");
                                loSpotPriceProduction.appendChild(loDocument.createTextNode(
                                                                  loTraditionalSale.getLsSpotpriceproduction()));
                                Element loRevenue = loDocument.createElement("Revenue");
                                loRevenue.appendChild(loDocument.createTextNode(loTraditionalSale.getLsRevsts()));
                                
                                loSpot.appendChild(loActionSpot);
                                loSpot.appendChild(loSpotID);    
                                loSpot.appendChild(loDate);
                                loSpot.appendChild(loBuyUnitID);
                                loSpot.appendChild(loBreakID);
                                loSpot.appendChild(loHour);
                                loSpot.appendChild(loDuration);
                                loSpot.appendChild(loChannelID);
                                loSpot.appendChild(loTitleID);
                                loSpot.appendChild(loEpisodeNameID);
                                loSpot.appendChild(loTipoPublicidadID);
                                loSpot.appendChild(loSpotFormatID);
                                loSpot.appendChild(loSpotComments);
                                loSpot.appendChild(loMediaID);
                                loSpot.appendChild(loProductID);
                                loSpot.appendChild(loSpotPrice);
                                loSpot.appendChild(loSpotPrice20);
                                loSpot.appendChild(loSpotPriceProduction);
                                loSpot.appendChild(loRevenue);
                                loCampaign.appendChild(loSpot);
                            }
                        }                        
                        loCommercialList.appendChild(loCampaign);
                        
                    }
                    /***********************************************************/
                    loOrdenesServicioVtaTra.appendChild(loCommercialList);
                    loRoot.appendChild(loOrdenesServicioVtaTra);
                    // Aqui cerraria el canal
                    try{
                        loDocument.appendChild(loRoot);
                        TransformerFactory transformerFactory =
                        TransformerFactory.newInstance();
                        Transformer loTransformer =
                        transformerFactory.newTransformer();
                        loTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
                        DOMSource loSource = new DOMSource(loDocument);                    
                        ByteArrayOutputStream loBaos = new ByteArrayOutputStream();
                        loTransformer.transform(loSource, new StreamResult(loBaos));
                        InputStream loFileXml = new ByteArrayInputStream(loBaos.toByteArray()); 
                        String lsNomFile = "VentaTradicionalUsr-"+getId()+".xml";
                        loEntityMappedDao.insertEvetvIntXmlFilesTab(Integer.parseInt(lsIdRequestReq), 
                                                                    Integer.parseInt(lsIdServiceReq), 
                                                                    lsNomFile, 
                                                                    "REQUEST", 
                                                                    "WsVentaTradicionalUsr", 
                                                                    "A", 
                                                                    lsIdUserNameReq, 
                                                                    loFileXml);
                        
                    } catch (TransformerException loExTfe) {
                         liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                        new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestReq), 
                                                                     Integer.parseInt(lsIdServiceReq), 
                                                                     liIndProcess, 
                                                                     "Error al generar Archivo XML-Request",
                                                                     0, 
                                                                     Integer.parseInt(lsIdRequestReq), 
                                                                     Integer.parseInt(lsIdUserReq),
                                                                     lsIdUserNameReq
                                                                     );
                    }catch (Exception loEx) {
                         liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                        new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestReq), 
                                                                     Integer.parseInt(lsIdServiceReq), 
                                                                     liIndProcess, 
                                                                     "Error al generar Archivo XML-Request",
                                                                     0, 
                                                                     Integer.parseInt(lsIdRequestReq), 
                                                                     Integer.parseInt(lsIdUserReq),
                                                                     lsIdUserNameReq
                                                                     );
                    }
                    
                    liIndProcess = new UtilFaces().getIdConfigParameterByName("InvokingNeptuno");
                    new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestReq), 
                                                                 Integer.parseInt(lsIdServiceReq), 
                                                                 liIndProcess, 
                                                                 "Servicio Neptuno invocado para Venta Tradicional Por Usuario",
                                                                 0, 
                                                                 Integer.parseInt(lsIdRequestReq), 
                                                                 Integer.parseInt(lsIdUserReq),
                                                                 lsIdUserNameReq
                                                                 );   
                    //########## INVOCAR A NEPTUNO ###############
                    new UtilFaces().insertLogServiceService(Integer.parseInt(lsIdRequestReq), 
                                                            Integer.parseInt(lsIdServiceReq), 
                                                            0, 
                                                            "N", 
                                                            0, 
                                                            Integer.parseInt(lsIdRequestReq), 
                                                            "Neptuno Execution Request", 
                                                            Integer.parseInt(lsIdUserReq), 
                                                            lsIdUserNameReq, 
                                                            "WsVentaTradicionalUsr"
                                                            );
                    
                    
                    WSNeptuno loWsNeptuno = 
                        new UtilsIntegrationService().getServiceNeptunoDynamic();
                    HeaderHandlerResolver loHandlerResolver = new HeaderHandlerResolver();
                    loWsNeptuno.setHandlerResolver(loHandlerResolver);
                    WSNeptunoSoap loWsNeptunoSoap = loWsNeptuno.getWSNeptunoSoap();
                    RecibirDatosExternos.Xmlentrada loXmlentrada = new RecibirDatosExternos.Xmlentrada();
                    boolean lbEsOffline = false;
                    loXmlentrada.getContent().add(loRoot);      
                    RecibirDatosExternosResponse.RecibirDatosExternosResult loRder = 
                        loWsNeptunoSoap.recibirDatosExternos(loXmlentrada, lbEsOffline);
                    new UtilFaces().updateLogServiceService(Integer.parseInt(lsIdRequestReq), 
                                                            Integer.parseInt(lsIdServiceReq), 
                                                            0, 
                                                            "N", 
                                                            0, 
                                                            Integer.parseInt(lsIdUserReq), 
                                                            Integer.parseInt(lsIdRequestReq), 
                                                            "Neptuno Execution Response", 
                                                            Integer.parseInt(lsIdUserReq), 
                                                            lsIdUserNameReq
                                                            );
                    liIndProcess = new UtilFaces().getIdConfigParameterByName("FinishResponseNeptuno");
                    new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestReq), 
                                                                  Integer.parseInt(lsIdServiceReq), 
                                                                  liIndProcess, 
                                                                  "Respuesta de Neptuno Obtenida para Venta Tradicional Por Usuario",
                                                                  0, 
                                                                  Integer.parseInt(lsIdRequestReq), 
                                                                  Integer.parseInt(lsIdUserReq),
                                                                  lsIdUserNameReq
                                                                  );     
                    
                    //################ Inicia Segunda fase (respuesta de evetv) ##############
                    CodRespuesta loResp;
                    try {
                        Integer liIndProcessRes = 0;
                        loResp = convertResponse(loRder);
                        List<CodRespuesta.ItemCabecera> laCabs = loResp.getItemCabecera();
                        for(CodRespuesta.ItemCabecera loCab : laCabs) {
                            CodRespuesta.ItemCabecera.ItemRespuesta loIRes = loCab.getItemRespuesta();
                            if(loIRes != null) {                                
                                String lsElemento = loIRes.getElemento();
                                String lsOrdId = null;
                                String lsSpotId = null;
                                if(lsElemento != null && lsElemento.equalsIgnoreCase("SpotID")) {
                                    //5.- Por cada ItemCabecera Actualizar Estatus
                                    String lsIdElemento = loIRes.getIdElemento(); //Update al split[OrderID]
                                    
                                    String[] laIdElementos = lsIdElemento.split(":");
                                    if(laIdElementos.length > 1){ 
                                        String[] lsCadOrd = laIdElementos[1].split(" ");
                                        if(lsCadOrd.length>0){
                                            lsOrdId = lsCadOrd[0].trim();
                                        }else{
                                            lsCadOrd = laIdElementos[1].split("-");
                                            if(lsCadOrd.length>0){
                                                lsOrdId = lsCadOrd[0].trim();
                                            }
                                        }
                                        lsSpotId = laIdElementos[2].trim();
                                    }                                        
                                }
                                String lsIdError = "";
                                String lsDescError = "";
                                if(lsOrdId != null && lsSpotId != null){
                                    String lsResultado = loIRes.getResultado();
                                    String lsResUpd = "E";
                                    if(lsResultado.equalsIgnoreCase("OK")){
                                        lsResUpd = "T";
                                    }else{
                                        lsIdError = loIRes.getListaMensaje().get(0).getIdError();
                                        lsDescError = loIRes.getListaMensaje().get(0).getDescripcion();
                                    }
                                    
                                    //Actualizar Tabla Control TVSA
                                    loVentaTradicionalDao.updateVtaTradicionalHdr(lsOrdId, lsSpotId, lsResUpd);
                                    //Actualizar Tabla Control Integration
                                    loVentaTradicionalDao.updateVtaTradicionalCtrl(lsIdRequestReq, 
                                                                                   lsIdServiceReq, 
                                                                                   lsOrdId, 
                                                                                   lsSpotId, 
                                                                                   lsResUpd,
                                                                                   lsDescError
                                                                                   );
                                }
                            }
                        }
                        liIndProcessRes = new UtilFaces().getIdConfigParameterByName("UpdateCtrlStatus");
                        new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestReq), 
                                                                     Integer.parseInt(lsIdServiceReq), 
                                                                     liIndProcessRes, 
                                                                     "Actualizacion de Status en Tablas de Control " +
                                                                     "para Venta Tradicional Por Usuario",
                                                                     0, 
                                                                     Integer.parseInt(lsIdRequestReq), 
                                                                     Integer.parseInt(lsIdUserReq),
                                                                     lsIdUserNameReq
                                                                     );     
                    } catch (Exception loEx) {
                        System.out.println("Error JAXBException "+loEx.getMessage());
                    }
                    
                    loTraditionalResponse.setIdRequestTraditionalRes(lsIdRequestReq);
                    XmlMessageResponseCollection loXmlMsCol = new XmlMessageResponseCollection();
                    XmlMessageReqRes loReqRes = new XmlMessageReqRes();               
                    loReqRes.setXmlMessageRequest("FIN REQUEST");       
                    
                    try{
                        ByteArrayOutputStream loBaosRes = new ByteArrayOutputStream();
                        JAXB.marshal(loRder, loBaosRes);
                        InputStream loFileXmlRes = new ByteArrayInputStream(loBaosRes.toByteArray()); 
                        String lsNomFile = "VentaTradicional-"+getId()+".xml";
                        loEntityMappedDao.insertEvetvIntXmlFilesTab(Integer.parseInt(lsIdRequestReq), 
                                                                    Integer.parseInt(lsIdServiceReq), 
                                                                    lsNomFile, 
                                                                    "RESPONSE", 
                                                                    "WsVentaTradicionalUsr", 
                                                                    "A", 
                                                                    lsIdUserNameReq, 
                                                                    loFileXmlRes);
                    }catch(Exception loEx){
                        liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                        new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestReq), 
                                                                      Integer.parseInt(lsIdServiceReq), 
                                                                      liIndProcess, 
                                                                      "Error al generar Archivo XML-Response",
                                                                      0, 
                                                                      Integer.parseInt(lsIdRequestReq), 
                                                                      Integer.parseInt(lsIdUserReq),
                                                                      lsIdUserNameReq
                                                                      );
                    }
                    
                    loReqRes.setXmlMessageResponse("FIN RESPONSE");                
                    loXmlMsCol.getXmlMessageReqRes().add(loReqRes);                    
                    loTraditionalResponse.setXmlMessageResponse(loXmlMsCol);
                } catch (ParserConfigurationException loEx) {                
                    loTraditionalResponse.setError(loEx.getMessage());
                    liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                    new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestReq), 
                                                                  Integer.parseInt(lsIdServiceReq), 
                                                                  liIndProcess, 
                                                                  loEx.getMessage(),
                                                                  0, 
                                                                  Integer.parseInt(lsIdRequestReq), 
                                                                  Integer.parseInt(lsIdUserReq),
                                                                  lsIdUserNameReq
                                                                  );     
                } 
            }else{
                loTraditionalResponse.setError("Sin Informacion Para Enviar");
                liIndProcess = new UtilFaces().getIdConfigParameterByName("NoDataToSend");
                new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestReq), 
                                                              Integer.parseInt(lsIdServiceReq), 
                                                              liIndProcess, 
                                                              "Sin Informacion Para Enviar",
                                                              0, 
                                                              Integer.parseInt(lsIdRequestReq), 
                                                              Integer.parseInt(lsIdUserReq),
                                                              lsIdUserNameReq
                                                              );    
            }
        }else{
            loTraditionalResponse.setError(lsResult);
            liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
            new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestReq), 
                                                          Integer.parseInt(lsIdServiceReq), 
                                                          liIndProcess, 
                                                          lsMessage,
                                                          0, 
                                                          Integer.parseInt(lsIdRequestReq), 
                                                          Integer.parseInt(lsIdUserReq),
                                                          lsIdUserNameReq
                                                          );     
        }
        return loTraditionalResponse;
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
    
    /**
     * Construye en formato de fecha aceptado por paradigm
     * @autor Jorge Luis Bautista Santiago  
     * @param tsDate
     * @param tsInMask
     * @return String
     */
    private String buildDate(String tsDate, String tsInMask){
        String   lsResponse = null;
        String[] laArrDate = tsDate.split("/");
        if(laArrDate.length > 0){
            String lsMonth = laArrDate[0];
            String lsDay = laArrDate[1];
            String lsYear = convierteFecha(tsDate,tsInMask,"YYYY");
            lsResponse = lsDay+"/"+lsMonth+"/"+lsYear;
        }
        return lsResponse;
    }

    /**
     * Convierte respuesta a clase java
     * @autor Jorge Luis Bautista Santiago  
     * @param toResp
     * @return CodRespuesta
     */
    private CodRespuesta convertResponse(RecibirDatosExternosResult toResp) 
                                                       throws JAXBException {
        JAXBContext  loJAXBContext = 
            JAXBContext.newInstance(RecibirDatosExternosResult.class);
        Marshaller   loJAXBMarshaller = loJAXBContext.createMarshaller();
        loJAXBMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter loSW = new StringWriter();
        loJAXBMarshaller.marshal(toResp, loSW);
        CodRespuesta loResp = 
            JAXB.unmarshal(new StringReader(loSW.toString()), CodRespuesta.class);
        return loResp;
    }
    
    /**
     * Obtiene identificador en base al momento de creacion de instancia
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
