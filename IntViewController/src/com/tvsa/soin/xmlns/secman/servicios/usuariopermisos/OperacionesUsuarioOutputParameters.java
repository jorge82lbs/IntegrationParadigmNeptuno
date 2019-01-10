
package com.tvsa.soin.xmlns.secman.servicios.usuariopermisos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OperacionesUsuarioOutputParameters complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="OperacionesUsuarioOutputParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserName" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}UserName"/>
 *         &lt;element name="AplicacionName" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}NomAplicacion"/>
 *         &lt;element name="OperacionesCollection" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}OperacionesCollection"/>
 *         &lt;element name="Error" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperacionesUsuarioOutputParameters", propOrder = {
         "userName", "aplicacionName", "operacionesCollection", "error"
    })
public class OperacionesUsuarioOutputParameters {

    @XmlElement(name = "UserName", required = true)
    protected UserName userName;
    @XmlElement(name = "AplicacionName", required = true)
    protected NomAplicacion aplicacionName;
    @XmlElement(name = "OperacionesCollection", required = true)
    protected OperacionesCollection operacionesCollection;
    @XmlElement(name = "Error")
    protected String error;

    /**
     * Gets the value of the userName property.
     *
     * @return
     *     possible object is
     *     {@link UserName }
     *
     */
    public UserName getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     *
     * @param value
     *     allowed object is
     *     {@link UserName }
     *
     */
    public void setUserName(UserName value) {
        this.userName = value;
    }

    /**
     * Gets the value of the aplicacionName property.
     *
     * @return
     *     possible object is
     *     {@link NomAplicacion }
     *
     */
    public NomAplicacion getAplicacionName() {
        return aplicacionName;
    }

    /**
     * Sets the value of the aplicacionName property.
     *
     * @param value
     *     allowed object is
     *     {@link NomAplicacion }
     *
     */
    public void setAplicacionName(NomAplicacion value) {
        this.aplicacionName = value;
    }

    /**
     * Gets the value of the operacionesCollection property.
     *
     * @return
     *     possible object is
     *     {@link OperacionesCollection }
     *
     */
    public OperacionesCollection getOperacionesCollection() {
        return operacionesCollection;
    }

    /**
     * Sets the value of the operacionesCollection property.
     *
     * @param value
     *     allowed object is
     *     {@link OperacionesCollection }
     *
     */
    public void setOperacionesCollection(OperacionesCollection value) {
        this.operacionesCollection = value;
    }

    /**
     * Gets the value of the error property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setError(String value) {
        this.error = value;
    }

}
