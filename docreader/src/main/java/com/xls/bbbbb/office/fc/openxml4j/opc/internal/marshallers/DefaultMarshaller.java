package com.xls.bbbbb.office.fc.openxml4j.opc.internal.marshallers;

import com.xls.bbbbb.office.fc.openxml4j.exceptions.OpenXML4JException;
import com.xls.bbbbb.office.fc.openxml4j.opc.PackagePart;
import com.xls.bbbbb.office.fc.openxml4j.opc.internal.PartMarshaller;

import java.io.OutputStream;


/**
 * Default marshaller that specified that the part is responsible to marshall its content.
 *
 * @author Julien Chable
 * @version 1.0
 * @see PartMarshaller
 */
public final class DefaultMarshaller implements PartMarshaller
{

    /**
     * Save part in the output stream by using the save() method of the part.
     *
     * @throws OpenXML4JException
     *             If any error occur.
     */
    public boolean marshall(PackagePart part, OutputStream out) throws OpenXML4JException
    {
        return part.save(out);
    }
}
