package com.xls.bbbbb.office.fc.hslf;

import com.xls.bbbbb.office.fc.hslf.exceptions.CorruptPowerPointFileException;
import com.xls.bbbbb.office.fc.hslf.record.CurrentUserAtom;
import com.xls.bbbbb.office.fc.hslf.record.DocumentEncryptionAtom;
import com.xls.bbbbb.office.fc.hslf.record.PersistPtrHolder;
import com.xls.bbbbb.office.fc.hslf.record.Record;
import com.xls.bbbbb.office.fc.hslf.record.UserEditAtom;


/**
 * This class provides helper functions for determining if a
 *  PowerPoint document is Encrypted.
 * In future, it may also provide Encryption and Decryption
 *  functions, but first we'd need to figure out how
 *  PowerPoint encryption is really done!
 *
 * @author Nick Burch
 */

public final class EncryptedSlideShow
{
    /**
     * Check to see if a HSLFSlideShow represents an encrypted
     *  PowerPoint document, or not
     * @param hss The HSLFSlideShow to check
     * @return true if encrypted, otherwise false
     */
    public static boolean checkIfEncrypted(HSLFSlideShow hss)
    {
        // Easy way to check - contains a stream
        //  "EncryptedSummary"
        /*try
        {
            hss.getPOIFSDirectory().getEntry("EncryptedSummary");
            return true;
        }
        catch(FileNotFoundException fnfe)
        {
            // Doesn't have encrypted properties
        }

        // If they encrypted the document but not the properties,
        //  it's harder.
        // We need to see what the last record pointed to by the
        //  first PersistPrtHolder is - if it's a
        //  DocumentEncryptionAtom, then the file's Encrypted
        DocumentEncryptionAtom dea = fetchDocumentEncryptionAtom(hss);
        if (dea != null)
        {
            return true;
        }*/
        return false;
    }

    /**
     * Return the DocumentEncryptionAtom for a HSLFSlideShow, or
     *  null if there isn't one.
     * @return a DocumentEncryptionAtom, or null if there isn't one
     */
    public static DocumentEncryptionAtom fetchDocumentEncryptionAtom(HSLFSlideShow hss)
    {
        // Will be the last Record pointed to by the
        //  first PersistPrtHolder, if there is one

        CurrentUserAtom cua = hss.getCurrentUserAtom();
        if (cua.getCurrentEditOffset() != 0)
        {
            // Check it's not past the end of the file
            if (cua.getCurrentEditOffset() > hss.getUnderlyingBytes().length)
            {
                throw new CorruptPowerPointFileException(
                    "The CurrentUserAtom claims that the offset of last edit details are past the end of the file");
            }

            // Grab the details of the UserEditAtom there
            // If the record's messed up, we could AIOOB
            Record r = null;
            try
            {
                r = Record.buildRecordAtOffset(hss.getUnderlyingBytes(),
                    (int)cua.getCurrentEditOffset());
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                return null;
            }
            if (r == null)
            {
                return null;
            }
            if (!(r instanceof UserEditAtom))
            {
                return null;
            }
            UserEditAtom uea = (UserEditAtom)r;

            // Now get the PersistPtrHolder
            Record r2 = Record.buildRecordAtOffset(hss.getUnderlyingBytes(),
                uea.getPersistPointersOffset());
            if (!(r2 instanceof PersistPtrHolder))
            {
                return null;
            }
            PersistPtrHolder pph = (PersistPtrHolder)r2;

            // Now get the last record
            int[] slideIds = pph.getKnownSlideIDs();
            int maxSlideId = -1;
            for (int i = 0; i < slideIds.length; i++)
            {
                if (slideIds[i] > maxSlideId)
                {
                    maxSlideId = slideIds[i];
                }
            }
            if (maxSlideId == -1)
            {
                return null;
            }

            int offset = ((Integer)pph.getSlideLocationsLookup().get(Integer.valueOf(maxSlideId)))
                .intValue();
            Record r3 = Record.buildRecordAtOffset(hss.getUnderlyingBytes(), offset);

            // If we have a DocumentEncryptionAtom, it'll be this one
            if (r3 instanceof DocumentEncryptionAtom)
            {
                return (DocumentEncryptionAtom)r3;
            }
        }

        return null;
    }
}
