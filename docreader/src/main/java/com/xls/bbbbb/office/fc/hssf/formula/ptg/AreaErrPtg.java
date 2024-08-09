package com.xls.bbbbb.office.fc.hssf.formula.ptg;


import com.xls.bbbbb.office.fc.ss.usermodel.ErrorConstants;
import com.xls.bbbbb.office.fc.util.LittleEndianInput;
import com.xls.bbbbb.office.fc.util.LittleEndianOutput;


/**
 * AreaErr - handles deleted cell area references.
 *
 * @author Daniel Noll (daniel at nuix dot com dot au)
 */
public final class AreaErrPtg extends OperandPtg {
	public final static byte sid = 0x2B;
	private final int unused1;
	private final int unused2;

	public AreaErrPtg() {
		unused1 = 0;
		unused2 = 0;
	}

	public AreaErrPtg(LittleEndianInput in)  {
		// 8 bytes unused:
		unused1 = in.readInt();
		unused2 = in.readInt();
	}

	public void write(LittleEndianOutput out) {
		out.writeByte(sid + getPtgClass());
		out.writeInt(unused1);
		out.writeInt(unused2);
	}

	public String toFormulaString() {
		return ErrorConstants.getText(ErrorConstants.ERROR_REF);
	}

	public byte getDefaultOperandClass() {
		return Ptg.CLASS_REF;
	}

	public int getSize() {
		return 9;
	}
}

