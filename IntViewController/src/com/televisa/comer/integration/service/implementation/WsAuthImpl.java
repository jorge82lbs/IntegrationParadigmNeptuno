package com.televisa.comer.integration.service.implementation;

import com.televisa.comer.integration.service.interfaces.WsAuth;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import java.util.Set;

import javax.annotation.Resource;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

public class WsAuthImpl implements WsAuth {
   @Resource 
   private WebServiceContext wsc;

    @WebMethod(exclude = false)
    public String authTest() {
        String lsResponse = null;
        MessageContext mc = wsc.getMessageContext();
        Map requestHeader = (Map)mc.get(MessageContext.HTTP_REQUEST_HEADERS);
        //System.out.println("requestHeader: "+requestHeader);
        List userList = (List) requestHeader.get("Username");
        //System.out.println("requestHeader: "+userList.size());
        List passwordList = (List) requestHeader.get("Password");
        //System.out.println("requestHeader: "+passwordList.size());
        if(userList != null && passwordList != null){
            String lsUserName = userList.get(0) == null ? null : userList.get(0).toString();
            String lsPassword = passwordList.get(0) == null ? null : passwordList.get(0).toString();
            //lsResponse = "Hola "+lsUserName+" Tu password["+lsPassword+"] es Correcto"; 
        }else{
            lsResponse = "Campos Obligatorios";
        }
        return lsResponse;
    }    
}
