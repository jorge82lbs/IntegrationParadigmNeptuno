
package com.tvsa.soin.xmlns.secman.servicios.usuariopermisos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Usuario complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Usuario">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdUsuario" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}UsuarioId"/>
 *         &lt;element name="NomUsuario" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}NomUsuario"/>
 *         &lt;element name="UserName" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}UserName"/>
 *         &lt;element name="DescUsuario" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}DescUsuario"/>
 *         &lt;element name="NomMostrar" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}NomMostrar"/>
 *         &lt;element name="MailUsuario" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}MailUsuario"/>
 *         &lt;element name="NomOrganizacion" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}NomOrganizacion"/>
 *         &lt;element name="IdOrganizacion" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}IdOrganizacion"/>
 *         &lt;element name="NumEmpleadoCrm" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}NumEmpleadoCrm"/>
 *         &lt;element name="Interno" type="{http://xmlns.soin.tvsa.com/Secman/Servicios/UsuarioPermisos}Interno"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Usuario", propOrder = {
         "idUsuario", "nomUsuario", "userName", "descUsuario", "nomMostrar", "mailUsuario", "nomOrganizacion",
         "idOrganizacion", "numEmpleadoCrm", "interno"
    })
public class Usuario {

    @XmlElement(name = "IdUsuario", required = true)
    protected UsuarioId idUsuario;
    @XmlElement(name = "NomUsuario", required = true)
    protected NomUsuario nomUsuario;
    @XmlElement(name = "UserName", required = true)
    protected UserName userName;
    @XmlElement(name = "DescUsuario", required = true)
    protected DescUsuario descUsuario;
    @XmlElement(name = "NomMostrar", required = true)
    protected NomMostrar nomMostrar;
    @XmlElement(name = "MailUsuario", required = true)
    protected MailUsuario mailUsuario;
    @XmlElement(name = "NomOrganizacion", required = true)
    protected NomOrganizacion nomOrganizacion;
    @XmlElement(name = "IdOrganizacion", required = true)
    protected IdOrganizacion idOrganizacion;
    @XmlElement(name = "NumEmpleadoCrm", required = true)
    protected NumEmpleadoCrm numEmpleadoCrm;
    @XmlElement(name = "Interno", required = true)
    protected Interno interno;

    /**
     * Gets the value of the idUsuario property.
     *
     * @return
     *     possible object is
     *     {@link UsuarioId }
     *
     */
    public UsuarioId getIdUsuario() {
        return idUsuario;
    }

    /**
     * Sets the value of the idUsuario property.
     *
     * @param value
     *     allowed object is
     *     {@link UsuarioId }
     *
     */
    public void setIdUsuario(UsuarioId value) {
        this.idUsuario = value;
    }

    /**
     * Gets the value of the nomUsuario property.
     *
     * @return
     *     possible object is
     *     {@link NomUsuario }
     *
     */
    public NomUsuario getNomUsuario() {
        return nomUsuario;
    }

    /**
     * Sets the value of the nomUsuario property.
     *
     * @param value
     *     allowed object is
     *     {@link NomUsuario }
     *
     */
    public void setNomUsuario(NomUsuario value) {
        this.nomUsuario = value;
    }

    /**
     * Gets the value of the userName property.
     *
     * @return
     *     possible object is
     *     {@link UserName }
     *
     */
    public UserName getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     *
     * @param value
     *     allowed object is
     *     {@link UserName }
     *
     */
    public void setUserName(UserName value) {
        this.userName = value;
    }

    /**
     * Gets the value of the descUsuario property.
     *
     * @return
     *     possible object is
     *     {@link DescUsuario }
     *
     */
    public DescUsuario getDescUsuario() {
        return descUsuario;
    }

    /**
     * Sets the value of the descUsuario property.
     *
     * @param value
     *     allowed object is
     *     {@link DescUsuario }
     *
     */
    public void setDescUsuario(DescUsuario value) {
        this.descUsuario = value;
    }

    /**
     * Gets the value of the nomMostrar property.
     *
     * @return
     *     possible object is
     *     {@link NomMostrar }
     *
     */
    public NomMostrar getNomMostrar() {
        return nomMostrar;
    }

    /**
     * Sets the value of the nomMostrar property.
     *
     * @param value
     *     allowed object is
     *     {@link NomMostrar }
     *
     */
    public void setNomMostrar(NomMostrar value) {
        this.nomMostrar = value;
    }

    /**
     * Gets the value of the mailUsuario property.
     *
     * @return
     *     possible object is
     *     {@link MailUsuario }
     *
     */
    public MailUsuario getMailUsuario() {
        return mailUsuario;
    }

    /**
     * Sets the value of the mailUsuario property.
     *
     * @param value
     *     allowed object is
     *     {@link MailUsuario }
     *
     */
    public void setMailUsuario(MailUsuario value) {
        this.mailUsuario = value;
    }

    /**
     * Gets the value of the nomOrganizacion property.
     *
     * @return
     *     possible object is
     *     {@link NomOrganizacion }
     *
     */
    public NomOrganizacion getNomOrganizacion() {
        return nomOrganizacion;
    }

    /**
     * Sets the value of the nomOrganizacion property.
     *
     * @param value
     *     allowed object is
     *     {@link NomOrganizacion }
     *
     */
    public void setNomOrganizacion(NomOrganizacion value) {
        this.nomOrganizacion = value;
    }

    /**
     * Gets the value of the idOrganizacion property.
     *
     * @return
     *     possible object is
     *     {@link IdOrganizacion }
     *
     */
    public IdOrganizacion getIdOrganizacion() {
        return idOrganizacion;
    }

    /**
     * Sets the value of the idOrganizacion property.
     *
     * @param value
     *     allowed object is
     *     {@link IdOrganizacion }
     *
     */
    public void setIdOrganizacion(IdOrganizacion value) {
        this.idOrganizacion = value;
    }

    /**
     * Gets the value of the numEmpleadoCrm property.
     *
     * @return
     *     possible object is
     *     {@link NumEmpleadoCrm }
     *
     */
    public NumEmpleadoCrm getNumEmpleadoCrm() {
        return numEmpleadoCrm;
    }

    /**
     * Sets the value of the numEmpleadoCrm property.
     *
     * @param value
     *     allowed object is
     *     {@link NumEmpleadoCrm }
     *
     */
    public void setNumEmpleadoCrm(NumEmpleadoCrm value) {
        this.numEmpleadoCrm = value;
    }

    /**
     * Gets the value of the interno property.
     *
     * @return
     *     possible object is
     *     {@link Interno }
     *
     */
    public Interno getInterno() {
        return interno;
    }

    /**
     * Sets the value of the interno property.
     *
     * @param value
     *     allowed object is
     *     {@link Interno }
     *
     */
    public void setInterno(Interno value) {
        this.interno = value;
    }

}
