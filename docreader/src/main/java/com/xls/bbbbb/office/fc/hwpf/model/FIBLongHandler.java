package com.xls.bbbbb.office.fc.hwpf.model;

import com.xls.bbbbb.office.fc.util.Internal;
import com.xls.bbbbb.office.fc.util.LittleEndian;

/**
 * Handles the fibRgLw / The FibRgLw97 part of
 *  the FIB (File Information Block)
 */
@ Internal
public final class FIBLongHandler
{
    public static final int CBMAC = 0;
    public static final int PRODUCTCREATED = 1;
    public static final int PRODUCTREVISED = 2;
    /**
     * Pointer to length of main document text stream 1
     * 
     * @since Word 97
     */
    public static final int CCPTEXT = 3;
    /**
     * Pointer to length of footnote subdocument text stream
     * 
     * @since Word 97
     */
    public static final int CCPFTN = 4;
    /**
     * Pointer to length of header subdocument text stream
     * 
     * @since Word 97
     */
    public static final int CCPHDD = 5;
    /**
     * Pointer to length of macro subdocument text stream, which should now
     * always be 0
     * 
     * @since Word 97
     */
    public static final int CCPMCR = 6;
    /**
     * Pointer to length of annotation subdocument text stream
     * 
     * @since Word 97
     */
    public static final int CCPATN = 7;
    /**
     * Pointer to length of endnote subdocument text stream
     * 
     * @since Word 97
     */
    public static final int CCPEDN = 8;
    /**
     * Pointer to length of textbox subdocument text stream
     * 
     * @since Word 97
     */
    public static final int CCPTXBX = 9;

    /**
     * Length of header textbox subdocument text stream
     * 
     * @since Word 97
     */
    public static final int CCPHDRTXBX = 10;
    public static final int PNFBPCHPFIRST = 11;
    public static final int PNCHPFIRST = 12;
    public static final int CPNBTECHP = 13;
    public static final int PNFBPPAPFIRST = 14;
    public static final int PNPAPFIRST = 15;
    public static final int CPNBTEPAP = 16;
    public static final int PNFBPLVCFIRST = 17;
    public static final int PNLVCFIRST = 18;
    public static final int CPNBTELVC = 19;
    public static final int FCISLANDFIRST = 20;
    public static final int FCISLANDLIM = 21;

    int[] _longs;

    public FIBLongHandler(byte[] mainStream, int offset)
    {
        int longCount = LittleEndian.getShort(mainStream, offset);
        offset += LittleEndian.SHORT_SIZE;
        _longs = new int[longCount];

        for (int x = 0; x < longCount; x++)
        {
            _longs[x] = LittleEndian.getInt(mainStream, offset + (x * LittleEndian.INT_SIZE));
        }
    }

    /**
     * Refers to a 32 bit windows "long" same as a Java int
     * @param longCode FIXME: Document this!
     * @return FIXME: Document this!
     */
    public int getLong(int longCode)
    {
        return _longs[longCode];
    }

    public void setLong(int longCode, int value)
    {
        _longs[longCode] = value;
    }

    void serialize(byte[] mainStream, int offset)
    {
        LittleEndian.putShort(mainStream, offset, (short)_longs.length);
        offset += LittleEndian.SHORT_SIZE;

        for (int x = 0; x < _longs.length; x++)
        {
            LittleEndian.putInt(mainStream, offset, _longs[x]);
            offset += LittleEndian.INT_SIZE;
        }
    }

    int sizeInBytes()
    {
        return (_longs.length * LittleEndian.INT_SIZE) + LittleEndian.SHORT_SIZE;
    }

}
