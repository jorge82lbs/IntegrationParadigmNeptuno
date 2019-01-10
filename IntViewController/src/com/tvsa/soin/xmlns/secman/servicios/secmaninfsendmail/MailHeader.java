
package com.tvsa.soin.xmlns.secman.servicios.secmaninfsendmail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MailHeader complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="MailHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AddressFrom" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/SecmanInfSendMail}MailAddress"/>
 *         &lt;element name="ReplyTo" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/SecmanInfSendMail}MailAddress" minOccurs="0"/>
 *         &lt;element name="To" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/SecmanInfSendMail}MailAddressCollection"/>
 *         &lt;element name="CC" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/SecmanInfSendMail}MailAddressCollection" minOccurs="0"/>
 *         &lt;element name="CCO" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/SecmanInfSendMail}MailAddressCollection" minOccurs="0"/>
 *         &lt;element name="Subject" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeliveryReceipt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReadReceipt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MailHeader", propOrder = {
         "addressFrom", "replyTo", "to", "cc", "cco", "subject", "priority", "deliveryReceipt", "readReceipt"
    })
public class MailHeader {

    @XmlElement(name = "AddressFrom", required = true)
    protected MailAddress addressFrom;
    @XmlElement(name = "ReplyTo")
    protected MailAddress replyTo;
    @XmlElement(name = "To", required = true)
    protected MailAddressCollection to;
    @XmlElement(name = "CC")
    protected MailAddressCollection cc;
    @XmlElement(name = "CCO")
    protected MailAddressCollection cco;
    @XmlElement(name = "Subject", required = true)
    protected String subject;
    @XmlElement(name = "Priority")
    protected String priority;
    @XmlElement(name = "DeliveryReceipt")
    protected String deliveryReceipt;
    @XmlElement(name = "ReadReceipt")
    protected String readReceipt;

    /**
     * Gets the value of the addressFrom property.
     *
     * @return
     *     possible object is
     *     {@link MailAddress }
     *
     */
    public MailAddress getAddressFrom() {
        return addressFrom;
    }

    /**
     * Sets the value of the addressFrom property.
     *
     * @param value
     *     allowed object is
     *     {@link MailAddress }
     *
     */
    public void setAddressFrom(MailAddress value) {
        this.addressFrom = value;
    }

    /**
     * Gets the value of the replyTo property.
     *
     * @return
     *     possible object is
     *     {@link MailAddress }
     *
     */
    public MailAddress getReplyTo() {
        return replyTo;
    }

    /**
     * Sets the value of the replyTo property.
     *
     * @param value
     *     allowed object is
     *     {@link MailAddress }
     *
     */
    public void setReplyTo(MailAddress value) {
        this.replyTo = value;
    }

    /**
     * Gets the value of the to property.
     *
     * @return
     *     possible object is
     *     {@link MailAddressCollection }
     *
     */
    public MailAddressCollection getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     *
     * @param value
     *     allowed object is
     *     {@link MailAddressCollection }
     *
     */
    public void setTo(MailAddressCollection value) {
        this.to = value;
    }

    /**
     * Gets the value of the cc property.
     *
     * @return
     *     possible object is
     *     {@link MailAddressCollection }
     *
     */
    public MailAddressCollection getCC() {
        return cc;
    }

    /**
     * Sets the value of the cc property.
     *
     * @param value
     *     allowed object is
     *     {@link MailAddressCollection }
     *
     */
    public void setCC(MailAddressCollection value) {
        this.cc = value;
    }

    /**
     * Gets the value of the cco property.
     *
     * @return
     *     possible object is
     *     {@link MailAddressCollection }
     *
     */
    public MailAddressCollection getCCO() {
        return cco;
    }

    /**
     * Sets the value of the cco property.
     *
     * @param value
     *     allowed object is
     *     {@link MailAddressCollection }
     *
     */
    public void setCCO(MailAddressCollection value) {
        this.cco = value;
    }

    /**
     * Gets the value of the subject property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    /**
     * Gets the value of the priority property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPriority(String value) {
        this.priority = value;
    }

    /**
     * Gets the value of the deliveryReceipt property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDeliveryReceipt() {
        return deliveryReceipt;
    }

    /**
     * Sets the value of the deliveryReceipt property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDeliveryReceipt(String value) {
        this.deliveryReceipt = value;
    }

    /**
     * Gets the value of the readReceipt property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getReadReceipt() {
        return readReceipt;
    }

    /**
     * Sets the value of the readReceipt property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setReadReceipt(String value) {
        this.readReceipt = value;
    }

}
