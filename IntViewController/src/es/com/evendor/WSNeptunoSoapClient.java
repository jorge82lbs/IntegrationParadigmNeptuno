package es.com.evendor;

import com.televisa.comer.integration.service.xmlutils.HeaderHandlerResolver;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
// This source file is generated by Oracle tools.
// Contents may be subject to change.
// For reporting problems, use the following:
// Generated by Oracle JDeveloper 12c 12.1.3.0.0.1008
public class WSNeptunoSoapClient {
    public static void main(String[] args) {
        WSNeptuno wSNeptuno = new WSNeptuno();
        HeaderHandlerResolver handlerResolver = new HeaderHandlerResolver();
        wSNeptuno.setHandlerResolver(handlerResolver);
        WSNeptunoSoap wSNeptunoSoap = wSNeptuno.getWSNeptunoSoap();
        RecibirDatosExternos.Xmlentrada xmlentrada = new RecibirDatosExternos.Xmlentrada();
        boolean esOffline = false;                           
        String poExternoIDPrograma = "LAJUGADA";
        String poDescripcionPrograma = "D LA JUGADA";
        String poIndActivo = "S";
        String psIndTarifaPrograma = "N";
        
        
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // elemento raiz
            Document poDocument = docBuilder.newDocument();           
            
            Element loRoot = poDocument.createElement("RAIZ");
            Element loProgram = null;
            loProgram = poDocument.createElement("programa");
            Element loExternoIDPrograma = poDocument.createElement("externoIDPrograma");
            loExternoIDPrograma.appendChild(poDocument.createTextNode(poExternoIDPrograma));
            Element loDescripcionPrograma = poDocument.createElement("descripcionPrograma");
            loDescripcionPrograma.appendChild(poDocument.createTextNode(poDescripcionPrograma));
            Element loIndActivo = poDocument.createElement("indActivo");
            loIndActivo.appendChild(poDocument.createTextNode(poIndActivo));
            Element loIndTarifaPrograma = poDocument.createElement("indTarifaPrograma");
            loIndTarifaPrograma.appendChild(poDocument.createTextNode(psIndTarifaPrograma));
            
            loProgram.appendChild(loExternoIDPrograma);
            loProgram.appendChild(loDescripcionPrograma);
            loProgram.appendChild(loIndActivo);
            loProgram.appendChild(loIndTarifaPrograma);
            loRoot.appendChild(loProgram);
            
            xmlentrada.getContent().add(loRoot);            
            
            RecibirDatosExternosResponse.RecibirDatosExternosResult rder = 
                wSNeptunoSoap.recibirDatosExternos(xmlentrada, esOffline);
            /*System.out.println("");
            System.out.println("**************************  REQ  ***************************");
            System.out.println(handlerResolver.getRequest());
            System.out.println("*******************************************************************");
*/
            StringWriter sw = new StringWriter();
            JAXB.marshal(rder, sw);
            /*
            System.out.println("**************************  RESPONSE  ***************************");
            System.out.println(sw.toString());           
            System.out.println("*******************************************************************");
            */
        } catch (ParserConfigurationException e) {
            System.out.println("Error al parsear"+e.getMessage());
        }
    }
}
