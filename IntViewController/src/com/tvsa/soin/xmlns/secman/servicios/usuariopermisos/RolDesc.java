
package com.tvsa.soin.xmlns.secman.servicios.usuariopermisos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RolDesc complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RolDesc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RolDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}DescriptionDesc"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RolDesc", propOrder = { "rolDesc" })
public class RolDesc {

    @XmlElement(name = "RolDesc", required = true)
    protected String rolDesc;
    @XmlAttribute(name = "DescriptionDesc", namespace = "http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos")
    protected String descriptionDesc;

    /**
     * Gets the value of the rolDesc property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRolDesc() {
        return rolDesc;
    }

    /**
     * Sets the value of the rolDesc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRolDesc(String value) {
        this.rolDesc = value;
    }

    /**
     * Gets the value of the descriptionDesc property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDescriptionDesc() {
        return descriptionDesc;
    }

    /**
     * Sets the value of the descriptionDesc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDescriptionDesc(String value) {
        this.descriptionDesc = value;
    }

}
