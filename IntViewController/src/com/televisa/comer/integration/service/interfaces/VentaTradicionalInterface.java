package com.televisa.comer.integration.service.interfaces;

import com.televisa.comer.integration.service.beans.traditionalsale.TraditionalInputParameters;
import com.televisa.comer.integration.service.beans.traditionalsale.TraditionalResponse;

public interface VentaTradicionalInterface {
    public TraditionalResponse invokeTraditionalSale(TraditionalInputParameters psPrograms, 
                                                    String tsDateStrt,
                                                    String tsDateEnd,
                                                    String tsCodeTrace,
                                                    Integer tiReqQu);    
}
