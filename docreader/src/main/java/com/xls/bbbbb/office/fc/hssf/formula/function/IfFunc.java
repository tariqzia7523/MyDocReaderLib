package com.xls.bbbbb.office.fc.hssf.formula.function;

import com.xls.bbbbb.office.fc.hssf.formula.eval.BlankEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.BoolEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.EvaluationException;
import com.xls.bbbbb.office.fc.hssf.formula.eval.MissingArgEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.OperandResolver;
import com.xls.bbbbb.office.fc.hssf.formula.eval.ValueEval;

/**
 * Implementation for the Excel function IF
 *
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt;
 */
public final class IfFunc extends Var2or3ArgFunction {

	public ValueEval evaluate(int srcRowIndex, int srcColumnIndex, ValueEval arg0, ValueEval arg1) {
		boolean b;
		try {
			b = evaluateFirstArg(arg0, srcRowIndex, srcColumnIndex);
		} catch (EvaluationException e) {
			return e.getErrorEval();
		}
		if (b) {
			if (arg1 == MissingArgEval.instance) {
				return BlankEval.instance;
			}
			return arg1;
		}
		return BoolEval.FALSE;
	}

	public ValueEval evaluate(int srcRowIndex, int srcColumnIndex, ValueEval arg0, ValueEval arg1,
			ValueEval arg2) {
		boolean b;
		try {
			b = evaluateFirstArg(arg0, srcRowIndex, srcColumnIndex);
		} catch (EvaluationException e) {
			return e.getErrorEval();
		}
		if (b) {
			if (arg1 == MissingArgEval.instance) {
				return BlankEval.instance;
			}
			return arg1;
		}
		if (arg2 == MissingArgEval.instance) {
			return BlankEval.instance;
		}
		return arg2;
	}

	public static boolean evaluateFirstArg(ValueEval arg, int srcCellRow, int srcCellCol)
			throws EvaluationException {
		ValueEval ve = OperandResolver.getSingleValue(arg, srcCellRow, srcCellCol);
		Boolean b = OperandResolver.coerceValueToBoolean(ve, false);
		if (b == null) {
			return false;
		}
		return b.booleanValue();
	}
}
