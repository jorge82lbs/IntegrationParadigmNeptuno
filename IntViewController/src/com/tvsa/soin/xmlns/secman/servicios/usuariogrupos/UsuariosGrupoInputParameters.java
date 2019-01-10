
package com.tvsa.soin.xmlns.secman.servicios.usuariogrupos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UsuariosGrupoInputParameters complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="UsuariosGrupoInputParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nomGrupo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UsuariosGrupoInputParameters", propOrder = { "nomGrupo" })
public class UsuariosGrupoInputParameters {

    @XmlElement(required = true)
    protected String nomGrupo;

    /**
     * Gets the value of the nomGrupo property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNomGrupo() {
        return nomGrupo;
    }

    /**
     * Sets the value of the nomGrupo property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNomGrupo(String value) {
        this.nomGrupo = value;
    }

}
