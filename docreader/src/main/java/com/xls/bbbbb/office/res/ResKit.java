
package com.xls.bbbbb.office.res;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class ResKit
{
    //
    private static ResKit kit = new ResKit();

    /**
     * 
     */
    public ResKit()
    {
        try
        {
            res = new HashMap<String, String>();
            // load "ResConstant"
            Class cls = Class.forName("com.xls.bbbbb.office.res.ResConstant");
            // get all fields
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields)
            {
                res.put(field.getName(), (String)field.get(null));
            }
        }
        catch(Exception e)
        {

        }
    }
    
    /**
     * 
     */
    public static ResKit instance()
    {
        return kit;
    }
    
    /**
     * 
     */
    public boolean hasResName(String resName)
    {
        return res.containsKey(resName);
    }
    
    /**
     * 
     * @param resName
     * @return
     */
    public String getLocalString(String resName)
    {
        return res.get(resName);
    }

    //
    private Map<String, String> res;
}
