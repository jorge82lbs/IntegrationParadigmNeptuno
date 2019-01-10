
package com.televisa.comer.integration.ws.beans.pgm.parrillas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecibirDatosExternosResult complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RecibirDatosExternosResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodRespuesta" type="{http://com/televisa/comer/integration/ws/beans/pgm/parrillas}CodRespuesta"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecibirDatosExternosResult", propOrder = { "codRespuesta" })
public class RecibirDatosExternosResult {

    @XmlElement(name = "CodRespuesta", required = true)
    protected CodRespuesta codRespuesta;

    /**
     * Gets the value of the codRespuesta property.
     *
     * @return
     *     possible object is
     *     {@link CodRespuesta }
     *
     */
    public CodRespuesta getCodRespuesta() {
        return codRespuesta;
    }

    /**
     * Sets the value of the codRespuesta property.
     *
     * @param value
     *     allowed object is
     *     {@link CodRespuesta }
     *
     */
    public void setCodRespuesta(CodRespuesta value) {
        this.codRespuesta = value;
    }

}
