
package com.tvsa.soin.xmlns.secman.servicios.secmaninfsendmail;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MailAddressCollection complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="MailAddressCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MailAddress" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/SecmanInfSendMail}MailAddress" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MailAddressCollection", propOrder = { "mailAddress" })
public class MailAddressCollection {

    @XmlElement(name = "MailAddress", required = true)
    protected List<MailAddress> mailAddress;

    /**
     * Gets the value of the mailAddress property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mailAddress property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMailAddress().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MailAddress }
     *
     *
     */
    public List<MailAddress> getMailAddress() {
        if (mailAddress == null) {
            mailAddress = new ArrayList<MailAddress>();
        }
        return this.mailAddress;
    }

}
