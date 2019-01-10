 /**
 * Project: Integraton Paradigm - EveTV
 *
 * File: IntegrationDasParrillasOnDemand.java
 *
 * Created on: Febrero 16, 2018 at 11:00
 *
 * Copyright (c) - OMW - 2018
 */
package com.televisa.comer.integration.service.implementation;

import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.beans.RsstParrillasCortesBean;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.model.daos.ParrillasCortesOnDemDao;
import com.televisa.comer.integration.service.beans.parrillascortes.Channel;
import com.televisa.comer.integration.service.beans.parrillascortes.ParrillasCortesInputParameters;
import com.televisa.comer.integration.service.beans.parrillascortes.ParrillasCortesResponse;
import com.televisa.comer.integration.service.beans.parrillascortes.XmlMessageReqRes;
import com.televisa.comer.integration.service.beans.parrillascortes.XmlMessageResponseCollection;
import com.televisa.comer.integration.service.beans.types.EvetvParrillasProcesadasBean;
import com.televisa.comer.integration.service.beans.types.ParrillaCorteMapChannels;
import com.televisa.comer.integration.service.beans.types.ParrillaCorteMapCorteBreaks;
import com.televisa.comer.integration.service.beans.types.ParrillaCorteMapCortes;
import com.televisa.comer.integration.service.beans.types.ParrillaCorteMapDates;
import com.televisa.comer.integration.service.beans.types.ParrillaCorteMapItems;
import com.televisa.comer.integration.service.beans.types.RequestResponseBean;
import com.televisa.comer.integration.service.interfaces.ParrillasCortesOnDemInterface;
import com.televisa.comer.integration.service.utils.UtilsIntegrationService;
import com.televisa.comer.integration.service.xmlutils.HeaderHandlerResolver;
import com.televisa.integration.view.front.util.UtilFaces;

import es.com.evendor.RecibirDatosExternos.Xmlentrada;
import es.com.evendor.RecibirDatosExternosResponse;
import es.com.evendor.RecibirDatosExternosResponse.RecibirDatosExternosResult;
import es.com.evendor.WSNeptuno;
import es.com.evendor.WSNeptunoSoap;
import es.com.evendor.types.CodRespuesta;
import es.com.evendor.types.RAIZ;
import es.com.evendor.types.RAIZ.Parrilla;
import es.com.evendor.types.RAIZ.Parrilla.Schedule;
import es.com.evendor.types.RAIZ.Parrilla.Schedule.Dates;
import es.com.evendor.types.RAIZ.Parrilla.Schedule.Dates.TimeBand;
import es.com.evendor.types.RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes;
import es.com.evendor.types.RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte;
import es.com.evendor.types.RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte.Breaks;
import es.com.evendor.types.RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte.Breaks.Break;
import es.com.evendor.types.RAIZ.Parrilla.Schedule.Dates.TimeBand.Items;
import es.com.evendor.types.RAIZ.Parrilla.Schedule.Dates.TimeBand.Items.Item;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import javax.jws.WebMethod;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.WebServiceContext;

/** Esta clase consulta parrillas de programas y cortes en Paradigm
 * y consume servicio web expuesto por eVeTV <br/><br/>
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Febrero 16, 2018, 12:00 pm
 */
 public class IntegrationDasParrillasOnDemand implements ParrillasCortesOnDemInterface{
     
     /**
      * Obtiene informacion de parrillas y cortes en Paradigm
      * @autor Jorge Luis Bautista Santiago
      * @param tsParrillasCortes
      * @return ParrillasCortesResponse
      */     
     @Resource
     WebServiceContext loWsCtxt;
     
     @Override
     @WebMethod
     public ParrillasCortesResponse invokeParrillasProgramasyCortes(ParrillasCortesInputParameters toParrillasCortes,
                                                                    String tsDateStrt,
                                                                    String tsDateEnd,
                                                                    String tsBuyunit,
                                                                    String tsCodeTrace) {
         ParrillasCortesResponse loParrillasRes = new ParrillasCortesResponse();
         
         loParrillasRes.setIdRequestParrillasCortesRes(toParrillasCortes.getIdRequestParrillasCortesReq());         
         String                       lsChannelAll = "";
         String                       lsIdRequestParrillasReq = toParrillasCortes.getIdRequestParrillasCortesReq();
         String                       lsIdServiceReq = toParrillasCortes.getIdService();
         String                       lsIdUserNameReq = toParrillasCortes.getUserName();
         String                       lsIdUserReq = toParrillasCortes.getIdUser();
         Integer                      liIndProcess = 0;
         String                       lsMessage = null;
         boolean                      lbFlagConError = false;
         ParrillasCortesOnDemDao      loPpyCorDao = new ParrillasCortesOnDemDao();
         List<Channel>                loChnllCol =
             toParrillasCortes.getChannelList().getChannels();
         for (Channel loChannel : loChnllCol) {             
             lsChannelAll = lsChannelAll + "'" + loChannel.getChannel() + "',";
         }
         //1.- Verificar si tabla EVENTAS.EVETV_PARRILLAS_PROCESADAS tiene uno o mas registros
         Integer                 liFlag = loPpyCorDao.getFlagParrillasProcesadas();
         liIndProcess = new UtilFaces().getIdConfigParameterByName("FlagDataParrillas");
         new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                      Integer.parseInt(lsIdServiceReq), 
                                                      liIndProcess, 
                                                      "Bandera Parrillas Procesadas [" + liFlag + "] OnDemand",
                                                      0, 
                                                      Integer.parseInt(lsIdRequestParrillasReq), 
                                                      Integer.parseInt(lsIdUserReq),
                                                      lsIdUserNameReq
                                                      ); 
         
         List<RsstParrillasCortesBean> loParrillasCortes = new ArrayList<RsstParrillasCortesBean>();
         
         if (liFlag > 0) {               

             System.out.println("2.- EVENTAS.EVETV_PARRILLAS_PROCESADAS - FLAG > 0");
             for (Channel loChannel : loChnllCol) {
                 try{
                     ResponseUpdDao loResponseUpdDao = 
                         loPpyCorDao.callParrillasOnDemandPr(loChannel.getChannel(),
                                                             tsDateStrt,
                                                             tsDateEnd,
                                                             tsBuyunit
                                                             );
                     lsMessage = "callParrillasOnDemandPr Resultado: " + loResponseUpdDao.getLsMessage();
                     liIndProcess = new UtilFaces().getIdConfigParameterByName("ExeProcedure");
                 }catch(Exception loExp){
                     lsMessage = "callParrillasOnDemandPr: " + loExp.getMessage();
                     liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                 }
                 new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                              Integer.parseInt(lsIdServiceReq), 
                                                              liIndProcess, 
                                                              lsMessage,
                                                              0, 
                                                              Integer.parseInt(lsIdRequestParrillasReq), 
                                                              Integer.parseInt(lsIdUserReq),
                                                              lsIdUserNameReq
                                                              );
             }
             
             //4.- Extraer de Paradigm y enviar a evetv
             System.out.println("4.- Extraer de Paradigm y enviar a evetv onDemand");
             try {
                loParrillasCortes =
                    loPpyCorDao.getParrillasOnDemandParadigmDb(lsChannelAll.substring(0, lsChannelAll.length() - 1),
                                                               tsDateStrt,
                                                               tsDateEnd,
                                                               tsBuyunit
                                                              );
                lsMessage = "Extraccion de Informacion para Servicio de Parrillas onDemand";
             } catch (SQLException loEx) {
                 System.out.println("ERROR 4 "+loEx.getMessage());
                lsMessage = loEx.getMessage();
             }
             System.out.println("4.- Extraer de Paradigm y enviar a evetv onDemand.....FIN");
             liIndProcess = new UtilFaces().getIdConfigParameterByName("ExtractData");
             new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                          Integer.parseInt(lsIdServiceReq), 
                                                          liIndProcess, 
                                                          lsMessage,
                                                          0, 
                                                          Integer.parseInt(lsIdRequestParrillasReq), 
                                                          Integer.parseInt(lsIdUserReq),
                                                          lsIdUserNameReq
                                                          );     
             System.out.println("insertar en tablas de control de omw");
             for (RsstParrillasCortesBean loParrilla : loParrillasCortes){
                 //###################### INSERTAR TABLA DE CONTROL ##############################    
                 loPpyCorDao.insertEveTvParrillas(lsIdRequestParrillasReq, 
                                                  lsIdServiceReq, 
                                                  "A",
                                                  lsIdUserNameReq,
                                                  lsIdUserReq,
                                                  loParrilla
                                                  );
                 //###################### FIN INSERTAR TABLA DE CONTROL ##############################
             }   
             System.out.println("Armar XML");
             List<ParrillaCorteMapChannels>    loListAllChannels =
                 new ArrayList<ParrillaCorteMapChannels>();
             List<ParrillaCorteMapDates>       loListAllDates =
                 new ArrayList<ParrillaCorteMapDates>();
             List<ParrillaCorteMapItems>       loListAllDateItems =
                 new ArrayList<ParrillaCorteMapItems>();
             List<ParrillaCorteMapCortes>      loListAllDateCortes =
                 new ArrayList<ParrillaCorteMapCortes>();
             List<ParrillaCorteMapCorteBreaks> loListAllDateCorteBreaks =
                 new ArrayList<ParrillaCorteMapCorteBreaks>();
             int                               liCount = 0;
             String                            lsTitles = "";
             for (RsstParrillasCortesBean loParrilla : loParrillasCortes) {
                 //Channels Nivel 1
                 liCount++;
                 String lsTitle = loParrilla.getLsTitle();
                 if(lsTitle != null && !lsTitle.equals("")) {
                     lsTitles += loParrilla.getLsTitle();
                 }
                 if(loParrilla.getLsHourstart() == null || 
                    loParrilla.getLsHourend().trim().equals("") ||
                    loParrilla.getLsHourend() == null || 
                    loParrilla.getLsHourend().trim().equals("")) {
                        continue;
                 }
                 ParrillaCorteMapChannels loMapChannel =
                     new ParrillaCorteMapChannels();
                 loMapChannel.setLsChannel(loParrilla.getLsChannelid().trim());
                 loListAllChannels.add(loMapChannel);
                 //Channel-Dates Nivel 2
                 ParrillaCorteMapDates loMapDates = new ParrillaCorteMapDates();
                 loMapDates.setLsChannel(loParrilla.getLsChannelid().trim());
                 loMapDates.setLsDateValue(loParrilla.getLsDatevalue());
                 loMapDates.setLsHourStart(loParrilla.getLsHourstart());
                 loMapDates.setLsHourEnd(loParrilla.getLsHourend());
                 loListAllDates.add(loMapDates);
                 //Channel-Date-Items Nivel 3
                 ParrillaCorteMapItems loMapDateItems =
                     new ParrillaCorteMapItems();
                 loMapDateItems.setLsChannel(loParrilla.getLsChannelid().trim());
                 loMapDateItems.setLsDateValue(loParrilla.getLsDatevalue());
                 loMapDateItems.setLsHourStart(loParrilla.getLsHourstart());
                 loMapDateItems.setLsHourEnd(loParrilla.getLsHourend());
                 loMapDateItems.setLsHour(loParrilla.getLsHour());
                 loMapDateItems.setLsTitle(loParrilla.getLsTitle());
                 loMapDateItems.setLsEpisodename(loParrilla.getLsEpisodename());
                 loMapDateItems.setLsEpno(loParrilla.getLsEpno());
                 loMapDateItems.setLsSlotduration(loParrilla.getLsSlotduration());
                 loMapDateItems.setLsTitleid(loParrilla.getLsTitleid());
                 loMapDateItems.setLsEpisodenameid(loParrilla.getLsEpisodenameid());
                 loMapDateItems.setLsBuyunitid(loParrilla.getLsBuyunitid());
                 loMapDateItems.setLsBuyunit(loParrilla.getLsBuyunit());
                 loMapDateItems.setLsExceptcofepris(loParrilla.getLsExceptcofepris());
                 loMapDateItems.setLsEventspecial(loParrilla.getLsEventspecial());
                 loMapDateItems.setLsEventbest(loParrilla.getLsEventbest());
                 
                 loMapDateItems.setLsGeneroID(loParrilla.getLsGeneroID());
                 loMapDateItems.setLsGeneroName(loParrilla.getLsGeneroName());
                 
                 loListAllDateItems.add(loMapDateItems);
                 //Channel-Date-Cortes Nivel 3
                 ParrillaCorteMapCortes loMapDateCortes =
                     new ParrillaCorteMapCortes();
                 loMapDateCortes.setLsChannel(loParrilla.getLsChannelid().trim());
                 loMapDateCortes.setLsDateValue(loParrilla.getLsDatevalue());
                 loMapDateCortes.setLsHourStart(loParrilla.getLsHourstart());
                 loMapDateCortes.setLsHourEnd(loParrilla.getLsHourend());
                 loMapDateCortes.setLsBuyuntidCorte(loParrilla.getLsBuyuntidCorte());
                 loMapDateCortes.setLsDateCorte(loParrilla.getLsDateCorte());
                 loMapDateCortes.setLsHourCorte(loParrilla.getLsHourCorte());
                 loMapDateCortes.setLsCorteid(loParrilla.getLsCorteid());
                 loListAllDateCortes.add(loMapDateCortes);
                 //Channel-Date-Cortes Nivel 4
                 ParrillaCorteMapCorteBreaks loMapDateCorteBreaks =
                     new ParrillaCorteMapCorteBreaks();
                 loMapDateCorteBreaks.setLsChannel(loParrilla.getLsChannelid().trim());
                 loMapDateCorteBreaks.setLsDateValue(loParrilla.getLsDatevalue());
                 loMapDateCorteBreaks.setLsHourStart(loParrilla.getLsHourstart());
                 loMapDateCorteBreaks.setLsHourEnd(loParrilla.getLsHourend());
                 loMapDateCorteBreaks.setLsBuyuntidCorte(loParrilla.getLsBuyuntidCorte());
                 loMapDateCorteBreaks.setLsDateCorte(loParrilla.getLsDateCorte());
                 loMapDateCorteBreaks.setLsHourCorte(loParrilla.getLsHourCorte());
                 loMapDateCorteBreaks.setLsCorteid(loParrilla.getLsCorteid());
                 loMapDateCorteBreaks.setLsBreakid(loParrilla.getLsBreakid());
                 loMapDateCorteBreaks.setLsDescription(loParrilla.getLsDescription().trim());
                 loMapDateCorteBreaks.setLsDateBreak(loParrilla.getLsDateBreak());
                 loMapDateCorteBreaks.setLsHourBreak(loParrilla.getLsHourBreak());
                 loMapDateCorteBreaks.setLsTotalduration(loParrilla.getLsTotalduration());
                 
                 loMapDateCorteBreaks.setLsComercialduration(loParrilla.getLsComercialduration());
                 loMapDateCorteBreaks.setLsTypeBreak(loParrilla.getLsTypeBreak());
                 
                 loListAllDateCorteBreaks.add(loMapDateCorteBreaks);
             }
             List<ParrillaCorteMapChannels> laListChannels =
                 new ArrayList<ParrillaCorteMapChannels>();
             laListChannels =
                 UtilsIntegrationService.removesChannelsRepeated(loListAllChannels);
             List<ParrillaCorteMapDates> laListDates =
                 new ArrayList<ParrillaCorteMapDates>();
             laListDates =
                 UtilsIntegrationService.removesDatesRepeated(loListAllDates);
             List<ParrillaCorteMapItems> laListDateItems =
                 new ArrayList<ParrillaCorteMapItems>();
             laListDateItems =
                 UtilsIntegrationService.removesDateItemsRepeated(loListAllDateItems);
             List<ParrillaCorteMapCortes> laListDateCortes =
                 new ArrayList<ParrillaCorteMapCortes>();
             laListDateCortes =
                 UtilsIntegrationService.removesCortesRepeated(loListAllDateCortes);
             List<ParrillaCorteMapCorteBreaks> laListDateCorteBreaks =
                 new ArrayList<ParrillaCorteMapCorteBreaks>();
             laListDateCorteBreaks =
                 UtilsIntegrationService.removesCorteBreaksRepeated(loListAllDateCorteBreaks);
             // ********* Armar aqui mismo el xml ***************
             System.out.println("Crear instancia de neptuno");
             WSNeptuno             loWsNeptuno = 
                 new UtilsIntegrationService().getServiceNeptunoDynamic();
             HeaderHandlerResolver loHandlerResolver = 
                 new HeaderHandlerResolver();
             loWsNeptuno.setHandlerResolver(loHandlerResolver);
             WSNeptunoSoap         loWsNeptunoSoap = loWsNeptuno.getWSNeptunoSoap();
             boolean               lbOffline = false;             
             Xmlentrada            loXmlentrada = createXmlentrada(loParrillasCortes, tsCodeTrace);
             liIndProcess = new UtilFaces().getIdConfigParameterByName("createXml");
             new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                          Integer.parseInt(lsIdServiceReq), 
                                                          liIndProcess, 
                                                          "Requets XML creado para Parrillas onDemand",
                                                          0, 
                                                          Integer.parseInt(lsIdRequestParrillasReq), 
                                                          Integer.parseInt(lsIdUserReq),
                                                          lsIdUserNameReq
                                                          );
             try {
                 liIndProcess = new UtilFaces().getIdConfigParameterByName("InvokingNeptuno");
                 new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                              Integer.parseInt(lsIdServiceReq), 
                                                              liIndProcess, 
                                                              "Servicio Neptuno invocado para Parrillas onDemand",
                                                              0, 
                                                              Integer.parseInt(lsIdRequestParrillasReq), 
                                                              Integer.parseInt(lsIdUserReq),
                                                              lsIdUserNameReq
                                                              );     
                 RequestResponseBean loRqRs = new RequestResponseBean();
                 EntityMappedDao     loEntityMappedDao = new EntityMappedDao();
                 String              lsNomFile = "";
                 try{
                     /*
                     StreamResult result =
                     new StreamResult(new File("C:\\Users\\JorgeOWM\\Desktop\\ParrillasOnDemandXml"+getId()+".xml"));
                     //transformer.transform(source, result);
                     JAXB.marshal(loXmlentrada, result);
                     */
                     System.out.println("Guardando archivo xml en bd");
                     ByteArrayOutputStream loBaos = new ByteArrayOutputStream();                     
                     JAXB.marshal(loXmlentrada, new StreamResult(loBaos));
                     InputStream           loFileXml = new ByteArrayInputStream(loBaos.toByteArray()); 
                     lsNomFile = "ParrillasOnDemand-" + getId() + ".xml";
                     loEntityMappedDao.insertEvetvIntXmlFilesTab(Integer.parseInt(lsIdRequestParrillasReq), 
                                                                 Integer.parseInt(lsIdServiceReq), 
                                                                 lsNomFile, 
                                                                 "REQUEST", 
                                                                 "WsParrillasOnDemand", 
                                                                 "A", 
                                                                 lsIdUserNameReq, 
                                                                 loFileXml);
                 }catch(Exception loEx){
                     System.out.println("Error al guardar archivo "+loEx.getMessage());
                     liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                     new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                                  Integer.parseInt(lsIdServiceReq), 
                                                                  liIndProcess, 
                                                                  "Error al generar Archivo XML-Request",
                                                                  0, 
                                                                  Integer.parseInt(lsIdRequestParrillasReq), 
                                                                  Integer.parseInt(lsIdUserReq),
                                                                  lsIdUserNameReq
                                                                  );
                 }
                 new UtilFaces().insertLogServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                         Integer.parseInt(lsIdServiceReq), 
                                                         0, 
                                                         "N", 
                                                         0, 
                                                         Integer.parseInt(lsIdRequestParrillasReq), 
                                                         "Neptuno Execution Request", 
                                                         Integer.parseInt(lsIdUserReq), 
                                                         lsIdUserNameReq, 
                                                         "WsBreaksOnDemand"
                                                         );    
                 
                 //######### Segunda 2da FASE ==> Recibir datos de Neptuno ####################################
                 System.out.println("invocando a neptuno");
                 RecibirDatosExternosResponse.RecibirDatosExternosResult loRder =
                     loWsNeptunoSoap.recibirDatosExternos(loXmlentrada,
                                                          lbOffline);
                 System.out.println("######### update LOG REQUEST ############");
                 new UtilFaces().updateLogServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                         Integer.parseInt(lsIdServiceReq), 
                                                         0, 
                                                         "", 
                                                         0, 
                                                         Integer.parseInt(lsIdUserReq), 
                                                         Integer.parseInt(lsIdRequestParrillasReq), 
                                                         "Neptuno Execution Response", 
                                                         Integer.parseInt(lsIdUserReq), 
                                                         lsIdUserNameReq
                                                         );
                 liIndProcess = new UtilFaces().getIdConfigParameterByName("FinishResponseNeptuno");
                 new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                              Integer.parseInt(lsIdServiceReq), 
                                                              liIndProcess, 
                                                              "Respuesta de Neptuno Obtenida para Parrillas onDemand",
                                                              0, 
                                                              Integer.parseInt(lsIdRequestParrillasReq), 
                                                              Integer.parseInt(lsIdUserReq),
                                                              lsIdUserNameReq
                                                              );    
                 
                 loRqRs.setLsRequest("NA");
                 ParrillasCortesOnDemDao            loDao = new ParrillasCortesOnDemDao();
                 CodRespuesta                       loCodResp = convertResponse(loRder);
                 //System.out.println("Iterar sobre el response de neptuno");
                 //List<EvetvParrillasProcesadasBean> laEppBeans = 
                 //    new ArrayList<EvetvParrillasProcesadasBean>();
                 List<CodRespuesta.ItemCabecera>    laCabs = loCodResp.getItemCabecera();
                 for(CodRespuesta.ItemCabecera loCab : laCabs) {
                     CodRespuesta.ItemCabecera.ItemRespuesta loIRes = 
                         loCab.getItemRespuesta();
                     System.out.println("");
                     if(loIRes != null) {
                         //System.out.print("loIRes NO ES NULL ");
                         String lsElemento = loIRes.getElemento();
                         //System.out.print("lsElemento["+lsElemento+"]  ");
                         if(lsElemento != null && lsElemento.equalsIgnoreCase("Break")) {
                             //5.- Por cada ItemCabecera Actualizar Estatus
                             String lsIdElemento = loIRes.getIdElemento();
                             //System.out.print("lsIdElemento["+lsIdElemento+"]  ");
                             String lsCanal = findValueInStr(lsIdElemento, "Soporte:");
                             lsCanal = lsCanal == null ? "" : lsCanal.trim();
                             //System.out.print("lsCanal["+lsCanal+"]  ");
                             String lsCorte = findValueInStr(lsIdElemento, "Corte:");
                             lsCorte = lsCorte == null ? "" : lsCorte.trim();
                             //System.out.print("lsCorte["+lsCorte+"]  ");
                             String lsBreak = findValueInStr(lsIdElemento, "Break:");
                             lsBreak = lsBreak == null ? "" : lsBreak.trim();     
                             //System.out.print("lsBreak["+lsBreak+"]  ");
                             RsstParrillasCortesBean loBreakData = 
                                 loDao.getBreakInfo(lsCanal, lsBreak);
                             //System.out.print("loBreakDataOBJECT["+loBreakData+"]  ");
                             String lsRess = 
                                 loIRes.getResultado() == null ? "" : 
                                 loIRes.getResultado().trim().toUpperCase();
                             //System.out.print("lsRess["+lsRess+"]  ");
                             String lsDateBreak = null;
                             String lsDescription = null;
                             if(loBreakData != null){                                                              
                                 lsDateBreak = 
                                     loBreakData.getLsDateBreak() == null ? "" : 
                                     loBreakData.getLsDateBreak().trim();
                                 //System.out.print("lsDateBreak["+lsDateBreak+"]  ");
                                 lsDateBreak = buildDate(lsDateBreak, "MM/dd/yy");
                                 //System.out.print("lsDateBreak["+lsDateBreak+"]  ");
                                 lsDescription = 
                                     loBreakData.getLsDescription() == null ? "" : 
                                     loBreakData.getLsDescription().trim();
                                 //System.out.print("lsDescription["+lsDescription+"]  ");
                                 lsDescription = lsDescription.replaceAll("'", "''");
                             }
                             
                             //if(loBreakData != null){
                             //    System.out.println("Resultado: ["+lsRess+"] para lsCanal["+lsCanal+"] Fecha["+buildDatePardigm(lsDateBreak, "dd/MM/YYYY")+"] lsDescription["+lsDescription+"] lsBreak ["+lsBreak+"]");    
                             //}else{
                             //    System.out.println("Resultado: ["+lsRess+"] para lsCanal["+lsCanal+"] Fecha[NULL] lsDescription[null] lsBreak ["+lsBreak+"]");
                             //}
                             
                             if(lsRess.equals("KO")){
                                 lbFlagConError = true;
                                 //System.out.println("Actualizando a 2: lsCanal["+lsCanal+"] Fecha["+buildDatePardigm(lsDateBreak, "dd/MM/YYYY")+"] lsDescription["+lsDescription+"]");
                                 if(lsDateBreak != null){
                                     loPpyCorDao.updateStstusParrillasProcesadas(lsCanal, 
                                                                                 buildDatePardigm(lsDateBreak, 
                                                                                                  "dd/MM/YYYY"), 
                                                                                 lsDescription, 
                                                                                 lsBreak,
                                                                                 "2"
                                                                                );
                                 }else{
                                     loPpyCorDao.updateStstusParrillasProcesadas(lsCanal, 
                                                                                 null, 
                                                                                 null, 
                                                                                 lsBreak,
                                                                                 "2"
                                                                                );
                                 }
                                 
                                 loDao.updateEveTvParrillas(lsIdRequestParrillasReq,
                                                            lsIdServiceReq,
                                                            "E",
                                                            lsIdUserNameReq,
                                                            lsIdUserReq,
                                                            lsCorte,                                  
                                                            lsBreak
                                                            );
                             }else{
                                 if(lsDateBreak != null){
                                     loPpyCorDao.updateStstusParrillasProcesadas(lsCanal, 
                                                                                 buildDatePardigm(lsDateBreak, 
                                                                                                  "dd/MM/YYYY"), 
                                                                                 lsDescription, 
                                                                                 lsBreak,
                                                                                 "0"
                                                                                );   
                                 }else{
                                     loPpyCorDao.updateStstusParrillasProcesadas(lsCanal, 
                                                                                 null, 
                                                                                 null, 
                                                                                 lsBreak,
                                                                                 "0"
                                                                                ); 
                                 }
                                                                   
                                 loDao.updateEveTvParrillas(lsIdRequestParrillasReq,
                                                            lsIdServiceReq,
                                                            "T",
                                                            lsIdUserNameReq,
                                                            lsIdUserReq,
                                                            lsCorte,                                  
                                                            lsBreak
                                                            );
                             }
                         }
                     }
                 }
                 
                 liIndProcess = new UtilFaces().getIdConfigParameterByName("UpdateCtrlStatus");
                 new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                              Integer.parseInt(lsIdServiceReq), 
                                                              liIndProcess, 
                                                              "Actualizacion de Estatus en Tablas de Control para " +
                                                              "Parrillas onDemand",
                                                              0, 
                                                              Integer.parseInt(lsIdRequestParrillasReq), 
                                                              Integer.parseInt(lsIdUserReq),
                                                              lsIdUserNameReq
                                                              );
                 
                 //Registrar Si el flujo si se completo, sin embargo Neptuno regreso al menos un KO
                 if(lbFlagConError){
                     liIndProcess = new UtilFaces().getIdConfigParameterByName("FinishedFlowWithError");
                     new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                                  Integer.parseInt(lsIdServiceReq), 
                                                                  liIndProcess, 
                                                                  "Flujo de Integracion Paradigm-Neptuno Finalizado" +
                                                                  "Con Error",
                                                                  0, 
                                                                  Integer.parseInt(lsIdRequestParrillasReq), 
                                                                  Integer.parseInt(lsIdUserReq),
                                                                  lsIdUserNameReq
                                                                  );     
                 }
                 //Cierre de comentar codigo para generar archivo XML
                 System.out.println("Crear archivo XML en base de datos");
                 XmlMessageReqRes loResReq = new XmlMessageReqRes();                 
                 try{
                     ByteArrayOutputStream loBaosRes = new ByteArrayOutputStream();
                     JAXB.marshal(loRder, loBaosRes);
                     InputStream           loFileXmlRes = new ByteArrayInputStream(loBaosRes.toByteArray()); 
                     lsNomFile = "ParrillasOnDemand-" + getId() + ".xml";
                     loEntityMappedDao.insertEvetvIntXmlFilesTab(Integer.parseInt(lsIdRequestParrillasReq), 
                                                                 Integer.parseInt(lsIdServiceReq), 
                                                                 lsNomFile, 
                                                                 "RESPONSE", 
                                                                 "WsParrillasOnDemand", 
                                                                 "A", 
                                                                 lsIdUserNameReq, 
                                                                 loFileXmlRes);
                 }catch(Exception loEx){
                     liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                     new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                                  Integer.parseInt(lsIdServiceReq), 
                                                                  liIndProcess, 
                                                                  "Error al generar Archivo XML-Response",
                                                                  0, 
                                                                  Integer.parseInt(lsIdRequestParrillasReq), 
                                                                  Integer.parseInt(lsIdUserReq),
                                                                  lsIdUserNameReq
                                                                  );
                 }
                 loResReq.setXmlMessageRequest("REQUEST");
                 loResReq.setXmlMessageResponse("RESPONSE");
                 XmlMessageResponseCollection loXmlMsCol =
                     new XmlMessageResponseCollection();
                 loXmlMsCol.getXmlMessageReqRes().add(loResReq);
                 loParrillasRes.setXmlMessageResponse(loXmlMsCol);
             } catch (Exception loExpEv) {
                 liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
                 new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                              Integer.parseInt(lsIdServiceReq), 
                                                              liIndProcess, 
                                                              "Error En: " + loExpEv.getMessage(),
                                                              0, 
                                                              Integer.parseInt(lsIdRequestParrillasReq), 
                                                              Integer.parseInt(lsIdUserReq),
                                                              lsIdUserNameReq
                                                              ); 
             }
         }else{
             liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
             new UtilFaces().insertBitacoraServiceService(Integer.parseInt(lsIdRequestParrillasReq), 
                                                          Integer.parseInt(lsIdServiceReq), 
                                                          liIndProcess, 
                                                          "Carga Inicial No Permitida para Parrillas OnDemand",
                                                          0, 
                                                          Integer.parseInt(lsIdRequestParrillasReq), 
                                                          Integer.parseInt(lsIdUserReq),
                                                          lsIdUserNameReq
                                                          );
             
             XmlMessageReqRes loResReq = new XmlMessageReqRes();
             loResReq.setXmlMessageRequest("REQUEST");
             loResReq.setXmlMessageResponse("RESPONSE ERROR");
             XmlMessageResponseCollection loXmlMsCol =
                 new XmlMessageResponseCollection();
             loXmlMsCol.getXmlMessageReqRes().add(loResReq);
             loParrillasRes.setXmlMessageResponse(loXmlMsCol);
        }
        System.out.println("FIN.......");
        return loParrillasRes;       
     }
     
     /**
      * Convierte una cadena tipo fecha a otra con diferente formato
      * @autor Jorge Luis Bautista Santiago
      * @param tsDateString
      * @param tsInputMask
      * @param tsOutputMask
      * @return Stirng
      */
     private static String convierteFecha(String tsDateString, 
                                          String tsInputMask,
                                          String tsOutputMask
                                          ) throws ParseException {

         SimpleDateFormat loSdfEntrada = 
             new SimpleDateFormat(tsInputMask);
         SimpleDateFormat loSdfSalida = 
             new SimpleDateFormat(tsOutputMask);
         Date             ltDatePivot = new Date();
         String           lsFormattedDate = "";
         try {
             ltDatePivot = loSdfEntrada.parse(tsDateString);
             lsFormattedDate = loSdfSalida.format(ltDatePivot);
         } 
         catch (ParseException loEx) {
             throw loEx;
         }
         return lsFormattedDate;
     }
 
     /**
      * Convierte a objeto mapeable a xml
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
         //loJAXBMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
         StringWriter loSW = new StringWriter();
         loJAXBMarshaller.marshal(toResp, loSW);//loSW puede ser un OutputStream o File
         CodRespuesta loResp = 
             JAXB.unmarshal(new StringReader(loSW.toString()), 
                            CodRespuesta.class );
         return loResp;
     }
     
     /**
      * Encuentra cadena dentro de otra
      * @autor Omar Ponciano Galicia
      * @param tsStr
      * @param tsToFind
      * @return CodRespuesta
      */
     private String findValueInStr(String tsStr, String tsToFind) {
         String lsValue = "";
         int    liAuxIdx1 = tsStr.toLowerCase().indexOf(tsToFind.toLowerCase());
         int    liAuxIdx2;
         if(liAuxIdx1 != -1) {
             liAuxIdx1 += tsToFind.length();
             String lsAux = tsStr.substring(liAuxIdx1);
             lsAux = lsAux.trim();
             liAuxIdx2 = lsAux.toLowerCase().indexOf(' ');
             if(liAuxIdx2 == -1) {
                 liAuxIdx2 += 1;
                 liAuxIdx2 = lsAux.length();
             }
             lsValue = lsAux.substring(0, liAuxIdx2);
         }
         return lsValue;
     }
     
     /**
      * Crea objeto solicitado por neptuno de Parrillas
      * @autor Omar Ponciano Galicia
      * @param toParrillasCortes
      * @return Xmlentrada
      */
     private Xmlentrada createXmlentrada(List<RsstParrillasCortesBean> toParrillasCortes, String tsCodeTrace) {
         Xmlentrada loXmlent = new Xmlentrada();
         RAIZ       loRoot = new RAIZ();
         Parrilla   loParrillas = new Parrilla();
         loParrillas.setMode("A");
         loParrillas.setCodeTrace(tsCodeTrace);
         Schedule   loSchedule = new Schedule();
         Dates      loDates = new Dates();
         if(toParrillasCortes.size() > 0) {
             //for(RsstParrillasCortesBean loRsstRow : toParrillasCortes){                              
             RsstParrillasCortesBean loRsstRow = toParrillasCortes.get(0);
             loSchedule.setChannelID(loRsstRow.getLsChannelid());
             loSchedule.setDates(loDates);
             TimeBand loTimeBand1 = new TimeBand();
             loDates.getTimeBand().add(loTimeBand1);
             loTimeBand1.setDateValue(loRsstRow.getLsDatevalue());
             loTimeBand1.setHourStart(loRsstRow.getLsHourstart());
             loTimeBand1.setHourEnd(loRsstRow.getLsHourend());
             Items loItems1 = new Items();
             loTimeBand1.setItems(loItems1);
             Item loItem1 = new Item();
             loItem1.setHour(loRsstRow.getLsHour());
             loItem1.setTitle(loRsstRow.getLsTitle());
             loItem1.setEpisodeName(loRsstRow.getLsEpisodename());
             loItem1.setEpNo(loRsstRow.getLsEpno());
             loItem1.setSlotDuration(loRsstRow.getLsSlotduration());
             loItem1.setTitleID(loRsstRow.getLsTitleid());
             loItem1.setEpisodeNameID(loRsstRow.getLsEpisodenameid());
             loItem1.setBuyUnitID(loRsstRow.getLsBuyunitid());
             loItem1.setBuyUnit(loRsstRow.getLsBuyunit());
             loItem1.setExceptCofepris(loRsstRow.getLsExceptcofepris());
             loItem1.setEventSpecial(loRsstRow.getLsEventspecial());
             loItem1.setEventBest(loRsstRow.getLsEventbest());
             
             loItem1.setGeneroID(loRsstRow.getLsGeneroID());
             loItem1.setGeneroName(loRsstRow.getLsGeneroName());
             
             loItems1.getItem().add(loItem1);
             Cortes loCortes1 = new Cortes();
             loTimeBand1.setCortes(loCortes1);
             String lsCurrentDate = loRsstRow.getLsDatevalue() == null ? "" : loRsstRow.getLsDatevalue();
             for(int liI = 1; liI < toParrillasCortes.size(); liI++) {
                 RsstParrillasCortesBean row = toParrillasCortes.get(liI);
                 if(!lsCurrentDate.equals(row.getLsDatevalue()) ||
                    (row.getLsHourstart() != null && !row.getLsHourstart().equals("") ||
                     (row.getLsHourend() != null && !row.getLsHourend().equals("")))) { // Nuevo timeband
                     TimeBand loTimeBand = new TimeBand();
                     loDates.getTimeBand().add(loTimeBand);
                     loTimeBand.setDateValue(row.getLsDatevalue());
                     loTimeBand.setHourStart(row.getLsHourstart());
                     loTimeBand.setHourEnd(row.getLsHourend());
                     Items loItems = new Items();
                     loTimeBand.setItems(loItems);
                     Item loItem = new Item();
                     loItem.setHour(row.getLsHour());
                     loItem.setTitle(row.getLsTitle());
                     loItem.setEpisodeName(row.getLsEpisodename());
                     loItem.setEpNo(row.getLsEpno());
                     loItem.setSlotDuration(row.getLsSlotduration());
                     loItem.setTitleID(row.getLsTitleid());
                     loItem.setEpisodeNameID(row.getLsEpisodenameid());
                     loItem.setBuyUnitID(row.getLsBuyunitid());
                     loItem.setBuyUnit(row.getLsBuyunit());
                     loItem.setExceptCofepris(row.getLsExceptcofepris());
                     loItem.setEventSpecial(row.getLsEventspecial());
                     loItem.setEventBest(row.getLsEventbest());
                     
                     loItem.setGeneroID(row.getLsGeneroID());
                     loItem.setGeneroName(row.getLsGeneroName());
                     
                     loItems.getItem().add(loItem);
                     Cortes loCortes = new Cortes();
                     loTimeBand.setCortes(loCortes);
                     lsCurrentDate = row.getLsDatevalue() == null ? "" : row.getLsDatevalue();
                     continue;
                 }
                 if(row.getLsTitle() != null && !row.getLsTitle().equals("")) { // Nuevo item
                     Item loItem = new Item();
                     loItem.setHour(row.getLsHour());
                     loItem.setTitle(row.getLsTitle());
                     loItem.setEpisodeName(row.getLsEpisodename());
                     loItem.setEpNo(row.getLsEpno());
                     loItem.setSlotDuration(row.getLsSlotduration());
                     loItem.setTitleID(row.getLsTitleid());
                     loItem.setEpisodeNameID(row.getLsEpisodenameid());
                     loItem.setBuyUnitID(row.getLsBuyunitid());
                     loItem.setBuyUnit(row.getLsBuyunit());
                     loItem.setExceptCofepris(row.getLsExceptcofepris());
                     loItem.setEventSpecial(row.getLsEventspecial());
                     loItem.setEventBest(row.getLsEventbest());
                     
                     loItem.setGeneroID(row.getLsGeneroID());
                     loItem.setGeneroName(row.getLsGeneroName());
                     
                     loDates.getTimeBand().get(loDates.getTimeBand().size() - 1).getItems().getItem().add(loItem);
                     continue;
                 }
                 if(row.getLsBuyuntidCorte() != null && !row.getLsBuyuntidCorte().equals("")) { // Nuevo corte
                     Corte loCorte = new Corte();
                     loCorte.setBuyUnitID(row.getLsBuyuntidCorte());
                     loCorte.setDate(row.getLsDateCorte());
                     loCorte.setHour(row.getLsHourCorte());
                     loCorte.setCorteID(row.getLsCorteid());
                     //OPGcorte.setDesCorte(row.getLsDateCorte());
                     String lsDesCorte = row.getLsDescorte() == null ? null : row.getLsDescorte().trim();;
                     loCorte.setDesCorte(lsDesCorte);
                     loCorte.setOverlay(row.getLsOverlay());
                     loDates.getTimeBand().get(loDates.getTimeBand().size() - 1).getCortes().getCorte().add(loCorte);
                     Breaks loBreaks = new Breaks();
                     loCorte.setBreaks(loBreaks);
                     continue;
                 }
                 if(row.getLsBreakid() != null && !row.getLsBreakid().equals("")) {
                     Break loBreakk = new Break();
                     loBreakk.setBreakID(row.getLsBreakid());
                     loBreakk.setDescription(row.getLsDescription());
                     loBreakk.setDate(row.getLsDateBreak());
                     loBreakk.setHour(row.getLsHourBreak());
                     loBreakk.setTotalDuration(row.getLsTotalduration());
                     loBreakk.setComercialDuration(row.getLsComercialduration());
                     
                     loBreakk.setTypeBreak(row.getLsTypeBreak());
                     
                     TimeBand loTbnd = loDates.getTimeBand().get(loDates.getTimeBand().size() - 1);
                     //OPGCortes cts = loTbnd.getCortes();
                     //System.out.println("SIZE: " + tb.getCortes().getCorte().size());
                     //System.out.println("SIZE2: " + (tb.getCortes().getCorte().size() - 1));
                     if(loTbnd.getCortes().getCorte().size() == 0) {
                         continue;
                     }
                     Corte loCrte = loTbnd.getCortes().getCorte().get(loTbnd.getCortes().getCorte().size() - 1);
                     loCrte.getBreaks().getBreak().add(loBreakk);
                     continue;
                 }
             }
         //}
         }
         loSchedule.setDates(loDates);
         loParrillas.setSchedule(loSchedule);
         loRoot.setParrilla(loParrillas);
         loXmlent.setRAIZ(loRoot);          
        //***********************************************************
         return loXmlent;
     }         
     
     /**
      * Transofrorma cadena de texto de fecha en mascara requerida por Paradigm
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
             String lsYear;
            try {
                lsYear = convierteFecha(tsDate, tsInMask, "YYYY");
                lsResponse = lsDay + "/" + lsMonth + "/" + lsYear;
            } catch (ParseException loEx) {
                ;
            }
            
         }
         return lsResponse;
     }
     
     /**
      * Transofrorma cadena de texto de fecha en mascara requerida por Paradigm
      * @autor Jorge Luis Bautista Santiago
      * @param tsDate
      * @param tsInMask
      * @return String
      */
     private String buildDatePardigm(String tsDate, String tsInMask){
         String   lsResponse = null;
         String[] laArrDate = tsDate.split("/");
         if(laArrDate.length > 0){
             String lsDay = laArrDate[0];
             String lsMonth = laArrDate[1];            
             String lsYear;
            try {
                lsYear = convierteFecha(tsDate, tsInMask, "YYYY");
                lsResponse = lsYear + "-" + lsMonth + "-" + lsDay;
            } catch (ParseException loEx) {
                ;
            }
         }
         return lsResponse;
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
