package com.xls.bbbbb.office.fc.hslf.record;
import java.util.Hashtable;

/**
 * A special (and dangerous) kind of Record Atom that cares about where
 *  it lives on the disk, or who has other Atoms that care about where
 *  this is on the disk.
 *
 * @author Nick Burch
 */

public abstract class PositionDependentRecordAtom extends RecordAtom implements PositionDependentRecord
{
	/** Our location on the disk, as of the last write out */
	protected int myLastOnDiskOffset;

	/** Fetch our location on the disk, as of the last write out */
	public int getLastOnDiskOffset() { return myLastOnDiskOffset; }

	/**
	 * Update the Record's idea of where on disk it lives, after a write out.
	 * Use with care...
	 */
	public void setLastOnDiskOffset(int offset) {
		myLastOnDiskOffset = offset;
	}

	/**
	 * Offer the record the list of records that have changed their
	 *  location as part of the writeout.
	 * Allows records to update their internal pointers to other records
	 *  locations
	 */
	public abstract void updateOtherRecordReferences(Hashtable<Integer,Integer> oldToNewReferencesLookup);
}
