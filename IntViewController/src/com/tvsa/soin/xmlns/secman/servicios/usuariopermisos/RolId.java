
package com.tvsa.soin.xmlns.secman.servicios.usuariopermisos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RolId complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RolId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RolId" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "RolId", propOrder = { "rolId" })
public class RolId {

    @XmlElement(name = "RolId", required = true)
    protected String rolId;
    @XmlAttribute(name = "DescriptionId", namespace = "http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos")
    protected String descriptionId;

    /**
     * Gets the value of the rolId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRolId() {
        return rolId;
    }

    /**
     * Sets the value of the rolId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRolId(String value) {
        this.rolId = value;
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
