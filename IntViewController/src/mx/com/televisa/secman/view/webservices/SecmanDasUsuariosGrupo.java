
package mx.com.televisa.secman.view.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import com.tvsa.soin.xmlns.secman.servicios.usuariogrupos.UsuariosCollection;
import com.tvsa.soin.xmlns.secman.servicios.usuariogrupos.UsuariosGrupoInputParameters;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10-b140319.1121
 * Generated source version: 2.2
 *
 */
@WebService(name = "SecmanDasUsuariosGrupo", targetNamespace = "http://webServices.view.secman.televisa.com.mx/")
@XmlSeeAlso({
            com.tvsa.soin.xmlns.secman.servicios.usuariogrupos.ObjectFactory.class,
            mx.com.televisa.secman.view.webservices.ObjectFactory.class })
public interface SecmanDasUsuariosGrupo {


    /**
     *
     * @param arg0
     * @return
     *     returns com.tvsa.soin.xmlns.secman.servicios.usuariogrupos.UsuariosCollection
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "obtenerUsuariosGrupo",
                    targetNamespace = "http://webServices.view.secman.televisa.com.mx/",
                    className = "mx.com.televisa.secman.view.webservices.ObtenerUsuariosGrupo")
    @ResponseWrapper(localName = "obtenerUsuariosGrupoResponse",
                     targetNamespace = "http://webServices.view.secman.televisa.com.mx/",
                     className = "mx.com.televisa.secman.view.webservices.ObtenerUsuariosGrupoResponse")
    public UsuariosCollection obtenerUsuariosGrupo(@WebParam(name = "arg0", targetNamespace = "")
                                                   UsuariosGrupoInputParameters arg0);

}
