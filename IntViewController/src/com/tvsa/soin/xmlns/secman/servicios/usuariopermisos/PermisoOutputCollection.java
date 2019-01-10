
package com.tvsa.soin.xmlns.secman.servicios.usuariopermisos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PermisoOutputCollection complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PermisoOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Usuario" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}Usuario"/>
 *         &lt;element name="Aplicacion" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}Aplicacion"/>
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
@XmlType(name = "PermisoOutputCollection", propOrder = { "usuario", "aplicacion", "error" })
public class PermisoOutputCollection {

    @XmlElement(name = "Usuario", required = true)
    protected Usuario usuario;
    @XmlElement(name = "Aplicacion", required = true)
    protected Aplicacion aplicacion;
    @XmlElement(name = "Error")
    protected String error;

    /**
     * Gets the value of the usuario property.
     *
     * @return
     *     possible object is
     *     {@link Usuario }
     *
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     *
     * @param value
     *     allowed object is
     *     {@link Usuario }
     *
     */
    public void setUsuario(Usuario value) {
        this.usuario = value;
    }

    /**
     * Gets the value of the aplicacion property.
     *
     * @return
     *     possible object is
     *     {@link Aplicacion }
     *
     */
    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    /**
     * Sets the value of the aplicacion property.
     *
     * @param value
     *     allowed object is
     *     {@link Aplicacion }
     *
     */
    public void setAplicacion(Aplicacion value) {
        this.aplicacion = value;
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
