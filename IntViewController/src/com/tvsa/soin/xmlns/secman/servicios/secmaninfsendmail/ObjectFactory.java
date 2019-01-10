
package com.tvsa.soin.xmlns.secman.servicios.secmaninfsendmail;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.tvsa.soin.xmlns.secman.servicios.secmaninfsendmail package.
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tvsa.soin.xmlns.secman.servicios.secmaninfsendmail
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AttachmentCollection }
     *
     */
    public AttachmentCollection createAttachmentCollection() {
        return new AttachmentCollection();
    }

    /**
     * Create an instance of {@link Attachment }
     *
     */
    public Attachment createAttachment() {
        return new Attachment();
    }

    /**
     * Create an instance of {@link MailAddress }
     *
     */
    public MailAddress createMailAddress() {
        return new MailAddress();
    }

    /**
     * Create an instance of {@link MailBody }
     *
     */
    public MailBody createMailBody() {
        return new MailBody();
    }

    /**
     * Create an instance of {@link MailHeader }
     *
     */
    public MailHeader createMailHeader() {
        return new MailHeader();
    }

    /**
     * Create an instance of {@link Mail }
     *
     */
    public Mail createMail() {
        return new Mail();
    }

    /**
     * Create an instance of {@link MailAddressCollection }
     *
     */
    public MailAddressCollection createMailAddressCollection() {
        return new MailAddressCollection();
    }

}
