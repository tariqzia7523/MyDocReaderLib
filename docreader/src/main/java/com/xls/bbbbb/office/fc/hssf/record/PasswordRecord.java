package com.xls.bbbbb.office.fc.hssf.record;

import com.xls.bbbbb.office.fc.util.HexDump;
import com.xls.bbbbb.office.fc.util.LittleEndianOutput;

/**
 * Title:        Password Record (0x0013)<p/>
 * Description:  stores the encrypted password for a sheet or workbook (HSSF doesn't support encryption)
 * REFERENCE:  PG 371 Microsoft Excel 97 Developer's Kit (ISBN: 1-57231-498-2)<p/>
 * @author Andrew C. Oliver (acoliver at apache dot org)
 *
 */
public final class PasswordRecord extends StandardRecord {
    public final static short sid = 0x0013;
    private int field_1_password;   // not sure why this is only 2 bytes, but it is... go figure

    public PasswordRecord(int password) {
        field_1_password = password;
    }

    public PasswordRecord(RecordInputStream in) {
        field_1_password = in.readShort();
    }

    //this is the world's lamest "security".  thanks to Wouter van Vugt for making me
    //not have to try real hard.  -ACO
    public static short hashPassword(String password) {
        byte[] passwordCharacters = password.getBytes();
        int hash = 0;
        if (passwordCharacters.length > 0) {
            int charIndex = passwordCharacters.length;
            while (charIndex-- > 0) {
                hash = ((hash >> 14) & 0x01) | ((hash << 1) & 0x7fff);
                hash ^= passwordCharacters[charIndex];
            }
            // also hash with charcount
            hash = ((hash >> 14) & 0x01) | ((hash << 1) & 0x7fff);
            hash ^= passwordCharacters.length;
            hash ^= (0x8000 | ('N' << 8) | 'K');
        }
        return (short)hash;
    }

    /**
     * set the password
     *
     * @param password  representing the password
     */

    public void setPassword(int password) {
        field_1_password = password;
    }

    /**
     * get the password
     *
     * @return short  representing the password
     */
    public int getPassword() {
        return field_1_password;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[PASSWORD]\n");
        buffer.append("    .password = ").append(HexDump.shortToHex(field_1_password)).append("\n");
        buffer.append("[/PASSWORD]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(field_1_password);
    }

    protected int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }

    /**
     * Clone this record.
     */
    public Object clone() {
        return new PasswordRecord(field_1_password);
    }
}
