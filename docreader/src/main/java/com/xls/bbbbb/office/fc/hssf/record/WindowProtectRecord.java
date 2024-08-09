package com.xls.bbbbb.office.fc.hssf.record;

import com.xls.bbbbb.office.fc.util.BitField;
import com.xls.bbbbb.office.fc.util.BitFieldFactory;
import com.xls.bbbbb.office.fc.util.HexDump;
import com.xls.bbbbb.office.fc.util.LittleEndianOutput;

/**
 * Title: Window Protect Record (0x0019) <p/>
 * Description:  flags whether workbook windows are protected<p/>
 * REFERENCE:  PG 424 Microsoft Excel 97 Developer's Kit (ISBN: 1-57231-498-2)<p/>
 * @author Andrew C. Oliver (acoliver at apache dot org)
 */
public final class WindowProtectRecord extends StandardRecord {
    public final static short sid = 0x0019;

    private static final BitField settingsProtectedFlag = BitFieldFactory.getInstance(0x0001);

    private int _options;

    public WindowProtectRecord(int options) {
        _options = options;
    }

    public WindowProtectRecord(RecordInputStream in) {
        this(in.readUShort());
    }

    public WindowProtectRecord(boolean protect) {
        this(0);
        setProtect(protect);
    }

    /**
     * set whether this window should be protected or not
     * @param protect or not
     */
    public void setProtect(boolean protect) {
        _options = settingsProtectedFlag.setBoolean(_options, protect);
    }

    /**
     * is this window protected or not
     *
     * @return protected or not
     */
    public boolean getProtect() {
        return settingsProtectedFlag.isSet(_options);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[WINDOWPROTECT]\n");
        buffer.append("    .options = ").append(HexDump.shortToHex(_options)).append("\n");
        buffer.append("[/WINDOWPROTECT]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(_options);
    }

    protected int getDataSize() {
        return 2;
    }

    public short getSid()
    {
        return sid;
    }
    @Override
    public Object clone() {
        return new WindowProtectRecord(_options);
    }
}
