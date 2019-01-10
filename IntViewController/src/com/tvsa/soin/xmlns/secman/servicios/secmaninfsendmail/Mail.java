
package com.tvsa.soin.xmlns.secman.servicios.secmaninfsendmail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Mail complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Mail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MailHeader" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/SecmanInfSendMail}MailHeader"/>
 *         &lt;element name="MailBody" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/SecmanInfSendMail}MailBody"/>
 *         &lt;element name="MailAttachments" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/SecmanInfSendMail}AttachmentCollection" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Mail", propOrder = { "mailHeader", "mailBody", "mailAttachments" })
public class Mail {

    @XmlElement(name = "MailHeader", required = true)
    protected MailHeader mailHeader;
    @XmlElement(name = "MailBody", required = true)
    protected MailBody mailBody;
    @XmlElement(name = "MailAttachments")
    protected AttachmentCollection mailAttachments;

    /**
     * Gets the value of the mailHeader property.
     *
     * @return
     *     possible object is
     *     {@link MailHeader }
     *
     */
    public MailHeader getMailHeader() {
        return mailHeader;
    }

    /**
     * Sets the value of the mailHeader property.
     *
     * @param value
     *     allowed object is
     *     {@link MailHeader }
     *
     */
    public void setMailHeader(MailHeader value) {
        this.mailHeader = value;
    }

    /**
     * Gets the value of the mailBody property.
     *
     * @return
     *     possible object is
     *     {@link MailBody }
     *
     */
    public MailBody getMailBody() {
        return mailBody;
    }

    /**
     * Sets the value of the mailBody property.
     *
     * @param value
     *     allowed object is
     *     {@link MailBody }
     *
     */
    public void setMailBody(MailBody value) {
        this.mailBody = value;
    }

    /**
     * Gets the value of the mailAttachments property.
     *
     * @return
     *     possible object is
     *     {@link AttachmentCollection }
     *
     */
    public AttachmentCollection getMailAttachments() {
        return mailAttachments;
    }

    /**
     * Sets the value of the mailAttachments property.
     *
     * @param value
     *     allowed object is
     *     {@link AttachmentCollection }
     *
     */
    public void setMailAttachments(AttachmentCollection value) {
        this.mailAttachments = value;
    }

}
