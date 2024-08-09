
package com.xls.bbbbb.office.fc.hssf.record;

import com.xls.bbbbb.office.fc.util.LittleEndianOutput;

/**
 * Title:        Gridset Record.<P>
 * Description:  flag denoting whether the user specified that gridlines are used when
 *               printing.<P>
 * REFERENCE:  PG 320 Microsoft Excel 97 Developer's Kit (ISBN: 1-57231-498-2)<P>
 *
 * @author Andrew C. Oliver (acoliver at apache dot org)
 * @author  Glen Stampoultzis (glens at apache.org)
 * @author Jason Height (jheight at chariot dot net dot au)
 *
 * @version 2.0-pre
 */

public final class GridsetRecord
    extends StandardRecord
{
    public final static short sid = 0x82;
    public short              field_1_gridset_flag;

    public GridsetRecord()
    {
    }

    public GridsetRecord(RecordInputStream in)
    {
        field_1_gridset_flag = in.readShort();
    }

    /**
     * set whether gridlines are visible when printing
     *
     * @param gridset - <b>true</b> if no gridlines are print, <b>false</b> if gridlines are not print.
     */

    public void setGridset(boolean gridset)
    {
        if (gridset == true)
        {
            field_1_gridset_flag = 1;
        }
        else
        {
            field_1_gridset_flag = 0;
        }
    }

    /**
     * get whether the gridlines are shown during printing.
     *
     * @return gridset - true if gridlines are NOT printed, false if they are.
     */

    public boolean getGridset()
    {
        return (field_1_gridset_flag == 1);
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[GRIDSET]\n");
        buffer.append("    .gridset        = ").append(getGridset())
            .append("\n");
        buffer.append("[/GRIDSET]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(field_1_gridset_flag);
    }

    protected int getDataSize() {
        return 2;
    }

    public short getSid()
    {
        return sid;
    }

    public Object clone() {
      GridsetRecord rec = new GridsetRecord();
      rec.field_1_gridset_flag = field_1_gridset_flag;
      return rec;
    }
}
