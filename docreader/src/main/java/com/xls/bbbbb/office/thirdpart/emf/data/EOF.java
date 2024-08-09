// Copyright 2002, FreeHEP.

package com.xls.bbbbb.office.thirdpart.emf.data;

import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;
import com.xls.bbbbb.office.thirdpart.emf.EMFRenderer;
import com.xls.bbbbb.office.thirdpart.emf.EMFTag;

import java.io.IOException;

/**
 * Rectangle TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: EOF.java 10367 2007-01-22 19:26:48Z duns $
 */
public class EOF extends EMFTag
{

    public EOF()
    {
        super(14, 1);
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        /* int[] bytes = */emf.readUnsignedByte(len);
        return new EOF();
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer)
    {
        // do nothing
    }
}
