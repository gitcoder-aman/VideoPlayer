package com.tech.videoplayer.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tech.videoplayer.R
import com.tech.videoplayer.activity.VideoFilesActivity
import com.tech.videoplayer.model.MediaFilesModel

class VideoFolderAdapter(
    private  var mediaFiles: ArrayList<MediaFilesModel>,
    private  var folderPath: ArrayList<String>,
    private  var context: Context
) : RecyclerView.Adapter<VideoFolderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.folder_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val indexPath:Int = folderPath[position].lastIndexOf("/")  //last file name found of index start like /storage/emulated/0/DCIM/Camera (camera getting index)
        val nameOfFolder:String = folderPath[position].substring(indexPath+1) //Camera get
        Log.d("@@@@", "position $position")
        Log.d("@@@@", "folderPath size ${folderPath.size}")
        Log.d("@@@@", "indexPath $indexPath")
        Log.d("@@@@", "nameOfFolder $nameOfFolder")


        holder.folderName.text = nameOfFolder
        holder.folderPath.text = folderPath[position]
        Log.d("@@@@", "Folder Path ${holder.folderPath.text}")
        holder.noOfFiles.text = "5 Videos"

        holder.itemView.setOnClickListener {
            val intent = Intent(context,VideoFilesActivity::class.java)
            intent.putExtra("folderName",nameOfFolder)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        Log.d("@@@@","size of folderPath Array "+folderPath.size.toString())
        return folderPath.size
    }

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val folderName : TextView
        val folderPath : TextView
        val noOfFiles : TextView

        init {
            folderName = itemView.findViewById(R.id.folderName)
            folderPath = itemView.findViewById(R.id.folderPath)
            noOfFiles = itemView.findViewById(R.id.noOfFiles)
        }
    }
}
