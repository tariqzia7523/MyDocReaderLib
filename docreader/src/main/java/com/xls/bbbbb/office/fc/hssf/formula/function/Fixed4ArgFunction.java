package com.xls.bbbbb.office.fc.hssf.formula.function;

import com.xls.bbbbb.office.fc.hssf.formula.eval.ErrorEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.ValueEval;

/**
 * Convenience base class for functions that must take exactly four arguments.
 *
 * @author Josh Micich
 */
public abstract class Fixed4ArgFunction implements Function4Arg {
	public final ValueEval evaluate(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
		if (args.length != 4) {
			return ErrorEval.VALUE_INVALID;
		}
		return evaluate(srcRowIndex, srcColumnIndex, args[0], args[1], args[2], args[3]);
	}
}
