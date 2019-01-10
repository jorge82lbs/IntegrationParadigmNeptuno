
package com.televisa.comer.integration.ws.beans.pgm.vtradicional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScheduleOnDemand complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ScheduleOnDemand">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InitialDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChannelID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CodeTrace" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleOnDemand", propOrder = { "initialDate", "endDate", "channelID", "codeTrace" })
public class ScheduleOnDemand {

    @XmlElement(name = "InitialDate")
    protected String initialDate;
    @XmlElement(name = "EndDate")
    protected String endDate;
    @XmlElement(name = "ChannelID", required = true)
    protected String channelID;
    @XmlElement(name = "CodeTrace", required = true)
    protected String codeTrace;

    /**
     * Gets the value of the initialDate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getInitialDate() {
        return initialDate;
    }

    /**
     * Sets the value of the initialDate property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setInitialDate(String value) {
        this.initialDate = value;
    }

    /**
     * Gets the value of the endDate property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEndDate(String value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the channelID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getChannelID() {
        return channelID;
    }

    /**
     * Sets the value of the channelID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setChannelID(String value) {
        this.channelID = value;
    }

    /**
     * Gets the value of the codeTrace property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCodeTrace() {
        return codeTrace;
    }

    /**
     * Sets the value of the codeTrace property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCodeTrace(String value) {
        this.codeTrace = value;
    }

}
