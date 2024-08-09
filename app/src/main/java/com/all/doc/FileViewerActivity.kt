package com.all.doc

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.all.doc.databinding.ActivityFileViewerBinding
import com.xls.bbbbb.office.FileOpenerClass
import java.io.File

class FileViewerActivity : AppCompatActivity() {
    lateinit var binding : ActivityFileViewerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFileViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try{
            val filepath = intent!!.extras!!.get("file_path") as String
            FileOpenerClass(this@FileViewerActivity, File(filepath).toUri(),binding.frameLayout,
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
        }catch (e : Exception){
            e.printStackTrace()
        }
    }
}