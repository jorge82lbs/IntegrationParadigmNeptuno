/**
* Project: Paradigm - eVeTV Integration
*
* File: ExecuteVtaTradicionalUsrCron.java
*
* Created on:  Marzo 10, 2018 at 11:00
*
* Copyright (c) - Omw - 2018
*/

package com.televisa.integration.view.jobs;

import com.televisa.comer.integration.model.beans.EvetvIntRequestBean;
import com.televisa.comer.integration.model.beans.pgm.EvetvIntServiceBitacoraTab;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.service.beans.traditionalsale.Channel;
import com.televisa.comer.integration.service.beans.traditionalsale.ChannelsCollection;
import com.televisa.comer.integration.service.beans.traditionalsale.TraditionalInputParameters;
import com.televisa.comer.integration.service.beans.traditionalsale.TraditionalResponse;
import com.televisa.comer.integration.service.beans.types.CronVtaTraditionalBean;
import com.televisa.comer.integration.service.implementation.IntegrationDasVentaTradicional;
import com.televisa.comer.integration.ws.beans.pgm.vtradicional.ScheduleOnDemand;
import com.televisa.integration.model.types.EvetvIntCronConfigTabRowBean;
import com.televisa.integration.model.types.EvetvIntServicesParamTabBean;
import com.televisa.integration.view.front.daos.ViewObjectDao;
import com.televisa.integration.view.front.util.UtilFaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/** Ejecuta en seguno plano el proceso de invocacion de Venta Tradicional <br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Marzo 10, 2018, 12:00 pm
 */

public class ExecuteVtaTradicionalUsrCron implements Job{
    /**
     * Ejecuta la invocacion en segundo plano para el servicio de venta tradicional
     * @autor Jorge Luis Bautista Santiago  
     * @param loJobExecutionContext
     * @return void
     */
    @Override
    public void execute(JobExecutionContext toJobExecutionContext) throws JobExecutionException {
        JobDataMap                loDataMap = toJobExecutionContext.getJobDetail().getJobDataMap();
        String                    lsIdService = loDataMap.getString("lsIdService");  
        String                    lsParamUserName = loDataMap.getString("lsUserName");
        String                    lsParamIdUser = loDataMap.getString("liIdUser");
        Integer                   liParamIdUser = Integer.parseInt(lsParamIdUser);    
        String                    lsTypeRequest = loDataMap.getString("lsTypeRequest");
        String                    lsIdRequestOnDemand = loDataMap.getString("lsIdRequestOnDemand");
        Integer                   liIdParameter = new ViewObjectDao().getMaxIdParadigm("RstVtaTradicional") + 1;
        String                    lsIdRequestTrad = String.valueOf(liIdParameter);
        Integer                   liIdRequestTrad = Integer.parseInt(lsIdRequestTrad);        
        //String                    lsStatusType = loDataMap.getString("lsStatusType");  
        String                    lsStatusType = "PN";
        boolean                   lbFlagCi = false;
        //ApplicationModule         loAm = Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        //AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
        EntityMappedDao           loEntityMappedDao = new EntityMappedDao();
        ViewObjectDao             loService = new ViewObjectDao();
        System.out.println("========= Dentro de Job, aqui ya es otro idRequest["+lsIdRequestTrad+"] pero tambien tenemos al del cliente wsdl["+lsIdRequestOnDemand+"]======");
        
        if(lsTypeRequest.equalsIgnoreCase("wsclient")){
            lbFlagCi = false;
            lsStatusType = "PN";//Carga Inicial
        }else{
            //Proceso para obtener lsStatusType        
            List<EvetvIntServicesParamTabBean> laListPrm = 
                loService.getParametersServices(Integer.parseInt(lsIdService)); 
            // Validar en Parametros del servicio, si se trata de carga inicial//
            for(EvetvIntServicesParamTabBean loParm : laListPrm){
                if(loParm.getIndParameter().equalsIgnoreCase("CARGA_INICIAL")){
                    String lsValParameter = loParm.getIndValParameter();
                    if(lsValParameter != null){
                        if(!lsValParameter.equalsIgnoreCase("NO")){
                            lbFlagCi = true;
                            lsStatusType = "CI";//Carga Inicial
                        }                    
                    }
                } 
            }
        }
        // Obtener Configuracion Cron
        EvetvIntCronConfigTabRowBean loRowCron = 
            loService.getRowCronConfigByServiceModel(Integer.parseInt(lsIdService));
        if(!lbFlagCi){
            lsStatusType = "PN";
            String lsDeadLine = null;
            if(loRowCron != null){
                lsDeadLine = loRowCron.getAttribute1() == null ? "" : loRowCron.getAttribute1();
                if(lsDeadLine.length() > 1){
                    boolean lbFlagType = new UtilFaces().isCurrentHourValid(lsDeadLine);
                    if(lbFlagType){ //La hora actual es menor o igual a hora deadline
                        lsStatusType = "SC";//Semana Por Confirmar
                    }
                }
            }
        }
        
        if(lsTypeRequest.equalsIgnoreCase("wsclient")){
            lbFlagCi = false;
            lsStatusType = "PN";//Proceso Nocturno
            String lsDeadLine = "23:01";
            boolean lbFlagType = new UtilFaces().isCurrentHourValid(lsDeadLine);
            if(lbFlagType){ //La hora actual es menor o igual a hora deadline
                lsStatusType = "SC";//Semana Por Confirmar
            }
        }
        //Fin de Proceso para obtener lsStatusType
        try{
            boolean                      lbFlagProcess = true;
            
            if(lsTypeRequest.equalsIgnoreCase("wsclient")){
                lbFlagProcess = true;   
            }else{            
                //Validar los dias de ejecucion
                if(loRowCron != null){
                    UtilFaces loUtFa = new UtilFaces();
                    String    lsCurrentDay = loUtFa.getCurrentDayOfWeek();
                    lbFlagProcess = validateDay(lsCurrentDay, loRowCron);
                }
                if(loRowCron != null){
                    if(loRowCron.getIndEstatus().equalsIgnoreCase("3")){
                        lbFlagProcess = false;   
                    }
                }
            }
            if(lbFlagProcess){
                
                // FECHA INICIO //
                String lsDateIni = null;
                // FECHA FIN //
                String lsDateFin = null;
                // BUYUNIT  //
                String lsBuyunit = null;
                // CODE_TRACE  //
                String lsCodeTrace = null;
                //CANALES//
                List<String>                       laChannels = new ArrayList<String>();
                
                if(lsTypeRequest.equalsIgnoreCase("wsclient")){
                    String lsChannelid = loDataMap.getString("ChannelID");
                    lsCodeTrace = loDataMap.getString("CodeTrace");
                    laChannels.add(lsChannelid);
                    lsDateIni = loDataMap.getString("InitialDate");
                    lsDateFin = loDataMap.getString("EndDate");
                }else{
                    lsCodeTrace = "";
                    List<EvetvIntServicesParamTabBean> laList = 
                        loService.getParametersServices(Integer.parseInt(lsIdService)); 
                    for(EvetvIntServicesParamTabBean loParm : laList){
                        if(loParm.getIndParameter().equalsIgnoreCase("CANAL")){
                            laChannels.add(loParm.getIndValParameter());
                        }                       
                    }
                }
                if(laChannels.size() > 0){                   
                    TraditionalInputParameters loInputTraditional = new TraditionalInputParameters();
                    loInputTraditional.setIdRequestTraditionalReq(lsIdRequestTrad);
                    loInputTraditional.setDateQuery(lsStatusType);
                    loInputTraditional.setIdService(lsIdService);
                    loInputTraditional.setUserName(lsParamUserName);
                    loInputTraditional.setIdUser(lsParamIdUser);
                    ChannelsCollection laChColl = new ChannelsCollection();
                    for(String lsCanal:laChannels){
                        Channel loChannel = new Channel();
                        loChannel.setChannel(lsCanal);
                        laChColl.getChannels().add(loChannel);
                    }
                    
                    loInputTraditional.setChannelList(laChColl);
                    /*
                    System.out.println("Invocando vtradicional onDemand desde cron.......Temporalmente deshabilitado " + new Date());
                    try{
                        Thread.sleep(60000);
                    }catch(Exception loExp){
                        System.out.println("Error en sleep: "+loExp.getMessage());    
                    }
                    System.out.println("fin de sleep");*/
                    IntegrationDasVentaTradicional  loTraditionalSale = 
                        new IntegrationDasVentaTradicional();
                    System.out.println("Invocando venta Tradicional (Integracion)");
                    TraditionalResponse             loPgrRes = 
                        loTraditionalSale.invokeTraditionalSale(loInputTraditional, 
                                                                lsDateIni, 
                                                                lsDateFin, 
                                                                lsCodeTrace,
                                                                Integer.parseInt(lsIdRequestOnDemand));                                        
                    System.out.println("Invocando venta Tradicional (Integracion)... Fin con Exito");
                    Integer                         liIndProcess = 
                        new UtilFaces().getIdConfigParameterByName("ProcessFinish");
                    EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
                    toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
                    toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsIndEvento("["+lsIdRequestOnDemand+"] Invocacion Finalizada para Servicio de Venta Tradicional Por Usuario");
                    loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                                       liParamIdUser, 
                                                       lsParamUserName);
                    loPgrRes.getXmlMessageResponse();
                }else{
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("ErrParameters");
                    EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
                    toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
                    toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsIndEvento("Error de Parametros en Invocacion del Servicio de Venta Tradicional Por Usuario");
                    loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                                       liParamIdUser, 
                                                       lsParamUserName);
                }      
            }else{//El cron se ejecuta sin relizar acciones
                Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("ErrParameters");
                
                EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
                toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
                toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
                toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
                toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
                toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
                toEvetvIntServiceBitacoraTabR.setLsIndEvento("["+lsIdRequestOnDemand+"]Cron ejecutado sin realizar acciones par el Servicio de Venta Tradicional Por Usuario");
                loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                                   liParamIdUser, 
                                                   lsParamUserName);
            }
        } catch (Exception loEx) {
            Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("GeneralError");
            System.out.println("FATAL ERROR: Invocando servicio de neptuno......"+loEx.getMessage());
            EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
            toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
            toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
            toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
            toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
            toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
            toEvetvIntServiceBitacoraTabR.setLsIndEvento("Error en Invocacion del Servicio de Venta Tradicional Por Usuario" + 
                                              loEx.getMessage());
            loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                               liParamIdUser, 
                                               lsParamUserName);
        }
        finally{
            com.televisa.comer.integration.ws.model.daos.EntityMappedDao loAux =
                new com.televisa.comer.integration.ws.model.daos.EntityMappedDao();
            Integer tiIdRequest = Integer.parseInt(lsIdRequestOnDemand);
            System.out.println("(FINALLY)=(onDemand)=======>>>Finalizando proceso principal["+tiIdRequest+"] idRequest cliente wsdl <<<<<========");
            loAux.updateServiceRequestById(tiIdRequest, null, "A");    
        }
        //Verificar si no existen procesos en cola, solo para onDemand
        com.televisa.comer.integration.ws.model.daos.EntityMappedDao loAux =
            new com.televisa.comer.integration.ws.model.daos.EntityMappedDao();
        Integer liResQu = loAux.validateQueueVtaTradicional();
        System.out.println("Existen procesos detenidos ["+liResQu+"]");
        if(liResQu > 0){
            List<EvetvIntRequestBean> laReqs = loAux.getRequestQueuedVtraditional();
            System.out.println("Parametros concatenados ["+laReqs.get(0).getLsNomUserName()+"]");
            String[] laParmas = laReqs.get(0).getLsNomUserName().split("\\|");
            CronVtaTraditionalBean loCron = new CronVtaTraditionalBean();
            System.out.println("laParmas[0]: "+laParmas[0]);
            loCron.setLsIdService(laParmas[0]);
            System.out.println("laParmas[1]: "+laParmas[1]);
            loCron.setLiIdUser(laParmas[1]);
            System.out.println("laParmas[2]: "+laParmas[2]);
            loCron.setLsUserName(laParmas[2]);
            loCron.setLiIdRequest(laReqs.get(0).getLsIdRequest());
            loCron.setLsWsclient(laParmas[4]);
            loCron.setLsInitialDate(laParmas[5]);
            loCron.setLsEndDate(laParmas[6]);
            loCron.setLsChannelID(laParmas[7]);
            loCron.setLsCodeTrace(laParmas[8]);
            String lsExecutionType = loCron.getLsWsclient();
            ScheduleOnDemand loPrmScheduleOnDemand = new ScheduleOnDemand();
            loPrmScheduleOnDemand.setChannelID(loCron.getLsChannelID());
            loPrmScheduleOnDemand.setInitialDate(loCron.getLsInitialDate());
            loPrmScheduleOnDemand.setEndDate(loCron.getLsEndDate());
            loPrmScheduleOnDemand.setCodeTrace(loCron.getLsCodeTrace());
            Integer tiIdUser = 1;
            String tsUserName = "neptuno";
            String tsServiceToInvoke = "WsVtaTradicionalClient";
            try{
                
                System.out.println("============================================");
                System.out.println("Inicio cron hijo de venta tradicional onDemand - Cliente "+new Date());
                System.out.println("IdRequest: ["+loCron.getLiIdRequest()+"]");
                System.out.println("lsIdService: ["+lsIdService+"]");
                System.out.println("tiIdUser: ["+tiIdUser+"]");
                System.out.println("tsUserName: ["+tsUserName+"]");
                System.out.println("tsServiceToInvoke: ["+tsServiceToInvoke+"]");
                System.out.println("lsExecutionType: ["+lsExecutionType+"]");
                System.out.println("============================================");
                System.out.println("ChannelID: ["+loPrmScheduleOnDemand.getChannelID()+"]");
                System.out.println("InitialDate: ["+loPrmScheduleOnDemand.getInitialDate()+"]");
                System.out.println("EndDate: ["+loPrmScheduleOnDemand.getEndDate()+"]");
                System.out.println("CodeTrace: ["+loPrmScheduleOnDemand.getCodeTrace()+"]");
                System.out.println("============================================");
                
                if(!lsExecutionType.equalsIgnoreCase("wsclient")){
                    System.out.println("================ Ejecucion NORMAL ================== desde wsclient a las "+new Date());
                    lsIdService = loCron.getLsIdService();
                    executeVtaTradicionalClient(Integer.parseInt(loCron.getLiIdRequest()),
                                           lsIdService,
                                           tiIdUser,
                                           tsUserName,
                                           tsServiceToInvoke,
                                           loPrmScheduleOnDemand
                                           );
                }else{
                    System.out.println("================ Ejecucion wsclient ================== desde wsclient a las "+new Date());
                    executeVtaTradicionalClientOnDem(Integer.parseInt(loCron.getLiIdRequest()),
                                           lsIdService,
                                           tiIdUser,
                                           tsUserName,
                                           tsServiceToInvoke,
                                           loPrmScheduleOnDemand
                                           );
                }
                System.out.println("Fin de ejecucion de cron vtradicional onDemand - Cliente (cron) "+new Date());
                
            }catch(Exception loEx){
                EntityMappedDao           loEntityMappedDaoEx = new EntityMappedDao();
                String lsIndProcessR = 
                    loEntityMappedDaoEx.getGeneralParameterID("GeneralError", "PROCESS_INTEGRATION");
                
                EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTab = new EvetvIntServiceBitacoraTab();
                toEvetvIntServiceBitacoraTab.setLsIdLogServices(lsIdService);
                toEvetvIntServiceBitacoraTab.setLsIdService(lsIdService);
                toEvetvIntServiceBitacoraTab.setLsIndProcess(lsIndProcessR); //Tipo de Proceso
                toEvetvIntServiceBitacoraTab.setLsNumEvtbProcessId(loCron.getLsCodeTrace());
                toEvetvIntServiceBitacoraTab.setLsNumPgmProcessId("0");        
                toEvetvIntServiceBitacoraTab.setLsIndEvento("ERROR: Vta Tradicional onDemand - Cliente " +loEx.getMessage());
                loEntityMappedDaoEx.insertBitacoraWs(toEvetvIntServiceBitacoraTab, tiIdUser, tsUserName);
            }/*finally{                    
                Integer tiIdRequest = Integer.parseInt(lsIdRequestOnDemand);
                System.out.println("(onDemand)Finalizando proceso de segunda iteración ["+tiIdRequest+"]");
                loAux.updateServiceRequestById(tiIdRequest, null, "A");  
            }*/
        }
    }
    
    /**
     * Valida si el proceso es ejecutable, de acuerdo a la configracion
     * @autor Jorge Luis Bautista Santiago  
     * @param toCronConfigBean
     * @return boolean
     */
    private boolean validateProcessCron(EvetvIntCronConfigTabRowBean toCronConfigBean){
        boolean   lbFlag = true;
        UtilFaces loUtFa = new UtilFaces();
        String    lsCurrentDay = loUtFa.getCurrentDayOfWeek();
        String    lsPeridicity = toCronConfigBean.getIndPeriodicity();
        if(lsPeridicity.equalsIgnoreCase("MESES")){
            lbFlag = true;
        }
        if(lsPeridicity.equalsIgnoreCase("SEMANAS")){
            lbFlag = true;
        }
        if(lsPeridicity.equalsIgnoreCase("DIAS")){
            //Excepto Dia
            lbFlag = validateDay(lsCurrentDay, toCronConfigBean);
        }
        if(lsPeridicity.equalsIgnoreCase("MINUTOS")){
            //Excepto Dia y hora
            lbFlag = validateDay(lsCurrentDay, toCronConfigBean);
            if(lbFlag){
                lbFlag = loUtFa.isCurrentHourValid(toCronConfigBean.getAttribute1());
            }
        }
        if(lsPeridicity.equalsIgnoreCase("HORAS")){
            //Excepto Dia y hora
            lbFlag = validateDay(lsCurrentDay, toCronConfigBean);
            if(lbFlag){
                lbFlag = loUtFa.isCurrentHourValid(toCronConfigBean.getAttribute1());
            }
        }
        return lbFlag;
    }
    
    /**
     * Validaciond el dia actual respecto al dia configurado
     * @autor Jorge Luis Bautista Santiago  
     * @param tsCurrentDay
     * @param toCronConfigBean
     * @return boolean
     */
    private boolean validateDay(String tsCurrentDay, EvetvIntCronConfigTabRowBean toCronConfigBean){
        boolean lbFlag = true;
        if(tsCurrentDay.equalsIgnoreCase("MONDAY") && toCronConfigBean.getIndMonday() == 1){
            lbFlag = false;
        }
        if(tsCurrentDay.equalsIgnoreCase("TUESDAY") && toCronConfigBean.getIndTuesday() == 1){
            lbFlag = false;
        }
        if(tsCurrentDay.equalsIgnoreCase("WEDNESDAY") && toCronConfigBean.getIndWednesday() == 1){
            lbFlag = false;
        }
        if(tsCurrentDay.equalsIgnoreCase("THURSDAY") && toCronConfigBean.getIndThursday() == 1){
            lbFlag = false;
        }
        if(tsCurrentDay.equalsIgnoreCase("FRIDAY") && toCronConfigBean.getIndFriday() == 1){
            lbFlag = false;
        }
        if(tsCurrentDay.equalsIgnoreCase("SATURDAY") && toCronConfigBean.getIndSaturday() == 1){
            lbFlag = false;
        }
        if(tsCurrentDay.equalsIgnoreCase("SUNDAY") && toCronConfigBean.getIndSunday() == 1){
            lbFlag = false;
        }
        return lbFlag;
    }
    
    /**
     * Invoca a proceso en segundo plano para procesar
     * @autor Jorge Luis Bautista Santiago
     * @param tsOrderId
     * @param tsIdServiceCopys
     * @return void
     */
    private void executeVtaTradicionalClient(Integer tiIdRequest,
                                        String tsIdService, 
                                        Integer tiIdUser, 
                                        String tsUserName, 
                                        String tsServiceToInvoke,
                                        ScheduleOnDemand toScheduleOnDemand){
        Integer piIndProcess = new UtilFaces().getIdConfigParameterByName("Execute");
        Integer piNumPgmProcessID = tiIdRequest;
        Integer piNumEvtbProcessId = 0;

        new UtilFaces().insertBitacoraServiceService(tiIdRequest,
                                          Integer.parseInt(tsIdService), 
                                          piIndProcess, 
                                          "("+tiIdRequest+")Ejecucion de Servicio de V. Tradicional",
                                          piNumEvtbProcessId, 
                                          piNumPgmProcessID, 
                                          tiIdUser,
                                          tsUserName);
        
        com.televisa.comer.integration.ws.model.daos.EntityMappedDao loEntityMappedDao2 = 
            new com.televisa.comer.integration.ws.model.daos.EntityMappedDao();
        loEntityMappedDao2.updateServiceRequestById(tiIdRequest, null, "E");
        Scheduler loScheduler;
        try {
            String lsIdTrigger = tsIdService + "-" + tsServiceToInvoke+String.valueOf(tiIdRequest);
            loScheduler = new StdSchedulerFactory().getScheduler();
            JobDetail loJob = 
                JobBuilder.newJob(ExecuteVtaTradicionalCron.class).build();
            Trigger   loTrigger = 
                TriggerBuilder.newTrigger().withIdentity(lsIdTrigger).build();
            JobDataMap loJobDataMap=  loJob.getJobDataMap();
            loJobDataMap.put("lsIdService", tsIdService); 
            loJobDataMap.put("liIdUser", String.valueOf(tiIdUser)); 
            loJobDataMap.put("lsUserName", tsUserName); 
            loJobDataMap.put("lsIdRequestOnDemand", String.valueOf(tiIdRequest));   
            loJobDataMap.put("lsTypeRequest", "normal");
            loScheduler.scheduleJob(loJob, loTrigger);                        
            loScheduler.start();    
            
        } catch (Exception loEx) {
            System.out.println("ERR 588 "+loEx.getMessage()); 
        } 
    }
    
    
    /**
     * Invoca a proceso en segundo plano para procesar
     * @autor Jorge Luis Bautista Santiago
     * @param tsOrderId
     * @param tsIdServiceCopys
     * @return void
     */
    private void executeVtaTradicionalClientOnDem(Integer tiIdRequest,
                                        String tsIdService, 
                                        Integer tiIdUser, 
                                        String tsUserName, 
                                        String tsServiceToInvoke,
                                        ScheduleOnDemand toScheduleOnDemand){
        Integer piIndProcess = new UtilFaces().getIdConfigParameterByName("Execute");
        Integer piNumPgmProcessID = tiIdRequest;
        Integer piNumEvtbProcessId = 0;

        new UtilFaces().insertBitacoraServiceService(tiIdRequest,
                                          Integer.parseInt(tsIdService), 
                                          piIndProcess, 
                                          "("+tiIdRequest+")Ejecucion de Servicio de V. Tradicional onDemand como Cliente",
                                          piNumEvtbProcessId, 
                                          piNumPgmProcessID, 
                                          tiIdUser,
                                          tsUserName);
        
        com.televisa.comer.integration.ws.model.daos.EntityMappedDao loEntityMappedDao2 = 
            new com.televisa.comer.integration.ws.model.daos.EntityMappedDao();
        loEntityMappedDao2.updateServiceRequestById(tiIdRequest, null, "E");
        Scheduler loScheduler;
        try {
            String lsIdTrigger = tsIdService + "-" + tsServiceToInvoke+String.valueOf(tiIdRequest);
            loScheduler = new StdSchedulerFactory().getScheduler();
            JobDetail loJob = 
                JobBuilder.newJob(ExecuteVtaTradicionalUsrCron.class).build();
            Trigger   loTrigger = 
                TriggerBuilder.newTrigger().withIdentity(lsIdTrigger).build();
            JobDataMap loJobDataMap=  loJob.getJobDataMap();
            loJobDataMap.put("lsIdService", tsIdService); 
            loJobDataMap.put("liIdUser", String.valueOf(tiIdUser)); 
            loJobDataMap.put("lsUserName", tsUserName); 
            loJobDataMap.put("lsIdRequestOnDemand", String.valueOf(tiIdRequest)); 
            loJobDataMap.put("lsTypeRequest", "wsclient");
            loJobDataMap.put("InitialDate", toScheduleOnDemand.getInitialDate()); 
            loJobDataMap.put("EndDate", toScheduleOnDemand.getEndDate()); 
            loJobDataMap.put("ChannelID", toScheduleOnDemand.getChannelID()); 
            loJobDataMap.put("CodeTrace", toScheduleOnDemand.getCodeTrace()); 
            loScheduler.scheduleJob(loJob, loTrigger);                        
            loScheduler.start();    
            
        } catch (Exception loEx) {
            System.out.println("ERR 57 "+loEx.getMessage()); 
        } 
    }
}
