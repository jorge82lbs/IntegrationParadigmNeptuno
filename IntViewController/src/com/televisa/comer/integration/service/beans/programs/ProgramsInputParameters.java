
package com.televisa.comer.integration.service.beans.programs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProgramsInputParameters complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ProgramsInputParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdRequestProgramsReq" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IdService" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UserName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IdUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DateQuery" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ChannelList" type="{http://com/televisa/comer/integration/service/beans/programs}ChannelsCollection"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProgramsInputParameters", propOrder = {
         "idRequestProgramsReq", "idService", "userName", "idUser", "dateQuery", "channelList"
    })
public class ProgramsInputParameters {

    @XmlElement(name = "IdRequestProgramsReq", required = true)
    protected String idRequestProgramsReq;
    @XmlElement(name = "IdService", required = true)
    protected String idService;
    @XmlElement(name = "UserName", required = true)
    protected String userName;
    @XmlElement(name = "IdUser", required = true)
    protected String idUser;
    @XmlElement(name = "DateQuery", required = true)
    protected String dateQuery;
    @XmlElement(name = "ChannelList", required = true)
    protected ChannelsCollection channelList;

    /**
     * Gets the value of the idRequestProgramsReq property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIdRequestProgramsReq() {
        return idRequestProgramsReq;
    }

    /**
     * Sets the value of the idRequestProgramsReq property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIdRequestProgramsReq(String value) {
        this.idRequestProgramsReq = value;
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
     * Gets the value of the dateQuery property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDateQuery() {
        return dateQuery;
    }

    /**
     * Sets the value of the dateQuery property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDateQuery(String value) {
        this.dateQuery = value;
    }

    /**
     * Gets the value of the channelList property.
     *
     * @return
     *     possible object is
     *     {@link ChannelsCollection }
     *
     */
    public ChannelsCollection getChannelList() {
        return channelList;
    }

    /**
     * Sets the value of the channelList property.
     *
     * @param value
     *     allowed object is
     *     {@link ChannelsCollection }
     *
     */
    public void setChannelList(ChannelsCollection value) {
        this.channelList = value;
    }

}
