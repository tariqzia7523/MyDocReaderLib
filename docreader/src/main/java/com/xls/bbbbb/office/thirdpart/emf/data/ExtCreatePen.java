// Copyright 2001, FreeHEP.

package com.xls.bbbbb.office.thirdpart.emf.data;

import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;
import com.xls.bbbbb.office.thirdpart.emf.EMFRenderer;
import com.xls.bbbbb.office.thirdpart.emf.EMFTag;

import java.io.IOException;

public class ExtCreatePen extends EMFTag
{

    private int index;

    private ExtLogPen pen;

    public ExtCreatePen()
    {
        super(95, 1);
    }

    public ExtCreatePen(int index, ExtLogPen pen)
    {
        this();
        this.index = index;
        this.pen = pen;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        int index = emf.readDWORD();
        /* int bmiOffset = */emf.readDWORD();
        /* int bmiSize = */emf.readDWORD();
        /* int brushOffset = */emf.readDWORD();
        /* int brushSize = */emf.readDWORD();
        return new ExtCreatePen(index, new ExtLogPen(emf, len));
    }

    public String toString()
    {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(index) + "\n"
            + pen.toString();
    }

    public void render(EMFRenderer renderer)
    {
        renderer.storeGDIObject(index, pen);
    }
}
