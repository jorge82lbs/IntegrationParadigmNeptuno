
package com.televisa.comer.integration.service.beans.targets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParameterMap complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ParameterMap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ParameterName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ParameterValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParameterMap", propOrder = { "parameterName", "parameterValue" })
public class ParameterMap {

    @XmlElement(name = "ParameterName", required = true)
    protected String parameterName;
    @XmlElement(name = "ParameterValue", required = true)
    protected String parameterValue;

    /**
     * Gets the value of the parameterName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * Sets the value of the parameterName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setParameterName(String value) {
        this.parameterName = value;
    }

    /**
     * Gets the value of the parameterValue property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getParameterValue() {
        return parameterValue;
    }

    /**
     * Sets the value of the parameterValue property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setParameterValue(String value) {
        this.parameterValue = value;
    }

}
