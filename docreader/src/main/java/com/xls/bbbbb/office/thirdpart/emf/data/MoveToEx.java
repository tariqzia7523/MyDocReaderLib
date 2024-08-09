// Copyright 2002, FreeHEP.

package com.xls.bbbbb.office.thirdpart.emf.data;

import android.graphics.Point;

import com.xls.bbbbb.office.java.awt.geom.GeneralPath;
import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;
import com.xls.bbbbb.office.thirdpart.emf.EMFRenderer;
import com.xls.bbbbb.office.thirdpart.emf.EMFTag;

import java.io.IOException;


public class MoveToEx extends EMFTag
{

    private Point point;

    public MoveToEx()
    {
        super(27, 1);
    }

    public MoveToEx(Point point)
    {
        this();
        this.point = point;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        return new MoveToEx(emf.readPOINTL());
    }

    public String toString()
    {
        return super.toString() + "\n  point: " + point;
    }

    public void render(EMFRenderer renderer)
    {
        // The MoveToEx function updates the current position to the
        // specified point
        // and optionally returns the previous position.
        GeneralPath currentFigure = new GeneralPath(renderer.getWindingRule());
        currentFigure.moveTo((float)point.x, (float)point.y);
        renderer.setFigure(currentFigure);
    }
}
