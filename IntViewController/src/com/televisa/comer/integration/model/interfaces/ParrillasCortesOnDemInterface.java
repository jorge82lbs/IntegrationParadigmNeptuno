/**
* Project: Integraton Paradigm - EveTV
*
* File: ParrillasCortesOnDemInterface.java
*
* Created on: Febrero 16, 2018 at 11:00
*
* Copyright (c) - OMW - 2018
*/
package com.televisa.comer.integration.model.interfaces;

/** Interface que define los metodos a utilizar para el modulo de Parrillas de Programas y Cortes
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Febrero 16, 2018, 12:00 pm
 */
import com.televisa.comer.integration.model.beans.ResponseUpdDao;
import com.televisa.comer.integration.model.beans.RsstParrillasCortesBean;

import java.sql.SQLException;

import java.util.List;

public interface ParrillasCortesOnDemInterface {
    public List<RsstParrillasCortesBean> getParrillasOnDemandParadigmDb(String psChannels,
                                                                        String tsDateStrt,
                                                                        String tsDateEnd,
                                                                        String tsBuyunit
                                                                        ) throws SQLException;    
    public List<RsstParrillasCortesBean> getParrillasCortesParadigmDbFlagZero(String psDate, String psChannels) throws SQLException;    
    public String getQueryOnDemandParadigm(String psChannels,
                                           String tsDateStrt,
                                           String tsDateEnd,
                                           String tsBuyunit
                                          );
    public String getQueryParadigmFlagZero(String psDate, String psChannels);
    public Integer getFlagParrillasProcesadas();
    public String getQueryFlagParadigm();
    public ResponseUpdDao insertParrillasProcesadas(String tsChannels);
    public ResponseUpdDao insertParrillasProcesadasFlagZero(String psDate, String psChannels);
    public String getQueryInsertParadigm(String tsChannels);
    public String getQueryInsertParadigmFlagZero(String psDate, String psChannels);
    public ResponseUpdDao updateParrillasProcesadas(String tsChannels);
    public String getQueryUpdateParadigm(String tsChannels);
    public ResponseUpdDao updateStstusParrillasProcesadas(String psStnid, String psBcstdt, String psPgmid, String tsBreakId,String psStatus);
    public String getQueryUpdateStstusParadigm(String psStnid, String psBcstdt, String psPgmid, String tsBreakId, String psStatus);
    public ResponseUpdDao updateFlagsPgmtxrntei(); 
    public String getQueryUpdateFlagsPgmtxrntei();
    public ResponseUpdDao updateFlagsABrkdttel(); 
    public String getQueryUpdateFlagsABrkdttel();
    public RsstParrillasCortesBean getBreakInfo(String tsChannel, String tsBreakId);
    public ResponseUpdDao updateAllParrillasProcesadas();
    public Integer insertEveTvParrillas(String psIdRequest, 
                                        String psIdService, 
                                        String psEstatus,
                                        String psUserName,
                                        String psIdUser,
                                        RsstParrillasCortesBean poParrilla);
    public String getQueryInsertParrillas(String psIdRequest, 
                                        String psIdService, 
                                        String psEstatus,
                                        String psUserName,
                                        String psIdUser,
                                        RsstParrillasCortesBean poParrilla);
    public Integer updateEveTvParrillas(String psIdRequest, 
                                        String psIdService, 
                                        String psEstatus,
                                        String psUserName,
                                        String psIdUser,
                                        String psCorteId,
                                        String psbreakId);
    public String getQueryUpdateParrillas(String psIdRequest, 
                                        String psIdService, 
                                        String psEstatus,
                                        String psUserName,
                                        String psIdUser,
                                        String psCorteId,
                                        String psbreakId);
        
}
