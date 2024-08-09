package com.xls.bbbbb.office.fc.hslf.record;

import java.util.Hashtable;

/**
 * A special (and dangerous) kind of Record Container, for which other
 *  Atoms care about where this one lives on disk.
 * Will track its position on disk.
 *
 * @author Nick Burch
 */

public abstract class PositionDependentRecordContainer extends RecordContainer implements
    PositionDependentRecord
{
    private int sheetId; // Found from PersistPtrHolder

    /**
     * Fetch our sheet ID, as found from a PersistPtrHolder.
     * Should match the RefId of our matching SlidePersistAtom
     */
    public int getSheetId()
    {
        return sheetId;
    }

    /**
     * Set our sheet ID, as found from a PersistPtrHolder
     */
    public void setSheetId(int id)
    {
        sheetId = id;
    }

    /** Our location on the disk, as of the last write out */
    protected int myLastOnDiskOffset;

    /** Fetch our location on the disk, as of the last write out */
    public int getLastOnDiskOffset()
    {
        return myLastOnDiskOffset;
    }

    /**
     * Update the Record's idea of where on disk it lives, after a write out.
     * Use with care...
     */
    public void setLastOnDiskOffset(int offset)
    {
        myLastOnDiskOffset = offset;
    }

    /**
     * Since we're a container, we don't mind if other records move about.
     * If we're told they have, just return straight off.
     */
    public void updateOtherRecordReferences(Hashtable<Integer, Integer> oldToNewReferencesLookup)
    {
        return;
    }
}
