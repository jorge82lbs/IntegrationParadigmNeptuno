
package com.televisa.comer.integration.service.beans.logcertificado;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for XmlMessageLcertResponseCollection complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="XmlMessageLcertResponseCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="XmlMessageLcertReqRes" type="{http://com/televisa/comer/integration/service/beans/logcertificado}XmlMessageLcertReqRes" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "XmlMessageLcertResponseCollection", propOrder = { "xmlMessageLcertReqRes" })
public class XmlMessageLcertResponseCollection {

    @XmlElement(name = "XmlMessageLcertReqRes", required = true)
    protected List<XmlMessageLcertReqRes> xmlMessageLcertReqRes;

    /**
     * Gets the value of the xmlMessageLcertReqRes property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the xmlMessageLcertReqRes property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getXmlMessageLcertReqRes().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XmlMessageLcertReqRes }
     *
     *
     */
    public List<XmlMessageLcertReqRes> getXmlMessageLcertReqRes() {
        if (xmlMessageLcertReqRes == null) {
            xmlMessageLcertReqRes = new ArrayList<XmlMessageLcertReqRes>();
        }
        return this.xmlMessageLcertReqRes;
    }

}
