/*
 * 文件名称:           MessageDialog.java
 *  
 * 编译器:             android2.2
 * 时间:               下午2:31:49
 */
package com.xls.bbbbb.office.system.dialog;

import android.content.Context;
import android.content.res.Configuration;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.xls.bbbbb.office.res.ResConstant;
import com.xls.bbbbb.office.system.IControl;
import com.xls.bbbbb.office.system.IDialogAction;
import com.xls.bbbbb.office.system.beans.ADialog;

import java.util.Vector;


public class MessageDialog extends ADialog
{

    public MessageDialog(IControl control, Context context, IDialogAction action, Vector<Object> model,
        int dialogID, int titleResID, String message)
    {
        super(control, context, action, model, dialogID, titleResID);
        init(context, message);
    }
    
    /**
     * 
     */
    public void init(Context context, String message)
    {
        int mWidth = getContext().getResources().getDisplayMetrics().widthPixels - MARGIN * 4;
        textView = new TextView(context);
        textView.setGravity(Gravity.TOP);
        textView.setPadding(5, 2, 5, 2);
        if (message != null)
        {
            textView.setText(message);
        }
        LayoutParams  params = new LayoutParams(mWidth, LayoutParams.WRAP_CONTENT);
        params.leftMargin = GAP;
        params.rightMargin = GAP;
        params.topMargin = GAP * 2;
        params.bottomMargin = GAP * 2;
        params.gravity = Gravity.CENTER;
        dialogFrame.addView(textView, params);
 
        ok = new Button(context);        
        ok.setText(ResConstant.BUTTON_OK);
        ok.setOnClickListener(this);
        dialogFrame.addView(ok);
    }
    
    /**
     * 
     *
     */
    public void onClick(View v)
    {
        if (action != null)
        {
            action.doAction(dialogID, model);
        }
        dismiss();
    }
    
    /**
     * 
     *
     */
    public void doLayout()
    {
        int mWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        if (control.getSysKit().isVertical(getContext()))
        {
            mWidth -= MARGIN * 4;
        }
        else
        {
            mWidth -= MARGIN * 12;
        }
        // text view
        LayoutParams params = new LayoutParams(mWidth, LayoutParams.WRAP_CONTENT);
        params.leftMargin = GAP;
        params.rightMargin = GAP;
        params.topMargin = GAP * 2;
        params.bottomMargin = GAP * 2;
        textView.setLayoutParams(params);
    }
    
    /**
     * 
     *
     */
    public void onConfigurationChanged(Configuration newConfig)
    {        
        doLayout();
    }
    
    /**
     * 
     */
    public void dispose()
    {
        super.dispose();
        textView = null;
    }
    
    //
    private TextView textView;
}
