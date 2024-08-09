package com.xls.bbbbb.office.fc.hwpf.model;

import com.xls.bbbbb.office.fc.util.Internal;
import com.xls.bbbbb.office.fc.util.LittleEndian;

/**
 * Handles the fibRgW / FibRgW97 part of
 *  the FIB (File Information Block)
 */
@ Internal
public final class FIBShortHandler
{
    public final static int MAGICCREATED = 0;
    public final static int MAGICREVISED = 1;
    public final static int MAGICCREATEDPRIVATE = 2;
    public final static int MAGICREVISEDPRIVATE = 3;
    public final static int LIDFE = 13;

    final static int START = 0x20;

    short[] _shorts;

    public FIBShortHandler(byte[] mainStream)
    {
        int offset = START;
        int shortCount = LittleEndian.getShort(mainStream, offset);
        offset += LittleEndian.SHORT_SIZE;
        _shorts = new short[shortCount];

        for (int x = 0; x < shortCount; x++)
        {
            _shorts[x] = LittleEndian.getShort(mainStream, offset);
            offset += LittleEndian.SHORT_SIZE;
        }
    }

    public short getShort(int shortCode)
    {
        return _shorts[shortCode];
    }

    int sizeInBytes()
    {
        return (_shorts.length * LittleEndian.SHORT_SIZE) + LittleEndian.SHORT_SIZE;
    }

    void serialize(byte[] mainStream)
    {
        int offset = START;
        LittleEndian.putShort(mainStream, offset, (short)_shorts.length);
        offset += LittleEndian.SHORT_SIZE;
        //mainStream.write(holder);

        for (int x = 0; x < _shorts.length; x++)
        {
            LittleEndian.putShort(mainStream, offset, _shorts[x]);
            offset += LittleEndian.SHORT_SIZE;
        }
    }

}
