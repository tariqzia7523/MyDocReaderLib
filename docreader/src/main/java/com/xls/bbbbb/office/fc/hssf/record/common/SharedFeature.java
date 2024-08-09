package com.xls.bbbbb.office.fc.hssf.record.common;

import com.xls.bbbbb.office.fc.util.LittleEndianOutput;

/**
 * Common Interface for all Shared Features
 */
public interface SharedFeature {
	public String toString();
	public void serialize(LittleEndianOutput out);
	public int getDataSize();
}
