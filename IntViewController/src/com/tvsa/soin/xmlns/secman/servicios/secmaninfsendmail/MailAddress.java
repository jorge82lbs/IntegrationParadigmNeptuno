
package com.tvsa.soin.xmlns.secman.servicios.secmaninfsendmail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MailAddress complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="MailAddress">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserNameAdress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MailAddress", propOrder = { "userNameAdress", "address" })
public class MailAddress {

    @XmlElement(name = "UserNameAdress", required = true)
    protected String userNameAdress;
    @XmlElement(name = "Address", required = true)
    protected String address;

    /**
     * Gets the value of the userNameAdress property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUserNameAdress() {
        return userNameAdress;
    }

    /**
     * Sets the value of the userNameAdress property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUserNameAdress(String value) {
        this.userNameAdress = value;
    }

    /**
     * Gets the value of the address property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAddress(String value) {
        this.address = value;
    }

}
