
package com.tvsa.soin.xmlns.secman.servicios.usuariogrupos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GrupoUsuarios complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="GrupoUsuarios">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idGrupo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomGrupo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="grupoPadre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomGrupoPadre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idUsuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomUsuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="apPaterno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="apMaterno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numEmpleadoCrm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idOrganizacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomOrganizacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descUsuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomMostrar" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mailUsuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="interno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GrupoUsuarios", propOrder = {
         "idGrupo", "nomGrupo", "grupoPadre", "nomGrupoPadre", "idUsuario", "nomUsuario", "apPaterno", "apMaterno",
         "userName", "numEmpleadoCrm", "idOrganizacion", "nomOrganizacion", "descUsuario", "nomMostrar", "mailUsuario",
         "interno"
    })
public class GrupoUsuarios {

    @XmlElement(required = true)
    protected String idGrupo;
    @XmlElement(required = true)
    protected String nomGrupo;
    @XmlElement(required = true)
    protected String grupoPadre;
    @XmlElement(required = true)
    protected String nomGrupoPadre;
    @XmlElement(required = true)
    protected String idUsuario;
    @XmlElement(required = true)
    protected String nomUsuario;
    @XmlElement(required = true)
    protected String apPaterno;
    @XmlElement(required = true)
    protected String apMaterno;
    @XmlElement(required = true)
    protected String userName;
    @XmlElement(required = true)
    protected String numEmpleadoCrm;
    @XmlElement(required = true)
    protected String idOrganizacion;
    @XmlElement(required = true)
    protected String nomOrganizacion;
    @XmlElement(required = true)
    protected String descUsuario;
    @XmlElement(required = true)
    protected String nomMostrar;
    @XmlElement(required = true)
    protected String mailUsuario;
    @XmlElement(required = true)
    protected String interno;

    /**
     * Gets the value of the idGrupo property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdGrupo() {
        return idGrupo;
    }

    /**
     * Sets the value of the idGrupo property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdGrupo(String value) {
        this.idGrupo = value;
    }

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

    /**
     * Gets the value of the grupoPadre property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getGrupoPadre() {
        return grupoPadre;
    }

    /**
     * Sets the value of the grupoPadre property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setGrupoPadre(String value) {
        this.grupoPadre = value;
    }

    /**
     * Gets the value of the nomGrupoPadre property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNomGrupoPadre() {
        return nomGrupoPadre;
    }

    /**
     * Sets the value of the nomGrupoPadre property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNomGrupoPadre(String value) {
        this.nomGrupoPadre = value;
    }

    /**
     * Gets the value of the idUsuario property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Sets the value of the idUsuario property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdUsuario(String value) {
        this.idUsuario = value;
    }

    /**
     * Gets the value of the nomUsuario property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNomUsuario() {
        return nomUsuario;
    }

    /**
     * Sets the value of the nomUsuario property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNomUsuario(String value) {
        this.nomUsuario = value;
    }

    /**
     * Gets the value of the apPaterno property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getApPaterno() {
        return apPaterno;
    }

    /**
     * Sets the value of the apPaterno property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setApPaterno(String value) {
        this.apPaterno = value;
    }

    /**
     * Gets the value of the apMaterno property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getApMaterno() {
        return apMaterno;
    }

    /**
     * Sets the value of the apMaterno property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setApMaterno(String value) {
        this.apMaterno = value;
    }

    /**
     * Gets the value of the userName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the numEmpleadoCrm property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNumEmpleadoCrm() {
        return numEmpleadoCrm;
    }

    /**
     * Sets the value of the numEmpleadoCrm property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNumEmpleadoCrm(String value) {
        this.numEmpleadoCrm = value;
    }

    /**
     * Gets the value of the idOrganizacion property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdOrganizacion() {
        return idOrganizacion;
    }

    /**
     * Sets the value of the idOrganizacion property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdOrganizacion(String value) {
        this.idOrganizacion = value;
    }

    /**
     * Gets the value of the nomOrganizacion property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNomOrganizacion() {
        return nomOrganizacion;
    }

    /**
     * Sets the value of the nomOrganizacion property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNomOrganizacion(String value) {
        this.nomOrganizacion = value;
    }

    /**
     * Gets the value of the descUsuario property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDescUsuario() {
        return descUsuario;
    }

    /**
     * Sets the value of the descUsuario property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDescUsuario(String value) {
        this.descUsuario = value;
    }

    /**
     * Gets the value of the nomMostrar property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNomMostrar() {
        return nomMostrar;
    }

    /**
     * Sets the value of the nomMostrar property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNomMostrar(String value) {
        this.nomMostrar = value;
    }

    /**
     * Gets the value of the mailUsuario property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getMailUsuario() {
        return mailUsuario;
    }

    /**
     * Sets the value of the mailUsuario property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setMailUsuario(String value) {
        this.mailUsuario = value;
    }

    /**
     * Gets the value of the interno property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getInterno() {
        return interno;
    }

    /**
     * Sets the value of the interno property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setInterno(String value) {
        this.interno = value;
    }

}
