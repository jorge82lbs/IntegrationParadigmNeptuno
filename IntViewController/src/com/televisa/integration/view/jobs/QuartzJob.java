package com.televisa.integration.view.jobs;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob implements Job{
    public QuartzJob() {
        super();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String name = dataMap.getString("name");
        System.out.println("parametro NOMBRE["+name+"]");
        System.out.println("Ejecutando TAREA 1 ...... "+new Date());
    }
}
