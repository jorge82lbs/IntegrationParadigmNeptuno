
package com.tvsa.soin.xmlns.secman.servicios.usuariopermisos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RolNum complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RolNum">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RolNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "RolNum", propOrder = { "rolNum" })
public class RolNum {

    @XmlElement(name = "RolNum", required = true)
    protected String rolNum;
    @XmlAttribute(name = "DescriptionNum", namespace = "http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos")
    protected String descriptionNum;

    /**
     * Gets the value of the rolNum property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRolNum() {
        return rolNum;
    }

    /**
     * Sets the value of the rolNum property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRolNum(String value) {
        this.rolNum = value;
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
