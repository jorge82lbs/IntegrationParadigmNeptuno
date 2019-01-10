
package com.televisa.comer.integration.ws.beans.pgm.parrillas;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemRespuesta complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ItemRespuesta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Elemento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IdElemento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Resultado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ListaMensaje" type="{http://com/televisa/comer/integration/ws/beans/pgm/parrillas}ListaMensaje" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemRespuesta", propOrder = { "elemento", "idElemento", "resultado", "listaMensaje" })
public class ItemRespuesta {

    @XmlElement(name = "Elemento", required = true)
    protected String elemento;
    @XmlElement(name = "IdElemento", required = true)
    protected String idElemento;
    @XmlElement(name = "Resultado", required = true)
    protected String resultado;
    @XmlElement(name = "ListaMensaje", required = true)
    protected List<ListaMensaje> listaMensaje;

    /**
     * Gets the value of the elemento property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getElemento() {
        return elemento;
    }

    /**
     * Sets the value of the elemento property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setElemento(String value) {
        this.elemento = value;
    }

    /**
     * Gets the value of the idElemento property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdElemento() {
        return idElemento;
    }

    /**
     * Sets the value of the idElemento property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdElemento(String value) {
        this.idElemento = value;
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
     * Gets the value of the listaMensaje property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaMensaje property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaMensaje().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ListaMensaje }
     *
     *
     */
    public List<ListaMensaje> getListaMensaje() {
        if (listaMensaje == null) {
            listaMensaje = new ArrayList<ListaMensaje>();
        }
        return this.listaMensaje;
    }

}
