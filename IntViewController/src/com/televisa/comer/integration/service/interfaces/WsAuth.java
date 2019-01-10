package com.televisa.comer.integration.service.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface WsAuth {
    @WebMethod
   public String authTest();
}
