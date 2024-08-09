package com.xls.bbbbb.office.fc.hssf.formula.function;

import com.xls.bbbbb.office.fc.hssf.formula.SheetNameFormatter;
import com.xls.bbbbb.office.fc.hssf.formula.eval.ErrorEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.EvaluationException;
import com.xls.bbbbb.office.fc.hssf.formula.eval.MissingArgEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.OperandResolver;
import com.xls.bbbbb.office.fc.hssf.formula.eval.StringEval;
import com.xls.bbbbb.office.fc.hssf.formula.eval.ValueEval;
import com.xls.bbbbb.office.fc.ss.util.CellReference;


/**
 * Creates a text reference as text, given specified row and column numbers.
 *
 * @author Aniket Banerjee (banerjee@google.com)
 */
public class Address implements Function {
    public static final int REF_ABSOLUTE = 1;
    public static final int REF_ROW_ABSOLUTE_COLUMN_RELATIVE = 2;
    public static final int REF_ROW_RELATIVE_RELATIVE_ABSOLUTE = 3;
    public static final int REF_RELATIVE = 4;

    public ValueEval evaluate(ValueEval[] args, int srcRowIndex,
                              int srcColumnIndex) {
        if(args.length < 2 || args.length > 5) {
            return ErrorEval.VALUE_INVALID;
        }
        try {
            boolean pAbsRow, pAbsCol;

            int row =  (int)NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            int col =  (int)NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);

            int refType;
            if(args.length > 2){
                refType = (int)NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            } else {
                refType = REF_ABSOLUTE;
            }
            switch (refType){
                case REF_ABSOLUTE:
                    pAbsRow = true;
                    pAbsCol = true;
                    break;
                case REF_ROW_ABSOLUTE_COLUMN_RELATIVE:
                    pAbsRow = true;
                    pAbsCol = false;
                    break;
                case REF_ROW_RELATIVE_RELATIVE_ABSOLUTE:
                    pAbsRow = false;
                    pAbsCol = true;
                    break;
                case REF_RELATIVE:
                    pAbsRow = false;
                    pAbsCol = false;
                    break;
                default:
                    throw new EvaluationException(ErrorEval.VALUE_INVALID);
            }

            boolean a1;
            if(args.length > 3){
                ValueEval ve = OperandResolver.getSingleValue(args[3], srcRowIndex, srcColumnIndex);
                // TODO R1C1 style is not yet supported
                a1 = ve == MissingArgEval.instance ? true : OperandResolver.coerceValueToBoolean(ve, false);
            } else {
                a1 = true;
            }

            String sheetName;
            if(args.length == 5){
                ValueEval ve = OperandResolver.getSingleValue(args[4], srcRowIndex, srcColumnIndex);
                sheetName = ve == MissingArgEval.instance ? null : OperandResolver.coerceValueToString(ve);
            } else {
                sheetName = null;
            }

            CellReference ref = new CellReference(row - 1, col - 1, pAbsRow, pAbsCol);
            StringBuffer sb = new StringBuffer(32);
            if(sheetName != null) {
                SheetNameFormatter.appendFormat(sb, sheetName);
                sb.append('!');
            }
            sb.append(ref.formatAsString());

            return new StringEval(sb.toString());

        } catch (EvaluationException e){
            return e.getErrorEval();
        }
    }
}
