
package mx.com.televisa.secman.view.permisos.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Rol complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Rol">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RolId" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}RolId"/>
 *         &lt;element name="RolName" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}RolName"/>
 *         &lt;element name="Property" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}ItemProperty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="TareasCollection" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}TareasCollection"/>
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
@XmlType(name = "Rol", propOrder = {
    "rolId",
    "rolName",
    "property",
    "tareasCollection",
    "operacionesCollection"
})
public class Rol {

    @XmlElement(name = "RolId", required = true)
    protected RolId rolId;
    @XmlElement(name = "RolName", required = true)
    protected RolName rolName;
    @XmlElement(name = "Property")
    protected List<ItemProperty> property;
    @XmlElement(name = "TareasCollection", required = true)
    protected TareasCollection tareasCollection;
    @XmlElement(name = "OperacionesCollection", required = true)
    protected OperacionesCollection operacionesCollection;

    /**
     * Gets the value of the rolId property.
     * 
     * @return
     *     possible object is
     *     {@link RolId }
     *     
     */
    public RolId getRolId() {
        return rolId;
    }

    /**
     * Sets the value of the rolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link RolId }
     *     
     */
    public void setRolId(RolId value) {
        this.rolId = value;
    }

    /**
     * Gets the value of the rolName property.
     * 
     * @return
     *     possible object is
     *     {@link RolName }
     *     
     */
    public RolName getRolName() {
        return rolName;
    }

    /**
     * Sets the value of the rolName property.
     * 
     * @param value
     *     allowed object is
     *     {@link RolName }
     *     
     */
    public void setRolName(RolName value) {
        this.rolName = value;
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
     * Gets the value of the tareasCollection property.
     * 
     * @return
     *     possible object is
     *     {@link TareasCollection }
     *     
     */
    public TareasCollection getTareasCollection() {
        return tareasCollection;
    }

    /**
     * Sets the value of the tareasCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link TareasCollection }
     *     
     */
    public void setTareasCollection(TareasCollection value) {
        this.tareasCollection = value;
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
