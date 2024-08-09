package com.xls.bbbbb.office.fc.dom4j.dom;

import com.xls.bbbbb.office.fc.dom4j.Attribute;
import com.xls.bbbbb.office.fc.dom4j.CDATA;
import com.xls.bbbbb.office.fc.dom4j.Comment;
import com.xls.bbbbb.office.fc.dom4j.Document;
import com.xls.bbbbb.office.fc.dom4j.DocumentFactory;
import com.xls.bbbbb.office.fc.dom4j.DocumentType;
import com.xls.bbbbb.office.fc.dom4j.Element;
import com.xls.bbbbb.office.fc.dom4j.Entity;
import com.xls.bbbbb.office.fc.dom4j.Namespace;
import com.xls.bbbbb.office.fc.dom4j.ProcessingInstruction;
import com.xls.bbbbb.office.fc.dom4j.QName;
import com.xls.bbbbb.office.fc.dom4j.Text;
import com.xls.bbbbb.office.fc.dom4j.util.SingletonStrategy;

import org.w3c.dom.DOMException;

import java.util.Map;


public class DOMDocumentFactory extends DocumentFactory implements org.w3c.dom.DOMImplementation
{

    /** The Singleton instance */
    private static SingletonStrategy singleton = null;

    static
    {
        try
        {
            String defaultSingletonClass = "org.dom4j.util.SimpleSingleton";
            Class clazz = null;
            try
            {
                String singletonClass = defaultSingletonClass;
                singletonClass = System.getProperty(
                    "org.dom4j.dom.DOMDocumentFactory.singleton.strategy", singletonClass);
                clazz = Class.forName(singletonClass);
            }
            catch(Exception exc1)
            {
                try
                {
                    String singletonClass = defaultSingletonClass;
                    clazz = Class.forName(singletonClass);
                }
                catch(Exception exc2)
                {
                }
            }
            singleton = (SingletonStrategy)clazz.newInstance();
            singleton.setSingletonClassName(DOMDocumentFactory.class.getName());
        }
        catch(Exception exc3)
        {
        }
    }

    /**
     * <p>
     * Access to the singleton instance of this factory.
     * </p>
     * 
     * @return the default singleon instance
     */
    public static DocumentFactory getInstance()
    {
        DOMDocumentFactory fact = (DOMDocumentFactory)singleton.instance();
        return fact;
    }

    // Factory methods
    public Document createDocument()
    {
        DOMDocument answer = new DOMDocument();
        answer.setDocumentFactory(this);

        return answer;
    }

    public DocumentType createDocType(String name, String publicId, String systemId)
    {
        return new DOMDocumentType(name, publicId, systemId);
    }

    public Element createElement(QName qname)
    {
        return new DOMElement(qname);
    }

    public Element createElement(QName qname, int attributeCount)
    {
        return new DOMElement(qname, attributeCount);
    }

    public Attribute createAttribute(Element owner, QName qname, String value)
    {
        return new DOMAttribute(qname, value);
    }

    public CDATA createCDATA(String text)
    {
        return new DOMCDATA(text);
    }

    public Comment createComment(String text)
    {
        return new DOMComment(text);
    }

    public Text createText(String text)
    {
        return new DOMText(text);
    }

    public Entity createEntity(String name)
    {
        return new DOMEntityReference(name);
    }

    public Entity createEntity(String name, String text)
    {
        return new DOMEntityReference(name, text);
    }

    public Namespace createNamespace(String prefix, String uri)
    {
        return new DOMNamespace(prefix, uri);
    }

    public ProcessingInstruction createProcessingInstruction(String target, String data)
    {
        return new DOMProcessingInstruction(target, data);
    }

    public ProcessingInstruction createProcessingInstruction(String target, Map data)
    {
        return new DOMProcessingInstruction(target, data);
    }

    // org.w3c.dom.DOMImplementation interface
    public boolean hasFeature(String feat, String version)
    {
        if ("XML".equalsIgnoreCase(feat) || "Core".equalsIgnoreCase(feat))
        {
            return ((version == null) || (version.length() == 0) || "1.0".equals(version) || "2.0"
                .equals(version));
        }

        return false;
    }

    public org.w3c.dom.DocumentType createDocumentType(String qualifiedName, String publicId,
        String systemId) throws DOMException
    {
        return new DOMDocumentType(qualifiedName, publicId, systemId);
    }

    public org.w3c.dom.Document createDocument(String namespaceURI, String qualifiedName,
        org.w3c.dom.DocumentType docType) throws DOMException
    {
        DOMDocument document;

        if (docType != null)
        {
            DOMDocumentType documentType = asDocumentType(docType);
            document = new DOMDocument(documentType);
        }
        else
        {
            document = new DOMDocument();
        }

        document.addElement(createQName(qualifiedName, namespaceURI));

        return document;
    }

    // Implementation methods
    protected DOMDocumentType asDocumentType(org.w3c.dom.DocumentType docType)
    {
        if (docType instanceof DOMDocumentType)
        {
            return (DOMDocumentType)docType;
        }
        else
        {
            return new DOMDocumentType(docType.getName(), docType.getPublicId(),
                docType.getSystemId());
        }
    }

    @ Override
    public Object getFeature(String feature, String version)
    {
        // TODO Auto-generated method stub
        return null;
    }
}


