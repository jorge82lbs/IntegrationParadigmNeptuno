
package com.televisa.comer.integration.ws.beans.pgm.vtradicional;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CodRespuesta complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CodRespuesta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ItemCabecera" type="{http://com/televisa/comer/integration/ws/beans/pgm/vtradicional}ItemCabecera" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CodRespuesta", propOrder = { "itemCabecera" })
public class CodRespuesta {

    @XmlElement(name = "ItemCabecera", required = true)
    protected List<ItemCabecera> itemCabecera;

    /**
     * Gets the value of the itemCabecera property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemCabecera property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemCabecera().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemCabecera }
     *
     *
     */
    public List<ItemCabecera> getItemCabecera() {
        if (itemCabecera == null) {
            itemCabecera = new ArrayList<ItemCabecera>();
        }
        return this.itemCabecera;
    }

}
