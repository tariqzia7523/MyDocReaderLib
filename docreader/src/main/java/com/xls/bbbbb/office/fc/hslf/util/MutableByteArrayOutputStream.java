package com.xls.bbbbb.office.fc.hslf.util;

import java.io.ByteArrayOutputStream;

/**
 * This class doesn't work yet, but is here to show the idea of a
 *  ByteArrayOutputStream where you can track how many bytes you've
 *  already written, and go back and write over a previous part of the stream
 *
 * @author Nick Burch
 */

public final class MutableByteArrayOutputStream extends ByteArrayOutputStream
{
    /** Return how many bytes we've stuffed in so far */
    public int getBytesWritten()
    {
        return -1;
    }

    /** Write some bytes to the array */
    public void write(byte[] b)
    {
    }

    public void write(int b)
    {
    }

    /** Write some bytes to an earlier bit of the array */
    public void overwrite(byte[] b, int startPos)
    {
    }
}
