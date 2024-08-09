package com.xls.bbbbb.office.fc.ss.usermodel.charts;

/**
 * A factory for different chart data types.
 *
 * @author Roman Kashitsyn
 */
public interface ChartDataFactory {
	
	/**
	 * @return an appropriate ScatterChartData instance
	 */
	ScatterChartData createScatterChartData();

}
