package com.xls.bbbbb.office.fc.hssf.formula.eval;

/**
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt;
 */
public final class BoolEval implements NumericValueEval, StringValueEval
{

    private boolean _value;

    public static final BoolEval FALSE = new BoolEval(false);

    public static final BoolEval TRUE = new BoolEval(true);

    /**
     * Convenience method for the following:<br/>
     * <code>(b ? BoolEval.TRUE : BoolEval.FALSE)</code>
     *
     * @return the <tt>BoolEval</tt> instance representing <tt>b</tt>.
     */
    public static final BoolEval valueOf(boolean b)
    {
        return b ? TRUE : FALSE;
    }

    private BoolEval(boolean value)
    {
        _value = value;
    }

    public boolean getBooleanValue()
    {
        return _value;
    }

    public double getNumberValue()
    {
        return _value ? 1 : 0;
    }

    public String getStringValue()
    {
        return _value ? "TRUE" : "FALSE";
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName()).append(" [");
        sb.append(getStringValue());
        sb.append("]");
        return sb.toString();
    }
}
