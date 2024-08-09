// Copyright 2002, FreeHEP.

package com.xls.bbbbb.office.thirdpart.emf.data;

import android.graphics.Point;

import com.xls.bbbbb.office.java.awt.Rectangle;
import com.xls.bbbbb.office.thirdpart.emf.EMFConstants;
import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;
import com.xls.bbbbb.office.thirdpart.emf.EMFTag;

import java.io.IOException;


public class PolyDraw extends EMFTag implements EMFConstants
{

    private Rectangle bounds;

    private Point[] points;

    private byte[] types;

    public PolyDraw()
    {
        super(56, 1);
    }

    public PolyDraw(Rectangle bounds, Point[] points, byte[] types)
    {
        this();
        this.bounds = bounds;
        this.points = points;
        this.types = types;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        int n;
        return new PolyDraw(emf.readRECTL(), emf.readPOINTL(n = emf.readDWORD()), emf.readBYTE(n));
    }

    public String toString()
    {
        return super.toString() + "\n  bounds: " + bounds + "\n  #points: " + points.length;
    }
}
