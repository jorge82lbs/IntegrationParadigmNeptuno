package com.televisa.comer.integration.service.xmlutils;

import com.televisa.integration.view.front.util.UtilFaces;

import java.io.StringWriter;

import java.util.Set;

import javax.xml.bind.JAXB;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Node;

/**
 *
 * @author www.javadb.com
 */
public class HeaderHandler implements SOAPHandler<SOAPMessageContext> {
    
    private String request = "";

    public void setRequest(String request) {
        this.request = request;
    }

    public boolean handleMessage(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outboundProperty.booleanValue()) {
            SOAPMessage message = smc.getMessage();
    
            try { 
                SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
                envelope.addNamespaceDeclaration("soap", "http://www.w3.org/2003/05/soap-envelope");
                envelope.addNamespaceDeclaration("even", "http://evendor.com.es/");
                SOAPHeader header = envelope.getHeader();
                header.setPrefix("soap");
    
                SOAPElement authHeaders =
                        header.addChildElement("AuthHeader", "even");
                System.out.println("");
                SOAPElement username = authHeaders.addChildElement("Username", "even");
                String lsDynaUserName = new UtilFaces().getConfigParameterByName("UsrNeptuno");
                //System.out.print("sett UserName Neptuno["+lsDynaUserName+"]");
                username.addTextNode(lsDynaUserName);
    
                SOAPElement password = authHeaders.addChildElement("Password", "even");
                String lsDynaPassword = new UtilFaces().getConfigParameterByName("PswNeptuno");
                //System.out.print(" - Password Neptuno["+lsDynaPassword+"]");
                password.addTextNode(lsDynaPassword);
                
                SOAPElement usuarioLogadoID = authHeaders.addChildElement("UsuarioLogadoID", "even");
                String lsDynaLogan = new UtilFaces().getConfigParameterByName("UsrLogIdNeptuno");
                //System.out.print(" - Logado Neptuno["+lsDynaLogan+"]");
                usuarioLogadoID.addTextNode(lsDynaLogan);
                request = formatXML(message.getSOAPPart().getDocumentElement());
                System.out.println("");
                //System.out.println(request);
                setRequest(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
    
        } else {
            try {
                //This handler does nothing with the response from the Web Service so
                //we just print out the SOAP message.
                SOAPMessage message = smc.getMessage();
                message.writeTo(System.out);
    
            } catch (Exception ex) {
                ex.printStackTrace();
            } 
        }
        return outboundProperty;     
    }
 
    public Set getHeaders() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }
 
    public boolean handleFault(SOAPMessageContext context) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return true;
    }
 
    public void close(MessageContext context) {
    //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String getRequest() {
        return request;
    }
    
    public String formatXML(Node node) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        //initialize StreamResult with File object to save to file
        StreamResult result = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(node);
        transformer.transform(source, result);
        String xmlString = result.getWriter().toString();
        return xmlString;
    }
}