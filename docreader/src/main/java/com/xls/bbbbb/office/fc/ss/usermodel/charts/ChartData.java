package com.xls.bbbbb.office.fc.ss.usermodel.charts;

import com.xls.bbbbb.office.fc.ss.usermodel.Chart;

/**
 * A base for all chart data types.
 *
 * @author Roman Kashitsyn
 */
public interface ChartData {

	/**
	 * Fills a chart with data specified by implementation.
	 *
	 * @param chart a chart to fill in
	 * @param axis chart axis to use
	 */
	void fillChart(Chart chart, ChartAxis... axis);
}
