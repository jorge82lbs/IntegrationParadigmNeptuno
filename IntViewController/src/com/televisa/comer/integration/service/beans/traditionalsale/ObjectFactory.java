
package com.televisa.comer.integration.service.beans.traditionalsale;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.televisa.comer.integration.service.beans.traditionalsale package.
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

    private final static QName _ParrillasInputParameters_QNAME =
        new QName("http://com/televisa/comer/integration/service/beans/traditionalsale", "ParrillasInputParameters");
    private final static QName _ParrillasResponse_QNAME =
        new QName("http://com/televisa/comer/integration/service/beans/traditionalsale", "ParrillasResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.televisa.comer.integration.service.beans.traditionalsale
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TraditionalInputParameters }
     *
     */
    public TraditionalInputParameters createTraditionalInputParameters() {
        return new TraditionalInputParameters();
    }

    /**
     * Create an instance of {@link TraditionalResponse }
     *
     */
    public TraditionalResponse createTraditionalResponse() {
        return new TraditionalResponse();
    }

    /**
     * Create an instance of {@link Channel }
     *
     */
    public Channel createChannel() {
        return new Channel();
    }

    /**
     * Create an instance of {@link ChannelsCollection }
     *
     */
    public ChannelsCollection createChannelsCollection() {
        return new ChannelsCollection();
    }

    /**
     * Create an instance of {@link XmlMessageReqRes }
     *
     */
    public XmlMessageReqRes createXmlMessageReqRes() {
        return new XmlMessageReqRes();
    }

    /**
     * Create an instance of {@link XmlMessageResponseCollection }
     *
     */
    public XmlMessageResponseCollection createXmlMessageResponseCollection() {
        return new XmlMessageResponseCollection();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TraditionalInputParameters }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://com/televisa/comer/integration/service/beans/traditionalsale",
                    name = "ParrillasInputParameters")
    public JAXBElement<TraditionalInputParameters> createParrillasInputParameters(TraditionalInputParameters value) {
        return new JAXBElement<TraditionalInputParameters>(_ParrillasInputParameters_QNAME,
                                                           TraditionalInputParameters.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TraditionalResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://com/televisa/comer/integration/service/beans/traditionalsale",
                    name = "ParrillasResponse")
    public JAXBElement<TraditionalResponse> createParrillasResponse(TraditionalResponse value) {
        return new JAXBElement<TraditionalResponse>(_ParrillasResponse_QNAME, TraditionalResponse.class, null, value);
    }

}
