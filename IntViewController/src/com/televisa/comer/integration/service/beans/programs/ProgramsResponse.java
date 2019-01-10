
package com.televisa.comer.integration.service.beans.programs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProgramsResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ProgramsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdRequestProgramsRes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="XmlMessageResponse" type="{http://com/televisa/comer/integration/service/beans/programs}XmlMessageResponseCollection"/>
 *         &lt;element name="Error" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProgramsResponse", propOrder = { "idRequestProgramsRes", "xmlMessageResponse", "error" })
public class ProgramsResponse {

    @XmlElement(name = "IdRequestProgramsRes", required = true)
    protected String idRequestProgramsRes;
    @XmlElement(name = "XmlMessageResponse", required = true)
    protected XmlMessageResponseCollection xmlMessageResponse;
    @XmlElement(name = "Error")
    protected String error;

    /**
     * Gets the value of the idRequestProgramsRes property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdRequestProgramsRes() {
        return idRequestProgramsRes;
    }

    /**
     * Sets the value of the idRequestProgramsRes property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdRequestProgramsRes(String value) {
        this.idRequestProgramsRes = value;
    }

    /**
     * Gets the value of the xmlMessageResponse property.
     *
     * @return
     *     possible object is
     *     {@link XmlMessageResponseCollection }
     *
     */
    public XmlMessageResponseCollection getXmlMessageResponse() {
        return xmlMessageResponse;
    }

    /**
     * Sets the value of the xmlMessageResponse property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlMessageResponseCollection }
     *
     */
    public void setXmlMessageResponse(XmlMessageResponseCollection value) {
        this.xmlMessageResponse = value;
    }

    /**
     * Gets the value of the error property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setError(String value) {
        this.error = value;
    }

}
