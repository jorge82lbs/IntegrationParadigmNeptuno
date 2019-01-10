
package mx.com.televisa.secman.view.webservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the mx.com.televisa.secman.view.webservices package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ObtenerRolTareaPermisosResponse_QNAME =
        new QName("http://webServices.view.secman.televisa.com.mx/", "obtenerRolTareaPermisosResponse");
    private final static QName _ObtenerRolTareaPermisos_QNAME =
        new QName("http://webServices.view.secman.televisa.com.mx/", "obtenerRolTareaPermisos");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.com.televisa.secman.view.webservices
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObtenerRolTareaPermisosResponse }
     *
     */
    public ObtenerRolTareaPermisosResponse createObtenerRolTareaPermisosResponse() {
        return new ObtenerRolTareaPermisosResponse();
    }

    /**
     * Create an instance of {@link ObtenerRolTareaPermisos }
     *
     */
    public ObtenerRolTareaPermisos createObtenerRolTareaPermisos() {
        return new ObtenerRolTareaPermisos();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerRolTareaPermisosResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.view.secman.televisa.com.mx/",
                    name = "obtenerRolTareaPermisosResponse")
    public JAXBElement<ObtenerRolTareaPermisosResponse> createObtenerRolTareaPermisosResponse(ObtenerRolTareaPermisosResponse value) {
        return new JAXBElement<ObtenerRolTareaPermisosResponse>(_ObtenerRolTareaPermisosResponse_QNAME,
                                                                ObtenerRolTareaPermisosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerRolTareaPermisos }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://webServices.view.secman.televisa.com.mx/", name = "obtenerRolTareaPermisos")
    public JAXBElement<ObtenerRolTareaPermisos> createObtenerRolTareaPermisos(ObtenerRolTareaPermisos value) {
        return new JAXBElement<ObtenerRolTareaPermisos>(_ObtenerRolTareaPermisos_QNAME, ObtenerRolTareaPermisos.class,
                                                        null, value);
    }

}
