
package com.televisa.comer.integration.service.beans.logcertificado;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChannelsLcertCollection complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ChannelsLcertCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChannelsLcert" type="{http://com/televisa/comer/integration/service/beans/logcertificado}ChannelLcert" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChannelsLcertCollection", propOrder = { "channelsLcert" })
public class ChannelsLcertCollection {

    @XmlElement(name = "ChannelsLcert", required = true)
    protected List<ChannelLcert> channelsLcert;

    /**
     * Gets the value of the channelsLcert property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the channelsLcert property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChannelsLcert().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChannelLcert }
     *
     *
     */
    public List<ChannelLcert> getChannelsLcert() {
        if (channelsLcert == null) {
            channelsLcert = new ArrayList<ChannelLcert>();
        }
        return this.channelsLcert;
    }

}
