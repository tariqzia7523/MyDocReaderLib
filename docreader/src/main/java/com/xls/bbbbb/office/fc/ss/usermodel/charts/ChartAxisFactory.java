package com.xls.bbbbb.office.fc.ss.usermodel.charts;

/**
 * A factory for different chart axis.
 *
 * @author Roman Kashitsyn
 */
public interface ChartAxisFactory {
	
	/**
	 * @return new value axis
	 */
	ValueAxis createValueAxis(AxisPosition pos);

}
