package com.xls.bbbbb.office.fc.hssf.formula.eval;

import com.xls.bbbbb.office.fc.hssf.formula.function.Fixed1ArgFunction;
import com.xls.bbbbb.office.fc.hssf.formula.function.Function;


/**
 * Implementation of Excel formula token '%'. <p/>
 * @author Josh Micich
 */
public final class PercentEval extends Fixed1ArgFunction {

	public static final Function instance = new PercentEval();

	private PercentEval() {
		// enforce singleton
	}

	public ValueEval evaluate(int srcRowIndex, int srcColumnIndex, ValueEval arg0) {
		double d;
		try {
			ValueEval ve = OperandResolver.getSingleValue(arg0, srcRowIndex, srcColumnIndex);
			d = OperandResolver.coerceValueToDouble(ve);
		} catch (EvaluationException e) {
			return e.getErrorEval();
		}
		if (d == 0.0) { // this '==' matches +0.0 and -0.0
			return NumberEval.ZERO;
		}
		return new NumberEval(d / 100);
	}
}
