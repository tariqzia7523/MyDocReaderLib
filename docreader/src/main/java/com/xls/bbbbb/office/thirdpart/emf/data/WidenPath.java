// Copyright 2002, FreeHEP.
package com.xls.bbbbb.office.thirdpart.emf.data;

import com.xls.bbbbb.office.java.awt.Stroke;
import com.xls.bbbbb.office.java.awt.geom.GeneralPath;
import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;
import com.xls.bbbbb.office.thirdpart.emf.EMFRenderer;
import com.xls.bbbbb.office.thirdpart.emf.EMFTag;

import java.io.IOException;


public class WidenPath extends EMFTag {

    public WidenPath() {
        super(66, 1);
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len)
            throws IOException {

        return this;
    }

    /**
     * displays the tag using the renderer
     *
     * @param renderer EMFRenderer storing the drawing session data
     */
    public void render(EMFRenderer renderer) {
        GeneralPath currentPath = renderer.getPath();
        Stroke currentPenStroke = renderer.getPenStroke();
        // The WidenPath function redefines the current path as the area
        // that would be painted if the path were stroked using the pen
        // currently selected into the given device context.
        if (currentPath != null && currentPenStroke != null) {
            GeneralPath newPath = new GeneralPath(
                renderer.getWindingRule());
            newPath.append(currentPenStroke.createStrokedShape(currentPath), false);
            renderer.setPath(newPath);
        }
    }
}
