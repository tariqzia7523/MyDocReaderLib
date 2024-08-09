package com.xls.bbbbb.office.fc.hwpf.usermodel;

import com.xls.bbbbb.office.fc.hwpf.HWPFDocument;

public final class DocumentPosition extends Range
{
    public DocumentPosition(HWPFDocument doc, int pos)
    {
        super(pos, pos, doc);
    }

}
