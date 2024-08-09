// Copyright 2002, FreeHEP.

package com.xls.bbbbb.office.thirdpart.emf.data;

import com.xls.bbbbb.office.java.awt.Rectangle;
import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;
import com.xls.bbbbb.office.thirdpart.emf.EMFRenderer;
import com.xls.bbbbb.office.thirdpart.emf.EMFTag;

import java.io.IOException;


public class EMFRectangle extends EMFTag
{

    private Rectangle bounds;

    public EMFRectangle()
    {
        super(43, 1);
    }

    public EMFRectangle(Rectangle bounds)
    {
        this();
        this.bounds = bounds;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new EMFRectangle(emf.readRECTL());
    }

    public String toString()
    {
        return super.toString() + "\n  bounds: " + bounds;
    }

    public void render(EMFRenderer renderer)
    {
        renderer.fillAndDrawOrAppend(bounds);
    }
}
