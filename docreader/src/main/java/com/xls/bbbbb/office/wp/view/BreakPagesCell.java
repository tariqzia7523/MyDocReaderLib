
package com.xls.bbbbb.office.wp.view;

import com.xls.bbbbb.office.wp.model.CellElement;


public class BreakPagesCell
{

    public BreakPagesCell(CellElement cell, long breakOffset)
    {
        this.cell = cell;
        this.breakOffset = breakOffset;
    }
    
    
    /**
     * 
     */
    public CellElement getCell()
    {
        return cell;
    }
    
    /**
     * 
     */
    public long getBreakOffset()
    {
        return breakOffset;
    }
    
    // across patges cell;
    private CellElement cell;
    // across pages offset
    private long breakOffset;
    
}
