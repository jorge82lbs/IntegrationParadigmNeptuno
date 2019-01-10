 /**
 * Project: Integraton Paradigm - EveTV
 *
 * File: ParrillasCortesOnDemInterface.java
 *
 * Created on: Febrero 16, 2018 at 11:00
 *
 * Copyright (c) - OMW - 2019
 */
 package com.televisa.comer.integration.service.interfaces;

 import com.televisa.comer.integration.service.beans.parrillascortes.ParrillasCortesInputParameters;
 import com.televisa.comer.integration.service.beans.parrillascortes.ParrillasCortesResponse;

 /** Interface que define los metodos a utilizar para el modulo de Parrillas de Programas y Cortes
  * en la capa de servicio
  *
  * @author Jorge Luis Bautista Santiago - OMW
  *
  * @version 01.00.01
  *
  * @date Febrero 16, 2018, 12:00 pm
  */
 public interface ParrillasCortesOnDemInterface {
     public ParrillasCortesResponse invokeParrillasProgramasyCortes(ParrillasCortesInputParameters psParrillasCortes,
     String tsDateStrt,
     String tsDateEnd,
     String tsBuyuni,
                                                                    String tsCodeTrace);   
 }
