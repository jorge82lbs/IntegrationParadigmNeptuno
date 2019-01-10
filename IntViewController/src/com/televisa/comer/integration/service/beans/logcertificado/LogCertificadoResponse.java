
package com.televisa.comer.integration.service.beans.logcertificado;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LogCertificadoResponse complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="LogCertificadoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdRequestLogCertificadoRes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="XmlMessageResponseLcert" type="{http://com/televisa/comer/integration/service/beans/logcertificado}XmlMessageLcertResponseCollection"/>
 *         &lt;element name="ErrorLcert" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LogCertificadoResponse", propOrder = {
         "idRequestLogCertificadoRes", "xmlMessageResponseLcert", "errorLcert" })
public class LogCertificadoResponse {

    @XmlElement(name = "IdRequestLogCertificadoRes", required = true)
    protected String idRequestLogCertificadoRes;
    @XmlElement(name = "XmlMessageResponseLcert", required = true)
    protected XmlMessageLcertResponseCollection xmlMessageResponseLcert;
    @XmlElement(name = "ErrorLcert")
    protected String errorLcert;

    /**
     * Gets the value of the idRequestLogCertificadoRes property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdRequestLogCertificadoRes() {
        return idRequestLogCertificadoRes;
    }

    /**
     * Sets the value of the idRequestLogCertificadoRes property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdRequestLogCertificadoRes(String value) {
        this.idRequestLogCertificadoRes = value;
    }

    /**
     * Gets the value of the xmlMessageResponseLcert property.
     *
     * @return
     *     possible object is
     *     {@link XmlMessageLcertResponseCollection }
     *
     */
    public XmlMessageLcertResponseCollection getXmlMessageResponseLcert() {
        return xmlMessageResponseLcert;
    }

    /**
     * Sets the value of the xmlMessageResponseLcert property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlMessageLcertResponseCollection }
     *
     */
    public void setXmlMessageResponseLcert(XmlMessageLcertResponseCollection value) {
        this.xmlMessageResponseLcert = value;
    }

    /**
     * Gets the value of the errorLcert property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getErrorLcert() {
        return errorLcert;
    }

    /**
     * Sets the value of the errorLcert property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setErrorLcert(String value) {
        this.errorLcert = value;
    }

}
