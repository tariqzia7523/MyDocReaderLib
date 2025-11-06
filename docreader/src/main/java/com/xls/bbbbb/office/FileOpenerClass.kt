package com.xls.bbbbb.office

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.xls.bbbbb.office.AppHelper.Companion.getFilePathFromExternalAppsURI
import com.xls.bbbbb.office.AppHelper.Companion.getMimeType
import com.xls.bbbbb.office.common.IOfficeToPicture
import com.xls.bbbbb.office.constant.EventConstant
import com.xls.bbbbb.office.constant.MainConstant
import com.xls.bbbbb.office.constant.wp.WPViewConstant
import com.xls.bbbbb.office.officereader.AppFrame
import com.xls.bbbbb.office.officereader.FindToolBar
import com.xls.bbbbb.office.officereader.beans.AImageButton
import com.xls.bbbbb.office.officereader.beans.AImageCheckButton
import com.xls.bbbbb.office.officereader.beans.AToolsbar
import com.xls.bbbbb.office.officereader.beans.CalloutToolsbar
import com.xls.bbbbb.office.officereader.beans.PDFToolsbar
import com.xls.bbbbb.office.officereader.beans.PGToolsbar
import com.xls.bbbbb.office.officereader.beans.SSToolsbar
import com.xls.bbbbb.office.officereader.beans.WPToolsbar
import com.xls.bbbbb.office.res.ResKit
import com.xls.bbbbb.office.ss.sheetbar.SheetBar
import com.xls.bbbbb.office.system.IMainFrame
import com.xls.bbbbb.office.system.MainControl
import com.xls.bbbbb.office.system.beans.pagelist.IPageListViewListener
import com.xls.bbbbb.office.system.dialog.ColorPickerDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale

class FileOpenerClass(var context : Activity,
        var fileUri : Uri,
        var mainScreenFrameLayout : FrameLayout,
        var fileType : String?) : IMainFrame {
    // updated on 06 Nov 2025

    var fileName: String? = null
    var isDispose = false
    var marked = false
    var applicationType = -1
    var filePath: String? = null
    var control: MainControl? = null
    var appFrame: AppFrame? = null
    var toolsbar: AToolsbar? = null
    var searchBar: FindToolBar? = null
    var bottomBar: SheetBar? = null
    var toast: Toast? = null
    var gapView: View? = null
    var wm: WindowManager? = null
    var wmParams: WindowManager.LayoutParams? = null
    var pageUp: AImageButton? = null
    var pageDown: AImageButton? = null
    var penButton: AImageCheckButton? = null
    var eraserButton: AImageCheckButton? = null
    var settingsButton: AImageButton? = null
    var writeLogTemp = true
    var isThumbnailTemp = false
    var bg: Any = Color.GRAY
    var calloutBar: CalloutToolsbar? = null
    var fullscreen = false
    var tempFilePath: String? = null

    init {
        control = MainControl(this)
        appFrame = AppFrame(context)
        appFrame!!.post {
            initAppFrame()
        }
        control!!.setOffictToPicture(object : IOfficeToPicture {
            override fun getBitmap(componentWidth: Int, componentHeight: Int): Bitmap? {
                if (componentWidth == 0 || componentHeight == 0) {
                    return null
                }
                if (bitmap == null || bitmap!!.width != componentWidth || bitmap!!.height != componentHeight) {
                    // custom picture size
                    bitmap?.recycle()
                    //bitmap = Bitmap.createBitmap(800, 600,  Config.ARGB_8888);
                    //
                    bitmap = Bitmap.createBitmap(
                        componentWidth,
                        componentHeight, Bitmap.Config.ARGB_8888
                    )
                }
                return bitmap
                //return null;
            }

            override fun callBack(bitmap: Bitmap) {
                saveBitmapToFile(bitmap)
            }

            //
            private var bitmap: Bitmap? = null

            override fun setModeType(modeType: Byte) {
                // TODO Auto-generated method stub
            }

            override fun getModeType(): Byte {
                // TODO Auto-generated method stub
                return IOfficeToPicture.VIEW_CHANGE_END
            }

            override fun isZoom(): Boolean {
                // TODO Auto-generated method stub
                return false
            }

            override fun dispose() {
                // TODO Auto-generated method stub
            }
        })
        //context.setTheme(if (control!!.getSysKit().isVertical(context)) R.style.title_background_vertical else R.style.title_background_horizontal)
        mainScreenFrameLayout.addView(appFrame)

    }

    private fun saveBitmapToFile(bitmap: Bitmap?) {
        if (bitmap == null) {
            return
        }
        if (tempFilePath == null) {
            val state = Environment.getExternalStorageState()
            if (Environment.MEDIA_MOUNTED == state) {
                tempFilePath = Environment.getExternalStorageDirectory().absolutePath
            }
            val file = File(tempFilePath + File.separatorChar + "tempPic")
            if (!file.exists()) {
                file.mkdir()
            }
            tempFilePath = file.absolutePath
        }

        val file = File(tempFilePath + File.separatorChar + "export_image.jpg")
        try {
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: IOException) {
        } finally {
            //bitmap.recycle();
        }
    }
    fun setButtonEnabled(enabled: Boolean) {
        if (fullscreen) {
            pageUp!!.isEnabled = enabled
            pageDown!!.isEnabled = enabled
            penButton!!.isEnabled = enabled
            eraserButton!!.isEnabled = enabled
            settingsButton!!.isEnabled = enabled
        }
    }
    fun onPause() {

        val obj = control!!.getActionValue(EventConstant.PG_SLIDESHOW, null)
        if (obj != null && obj as Boolean) {
            wm!!.removeView(pageUp)
            wm!!.removeView(pageDown)
            wm!!.removeView(penButton)
            wm!!.removeView(eraserButton)
            wm!!.removeView(settingsButton)
        }
    }

    fun onResume() {

        val obj = control!!.getActionValue(EventConstant.PG_SLIDESHOW, null)
        if (obj != null && obj as Boolean) {
            wmParams!!.gravity = Gravity.RIGHT or Gravity.TOP
            wmParams!!.x = MainConstant.GAP
            wm!!.addView(penButton, wmParams)

            wmParams!!.gravity = Gravity.RIGHT or Gravity.TOP
            wmParams!!.x = MainConstant.GAP
            wmParams!!.y = wmParams!!.height
            wm!!.addView(eraserButton, wmParams)

            wmParams!!.gravity = Gravity.RIGHT or Gravity.TOP
            wmParams!!.x = MainConstant.GAP
            wmParams!!.y = wmParams!!.height * 2
            wm!!.addView(settingsButton, wmParams)

            wmParams!!.gravity = Gravity.LEFT or Gravity.CENTER
            wmParams!!.x = MainConstant.GAP
            wmParams!!.y = 0
            wm!!.addView(pageUp, wmParams)

            wmParams!!.gravity = Gravity.RIGHT or Gravity.CENTER
            wm!!.addView(pageDown, wmParams)
        }
    }
    fun onConfigurationChanged(newConfig: Configuration?) {
        if (isSearchbarActive()) {
            searchBar!!.onConfigurationChanged(newConfig)
        }
    }
   fun isSearchbarActive(): Boolean {
        if (appFrame == null || isDispose) {
            return false
        }
        val count = appFrame!!.childCount
        for (i in 0 until count) {
            val v = appFrame!!.getChildAt(i)
            if (v is FindToolBar) {
                return v.getVisibility() == View.VISIBLE
            }
        }
        return false
    }

    fun onDestroy() {
        dispose()
       // super.onDestroy()
    }
    fun initAppFrame(){
        this.toast = Toast.makeText(context, "", Toast.LENGTH_LONG)
        if (fileType == "application/msword" || (fileType == "application/pdf" || fileType == "application/vnd.openxmlformats-officedocument.wordprocessingml.document" || fileType == "application/vnd.ms-powerpoint" || fileType == "application/vnd.openxmlformats-officedocument.presentationml.presentation" || fileType == "application/vnd.ms-excel" || fileType == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" || fileType == "text/plain")) {
            val sb = StringBuilder()
            sb.append(".")
            sb.append(getMimeType(context, fileUri))
            this.filePath =
                Uri.parse(getFilePathFromExternalAppsURI(context, fileUri, sb.toString())).toString()
        }

//        if (this.filePath == null) {
//            this.filePath = intent.dataString
//            val indexOf: Int = getFilePath().indexOf(":")
//            if (indexOf > 0) {
//                this.filePath = filePath!!.substring(indexOf + 3)
//            }
//            this.filePath = Uri.decode(this.filePath)
//        }
        val lastIndexOf = filePath!!.lastIndexOf(File.separator)
        if (lastIndexOf > 0) {
            context.setTitle(filePath!!.substring(lastIndexOf + 1))
        } else {
            context.setTitle(this.filePath)
        }
        createView()
        control!!.openFile(filePath)
    }



    private fun createView() {
        // word
        val file = filePath!!.lowercase(Locale.getDefault())
        if (file.endsWith(MainConstant.FILE_TYPE_DOC) || file.endsWith(MainConstant.FILE_TYPE_DOCX)
            || file.endsWith(MainConstant.FILE_TYPE_TXT)
            || file.endsWith(MainConstant.FILE_TYPE_DOT)
            || file.endsWith(MainConstant.FILE_TYPE_DOTX)
            || file.endsWith(MainConstant.FILE_TYPE_DOTM)
        ) {
            applicationType = MainConstant.APPLICATION_TYPE_WP.toInt()
            toolsbar = WPToolsbar(context.applicationContext, control)
        } else if (file.endsWith(MainConstant.FILE_TYPE_XLS)
            || file.endsWith(MainConstant.FILE_TYPE_XLSX)
            || file.endsWith(MainConstant.FILE_TYPE_XLT)
            || file.endsWith(MainConstant.FILE_TYPE_XLTX)
            || file.endsWith(MainConstant.FILE_TYPE_XLTM)
            || file.endsWith(MainConstant.FILE_TYPE_XLSM)
        ) {
            applicationType = MainConstant.APPLICATION_TYPE_SS.toInt()
            toolsbar = SSToolsbar(context.applicationContext, control)
        } else if (file.endsWith(MainConstant.FILE_TYPE_PPT)
            || file.endsWith(MainConstant.FILE_TYPE_PPTX)
            || file.endsWith(MainConstant.FILE_TYPE_POT)
            || file.endsWith(MainConstant.FILE_TYPE_PPTM)
            || file.endsWith(MainConstant.FILE_TYPE_POTX)
            || file.endsWith(MainConstant.FILE_TYPE_POTM)
        ) {
            applicationType = MainConstant.APPLICATION_TYPE_PPT.toInt()
            toolsbar = PGToolsbar(context.applicationContext, control)
        } else if (file.endsWith(MainConstant.FILE_TYPE_PDF)) {
            applicationType = MainConstant.APPLICATION_TYPE_PDF.toInt()
            toolsbar = PDFToolsbar(context.applicationContext, control)
        } else {
            applicationType = MainConstant.APPLICATION_TYPE_WP.toInt()
            toolsbar = WPToolsbar(context.applicationContext, control)
        }
        // 添加tool bar
        appFrame!!.addView(toolsbar)
    }

    fun showSearchBar(show: Boolean) {
        //show search bar
        if (show) {
            if (searchBar == null) {
                searchBar = FindToolBar(context, control)
                appFrame!!.addView(searchBar, 0)
            }
            searchBar!!.visibility = View.VISIBLE
            toolsbar!!.visibility = View.GONE
        } else {
            if (searchBar != null) {
                searchBar!!.visibility = View.GONE
            }
            toolsbar!!.visibility = View.VISIBLE
        }
    }

    fun showCalloutToolsBar(show: Boolean) {
        //show callout bar
        if (show) {
            if (calloutBar == null) {
                calloutBar = CalloutToolsbar(context.applicationContext, control)
                appFrame!!.addView(calloutBar, 0)
            }
            calloutBar!!.setCheckState(EventConstant.APP_PEN_ID, AImageCheckButton.CHECK)
            calloutBar!!.setCheckState(EventConstant.APP_ERASER_ID, AImageCheckButton.UNCHECK)
            calloutBar!!.visibility = View.VISIBLE
            toolsbar!!.visibility = View.GONE
        } else {
            if (calloutBar != null) {
                calloutBar!!.visibility = View.GONE
            }
            toolsbar!!.visibility = View.VISIBLE
        }
    }

    fun setPenUnChecked() {
        if (fullscreen) {
            penButton!!.state = AImageCheckButton.UNCHECK
            penButton!!.postInvalidate()
        } else {
            calloutBar!!.setCheckState(EventConstant.APP_PEN_ID, AImageCheckButton.UNCHECK)
            calloutBar!!.postInvalidate()
        }
    }

    fun setEraserUnChecked() {
        if (fullscreen) {
            eraserButton!!.state = AImageCheckButton.UNCHECK
            eraserButton!!.postInvalidate()
        } else {
            calloutBar!!.setCheckState(EventConstant.APP_ERASER_ID, AImageCheckButton.UNCHECK)
            calloutBar!!.postInvalidate()
        }
    }

    override fun setFindBackForwardState(state: Boolean) {
        if (isSearchbarActive()) {
            searchBar!!.setEnabled(EventConstant.APP_FIND_BACKWARD, state)
            searchBar!!.setEnabled(EventConstant.APP_FIND_FORWARD, state)
        }
    }
    override fun updateToolsbarStatus() {
        if (appFrame == null || isDispose) {
            return
        }
        val count = appFrame!!.childCount
        for (i in 0 until count) {
            val v = appFrame!!.getChildAt(i)
            if (v is AToolsbar) {
//                ((AToolsbar)v).updateStatus();
            }
        }
    }

    override fun getActivity(): Activity {
        return context
    }

    fun fileShare(){
        //TODO Add FileShare
    }

    override fun doActionEvent(actionID: Int, obj: Any?): Boolean {
        try {
            when (actionID) {
                EventConstant.SYS_ONBACK_ID -> context.onBackPressed()
                EventConstant.SYS_UPDATE_TOOLSBAR_BUTTON_STATUS -> updateToolsbarStatus()
                EventConstant.APP_FIND_ID -> showSearchBar(true)
                EventConstant.APP_SHARE_ID -> fileShare()
                EventConstant.APP_FINDING -> {
                    val content = (obj as String).trim { it <= ' ' }
                    if (content.length > 0 && control!!.find.find(content)) {
                        setFindBackForwardState(true)
                    } else {
                        setFindBackForwardState(false)
                        toast!!.setText(getLocalString("DIALOG_FIND_NOT_FOUND"))
                        toast!!.show()
                    }
                }

                EventConstant.APP_FIND_BACKWARD -> if (!control!!.find.findBackward()) {
                    searchBar!!.setEnabled(EventConstant.APP_FIND_BACKWARD, false)
                    toast!!.setText(getLocalString("DIALOG_FIND_TO_BEGIN"))
                    toast!!.show()
                } else {
                    searchBar!!.setEnabled(EventConstant.APP_FIND_FORWARD, true)
                }

                EventConstant.APP_FIND_FORWARD -> if (!control!!.find.findForward()) {
                    searchBar!!.setEnabled(EventConstant.APP_FIND_FORWARD, false)
                    toast!!.setText(getLocalString("DIALOG_FIND_TO_END"))
                    toast!!.show()
                } else {
                    searchBar!!.setEnabled(EventConstant.APP_FIND_BACKWARD, true)
                }

                EventConstant.SS_CHANGE_SHEET -> bottomBar!!.setFocusSheetButton((obj as Int?)!!)
                EventConstant.APP_DRAW_ID -> {
                    showCalloutToolsBar(true)
                    control!!.getSysKit().calloutManager.drawingMode =
                        MainConstant.DRAWMODE_CALLOUTDRAW
                    appFrame!!.post { // TODO Auto-generated method stub
                        control!!.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, null)
                    }
                }

                EventConstant.APP_BACK_ID -> {
                    showCalloutToolsBar(false)
                    control!!.getSysKit().calloutManager.drawingMode = MainConstant.DRAWMODE_NORMAL
                }

                EventConstant.APP_PEN_ID -> if (obj as Boolean) {
                    control!!.getSysKit().calloutManager.drawingMode =
                        MainConstant.DRAWMODE_CALLOUTDRAW
                    setEraserUnChecked()
                    appFrame!!.post { // TODO Auto-generated method stub
                        control!!.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, null)
                    }
                } else {
                    control!!.getSysKit().calloutManager.drawingMode = MainConstant.DRAWMODE_NORMAL
                }

                EventConstant.APP_ERASER_ID -> if (obj as Boolean) {
                    control!!.getSysKit().calloutManager.drawingMode =
                        MainConstant.DRAWMODE_CALLOUTERASE
                    setPenUnChecked()
                } else {
                    control!!.getSysKit().calloutManager.drawingMode = MainConstant.DRAWMODE_NORMAL
                }

                EventConstant.APP_COLOR_ID -> {
                    val dlg = ColorPickerDialog(context, control)
                    dlg.show()
                    dlg.setOnDismissListener { setButtonEnabled(true) }
                    setButtonEnabled(false)
                }

                else -> return false
            }
        } catch (e: Exception) {
            control!!.getSysKit().errorKit.writerLog(e)
        }
        return false
    }


    override fun openFileFinish() {
        gapView = View(context.applicationContext)
        gapView!!.setBackgroundColor(Color.WHITE)
        appFrame!!.addView(
            gapView,
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1)
        )

        //
        val app = control!!.view
        appFrame!!.addView(
            app,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
    }



    override fun getBottomBarHeight(): Int {
        if (bottomBar != null) {
            return bottomBar!!.sheetbarHeight
        }
        return 0
    }

    override fun getTopBarHeight(): Int  = 0

    override fun getAppName(): String =  ""

    override fun getTemporaryDirectory(): File {

        // Get path for the file on external storage.  If external
        // storage is not currently mounted this will fail.
        val file: File = context.getExternalFilesDir(null)!!
        return file ?: context.getFilesDir()
    }

    override fun onEventMethod(
        v: View?,
        e1: MotionEvent?,
        e2: MotionEvent?,
        xValue: Float,
        yValue: Float,
        eventMethodType: Byte
    ): Boolean = false

    override fun isDrawPageNumber(): Boolean = true

    override fun isShowZoomingMsg(): Boolean {
        return true
    }


    override fun isPopUpErrorDlg(): Boolean {
        return true
    }


    override fun isShowPasswordDlg(): Boolean = true

    override fun isShowProgressBar(): Boolean = true

    override fun isShowFindDlg(): Boolean = true

    override fun isShowTXTEncodeDlg(): Boolean = true

    override fun getTXTDefaultEncode(): String = "GBK"

    override fun isTouchZoom(): Boolean  = true

    override fun isZoomAfterLayoutForWord(): Boolean =  true

    override fun getWordDefaultView(): Byte {
        return WPViewConstant.PAGE_ROOT.toByte()
    }

    override fun getLocalString(resName: String?): String {
        return ResKit.instance().getLocalString(resName)
    }

    override fun changeZoom() {
        //TODO("Not yet implemented")
    }

    override fun changePage() {
        //TODO("Not yet implemented")
    }

    override fun completeLayout() {
        //TODO("Not yet implemented")
    }

    override fun error(errorCode: Int) {
        //TODO("Not yet implemented")
    }

    override fun fullScreen(fullscreen: Boolean) {
        this.fullscreen = fullscreen
        if (fullscreen) {
            if (wm == null || wmParams == null) {
//                initFloatButton();
            }

            wmParams!!.gravity = Gravity.RIGHT or Gravity.TOP
            wmParams!!.x = MainConstant.GAP
            wm!!.addView(penButton, wmParams)

            wmParams!!.gravity = Gravity.RIGHT or Gravity.TOP
            wmParams!!.x = MainConstant.GAP
            wmParams!!.y = wmParams!!.height
            wm!!.addView(eraserButton, wmParams)

            wmParams!!.gravity = Gravity.RIGHT or Gravity.TOP
            wmParams!!.x = MainConstant.GAP
            wmParams!!.y = wmParams!!.height * 2
            wm!!.addView(settingsButton, wmParams)

            wmParams!!.gravity = Gravity.LEFT or Gravity.CENTER
            wmParams!!.x = MainConstant.GAP
            wmParams!!.y = 0
            wm!!.addView(pageUp, wmParams)

            wmParams!!.gravity = Gravity.RIGHT or Gravity.CENTER
            wm!!.addView(pageDown, wmParams)

            //hide title and tool bar
            (context.window.findViewById<View>(android.R.id.title).getParent() as View).visibility =
                View.GONE
            //hide status bar
            toolsbar!!.visibility = View.GONE
            //
            gapView!!.visibility = View.GONE

            penButton!!.state = AImageCheckButton.UNCHECK
            eraserButton!!.state = AImageCheckButton.UNCHECK

            val params: WindowManager.LayoutParams = context.window.getAttributes()
            params.flags = params.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
            context.window.setAttributes(params)
            context.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            //landscape
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            wm!!.removeView(pageUp)
            wm!!.removeView(pageDown)
            wm!!.removeView(penButton)
            wm!!.removeView(eraserButton)
            wm!!.removeView(settingsButton)
            //show title and tool bar
            (context.window.findViewById<View>(android.R.id.title).getParent() as View).visibility =
                View.VISIBLE
            toolsbar!!.visibility = View.VISIBLE
            gapView!!.visibility = View.VISIBLE

            //show status bar
            val params: WindowManager.LayoutParams = context.window.getAttributes()
            params.flags = params.flags and (WindowManager.LayoutParams.FLAG_FULLSCREEN.inv())
            context.window.setAttributes(params)
            context.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR)
        }
    }

    override fun showProgressBar(visible: Boolean) {
        context.setProgressBarIndeterminateVisibility(visible)
    }

    override fun updateViewImages(viewList: MutableList<Int>?) {

    }

    override fun isChangePage(): Boolean = true

    override fun setWriteLog(saveLog: Boolean) {
        this.writeLogTemp = saveLog
    }

    override fun isWriteLog(): Boolean = true

    override fun setThumbnail(isThumbnail: Boolean) {
        this.isThumbnailTemp = isThumbnail
    }

    override fun isThumbnail(): Boolean  = isThumbnailTemp

    override fun getViewBackground(): Any = bg

    override fun setIgnoreOriginalSize(ignoreOriginalSize: Boolean) {
       // TODO("Not yet implemented")
    }

    override fun isIgnoreOriginalSize(): Boolean = false

    override fun getPageListViewMovingPosition(): Byte = IPageListViewListener.Moving_Horizontal

    override fun dispose() {
        isDispose = true
        if (control != null) {
            control!!.dispose()
            control = null
        }
        toolsbar = null
        searchBar = null
        bottomBar = null
        if (appFrame != null) {
            val count = appFrame!!.childCount
            for (i in 0 until count) {
                val v = appFrame!!.getChildAt(i)
                if (v is AToolsbar) {
                    v.dispose()
                }
            }
            appFrame = null
        }

        if (wm != null) {
            wm = null
            wmParams = null
            pageUp!!.dispose()
            pageDown!!.dispose()
            penButton!!.dispose()
            eraserButton!!.dispose()
            settingsButton!!.dispose()
            pageUp = null
            pageDown = null
            penButton = null
            eraserButton = null
            settingsButton = null
        }
    }

}