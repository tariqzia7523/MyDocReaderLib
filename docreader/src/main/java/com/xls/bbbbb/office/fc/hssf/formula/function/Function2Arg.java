package com.xls.bbbbb.office.fc.hssf.formula.function;

import com.xls.bbbbb.office.fc.hssf.formula.eval.ValueEval;

/**
 * Implemented by all functions that can be called with two arguments
 *
 * @author Josh Micich
 */
public interface Function2Arg extends Function {
	/**
	 * see {@link Function#evaluate(ValueEval[], int, int)}
	 */
	ValueEval evaluate(int srcRowIndex, int srcColumnIndex, ValueEval arg0, ValueEval arg1);
}
