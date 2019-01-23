
package com.televisa.comer.integration.service.beans.targets;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.televisa.comer.integration.service.beans.targets package.
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.televisa.comer.integration.service.beans.targets
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TargetsInputParameters }
     *
     */
    public TargetsInputParameters createTargetsInputParameters() {
        return new TargetsInputParameters();
    }

    /**
     * Create an instance of {@link TargetsResponse }
     *
     */
    public TargetsResponse createTargetsResponse() {
        return new TargetsResponse();
    }

    /**
     * Create an instance of {@link ParametersCollection }
     *
     */
    public ParametersCollection createParametersCollection() {
        return new ParametersCollection();
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
     * Create an instance of {@link ParameterMap }
     *
     */
    public ParameterMap createParameterMap() {
        return new ParameterMap();
    }

}
