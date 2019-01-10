
package com.televisa.comer.integration.service.beans.programs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.televisa.comer.integration.service.beans.programs package.
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
        new QName("http://com/televisa/comer/integration/service/beans/programs", "ParrillasInputParameters");
    private final static QName _ParrillasResponse_QNAME =
        new QName("http://com/televisa/comer/integration/service/beans/programs", "ParrillasResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.televisa.comer.integration.service.beans.programs
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProgramsInputParameters }
     *
     */
    public ProgramsInputParameters createProgramsInputParameters() {
        return new ProgramsInputParameters();
    }

    /**
     * Create an instance of {@link ProgramsResponse }
     *
     */
    public ProgramsResponse createProgramsResponse() {
        return new ProgramsResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ProgramsInputParameters }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://com/televisa/comer/integration/service/beans/programs",
                    name = "ParrillasInputParameters")
    public JAXBElement<ProgramsInputParameters> createParrillasInputParameters(ProgramsInputParameters value) {
        return new JAXBElement<ProgramsInputParameters>(_ParrillasInputParameters_QNAME, ProgramsInputParameters.class,
                                                        null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProgramsResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://com/televisa/comer/integration/service/beans/programs",
                    name = "ParrillasResponse")
    public JAXBElement<ProgramsResponse> createParrillasResponse(ProgramsResponse value) {
        return new JAXBElement<ProgramsResponse>(_ParrillasResponse_QNAME, ProgramsResponse.class, null, value);
    }

}
