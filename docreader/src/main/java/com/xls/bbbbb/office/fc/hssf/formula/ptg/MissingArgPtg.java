package com.xls.bbbbb.office.fc.hssf.formula.ptg;

import com.xls.bbbbb.office.fc.util.LittleEndianOutput;

/**
 * Missing Function Arguments
 * 
 * Avik Sengupta &lt;avik at apache.org&gt;
 * 
 * @author Jason Height (jheight at chariot dot net dot au)
 */
public final class MissingArgPtg extends ScalarConstantPtg {

	private final static int SIZE = 1;
	public final static byte sid = 0x16;

	public static final Ptg instance = new MissingArgPtg();

	private MissingArgPtg() {
		// enforce singleton
	}

	public void write(LittleEndianOutput out) {
		out.writeByte(sid + getPtgClass());
	}

	public int getSize() {
		return SIZE;
	}

	public String toFormulaString() {
		return " ";
	}
}
