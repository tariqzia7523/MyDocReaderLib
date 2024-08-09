package com.xls.bbbbb.office

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.webkit.MimeTypeMap
import java.io.*

class AppHelper {
    override fun equals(obj: Any?): Boolean {
        return super.equals(obj)
    }

    companion object {
        private const val BUFFER_SIZE = 1024 * 4
        private const val EOF = -1
        fun getMimeType(context: Context, uri: Uri): String? {
            return if (uri.scheme == "content") {
                MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(context.contentResolver.getType(uri))
            } else MimeTypeMap.getFileExtensionFromUrl(
                Uri.fromFile(
                    File(
                        uri.path
                    )
                ).toString()
            )
        }

        fun copy(context: Context, uri: Uri?, file: File?) {
            try {
                val openInputStream = context.contentResolver.openInputStream(
                    uri!!
                )
                if (openInputStream != null) {
                    val fileOutputStream = FileOutputStream(file)
                    copy(openInputStream, fileOutputStream)
                    openInputStream.close()
                    fileOutputStream.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @Throws(IOException::class)
        fun copy(input: InputStream, output: OutputStream): Long {
            var count: Long = 0
            var n: Int
            val buffer = ByteArray(BUFFER_SIZE)
            while (EOF != input.read(buffer).also { n = it }) {
                output.write(buffer, 0, n)
                count += n.toLong()
            }
            return count
        }

        fun getFilePathFromExternalAppsURI(context: Context, uri: Uri?, str: String): String? {
            val fileName = getFileName(uri)
            if (TextUtils.isEmpty(fileName)) {
                return null
            }
            val filesDir = context.filesDir
            val sb = fileName +
                    str
            val file = File(filesDir, sb)
            copy(context, uri, file)
            return file.absolutePath
        }

        fun getFileName(uri: Uri?): String? {
            var str: String? = null
            if (uri == null) {
                return null
            }
            val path = uri.path!!
            val lastIndexOf = path.lastIndexOf('/')
            if (lastIndexOf != -1) {
                str = path.substring(lastIndexOf + 1)
            }
            return str
        }

        fun getBaseFileName(fileName: String): String? {
            return if (TextUtils.isEmpty(fileName) || !fileName.contains(".") || fileName.endsWith(".")) null else fileName.substring(
                0,
                fileName.lastIndexOf(".")
            )
        }
    }
}