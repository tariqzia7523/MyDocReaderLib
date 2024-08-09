package com.xls.bbbbb.office.fc.hssf.formula.udf;


import com.xls.bbbbb.office.fc.hssf.formula.function.FreeRefFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Collects add-in libraries and VB macro functions together into one UDF finder
 *
 * @author PUdalau
 */
public class AggregatingUDFFinder implements UDFFinder {

	private final Collection<UDFFinder> _usedToolPacks;

	public AggregatingUDFFinder(UDFFinder ... usedToolPacks) {
        _usedToolPacks = new ArrayList<UDFFinder>(usedToolPacks.length);
		_usedToolPacks.addAll(Arrays.asList(usedToolPacks));
	}

	/**
	 * Returns executor by specified name. Returns <code>null</code> if
	 * function isn't contained by any registered tool pack.
	 *
	 * @param name Name of function.
	 * @return Function executor. <code>null</code> if not found
	 */
	public FreeRefFunction findFunction(String name) {
		FreeRefFunction evaluatorForFunction;
		for (UDFFinder pack : _usedToolPacks) {
			evaluatorForFunction = pack.findFunction(name);
			if (evaluatorForFunction != null) {
				return evaluatorForFunction;
			}
		}
		return null;
	}

    /**
     * Add a new toolpack
     *
     * @param toolPack the UDF toolpack to add
     */
    public void add(UDFFinder toolPack){
        _usedToolPacks.add(toolPack);
    }
}
