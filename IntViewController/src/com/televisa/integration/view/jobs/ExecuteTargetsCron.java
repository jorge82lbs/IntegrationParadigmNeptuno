/**
* Project: Paradigm - eVeTV Integration
*
* File: ExecuteTargetsCron.java
*
* Created on:  Enero 23, 2019 at 11:00
*
* Copyright (c) - Omw - 2019
*/
package com.televisa.integration.view.jobs;

import com.televisa.comer.integration.model.beans.pgm.EvetvIntServiceBitacoraTab;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.service.beans.targets.TargetsInputParameters;
import com.televisa.comer.integration.service.beans.targets.TargetsResponse;

import com.televisa.comer.integration.service.implementation.IntegrationDasTargets;
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
/** Ejecuta en seguno plano el proceso de invocacion de Targets <br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Enero 23, 2019, 12:00 pm
 */
public class ExecuteTargetsCron implements Job {
    
    String              gsAppModule = "AppModuleIntergrationDataControl";
    String              gsAmDef = "com.televisa.integration.model.AppModuleIntergrationImpl";
    String              gsConfig = "AppModuleIntergrationLocal";
    
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
                }
                if(lsPeridicity.equalsIgnoreCase("HORAS")){
                    //Excepto Dia y hora
                    lbFlag = validateDay(lsCurrentDay, toCronConfigBean);           
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
    
    /**
     * Ejecuta la invocacion en segundo plano para el servicio de Targets
     * @autor Jorge Luis Bautista Santiago  
     * @param loJobExecutionContext
     * @return void
     */
    @Override
    public void execute(JobExecutionContext toJobExecutionContext) throws JobExecutionException {
        System.out.println("Ejecucion de Cron >> Targets ["+new Date()+"]");
        JobDataMap                loDataMap = toJobExecutionContext.getJobDetail().getJobDataMap();
        String                    lsIdService = loDataMap.getString("lsIdService");  
        String                    lsParamUserName = loDataMap.getString("lsUserName");
        String                    lsParamIdUser = loDataMap.getString("liIdUser");
        Integer                   liParamIdUser = Integer.parseInt(lsParamIdUser);
        String                    lsTypeRequest = loDataMap.getString("lsTypeRequest");
        //String                    lsIdRequestPgr = loDataMap.getString("lsIdRequestPgr");        
        Integer                   liIdParameter = new ViewObjectDao().getMaxIdParadigm("RstTargets") + 1;
        String                    lsIdRequestPgr = String.valueOf(liIdParameter);
        
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
                
                System.out.println("**********************************************************************");
                System.out.println("***** Ejecutando Targets("+lsIdService+") at ["+new Date()+"] ******");
                
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
                if(lsDateQuery != null && laChannels.size() > 0){                  
                    TargetsInputParameters loInputTargets = new TargetsInputParameters();
                    loInputTargets.setIdRequestTargetsReq(lsIdRequestPgr);
                    
                    /*loInputPrograms.setDateQuery(lsDateQuery);
                    loInputPrograms.setIdService(lsIdService);
                    loInputPrograms.setUserName(lsParamUserName);
                    loInputPrograms.setIdUser(lsParamIdUser);
                                   
                    ChannelsCollection laChColl = new ChannelsCollection();
                    for(String lsCanal:laChannels){
                        Channel loChannel = new Channel();
                        loChannel.setChannel(lsCanal);
                        laChColl.getChannels().add(loChannel);
                    }                
                    loInputPrograms.setChannelList(laChColl);
                    */
                    IntegrationDasTargets  loInt =  new IntegrationDasTargets();
                    
                    TargetsResponse         loTargetRes = loInt.invokeTargets(loInputTargets);
                    
                    Integer                  liIndProcess = 
                        new UtilFaces().getIdConfigParameterByName("ProcessFinish");
                    EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
                    toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
                    toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsIndEvento("Invocacion Finalizada para Servicio de Targets");
                    loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                                       liParamIdUser, 
                                                       lsParamUserName);
                    loTargetRes.getXmlMessageResponse();
                    System.out.println("Fin de invocacion de targets....");
                    
                   
                }else{
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("ErrParameters");                   
                    EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
                    toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
                    toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsIndEvento("Error de Parametros en Invocacion del Servicio de Targets");
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
                toEvetvIntServiceBitacoraTabR.setLsIndEvento("Cron ejecutado sin realizar acciones par el Servicio de Targets");
                loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                                   liParamIdUser, 
                                                   lsParamUserName);

            }
            
        } catch (Exception loEx) {
            System.out.println("FATAL ERROR: Invocando servicio de neptuno......"+loEx.getMessage());
        } 
    }
}
