
package com.tvsa.soin.xmlns.secman.servicios.usuariopermisos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Operacion complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Operacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OperacionId" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}OperacionId"/>
 *         &lt;element name="OperacionName" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}OperacionName"/>
 *         &lt;element name="OperacionDesc" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}OperacionDesc"/>
 *         &lt;element name="OperacionNum" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}OperacionNum"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Operacion", propOrder = { "operacionId", "operacionName", "operacionDesc", "operacionNum" })
public class Operacion {

    @XmlElement(name = "OperacionId", required = true)
    protected OperacionId operacionId;
    @XmlElement(name = "OperacionName", required = true)
    protected OperacionName operacionName;
    @XmlElement(name = "OperacionDesc", required = true)
    protected OperacionDesc operacionDesc;
    @XmlElement(name = "OperacionNum", required = true)
    protected OperacionNum operacionNum;

    /**
     * Gets the value of the operacionId property.
     *
     * @return
     *     possible object is
     *     {@link OperacionId }
     *
     */
    public OperacionId getOperacionId() {
        return operacionId;
    }

    /**
     * Sets the value of the operacionId property.
     *
     * @param value
     *     allowed object is
     *     {@link OperacionId }
     *
     */
    public void setOperacionId(OperacionId value) {
        this.operacionId = value;
    }

    /**
     * Gets the value of the operacionName property.
     *
     * @return
     *     possible object is
     *     {@link OperacionName }
     *
     */
    public OperacionName getOperacionName() {
        return operacionName;
    }

    /**
     * Sets the value of the operacionName property.
     *
     * @param value
     *     allowed object is
     *     {@link OperacionName }
     *
     */
    public void setOperacionName(OperacionName value) {
        this.operacionName = value;
    }

    /**
     * Gets the value of the operacionDesc property.
     *
     * @return
     *     possible object is
     *     {@link OperacionDesc }
     *
     */
    public OperacionDesc getOperacionDesc() {
        return operacionDesc;
    }

    /**
     * Sets the value of the operacionDesc property.
     *
     * @param value
     *     allowed object is
     *     {@link OperacionDesc }
     *
     */
    public void setOperacionDesc(OperacionDesc value) {
        this.operacionDesc = value;
    }

    /**
     * Gets the value of the operacionNum property.
     *
     * @return
     *     possible object is
     *     {@link OperacionNum }
     *
     */
    public OperacionNum getOperacionNum() {
        return operacionNum;
    }

    /**
     * Sets the value of the operacionNum property.
     *
     * @param value
     *     allowed object is
     *     {@link OperacionNum }
     *
     */
    public void setOperacionNum(OperacionNum value) {
        this.operacionNum = value;
    }

}
