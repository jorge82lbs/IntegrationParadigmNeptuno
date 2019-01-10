/**
* Project: Integraton Paradigm - EveTV
*
* File: ProgramasInterface.java
*
* Created on: Septiembre 23, 2017 at 11:00
*
* Copyright (c) - OMW - 2017
*/
package com.televisa.comer.integration.service.interfaces;
/** Interface que define los metodos a utilizar para el modulo de Programas
 * en la capa de servicio
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Septiembre 23, 2017, 12:00 pm
 */
import com.televisa.comer.integration.service.beans.programs.ProgramsInputParameters;
import com.televisa.comer.integration.service.beans.programs.ProgramsResponse;

public interface ProgramasInterface {
    public ProgramsResponse invokePrograms(ProgramsInputParameters psPrograms);    
}
