package com.televisa.comer.integration.service.xmlutils;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
 
/**
 *
 * @author www.javadb.com
 */
public class HeaderHandlerResolver implements HandlerResolver {
    HeaderHandler headerHandler;
    
    public List<Handler> getHandlerChain(PortInfo portInfo) {
        List<Handler> handlerChain = new ArrayList<Handler>();
        headerHandler = new HeaderHandler();
        handlerChain.add(headerHandler);
        return handlerChain;
    }
    
    public String getRequest() {
        return headerHandler.getRequest();
    }
}