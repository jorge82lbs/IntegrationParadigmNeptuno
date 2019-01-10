 /**
 * Project: Integraton Paradigm - EveTV
 *
 * File: ParrillasProgramasCortesInterface.java
 *
 * Created on: Septiembre 23, 2017 at 11:00
 *
 * Copyright (c) - OMW - 2017
 */
 package com.televisa.comer.integration.service.interfaces;

 import com.televisa.comer.integration.service.beans.parrillascortes.ParrillasCortesInputParameters;
 import com.televisa.comer.integration.service.beans.parrillascortes.ParrillasCortesResponse;
 /** Interface que define los metodos a utilizar para el módulo de Parrillas de Programas y Cortes
  * en la capa de servicio
  *
  * @author Jorge Luis Bautista Santiago - OMW
  *
  * @version 01.00.01
  *
  * @date Septiembre 23, 2017, 12:00 pm
  */
 public interface ParrillasProgramasCortesInterface {
     public ParrillasCortesResponse invokeParrillasProgramasyCortes(ParrillasCortesInputParameters psParrillasCortes);   
 }
