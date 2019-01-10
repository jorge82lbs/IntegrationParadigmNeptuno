
package com.televisa.comer.integration.service.beans.logcertificado;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for XmlMessageLcertReqRes complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="XmlMessageLcertReqRes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="XmlMessageLcertRequest" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="XmlMessageLcertResponse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XmlMessageLcertReqRes", propOrder = { "xmlMessageLcertRequest", "xmlMessageLcertResponse" })
public class XmlMessageLcertReqRes {

    @XmlElement(name = "XmlMessageLcertRequest")
    protected String xmlMessageLcertRequest;
    @XmlElement(name = "XmlMessageLcertResponse")
    protected String xmlMessageLcertResponse;

    /**
     * Gets the value of the xmlMessageLcertRequest property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getXmlMessageLcertRequest() {
        return xmlMessageLcertRequest;
    }

    /**
     * Sets the value of the xmlMessageLcertRequest property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setXmlMessageLcertRequest(String value) {
        this.xmlMessageLcertRequest = value;
    }

    /**
     * Gets the value of the xmlMessageLcertResponse property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getXmlMessageLcertResponse() {
        return xmlMessageLcertResponse;
    }

    /**
     * Sets the value of the xmlMessageLcertResponse property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setXmlMessageLcertResponse(String value) {
        this.xmlMessageLcertResponse = value;
    }

}
