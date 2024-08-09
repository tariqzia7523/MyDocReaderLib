// Copyright 2002, FreeHEP.
package com.xls.bbbbb.office.thirdpart.emf.data;

import com.xls.bbbbb.office.thirdpart.emf.EMFInputStream;
import com.xls.bbbbb.office.thirdpart.emf.EMFRenderer;
import com.xls.bbbbb.office.thirdpart.emf.EMFTag;

import java.io.IOException;


public class SetMetaRgn extends EMFTag {

    public SetMetaRgn() {
        super(28, 1);
    }

    public EMFTag read(int tagID, EMFInputStream emf, int len)
            throws IOException {

        return this;
    }

    public void render(EMFRenderer renderer) {
        // The SetMetaRgn function intersects the current clipping region
        // for the specified device context with the current metaregion and
        // saves the combined region as the new metaregion for the specified
        // device context. The clipping region is reset to a null region.

        // TODO: what ist the current metaregion?
    }
}
