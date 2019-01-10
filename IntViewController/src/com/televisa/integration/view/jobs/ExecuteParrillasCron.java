/**
* Project: Paradigm - eVeTV Integration
*
* File: ExecuteParrillasCron.java
*
* Created on:  Septiembre 6, 2017 at 11:00
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
 * @date Septiembre 14, 2017, 12:00 pm
 */
public class ExecuteParrillasCron implements Job{
    
    String              gsAppModule = "AppModuleIntergrationDataControl";
    String              gsAmDef = "com.televisa.integration.model.AppModuleIntergrationImpl";
    String              gsConfig = "AppModuleIntergrationLocal";
    
    /**
     * Ejecuta la invocacion al servicio de parrillas
     * @autor Jorge Luis Bautista Santiago  
     * @param loJobExecutionContext
     * @return void
     */
    @Override
    public void execute(JobExecutionContext toJobExecutionContext) throws JobExecutionException {
        System.out.println("CRON- Iniciando metodo Execute para PARRILLAS-BREAKS en Fecha y hora ["+new Date()+"]");
        JobDataMap                loDataMap = toJobExecutionContext.getJobDetail().getJobDataMap();
        String                    lsIdService = loDataMap.getString("lsIdService");  
        String                    lsParamUserName = loDataMap.getString("lsUserName");
        String                    lsParamIdUser = loDataMap.getString("liIdUser");
        Integer                   liParamIdUser = Integer.parseInt(lsParamIdUser);    
        String                    lsTypeRequest = loDataMap.getString("lsTypeRequest");
        //String                    lsIdRequestPrr = loDataMap.getString("lsIdRequestPrr");
        Integer                   liIdParameter = new ViewObjectDao().getMaxIdParadigm("RstParrillas") + 1;
        String                    lsIdRequestPrr = String.valueOf(liIdParameter);
        //Integer                   liIdRequestPrr = Integer.parseInt(lsIdRequestPrr);        
        //ApplicationModule         loAm = Configuration.createRootApplicationModule(gsAmDef, gsConfig);
        //AppModuleIntergrationImpl loService = (AppModuleIntergrationImpl)loAm;
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
                List<EvetvIntServicesParamTabBean> laList = 
                    loService.getParametersServices(Integer.parseInt(lsIdService)); 
                // FECHA//
                String lsDateQuery = null;
                for(EvetvIntServicesParamTabBean loParm : laList){
                    if(loParm.getIndParameter().equalsIgnoreCase("FECHA")){
                        lsDateQuery = loParm.getIndValParameter();
                    }                       
                }
                //CANALES//
                List<String> laChannels = new ArrayList<String>();
                for(EvetvIntServicesParamTabBean loParm : laList){
                    if(loParm.getIndParameter().equalsIgnoreCase("CANAL")){
                        laChannels.add(loParm.getIndValParameter());
                    }                       
                }
                if(laChannels.size() > 0){
                    if(lsDateQuery == null){
                        lsDateQuery = "NA"; 
                    }
                    System.out.println("*******************Parametros Correctos************************");
                                 
                    ParrillasCortesInputParameters loInputParrillas = new ParrillasCortesInputParameters();
                    loInputParrillas.setIdRequestParrillasCortesReq(lsIdRequestPrr);
                    loInputParrillas.setDateQuery(lsDateQuery);
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
                    IntegrationDasParrillasCortes  loInt =  new IntegrationDasParrillasCortes();
                    System.out.println("invocando parrillas desde cron.......");
                    ParrillasCortesResponse        loPgrRes = loInt.invokeParrillasProgramasyCortes(loInputParrillas);
                    Integer                        liIndProcess = 
                        new UtilFaces().getIdConfigParameterByName("ProcessFinish");
                    
                    EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
                    toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
                    toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsIndEvento("Invocacion Finalizada para Servicio de Parrillas Cortes");
                    loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                                       liParamIdUser, 
                                                       lsParamUserName);
                    
                    //Integer                        liNumPgmProcessID = liIdRequestPrr;
                    //Integer                        liNumEvtbProcessId = 0;                  
                  /*
                    new UtilFaces().insertBitacoraServiceService(liIdRequestPrr,
                                                      Integer.parseInt(lsIdService), 
                                                      liIndProcess, 
                                                      "Invocacion Finalizada para Servicio de Parrillas Cortes",
                                                      liNumEvtbProcessId, 
                                                      liNumPgmProcessID, 
                                                      liParamIdUser,
                                                      lsParamUserName);   
                    */
                    
                    loPgrRes.getXmlMessageResponse();
                                   
                }else{
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("ErrParameters");
                    
                    EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
                    toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
                    toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsIndEvento("Error de Parametros en Invocacion del Servicio de Parrillas Cortes");
                    loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                                       liParamIdUser, 
                                                       lsParamUserName);
                    /*
                    Integer liNumPgmProcessID = 0;
                    Integer liNumEvtbProcessId = 0;
                    new UtilFaces().insertBitacoraServiceService(liIdRequestPrr,
                                                      Integer.parseInt(lsIdService), 
                                                      liIndProcess, 
                                                      "Error de Parametros en Invocacion del Servicio de Parrillas Cortes",
                                                      liNumEvtbProcessId, 
                                                      liNumPgmProcessID, 
                                                      liParamIdUser,
                                                      lsParamUserName);     */  
                }       
            }else{//El cron se ejecuta sin relizar acciones
                Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("ErrParameters");
                
                EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
                toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
                toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
                toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
                toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
                toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
                toEvetvIntServiceBitacoraTabR.setLsIndEvento("Cron ejecutado sin realizar acciones par el Servicio de Parrillas-Cortes");
                loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                                   liParamIdUser, 
                                                   lsParamUserName);
                
                
                /*
                Integer liNumPgmProcessID = 0;
                Integer liNumEvtbProcessId = 0;
                
                new UtilFaces().insertBitacoraServiceService(liIdRequestPrr,
                                                  Integer.parseInt(lsIdService), 
                                                  liIndProcess, 
                                                  "Cron ejecutado sin realizar acciones par el Servicio de " +
                    "Parrillas-Cortes",
                                                  liNumEvtbProcessId, 
                                                  liNumPgmProcessID, 
                                                  liParamIdUser,
                                                  lsParamUserName);*/
            }
        } catch (Exception loEx) {
            System.out.println("FATAL ERROR: Invocando servicio de neptuno......"+loEx.getMessage());
        }// finally {
         //   Configuration.releaseRootApplicationModule(loAm, true);
        //}
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
