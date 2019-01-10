/**
* Project: Paradigm - eVeTV Integration
*
* File: ExecuteLogCertificadoCron.java
*
* Created on:  Septiembre 14, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package com.televisa.integration.view.jobs;

import com.televisa.comer.integration.model.beans.pgm.EvetvIntServiceBitacoraTab;
import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.service.beans.logcertificado.ChannelLcert;
import com.televisa.comer.integration.service.beans.logcertificado.ChannelsLcertCollection;
import com.televisa.comer.integration.service.beans.logcertificado.LogCertificadoInputParameters;
import com.televisa.comer.integration.service.beans.logcertificado.LogCertificadoResponse;
import com.televisa.comer.integration.service.beans.logcertificado.XmlMessageLcertReqRes;
import com.televisa.comer.integration.service.beans.logcertificado.XmlMessageLcertResponseCollection;
import com.televisa.comer.integration.service.implementation.IntegrationDasLogCertificado;
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

/** Esta clase ejecuta en segundo plano el proceso de Log Certificado <br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Septiembre 14, 2017, 12:00 pm
 */
public class ExecuteLogCertificadoCron implements Job{
    
    String              gsAppModule = "AppModuleIntergrationDataControl";
    String              gsAmDef = "com.televisa.integration.model.AppModuleIntergrationImpl";
    String              gsConfig = "AppModuleIntergrationLocal";
    
    /**
     * Ejecuta la invocacion al servicio de Log Certificado
     * @autor Jorge Luis Bautista Santiago  
     * @param loJobExecutionContext
     * @return void
     */
    @Override
    public void execute(JobExecutionContext toJobExecutionContext) throws JobExecutionException {
        System.out.println("CRON- Iniciando metodo Execute para LOG CERTIFICADO en Fecha y hora ["+new Date()+"]");
        JobDataMap                loDataMap = toJobExecutionContext.getJobDetail().getJobDataMap();
        String                    lsIdService = loDataMap.getString("lsIdService");  
        String                    lsParamUserName = loDataMap.getString("lsUserName");
        String                    lsParamIdUser = loDataMap.getString("liIdUser");
        Integer                   liParamIdUser = Integer.parseInt(lsParamIdUser);
        String                    lsTypeRequest = loDataMap.getString("lsTypeRequest");
        //String                    lsIdRequestlCert = loDataMap.getString("lsIdRequestPgr");
        Integer                   liIdParameter = new ViewObjectDao().getMaxIdParadigm("RstLogCertificado") + 1;
        String                    lsIdRequestlCert = String.valueOf(liIdParameter);
        //Integer                   liIdRequestlCert = Integer.parseInt(lsIdRequestlCert);
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
                if(lsDateQuery != null && laChannels.size() > 0){                    
                    LogCertificadoInputParameters loInputLogCert = new LogCertificadoInputParameters();
                    loInputLogCert.setIdRequestLogCertificadoReq(lsIdRequestlCert);
                    loInputLogCert.setDateQueryLcert(lsDateQuery);
                    loInputLogCert.setIdService(lsIdService);
                    loInputLogCert.setUserName(lsParamUserName);
                    loInputLogCert.setIdUser(lsParamIdUser);
                    ChannelsLcertCollection laChColl = new ChannelsLcertCollection();
                    for(String lsCanal:laChannels){
                        ChannelLcert loChannel = new ChannelLcert();
                        loChannel.setChannelLcert(lsCanal);
                        laChColl.getChannelsLcert().add(loChannel);
                    }
                    loInputLogCert.setChannelLcertList(laChColl);
                    IntegrationDasLogCertificado  loLcert =  new IntegrationDasLogCertificado();
                    LogCertificadoResponse        loPgrRes = loLcert.invokeLogCertificado(loInputLogCert);
                    Integer                       liIndProcess = 
                        new UtilFaces().getIdConfigParameterByName("ProcessFinish");
                    
                    EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
                    toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
                    toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsIndEvento("Invocacion Finalizada para Servicio de Log Certificado");
                    loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                                       liParamIdUser, 
                                                       lsParamUserName);
                    
                    /*
                    Integer                       liNumPgmProcessID = liIdRequestlCert;
                    Integer                       liNumEvtbProcessId = 0;
                    new UtilFaces().insertBitacoraServiceService(liIdRequestlCert,
                                                      Integer.parseInt(lsIdService), 
                                                      liIndProcess, 
                                                      "Invocacion Finalizada para Servicio de Log Certificado",
                                                      liNumEvtbProcessId, 
                                                      liNumPgmProcessID, 
                                                      liParamIdUser,
                                                      lsParamUserName);     */   
                    XmlMessageLcertResponseCollection locmlrc = loPgrRes.getXmlMessageResponseLcert();
                    List<XmlMessageLcertReqRes>       laXmlRes = locmlrc.getXmlMessageLcertReqRes();
                    for(XmlMessageLcertReqRes loXml : laXmlRes){                    
                        System.out.println(loXml.getXmlMessageLcertResponse());
                    }
                }else{
                    Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("ErrParameters");
                    EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
                    toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
                    toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
                    toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
                    toEvetvIntServiceBitacoraTabR.setLsIndEvento("Error de Parametros en Invocacion del Servicio de Log Certificado");
                    loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                                       liParamIdUser, 
                                                       lsParamUserName);
                    /*
                    Integer liNumPgmProcessID = 0;
                    Integer liNumEvtbProcessId = 0;
                    new UtilFaces().insertBitacoraServiceService(liIdRequestlCert,
                                                      Integer.parseInt(lsIdService), 
                                                      liIndProcess, 
                                                      "Error de Parametros en Invocacion del Servicio de Log Certificado",
                                                      liNumEvtbProcessId, 
                                                      liNumPgmProcessID, 
                                                      liParamIdUser,
                                                      lsParamUserName);   */    
                }        
            }else{//El cron se ejecuta sin relizar acciones
                Integer liIndProcess = new UtilFaces().getIdConfigParameterByName("ErrParameters");
                EvetvIntServiceBitacoraTab toEvetvIntServiceBitacoraTabR = new EvetvIntServiceBitacoraTab();
                toEvetvIntServiceBitacoraTabR.setLsIdLogServices(lsIdService);
                toEvetvIntServiceBitacoraTabR.setLsIdService(lsIdService);
                toEvetvIntServiceBitacoraTabR.setLsIndProcess(String.valueOf(liIndProcess)); //Tipo de Proceso
                toEvetvIntServiceBitacoraTabR.setLsNumEvtbProcessId("0");
                toEvetvIntServiceBitacoraTabR.setLsNumPgmProcessId("0");
                toEvetvIntServiceBitacoraTabR.setLsIndEvento("Cron ejecutado sin realizar acciones par el Servicio de Log Certificado");
                loEntityMappedDao.insertBitacoraWs(toEvetvIntServiceBitacoraTabR,
                                                   liParamIdUser, 
                                                   lsParamUserName);
                
                /*
                Integer liNumPgmProcessID = 0;
                Integer liNumEvtbProcessId = 0;
                
                new UtilFaces().insertBitacoraServiceService(liIdRequestlCert,
                                                  Integer.parseInt(lsIdService), 
                                                  liIndProcess, 
                                                  "Cron ejecutado sin realizar acciones par el Servicio de " +
                    "Log Certificado",
                                                  liNumEvtbProcessId, 
                                                  liNumPgmProcessID, 
                                                  liParamIdUser,
                                                  lsParamUserName);*/
            }
        } catch (Exception loEx) {
            System.out.println("FATAL ERROR: Invocando servicio de neptuno......"+loEx.getMessage());
        }// finally {
        //    Configuration.releaseRootApplicationModule(loAm, true);
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
