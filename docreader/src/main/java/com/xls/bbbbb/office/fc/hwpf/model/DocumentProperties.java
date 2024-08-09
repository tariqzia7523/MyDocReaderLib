package com.xls.bbbbb.office.fc.hwpf.model;

import com.xls.bbbbb.office.fc.hwpf.model.types.DOPAbstractType;
import com.xls.bbbbb.office.fc.util.Internal;
import com.xls.bbbbb.office.fc.util.LittleEndian;


/**
 * Comment me
 * 
 * @author Ryan Ackley
 */
@Internal
public final class DocumentProperties extends DOPAbstractType
{

    private byte[] _preserved;

    /**
     * @deprecated Use {@link #DocumentProperties(byte[],int,int)} instead
     */
    public DocumentProperties( byte[] tableStream, int offset )
    {
        this( tableStream, offset, DOPAbstractType.getSize() );
    }

    public DocumentProperties( byte[] tableStream, int offset, int length )
    {
        super.fillFields( tableStream, offset );

        final int supportedSize = DOPAbstractType.getSize();
        if ( length != supportedSize )
        {
            this._preserved = LittleEndian.getByteArray( tableStream, offset
                    + supportedSize, length - supportedSize );
        }
        else
        {
            _preserved = new byte[0];
        }
    }

    @Override
    public void serialize( byte[] data, int offset )
    {
        super.serialize( data, offset );
    }
}
