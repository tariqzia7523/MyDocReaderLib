package com.xls.bbbbb.office.fc;

/**
 * Base class of all the exceptions that POI throws in the event
 * that it's given a file that's older than currently supported.
 */
public abstract class OldFileFormatException extends IllegalArgumentException {
	public OldFileFormatException(String s) {
		super(s);
	}
}
