
package com.televisa.comer.integration.service.beans.logcertificado;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LogCertificadoInputParameters complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LogCertificadoInputParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdRequestLogCertificadoReq" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IdService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IdUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DateQueryLcert" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ChannelLcertList" type="{http://com/televisa/comer/integration/service/beans/logcertificado}ChannelsLcertCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LogCertificadoInputParameters", propOrder = {
         "idRequestLogCertificadoReq", "idService", "userName", "idUser", "dateQueryLcert", "channelLcertList"
    })
public class LogCertificadoInputParameters {

    @XmlElement(name = "IdRequestLogCertificadoReq", required = true)
    protected String idRequestLogCertificadoReq;
    @XmlElement(name = "IdService", required = true)
    protected String idService;
    @XmlElement(name = "UserName", required = true)
    protected String userName;
    @XmlElement(name = "IdUser", required = true)
    protected String idUser;
    @XmlElement(name = "DateQueryLcert", required = true)
    protected String dateQueryLcert;
    @XmlElement(name = "ChannelLcertList", required = true)
    protected ChannelsLcertCollection channelLcertList;

    /**
     * Gets the value of the idRequestLogCertificadoReq property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdRequestLogCertificadoReq() {
        return idRequestLogCertificadoReq;
    }

    /**
     * Sets the value of the idRequestLogCertificadoReq property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdRequestLogCertificadoReq(String value) {
        this.idRequestLogCertificadoReq = value;
    }

    /**
     * Gets the value of the idService property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdService() {
        return idService;
    }

    /**
     * Sets the value of the idService property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdService(String value) {
        this.idService = value;
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
     * Gets the value of the idUser property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * Sets the value of the idUser property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdUser(String value) {
        this.idUser = value;
    }

    /**
     * Gets the value of the dateQueryLcert property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDateQueryLcert() {
        return dateQueryLcert;
    }

    /**
     * Sets the value of the dateQueryLcert property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDateQueryLcert(String value) {
        this.dateQueryLcert = value;
    }

    /**
     * Gets the value of the channelLcertList property.
     *
     * @return
     *     possible object is
     *     {@link ChannelsLcertCollection }
     *
     */
    public ChannelsLcertCollection getChannelLcertList() {
        return channelLcertList;
    }

    /**
     * Sets the value of the channelLcertList property.
     *
     * @param value
     *     allowed object is
     *     {@link ChannelsLcertCollection }
     *
     */
    public void setChannelLcertList(ChannelsLcertCollection value) {
        this.channelLcertList = value;
    }

}
