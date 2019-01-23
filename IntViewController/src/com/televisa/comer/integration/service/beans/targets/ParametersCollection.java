
package com.televisa.comer.integration.service.beans.targets;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParametersCollection complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ParametersCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ParameterMap" type="{http://com/televisa/comer/integration/service/beans/targets}ParameterMap" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParametersCollection", propOrder = { "parameterMap" })
public class ParametersCollection {

    @XmlElement(name = "ParameterMap", required = true)
    protected List<ParameterMap> parameterMap;

    /**
     * Gets the value of the parameterMap property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameterMap property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameterMap().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParameterMap }
     *
     *
     */
    public List<ParameterMap> getParameterMap() {
        if (parameterMap == null) {
            parameterMap = new ArrayList<ParameterMap>();
        }
        return this.parameterMap;
    }

}
