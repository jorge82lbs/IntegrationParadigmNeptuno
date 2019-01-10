
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.tvsa.soin.xmlns.secman.servicios.secmaninfsendmail.Mail;


/**
 * <p>Java class for EnviarCorreo complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="EnviarCorreo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mail" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/SecmanInfSendMail}Mail" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnviarCorreo", propOrder = { "mail" })
public class EnviarCorreo {

    protected Mail mail;

    /**
     * Gets the value of the mail property.
     *
     * @return
     *     possible object is
     *     {@link Mail }
     *
     */
    public Mail getMail() {
        return mail;
    }

    /**
     * Sets the value of the mail property.
     *
     * @param value
     *     allowed object is
     *     {@link Mail }
     *
     */
    public void setMail(Mail value) {
        this.mail = value;
    }

}
