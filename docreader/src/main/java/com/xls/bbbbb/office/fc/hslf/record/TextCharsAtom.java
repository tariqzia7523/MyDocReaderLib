package com.xls.bbbbb.office.fc.hslf.record;

import com.xls.bbbbb.office.fc.util.HexDump;
import com.xls.bbbbb.office.fc.util.LittleEndian;
import com.xls.bbbbb.office.fc.util.StringUtil;


/**
 * A TextCharsAtom (type 4000). Holds text in byte swapped unicode form.
 * The trailing return character is always stripped from this
 *
 * @author Nick Burch
 */

public final class TextCharsAtom extends RecordAtom
{
	private byte[] _header;
	private static long _type = 4000l;

	/** The bytes that make up the text */
	private byte[] _text;

	/** Grabs the text. */
	public String getText() {
		return StringUtil.getFromUnicodeLE(_text);
	}

	/** Updates the text in the Atom. */
	public void setText(String text) {
		// Convert to little endian unicode
		_text = new byte[text.length()*2];
		StringUtil.putUnicodeLE(text,_text,0);

		// Update the size (header bytes 5-8)
		LittleEndian.putInt(_header,4,_text.length);
	}

	/* *************** record code follows ********************** */

	/**
	 * For the TextChars Atom
	 */
	protected TextCharsAtom(byte[] source, int start, int len) {
		// Sanity Checking
		if(len < 8) { len = 8; }

		// Get the header
		_header = new byte[8];
		System.arraycopy(source,start,_header,0,8);

		// Grab the text
		_text = new byte[len-8];
		System.arraycopy(source,start+8,_text,0,len-8);
	}
	/**
	 * Create an empty TextCharsAtom
	 */
	public TextCharsAtom() {
		// 0 length header
		_header = new byte[] {  0, 0, 0xA0-256, 0x0f, 0, 0, 0, 0 };
		// Empty text
		_text = new byte[0];
	}

	/**
	 * We are of type 4000
	 */
	public long getRecordType() { return _type; }


	/**
	 * dump debug info; use getText() to return a string
	 * representation of the atom
	 */
	public String toString() {
        StringBuffer out = new StringBuffer();
        out.append( "TextCharsAtom:\n");
		out.append( HexDump.dump(_text, 0, 0) );
		return out.toString();
	}
	
	/**
	 * 
	 */
	public void dispose()
	{
	    _header = null;
	    _text = null;
	}
}
