package com.xls.bbbbb.office.fc.hwpf.model;

import com.xls.bbbbb.office.fc.hwpf.model.types.FSPAAbstractType;
import com.xls.bbbbb.office.fc.util.Internal;


/**
 * File Shape Address structure
 * 
 * @author Squeeself
 */
@Internal
public final class FSPA extends FSPAAbstractType
{
    @Deprecated
    public static final int FSPA_SIZE = getSize(); // 26

    public FSPA()
    {
    }

    public FSPA( byte[] bytes, int offset )
    {
        fillFields( bytes, offset );
    }

    public byte[] toByteArray()
    {
        byte[] buf = new byte[FSPA_SIZE];
        serialize( buf, 0 );
        return buf;
    }

}
