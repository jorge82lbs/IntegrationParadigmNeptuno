
package com.televisa.comer.integration.service.beans.logcertificado;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChannelLcert complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ChannelLcert">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChannelLcert" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChannelLcert", propOrder = { "channelLcert" })
public class ChannelLcert {

    @XmlElement(name = "ChannelLcert")
    protected String channelLcert;

    /**
     * Gets the value of the channelLcert property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getChannelLcert() {
        return channelLcert;
    }

    /**
     * Sets the value of the channelLcert property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setChannelLcert(String value) {
        this.channelLcert = value;
    }

}
