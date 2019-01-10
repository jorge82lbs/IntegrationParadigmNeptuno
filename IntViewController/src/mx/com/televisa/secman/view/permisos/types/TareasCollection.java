
package mx.com.televisa.secman.view.permisos.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TareasCollection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TareasCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Tarea" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}Tarea" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TareasCollection", propOrder = {
    "tarea"
})
public class TareasCollection {

    @XmlElement(name = "Tarea", required = true)
    protected List<Tarea> tarea;

    /**
     * Gets the value of the tarea property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tarea property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTarea().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Tarea }
     * 
     * 
     */
    public List<Tarea> getTarea() {
        if (tarea == null) {
            tarea = new ArrayList<Tarea>();
        }
        return this.tarea;
    }

}
