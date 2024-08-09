package com.xls.bbbbb.office.fc.hssf.formula;

import com.xls.bbbbb.office.fc.hssf.formula.eval.ValueEval;


final class PlainValueCellCacheEntry extends CellCacheEntry {
	public PlainValueCellCacheEntry(ValueEval value) {
		updateValue(value);
	}
}
