
package mx.com.televisa.secman.view.permisos.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtenerRolTareaPermisosResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtenerRolTareaPermisosResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}PermisoOutputCollection" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerRolTareaPermisosResponse", namespace = "http://webServices.view.secman.televisa.com.mx/", propOrder = {
    "_return"
})
public class ObtenerRolTareaPermisosResponse {

    @XmlElement(name = "return", namespace = "")
    protected PermisoOutputCollection _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link PermisoOutputCollection }
     *     
     */
    public PermisoOutputCollection getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link PermisoOutputCollection }
     *     
     */
    public void setReturn(PermisoOutputCollection value) {
        this._return = value;
    }

}
