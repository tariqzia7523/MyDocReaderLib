package com.all.doc

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.webkit.MimeTypeMap
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.all.doc.databinding.ActivityMainBinding
import com.xls.bbbbb.office.FileListUtils
import com.xls.bbbbb.office.FileModel
import com.xls.bbbbb.office.OpenFileActivity
import com.xls.bbbbb.office.constant.MainConstant
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var progressDialog : ProgressDialog
    val filesList = ArrayList<FileModel>()
    lateinit var adapter: AllFilesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        binding.allList.layoutManager = LinearLayoutManager(this)
        adapter = AllFilesAdapter(this@MainActivity,filesList,object : AllFilesAdapter.OnFileSelected {
            override fun onFileSelected(fileModel: FileModel) {
//                val intent = Intent()
//                intent.setClass(this@MainActivity, FileViewerActivity::class.java)
//                intent.putExtra("file_path", fileModel.filePath)
//                startActivityForResult(intent, RESULT_FIRST_USER)
                val intent = Intent()
                intent.setClass(this@MainActivity, OpenFileActivity::class.java)
                intent.putExtra(MainConstant.INTENT_FILED_FILE_PATH, fileModel.filePath)
                startActivityForResult(intent, RESULT_FIRST_USER)

            }
        })
        binding.allList.adapter = adapter
        binding.getAllFiles.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    getFiles()
                } else {
                    //request for the permission
                    val inten = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    val uri = Uri.fromParts("package", packageName, null)
                    inten.data = uri
                    startActivity(inten)
                }
            }
        }

        binding.getAllFiles.performClick()
    }

    private fun getFiles() {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        progressDialog.show()
        executor.execute {
            //Background work here
//            filesList.addAll(FileListUtils.getExternalFileList(this@MainActivity, MimeTypeMap.getSingleton().getMimeTypeFromExtension("docx")!!))
//            filesList.addAll(FileListUtils.getExternalFileList(this@MainActivity, MimeTypeMap.getSingleton().getMimeTypeFromExtension("xlsx")!!))
            try{
                filesList.addAll(FileListUtils.getExternalFileList(this@MainActivity, MimeTypeMap.getSingleton().getMimeTypeFromExtension("docx")!!))
            }catch (e : Exception){
                e.printStackTrace()
            }
            try{
                filesList.addAll(FileListUtils.getExternalFileList(this@MainActivity, MimeTypeMap.getSingleton().getMimeTypeFromExtension("xls")!!))
            }catch (e : Exception){
                e.printStackTrace()
            }

            try{
                filesList.addAll(FileListUtils.getExternalFileList(this@MainActivity, MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")!!))
            }catch (e : Exception){
                e.printStackTrace()
            }
            handler.post {
                progressDialog.dismiss()
                adapter.notifyDataSetChanged()

            }
        }

    }
}