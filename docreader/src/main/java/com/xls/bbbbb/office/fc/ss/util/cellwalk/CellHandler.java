package com.xls.bbbbb.office.fc.ss.util.cellwalk;

import com.xls.bbbbb.office.fc.ss.usermodel.ICell;

/**
 * Represents callback for CellWalk traverse method.  
 * @author Roman Kashitsyn
 */
public interface CellHandler {

    /**
     * @param cell current cell
     * @param ctx information about invokation context
     */
    void onCell(ICell cell, CellWalkContext ctx);

}
