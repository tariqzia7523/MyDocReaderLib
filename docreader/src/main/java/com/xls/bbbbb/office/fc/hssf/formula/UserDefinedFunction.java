package com.xls.bbbbb.office.fc.hssf.formula;


import com.xls.bbbbb.office.fc.hssf.formula.eval.ErrorEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.NameEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.NameXEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.ValueEval;
import com.xls.bbbbb.office.fc.hssf.formula.function.FreeRefFunction;
import com.xls.bbbbb.office.fc.ss.usermodel.ErrorConstants;

/**
 *
 * Common entry point for all user-defined (non-built-in) functions (where
 * <tt>AbstractFunctionPtg.field_2_fnc_index</tt> == 255)
 *
 * @author Josh Micich
 * @author Petr Udalau - Improved resolving of UDFs through the ToolPacks.
 */
final class UserDefinedFunction implements FreeRefFunction {

	public static final FreeRefFunction instance = new UserDefinedFunction();

	private UserDefinedFunction() {
		// enforce singleton
	}

	public ValueEval evaluate(ValueEval[] args, OperationEvaluationContext ec) {
		int nIncomingArgs = args.length;
		if(nIncomingArgs < 1) {
			throw new RuntimeException("function name argument missing");
		}

		ValueEval nameArg = args[0];
		String functionName;
		if (nameArg instanceof NameEval) {
			functionName = ((NameEval) nameArg).getFunctionName();
		} else if (nameArg instanceof NameXEval) {
			functionName = ec.getWorkbook().resolveNameXText(((NameXEval) nameArg).getPtg());
		} else {
			throw new RuntimeException("First argument should be a NameEval, but got ("
					+ nameArg.getClass().getName() + ")");
		}
		FreeRefFunction targetFunc = ec.findUserDefinedFunction(functionName);
		if (targetFunc == null) {
		    return ErrorEval.valueOf(ErrorConstants.ERROR_NAME);
			//throw new NotImplementedException(functionName);
		}
		int nOutGoingArgs = nIncomingArgs -1;
		ValueEval[] outGoingArgs = new ValueEval[nOutGoingArgs];
		System.arraycopy(args, 1, outGoingArgs, 0, nOutGoingArgs);
		return targetFunc.evaluate(outGoingArgs, ec);
	}
}
