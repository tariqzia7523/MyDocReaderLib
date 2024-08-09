// Copyright 2002, FreeHEP.

package com.xls.bbbbb.office.thirdpart.emf.data;

import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;
import com.xls.bbbbb.office.thirdpart.emf.EMFRenderer;
import com.xls.bbbbb.office.thirdpart.emf.EMFTag;

import java.io.IOException;

public class DeleteObject extends EMFTag
{

    private int index;

    public DeleteObject()
    {
        super(40, 1);
    }

    public DeleteObject(int index)
    {
        this();
        this.index = index;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new DeleteObject(emf.readDWORD());
    }

    public String toString()
    {
        return super.toString() + "\n  index: 0x" + Integer.toHexString(index);
    }



    public void render(EMFRenderer renderer)
    {
        renderer.storeGDIObject(index, null);
    }
}
