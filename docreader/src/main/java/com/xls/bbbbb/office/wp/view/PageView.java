/*
 * 文件名称:          PageView.java
 *  
 * 编译器:            android2.2
 * 时间:              下午9:42:18
 */
package com.xls.bbbbb.office.wp.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.xls.bbbbb.office.common.BackgroundDrawer;
import com.xls.bbbbb.office.common.bg.BackgroundAndFill;
import com.xls.bbbbb.office.common.borders.Border;
import com.xls.bbbbb.office.common.borders.Borders;
import com.xls.bbbbb.office.constant.wp.WPViewConstant;
import com.xls.bbbbb.office.java.awt.Rectangle;
import com.xls.bbbbb.office.simpletext.model.IElement;
import com.xls.bbbbb.office.simpletext.view.AbstractView;
import com.xls.bbbbb.office.simpletext.view.IView;
import com.xls.bbbbb.office.wp.model.WPDocument;

import java.util.ArrayList;
import java.util.List;


public class PageView extends AbstractView
{
    /**
     * 
     * @param elem
     */
    public PageView(IElement elem)
    {
        this.elem = elem;
        paint = new Paint();
        paint.setStrokeWidth(2);
    }
    
    /**
     * 
     */
    public short getType()
    {
        return WPViewConstant.PAGE_VIEW;
    }
    

    public void draw(Canvas canvas, int originX, int originY, float zoom)
    {
        canvas.save();         
        int dX = (int)(x * zoom) + originX;
        int dY = (int)(y * zoom) + originY;
        
        canvas.clipRect(dX, dY, dX + getWidth() * zoom, dY + getHeight() * zoom);
        
        // draw background
        drawBackground(canvas, dX, dY, zoom);
        // draw border
        drawBorder(canvas, dX, dY, zoom);
        // draw paper
        drawPaper(canvas, dX, dY, zoom);
        // Separated
        drawPageSeparated(canvas, dX, dY, zoom);
        
        if (header != null)
        {
        	header.setParentView(this);
            header.draw(canvas, dX, dY, zoom);
        }
        if (footer != null)
        {
        	footer.setParentView(this);
            footer.draw(canvas, dX, dY, zoom);
        }       
        
        // draw shape
        drawShape(canvas, dX, dY, zoom, true);
        
        super.draw(canvas, originX, originY, zoom);
        
        // draw shape
        drawShape(canvas, dX, dY, zoom, false);
        
        canvas.restore();
    }
    

    public void drawForPrintMode(Canvas canvas, int originX, int originY, float zoom)
    {
        int dX = (int)(x * zoom) + originX;
        int dY = (int)(y * zoom) + originY;
        // draw background
        drawBackground(canvas, dX, dY, zoom);
        //
        drawBorder(canvas, dX, dY, zoom);
        //
        drawPageSeparated(canvas, dX, dY, zoom);
        if (header != null)
        {
        	header.setParentView(this);
            header.draw(canvas, dX, dY, zoom);
        }
        if (footer != null)
        {
        	footer.setParentView(this);
            footer.draw(canvas, dX, dY, zoom);
        }
        // draw shape
        drawShape(canvas, dX, dY, zoom, true);
        
        super.draw(canvas, originX, originY, zoom);
        
        // draw shape
        drawShape(canvas, dX, dY, zoom, false);
    }
    


    public void drawToImage(Canvas canvas, int originX, int originY, float zoom)
    {       
        int dX = (int)(x * zoom) + originX;
        int dY = (int)(y * zoom) + originY;
        //canvas.translate(dX, dY);
        // draw background
        drawBackground(canvas, dX, dY, zoom);
        //
        drawBorder(canvas, dX, dY, zoom);
        //drawPaper(canvas, dX, dY, zoom);
        if (header != null)
        {
        	header.setParentView(this);
            header.draw(canvas, dX, dY, zoom);
        }
        if (footer != null)
        {
        	footer.setParentView(this);
            footer.draw(canvas, dX, dY, zoom);
        }
        // draw shape
        drawShape(canvas, dX, dY, zoom, true);
        
        super.draw(canvas, originX, originY, zoom);
        
        // draw shape
        drawShape(canvas, dX, dY, zoom, false);
        //canvas.restore();
    }
    
    /**
     * 
     */
    private void drawBackground(Canvas canvas, int dx, int dy, float zoom)
    {
        int w = (int)(getWidth() * zoom);
        int h = (int)(getHeight() * zoom);
    	
    	Rect rect = new Rect(dx, dy, dx + w, dy + h);
    	BackgroundAndFill pageFill = ((WPDocument)getDocument()).getPageBackground();
    	if(pageFill != null)
    	{
    		BackgroundDrawer.drawBackground(canvas, getControl(), pageNumber, pageFill, rect, null, zoom);
    	}
    	else
    	{
    		paint.setColor(0xFFFFFFFF);
    		canvas.drawRect(dx, dy, dx + w, dy + h, paint);
    	}
    }
    

    private void drawBorder(Canvas canvas, int dx, int dy, float zoom)
    {
        // draw page border
        if (pageBorder >= 0)
        {
            int w = (int)(getWidth() * zoom);
            int h = (int)(getHeight() * zoom);
            Borders bs = getControl().getSysKit().getBordersManage().getBorders(pageBorder);
            int old = paint.getColor();
            if (bs != null)
            {               
                Border left = bs.getLeftBorder();
                Border top = bs.getTopBorder();
                Border right = bs.getRightBorder();
                Border bottom = bs.getBottomBorder();
                int sX,sY,eX,eY;
                // left
                if (left != null)
                {
                    paint.setColor(left.getColor());
                    sX = (int)(zoom * left.getSpace()) + dx;
                    eX = sX;
                    sY = (top == null ? 0 : (int)(top.getSpace() * zoom)) + dy;
                    eY = (int)(bottom == null ? h : (h - bottom.getSpace() * zoom)) + dy;
                    canvas.drawLine(sX, sY, eX, eY, paint);
                }
                // top
                if (top != null)
                {
                    paint.setColor(top.getColor());
                    sY = (int)(zoom * top.getSpace()) + dy;
                    eY = sY;
                    sX = (left == null ? 0 : (int)(left.getSpace() * zoom)) + dx - 1;
                    eX = (int)(right == null ? w : (w - right.getSpace() * zoom)) + dx + 1;
                    canvas.drawLine(sX, sY, eX, eY, paint);
                }
                // right
                if (right != null)
                {
                    paint.setColor(right.getColor());
                    sX = (int)(w - right.getSpace() * zoom) + dx;
                    eX = sX;
                    sY = (int)(top == null ? 0 : (top.getSpace() * zoom)) + dy;
                    eY = (int)(bottom == null ? h : (h - bottom.getSpace() * zoom)) + dy;
                    canvas.drawLine(sX, sY, eX, eY, paint);
                }
                // bottom
                if (bottom != null)
                {
                    paint.setColor(bottom.getColor());
                    sY = (int)(h - zoom * top.getSpace()) + dy;
                    eY = sY;
                    sX = (left == null ? 0 : (int)(left.getSpace() * zoom)) + dx - 1;
                    eX = (int)(right == null ? w : (w - right.getSpace() * zoom)) + dx + 1;
                    canvas.drawLine(sX, sY, eX, eY, paint);
                }
            }                
            paint.setColor(old);
        }
    }    
    
    /**
     * 
     */
    private void drawPaper(Canvas canvas, int dx, int dy, float zoom)
    {
        canvas.save();
        int w = (int)(getWidth() * zoom);
        int h = (int)(getHeight() * zoom);
        canvas.clipRect(dx, dy, dx + w + 5, dy + h + 5);
        // 绘黑色边框 
        paint.setColor(Color.BLACK);
        // 上
        canvas.drawLine(dx, dy, dx + w, dy, paint);
        // 左
        canvas.drawLine(dx, dy, dx, dy + h, paint);
        // 右
        canvas.drawLine(dx + w, dy, dx + w, dy + h, paint);
        // 下
        canvas.drawLine(dx, dy + h, dx + w, dy + h, paint);
        
        canvas.restore();
    }    

    /**
     * 
     */
    private void drawPageSeparated(Canvas canvas, int dx, int dy, float zoom)
    {        
        int bm = 30;
        float left = getLeftIndent() * zoom + dx;
        float top = getTopIndent() * zoom + dy;
        paint.setColor(Color.GRAY);
        // 左上角
        canvas.drawRect(left - 1, top - bm, left, top, paint);
        canvas.drawRect(left - bm, top - 1, left, top, paint);
        
        // 右上角
        float right = (getWidth() - getRightIndent()) * zoom + dx;
        canvas.drawRect(right, top - bm, right + 1 , top, paint);
        canvas.drawRect(right, top - 1, right + bm, top, paint);
        
        // 左下角
        float bottom = (getHeight() - getBottomIndent()) * zoom + dy;
        canvas.drawRect(left - 1, bottom, left , bottom + bm, paint);
        canvas.drawRect(left - bm, bottom, left, bottom + 1, paint);
        
        // 右下角
        canvas.drawRect(right, bottom, right + 1 , bottom + bm, paint);
        canvas.drawRect(right, bottom, right + bm, bottom + 1, paint);
    }
    
    /**
     * 
     */
    private void drawShape(Canvas canvas, int originX, int originY, float zoom, boolean drawBehindDocShape)
    {
        if (shapeViews == null || shapeViews.size() == 0)
        {
            return;
        }
        
        if(drawBehindDocShape)
        {
            //behind doc
            for (LeafView shape : shapeViews)
            {
                if(shape instanceof ShapeView && ((ShapeView)shape).isBehindDoc())
                {
                	((ShapeView)shape).drawForWrap(canvas, originX, originY, zoom);                 
                }
                else if(shape instanceof ObjView && ((ObjView)shape).isBehindDoc())
                {
                    ((ObjView)shape).drawForWrap(canvas, originX, originY, zoom);
                }
            }
        }
        else
        {
            for (LeafView shape : shapeViews)
            {
                if(shape instanceof ShapeView && !((ShapeView)shape).isBehindDoc())
                {
                    ((ShapeView)shape).drawForWrap(canvas, originX, originY, zoom);
                }
                else if(shape instanceof ObjView && !((ObjView)shape).isBehindDoc())
                {
                    ((ObjView)shape).drawForWrap(canvas, originX, originY, zoom);
                }
            }
        }
    }

    public Rectangle modelToView(long offset, Rectangle rect, boolean isBack)
    {
        IView view = getView(offset, WPViewConstant.PARAGRAPH_VIEW, isBack);
        if (view != null)
        {
            view.modelToView(offset, rect, isBack);
        }
        rect.x += getX();
        rect.y += getY();
        return rect;        
    }   
    

    public IView getView(long offset, int type, boolean isBack)
    {
        IView view = child;
        while (view != null && !view.contains(offset, isBack))
        {
            view = view.getNextView();
        }
        if (view != null && view.getType() != type
            && view.getType() != WPViewConstant.TABLE_VIEW)
        {
            return view.getView(offset, type, isBack);
        }
        return view;
    }
    

    public long viewToModel(int x, int y, boolean isBack)
    {
        x -= getX();
        y -= getY();
        IView view = getChildView();
        if (view != null && y > view.getY())
        {
            while (view != null)
            {
                if (y >= view.getY() && y < view.getY() + view.getHeight()/*.getLayoutSpan(WPViewConstant.Y_AXIS)*/)
                {
                    break;
                }
                view = view.getNextView();
            }
        }
        view = view == null ? getChildView() : view;
        if (view != null)
        {
            return view.viewToModel(x, y, isBack);
        }
        return -1;
    }
    

    public int getPageNumber()
    {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber)
    {
        this.pageNumber = pageNumber;
    }

    public TitleView getHeader()
    {
        return header;
    }


    public void setHeader(TitleView header)
    {
        this.header = header;
    }
    

    public TitleView getFooter()
    {
        return footer;
    }

    /**
     * @param footer The footer to set.
     */
    public void setFooter(TitleView footer)
    {
        this.footer = footer;
    }

    /**
     * @return Returns the hasBreakTable.
     */
    public boolean isHasBreakTable()
    {
        return hasBreakTable;
    }

    /**
     * @param hasBreakTable The hasBreakTable to set.
     */
    public void setHasBreakTable(boolean hasBreakTable)
    {
        this.hasBreakTable = hasBreakTable;
    }
    
    /**
     * 
     */
    public void setPageBackgroundColor(int color)
    {
        this.pageBRColor = color;
    }
    
    /**
     * 
     */
    public void setPageBorder(int border)
    {
        this.pageBorder = border;
    }

    /**
     * 
     * @param view
     */
    public void addShapeView(LeafView view)
    {
        if (shapeViews == null)
        {
            shapeViews = new ArrayList<LeafView>();
        }
        shapeViews.add(view);            
    }
    

    public boolean checkUpdateHeaderFooterFieldText(int totalPages)
    {
    	boolean hasTotalPageCode = checkUpdateHeaderFooterFieldText(header, totalPages);
    	
    	return hasTotalPageCode || checkUpdateHeaderFooterFieldText(footer, totalPages);
    }
    

    private boolean checkUpdateHeaderFooterFieldText(TitleView titleView, int totalPages)
    {
    	boolean hasTotalPageCode = false;
    	if(titleView != null)
    	{
    		IView paraView = titleView.getChildView();
    		while (paraView != null)
            {
    			IView lineView = paraView.getChildView();
    			while(lineView != null)
    			{
    				IView leafView = lineView.getChildView();
    				while(leafView != null)
    				{
    					if(leafView instanceof LeafView)
    					{
    						if(((LeafView)leafView).hasUpdatedFieldText())
    						{
    							hasTotalPageCode = true;
    							((LeafView)leafView).setNumPages(totalPages);
    						}
    					}    					
    					
    					leafView = leafView.getNextView();
    				}
    				
    				lineView = lineView.getNextView();
    			}
    			
    			paraView = paraView.getNextView();
            }
    	}
    	
    	return hasTotalPageCode;
    }
    
    /**
     * 
     */
    public void dispose()
    {
        super.dispose();
        if (header != null)
        {
            header.dispose();
            header = null;
        }
        if (footer != null)
        {
            footer.dispose();
            footer = null;
        }
        if (shapeViews != null)
        {
            shapeViews.clear();
            shapeViews = null;
        }
        paint = null;
    }

    //
    private boolean hasBreakTable;
    //
    private int pageBRColor;
    //
    private int pageBorder = -1;
    //
    private Paint paint;
    // 页码
    private int pageNumber;
    // header
    private TitleView header;
    // footer
    private TitleView footer;
    //autoshape view and picture view
    private List<LeafView> shapeViews;
}
