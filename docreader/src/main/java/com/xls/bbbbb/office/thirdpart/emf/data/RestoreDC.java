// Copyright 2002, FreeHEP.

package com.xls.bbbbb.office.thirdpart.emf.data;

import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;
import com.xls.bbbbb.office.thirdpart.emf.EMFRenderer;
import com.xls.bbbbb.office.thirdpart.emf.EMFTag;

import java.io.IOException;


public class RestoreDC extends EMFTag
{

    private int savedDC = -1;

    public RestoreDC()
    {
        super(34, 1);
    }

    public RestoreDC(int savedDC)
    {
        this();
        this.savedDC = savedDC;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new RestoreDC(emf.readDWORD());
    }


    public String toString()
    {
        return super.toString() + "\n  savedDC: " + savedDC;
    }


    public void render(EMFRenderer renderer)
    {
        renderer.retoreDC();
    }
}
