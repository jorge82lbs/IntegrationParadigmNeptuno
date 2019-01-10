package com.televisa.comer.integration.ws.impl;

import com.televisa.comer.integration.model.beans.pgm.EvetvIntServiceBitacoraTab;
import com.televisa.comer.integration.ws.model.daos.EntityMappedDao;
import com.televisa.comer.integration.ws.beans.pgm.parrillas.CodRespuesta;
import com.televisa.comer.integration.ws.beans.pgm.parrillas.ItemCabecera;
import com.televisa.comer.integration.ws.beans.pgm.parrillas.ItemRespuesta;
import com.televisa.comer.integration.ws.beans.pgm.parrillas.ListaMensaje;
import com.televisa.comer.integration.ws.beans.pgm.parrillas.RecibirDatosExternosResponse;
import com.televisa.comer.integration.ws.beans.pgm.parrillas.RecibirDatosExternosResult;
import com.televisa.comer.integration.ws.beans.pgm.parrillas.ScheduleOnDemand;
import com.televisa.comer.integration.ws.model.beans.EvetvIntConfigParamTabBean;
import com.televisa.comer.integration.ws.model.beans.EvetvIntServicesLogBean;
import com.televisa.comer.integration.ws.utils.UtilsOnDemand;

import com.televisa.integration.view.front.util.UtilFaces;
import com.televisa.integration.view.jobs.ExecuteParrillasOnDemandCron;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Map;

import javax.annotation.Resource;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import javax.xml.bind.JAXB;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

@WebService
public class IntegrationBsParrillas {
    
    @Resource 
    private WebServiceContext loWsc;

    @WebMethod
    public RecibirDatosExternosResponse executeClientParrillas(@WebParam(name = "ScheduleOnDemand")
                                                               ScheduleOnDemand toScheduleOnDemand){
        RecibirDatosExternosResponse loResponse = new RecibirDatosExternosResponse();
        boolean                    lbSecurityFalg = false;
        boolean                    lbInitFalg = true;
        EntityMappedDao            loEntityMappedDao = new EntityMappedDao();
        boolean                    lbProcess = true;
        String                     lsFieldErrorRq = "";
        CodRespuesta               loCodRespuesta = new CodRespuesta();
        Integer                    liIdRequest = loEntityMappedDao.getMaxIdParadigm("RstRequest") + 1; 
        String                     lsIdService = loEntityMappedDao.getWsParadigmOrigin("WsParrillasClient");
        String                     lsNomFile = "ParrillasClient-" + getId() + ".xml";
        String                     lsIndFileType = "REQUEST";
        String                     lsIndServiceType = "WsParrillasClient";
        String                     lsIndEstatus = "A";
        String                     lsNomUserName = "neptuno";  
        boolean                    lbDateNull = false;
        try{
            ByteArrayOutputStream loBaos = new ByteArrayOutputStream();
            JAXB.marshal(toScheduleOnDemand, loBaos);
            InputStream           loFileXml = new ByteArrayInputStream(loBaos.toByteArray()); 
            loEntityMappedDao.insertEvetvIntXmlFilesTab(liIdRequest, 
                                                        Integer.parseInt(lsIdService), 
                                                        lsNomFile, 
                                                        lsIndFileType, 
                                                        lsIndServiceType, 
                                                        lsIndEstatus, 
                                                        lsNomUserName, 
                                                        loFileXml
                                                        );
        }catch(Exception loEx){
            System.out.println("Archivo no generado");
        }
        loEntityMappedDao.insertLogServicesRequest(liIdRequest, 
                                                   Integer.parseInt(lsIdService), 
                                                   "WsParrillasClient", 
                                                   "neptuno"
                                                   );
        EvetvIntServicesLogBean loEvetvIntServicesLogBean = new EvetvIntServicesLogBean();
        loEvetvIntServicesLogBean.setLiIdLogServices(liIdRequest);
        loEvetvIntServicesLogBean.setLiIdService(Integer.parseInt(lsIdService));
        loEvetvIntServicesLogBean.setLiIndProcess(0);
        loEvetvIntServicesLogBean.setLsIndResponse("N");
        loEvetvIntServicesLogBean.setLsIndEstatus("A");
        loEvetvIntServicesLogBean.setLsAttribute9("WsParrillasClient");
        loEvetvIntServicesLogBean.setLsAttribute10("Execution");
        loEvetvIntServicesLogBean.setLsAttribute15("neptuno");
        loEntityMappedDao.insertServicesLogWs(loEvetvIntServicesLogBean);
        
        String lsIndProcessR = 
            loEntityMappedDao.getGeneralParameterID("serviceRequest", "PROCESS_INTEGRATION");
        EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
        toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
        toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
        toEvetvIntServiceBitacoraTabR.setLsIndProcess(lsIndProcessR); //Tipo de Proceso
        toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId(toScheduleOnDemand.getCodeTrace());
        toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");        
        toEvetvIntServiceBitacoraTabR.setLsIndEvento("Solicitud Recibida para Servicio Parrillas onDemand - Cliente");
        loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR);
        
        String lsSecurity = 
            loEntityMappedDao.getSecurityService(lsIdService) == null ? "NO": 
            loEntityMappedDao.getSecurityService(lsIdService);        
        
        if(!lsSecurity.equalsIgnoreCase("NO")){
            lbSecurityFalg = true;
        }
        if(lbSecurityFalg){
            String lsResponse = null;
            MessageContext mc = loWsc.getMessageContext();
            Map requestHeader = (Map)mc.get(MessageContext.HTTP_REQUEST_HEADERS);
            
            if(requestHeader.get("Username") != null && requestHeader.get("Password") != null){
                List userList = (List) requestHeader.get("Username");
                List passwordList = (List) requestHeader.get("Password");
                if(userList != null && passwordList != null){
                    String lsUserName = userList.get(0) == null ? null : userList.get(0).toString();
                    String lsPassword = passwordList.get(0) == null ? null : passwordList.get(0).toString();
                    //Obtener usuario y contrase�a de la bd
                    String lsUsernameBd = 
                        loEntityMappedDao.getGeneralParameter("UsrNeptuno", "AUTHENTICATION");
                    String lsPasswordBd = 
                        loEntityMappedDao.getGeneralParameter("PswNeptuno", "AUTHENTICATION");
                    if(lsUsernameBd.equals(lsUserName) && lsPasswordBd.equals(lsPassword) ){
                        lbInitFalg = true;
                    }else{
                        lbInitFalg = false;
                    }
                    
                }else{
                    lsResponse = "Campos Obligatorios";
                    lbInitFalg = false;
                }
            }else{
                lbInitFalg = false;
            }
        }
        
        if(lbInitFalg){   
            String lsChannelID = 
                toScheduleOnDemand.getChannelID() == null ? "" : 
                toScheduleOnDemand.getChannelID();
            
            String lsInitialDate = 
                toScheduleOnDemand.getInitialDate() == null ? "" : 
                toScheduleOnDemand.getInitialDate();
            
            String lsEndDate = 
                toScheduleOnDemand.getEndDate() == null ? "" : 
                toScheduleOnDemand.getEndDate();
            
            String lsBuyUnitID = 
                toScheduleOnDemand.getBuyUnitID() == null ? "" : 
                toScheduleOnDemand.getBuyUnitID();
            
            String lsCodeTrace = 
                toScheduleOnDemand.getCodeTrace() == null ? "" : 
                toScheduleOnDemand.getCodeTrace();
            
            System.out.println("InputWS-lsChannelID["+lsChannelID+"]");
            System.out.println("InputWS-lsInitialDate["+lsInitialDate+"]");
            System.out.println("InputWS-lsEndDate["+lsEndDate+"]");
            System.out.println("InputWS-lsBuyUnitID["+lsBuyUnitID+"]");
            System.out.println("InputWS-lsCodeTrace["+lsCodeTrace+"]");
            
            //Validar Requeridos
            if(lsChannelID.trim().length() < 1){
                lsFieldErrorRq += "ChannelID,";
                lbProcess = false;   
            }
            
            if(lsCodeTrace.trim().length() < 1){
                lsFieldErrorRq += "CodeTrace,";
                lbProcess = false;   
            }
            
            //Si viene fecha inicio debe venir fecha fin
            if(lsInitialDate.trim().length() < 1 && lsEndDate.trim().length() > 0){
                lsFieldErrorRq += "InitialDate,";
                lbProcess = false;   
            }
            if(lsEndDate.trim().length() < 1 && lsInitialDate.trim().length() > 0){
                lsFieldErrorRq += "EndDate,";
                lbProcess = false;   
            }
            if(lsEndDate.trim().length() < 1 && lsInitialDate.trim().length() < 1){
                lbDateNull = true;   
            }       
            
            if(!lbProcess){
                ItemCabecera loItemCabecera = new ItemCabecera();
                loItemCabecera.setProcessID(toScheduleOnDemand.getCodeTrace());
                loItemCabecera.setResultado("KO");
                loItemCabecera.setTipoProceso("OnLine");
                 
                ItemRespuesta loItemRespuesta = new ItemRespuesta();
                 
                loItemRespuesta.setElemento("0");
                loItemRespuesta.setIdElemento("0");
                loItemRespuesta.setResultado("KO");   
                    
                String lsMess = "Los Siguientes Campos son Requeridos " + 
                    lsFieldErrorRq.substring(0, lsFieldErrorRq.length()-1);
                ListaMensaje laLmes = new ListaMensaje();
                laLmes.setIdError("0");
                laLmes.setDescripcion(lsMess);
                loItemRespuesta.getListaMensaje().add(laLmes);
                loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                loCodRespuesta.getItemCabecera().add(loItemCabecera);
            }else{
                UtilsOnDemand loUtilsOnDemand = new UtilsOnDemand();
                //Validar formato de fecha establecido
                boolean lbInitialDateSize = true;
                boolean lbInitialDateFormat = true;
                boolean lbEndDateSize = true;
                boolean lbEndDateFormat = true;
                if(!lbDateNull){
                    //- Ellos mandan un formato establecido por ejemplo: 15/04/2018
                    lbInitialDateSize = loUtilsOnDemand.validateLength(lsInitialDate,10);
                    lbInitialDateFormat = loUtilsOnDemand.isFormatDateNeptuno(lsInitialDate);
                    lbEndDateSize = loUtilsOnDemand.validateLength(lsEndDate,10);
                    lbEndDateFormat = loUtilsOnDemand.isFormatDateNeptuno(lsEndDate);
                }
                if(lbInitialDateSize && lbInitialDateFormat &&
                   lbEndDateSize && lbEndDateFormat
                ){
                    //- Transformar a YYYY-MM-DD
                    String lsInitDatePgm = null;
                    String lsEndDatePgm = null;
                    if(!lbDateNull){
                        lsInitDatePgm = loUtilsOnDemand.getDatePrgmFormat(lsInitialDate);
                        lsEndDatePgm = loUtilsOnDemand.getDatePrgmFormat(lsEndDate);
                    }
                    ScheduleOnDemand loPrmScheduleOnDemand = new ScheduleOnDemand();
                    loPrmScheduleOnDemand.setBuyUnitID(toScheduleOnDemand.getBuyUnitID());
                    loPrmScheduleOnDemand.setChannelID(toScheduleOnDemand.getChannelID());
                    loPrmScheduleOnDemand.setInitialDate(lsInitDatePgm);
                    loPrmScheduleOnDemand.setEndDate(lsEndDatePgm);
                    loPrmScheduleOnDemand.setCodeTrace(toScheduleOnDemand.getCodeTrace());
                    Integer tiIdUser = 1;
                    String tsUserName = "neptuno";
                    String tsServiceToInvoke = "WsParrillasClient";
                    try{
                        System.out.println("Inicio cron Parrillas onDemand - Cliente "+new Date());
                        
                        //TODO Validar si es posible ejecutar las parrillas, de lo contrario encolar la solicitud
                        executeParrillasClient(liIdRequest,
                                               lsIdService,
                                               tiIdUser,
                                               tsUserName,
                                               tsServiceToInvoke,
                                               loPrmScheduleOnDemand
                                               );
                        System.out.println("Fin de ejecucion de cron Parrillas onDemand - Cliente "+new Date());
                    }catch(Exception loEx){
                        lsIndProcessR = 
                            loEntityMappedDao.getGeneralParameterID("GeneralError", "PROCESS_INTEGRATION");
                        EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTab = new EvetvIntServiceBitacoraTab();
                        toEvetvIntServiceBitacoraTab.setLsIdLogServices(lsIdService);
                        toEvetvIntServiceBitacoraTab.setLsIdService(lsIdService);
                        toEvetvIntServiceBitacoraTab.setLsIndProcess(lsIndProcessR); //Tipo de Proceso
                        toEvetvIntServiceBitacoraTab.setLsNumEvtbProcessId(toScheduleOnDemand.getCodeTrace());
                        toEvetvIntServiceBitacoraTab.setLsNumPgmProcessId("0");        
                        toEvetvIntServiceBitacoraTab.setLsIndEvento("ERROR: Parrillas onDemand - Cliente " +loEx.getMessage());
                        loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTab);
                    }
                    
                    //Acuse de recibido al cliente
                    ItemCabecera loItemCabecera = new ItemCabecera();
                    loItemCabecera.setProcessID(toScheduleOnDemand.getCodeTrace());
                    loItemCabecera.setResultado("OK");
                    loItemCabecera.setTipoProceso("OnLine");
                    ItemRespuesta loItemRespuesta = new ItemRespuesta();
                    loItemRespuesta.setElemento("ScheduleOnDemand");
                    loItemRespuesta.setIdElemento("0");
                    loItemRespuesta.setResultado("solicitud recibida satisfactoriamente");                       
                    loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                    loCodRespuesta.getItemCabecera().add(loItemCabecera);
                }else{
                    
                    String lsOrderID = "0";
                    String lsFieldValidate = "";
                    String lsFieldParent = "ScheduleOnDemand";
                    ItemCabecera loItemCabecera = new ItemCabecera();
                    loItemCabecera.setProcessID(toScheduleOnDemand.getCodeTrace());
                    loItemCabecera.setResultado("KO");
                    loItemCabecera.setTipoProceso("OnLine");
                    ItemRespuesta loItemRespuesta = new ItemRespuesta();
                    loItemRespuesta.setElemento("OrderID: " + lsOrderID);
                    loItemRespuesta.setIdElemento(lsOrderID);
                    loItemRespuesta.setResultado("KO");      
                    lsFieldValidate = "InitialDate";
                    if(!lbInitialDateSize){
                        EvetvIntConfigParamTabBean   loError = new EvetvIntConfigParamTabBean();
                        ListaMensaje laLmes = new ListaMensaje();
                        loError = getMessageErrDb("SizeValidate");
                        laLmes.setIdError(loError.getLsIndValueParameter());
                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                              loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                        loItemRespuesta.getListaMensaje().add(laLmes);
                    }
                    if(!lbInitialDateFormat){
                        EvetvIntConfigParamTabBean   loError = new EvetvIntConfigParamTabBean();
                        ListaMensaje laLmes = new ListaMensaje();
                        loError = getMessageErrDb("FormatValidate");
                        laLmes.setIdError(loError.getLsIndValueParameter());
                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                              loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                        loItemRespuesta.getListaMensaje().add(laLmes);
                    }
                    lsFieldValidate = "EndDate";
                    if(!lbEndDateSize){
                        EvetvIntConfigParamTabBean   loError = new EvetvIntConfigParamTabBean();
                        ListaMensaje laLmes = new ListaMensaje();
                        loError = getMessageErrDb("SizeValidate");
                        laLmes.setIdError(loError.getLsIndValueParameter());
                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                              loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                        loItemRespuesta.getListaMensaje().add(laLmes);
                    }
                    if(!lbEndDateFormat){
                        EvetvIntConfigParamTabBean   loError = new EvetvIntConfigParamTabBean();
                        ListaMensaje laLmes = new ListaMensaje();
                        loError = getMessageErrDb("FormatValidate");
                        laLmes.setIdError(loError.getLsIndValueParameter());
                        laLmes.setDescripcion("[" + lsFieldParent + "] " + 
                                              loError.getLsIndDescParameter() + " [" + lsFieldValidate + "]");
                        loItemRespuesta.getListaMensaje().add(laLmes);
                    }
                    loItemCabecera.getItemRespuesta().add(loItemRespuesta);
                    loCodRespuesta.getItemCabecera().add(loItemCabecera);
                }
            }
        }else{//Autenticacion fallida
            ItemCabecera loItemCabecera = new ItemCabecera();
            loItemCabecera.setProcessID(toScheduleOnDemand.getCodeTrace());
            loItemCabecera.setResultado("KO");
            loItemCabecera.setTipoProceso("OnLine");
            ItemRespuesta loItemRespuesta = new ItemRespuesta();
            loItemRespuesta.setElemento("0");
            loItemRespuesta.setIdElemento("0");
            loItemRespuesta.setResultado("KO");   
            String lsFieldParent = "ScheduleOnDemand";
            String lsFieldValidate = "Authentication";            
            String       lsMess = "Credenciales no permitidas, verifique Username y Password";  
            ListaMensaje laLmes = new ListaMensaje();
            laLmes.setIdError("0");
            laLmes.setDescripcion("[" + lsFieldParent + "] " + lsMess + " [" + lsFieldValidate + "]");
            loItemRespuesta.getListaMensaje().add(laLmes);
            loItemCabecera.getItemRespuesta().add(loItemRespuesta);
            loCodRespuesta.getItemCabecera().add(loItemCabecera);
        }//FIN de Autenticacion fallida 
        RecibirDatosExternosResult loRecibirDatosExternosResult = new RecibirDatosExternosResult();
        loRecibirDatosExternosResult.setCodRespuesta(loCodRespuesta);
        loResponse.setRecibirDatosExternosResult(loRecibirDatosExternosResult);
        
        //###############################################################################################
        try{
            String lsIndProcessRes = 
                loEntityMappedDao.getGeneralParameterID("FinishResponseNeptuno", "PROCESS_INTEGRATION");
            EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabRes = new EvetvIntServiceBitacoraTab();
            toEvetvIntServiceBitacoraTabRes.setLsIdLogServices(lsIdService);
            toEvetvIntServiceBitacoraTabRes.setLsIdService(lsIdService);
            toEvetvIntServiceBitacoraTabRes.setLsIndProcess(lsIndProcessRes);
            toEvetvIntServiceBitacoraTabRes.setLsNumEvtbProcessId(toScheduleOnDemand.getCodeTrace());
            toEvetvIntServiceBitacoraTabRes.setLsNumPgmProcessId("0");
            toEvetvIntServiceBitacoraTabRes.setLsIndEvento("Respuesta a Cliente para Servicio Parrillas onDemand - Cliente");
            loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabRes);
            
            lsNomFile = "ParrillasClient-" + getId() + ".xml";
            lsIndFileType = "RESPONSE";
            lsIndServiceType = "WsParrillasClient";
            lsIndEstatus = "A";
            lsNomUserName = "neptuno";        
            ByteArrayOutputStream loBaosRes = new ByteArrayOutputStream();
            JAXB.marshal(loResponse, loBaosRes);
            InputStream loFileXmlRes = new ByteArrayInputStream(loBaosRes.toByteArray()); 
            loEntityMappedDao.insertEvetvIntXmlFilesTab(liIdRequest, 
                                                        Integer.parseInt(lsIdService), 
                                                        lsNomFile, 
                                                        lsIndFileType, 
                                                        lsIndServiceType, 
                                                        lsIndEstatus, 
                                                        lsNomUserName, 
                                                        loFileXmlRes
                                                        );
        }catch(Exception loEx){
            System.out.println("Archivo Response no generado");
        }
        
        
        
        return loResponse;
    }
    
    /**
     * Ejecuta validaciones por cada campo ingresado en el request xml del cliente
     * @autor Jorge Luis Bautista Santiago
     * @param tsField
     * @return EvetvIntConfigParamTabBean
     */    
    private EvetvIntConfigParamTabBean getMessageErrDb(String tsField){
        EvetvIntConfigParamTabBean lsRes = new EvetvIntConfigParamTabBean();
        EntityMappedDao            loEmd = new EntityMappedDao();
        lsRes = loEmd.getGeneralParameterBean(tsField, "VALIDATE_FIELD_WS");
        return lsRes;
    }
    /**
     * Genera en base al momento en tiempo una clave de identificacion
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    private String getId(){
        String     lsResponse = null;
        DateFormat loDf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        lsResponse = loDf.format(new java.util.Date(System.currentTimeMillis()));
        return lsResponse;
    }
    
    /**
     * Invoca a proceso en segundo plano para procesar los copys
     * @autor Jorge Luis Bautista Santiago
     * @param tsOrderId
     * @param tsIdServiceCopys
     * @return void
     */
    private void executeParrillasClient(Integer tiIdRequest,
                                        String tsIdService, 
                                        Integer tiIdUser, 
                                        String tsUserName, 
                                        String tsServiceToInvoke,
                                        ScheduleOnDemand toScheduleOnDemand){
        
        System.out.println("SettCron-lsChannelID["+toScheduleOnDemand.getChannelID()+"]");
        System.out.println("SettCron-lsInitialDate["+toScheduleOnDemand.getInitialDate()+"]");
        System.out.println("SettCron-lsEndDate["+toScheduleOnDemand.getEndDate()+"]");
        System.out.println("SettCron-lsBuyUnitID["+toScheduleOnDemand.getBuyUnitID()+"]");
        System.out.println("SettCron-lsCodeTrace["+toScheduleOnDemand.getCodeTrace()+"]");
        Integer piIndProcess = new UtilFaces().getIdConfigParameterByName("Execute");
        Integer piNumPgmProcessID = tiIdRequest;
        Integer piNumEvtbProcessId = 0;
        
        new UtilFaces().insertBitacoraServiceService(tiIdRequest,
                                          Integer.parseInt(tsIdService), 
                                          piIndProcess, 
                                          "Ejecucion de Servicio de Parrillas onDemand como Cliente",
                                          piNumEvtbProcessId, 
                                          piNumPgmProcessID, 
                                          tiIdUser,
                                          tsUserName);
        
        Scheduler loScheduler;
        try {
            String lsIdTrigger = tsIdService + "-" + tsServiceToInvoke;
            loScheduler = new StdSchedulerFactory().getScheduler();
            JobDetail loJob = 
                JobBuilder.newJob(ExecuteParrillasOnDemandCron.class).build();
            Trigger   loTrigger = 
                TriggerBuilder.newTrigger().withIdentity(lsIdTrigger).build();
            JobDataMap loJobDataMap=  loJob.getJobDataMap();
            loJobDataMap.put("lsIdService", tsIdService); 
            loJobDataMap.put("liIdUser", String.valueOf(tiIdUser)); 
            loJobDataMap.put("lsUserName", tsUserName); 
            loJobDataMap.put("lsIdRequestPrr", tiIdRequest); 
            loJobDataMap.put("lsTypeRequest", "wsclient");
            loJobDataMap.put("InitialDate", toScheduleOnDemand.getInitialDate()); 
            loJobDataMap.put("EndDate", toScheduleOnDemand.getEndDate()); 
            loJobDataMap.put("ChannelID", toScheduleOnDemand.getChannelID()); 
            loJobDataMap.put("BuyUnitID", toScheduleOnDemand.getBuyUnitID()); 
            loJobDataMap.put("CodeTrace", toScheduleOnDemand.getCodeTrace()); 
            loScheduler.scheduleJob(loJob, loTrigger);                        
            loScheduler.start();
            
        } catch (Exception loEx) {
            System.out.println("ERR 56"+loEx.getMessage()); 
        } 
    }
    
}
