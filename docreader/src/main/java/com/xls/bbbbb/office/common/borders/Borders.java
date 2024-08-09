package com.xls.bbbbb.office.common.borders;

public class Borders
{

    public Borders()
    {
        
    }
    

    public Border getTopBorder()
    {
        return this.top;
    }

    public void setTopBorder(Border b)
    {
        this.top = b;
    }
    
    

    public Border getLeftBorder()
    {
        return this.left;
    }

    public void setLeftBorder(Border b)
    {
        this.left = b;
    }
    
    

    public Border getRightBorder()
    {
        return this.right;
    }

    public void setRightBorder(Border b)
    {
        this.right = b;
    }
    

    public Border getBottomBorder()
    {
        return this.bottom;
    }

    public void setBottomBorder(Border b)
    {
        this.bottom = b;
    }
    

    public byte getOnType()
    {
        return onType;
    }

    public void setOnType(byte onType)
    {
        this.onType = onType;
    }
    
    //
    private Border left;
    //
    private Border top;
    //
    private Border right;
    //
    private Border bottom;
    // on page or on text
    private byte onType;
}
