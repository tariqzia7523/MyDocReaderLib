package com.xls.bbbbb.office.fc.hslf.blip;


import com.xls.bbbbb.office.fc.hslf.usermodel.PictureData;

import java.io.IOException;

/**
 * Represents a bitmap picture data:  JPEG or PNG.
 * The data is not compressed and the exact file content is written in the stream.
 *
 * @author Yegor Kozlov
 */
public abstract class Bitmap extends PictureData
{

    public byte[] getData()
    {
        byte[] rawdata = getRawData();
        byte[] imgdata = new byte[rawdata.length - 17];
        System.arraycopy(rawdata, 17, imgdata, 0, imgdata.length);
        return imgdata;
    }

    public void setData(byte[] data) throws IOException
    {
        /*ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] checksum = getChecksum(data);
        out.write(checksum);
        out.write(0);
        out.write(data);

        setRawData(out.toByteArray());*/
    }
}
