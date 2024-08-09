package com.xls.bbbbb.office.common.bookmark;

import java.util.HashMap;
import java.util.Map;


public class BookmarkManage
{

    public BookmarkManage()
    {
        bms = new HashMap<String, Bookmark>();
    }
    

    public void addBookmark(Bookmark bm)
    {
        bms.put(bm.getName(), bm);
    }
    

    public Bookmark getBookmark(String name)
    {
        return bms.get(name);
    }
    

    public int getBookmarkCount()
    {
        return bms.size();
    }
    

    public void dispose()
    {
        if (bms != null)
        {
            bms.clear();
            bms = null;
        }
    }
    
    //
    private Map<String, Bookmark> bms;
}
