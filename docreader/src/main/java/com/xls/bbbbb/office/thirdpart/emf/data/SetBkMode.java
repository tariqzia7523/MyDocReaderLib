// Copyright 2002, FreeHEP.

package com.xls.bbbbb.office.thirdpart.emf.data;

import com.xls.bbbbb.office.thirdpart.emf.EMFConstants;
import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;
import com.xls.bbbbb.office.thirdpart.emf.EMFRenderer;
import com.xls.bbbbb.office.thirdpart.emf.EMFTag;

import java.io.IOException;


public class SetBkMode extends EMFTag implements EMFConstants
{

    private int mode;

    public SetBkMode()
    {
        super(18, 1);
    }

    public SetBkMode(int mode)
    {
        this();
        this.mode = mode;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new SetBkMode(emf.readDWORD());
    }

    public String toString()
    {
        return super.toString() + "\n  mode: " + mode;
    }


    public void render(EMFRenderer renderer)
    {
        // The SetBkMode function affects the line styles for lines drawn using a
        // pen created by the CreatePen function. SetBkMode does not affect lines
        // drawn using a pen created by the ExtCreatePen function.
        renderer.setBkMode(mode);
    }
}
