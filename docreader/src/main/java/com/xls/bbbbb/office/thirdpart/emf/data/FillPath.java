// Copyright 2002, FreeHEP.

package com.xls.bbbbb.office.thirdpart.emf.data;

import com.xls.bbbbb.office.java.awt.Rectangle;
import com.xls.bbbbb.office.java.awt.geom.GeneralPath;
import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;
import com.xls.bbbbb.office.thirdpart.emf.EMFRenderer;
import com.xls.bbbbb.office.thirdpart.emf.EMFTag;

import java.io.IOException;


public class FillPath extends EMFTag
{

    private Rectangle bounds;

    public FillPath()
    {
        super(62, 1);
    }

    public FillPath(Rectangle bounds)
    {
        this();
        this.bounds = bounds;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new FillPath(emf.readRECTL());
    }

    public String toString()
    {
        return super.toString() + "\n  bounds: " + bounds;
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer)
    {
        GeneralPath currentPath = renderer.getPath();
        // fills the current path
        if (currentPath != null)
        {
            renderer.fillShape(currentPath);
            renderer.setPath(null);
        }
    }
}
