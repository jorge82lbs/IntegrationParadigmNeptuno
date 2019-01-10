
package com.tvsa.soin.xmlns.secman.servicios.usuariopermisos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UsuarioId complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="UsuarioId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdUsuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}DescriptionId"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsuarioId", propOrder = { "idUsuario" })
public class UsuarioId {

    @XmlElement(name = "IdUsuario", required = true)
    protected String idUsuario;
    @XmlAttribute(name = "DescriptionId", namespace = "http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos")
    protected String descriptionId;

    /**
     * Gets the value of the idUsuario property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Sets the value of the idUsuario property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdUsuario(String value) {
        this.idUsuario = value;
    }

    /**
     * Gets the value of the descriptionId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDescriptionId() {
        return descriptionId;
    }

    /**
     * Sets the value of the descriptionId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDescriptionId(String value) {
        this.descriptionId = value;
    }

}
