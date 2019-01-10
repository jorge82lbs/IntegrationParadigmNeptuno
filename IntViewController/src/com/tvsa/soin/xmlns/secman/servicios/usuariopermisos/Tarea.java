
package com.tvsa.soin.xmlns.secman.servicios.usuariopermisos;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Tarea complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Tarea">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TareaId" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}TareaId"/>
 *         &lt;element name="TareaName" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}TareaName"/>
 *         &lt;element name="TareaNum" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}TareaNum"/>
 *         &lt;element name="TareaDesc" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}TareaDesc"/>
 *         &lt;element name="Property" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}ItemProperty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="OperacionesCollection" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}OperacionesCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tarea", propOrder = {
         "tareaId", "tareaName", "tareaNum", "tareaDesc", "property", "operacionesCollection" })
public class Tarea {

    @XmlElement(name = "TareaId", required = true)
    protected TareaId tareaId;
    @XmlElement(name = "TareaName", required = true)
    protected TareaName tareaName;
    @XmlElement(name = "TareaNum", required = true)
    protected TareaNum tareaNum;
    @XmlElement(name = "TareaDesc", required = true)
    protected TareaDesc tareaDesc;
    @XmlElement(name = "Property")
    protected List<ItemProperty> property;
    @XmlElement(name = "OperacionesCollection", required = true)
    protected OperacionesCollection operacionesCollection;

    /**
     * Gets the value of the tareaId property.
     *
     * @return
     *     possible object is
     *     {@link TareaId }
     *
     */
    public TareaId getTareaId() {
        return tareaId;
    }

    /**
     * Sets the value of the tareaId property.
     *
     * @param value
     *     allowed object is
     *     {@link TareaId }
     *
     */
    public void setTareaId(TareaId value) {
        this.tareaId = value;
    }

    /**
     * Gets the value of the tareaName property.
     *
     * @return
     *     possible object is
     *     {@link TareaName }
     *
     */
    public TareaName getTareaName() {
        return tareaName;
    }

    /**
     * Sets the value of the tareaName property.
     *
     * @param value
     *     allowed object is
     *     {@link TareaName }
     *
     */
    public void setTareaName(TareaName value) {
        this.tareaName = value;
    }

    /**
     * Gets the value of the tareaNum property.
     *
     * @return
     *     possible object is
     *     {@link TareaNum }
     *
     */
    public TareaNum getTareaNum() {
        return tareaNum;
    }

    /**
     * Sets the value of the tareaNum property.
     *
     * @param value
     *     allowed object is
     *     {@link TareaNum }
     *
     */
    public void setTareaNum(TareaNum value) {
        this.tareaNum = value;
    }

    /**
     * Gets the value of the tareaDesc property.
     *
     * @return
     *     possible object is
     *     {@link TareaDesc }
     *
     */
    public TareaDesc getTareaDesc() {
        return tareaDesc;
    }

    /**
     * Sets the value of the tareaDesc property.
     *
     * @param value
     *     allowed object is
     *     {@link TareaDesc }
     *
     */
    public void setTareaDesc(TareaDesc value) {
        this.tareaDesc = value;
    }

    /**
     * Gets the value of the property property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the property property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperty().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemProperty }
     *
     *
     */
    public List<ItemProperty> getProperty() {
        if (property == null) {
            property = new ArrayList<ItemProperty>();
        }
        return this.property;
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

}
