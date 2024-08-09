package com.all.doc

import android.content.Context
import android.view.*
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.xls.bbbbb.office.FileModel

class AllFilesAdapter(var context: Context, var list: ArrayList<FileModel>, var onFileSelected: OnFileSelected) : RecyclerView.Adapter<AllFilesAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fileName = itemView.findViewById(R.id.file_name) as AppCompatTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.file_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fileName.text = list[position].displayName

        holder.itemView.setOnClickListener {
            onFileSelected.onFileSelected(list.get(position))
        }

    }

    override fun getItemCount():
            Int = list.size

    interface OnFileSelected{
        fun onFileSelected(fileModel: FileModel)
    }
}