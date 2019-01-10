/**
* Project: Paradigm - eVeTV Integration
*
* File: InitCronStartupServlet.java
*
* Created on:  Diciembre 6, 2018 at 11:00
*
* Copyright (c) - Omw - 2017
*/
package com.televisa.integration.view.listeners;

import com.televisa.comer.integration.model.beans.pgm.EvetvIntServiceBitacoraTab;
import com.televisa.comer.integration.model.daos.EntityMappedDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.quartz.ee.servlet.QuartzInitializerListener;


/** Esta clase inicializa las crones activos despues de un reinicio del servidor <br/><br/>
 *
 * @author Jorge Luis Bautista - Omw
 *
 * @version 01.00.01
 *
 * @date Diciembre 14, 2018, 12:00 pm
 */

@WebListener
public class InitCronStartupServlet extends QuartzInitializerListener{
    public InitCronStartupServlet() {
        super();
    }
    /**
     * Incializa el contxto del servlet
     * @autor Jorge Luis Bautista Santiago  
     * @param loExpression
     * @return Object
     */
    @Override
    public void contextInitialized(ServletContextEvent loSce) {
        super.contextInitialized(loSce);
        //System.out.println(">>> Actualizando estatus de crones a inactivos, solo los que tienen estatus Activo [2]");
        EntityMappedDao loEntityMappedDao = new EntityMappedDao();
        Integer liRes = loEntityMappedDao.disableInitializedCron();
        if(liRes > 0){
            //System.out.println(">>> Se han deshabilitado crones activos por reinicio - estatus Deshabilitado [4]");
            EvetvIntServiceBitacoraTab loEvetvIntServiceBitacoraTab = new EvetvIntServiceBitacoraTab();
            loEvetvIntServiceBitacoraTab.setLsIdLogServices("0");
            loEvetvIntServiceBitacoraTab.setLsIdService("0");
            loEvetvIntServiceBitacoraTab.setLsIndProcess("0");
            loEvetvIntServiceBitacoraTab.setLsNumEvtbProcessId("0");
            loEvetvIntServiceBitacoraTab.setLsNumPgmProcessId("0");
            loEvetvIntServiceBitacoraTab.setLsIndEvento("Se han deshabilitado crones activos por reinicio");
            loEntityMappedDao.insertBitacoraWs(loEvetvIntServiceBitacoraTab,0, "Server");
        }
        
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        super.contextDestroyed(sce);
    }        
    
    public Date getDateYyyyMmDd(Date ttDate){
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
