package com.xls.bbbbb.office.fc.hssf.formula.function;


import com.xls.bbbbb.office.fc.hssf.formula.eval.ErrorEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.EvaluationException;
import com.xls.bbbbb.office.fc.hssf.formula.eval.NumberEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.OperandResolver;
import com.xls.bbbbb.office.fc.hssf.formula.eval.ValueEval;
import com.xls.bbbbb.office.fc.ss.usermodel.ErrorConstants;


/**
 * Implementation for the ERROR.TYPE() Excel function.
 * <p>
 * <b>Syntax:</b><br/>
 * <b>ERROR.TYPE</b>(<b>errorValue</b>)</p>
 * <p>
 * Returns a number corresponding to the error type of the supplied argument.<p/>
 * <p>
 *    <table border="1" cellpadding="1" cellspacing="1" summary="Return values for ERROR.TYPE()">
 *      <tr><td>errorValue</td><td>Return Value</td></tr>
 *      <tr><td>#NULL!</td><td>1</td></tr>
 *      <tr><td>#DIV/0!</td><td>2</td></tr>
 *      <tr><td>#VALUE!</td><td>3</td></tr>
 *      <tr><td>#REF!</td><td>4</td></tr>
 *      <tr><td>#NAME?</td><td>5</td></tr>
 *      <tr><td>#NUM!</td><td>6</td></tr>
 *      <tr><td>#N/A!</td><td>7</td></tr>
 *      <tr><td>everything else</td><td>#N/A!</td></tr>
 *    </table>
 *
 * Note - the results of ERROR.TYPE() are different to the constants defined in
 * <tt>ErrorConstants</tt>.
 * </p>
 *
 * @author Josh Micich
 */
public final class Errortype extends Fixed1ArgFunction {

	public ValueEval evaluate(int srcRowIndex, int srcColumnIndex, ValueEval arg0) {

		try {
			OperandResolver.getSingleValue(arg0, srcRowIndex, srcColumnIndex);
			return ErrorEval.NA;
		} catch (EvaluationException e) {
			int result = translateErrorCodeToErrorTypeValue(e.getErrorEval().getErrorCode());
			return new NumberEval(result);
		}
	}

	private int translateErrorCodeToErrorTypeValue(int errorCode) {
		switch (errorCode) {
			case ErrorConstants.ERROR_NULL:  return 1;
			case ErrorConstants.ERROR_DIV_0: return 2;
			case ErrorConstants.ERROR_VALUE: return 3;
			case ErrorConstants.ERROR_REF:   return 4;
			case ErrorConstants.ERROR_NAME:  return 5;
			case ErrorConstants.ERROR_NUM:   return 6;
			case ErrorConstants.ERROR_NA :   return 7;
		}
		throw new IllegalArgumentException("Invalid error code (" + errorCode + ")");
	}

}
