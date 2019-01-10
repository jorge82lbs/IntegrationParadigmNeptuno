
package mx.com.televisa.secman.view.permisos.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Permiso complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Permiso">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nomPermiso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descPermiso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fuentePermiso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RolesCollection" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}RolesCollection"/>
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
@XmlType(name = "Permiso", propOrder = {
    "nomPermiso",
    "descPermiso",
    "fuentePermiso",
    "rolesCollection",
    "tareasCollection",
    "operacionesCollection"
})
public class Permiso {

    @XmlElement(required = true)
    protected String nomPermiso;
    protected String descPermiso;
    protected String fuentePermiso;
    @XmlElement(name = "RolesCollection", required = true)
    protected RolesCollection rolesCollection;
    @XmlElement(name = "TareasCollection", required = true)
    protected TareasCollection tareasCollection;
    @XmlElement(name = "OperacionesCollection", required = true)
    protected OperacionesCollection operacionesCollection;

    /**
     * Gets the value of the nomPermiso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomPermiso() {
        return nomPermiso;
    }

    /**
     * Sets the value of the nomPermiso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomPermiso(String value) {
        this.nomPermiso = value;
    }

    /**
     * Gets the value of the descPermiso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescPermiso() {
        return descPermiso;
    }

    /**
     * Sets the value of the descPermiso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescPermiso(String value) {
        this.descPermiso = value;
    }

    /**
     * Gets the value of the fuentePermiso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFuentePermiso() {
        return fuentePermiso;
    }

    /**
     * Sets the value of the fuentePermiso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFuentePermiso(String value) {
        this.fuentePermiso = value;
    }

    /**
     * Gets the value of the rolesCollection property.
     * 
     * @return
     *     possible object is
     *     {@link RolesCollection }
     *     
     */
    public RolesCollection getRolesCollection() {
        return rolesCollection;
    }

    /**
     * Sets the value of the rolesCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link RolesCollection }
     *     
     */
    public void setRolesCollection(RolesCollection value) {
        this.rolesCollection = value;
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
