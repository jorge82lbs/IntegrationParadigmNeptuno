/**
* Project: Paradigm - eVeTV Integration
*
* File: ExecuteParrillasOnDemandCron.java
*
* Created on:  Febrero 16, 2018 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package com.televisa.integration.view.jobs;

import com.televisa.comer.integration.model.beans.pgm.EvetvIntServiceBitacoraTab;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.service.beans.parrillascortes.Channel;
import com.televisa.comer.integration.service.beans.parrillascortes.ChannelsCollection;
import com.televisa.comer.integration.service.beans.parrillascortes.ParrillasCortesInputParameters;
import com.televisa.comer.integration.service.beans.parrillascortes.ParrillasCortesResponse;
import com.televisa.comer.integration.service.implementation.IntegrationDasParrillasCortes;
import com.televisa.comer.integration.service.implementation.IntegrationDasParrillasOnDemand;
import com.televisa.integration.model.types.EvetvIntCronConfigTabRowBean;
import com.televisa.integration.model.types.EvetvIntServicesParamTabBean;
import com.televisa.integration.view.front.daos.ViewObjectDao;
import com.televisa.integration.view.front.util.UtilFaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/** Esta clase es un cliente del servicio de parrillas <br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Febrero 16, 2018, 12:00 pm
 */
public class ExecuteParrillasOnDemandCron implements Job{
   
    /**
     * Ejecuta la invocacion al servicio de parrillas
     * @autor Jorge Luis Bautista Santiago  
     * @param loJobExecutionContext
     * @return void
     */
    @Override
    public void execute(JobExecutionContext toJobExecutionContext) throws JobExecutionException {
        System.out.println("CRON- Iniciando metodo Execute para PARRILLAS-BREAKS-ON-DEMAND en Fecha y hora ["+new Date()+"]");
        JobDataMap                loDataMap = toJobExecutionContext.getJobDetail().getJobDataMap();
        String                    lsIdService = loDataMap.getString("lsIdService");  
        System.out.println("lsIdService ["+lsIdService+"]");
        String                    lsParamUserName = loDataMap.getString("lsUserName");
        System.out.println("lsParamUserName ["+lsParamUserName+"]");
        String                    lsParamIdUser = loDataMap.getString("liIdUser");
        System.out.println("lsParamIdUser ["+lsParamIdUser+"]");
        Integer                   liParamIdUser = Integer.parseInt(lsParamIdUser);    
        System.out.println("liParamIdUser ["+liParamIdUser+"]");
        String                    lsTypeRequest = loDataMap.getString("lsTypeRequest");
        System.out.println("lsTypeRequest ["+lsTypeRequest+"]");
        Integer                   liIdParameter = new ViewObjectDao().getMaxIdParadigm("RstParrillas") + 1;
        System.out.println("liIdParameter ["+liIdParameter+"]");
        String                    lsIdRequestPrr = String.valueOf(liIdParameter);
        System.out.println("lsIdRequestPrr ["+lsIdRequestPrr+"]");
        EntityMappedDao           loEntityMappedDao = new EntityMappedDao();
        ViewObjectDao             loService = new ViewObjectDao();
         
        try{
            EvetvIntCronConfigTabRowBean loCronConfig = 
                loService.getRowCronConfigByServiceModel(Integer.parseInt(lsIdService));
            boolean                      lbFlagProcess = true;
            if(loCronConfig != null){
                lbFlagProcess = validateProcessCron(loCronConfig);
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
                List<String> laChannels = new ArrayList<String>();
                
                if(lsTypeRequest.equalsIgnoreCase("wsclient")){
                    System.out.println("tipo de solicitud es wsclient");
                    lsDateIni = loDataMap.getString("InitialDate");
                    lsDateFin = loDataMap.getString("EndDate");
                    lsBuyunit = loDataMap.getString("BuyUnitID");
                    laChannels.add(loDataMap.getString("ChannelID"));
                    lsCodeTrace = loDataMap.getString("CodeTrace");
                    
                    System.out.println("Cron(wsclient)-lsChannelID["+loDataMap.getString("ChannelID")+"]");
                    System.out.println("Cron(wsclient)-lsInitialDate["+lsDateIni+"]");
                    System.out.println("Cron(wsclient)-lsEndDate["+lsDateFin+"]");
                    System.out.println("Cron(wsclient)-lsBuyUnitID["+lsBuyunit+"]");
                    System.out.println("Cron(wsclient)-lsCodeTrace["+lsCodeTrace+"]");
                    
                }else{
                    lsCodeTrace = "";
                    List<EvetvIntServicesParamTabBean> laList = 
                        loService.getParametersServices(Integer.parseInt(lsIdService)); 
                    
                    for(EvetvIntServicesParamTabBean loParm : laList){
                        if(loParm.getIndParameter().equalsIgnoreCase("FECHA INICIO")){
                            lsDateIni = loParm.getIndValParameter();
                        }                       
                    }
                    
                    for(EvetvIntServicesParamTabBean loParm : laList){
                        if(loParm.getIndParameter().equalsIgnoreCase("FECHA FIN")){
                            lsDateFin = loParm.getIndValParameter();
                        }                       
                    }
                    
                    for(EvetvIntServicesParamTabBean loParm : laList){
                        if(loParm.getIndParameter().equalsIgnoreCase("BUYUNIT")){
                            lsBuyunit = loParm.getIndValParameter();
                        }                       
                    }
                   
                    for(EvetvIntServicesParamTabBean loParm : laList){
                        if(loParm.getIndParameter().equalsIgnoreCase("CANAL")){
                            laChannels.add(loParm.getIndValParameter());
                        }                       
                    }
                    
                    System.out.println("Cron(normal)-lsChannelID["+laChannels.get(0)+"]");
                    System.out.println("Cron(normal)-lsInitialDate["+lsDateIni+"]");
                    System.out.println("Cron(normal)-lsEndDate["+lsDateFin+"]");
                    System.out.println("Cron(normal)-lsBuyUnitID["+lsBuyunit+"]");
                    System.out.println("Cron(normal)-lsCodeTrace["+lsCodeTrace+"]");
                    
                }
                
                boolean lbProcessCron = true;   
                System.out.println("laChannels.size() ["+laChannels.size()+"]");
                if(!(laChannels.size() > 0)){
                    lbProcessCron = false;
                }
                System.out.println("lsDateIni ["+lsDateIni+"] && lsDateFin["+lsDateFin+"]");
                if(lsDateIni != null && lsDateFin == null){
                    lbProcessCron = false;
                }
                if(lsDateIni == null && lsDateFin != null){
                    lbProcessCron = false;
                }
                System.out.println("lbProcessCron ["+lbProcessCron+"]");      
                if(lbProcessCron){                    
                    System.out.println("*******************Parametros Correctos************************");
                    ParrillasCortesInputParameters loInputParrillas = new ParrillasCortesInputParameters();
                    loInputParrillas.setIdRequestParrillasCortesReq(lsIdRequestPrr);
                    loInputParrillas.setDateQuery("lsDateQuery");
                    loInputParrillas.setIdService(lsIdService);
                    loInputParrillas.setUserName(lsParamUserName);
                    loInputParrillas.setIdUser(lsParamIdUser);
                                    
                    ChannelsCollection laChColl = new ChannelsCollection();
                    
                    for(String lsCanal:laChannels){
                        Channel loChannel = new Channel();
                        loChannel.setChannel(lsCanal);
                        laChColl.getChannels().add(loChannel);
                    }
                    
                    loInputParrillas.setChannelList(laChColl);
                    IntegrationDasParrillasOnDemand  loInt =  new IntegrationDasParrillasOnDemand();
                    //System.out.println("Invocando parrillas onDemand desde cron.......Temporalmente deshabilitado ");
                    /*
                    try{
                        Thread.sleep(60000);
                    }catch(Exception loExp){
                        System.out.println("Error en sleep: "+loExp.getMessage());    
                    }
                    System.out.println("fin de sleep");
*/
                    
                    ParrillasCortesResponse        loPgrRes = 
                        loInt.invokeParrillasProgramasyCortes(loInputParrillas, 
                                                              lsDateIni, 
                                                              lsDateFin, 
                                                              lsBuyunit, lsCodeTrace);

                    Integer                        liIndProcess = 
                        new UtilFaces().getIdConfigParameterByName("ProcessFinish");
                    
                    EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
                    toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
                    toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsIndEvento("Invocacion Finalizada para Servicio de Parrillas OnDemand");
                    loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                                       liParamIdUser, 
                                                       lsParamUserName);
                    // Revisar si existen solicitudes encoladas, si es el caso
                    // ejecutar la siguiente solicitud encolada de acuerdo al orden de llegada
                    // ejecutar como cron, crear otra instancia
                    loPgrRes.getXmlMessageResponse();
                    
                                   
                }else{
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("ErrParameters");
                    
                    EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
                    toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
                    toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsIndEvento("Error de Parametros en Invocacion del Servicio de Parrillas onDemand");
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
                toEvetvIntServiceBitacoraTabR.setLsIndEvento("Cron ejecutado sin realizar acciones par el Servicio de Parrillas onDemand");
                loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                                   liParamIdUser, 
                                                   lsParamUserName);
            }
        } catch (Exception loEx) {
            System.out.println("FATAL ERROR: Invocando servicio de neptuno......"+loEx.getMessage());
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
        boolean   lbFlagDeadLine = true;
        String    lsDeadLine = 
            toCronConfigBean.getAttribute1() == null ? "" : 
            toCronConfigBean.getAttribute1();
        
        if(toCronConfigBean.getIndEstatus().equalsIgnoreCase("3")){
            lbFlag = false;
        }else{
            if(lsDeadLine.length() > 0 ){
                lbFlagDeadLine = new UtilFaces().isCurrentHourValid(lsDeadLine);
            }
            if(lbFlagDeadLine){
                
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
            }else{
                lbFlag = false;
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
}
