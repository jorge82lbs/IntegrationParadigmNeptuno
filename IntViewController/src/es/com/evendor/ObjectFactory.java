
package es.com.evendor;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the es.com.evendor package.
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

    private final static QName _AuthHeader_QNAME = new QName("http://evendor.com.es/", "AuthHeader");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.com.evendor
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultarEstadoProceso }
     *
     */
    public ConsultarEstadoProceso createConsultarEstadoProceso() {
        return new ConsultarEstadoProceso();
    }

    /**
     * Create an instance of {@link ConsultarEstadoProcesoResponse }
     *
     */
    public ConsultarEstadoProcesoResponse createConsultarEstadoProcesoResponse() {
        return new ConsultarEstadoProcesoResponse();
    }

    /**
     * Create an instance of {@link RecibirDatosExternos }
     *
     */
    public RecibirDatosExternos createRecibirDatosExternos() {
        return new RecibirDatosExternos();
    }

    /**
     * Create an instance of {@link RecibirDatosExternosResponse }
     *
     */
    public RecibirDatosExternosResponse createRecibirDatosExternosResponse() {
        return new RecibirDatosExternosResponse();
    }

    /**
     * Create an instance of {@link ConsultarEstadoProceso.Xmlentrada }
     *
     */
    public ConsultarEstadoProceso.Xmlentrada createConsultarEstadoProcesoXmlentrada() {
        return new ConsultarEstadoProceso.Xmlentrada();
    }

    /**
     * Create an instance of {@link ConsultarEstadoProcesoResponse.ConsultarEstadoProcesoResult }
     *
     */
    public ConsultarEstadoProcesoResponse.ConsultarEstadoProcesoResult createConsultarEstadoProcesoResponseConsultarEstadoProcesoResult() {
        return new ConsultarEstadoProcesoResponse.ConsultarEstadoProcesoResult();
    }

    /**
     * Create an instance of {@link AuthHeader }
     *
     */
    public AuthHeader createAuthHeader() {
        return new AuthHeader();
    }

    /**
     * Create an instance of {@link RecibirDatosExternos.Xmlentrada }
     *
     */
    public RecibirDatosExternos.Xmlentrada createRecibirDatosExternosXmlentrada() {
        return new RecibirDatosExternos.Xmlentrada();
    }

    /**
     * Create an instance of {@link RecibirDatosExternosResponse.RecibirDatosExternosResult }
     *
     */
    public RecibirDatosExternosResponse.RecibirDatosExternosResult createRecibirDatosExternosResponseRecibirDatosExternosResult() {
        return new RecibirDatosExternosResponse.RecibirDatosExternosResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthHeader }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://evendor.com.es/", name = "AuthHeader")
    public JAXBElement<AuthHeader> createAuthHeader(AuthHeader value) {
        return new JAXBElement<AuthHeader>(_AuthHeader_QNAME, AuthHeader.class, null, value);
    }

}
