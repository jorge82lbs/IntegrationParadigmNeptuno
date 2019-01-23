/**
* Project: Integraton Paradigm - Landmark
*
* File: TargetsInterface.java
*
* Created on: Enero 09, 2019 at 11:00
*
* Copyright (c) - OMW - 2019
*/
package com.televisa.comer.integration.service.interfaces;
/** Interface que define los metodos a utilizar para el modulo de Targets
 * en la capa de servicio
 *
 * @author Jorge Luis Bautista Santiago - OMW
 *
 * @version 01.00.01
 *
 * @date Enero 09, 2019, 12:00 pm
 */
import com.televisa.comer.integration.service.beans.targets.TargetsInputParameters;
import com.televisa.comer.integration.service.beans.targets.TargetsResponse;

public interface TargetsInterface {
    public TargetsResponse invokeTargets(TargetsInputParameters toTargetsInputParameters);
}
