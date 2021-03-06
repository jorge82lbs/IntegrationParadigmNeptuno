
package es.com.evendor;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10-b140319.1121
 * Generated source version: 2.2
 *
 */
@WebService(name = "WSNeptunoSoap", targetNamespace = "http://evendor.com.es/")
@XmlSeeAlso({ ObjectFactory.class })
public interface WSNeptunoSoap {


    /**
     * El método recibe un XML y un booleano esOnline (1 - offline / 0 - online) y devuelve un XML
     *
     * @param xmlentrada
     * @param esOffline
     * @return
     *     returns es.com.evendor.RecibirDatosExternosResponse.RecibirDatosExternosResult
     */
    @WebMethod(operationName = "RecibirDatosExternos", action = "http://evendor.com.es/RecibirDatosExternos")
    @WebResult(name = "RecibirDatosExternosResult", targetNamespace = "http://evendor.com.es/")
    @RequestWrapper(localName = "RecibirDatosExternos", targetNamespace = "http://evendor.com.es/",
                    className = "es.com.evendor.RecibirDatosExternos")
    @ResponseWrapper(localName = "RecibirDatosExternosResponse", targetNamespace = "http://evendor.com.es/",
                     className = "es.com.evendor.RecibirDatosExternosResponse")
    public es.com.evendor.RecibirDatosExternosResponse.RecibirDatosExternosResult recibirDatosExternos(@WebParam(name =
                                                                                                                 "xmlentrada",
                                                                                                                 targetNamespace =
                                                                                                                 "http://evendor.com.es/")
                                                                                                       es.com.evendor.RecibirDatosExternos.Xmlentrada xmlentrada,
                                                                                                       @WebParam(name =
                                                                                                                 "esOffline",
                                                                                                                 targetNamespace =
                                                                                                                 "http://evendor.com.es/")
                                                                                                       boolean esOffline);

    /**
     * El método recibe un XML con el IdProceso y devuelve un XML con el estado del proceso
     *
     * @param xmlentrada
     * @return
     *     returns es.com.evendor.ConsultarEstadoProcesoResponse.ConsultarEstadoProcesoResult
     */
    @WebMethod(operationName = "ConsultarEstadoProceso", action = "http://evendor.com.es/ConsultarEstadoProceso")
    @WebResult(name = "ConsultarEstadoProcesoResult", targetNamespace = "http://evendor.com.es/")
    @RequestWrapper(localName = "ConsultarEstadoProceso", targetNamespace = "http://evendor.com.es/",
                    className = "es.com.evendor.ConsultarEstadoProceso")
    @ResponseWrapper(localName = "ConsultarEstadoProcesoResponse", targetNamespace = "http://evendor.com.es/",
                     className = "es.com.evendor.ConsultarEstadoProcesoResponse")
    public es.com.evendor.ConsultarEstadoProcesoResponse.ConsultarEstadoProcesoResult consultarEstadoProceso(@WebParam(name =
                                                                                                                       "xmlentrada",
                                                                                                                       targetNamespace =
                                                                                                                       "http://evendor.com.es/")
                                                                                                             es.com.evendor.ConsultarEstadoProceso.Xmlentrada xmlentrada);

}
