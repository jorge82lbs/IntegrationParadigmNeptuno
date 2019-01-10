
package com.tvsa.soin.xmlns.secman.servicios.usuariopermisos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TareaNum complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TareaNum">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TareaNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}DescriptionNum"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TareaNum", propOrder = { "tareaNum" })
public class TareaNum {

    @XmlElement(name = "TareaNum", required = true)
    protected String tareaNum;
    @XmlAttribute(name = "DescriptionNum", namespace = "http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos")
    protected String descriptionNum;

    /**
     * Gets the value of the tareaNum property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTareaNum() {
        return tareaNum;
    }

    /**
     * Sets the value of the tareaNum property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTareaNum(String value) {
        this.tareaNum = value;
    }

    /**
     * Gets the value of the descriptionNum property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDescriptionNum() {
        return descriptionNum;
    }

    /**
     * Sets the value of the descriptionNum property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDescriptionNum(String value) {
        this.descriptionNum = value;
    }

}
