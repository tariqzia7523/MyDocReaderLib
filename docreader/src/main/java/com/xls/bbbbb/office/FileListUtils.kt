package com.xls.bbbbb.office

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.util.Collections

object FileListUtils {

    fun getExternalFileList(context: Activity,mimeType: String): ArrayList<FileModel> {
        val uriList: ArrayList<FileModel> = ArrayList()
        try{
            Log.e("***mime","mimetype "+mimeType)
            val cr = context.contentResolver
            val uri: Uri = MediaStore.Files.getContentUri("external")
            val projection =
                arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.DISPLAY_NAME)
            val selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE)
            val selectionArgs: Array<String>? = null
            val selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE + "=?"
            val selectionArgsPdf = arrayOf(mimeType)

//        val selectionArgsPdf = mimeType
            val cursor: Cursor = cr.query(uri, projection, selectionMimeType, selectionArgsPdf, null)!!

            cursor.moveToFirst()
            while (!cursor.isAfterLast()) {

                val fileId: Long = cursor.getLong(0)
                val fileUri: Uri = Uri.parse(uri.toString() + "/" + fileId)
                Log.e("***URI","File Uri "+fileUri.toString())
                val filepath = ImageFilePath.getPath(context, fileUri);
                Log.e("***URI","Filepath "+filepath)
                val displayName: String = cursor.getString(1)
                uriList.add(FileModel(displayName, fileUri,filepath!!))
                cursor.moveToNext()
            }
            cursor.close()
        }catch (e : Exception){
            e.printStackTrace()
        }

        return uriList
    }

//    fun getFiles(context: Activity,filetype :String): ArrayList<FileModel>{
//        val files = ArrayList<FileModel>()
//        if(filetype.equals(context.getString(R.string.word))){
//            files.addAll(getExternalFileList(context, MimeTypeMap.getSingleton().getMimeTypeFromExtension("docx")!!))
//            files.addAll(getExternalFileList(context, MimeTypeMap.getSingleton().getMimeTypeFromExtension("doc")!!))
//        }else if(filetype.equals(context.getString(R.string.ppt))){
//            files.addAll(getExternalFileList(context, MimeTypeMap.getSingleton().getMimeTypeFromExtension("pptx")!!))
//            files.addAll(getExternalFileList(context, MimeTypeMap.getSingleton().getMimeTypeFromExtension("ppt")!!))
//        }else if(filetype.equals(context.getString(R.string.excel))){
//            files.addAll(getExternalFileList(context, MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx")!!))
//            files.addAll(getExternalFileList(context, MimeTypeMap.getSingleton().getMimeTypeFromExtension("xls")!!))
//        }else if(filetype.equals(context.getString(R.string.pdf))){
//            files.addAll(getExternalFileList(context, MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")!!))
//        }else if(filetype.equals(context.getString(R.string.txt))){
//            files.addAll(getExternalFileList(context, MimeTypeMap.getSingleton().getMimeTypeFromExtension("txt")!!))
//        }else if(filetype.equals(context.getString(R.string.xml))){
//            files.addAll(getExternalFileList(context, MimeTypeMap.getSingleton().getMimeTypeFromExtension("xml")!!))
//        }else if(filetype.equals(context.getString(R.string.html))){
//            files.addAll(getExternalFileList(context, MimeTypeMap.getSingleton().getMimeTypeFromExtension("html")!!))
//        }else if(filetype.equals(context.getString(R.string.csv))){
//            files.addAll(getExternalFileList(context, MimeTypeMap.getSingleton().getMimeTypeFromExtension("csv")!!))
//        }else if(filetype.equals(context.getString(R.string.rtf))){
//            files.addAll(getExternalFileList(context, MimeTypeMap.getSingleton().getMimeTypeFromExtension("rtf")!!))
//            files.addAll(getExternalFileList(context, "application/rtf"))
//        }
//        return files
//    }
    fun getRecentFiles(context: Context,folderName : String): ArrayList<File>{
        val files  = getFiles(getDirectory(context, folderName))
        Log.e("***Size","Images "+files.size)
        Collections.sort(files, object : Comparator<File?> {
            override fun compare(o1: File?, o2: File?): Int {
                val k = o2!!.lastModified() - o1!!.lastModified()
                return if (k > 0) {
                    1
                } else if (k == 0L) {
                    0
                } else {
                    -1
                }
            }
        })
        return files
    }

    fun getDirectory(context: Context,folderName : String): File{
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val drive = File(directory,folderName)
        if(!drive.exists())
            drive.mkdir()
        return drive
    }

    fun getFiles(directory: File): ArrayList<File> {
        val arraylist = ArrayList<File>()
        try{
            val files: Array<File> = directory.listFiles() // get files list from directory
            Log.e("images", "" + files.size)
            arraylist.addAll(files)
        }catch ( e : Exception){
            e.printStackTrace()
        }
        return arraylist

    }
}