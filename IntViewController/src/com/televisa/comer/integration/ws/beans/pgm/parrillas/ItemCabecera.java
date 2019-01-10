
package com.televisa.comer.integration.ws.beans.pgm.parrillas;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemCabecera complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ItemCabecera">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProcessID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Resultado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TipoProceso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ItemRespuesta" type="{http://com/televisa/comer/integration/ws/beans/pgm/parrillas}ItemRespuesta" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemCabecera", propOrder = { "processID", "resultado", "tipoProceso", "itemRespuesta" })
public class ItemCabecera {

    @XmlElement(name = "ProcessID", required = true)
    protected String processID;
    @XmlElement(name = "Resultado", required = true)
    protected String resultado;
    @XmlElement(name = "TipoProceso", required = true)
    protected String tipoProceso;
    @XmlElement(name = "ItemRespuesta", required = true)
    protected List<ItemRespuesta> itemRespuesta;

    /**
     * Gets the value of the processID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getProcessID() {
        return processID;
    }

    /**
     * Sets the value of the processID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setProcessID(String value) {
        this.processID = value;
    }

    /**
     * Gets the value of the resultado property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getResultado() {
        return resultado;
    }

    /**
     * Sets the value of the resultado property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setResultado(String value) {
        this.resultado = value;
    }

    /**
     * Gets the value of the tipoProceso property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTipoProceso() {
        return tipoProceso;
    }

    /**
     * Sets the value of the tipoProceso property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTipoProceso(String value) {
        this.tipoProceso = value;
    }

    /**
     * Gets the value of the itemRespuesta property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemRespuesta property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemRespuesta().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemRespuesta }
     *
     *
     */
    public List<ItemRespuesta> getItemRespuesta() {
        if (itemRespuesta == null) {
            itemRespuesta = new ArrayList<ItemRespuesta>();
        }
        return this.itemRespuesta;
    }

}
