package com.xls.bbbbb.office.common.shape;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;

import com.xls.bbbbb.office.common.PaintKit;
import com.xls.bbbbb.office.common.picture.Picture;
import com.xls.bbbbb.office.system.IControl;
import com.xls.bbbbb.office.thirdpart.achartengine.chart.AbstractChart;

import java.io.File;
import java.io.FileOutputStream;

public class AChart  extends AbstractShape {
    private AbstractChart chart;
    private int picIndex = -1;

    public short getType() {
        return 5;
    }

    public void setAChart(AbstractChart abstractChart) {
        this.chart = abstractChart;
    }

    public AbstractChart getAChart() {
        return this.chart;
    }

    private void saveChartToPicture(IControl iControl) {
        Bitmap bitmap = null;
        try {
            int zoomRate = (int) (((float) this.rect.width) * getAChart().getZoomRate());
            int zoomRate2 = (int) (((float) this.rect.height) * getAChart().getZoomRate());
            bitmap = Bitmap.createBitmap(zoomRate, zoomRate2, Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            this.chart.draw(canvas, iControl, 0, 0, zoomRate, zoomRate2, PaintKit.instance().getPaint());
            canvas.save();
            canvas.restore();
            Picture picture = new Picture();
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(System.currentTimeMillis()));
            sb.append(".tmp");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(iControl.getSysKit().getPictureManage().getPicTempPath());
            sb3.append(File.separator);
            sb3.append(sb2);
            File file = new File(sb3.toString());
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            bitmap.recycle();
            fileOutputStream.close();
            picture.setTempFilePath(file.getAbsolutePath());
            this.picIndex = iControl.getSysKit().getPictureManage().addPicture(picture);
        } catch (Exception e) {
            if (bitmap != null) {
                bitmap.recycle();
            }
            iControl.getSysKit().getErrorKit().writerLog(e);
        }
    }

    public int getDrawingPicture(IControl iControl) {
        if (this.picIndex == -1) {
            saveChartToPicture(iControl);
        }
        return this.picIndex;
    }

    public void dispose() {
        super.dispose();
        this.chart = null;
    }

}
