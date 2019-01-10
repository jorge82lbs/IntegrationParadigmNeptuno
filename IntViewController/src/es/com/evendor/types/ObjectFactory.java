
package es.com.evendor.types;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the es.com.vendor.types package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.com.vendor.types
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link es.com.evendor.types.CodRespuesta}
     *
     */
    public CodRespuesta createCodRespuesta() {
        return new CodRespuesta();
    }

    /**
     * Create an instance of {@link CodRespuesta.ItemCabecera }
     *
     */
    public CodRespuesta.ItemCabecera createCodRespuestaItemCabecera() {
        return new CodRespuesta.ItemCabecera();
    }

    /**
     * Create an instance of {@link CodRespuesta.ItemCabecera.ItemRespuesta }
     *
     */
    public CodRespuesta.ItemCabecera.ItemRespuesta createCodRespuestaItemCabeceraItemRespuesta() {
        return new CodRespuesta.ItemCabecera.ItemRespuesta();
    }

    /**
     * Create an instance of {@link CodRespuesta.ItemCabecera.ItemRespuesta.ListaMensaje }
     *
     */
    public CodRespuesta.ItemCabecera.ItemRespuesta.ListaMensaje createCodRespuestaItemCabeceraItemRespuestaListaMensaje() {
        return new CodRespuesta.ItemCabecera.ItemRespuesta.ListaMensaje();
    }

}
