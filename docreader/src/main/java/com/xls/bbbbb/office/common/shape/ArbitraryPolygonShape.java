package com.xls.bbbbb.office.common.shape;

import com.xls.bbbbb.office.common.autoshape.ExtendPath;

import java.util.ArrayList;
import java.util.List;


public class ArbitraryPolygonShape extends LineShape
{   
    public ArbitraryPolygonShape()
    {
        paths = new ArrayList<ExtendPath>();
    }
    
    public void appendPath(ExtendPath path)
    {
        this.paths.add(path);
    }
    
    public List<ExtendPath> getPaths()
    {
        return paths;
    }
    
    //
    private List<ExtendPath> paths;
}
