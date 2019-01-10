
package mx.com.televisa.secman.view.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.tvsa.soin.xmlns.secman.servicios.usuariogrupos.UsuariosGrupoInputParameters;


/**
 * <p>Java class for obtenerUsuariosGrupo complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="obtenerUsuariosGrupo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioGrupos}UsuariosGrupoInputParameters" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerUsuariosGrupo", propOrder = { "arg0" })
public class ObtenerUsuariosGrupo {

    protected UsuariosGrupoInputParameters arg0;

    /**
     * Gets the value of the arg0 property.
     *
     * @return
     *     possible object is
     *     {@link UsuariosGrupoInputParameters }
     *
     */
    public UsuariosGrupoInputParameters getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     *
     * @param value
     *     allowed object is
     *     {@link UsuariosGrupoInputParameters }
     *
     */
    public void setArg0(UsuariosGrupoInputParameters value) {
        this.arg0 = value;
    }

}
