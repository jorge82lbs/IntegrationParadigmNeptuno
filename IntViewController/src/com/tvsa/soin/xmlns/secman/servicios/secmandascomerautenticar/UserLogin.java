
package com.tvsa.soin.xmlns.secman.servicios.secmandascomerautenticar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserLogin complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="UserLogin">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NomAplicacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Accion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UserPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserLogin", propOrder = { "userName", "nomAplicacion", "accion", "userPassword", "token" })
public class UserLogin {

    @XmlElement(name = "UserName", required = true)
    protected String userName;
    @XmlElement(name = "NomAplicacion", required = true)
    protected String nomAplicacion;
    @XmlElement(name = "Accion", required = true)
    protected String accion;
    @XmlElement(name = "UserPassword", required = true)
    protected String userPassword;
    @XmlElement(name = "Token", required = true)
    protected String token;

    /**
     * Gets the value of the userName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the nomAplicacion property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNomAplicacion() {
        return nomAplicacion;
    }

    /**
     * Sets the value of the nomAplicacion property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNomAplicacion(String value) {
        this.nomAplicacion = value;
    }

    /**
     * Gets the value of the accion property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAccion() {
        return accion;
    }

    /**
     * Sets the value of the accion property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAccion(String value) {
        this.accion = value;
    }

    /**
     * Gets the value of the userPassword property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Sets the value of the userPassword property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUserPassword(String value) {
        this.userPassword = value;
    }

    /**
     * Gets the value of the token property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setToken(String value) {
        this.token = value;
    }

}
