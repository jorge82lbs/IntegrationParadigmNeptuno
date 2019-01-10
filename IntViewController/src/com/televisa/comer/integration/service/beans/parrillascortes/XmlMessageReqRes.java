
package com.televisa.comer.integration.service.beans.parrillascortes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for XmlMessageReqRes complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="XmlMessageReqRes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="XmlMessageRequest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="XmlMessageResponse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XmlMessageReqRes", propOrder = { "xmlMessageRequest", "xmlMessageResponse" })
public class XmlMessageReqRes {

    @XmlElement(name = "XmlMessageRequest")
    protected String xmlMessageRequest;
    @XmlElement(name = "XmlMessageResponse")
    protected String xmlMessageResponse;

    /**
     * Gets the value of the xmlMessageRequest property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getXmlMessageRequest() {
        return xmlMessageRequest;
    }

    /**
     * Sets the value of the xmlMessageRequest property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setXmlMessageRequest(String value) {
        this.xmlMessageRequest = value;
    }

    /**
     * Gets the value of the xmlMessageResponse property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getXmlMessageResponse() {
        return xmlMessageResponse;
    }

    /**
     * Sets the value of the xmlMessageResponse property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setXmlMessageResponse(String value) {
        this.xmlMessageResponse = value;
    }

}
