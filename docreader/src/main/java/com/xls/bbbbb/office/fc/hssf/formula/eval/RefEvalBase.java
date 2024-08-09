package com.xls.bbbbb.office.fc.hssf.formula.eval;

/**
 * Common base class for implementors of {@link RefEval}
 *
 * @author Josh Micich
 */
public abstract class RefEvalBase implements RefEval {

	private final int _rowIndex;
	private final int _columnIndex;

	protected RefEvalBase(int rowIndex, int columnIndex) {
		_rowIndex = rowIndex;
		_columnIndex = columnIndex;
	}
	public final int getRow() {
		return _rowIndex;
	}
	public final int getColumn() {
		return _columnIndex;
	}
}
