
package com.televisa.comer.integration.service.beans.logcertificado;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.televisa.comer.integration.service.beans.logcertificado package.
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

    private final static QName _LogCertificadoResponse_QNAME =
        new QName("http://com/televisa/comer/integration/service/beans/logcertificado", "LogCertificadoResponse");
    private final static QName _LogCertificadoInputParameters_QNAME =
        new QName("http://com/televisa/comer/integration/service/beans/logcertificado",
                  "LogCertificadoInputParameters");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.televisa.comer.integration.service.beans.logcertificado
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LogCertificadoInputParameters }
     *
     */
    public LogCertificadoInputParameters createLogCertificadoInputParameters() {
        return new LogCertificadoInputParameters();
    }

    /**
     * Create an instance of {@link LogCertificadoResponse }
     *
     */
    public LogCertificadoResponse createLogCertificadoResponse() {
        return new LogCertificadoResponse();
    }

    /**
     * Create an instance of {@link ChannelsLcertCollection }
     *
     */
    public ChannelsLcertCollection createChannelsLcertCollection() {
        return new ChannelsLcertCollection();
    }

    /**
     * Create an instance of {@link XmlMessageLcertReqRes }
     *
     */
    public XmlMessageLcertReqRes createXmlMessageLcertReqRes() {
        return new XmlMessageLcertReqRes();
    }

    /**
     * Create an instance of {@link XmlMessageLcertResponseCollection }
     *
     */
    public XmlMessageLcertResponseCollection createXmlMessageLcertResponseCollection() {
        return new XmlMessageLcertResponseCollection();
    }

    /**
     * Create an instance of {@link ChannelLcert }
     *
     */
    public ChannelLcert createChannelLcert() {
        return new ChannelLcert();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogCertificadoResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://com/televisa/comer/integration/service/beans/logcertificado",
                    name = "LogCertificadoResponse")
    public JAXBElement<LogCertificadoResponse> createLogCertificadoResponse(LogCertificadoResponse value) {
        return new JAXBElement<LogCertificadoResponse>(_LogCertificadoResponse_QNAME, LogCertificadoResponse.class,
                                                       null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogCertificadoInputParameters }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://com/televisa/comer/integration/service/beans/logcertificado",
                    name = "LogCertificadoInputParameters")
    public JAXBElement<LogCertificadoInputParameters> createLogCertificadoInputParameters(LogCertificadoInputParameters value) {
        return new JAXBElement<LogCertificadoInputParameters>(_LogCertificadoInputParameters_QNAME,
                                                              LogCertificadoInputParameters.class, null, value);
    }

}
