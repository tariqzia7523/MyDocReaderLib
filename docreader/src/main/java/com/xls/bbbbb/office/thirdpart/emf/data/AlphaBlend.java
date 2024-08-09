// Copyright 2002-2007, FreeHEP.

package com.xls.bbbbb.office.thirdpart.emf.data;

import android.graphics.Bitmap;

import com.xls.bbbbb.office.java.awt.Color;
import com.xls.bbbbb.office.java.awt.Rectangle;
import com.xls.bbbbb.office.java.awt.geom.AffineTransform;
import com.xls.bbbbb.office.thirdpart.emf.EMFConstants;
import com.xls.bbbbb.office.thirdpart.emf.EMFImageLoader;
import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;
import com.xls.bbbbb.office.thirdpart.emf.EMFRenderer;
import com.xls.bbbbb.office.thirdpart.emf.EMFTag;

import java.io.IOException;


public class AlphaBlend extends EMFTag implements EMFConstants
{

    private final static int size = 108;

    private Rectangle bounds;

    private int x, y, width, height;

    private BlendFunction dwROP;

    private int xSrc, ySrc;

    private AffineTransform transform;

    private Color bkg;

    private int usage;

    private BitmapInfo bmi;

    //private BufferedImage image;
    private Bitmap image;

    public AlphaBlend()
    {
        super(114, 1);
    }

    public AlphaBlend(Rectangle bounds, int x, int y, int width, int height,
        AffineTransform transform, Bitmap image, Color bkg)
    {

        this();
        this.bounds = bounds;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dwROP = new BlendFunction(AC_SRC_OVER, 0, 0xFF, AC_SRC_ALPHA);
        this.xSrc = 0;
        this.ySrc = 0;
        this.transform = transform;
        this.bkg = (bkg == null) ? new Color(0, 0, 0, 0) : bkg;
        this.usage = DIB_RGB_COLORS;
        this.image = image;
        this.bmi = null;
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
    {

        AlphaBlend tag = new AlphaBlend();
        tag.bounds = emf.readRECTL(); // 16
        tag.x = emf.readLONG(); // 20
        tag.y = emf.readLONG(); // 24
        tag.width = emf.readLONG(); // 28
        tag.height = emf.readLONG(); // 32
        tag.dwROP = new BlendFunction(emf); // 36
        tag.xSrc = emf.readLONG(); // 40
        tag.ySrc = emf.readLONG(); // 44
        tag.transform = emf.readXFORM(); // 68
        tag.bkg = emf.readCOLORREF(); // 72
        tag.usage = emf.readDWORD(); // 76

        // ignored
        /* int bmiOffset = */emf.readDWORD(); // 80
        int bmiSize = emf.readDWORD(); // 84
        /* int bitmapOffset = */emf.readDWORD(); // 88
        int bitmapSize = emf.readDWORD(); // 92

        /* int width = */emf.readLONG(); // 96
        /* int height = */emf.readLONG(); // 100

        // FIXME: this size can differ and can be placed somewhere else
        tag.bmi = (bmiSize > 0) ? new BitmapInfo(emf) : null;

        tag.image = EMFImageLoader.readImage(tag.bmi.getHeader(), tag.width, tag.height, emf,
            /*bitmapSize*/len - 100 - BitmapInfoHeader.size, tag.dwROP);

        return tag;
    }

    public String toString()
    {
        return super.toString() + "\n  bounds: " + bounds + "\n  x, y, w, h: " + x + " " + y + " "
            + width + " " + height + "\n  dwROP: " + dwROP + "\n  xSrc, ySrc: " + xSrc + " " + ySrc
            + "\n  transform: " + transform + "\n  bkg: " + bkg + "\n  usage: " + usage + "\n"
            + ((bmi != null) ? bmi.toString() : "  bitmap: null");
    }


    public void render(EMFRenderer renderer)
    {
        // This function displays bitmaps that have transparent or semitransparent pixels.
        if (image != null)
        {
            renderer.drawImage(image, x, y, width, height);
        }
    }
}
