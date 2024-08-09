package com.xls.bbbbb.office.fc.hssf.record.chart;


import com.xls.bbbbb.office.fc.hssf.record.RecordInputStream;
import com.xls.bbbbb.office.fc.hssf.record.StandardRecord;
import com.xls.bbbbb.office.fc.util.HexDump;
import com.xls.bbbbb.office.fc.util.LittleEndianOutput;


/**
 * Indicates the chart-group index for a series.  The order probably defines the mapping.  
 * So the 0th record probably means the 0th series.  The only field in this of course defines which chart 
 * group the 0th series (for instance) would map to.  Confusing?  Well thats because it is.  (p 522 BCG)<p/>
 * 
 * @author Andrew C. Oliver (acoliver at apache.org)
 */
public final class SeriesToChartGroupRecord extends StandardRecord {
    public final static short      sid                             = 0x1045;
    private  short      field_1_chartGroupIndex;


    public SeriesToChartGroupRecord()
    {

    }

    public SeriesToChartGroupRecord(RecordInputStream in)
    {
        field_1_chartGroupIndex        = in.readShort();
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[SeriesToChartGroup]\n");
        buffer.append("    .chartGroupIndex      = ")
            .append("0x").append(HexDump.toHex(  getChartGroupIndex ()))
            .append(" (").append( getChartGroupIndex() ).append(" )");
        buffer.append(System.getProperty("line.separator")); 

        buffer.append("[/SeriesToChartGroup]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(field_1_chartGroupIndex);
    }

    protected int getDataSize() {
        return 2;
    }

    public short getSid()
    {
        return sid;
    }

    public Object clone() {
        SeriesToChartGroupRecord rec = new SeriesToChartGroupRecord();
    
        rec.field_1_chartGroupIndex = field_1_chartGroupIndex;
        return rec;
    }




    /**
     * Get the chart group index field for the SeriesToChartGroup record.
     */
    public short getChartGroupIndex()
    {
        return field_1_chartGroupIndex;
    }

    /**
     * Set the chart group index field for the SeriesToChartGroup record.
     */
    public void setChartGroupIndex(short field_1_chartGroupIndex)
    {
        this.field_1_chartGroupIndex = field_1_chartGroupIndex;
    }
}
