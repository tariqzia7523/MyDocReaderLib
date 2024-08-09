package com.xls.bbbbb.office.fc.hssf.formula.eval;

import com.xls.bbbbb.office.fc.hssf.formula.function.Fixed1ArgFunction;
import com.xls.bbbbb.office.fc.hssf.formula.function.Function;

/**
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt;
 */
public final class UnaryMinusEval extends Fixed1ArgFunction {

	public static final Function instance = new UnaryMinusEval();

	private UnaryMinusEval() {
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
		return new NumberEval(-d);
	}
}
