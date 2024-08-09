package com.xls.bbbbb.office.fc.hwpf.usermodel;

import com.xls.bbbbb.office.fc.hwpf.model.FieldsDocumentPart;

import java.util.Collection;


/**
 * User-friendly interface to access document {@link Field}s
 * 
 * @author Sergey Vladimirov (vlsergey {at} gmail {dot} com)
 */
public interface Fields
{
    Field getFieldByStartOffset(FieldsDocumentPart documentPart, int offset);

    Collection<Field> getFields(FieldsDocumentPart part);
}
