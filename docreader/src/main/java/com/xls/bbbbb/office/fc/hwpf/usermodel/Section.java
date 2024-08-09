package com.xls.bbbbb.office.fc.hwpf.usermodel;

import com.xls.bbbbb.office.fc.hwpf.model.SEPX;


public final class Section extends Range
{
    private SectionProperties _props;

    public Section( SEPX sepx, Range parent )
    {
        super( Math.max( parent._start, sepx.getStart() ), Math.min(
                parent._end, sepx.getEnd() ), parent );

        // XXX: temporary workaround for old Word95 document
        /*if ( parent.getDocument() instanceof HWPFOldDocument )
            _props = new SectionProperties();
        else*/
            _props = sepx.getSectionProperties();
    }

    public Object clone() throws CloneNotSupportedException
    {
        Section s = (Section) super.clone();
        s._props = (SectionProperties) _props.clone();
        return s;
    }

    /**
     * @return distance to be maintained between columns, in twips. Used when
     *         {@link #isColumnsEvenlySpaced()} == true
     */
    public int getDistanceBetweenColumns()
    {
        return _props.getDxaColumns();
    }

    public int getMarginBottom()
    {
        return _props.getDyaBottom();
    }

    public int getMarginLeft()
    {
        return _props.getDxaLeft();
    }

    public int getMarginRight()
    {
        return _props.getDxaRight();
    }

    public int getMarginTop()
    {
        return _props.getDyaTop();
    }
    
    public int getMarginHeader()
    {
        return _props.getDyaHdrTop();
    }
    
    public int getMarginFooter()
    {
        return _props.getDyaHdrBottom();
    }

    public int getNumColumns()
    {
        return _props.getCcolM1() + 1;
    }

    /**
     * @return page height (in twips) in current section. Default value is 15840
     *         twips
     */
    public int getPageHeight()
    {
        return _props.getYaPage();
    }

    /**
     * @return page width (in twips) in current section. Default value is 12240
     *         twips
     */
    public int getPageWidth()
    {
        return _props.getXaPage();
    }

    public int getGridType()
    {
    	return _props.getClm();
    }

    public int getLinePitch()
    {
    	return _props.getDyaLinePitch();
    }
    
    public boolean isColumnsEvenlySpaced()
    {
        return _props.getFEvenlySpaced();
    }
    
    /**
     */
    public BorderCode getTopBorder()
    {
        return _props.getTopBorder();
    }
    
    /**
     */
    public BorderCode getBottomBorder()
    {
        return _props.getBottomBorder();
    }
    /**
     */
    public BorderCode getLeftBorder()
    {
        return _props.getLeftBorder();
    }
    /**
     */
    public BorderCode getRightBorder()
    {
        return _props.getRightBorder();
    }
    
    /**
     */
    public int getPageBorderInfo()
    {
        return _props.getPgbProp();
    }

    @Override
    public String toString()
    {
        return "Section [" + getStartOffset() + "; " + getEndOffset() + ")";
    }

    public int type()
    {
        return TYPE_SECTION;
    }
}
