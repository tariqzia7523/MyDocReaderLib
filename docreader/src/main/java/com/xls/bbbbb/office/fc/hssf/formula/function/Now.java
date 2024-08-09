package com.xls.bbbbb.office.fc.hssf.formula.function;

import com.xls.bbbbb.office.fc.hssf.formula.eval.NumberEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.ValueEval;
import com.xls.bbbbb.office.ss.util.DateUtil;

import java.util.Date;


/**
 * Implementation of Excel NOW() Function
 *
 * @author Frank Taffelt
 */
public final class Now extends Fixed0ArgFunction {

	public ValueEval evaluate(int srcRowIndex, int srcColumnIndex) {
		Date now = new Date(System.currentTimeMillis());
		return new NumberEval(DateUtil.getExcelDate(now));
	}
}
