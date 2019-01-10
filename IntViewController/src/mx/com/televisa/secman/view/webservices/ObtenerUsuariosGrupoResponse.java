
package mx.com.televisa.secman.view.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.tvsa.soin.xmlns.secman.servicios.usuariogrupos.UsuariosCollection;


/**
 * <p>Java class for obtenerUsuariosGrupoResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="obtenerUsuariosGrupoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioGrupos}UsuariosCollection" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerUsuariosGrupoResponse", propOrder = { "_return" })
public class ObtenerUsuariosGrupoResponse {

    @XmlElement(name = "return")
    protected UsuariosCollection _return;

    /**
     * Gets the value of the return property.
     *
     * @return
     *     possible object is
     *     {@link UsuariosCollection }
     *
     */
    public UsuariosCollection getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     *
     * @param value
     *     allowed object is
     *     {@link UsuariosCollection }
     *
     */
    public void setReturn(UsuariosCollection value) {
        this._return = value;
    }

}
