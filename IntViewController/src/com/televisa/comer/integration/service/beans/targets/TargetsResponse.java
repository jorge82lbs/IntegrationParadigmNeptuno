
package com.televisa.comer.integration.service.beans.targets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TargetsResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="TargetsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdRequestTargetsRes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IdService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IdUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="XmlMessageResponse" type="{http://com/televisa/comer/integration/service/beans/targets}XmlMessageResponseCollection"/>
 *         &lt;element name="Error" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetsResponse", propOrder = {
         "idRequestTargetsRes", "idService", "userName", "idUser", "xmlMessageResponse", "error"
    })
public class TargetsResponse {

    @XmlElement(name = "IdRequestTargetsRes", required = true)
    protected String idRequestTargetsRes;
    @XmlElement(name = "IdService", required = true)
    protected String idService;
    @XmlElement(name = "UserName", required = true)
    protected String userName;
    @XmlElement(name = "IdUser", required = true)
    protected String idUser;
    @XmlElement(name = "XmlMessageResponse", required = true)
    protected XmlMessageResponseCollection xmlMessageResponse;
    @XmlElement(name = "Error")
    protected String error;

    /**
     * Gets the value of the idRequestTargetsRes property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdRequestTargetsRes() {
        return idRequestTargetsRes;
    }

    /**
     * Sets the value of the idRequestTargetsRes property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdRequestTargetsRes(String value) {
        this.idRequestTargetsRes = value;
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
     * Gets the value of the xmlMessageResponse property.
     *
     * @return
     *     possible object is
     *     {@link XmlMessageResponseCollection }
     *
     */
    public XmlMessageResponseCollection getXmlMessageResponse() {
        return xmlMessageResponse;
    }

    /**
     * Sets the value of the xmlMessageResponse property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlMessageResponseCollection }
     *
     */
    public void setXmlMessageResponse(XmlMessageResponseCollection value) {
        this.xmlMessageResponse = value;
    }

    /**
     * Gets the value of the error property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setError(String value) {
        this.error = value;
    }

}
