
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.tvsa.soin.xmlns.secman.servicios.secmandascomerautenticar.UserLogin;


/**
 * <p>Java class for autenticarUsuario complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="autenticarUsuario">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserLogin" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/SecmanDasComerAutenticar}UserLogin" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autenticarUsuario", propOrder = { "userLogin" })
public class AutenticarUsuario {

    @XmlElement(name = "UserLogin")
    protected UserLogin userLogin;

    /**
     * Gets the value of the userLogin property.
     *
     * @return
     *     possible object is
     *     {@link UserLogin }
     *
     */
    public UserLogin getUserLogin() {
        return userLogin;
    }

    /**
     * Sets the value of the userLogin property.
     *
     * @param value
     *     allowed object is
     *     {@link UserLogin }
     *
     */
    public void setUserLogin(UserLogin value) {
        this.userLogin = value;
    }

}
