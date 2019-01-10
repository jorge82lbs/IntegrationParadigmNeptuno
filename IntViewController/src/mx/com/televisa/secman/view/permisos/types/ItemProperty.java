
package mx.com.televisa.secman.view.permisos.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemProperty complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemProperty">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ItemProperty" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}ValueProperty"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemProperty", propOrder = {
    "itemProperty"
})
public class ItemProperty {

    @XmlElement(name = "ItemProperty", required = true)
    protected String itemProperty;
    @XmlAttribute(name = "ValueProperty", namespace = "http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos")
    protected String valueProperty;

    /**
     * Gets the value of the itemProperty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemProperty() {
        return itemProperty;
    }

    /**
     * Sets the value of the itemProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemProperty(String value) {
        this.itemProperty = value;
    }

    /**
     * Gets the value of the valueProperty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueProperty() {
        return valueProperty;
    }

    /**
     * Sets the value of the valueProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueProperty(String value) {
        this.valueProperty = value;
    }

}
