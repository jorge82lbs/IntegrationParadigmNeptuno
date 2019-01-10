/**
* Project: Paradigm - eVeTV Integration
*
* File: SchedulerStartupServlet.java
*
* Created on:  Septiembre 6, 2017 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package com.televisa.integration.view.listeners;

import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.integration.model.types.EvetvIntCronConfigTabRowBean;
import com.televisa.integration.view.jobs.ExecuteLogCertificadoCron;
import com.televisa.integration.view.jobs.ExecuteParrillasCron;
import com.televisa.integration.view.jobs.ExecuteProgramasCron;
import com.televisa.integration.view.jobs.ExecuteVtaTradicionalCron;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContextEvent;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

/** Esta clase inicializa las tareas den el despliegue de la aplicacion <br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Septiembre 14, 2017, 12:00 pm
 */

//@WebListener
public class SchedulerStartupServlet extends QuartzInitializerListener{
    //private String glGroup = "Integration";
    /**
     * Incializa el contxto del servlet
     * @autor Jorge Luis Bautista Santiago  
     * @param loExpression
     * @return Object
     */
    @Override
    public void contextInitialized(ServletContextEvent loSce) {
        super.contextInitialized(loSce);   
        
        System.out.println("********************************** ELIMINANDO DE MEMORIA *****************************************************");
        Scheduler schedulerDlt;
        try {
            schedulerDlt = new StdSchedulerFactory().getScheduler();            
            for (String groupName : schedulerDlt.getJobGroupNames()) {
             for (JobKey jobKey : schedulerDlt.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                schedulerDlt.deleteJob(jobKey);
              }    
            }
        } catch (SchedulerException e) {
            System.out.println("Error al ELIMINAR crones "+e.getMessage());
        }

        List<EvetvIntCronConfigTabRowBean> laServsCron = 
            new ArrayList<EvetvIntCronConfigTabRowBean>();
        EntityMappedDao loEntityMappedDao = new EntityMappedDao();
        laServsCron = loEntityMappedDao.getServicesCronExecution();
        System.out.println("Crones inicializados ["+laServsCron.size()+"]");
        //---------------------------------------------------------------------------------
        System.out.println("Eliminar todos los crons inicializados..............");
        for(EvetvIntCronConfigTabRowBean loSerCronBean: laServsCron){
            String lsNomService = 
                loEntityMappedDao.getTypeService(String.valueOf(loSerCronBean.getIdService()));
            String psIdTrigger = loSerCronBean.getIdService() + "-" + lsNomService;
            Scheduler loSchedulerDel;
            try {
                String    lsNewCronExpression = "0 0 1 1/1 * ? *"; 
                System.out.println("Eliminar cualquier cron con ID["+psIdTrigger+"]");
                loSchedulerDel = new StdSchedulerFactory().getScheduler();
                Trigger loTrigger = 
                    TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                       CronScheduleBuilder.cronSchedule(lsNewCronExpression)).startNow().build();
                loSchedulerDel.rescheduleJob(new TriggerKey(psIdTrigger), loTrigger);
                loSchedulerDel.deleteJob(loTrigger.getJobKey());                        
                System.out.println("Cron con ID["+psIdTrigger+"] Eliminado Satifactoriamente");
            } catch (SchedulerException loEx) {
                System.out.println("SchedulerException: " + loEx.getMessage());
            }
            System.out.println("Crones eliminados satisfactoriamente");
        }
        //Incicializar attribute12 que es campo que indica si esta en ejecucion
        //loEntityMappedDao.initFieldExeCron();
        //---------------------------------------------------------------------------------
        System.out.println("Iniciar Crones(nuevos) en el actual despliegue....");
        for(EvetvIntCronConfigTabRowBean loSerCronBean: laServsCron){
            //Verificar fechas, ACTUAL y LAST_MODIF
            boolean lbProcessCron = true; //Valida si se inicia cron
            Date ltCurrentDate = getDateYyyyMmDd(new Date());
            Date ltDateLastMod = getDateYyyyMmDd(new Date(loSerCronBean.getFecLastUpdateDate().getTime()));
            System.out.println("ltCurrentDate: ["+ltCurrentDate+"]");
            System.out.println("ltDateLastMod: ["+ltDateLastMod+"]");
            if(ltCurrentDate.compareTo(ltDateLastMod) != 0){
                System.out.println("Fechas iguales actualizar campo exe y actualizar fecha_LAST");
                loEntityMappedDao.updateExeFieldByService(loSerCronBean.getIdService());      
                lbProcessCron = true;
            }else{
                if(loSerCronBean.getAttribute12() == null){
                    loEntityMappedDao.updateExeFieldByService(loSerCronBean.getIdService());
                    lbProcessCron = true;
                }else{
                    if(loSerCronBean.getAttribute12().equalsIgnoreCase("exe")){//Ya esta en ejecucion
                        lbProcessCron = false;
                        System.out.println("No iniciar cron ["+loSerCronBean.getIdService()+"]");
                    }else{
                        loEntityMappedDao.updateExeFieldByService(loSerCronBean.getIdService());
                        lbProcessCron = true;
                    }
                }
                
            }
            
            //El nombre Key del servicio es idService y nomService
            System.out.println("IdService["+loSerCronBean.getIdService()+"]");
            
            if(lbProcessCron){
                String lsNomService = 
                    loEntityMappedDao.getTypeService(String.valueOf(loSerCronBean.getIdService()));
                String psIdTrigger = loSerCronBean.getIdService() + "-" + lsNomService;
                System.out.print(" psIdTrigger["+psIdTrigger+"]");
                String lsIndEstatusService = 
                    loEntityMappedDao.getIndEstatusService(String.valueOf(loSerCronBean.getIdService()));
            
                if(lsIndEstatusService.equalsIgnoreCase("1")){
                    if(lsNomService.equalsIgnoreCase("WsProgramas")){
                        Scheduler loScheduler;                    
                        System.out.println("Iniciando Programas con ID["+psIdTrigger+"]");
                        try {
                            String lsCronExpression = loSerCronBean.getIndCronExpression();
                            if(lsCronExpression != null){
                                boolean lbSimple = false;
                                Trigger loTrigger = null;
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteProgramasCron.class).build();
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loSerCronBean.getIndBeginSchedule() == null ? "08:00" : 
                                        loSerCronBean.getIndBeginSchedule();
                                    String lsFin = 
                                        loSerCronBean.getAttribute1() == null ? "23:50" : 
                                        loSerCronBean.getAttribute1();
                                    String lsEvery =  
                                        loSerCronBean.getIndValTypeSchedule() == null ? "23" : 
                                        loSerCronBean.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                        System.out.println("Hora Inicio: ["+ltFechaIni+" ]");
                                        System.out.println("Hora Fin: ["+ltFechaFin+" ]");
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    
                                    if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                }                        
                                String lsUserName = loSerCronBean.getAttribute14();
                                System.out.println("lsUserName["+lsUserName+"]");
                                Integer liIdUser = loSerCronBean.getNumLastUpdateBy();
                                System.out.println("liIdUser["+liIdUser+"]");
                                String lsIdService = String.valueOf(loSerCronBean.getIdService());
                                System.out.println("lsIdService["+lsIdService+"]");
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", lsIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(liIdUser)); 
                                loJobDataMap.put("lsUserName", lsUserName); 
                                loJobDataMap.put("lsIdRequestPgr", "0");
                                loJobDataMap.put("lsTypeRequest", "load");
                                loScheduler.scheduleJob(loJob, loTrigger);  
                                System.out.println("start");
                                loScheduler.start();
                            }
                        } catch (Exception loEx) {
                            System.out.println("Error al inicializar tareas " + loEx.getMessage());                    
                        } 
                    }
                    if(lsNomService.equalsIgnoreCase("WsLogCertificado")){
                        System.out.println("Iniciando Log Certificado con ID["+psIdTrigger+"]");
                        Scheduler loScheduler;
                        try {
                            boolean lbSimple = false;
                            Trigger loTrigger = null;
                            String lsCronExpression = loSerCronBean.getIndCronExpression();
                            if(lsCronExpression != null){
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteLogCertificadoCron.class).build();
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loSerCronBean.getIndBeginSchedule() == null ? "08:00" : 
                                        loSerCronBean.getIndBeginSchedule();
                                    String lsFin = 
                                        loSerCronBean.getAttribute1() == null ? "23:50" : 
                                        loSerCronBean.getAttribute1();
                                    String lsEvery =  
                                        loSerCronBean.getIndValTypeSchedule() == null ? "23" : 
                                        loSerCronBean.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                }
                                String lsUserName = loSerCronBean.getAttribute14();
                                Integer liIdUser = loSerCronBean.getNumLastUpdateBy();
                                String lsIdService = String.valueOf(loSerCronBean.getIdService());
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", lsIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(liIdUser)); 
                                loJobDataMap.put("lsUserName", lsUserName); 
                                loJobDataMap.put("lsIdRequestPgr", "0");    
                                loJobDataMap.put("lsTypeRequest", "load");
                                loScheduler.scheduleJob(loJob, loTrigger);                        
                                loScheduler.start();
                            }
                        } catch (Exception loEx) {
                            System.out.println("Error al inicializar tareas " + loEx.getMessage());                    
                        }
                    }
                    if(lsNomService.equalsIgnoreCase("WsVentaTradicional")){
                        System.out.println("Iniciando Venta Tradicional con ID["+psIdTrigger+"]");
                        Scheduler loScheduler;
                        try {
                            boolean lbSimple = false;
                            Trigger loTrigger = null;
                            String lsCronExpression = loSerCronBean.getIndCronExpression();
                            if(lsCronExpression != null){
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteVtaTradicionalCron.class).build();
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loSerCronBean.getIndBeginSchedule() == null ? "08:00" : 
                                        loSerCronBean.getIndBeginSchedule();
                                    String lsFin = 
                                        loSerCronBean.getAttribute1() == null ? "23:50" : 
                                        loSerCronBean.getAttribute1();
                                    String lsEvery =  
                                        loSerCronBean.getIndValTypeSchedule() == null ? "23" : 
                                        loSerCronBean.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    
                                    if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                }    
                                String lsUserName = loSerCronBean.getAttribute14();
                                Integer liIdUser = loSerCronBean.getNumLastUpdateBy();
                                String lsIdService = String.valueOf(loSerCronBean.getIdService());
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", lsIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(liIdUser)); 
                                loJobDataMap.put("lsUserName", lsUserName); 
                                loJobDataMap.put("lsIdRequest", "0"); 
                                loJobDataMap.put("lsStatusType", "PN");     
                                loJobDataMap.put("lsTypeRequest", "load");
                                loScheduler.scheduleJob(loJob, loTrigger);                        
                                loScheduler.start();
                            }
                        } catch (Exception loEx) {
                            System.out.println("Error al inicializar tareas " + loEx.getMessage());                    
                        }
                    }
                    if(lsNomService.equalsIgnoreCase("WsBreaks")){
                        System.out.println("Iniciando Parrillas Cortes con ID["+psIdTrigger+"]");
                        Scheduler loScheduler;
                        try {
                            boolean lbSimple = false;
                            Trigger loTrigger = null;
                            String lsCronExpression = loSerCronBean.getIndCronExpression();
                            if(lsCronExpression != null){
                                loScheduler = new StdSchedulerFactory().getScheduler();
                                JobDetail loJob = 
                                    JobBuilder.newJob(ExecuteParrillasCron.class).build();
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                    lbSimple = true;
                                }
                                if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("HORAS")){
                                    lbSimple = true;
                                }
                                if(!lbSimple){
                                    loTrigger = 
                                        TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                            CronScheduleBuilder.cronSchedule(lsCronExpression)
                                    ).startNow().build();
                                }else{
                                    Date ltCurrent = new Date();
                                    String lsCurrDate = "";
                                    SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                                    lsCurrDate = lodfCurrent.format(ltCurrent);
                                    Date ltFechaIni = new Date();
                                    Date ltFechaFin = new Date();
                                    String lsInicio = 
                                        loSerCronBean.getIndBeginSchedule() == null ? "08:00" : 
                                        loSerCronBean.getIndBeginSchedule();
                                    String lsFin = 
                                        loSerCronBean.getAttribute1() == null ? "23:50" : 
                                        loSerCronBean.getAttribute1();
                                    String lsEvery =  
                                        loSerCronBean.getIndValTypeSchedule() == null ? "23" : 
                                        loSerCronBean.getIndValTypeSchedule();
                                    Integer liEvery = Integer.parseInt(lsEvery);
                                    SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                                    String lsStartDate = lsCurrDate + " " + lsInicio + ":00.0";
                                    String lsFinalDate = lsCurrDate + " " + lsFin + ":00.0";
                                    try {
                                        ltFechaIni = lodf.parse(lsStartDate);
                                        ltFechaFin = lodf.parse(lsFinalDate);
                                    } catch (ParseException e) {
                                        System.out.println("Error al parsear: "+e.getMessage());
                                        e.printStackTrace();
                                    }
                                    if(loSerCronBean.getIndPeriodicity().equalsIgnoreCase("MINUTOS")){
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }else{
                                        loTrigger = 
                                            TriggerBuilder.newTrigger().withIdentity(psIdTrigger).withSchedule(
                                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(liEvery).repeatForever()
                                        ).startAt(ltFechaIni).endAt(ltFechaFin).build();
                                    }
                                }        
                                String lsUserName = loSerCronBean.getAttribute14();
                                Integer liIdUser = loSerCronBean.getNumLastUpdateBy();
                                String lsIdService = String.valueOf(loSerCronBean.getIdService());
                                JobDataMap loJobDataMap=  loJob.getJobDataMap();
                                loJobDataMap.put("lsIdService", lsIdService); 
                                loJobDataMap.put("liIdUser", String.valueOf(liIdUser)); 
                                loJobDataMap.put("lsUserName", lsUserName); 
                                loJobDataMap.put("lsIdRequestPgr", "0");       
                                loJobDataMap.put("lsTypeRequest", "load");
                                loScheduler.scheduleJob(loJob, loTrigger);                        
                                loScheduler.start();
                            }
                        } catch (Exception loEx) {
                            System.out.println("Error al inicializar tareas " + loEx.getMessage());                    
                        } 
                    }
                }
            }
        }
        
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        super.contextDestroyed(sce);
    }        
    
    public Date getDateYyyyMmDd(Date ttDate){
        //Date             ltCurrent = new Date();
        String           lsCurrDate = "";
        SimpleDateFormat lodfCurrent = new SimpleDateFormat("yyyy-MM-dd");
        lsCurrDate = lodfCurrent.format(ttDate);
        SimpleDateFormat lodf = new SimpleDateFormat("yyyy-MM-dd");
        Date             ltFechaReturn = new Date();
        try {
            ltFechaReturn = lodf.parse(lsCurrDate);
        } catch (ParseException e) {
            System.out.println("Error al parsear: "+e.getMessage());
            e.printStackTrace();
        }
        return ltFechaReturn;
    }
}
