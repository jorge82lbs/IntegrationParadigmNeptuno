package es.com.evendor.types;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parrilla">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Mode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CodeTrace" type="{http://www.w3.org/2001/XMLSchema}string"/>*                   
 *                   &lt;element name="Schedule">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ChannelID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Dates">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="TimeBand">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="DateValue" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                                 &lt;element name="HourStart" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="HourEnd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="Items">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="Item">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="EpisodeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="EpNo" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                                                     &lt;element name="SlotDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="TitleID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                                                     &lt;element name="EpisodeNameID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                                                     &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="BuyUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="exceptCofepris" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="EventSpecial" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="EventBest" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="GeneroID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="GeneroName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                   &lt;/sequence>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                                 &lt;element name="Cortes">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="Corte">
 *                                                             &lt;complexType>
 *                                                               &lt;complexContent>
 *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                   &lt;sequence>
 *                                                                     &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                                                     &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="CorteID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                                                     &lt;element name="DesCorte" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="Overlay" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                     &lt;element name="Breaks">
 *                                                                       &lt;complexType>
 *                                                                         &lt;complexContent>
 *                                                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                             &lt;sequence>
 *                                                                               &lt;element name="Break" maxOccurs="unbounded">
 *                                                                                 &lt;complexType>
 *                                                                                   &lt;complexContent>
 *                                                                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                                                       &lt;sequence>
 *                                                                                         &lt;element name="BreakID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                                                                         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                                         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                                                                         &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                                         &lt;element name="TotalDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                                         &lt;element name="ComercialDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                                         &lt;element name="TypeBreak" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                                                       &lt;/sequence>
 *                                                                                     &lt;/restriction>
 *                                                                                   &lt;/complexContent>
 *                                                                                 &lt;/complexType>
 *                                                                               &lt;/element>
 *                                                                             &lt;/sequence>
 *                                                                           &lt;/restriction>
 *                                                                         &lt;/complexContent>
 *                                                                       &lt;/complexType>
 *                                                                     &lt;/element>
 *                                                                   &lt;/sequence>
 *                                                                 &lt;/restriction>
 *                                                               &lt;/complexContent>
 *                                                             &lt;/complexType>
 *                                                           &lt;/element>
 *                                                         &lt;/sequence>
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "parrilla" })
@XmlRootElement(name = "RAIZ")
public class RAIZ {

    @XmlElement(required = true)
    protected RAIZ.Parrilla parrilla;

    /**
     * Gets the value of the parrilla property.
     *
     * @return
     *     possible object is
     *     {@link RAIZ.Parrilla }
     *
     */
    public RAIZ.Parrilla getParrilla() {
        return parrilla;
    }

    /**
     * Sets the value of the parrilla property.
     *
     * @param value
     *     allowed object is
     *     {@link RAIZ.Parrilla }
     *
     */
    public void setParrilla(RAIZ.Parrilla value) {
        this.parrilla = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Mode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CodeTrace" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Schedule">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ChannelID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Dates">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="TimeBand">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="DateValue" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                                       &lt;element name="HourStart" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="HourEnd" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="Items">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="Item">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="EpisodeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="EpNo" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                                           &lt;element name="SlotDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="TitleID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                                           &lt;element name="EpisodeNameID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                                           &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="BuyUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="exceptCofepris" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="EventSpecial" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="EventBest" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="GeneroID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="GeneroName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                         &lt;/sequence>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                       &lt;element name="Cortes">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="Corte">
     *                                                   &lt;complexType>
     *                                                     &lt;complexContent>
     *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                         &lt;sequence>
     *                                                           &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                                                           &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="CorteID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                                           &lt;element name="DesCorte" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="Overlay" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                           &lt;element name="Breaks">
     *                                                             &lt;complexType>
     *                                                               &lt;complexContent>
     *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                                   &lt;sequence>
     *                                                                     &lt;element name="Break" maxOccurs="unbounded">
     *                                                                       &lt;complexType>
     *                                                                         &lt;complexContent>
     *                                                                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                                                             &lt;sequence>
     *                                                                               &lt;element name="BreakID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                                                                               &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                                               &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                                                                               &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                                               &lt;element name="TotalDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                                               &lt;element name="ComercialDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                                               &lt;element name="TypeBreak" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                                             &lt;/sequence>
     *                                                                           &lt;/restriction>
     *                                                                         &lt;/complexContent>
     *                                                                       &lt;/complexType>
     *                                                                     &lt;/element>
     *                                                                   &lt;/sequence>
     *                                                                 &lt;/restriction>
     *                                                               &lt;/complexContent>
     *                                                             &lt;/complexType>
     *                                                           &lt;/element>
     *                                                         &lt;/sequence>
     *                                                       &lt;/restriction>
     *                                                     &lt;/complexContent>
     *                                                   &lt;/complexType>
     *                                                 &lt;/element>
     *                                               &lt;/sequence>
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "mode", "codeTrace" ,"schedule" })
    public static class Parrilla {

        @XmlElement(name = "Mode", required = true)
        protected String mode;
        @XmlElement(name = "CodeTrace")
        protected String codeTrace;
        @XmlElement(name = "Schedule", required = true)
        protected RAIZ.Parrilla.Schedule schedule;

        /**
         * Gets the value of the mode property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getMode() {
            return mode;
        }

        /**
         * Sets the value of the mode property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setMode(String value) {
            this.mode = value;
        }
        
        
        /**
         * Gets the value of the codeTrace property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getCodeTrace() {
            return codeTrace;
        }

        /**
         * Sets the value of the codeTrace property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setCodeTrace(String value) {
            this.codeTrace = value;
        }

        /**
         * Gets the value of the schedule property.
         *
         * @return
         *     possible object is
         *     {@link RAIZ.Parrilla.Schedule }
         *
         */
        public RAIZ.Parrilla.Schedule getSchedule() {
            return schedule;
        }

        /**
         * Sets the value of the schedule property.
         *
         * @param value
         *     allowed object is
         *     {@link RAIZ.Parrilla.Schedule }
         *
         */
        public void setSchedule(RAIZ.Parrilla.Schedule value) {
            this.schedule = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         *
         * <p>The following schema fragment specifies the expected content contained within this class.
         *
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="ChannelID" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Dates">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="TimeBand">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="DateValue" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *                             &lt;element name="HourStart" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="HourEnd" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="Items">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="Item">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="EpisodeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="EpNo" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                                                 &lt;element name="SlotDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="TitleID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                                                 &lt;element name="EpisodeNameID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                                                 &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="BuyUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="exceptCofepris" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="EventSpecial" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="EventBest" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="GeneroID" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="GeneroName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                               &lt;/sequence>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                             &lt;element name="Cortes">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="Corte">
         *                                         &lt;complexType>
         *                                           &lt;complexContent>
         *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                               &lt;sequence>
         *                                                 &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *                                                 &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="CorteID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                                                 &lt;element name="DesCorte" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="Overlay" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                 &lt;element name="Breaks">
         *                                                   &lt;complexType>
         *                                                     &lt;complexContent>
         *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                                         &lt;sequence>
         *                                                           &lt;element name="Break" maxOccurs="unbounded">
         *                                                             &lt;complexType>
         *                                                               &lt;complexContent>
         *                                                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                                                   &lt;sequence>
         *                                                                     &lt;element name="BreakID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *                                                                     &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                                     &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *                                                                     &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                                     &lt;element name="TotalDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                                     &lt;element name="ComercialDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                                     &lt;element name="TypeBreak" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                                                   &lt;/sequence>
         *                                                                 &lt;/restriction>
         *                                                               &lt;/complexContent>
         *                                                             &lt;/complexType>
         *                                                           &lt;/element>
         *                                                         &lt;/sequence>
         *                                                       &lt;/restriction>
         *                                                     &lt;/complexContent>
         *                                                   &lt;/complexType>
         *                                                 &lt;/element>
         *                                               &lt;/sequence>
         *                                             &lt;/restriction>
         *                                           &lt;/complexContent>
         *                                         &lt;/complexType>
         *                                       &lt;/element>
         *                                     &lt;/sequence>
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         *
         *
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "channelID", "dates" })
        public static class Schedule {

            @XmlElement(name = "ChannelID", required = true)
            protected String channelID;
            @XmlElement(name = "Dates", required = true)
            protected RAIZ.Parrilla.Schedule.Dates dates;

            /**
             * Gets the value of the channelID property.
             *
             * @return
             *     possible object is
             *     {@link String }
             *
             */
            public String getChannelID() {
                return channelID;
            }

            /**
             * Sets the value of the channelID property.
             *
             * @param value
             *     allowed object is
             *     {@link String }
             *
             */
            public void setChannelID(String value) {
                this.channelID = value;
            }

            /**
             * Gets the value of the dates property.
             *
             * @return
             *     possible object is
             *     {@link RAIZ.Parrilla.Schedule.Dates }
             *
             */
            public RAIZ.Parrilla.Schedule.Dates getDates() {
                return dates;
            }

            /**
             * Sets the value of the dates property.
             *
             * @param value
             *     allowed object is
             *     {@link RAIZ.Parrilla.Schedule.Dates }
             *
             */
            public void setDates(RAIZ.Parrilla.Schedule.Dates value) {
                this.dates = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             *
             * <p>The following schema fragment specifies the expected content contained within this class.
             *
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="TimeBand">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="DateValue" type="{http://www.w3.org/2001/XMLSchema}date"/>
             *                   &lt;element name="HourStart" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="HourEnd" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="Items">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="Item">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="EpisodeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="EpNo" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                                       &lt;element name="SlotDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="TitleID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                                       &lt;element name="EpisodeNameID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                                       &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="BuyUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="exceptCofepris" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="EventSpecial" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="EventBest" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="GeneroID" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="GeneroName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                     &lt;/sequence>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                   &lt;element name="Cortes">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                           &lt;sequence>
             *                             &lt;element name="Corte">
             *                               &lt;complexType>
             *                                 &lt;complexContent>
             *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                     &lt;sequence>
             *                                       &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
             *                                       &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="CorteID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                                       &lt;element name="DesCorte" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="Overlay" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                       &lt;element name="Breaks">
             *                                         &lt;complexType>
             *                                           &lt;complexContent>
             *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                               &lt;sequence>
             *                                                 &lt;element name="Break" maxOccurs="unbounded">
             *                                                   &lt;complexType>
             *                                                     &lt;complexContent>
             *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                                                         &lt;sequence>
             *                                                           &lt;element name="BreakID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
             *                                                           &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                                           &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
             *                                                           &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                                           &lt;element name="TotalDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                                           &lt;element name="ComercialDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                                           &lt;element name="TypeBreak" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                                                         &lt;/sequence>
             *                                                       &lt;/restriction>
             *                                                     &lt;/complexContent>
             *                                                   &lt;/complexType>
             *                                                 &lt;/element>
             *                                               &lt;/sequence>
             *                                             &lt;/restriction>
             *                                           &lt;/complexContent>
             *                                         &lt;/complexType>
             *                                       &lt;/element>
             *                                     &lt;/sequence>
             *                                   &lt;/restriction>
             *                                 &lt;/complexContent>
             *                               &lt;/complexType>
             *                             &lt;/element>
             *                           &lt;/sequence>
             *                         &lt;/restriction>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "timeBand" })
            public static class Dates {

                @XmlElement(name = "TimeBand", required = true)
                protected List<RAIZ.Parrilla.Schedule.Dates.TimeBand> timeBand;

                /**
                 * Gets the value of the timeBand property.
                 *
                 * @return
                 *     possible object is
                 *     {@link RAIZ.Parrilla.Schedule.Dates.TimeBand }
                 *
                 */
                public List<RAIZ.Parrilla.Schedule.Dates.TimeBand> getTimeBand() {
                    if (timeBand == null) {
                        timeBand =
                            new ArrayList<RAIZ.Parrilla.Schedule.Dates.TimeBand>();
                    }
                    return this.timeBand;
                }

                /**
                 * Sets the value of the timeBand property.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link RAIZ.Parrilla.Schedule.Dates.TimeBand }
                 *
                 */
                /*public void setTimeBand(RAIZ.Parrilla.Schedule.Dates.TimeBand value) {
                    this.timeBand = value;
                }*/


                /**
                 * <p>Java class for anonymous complex type.
                 *
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 *
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="DateValue" type="{http://www.w3.org/2001/XMLSchema}date"/>
                 *         &lt;element name="HourStart" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="HourEnd" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="Items">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="Item">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="EpisodeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="EpNo" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                 *                             &lt;element name="SlotDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="TitleID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                 *                             &lt;element name="EpisodeNameID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                 *                             &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="BuyUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="exceptCofepris" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="EventSpecial" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="EventBest" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="GeneroID" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="GeneroName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                           &lt;/sequence>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *         &lt;element name="Cortes">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                 &lt;sequence>
                 *                   &lt;element name="Corte">
                 *                     &lt;complexType>
                 *                       &lt;complexContent>
                 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                           &lt;sequence>
                 *                             &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
                 *                             &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="CorteID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                 *                             &lt;element name="DesCorte" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="Overlay" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                             &lt;element name="Breaks">
                 *                               &lt;complexType>
                 *                                 &lt;complexContent>
                 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                                     &lt;sequence>
                 *                                       &lt;element name="Break" maxOccurs="unbounded">
                 *                                         &lt;complexType>
                 *                                           &lt;complexContent>
                 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *                                               &lt;sequence>
                 *                                                 &lt;element name="BreakID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                 *                                                 &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                                                 &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
                 *                                                 &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                                                 &lt;element name="TotalDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                                                 &lt;element name="ComercialDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                                                 &lt;element name="TypeBreak" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *                                               &lt;/sequence>
                 *                                             &lt;/restriction>
                 *                                           &lt;/complexContent>
                 *                                         &lt;/complexType>
                 *                                       &lt;/element>
                 *                                     &lt;/sequence>
                 *                                   &lt;/restriction>
                 *                                 &lt;/complexContent>
                 *                               &lt;/complexType>
                 *                             &lt;/element>
                 *                           &lt;/sequence>
                 *                         &lt;/restriction>
                 *                       &lt;/complexContent>
                 *                     &lt;/complexType>
                 *                   &lt;/element>
                 *                 &lt;/sequence>
                 *               &lt;/restriction>
                 *             &lt;/complexContent>
                 *           &lt;/complexType>
                 *         &lt;/element>
                 *       &lt;/sequence>
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 *
                 *
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                         "dateValue", "hourStart", "hourEnd", "items", "cortes"
                    })
                public static class TimeBand {

                    @XmlElement(name = "DateValue", required = true)
                    @XmlSchemaType(name = "date")
                    protected String dateValue;
                    @XmlElement(name = "HourStart", required = true)
                    protected String hourStart;
                    @XmlElement(name = "HourEnd", required = true)
                    protected String hourEnd;
                    @XmlElement(name = "Items", required = true)
                    protected RAIZ.Parrilla.Schedule.Dates.TimeBand.Items items;
                    @XmlElement(name = "Cortes", required = true)
                    protected RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes cortes;

                    /**
                     * Gets the value of the dateValue property.
                     *
                     * @return
                     *     possible object is
                     *     {@link XMLGregorianCalendar }
                     *
                     */
                    public String getDateValue() {
                        return dateValue;
                    }

                    /**
                     * Sets the value of the dateValue property.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link XMLGregorianCalendar }
                     *
                     */
                    public void setDateValue(String value) {
                        this.dateValue = value;
                    }

                    /**
                     * Gets the value of the hourStart property.
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getHourStart() {
                        return hourStart;
                    }

                    /**
                     * Sets the value of the hourStart property.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setHourStart(String value) {
                        this.hourStart = value;
                    }

                    /**
                     * Gets the value of the hourEnd property.
                     *
                     * @return
                     *     possible object is
                     *     {@link String }
                     *
                     */
                    public String getHourEnd() {
                        return hourEnd;
                    }

                    /**
                     * Sets the value of the hourEnd property.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *
                     */
                    public void setHourEnd(String value) {
                        this.hourEnd = value;
                    }

                    /**
                     * Gets the value of the items property.
                     *
                     * @return
                     *     possible object is
                     *     {@link RAIZ.Parrilla.Schedule.Dates.TimeBand.Items }
                     *
                     */
                    public RAIZ.Parrilla.Schedule.Dates.TimeBand.Items getItems() {
                        return items;
                    }

                    /**
                     * Sets the value of the items property.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link RAIZ.Parrilla.Schedule.Dates.TimeBand.Items }
                     *
                     */
                    public void setItems(RAIZ.Parrilla.Schedule.Dates.TimeBand.Items value) {
                        this.items = value;
                    }

                    /**
                     * Gets the value of the cortes property.
                     *
                     * @return
                     *     possible object is
                     *     {@link RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes }
                     *
                     */
                    public RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes getCortes() {
                        return cortes;
                    }

                    /**
                     * Sets the value of the cortes property.
                     *
                     * @param value
                     *     allowed object is
                     *     {@link RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes }
                     *
                     */
                    public void setCortes(RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes value) {
                        this.cortes = value;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     *
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     *
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="Corte">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
                     *                   &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="CorteID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                     *                   &lt;element name="DesCorte" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="Overlay" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="Breaks">
                     *                     &lt;complexType>
                     *                       &lt;complexContent>
                     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                           &lt;sequence>
                     *                             &lt;element name="Break" maxOccurs="unbounded">
                     *                               &lt;complexType>
                     *                                 &lt;complexContent>
                     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                                     &lt;sequence>
                     *                                       &lt;element name="BreakID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                     *                                       &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                                       &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
                     *                                       &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                                       &lt;element name="TotalDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                                       &lt;element name="ComercialDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                                       &lt;element name="TypeBreak" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                                     &lt;/sequence>
                     *                                   &lt;/restriction>
                     *                                 &lt;/complexContent>
                     *                               &lt;/complexType>
                     *                             &lt;/element>
                     *                           &lt;/sequence>
                     *                         &lt;/restriction>
                     *                       &lt;/complexContent>
                     *                     &lt;/complexType>
                     *                   &lt;/element>
                     *                 &lt;/sequence>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     *
                     *
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = { "corte" })
                    public static class Cortes {

                        @XmlElement(name = "Corte", required = true)
                        protected List<RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte> corte;

                        /**
                         * Gets the value of the corte property.
                         *
                         * @return
                         *     possible object is
                         *     {@link RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte }
                         *
                         */
                        public List<RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte> getCorte() {
                            if (corte == null) {
                                corte =
                                    new ArrayList<RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte>();
                            }
                            return this.corte;
                        }

                        /**
                         * Sets the value of the corte property.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte }
                         *
                         */
                        /*public void setCorte(RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte value) {
                            this.corte = value;
                        }*/


                        /**
                         * <p>Java class for anonymous complex type.
                         *
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         *
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;sequence>
                         *         &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
                         *         &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="CorteID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                         *         &lt;element name="DesCorte" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="Overlay" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="Breaks">
                         *           &lt;complexType>
                         *             &lt;complexContent>
                         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *                 &lt;sequence>
                         *                   &lt;element name="Break" maxOccurs="unbounded">
                         *                     &lt;complexType>
                         *                       &lt;complexContent>
                         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *                           &lt;sequence>
                         *                             &lt;element name="BreakID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                         *                             &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *                             &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
                         *                             &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *                             &lt;element name="TotalDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *                             &lt;element name="ComercialDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *                             &lt;element name="TypeBreak" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *                           &lt;/sequence>
                         *                         &lt;/restriction>
                         *                       &lt;/complexContent>
                         *                     &lt;/complexType>
                         *                   &lt;/element>
                         *                 &lt;/sequence>
                         *               &lt;/restriction>
                         *             &lt;/complexContent>
                         *           &lt;/complexType>
                         *         &lt;/element>
                         *       &lt;/sequence>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         *
                         *
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                                 "buyUnitID", "date", "hour", "corteID",
                                 "desCorte", "overlay", "breaks"
                            })
                        public static class Corte {

                            @XmlElement(name = "BuyUnitID", required = true)
                            protected String buyUnitID;
                            @XmlElement(name = "Date", required = true)
                            @XmlSchemaType(name = "date")
                            protected String date;
                            @XmlElement(name = "Hour", required = true)
                            protected String hour;
                            @XmlElement(name = "CorteID", required = true)
                            protected String corteID;
                            @XmlElement(name = "DesCorte", required = true)
                            protected String desCorte;
                            @XmlElement(name = "Overlay", required = true)
                            protected String overlay;
                            @XmlElement(name = "Breaks", required = true)
                            protected RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte.Breaks breaks;

                            /**
                             * Gets the value of the buyUnitID property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getBuyUnitID() {
                                return buyUnitID;
                            }

                            /**
                             * Sets the value of the buyUnitID property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setBuyUnitID(String value) {
                                this.buyUnitID = value;
                            }

                            /**
                             * Gets the value of the date property.
                             *
                             * @return
                             *     possible object is
                             *     {@link XMLGregorianCalendar }
                             *
                             */
                            public String getDate() {
                                return date;
                            }

                            /**
                             * Sets the value of the date property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link XMLGregorianCalendar }
                             *
                             */
                            public void setDate(String value) {
                                this.date = value;
                            }

                            /**
                             * Gets the value of the hour property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getHour() {
                                return hour;
                            }

                            /**
                             * Sets the value of the hour property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setHour(String value) {
                                this.hour = value;
                            }

                            /**
                             * Gets the value of the corteID property.
                             *
                             * @return
                             *     possible object is
                             *     {@link BigInteger }
                             *
                             */
                            public String getCorteID() {
                                return corteID;
                            }

                            /**
                             * Sets the value of the corteID property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link BigInteger }
                             *
                             */
                            public void setCorteID(String value) {
                                this.corteID = value;
                            }

                            /**
                             * Gets the value of the desCorte property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getDesCorte() {
                                return desCorte;
                            }

                            /**
                             * Sets the value of the desCorte property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setDesCorte(String value) {
                                this.desCorte = value;
                            }

                            /**
                             * Gets the value of the overlay property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getOverlay() {
                                return overlay;
                            }

                            /**
                             * Sets the value of the overlay property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setOverlay(String value) {
                                this.overlay = value;
                            }

                            /**
                             * Gets the value of the breaks property.
                             *
                             * @return
                             *     possible object is
                             *     {@link RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte.Breaks }
                             *
                             */
                            public RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte.Breaks getBreaks() {
                                return breaks;
                            }

                            /**
                             * Sets the value of the breaks property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte.Breaks }
                             *
                             */
                            public void setBreaks(RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte.Breaks value) {
                                this.breaks = value;
                            }


                            /**
                             * <p>Java class for anonymous complex type.
                             *
                             * <p>The following schema fragment specifies the expected content contained within this class.
                             *
                             * <pre>
                             * &lt;complexType>
                             *   &lt;complexContent>
                             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                             *       &lt;sequence>
                             *         &lt;element name="Break" maxOccurs="unbounded">
                             *           &lt;complexType>
                             *             &lt;complexContent>
                             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                             *                 &lt;sequence>
                             *                   &lt;element name="BreakID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                             *                   &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
                             *                   &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
                             *                   &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
                             *                   &lt;element name="TotalDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
                             *                   &lt;element name="ComercialDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
                             *                   &lt;element name="TypeBreak" type="{http://www.w3.org/2001/XMLSchema}string"/>
                             *                 &lt;/sequence>
                             *               &lt;/restriction>
                             *             &lt;/complexContent>
                             *           &lt;/complexType>
                             *         &lt;/element>
                             *       &lt;/sequence>
                             *     &lt;/restriction>
                             *   &lt;/complexContent>
                             * &lt;/complexType>
                             * </pre>
                             *
                             *
                             */
                            @XmlAccessorType(XmlAccessType.FIELD)
                            @XmlType(name = "", propOrder = { "_break" })
                            public static class Breaks {

                                @XmlElement(name = "Break", required = true)
                                protected List<RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte.Breaks.Break> _break;

                                /**
                                 * Gets the value of the break property.
                                 *
                                 * <p>
                                 * This accessor method returns a reference to the live list,
                                 * not a snapshot. Therefore any modification you make to the
                                 * returned list will be present inside the JAXB object.
                                 * This is why there is not a <CODE>set</CODE> method for the break property.
                                 *
                                 * <p>
                                 * For example, to add a new item, do as follows:
                                 * <pre>
                                 *    getBreak().add(newItem);
                                 * </pre>
                                 *
                                 *
                                 * <p>
                                 * Objects of the following type(s) are allowed in the list
                                 * {@link RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte.Breaks.Break }
                                 *
                                 *
                                 */
                                public List<RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte.Breaks.Break> getBreak() {
                                    if (_break == null) {
                                        _break =
                                            new ArrayList<RAIZ.Parrilla.Schedule.Dates.TimeBand.Cortes.Corte.Breaks.Break>();
                                    }
                                    return this._break;
                                }


                                /**
                                 * <p>Java class for anonymous complex type.
                                 *
                                 * <p>The following schema fragment specifies the expected content contained within this class.
                                 *
                                 * <pre>
                                 * &lt;complexType>
                                 *   &lt;complexContent>
                                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                                 *       &lt;sequence>
                                 *         &lt;element name="BreakID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                                 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
                                 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
                                 *         &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
                                 *         &lt;element name="TotalDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
                                 *         &lt;element name="ComercialDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
                                 *         &lt;element name="TypeBreak" type="{http://www.w3.org/2001/XMLSchema}string"/>
                                 *       &lt;/sequence>
                                 *     &lt;/restriction>
                                 *   &lt;/complexContent>
                                 * &lt;/complexType>
                                 * </pre>
                                 *
                                 *
                                 */
                                @XmlAccessorType(XmlAccessType.FIELD)
                                @XmlType(name = "", propOrder = {
                                         "breakID", "description", "date",
                                         "hour", "totalDuration",
                                         "comercialDuration","typeBreak"
                                    })
                                public static class Break {

                                    @XmlElement(name = "BreakID",
                                                required = true)
                                    protected String breakID;
                                    @XmlElement(name = "Description",
                                                required = true)
                                    protected String description;
                                    @XmlElement(name = "Date", required = true)
                                    @XmlSchemaType(name = "date")
                                    protected String date;
                                    @XmlElement(name = "Hour", required = true)
                                    protected String hour;
                                    @XmlElement(name = "TotalDuration",
                                                required = true)
                                    protected String totalDuration;
                                    @XmlElement(name = "ComercialDuration",
                                                required = true)
                                    protected String comercialDuration;
                                    
                                    @XmlElement(name = "TypeBreak",
                                                required = true)
                                    protected String typeBreak;

                                    /**
                                     * Gets the value of the breakID property.
                                     *
                                     * @return
                                     *     possible object is
                                     *     {@link BigInteger }
                                     *
                                     */
                                    public String getBreakID() {
                                        return breakID;
                                    }

                                    /**
                                     * Sets the value of the breakID property.
                                     *
                                     * @param value
                                     *     allowed object is
                                     *     {@link BigInteger }
                                     *
                                     */
                                    public void setBreakID(String value) {
                                        this.breakID = value;
                                    }

                                    /**
                                     * Gets the value of the description property.
                                     *
                                     * @return
                                     *     possible object is
                                     *     {@link String }
                                     *
                                     */
                                    public String getDescription() {
                                        return description;
                                    }

                                    /**
                                     * Sets the value of the description property.
                                     *
                                     * @param value
                                     *     allowed object is
                                     *     {@link String }
                                     *
                                     */
                                    public void setDescription(String value) {
                                        this.description = value;
                                    }

                                    /**
                                     * Gets the value of the date property.
                                     *
                                     * @return
                                     *     possible object is
                                     *     {@link XMLGregorianCalendar }
                                     *
                                     */
                                    public String getDate() {
                                        return date;
                                    }

                                    /**
                                     * Sets the value of the date property.
                                     *
                                     * @param value
                                     *     allowed object is
                                     *     {@link XMLGregorianCalendar }
                                     *
                                     */
                                    public void setDate(String value) {
                                        this.date = value;
                                    }

                                    /**
                                     * Gets the value of the hour property.
                                     *
                                     * @return
                                     *     possible object is
                                     *     {@link String }
                                     *
                                     */
                                    public String getHour() {
                                        return hour;
                                    }

                                    /**
                                     * Sets the value of the hour property.
                                     *
                                     * @param value
                                     *     allowed object is
                                     *     {@link String }
                                     *
                                     */
                                    public void setHour(String value) {
                                        this.hour = value;
                                    }

                                    /**
                                     * Gets the value of the totalDuration property.
                                     *
                                     * @return
                                     *     possible object is
                                     *     {@link String }
                                     *
                                     */
                                    public String getTotalDuration() {
                                        return totalDuration;
                                    }

                                    /**
                                     * Sets the value of the totalDuration property.
                                     *
                                     * @param value
                                     *     allowed object is
                                     *     {@link String }
                                     *
                                     */
                                    public void setTotalDuration(String value) {
                                        this.totalDuration = value;
                                    }

                                    /**
                                     * Gets the value of the comercialDuration property.
                                     *
                                     * @return
                                     *     possible object is
                                     *     {@link String }
                                     *
                                     */
                                    public String getComercialDuration() {
                                        return comercialDuration;
                                    }

                                    /**
                                     * Sets the value of the comercialDuration property.
                                     *
                                     * @param value
                                     *     allowed object is
                                     *     {@link String }
                                     *
                                     */
                                    public void setComercialDuration(String value) {
                                        this.comercialDuration = value;
                                    }
                                    
                                    /**
                                     * Gets the value of the typeBreak property.
                                     *
                                     * @return
                                     *     possible object is
                                     *     {@link String }
                                     *
                                     */
                                    public String getTypeBreak() {
                                        return typeBreak;
                                    }

                                    /**
                                     * Sets the value of the typeBreak property.
                                     *
                                     * @param value
                                     *     allowed object is
                                     *     {@link String }
                                     *
                                     */
                                    public void setTypeBreak(String value) {
                                        this.typeBreak = value;
                                    }

                                }

                            }

                        }

                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     *
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     *
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *       &lt;sequence>
                     *         &lt;element name="Item">
                     *           &lt;complexType>
                     *             &lt;complexContent>
                     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                     *                 &lt;sequence>
                     *                   &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="EpisodeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="EpNo" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                     *                   &lt;element name="SlotDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="TitleID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                     *                   &lt;element name="EpisodeNameID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                     *                   &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="BuyUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="exceptCofepris" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="EventSpecial" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="EventBest" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="GeneroID" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                   &lt;element name="GeneroName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                     *                 &lt;/sequence>
                     *               &lt;/restriction>
                     *             &lt;/complexContent>
                     *           &lt;/complexType>
                     *         &lt;/element>
                     *       &lt;/sequence>
                     *     &lt;/restriction>
                     *   &lt;/complexContent>
                     * &lt;/complexType>
                     * </pre>
                     *
                     *
                     */
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = { "item" })
                    public static class Items {

                        @XmlElement(name = "Item", required = true)
                        protected List<RAIZ.Parrilla.Schedule.Dates.TimeBand.Items.Item> item;

                        /**
                         * Gets the value of the item property.
                         *
                         * @return
                         *     possible object is
                         *     {@link RAIZ.Parrilla.Schedule.Dates.TimeBand.Items.Item }
                         *
                         */
                        public List<RAIZ.Parrilla.Schedule.Dates.TimeBand.Items.Item> getItem() {
                            if (item == null) {
                                item =
                                    new ArrayList<RAIZ.Parrilla.Schedule.Dates.TimeBand.Items.Item>();
                            }
                            return this.item;
                        }

                        /**
                         * Sets the value of the item property.
                         *
                         * @param value
                         *     allowed object is
                         *     {@link RAIZ.Parrilla.Schedule.Dates.TimeBand.Items.Item }
                         *
                         */
                        /*public void setItem(RAIZ.Parrilla.Schedule.Dates.TimeBand.Items.Item value) {
                            this.item = value;
                        }*/


                        /**
                         * <p>Java class for anonymous complex type.
                         *
                         * <p>The following schema fragment specifies the expected content contained within this class.
                         *
                         * <pre>
                         * &lt;complexType>
                         *   &lt;complexContent>
                         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                         *       &lt;sequence>
                         *         &lt;element name="Hour" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="EpisodeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="EpNo" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                         *         &lt;element name="SlotDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="TitleID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                         *         &lt;element name="EpisodeNameID" type="{http://www.w3.org/2001/XMLSchema}integer"/>
                         *         &lt;element name="BuyUnitID" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="BuyUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="exceptCofepris" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="EventSpecial" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="EventBest" type="{http://www.w3.org/2001/XMLSchema}string"/>}
                         *         &lt;element name="GeneroID" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *         &lt;element name="GeneroName" type="{http://www.w3.org/2001/XMLSchema}string"/>
                         *       &lt;/sequence>
                         *     &lt;/restriction>
                         *   &lt;/complexContent>
                         * &lt;/complexType>
                         * </pre>
                         *
                         *
                         */
                        @XmlAccessorType(XmlAccessType.FIELD)
                        @XmlType(name = "", propOrder = {
                                 "hour", "title", "episodeName", "epNo",
                                 "slotDuration", "titleID", "episodeNameID",
                                 "buyUnitID", "buyUnit", "exceptCofepris",
                                 "eventSpecial","eventBest","generoID","generoName"
                            })
                        public static class Item {

                            @XmlElement(name = "Hour", required = true)
                            protected String hour;
                            @XmlElement(name = "Title", required = true)
                            protected String title;
                            @XmlElement(name = "EpisodeName", required = true)
                            protected String episodeName;
                            @XmlElement(name = "EpNo", required = true)
                            protected String epNo;
                            @XmlElement(name = "SlotDuration", required = true)
                            protected String slotDuration;
                            @XmlElement(name = "TitleID", required = true)
                            protected String titleID;
                            @XmlElement(name = "EpisodeNameID", required = true)
                            protected String episodeNameID;
                            @XmlElement(name = "BuyUnitID", required = true)
                            protected String buyUnitID;
                            @XmlElement(name = "BuyUnit", required = true)
                            protected String buyUnit;
                            @XmlElement(required = true)
                            protected String exceptCofepris;
                            @XmlElement(name = "EventSpecial", required = true)
                            protected String eventSpecial;
                            @XmlElement(name = "EventBest", required = true)
                            protected String eventBest;
                            
                            @XmlElement(name = "GeneroID", required = true)
                            protected String generoID;
                            @XmlElement(name = "GeneroName", required = true)
                            protected String generoName;

                            /**
                             * Gets the value of the hour property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getHour() {
                                return hour;
                            }

                            /**
                             * Sets the value of the hour property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setHour(String value) {
                                this.hour = value;
                            }

                            /**
                             * Gets the value of the title property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getTitle() {
                                return title;
                            }

                            /**
                             * Sets the value of the title property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setTitle(String value) {
                                this.title = value;
                            }

                            /**
                             * Gets the value of the episodeName property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getEpisodeName() {
                                return episodeName;
                            }

                            /**
                             * Sets the value of the episodeName property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setEpisodeName(String value) {
                                this.episodeName = value;
                            }

                            /**
                             * Gets the value of the epNo property.
                             *
                             * @return
                             *     possible object is
                             *     {@link BigInteger }
                             *
                             */
                            public String getEpNo() {
                                return epNo;
                            }

                            /**
                             * Sets the value of the epNo property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link BigInteger }
                             *
                             */
                            public void setEpNo(String value) {
                                this.epNo = value;
                            }

                            /**
                             * Gets the value of the slotDuration property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getSlotDuration() {
                                return slotDuration;
                            }

                            /**
                             * Sets the value of the slotDuration property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setSlotDuration(String value) {
                                this.slotDuration = value;
                            }

                            /**
                             * Gets the value of the titleID property.
                             *
                             * @return
                             *     possible object is
                             *     {@link BigInteger }
                             *
                             */
                            public String getTitleID() {
                                return titleID;
                            }

                            /**
                             * Sets the value of the titleID property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link BigInteger }
                             *
                             */
                            public void setTitleID(String value) {
                                this.titleID = value;
                            }

                            /**
                             * Gets the value of the episodeNameID property.
                             *
                             * @return
                             *     possible object is
                             *     {@link BigInteger }
                             *
                             */
                            public String getEpisodeNameID() {
                                return episodeNameID;
                            }

                            /**
                             * Sets the value of the episodeNameID property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link BigInteger }
                             *
                             */
                            public void setEpisodeNameID(String value) {
                                this.episodeNameID = value;
                            }

                            /**
                             * Gets the value of the buyUnitID property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getBuyUnitID() {
                                return buyUnitID;
                            }

                            /**
                             * Sets the value of the buyUnitID property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setBuyUnitID(String value) {
                                this.buyUnitID = value;
                            }

                            /**
                             * Gets the value of the buyUnit property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getBuyUnit() {
                                return buyUnit;
                            }

                            /**
                             * Sets the value of the buyUnit property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setBuyUnit(String value) {
                                this.buyUnit = value;
                            }

                            /**
                             * Gets the value of the exceptCofepris property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getExceptCofepris() {
                                return exceptCofepris;
                            }

                            /**
                             * Sets the value of the exceptCofepris property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setExceptCofepris(String value) {
                                this.exceptCofepris = value;
                            }

                            /**
                             * Gets the value of the eventSpecial property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getEventSpecial() {
                                return eventSpecial;
                            }

                            /**
                             * Sets the value of the eventSpecial property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setEventSpecial(String value) {
                                this.eventSpecial = value;
                            }
                            
                            /**
                             * Gets the value of the eventBest property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getEventBest() {
                                return eventBest;
                            }

                            /**
                             * Sets the value of the eventBest property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setEventBest(String value) {
                                this.eventBest = value;
                            }
                            
                            /**
                             * Gets the value of the generoID property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getGeneroID() {
                                return generoID;
                            }

                            /**
                             * Sets the value of the generoID property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setGeneroID(String value) {
                                this.generoID = value;
                            }
                            
                            /**
                             * Gets the value of the generoName property.
                             *
                             * @return
                             *     possible object is
                             *     {@link String }
                             *
                             */
                            public String getGeneroName() {
                                return generoName;
                            }

                            /**
                             * Sets the value of the generoName property.
                             *
                             * @param value
                             *     allowed object is
                             *     {@link String }
                             *
                             */
                            public void setGeneroName(String value) {
                                this.generoName = value;
                            }

                        }

                    }

                }

            }

        }

    }

}

