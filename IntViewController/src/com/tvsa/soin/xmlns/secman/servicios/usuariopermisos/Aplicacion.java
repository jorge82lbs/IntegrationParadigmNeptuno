
package com.tvsa.soin.xmlns.secman.servicios.usuariopermisos;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Aplicacion complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Aplicacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdAplicacion" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}IdAplicacion"/>
 *         &lt;element name="NomAplicacion" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}NomAplicacion"/>
 *         &lt;element name="DescAplicacion" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}DescAplicacion"/>
 *         &lt;element name="UrlAplicacion" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}UrlAplicacion"/>
 *         &lt;element name="EstatusAplicacion" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}EstatusAplicacion"/>
 *         &lt;element name="Permisos" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}Permiso" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Aplicacion", propOrder = {
         "idAplicacion", "nomAplicacion", "descAplicacion", "urlAplicacion", "estatusAplicacion", "permisos"
    })
public class Aplicacion {

    @XmlElement(name = "IdAplicacion", required = true)
    protected IdAplicacion idAplicacion;
    @XmlElement(name = "NomAplicacion", required = true)
    protected NomAplicacion nomAplicacion;
    @XmlElement(name = "DescAplicacion", required = true)
    protected DescAplicacion descAplicacion;
    @XmlElement(name = "UrlAplicacion", required = true)
    protected UrlAplicacion urlAplicacion;
    @XmlElement(name = "EstatusAplicacion", required = true)
    protected EstatusAplicacion estatusAplicacion;
    @XmlElement(name = "Permisos", required = true)
    protected List<Permiso> permisos;

    /**
     * Gets the value of the idAplicacion property.
     *
     * @return
     *     possible object is
     *     {@link IdAplicacion }
     *
     */
    public IdAplicacion getIdAplicacion() {
        return idAplicacion;
    }

    /**
     * Sets the value of the idAplicacion property.
     *
     * @param value
     *     allowed object is
     *     {@link IdAplicacion }
     *
     */
    public void setIdAplicacion(IdAplicacion value) {
        this.idAplicacion = value;
    }

    /**
     * Gets the value of the nomAplicacion property.
     *
     * @return
     *     possible object is
     *     {@link NomAplicacion }
     *
     */
    public NomAplicacion getNomAplicacion() {
        return nomAplicacion;
    }

    /**
     * Sets the value of the nomAplicacion property.
     *
     * @param value
     *     allowed object is
     *     {@link NomAplicacion }
     *
     */
    public void setNomAplicacion(NomAplicacion value) {
        this.nomAplicacion = value;
    }

    /**
     * Gets the value of the descAplicacion property.
     *
     * @return
     *     possible object is
     *     {@link DescAplicacion }
     *
     */
    public DescAplicacion getDescAplicacion() {
        return descAplicacion;
    }

    /**
     * Sets the value of the descAplicacion property.
     *
     * @param value
     *     allowed object is
     *     {@link DescAplicacion }
     *
     */
    public void setDescAplicacion(DescAplicacion value) {
        this.descAplicacion = value;
    }

    /**
     * Gets the value of the urlAplicacion property.
     *
     * @return
     *     possible object is
     *     {@link UrlAplicacion }
     *
     */
    public UrlAplicacion getUrlAplicacion() {
        return urlAplicacion;
    }

    /**
     * Sets the value of the urlAplicacion property.
     *
     * @param value
     *     allowed object is
     *     {@link UrlAplicacion }
     *
     */
    public void setUrlAplicacion(UrlAplicacion value) {
        this.urlAplicacion = value;
    }

    /**
     * Gets the value of the estatusAplicacion property.
     *
     * @return
     *     possible object is
     *     {@link EstatusAplicacion }
     *
     */
    public EstatusAplicacion getEstatusAplicacion() {
        return estatusAplicacion;
    }

    /**
     * Sets the value of the estatusAplicacion property.
     *
     * @param value
     *     allowed object is
     *     {@link EstatusAplicacion }
     *
     */
    public void setEstatusAplicacion(EstatusAplicacion value) {
        this.estatusAplicacion = value;
    }

    /**
     * Gets the value of the permisos property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the permisos property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPermisos().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Permiso }
     *
     *
     */
    public List<Permiso> getPermisos() {
        if (permisos == null) {
            permisos = new ArrayList<Permiso>();
        }
        return this.permisos;
    }

}
