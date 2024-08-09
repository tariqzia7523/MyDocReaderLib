package com.xls.bbbbb.office.fc.hssf.formula.function;


import com.xls.bbbbb.office.fc.hssf.formula.TwoDEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.BlankEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.NumberEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.RefEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.ValueEval;
import com.xls.bbbbb.office.fc.hssf.formula.function.CountUtils.I_MatchPredicate;


public final class Countblank extends Fixed1ArgFunction {

	public ValueEval evaluate(int srcRowIndex, int srcColumnIndex, ValueEval arg0) {

		double result;
		if (arg0 instanceof RefEval) {
			result = CountUtils.countMatchingCell((RefEval) arg0, predicate);
		} else if (arg0 instanceof TwoDEval) {
			result = CountUtils.countMatchingCellsInArea((TwoDEval) arg0, predicate);
		} else {
			throw new IllegalArgumentException("Bad range arg type (" + arg0.getClass().getName() + ")");
		}
		return new NumberEval(result);
	}

	private static final I_MatchPredicate predicate = new I_MatchPredicate() {

		public boolean matches(ValueEval valueEval) {
			// Note - only BlankEval counts
			return valueEval == BlankEval.instance;
		}
	};
}
