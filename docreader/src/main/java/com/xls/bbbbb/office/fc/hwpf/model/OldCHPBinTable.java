package com.xls.bbbbb.office.fc.hwpf.model;

import com.xls.bbbbb.office.fc.poifs.common.POIFSConstants;
import com.xls.bbbbb.office.fc.util.Internal;
import com.xls.bbbbb.office.fc.util.LittleEndian;

import java.util.Collections;


/**
 * This class holds all of the character formatting 
 *  properties from Old (Word 6 / Word 95) documents.
 * Unlike with Word 97+, it all gets held in the
 *  same stream.
 * In common with the rest of the old support, it 
 *  is read only
 */
@ Internal
public final class OldCHPBinTable extends CHPBinTable
{
    /**
     * Constructor used to read an old-style binTable
     *  in from a Word document.
     *
     * @param documentStream
     * @param offset
     * @param size
     * @param fcMin
     */
    public OldCHPBinTable(byte[] documentStream, int offset, int size, int fcMin, TextPieceTable tpt)
    {
        PlexOfCps binTable = new PlexOfCps(documentStream, offset, size, 2);

        int length = binTable.length();
        for (int x = 0; x < length; x++)
        {
            GenericPropertyNode node = binTable.getProperty(x);

            int pageNum = LittleEndian.getShort(node.getBytes());
            int pageOffset = POIFSConstants.SMALLER_BIG_BLOCK_SIZE * pageNum;

            CHPFormattedDiskPage cfkp = new CHPFormattedDiskPage(documentStream, pageOffset, tpt);

            int fkpSize = cfkp.size();

            for (int y = 0; y < fkpSize; y++)
            {
                CHPX chpx = cfkp.getCHPX(y);
                if (chpx != null)
                    _textRuns.add(chpx);
            }
        }
        Collections.sort(_textRuns, PropertyNode.StartComparator.instance);
    }
}
