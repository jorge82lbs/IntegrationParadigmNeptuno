
package com.tvsa.soin.xmlns.secman.servicios.usuariopermisos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TareaDesc complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TareaDesc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TareaDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "TareaDesc", propOrder = { "tareaDesc" })
public class TareaDesc {

    @XmlElement(name = "TareaDesc", required = true)
    protected String tareaDesc;
    @XmlAttribute(name = "DescriptionDesc", namespace = "http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos")
    protected String descriptionDesc;

    /**
     * Gets the value of the tareaDesc property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTareaDesc() {
        return tareaDesc;
    }

    /**
     * Sets the value of the tareaDesc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTareaDesc(String value) {
        this.tareaDesc = value;
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
