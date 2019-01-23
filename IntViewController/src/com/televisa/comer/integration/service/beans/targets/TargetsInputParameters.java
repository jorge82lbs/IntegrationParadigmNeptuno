
package com.televisa.comer.integration.service.beans.targets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TargetsInputParameters complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TargetsInputParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdRequestTargetsReq" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ParametersList" type="{http://com/televisa/comer/integration/service/beans/targets}ParametersCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetsInputParameters", propOrder = { "idRequestTargetsReq", "parametersList" })
public class TargetsInputParameters {

    @XmlElement(name = "IdRequestTargetsReq", required = true)
    protected String idRequestTargetsReq;
    @XmlElement(name = "ParametersList", required = true)
    protected ParametersCollection parametersList;

    /**
     * Gets the value of the idRequestTargetsReq property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdRequestTargetsReq() {
        return idRequestTargetsReq;
    }

    /**
     * Sets the value of the idRequestTargetsReq property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdRequestTargetsReq(String value) {
        this.idRequestTargetsReq = value;
    }

    /**
     * Gets the value of the parametersList property.
     *
     * @return
     *     possible object is
     *     {@link ParametersCollection }
     *
     */
    public ParametersCollection getParametersList() {
        return parametersList;
    }

    /**
     * Sets the value of the parametersList property.
     *
     * @param value
     *     allowed object is
     *     {@link ParametersCollection }
     *
     */
    public void setParametersList(ParametersCollection value) {
        this.parametersList = value;
    }

}
