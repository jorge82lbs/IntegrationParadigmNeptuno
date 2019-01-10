
package mx.com.televisa.secman.view.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.tvsa.soin.xmlns.secman.servicios.usuariopermisos.PermisosUsuarioInputParameters;


/**
 * <p>Java class for obtenerRolTareaPermisos complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="obtenerRolTareaPermisos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}PermisosUsuarioInputParameters" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerRolTareaPermisos", propOrder = { "arg0" })
public class ObtenerRolTareaPermisos {

    protected PermisosUsuarioInputParameters arg0;

    /**
     * Gets the value of the arg0 property.
     *
     * @return
     *     possible object is
     *     {@link PermisosUsuarioInputParameters }
     *
     */
    public PermisosUsuarioInputParameters getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     *
     * @param value
     *     allowed object is
     *     {@link PermisosUsuarioInputParameters }
     *
     */
    public void setArg0(PermisosUsuarioInputParameters value) {
        this.arg0 = value;
    }

}
