// Copyright 2002, FreeHEP.

package com.xls.bbbbb.office.thirdpart.emf.data;

import android.graphics.Point;

import com.xls.bbbbb.office.java.awt.Rectangle;
import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;
import com.xls.bbbbb.office.thirdpart.emf.EMFTag;

import java.io.IOException;


public class PolylineTo16 extends PolylineTo
{

    public PolylineTo16()
    {
        super(89, 1, null, 0, null);
    }

    public PolylineTo16(Rectangle bounds, int numberOfPoints, Point[] points)
    {
        super(89, 1, bounds, numberOfPoints, points);
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        Rectangle r = emf.readRECTL();
        int n = emf.readDWORD();
        return new PolylineTo16(r, n, emf.readPOINTS(n));
    }

}
