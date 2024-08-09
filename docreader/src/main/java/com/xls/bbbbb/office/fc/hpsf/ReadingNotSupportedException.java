package com.xls.bbbbb.office.fc.hpsf;

/**
 * <p>This exception is thrown when HPSF tries to read a (yet) unsupported
 * variant type.</p>
 * 
 * @see WritingNotSupportedException
 * @see UnsupportedVariantTypeException
 *
 * @author Rainer Klute <a
 * href="mailto:klute@rainer-klute.de">&lt;klute@rainer-klute.de&gt;</a>
 */
public class ReadingNotSupportedException
    extends UnsupportedVariantTypeException
{

    /**
     * <p>Constructor</p>
     * 
     * @param variantType The unsupported variant type.
     * @param value The value.
     */
    public ReadingNotSupportedException(final long variantType,
                                        final Object value)
    {
        super(variantType, value);
    }

}
