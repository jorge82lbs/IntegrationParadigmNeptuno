
package com.televisa.comer.integration.ws.beans.pgm.vtradicional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecibirDatosExternosResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RecibirDatosExternosResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RecibirDatosExternosResult" type="{http://com/televisa/comer/integration/ws/beans/pgm/vtradicional}RecibirDatosExternosResult"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecibirDatosExternosResponse", propOrder = { "recibirDatosExternosResult" })
public class RecibirDatosExternosResponse {

    @XmlElement(name = "RecibirDatosExternosResult", required = true)
    protected RecibirDatosExternosResult recibirDatosExternosResult;

    /**
     * Gets the value of the recibirDatosExternosResult property.
     *
     * @return
     *     possible object is
     *     {@link RecibirDatosExternosResult }
     *
     */
    public RecibirDatosExternosResult getRecibirDatosExternosResult() {
        return recibirDatosExternosResult;
    }

    /**
     * Sets the value of the recibirDatosExternosResult property.
     *
     * @param value
     *     allowed object is
     *     {@link RecibirDatosExternosResult }
     *
     */
    public void setRecibirDatosExternosResult(RecibirDatosExternosResult value) {
        this.recibirDatosExternosResult = value;
    }

}
