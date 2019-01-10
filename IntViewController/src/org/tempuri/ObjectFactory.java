
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the org.tempuri package.
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

    private final static QName _EnviarCorreoResponse_QNAME = new QName("http://tempuri.org/", "EnviarCorreoResponse");
    private final static QName _EnviarCorreo_QNAME = new QName("http://tempuri.org/", "EnviarCorreo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EnviarCorreo }
     *
     */
    public EnviarCorreo createEnviarCorreo() {
        return new EnviarCorreo();
    }

    /**
     * Create an instance of {@link EnviarCorreoResponse }
     *
     */
    public EnviarCorreoResponse createEnviarCorreoResponse() {
        return new EnviarCorreoResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarCorreoResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "EnviarCorreoResponse")
    public JAXBElement<EnviarCorreoResponse> createEnviarCorreoResponse(EnviarCorreoResponse value) {
        return new JAXBElement<EnviarCorreoResponse>(_EnviarCorreoResponse_QNAME, EnviarCorreoResponse.class, null,
                                                     value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnviarCorreo }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "EnviarCorreo")
    public JAXBElement<EnviarCorreo> createEnviarCorreo(EnviarCorreo value) {
        return new JAXBElement<EnviarCorreo>(_EnviarCorreo_QNAME, EnviarCorreo.class, null, value);
    }

}
