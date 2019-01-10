/**
* Project: Integraton Paradigm - EveTV
*
* File: ProgramasInterfaces.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.model.interfaces;

import com.televisa.comer.integration.model.beans.RsstProgramasBean;
import java.util.List;
/** Interface que define los metodos a utilizar para el modulo de Programas
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
public interface ProgramasInterfaces {
    public Integer getCountEveTvProgramas();
    public List<RsstProgramasBean> getProgramsFromParadigmService(String psDate, String psChannels);
    public String getQueryFlagPrograms();
    public String getQueryPrograms(String psDate, String psChannels);
    public String getQueryPrograms();
    public Integer insertEveTvProgramas(String psIdRequest, 
                                        String psIdService, 
                                        String psBuyuntid, 
                                        String psBuylnam,
                                        String psActivo,
                                        String psTarifaProgramada,
                                        String psEstatus,
                                        String psUserName,
                                        String psIdUser
                                       );
    public String getQueryInsertPrograms(String psIdRequest, 
                                         String psIdService, 
                                         String psBuyuntid, 
                                         String psBuylnam,
                                         String psActivo,
                                         String psTarifaProgramada,
                                         String psEstatus,
                                         String psUserName,
                                         String psIdUser
                                         );
    
    public Integer updateEveTvProgramas(String psIdRequest, 
                                         String psIdService, 
                                         String psBuyuntid, 
                                         String psDescNeptuno,
                                         String psIdError,
                                         String psEstatus,
                                         String psUserName,
                                         String psIdUser
                                        );
    public String getQueryUpdatePrograms(String psIdRequest, 
                                         String psIdService, 
                                         String psBuyuntid,                                          
                                         String psDescNeptuno,
                                         String psIdError,
                                         String psEstatus,
                                         String psUserName,
                                         String psIdUser
                                         );
    public Integer updateProgramsParadigm(String psBuyuntid, String psStatus);
    public String getQueryUpdProgramsParadigm(String psBuyuntid, String psStatus);
}
