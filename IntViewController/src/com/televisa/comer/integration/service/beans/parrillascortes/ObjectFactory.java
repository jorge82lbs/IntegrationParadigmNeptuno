
package com.televisa.comer.integration.service.beans.parrillascortes;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.televisa.comer.integration.service.beans.parrillascortes package.
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

    private final static QName _ParrillasCortesInputParameters_QNAME =
        new QName("http://com/televisa/comer/integration/service/beans/parrillascortes",
                  "ParrillasCortesInputParameters");
    private final static QName _ParrillasCortesResponse_QNAME =
        new QName("http://com/televisa/comer/integration/service/beans/parrillascortes", "ParrillasCortesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.televisa.comer.integration.service.beans.parrillascortes
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ParrillasCortesResponse }
     *
     */
    public ParrillasCortesResponse createParrillasCortesResponse() {
        return new ParrillasCortesResponse();
    }

    /**
     * Create an instance of {@link ParrillasCortesInputParameters }
     *
     */
    public ParrillasCortesInputParameters createParrillasCortesInputParameters() {
        return new ParrillasCortesInputParameters();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ParrillasCortesInputParameters }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://com/televisa/comer/integration/service/beans/parrillascortes",
                    name = "ParrillasCortesInputParameters")
    public JAXBElement<ParrillasCortesInputParameters> createParrillasCortesInputParameters(ParrillasCortesInputParameters value) {
        return new JAXBElement<ParrillasCortesInputParameters>(_ParrillasCortesInputParameters_QNAME,
                                                               ParrillasCortesInputParameters.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParrillasCortesResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://com/televisa/comer/integration/service/beans/parrillascortes",
                    name = "ParrillasCortesResponse")
    public JAXBElement<ParrillasCortesResponse> createParrillasCortesResponse(ParrillasCortesResponse value) {
        return new JAXBElement<ParrillasCortesResponse>(_ParrillasCortesResponse_QNAME, ParrillasCortesResponse.class,
                                                        null, value);
    }

}
