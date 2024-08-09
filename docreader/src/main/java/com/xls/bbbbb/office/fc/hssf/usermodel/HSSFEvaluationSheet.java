package com.xls.bbbbb.office.fc.hssf.usermodel;

import com.xls.bbbbb.office.fc.hssf.formula.EvaluationCell;
import com.xls.bbbbb.office.fc.hssf.formula.EvaluationSheet;
import com.xls.bbbbb.office.ss.model.XLSModel.ACell;
import com.xls.bbbbb.office.ss.model.XLSModel.ARow;
import com.xls.bbbbb.office.ss.model.XLSModel.ASheet;

/**
 * HSSF wrapper for a sheet under evaluation
 * 
 * @author Josh Micich
 */
/*final*/ class HSSFEvaluationSheet implements EvaluationSheet {

	private /*final*/ ASheet _hs;

	public HSSFEvaluationSheet(ASheet hs) {
		_hs = hs;
	}

	public void setASheet(ASheet hs)
	{
	    _hs = hs;
	}
	
	public ASheet getASheet() {
		return _hs;
	}
	public EvaluationCell getCell(int rowIndex, int columnIndex) {
		ARow row = (ARow)_hs.getRow(rowIndex);
		if (row == null) {
			return null;
		}
		ACell cell = (ACell)row.getCell(columnIndex);
		if (cell == null) {
			return null;
		}
		return new HSSFEvaluationCell(cell, this);
	}
}
