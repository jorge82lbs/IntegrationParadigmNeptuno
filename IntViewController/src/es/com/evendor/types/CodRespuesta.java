
package es.com.evendor.types;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="ItemCabecera">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ProcessID" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                   &lt;element name="Resultado" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                   &lt;element name="TipoProceso" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                   &lt;element name="ItemRespuesta">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Elemento" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="IdElemento" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="Resultado" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="ListaMensaje" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="IdCodigo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="Descripcion" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
@XmlType(name = "", propOrder = { "itemCabecera" })
@XmlRootElement(name = "CodRespuesta")
public class CodRespuesta {

    @XmlElement(name = "ItemCabecera", required = true)
    protected List<CodRespuesta.ItemCabecera> itemCabecera;

    /**
     * Gets the value of the itemCabecera property.
     *
     * @return
     *     possible object is
     *     {@link CodRespuesta.ItemCabecera }
     *
     */
    /*public CodRespuesta.ItemCabecera getItemCabecera() {
        return itemCabecera;
    }*/

    /*public void setItemCabecera(CodRespuesta.ItemCabecera value) {
        this.itemCabecera = value;
    }*/

    /**
     * Sets the value of the itemCabecera property.
     *
     * @param value
     *     allowed object is
     *     {@link CodRespuesta.ItemCabecera }
     *
     */
    public List<CodRespuesta.ItemCabecera> getItemCabecera() {
        if (itemCabecera == null) {
            itemCabecera = new ArrayList<CodRespuesta.ItemCabecera>();
        }
        return itemCabecera;
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
     *         &lt;element name="ProcessID" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *         &lt;element name="Resultado" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *         &lt;element name="TipoProceso" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *         &lt;element name="ItemRespuesta">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Elemento" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="IdElemento" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="Resultado" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="ListaMensaje" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="IdCodigo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="Descripcion" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
             "processID", "resultado", "tipoProceso", "itemRespuesta" })
    public static class ItemCabecera {

        @XmlElement(name = "ProcessID", required = true)
        protected BigDecimal processID;
        @XmlElement(name = "Resultado", required = true)
        protected String resultado;
        @XmlElement(name = "TipoProceso", required = true)
        protected String tipoProceso;
        @XmlElement(name = "ItemRespuesta", required = true)
        protected CodRespuesta.ItemCabecera.ItemRespuesta itemRespuesta;

        /**
         * Gets the value of the processID property.
         *
         * @return
         *     possible object is
         *     {@link Object }
         *
         */
        public BigDecimal getProcessID() {
            return processID;
        }

        /**
         * Sets the value of the processID property.
         *
         * @param value
         *     allowed object is
         *     {@link Object }
         *
         */
        public void setProcessID(BigDecimal value) {
            this.processID = value;
        }

        /**
         * Gets the value of the resultado property.
         *
         * @return
         *     possible object is
         *     {@link Object }
         *
         */
        public String getResultado() {
            return resultado;
        }

        /**
         * Sets the value of the resultado property.
         *
         * @param value
         *     allowed object is
         *     {@link Object }
         *
         */
        public void setResultado(String value) {
            this.resultado = value;
        }

        /**
         * Gets the value of the tipoProceso property.
         *
         * @return
         *     possible object is
         *     {@link Object }
         *
         */
        public String getTipoProceso() {
            return tipoProceso;
        }

        /**
         * Sets the value of the tipoProceso property.
         *
         * @param value
         *     allowed object is
         *     {@link Object }
         *
         */
        public void setTipoProceso(String value) {
            this.tipoProceso = value;
        }

        /**
         * Gets the value of the itemRespuesta property.
         *
         * @return
         *     possible object is
         *     {@link CodRespuesta.ItemCabecera.ItemRespuesta }
         *
         */
        public CodRespuesta.ItemCabecera.ItemRespuesta getItemRespuesta() {
            return itemRespuesta;
        }

        /**
         * Sets the value of the itemRespuesta property.
         *
         * @param value
         *     allowed object is
         *     {@link CodRespuesta.ItemCabecera.ItemRespuesta }
         *
         */
        public void setItemRespuesta(CodRespuesta.ItemCabecera.ItemRespuesta value) {
            this.itemRespuesta = value;
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
         *         &lt;element name="Elemento" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="IdElemento" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="Resultado" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="ListaMensaje" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="IdCodigo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="Descripcion" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
                 "elemento", "idElemento", "resultado", "listaMensaje" })
        public static class ItemRespuesta {

            @XmlElement(name = "Elemento", required = true)
            protected String elemento;
            @XmlElement(name = "IdElemento", required = true)
            protected String idElemento;
            @XmlElement(name = "Resultado", required = true)
            protected String resultado;
            @XmlElement(name = "ListaMensaje")
            protected List<CodRespuesta.ItemCabecera.ItemRespuesta.ListaMensaje> listaMensaje;

            /**
             * Gets the value of the elemento property.
             *
             * @return
             *     possible object is
             *     {@link Object }
             *
             */
            public String getElemento() {
                return elemento;
            }

            /**
             * Sets the value of the elemento property.
             *
             * @param value
             *     allowed object is
             *     {@link Object }
             *
             */
            public void setElemento(String value) {
                this.elemento = value;
            }

            /**
             * Gets the value of the idElemento property.
             *
             * @return
             *     possible object is
             *     {@link Object }
             *
             */
            public String getIdElemento() {
                return idElemento;
            }

            /**
             * Sets the value of the idElemento property.
             *
             * @param value
             *     allowed object is
             *     {@link Object }
             *
             */
            public void setIdElemento(String value) {
                this.idElemento = value;
            }

            /**
             * Gets the value of the resultado property.
             *
             * @return
             *     possible object is
             *     {@link Object }
             *
             */
            public String getResultado() {
                return resultado;
            }

            /**
             * Sets the value of the resultado property.
             *
             * @param value
             *     allowed object is
             *     {@link Object }
             *
             */
            public void setResultado(String value) {
                this.resultado = value;
            }

            /**
             * Gets the value of the listaMensaje property.
             *
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the listaMensaje property.
             *
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getListaMensaje().add(newItem);
             * </pre>
             *
             *
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CodRespuesta.ItemCabecera.ItemRespuesta.ListaMensaje }
             *
             *
             */
            public List<CodRespuesta.ItemCabecera.ItemRespuesta.ListaMensaje> getListaMensaje() {
                if (listaMensaje == null) {
                    listaMensaje =
                        new ArrayList<CodRespuesta.ItemCabecera.ItemRespuesta.ListaMensaje>();
                }
                return this.listaMensaje;
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
             *         &lt;element name="IdCodigo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="Descripcion" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             *
             *
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = { "idError", "descripcion" })
            public static class ListaMensaje {

                @XmlElement(name = "IdError", required = true)
                protected String idError;
                @XmlElement(name = "Descripcion", required = true)
                protected String descripcion;

                /**
                 * Gets the value of the idCodigo property.
                 *
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *
                 */
                public String getIdError() {
                    return idError;
                }

                /**
                 * Sets the value of the idCodigo property.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *
                 */
                public void setIdError(String value) {
                    this.idError = value;
                }

                /**
                 * Gets the value of the descripcion property.
                 *
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *
                 */
                public String getDescripcion() {
                    return descripcion;
                }

                /**
                 * Sets the value of the descripcion property.
                 *
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *
                 */
                public void setDescripcion(String value) {
                    this.descripcion = value;
                }

            }

        }

    }

}
