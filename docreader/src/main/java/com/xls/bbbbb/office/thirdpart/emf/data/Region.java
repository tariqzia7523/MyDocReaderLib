
package com.xls.bbbbb.office.thirdpart.emf.data;

import com.xls.bbbbb.office.java.awt.Rectangle;
import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;

import java.io.IOException;


public class Region
{
    private Rectangle bounds;

    private Rectangle region;

    public Region(Rectangle bounds, Rectangle region)
    {
        this.bounds = bounds;
        this.region = region;
    }

    public Region(EMFInputStream emf) throws IOException
    {
        /* int length = */emf.readDWORD();
        /* int mode = */emf.readDWORD();
        /* int nRect = */emf.readDWORD();
        int size = emf.readDWORD();
        bounds = emf.readRECTL();
        region = emf.readRECTL();
        for (int i = 16; i < size; i += 16)
            emf.readRECTL();
    }


    public int length()
    {
        return 48;
    }

    public String toString()
    {
        return "  Region\n" + "    bounds: " + bounds + "\n    region: " + region;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }
}
