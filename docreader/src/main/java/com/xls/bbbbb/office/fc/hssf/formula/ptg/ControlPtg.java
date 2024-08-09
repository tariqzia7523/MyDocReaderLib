package com.xls.bbbbb.office.fc.hssf.formula.ptg;

/**
 * Common superclass for 
 * tExp
 * tTbl
 * tParen
 * tNlr
 * tAttr
 * tSheet
 * tEndSheet
 */
public abstract class ControlPtg extends Ptg {

	public boolean isBaseToken() {
		return true;
	}
	public final byte getDefaultOperandClass() {
		throw new IllegalStateException("Control tokens are not classified");
	}
}
