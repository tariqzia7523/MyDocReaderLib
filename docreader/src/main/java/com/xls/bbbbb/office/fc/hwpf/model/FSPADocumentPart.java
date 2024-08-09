package com.xls.bbbbb.office.fc.hwpf.model;

import com.xls.bbbbb.office.fc.util.Internal;

@ Internal
public enum FSPADocumentPart
{
    HEADER(FIBFieldHandler.PLCSPAHDR),

    MAIN(FIBFieldHandler.PLCSPAMOM);

    private final int fibFieldsField;

    private FSPADocumentPart(final int fibHandlerField)
    {
        this.fibFieldsField = fibHandlerField;
    }

    public int getFibFieldsField()
    {
        return fibFieldsField;
    }
}
