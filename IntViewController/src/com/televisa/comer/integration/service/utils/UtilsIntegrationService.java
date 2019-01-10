/**
* Project: Integraton Paradigm - EveTV
*
* File: UtilsIntegrationService.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.service.utils;

import com.televisa.comer.integration.model.daos.EntityMappedDao;
import com.televisa.comer.integration.service.beans.types.ParrillaCorteMapChannels;
import com.televisa.comer.integration.service.beans.types.ParrillaCorteMapCorteBreaks;
import com.televisa.comer.integration.service.beans.types.ParrillaCorteMapCortes;
import com.televisa.comer.integration.service.beans.types.ParrillaCorteMapDates;
import com.televisa.comer.integration.service.beans.types.ParrillaCorteMapItems;
import com.televisa.comer.integration.service.beans.types.TraditionalMapCampaignHeader;

import es.com.evendor.WSNeptuno;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

/** Clase que contiene diversos metodos utiles para el desarrollo de los diferentes modulos
 * en la capa de servicio
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public class UtilsIntegrationService {
    /**
     * Quita elementos repetidos de una lista de elementos tipo ParrillaCorteMapChannels
     * @autor Jorge Luis Bautista Santiago
     * @param taInputList
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List<ParrillaCorteMapChannels> removesChannelsRepeated(List<ParrillaCorteMapChannels> taInputList) {
        List<ParrillaCorteMapChannels> laOutputList = new ArrayList<ParrillaCorteMapChannels>();
        Map                            laMap = new HashMap();
        for (int liI = 0; liI < taInputList.size(); liI++) {
            laMap.put(taInputList.get(liI).getLsChannel(),
                      taInputList.get(liI).getLsChannel());
        }
        Iterator                       loIterator = laMap.entrySet().iterator();
        while (loIterator.hasNext()) {
            ParrillaCorteMapChannels loItem = new ParrillaCorteMapChannels();
            Map.Entry                loEntry = (Map.Entry)loIterator.next();
            loItem.setLsChannel(String.valueOf(loEntry.getKey()));
            loItem.setLsChannel(String.valueOf(loEntry.getValue()));
            laOutputList.add(loItem);
        }
        return laOutputList;
    }
    
    /**
     * Quita elementos repetidos de una lista de elementos tipo ParrillaCorteMapDates
     * @autor Jorge Luis Bautista Santiago
     * @param taInputList
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List<ParrillaCorteMapDates> removesDatesRepeated(List<ParrillaCorteMapDates> taInputList) {
        List<ParrillaCorteMapDates> laOutputList = new ArrayList<ParrillaCorteMapDates>();
        Map                         laMap = new HashMap();
        for (int liI = 0; liI < taInputList.size(); liI++) {
            String lsChannel = 
                taInputList.get(liI).getLsChannel() == null ? null : 
                taInputList.get(liI).getLsChannel().trim();
            String lsHourStart =
                taInputList.get(liI).getLsHourStart() == null ? null :
                taInputList.get(liI).getLsHourStart().trim();
            String lsHourEnd = 
                taInputList.get(liI).getLsHourEnd() == null ? null :
                taInputList.get(liI).getLsHourEnd().trim();
            laMap.put(lsChannel+taInputList.get(liI).getLsDateValue()+
                      lsHourStart+lsHourEnd,
                      taInputList.get(liI));
        }
        Iterator                    loIterator = laMap.entrySet().iterator();
        while (loIterator.hasNext()) {            
            Map.Entry             loEntry = (Map.Entry)loIterator.next();
            ParrillaCorteMapDates loItem = (ParrillaCorteMapDates)loEntry.getValue();
            laOutputList.add(loItem);
        }
        return laOutputList;
    }
    
    /**
     * Quita elementos repetidos de una lista de elementos tipo ParrillaCorteMapDates
     * @autor Jorge Luis Bautista Santiago
     * @param taInputList
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List<ParrillaCorteMapItems> removesDateItemsRepeated(List<ParrillaCorteMapItems> taInputList) {
        List<ParrillaCorteMapItems> laOutputList = new ArrayList<ParrillaCorteMapItems>();
        Map                         laMap = new HashMap();
        for (int liI = 0; liI < taInputList.size(); liI++) {
            String lsKey = taInputList.get(liI).getLsChannel()+
                           taInputList.get(liI).getLsDateValue()+
                           taInputList.get(liI).getLsHourStart()+
                           taInputList.get(liI).getLsHourEnd()+
                           taInputList.get(liI).getLsHour()+
                           taInputList.get(liI).getLsTitle()+
                           taInputList.get(liI).getLsEpisodename()+
                           taInputList.get(liI).getLsEpno()+
                           taInputList.get(liI).getLsSlotduration()+
                           taInputList.get(liI).getLsTitleid()+
                           taInputList.get(liI).getLsEpisodenameid()+    
                           taInputList.get(liI).getLsBuyunitid()+
                           taInputList.get(liI).getLsBuyunit()+
                           taInputList.get(liI).getLsExceptcofepris()+
            taInputList.get(liI).getLsEventspecial()+
                           taInputList.get(liI).getLsEventbest()+
                taInputList.get(liI).getLsGeneroID()+
                taInputList.get(liI).getLsGeneroName();
            
            laMap.put(lsKey,taInputList.get(liI));
        }
        Iterator                    loIterator = laMap.entrySet().iterator();
        while (loIterator.hasNext()) {            
            Map.Entry             loEntry = (Map.Entry)loIterator.next();
            ParrillaCorteMapItems loItem = (ParrillaCorteMapItems)loEntry.getValue();
            laOutputList.add(loItem);
        }
        return laOutputList;
    }
    
    /**
     * Quita elementos repetidos de una lista de elementos tipo ParrillaCorteMapCortes
     * @autor Jorge Luis Bautista Santiago
     * @param taInputList
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List<ParrillaCorteMapCortes> removesCortesRepeated(List<ParrillaCorteMapCortes> taInputList) {
        List<ParrillaCorteMapCortes> laOutputList = new ArrayList<ParrillaCorteMapCortes>();
        Map                          laMap = new HashMap();
        
        for (int liI = 0; liI < taInputList.size(); liI++) {
            String lsKey = taInputList.get(liI).getLsChannel()+
                           taInputList.get(liI).getLsDateValue()+
                           taInputList.get(liI).getLsHourStart()+
                           taInputList.get(liI).getLsHourEnd()+            
                           taInputList.get(liI).getLsBuyuntidCorte()+
                           taInputList.get(liI).getLsDateCorte()+
                           taInputList.get(liI).getLsHourCorte()+
                           taInputList.get(liI).getLsCorteid();
            
            laMap.put(lsKey,taInputList.get(liI));
        }
        Iterator                     loIterator = laMap.entrySet().iterator();
        while (loIterator.hasNext()) {                        
            Map.Entry              loEntry = (Map.Entry)loIterator.next();
            ParrillaCorteMapCortes loItem = (ParrillaCorteMapCortes)loEntry.getValue();
            laOutputList.add(loItem);
        }
        return laOutputList;
    }
    
    /**
     * Quita elementos repetidos de una lista de elementos tipo ParrillaCorteMapCorteBreaks
     * @autor Jorge Luis Bautista Santiago
     * @param taInputList
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List<ParrillaCorteMapCorteBreaks> removesCorteBreaksRepeated(List<ParrillaCorteMapCorteBreaks> 
                                                                               taInputList
                                                                               ) {
        List<ParrillaCorteMapCorteBreaks> laOutputList = new ArrayList<ParrillaCorteMapCorteBreaks>();
        Map                               laMap = new HashMap();       
        for (int liI = 0; liI < taInputList.size(); liI++) {
            String lsKey = taInputList.get(liI).getLsChannel()+
                           taInputList.get(liI).getLsDateValue()+
                           taInputList.get(liI).getLsHourStart()+
                           taInputList.get(liI).getLsHourEnd()+            
                           taInputList.get(liI).getLsBuyuntidCorte()+
                           taInputList.get(liI).getLsDateCorte()+
                           taInputList.get(liI).getLsHourCorte()+
                           taInputList.get(liI).getLsCorteid()+
                           taInputList.get(liI).getLsBreakid()+
                           taInputList.get(liI).getLsDescription()+
                           taInputList.get(liI).getLsDateBreak()+
                           taInputList.get(liI).getLsHourBreak()+ 
                           taInputList.get(liI).getLsTotalduration()+
                taInputList.get(liI).getLsComercialduration()+
                taInputList.get(liI).getLsTypeBreak();
            laMap.put(lsKey,taInputList.get(liI));
        }
        Iterator                          loIterator = laMap.entrySet().iterator();
        while (loIterator.hasNext()) {                        
            Map.Entry                   loEntry = (Map.Entry)loIterator.next();
            ParrillaCorteMapCorteBreaks loItem = (ParrillaCorteMapCorteBreaks)loEntry.getValue();
            laOutputList.add(loItem);
        }
        return laOutputList;
    }
    
    /**
     * Obtiene de forma dinamica la cadena wsdl de neptuno
     * desde la base de datos
     * @autor Jorge Luis Bautista Santiago
     * @return String
     */
    public String getWsdlNeptunoModel(){
        String          lsRes = "";
        EntityMappedDao loEmd = new EntityMappedDao();
        String          lsName = "WsNeptuno";
        String          lsUsedBy = "INTEGRATION_SERVICES";
        lsRes = loEmd.getGeneralParameter(lsName, lsUsedBy);
        return lsRes;
    }
    
    /**
     * Obtiene Objeto WsNemptuno de forma dinamica
     * @autor Jorge Luis Bautista Santiago
     * @return WSNeptuno
     */
    public WSNeptuno getServiceNeptunoDynamic(){
        WSNeptuno  loWSNeptuno = new WSNeptuno();
        String     lsDynaUrl = "http://corpsfeapld123:8080/WSNeptuno.asmx?WSDL";        
        lsDynaUrl = getWsdlNeptunoModel();
        URL        loWsdlDocLoc;
        try {
            loWsdlDocLoc = new URL(lsDynaUrl);
            QName loQname = loWSNeptuno.getServiceName();        
            loWSNeptuno.create(loWsdlDocLoc, loQname);
        } catch (MalformedURLException loEx) {
            System.out.println("Err al obtener servicio de Neptuno");
        }
        return loWSNeptuno;
    }
    
    /**
     * Quita elementos repetidos de una lista de elementos tipo TraditionalHeader
     * @autor Jorge Luis Bautista Santiago
     * @param taInputList
     * @return List
     */
    @SuppressWarnings("unchecked")
    public static List<TraditionalMapCampaignHeader> 
    removeTraditionalHeaderRepeated(List<TraditionalMapCampaignHeader> taInputList) {
        List<TraditionalMapCampaignHeader> laOutputList = new ArrayList<TraditionalMapCampaignHeader>();
        Map                                laMap = new HashMap();
        for (int liI = 0; liI < taInputList.size(); liI++) {
            String lsKey = taInputList.get(liI).getLsActionC()+
                           taInputList.get(liI).getLsOrderID()+
                           taInputList.get(liI).getLsAgencyID()+
                           taInputList.get(liI).getLsAdvertirserID()+            
                           taInputList.get(liI).getLsInitialDate()+
                           taInputList.get(liI).getLsEndDate()+
                           taInputList.get(liI).getLsMasterContract()+
                           taInputList.get(liI).getLsRateCard()+
                           taInputList.get(liI).getLsTargetID();
            laMap.put(lsKey,taInputList.get(liI));
        }
        Iterator                           loIterator = laMap.entrySet().iterator();
        while (loIterator.hasNext()) {                        
            Map.Entry                    loEntry = (Map.Entry)loIterator.next();
            TraditionalMapCampaignHeader loItem = (TraditionalMapCampaignHeader)loEntry.getValue();
            laOutputList.add(loItem);
        }
        return laOutputList;
    }
}
