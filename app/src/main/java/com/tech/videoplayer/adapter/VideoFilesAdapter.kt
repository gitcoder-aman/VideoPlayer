package com.tech.videoplayer.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tech.videoplayer.R
import com.tech.videoplayer.activity.VideoPlayerActivity
import com.tech.videoplayer.model.MediaFilesModel
import java.io.File

class VideoFilesAdapter(
    private val videoList: ArrayList<MediaFilesModel>,
    private val context: Context
) : RecyclerView.Adapter<VideoFilesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = videoList[position]
        holder.videoName.text = model.getDisplayName()
        val size: String = model.getSize()
        Log.d("@@@@", "" + size)
//        holder.videoSize.text = android.text.format.Formatter.formatFileSize(context,(size as Long))

        val milliSeconds: String = model.getDuration()
        holder.videoDuration.text = timeConversion(milliSeconds)

        Glide.with(context)
            .load(File(model.getPath()))
            .into(holder.thumbnail)
        Log.d("@@@@", model.getPath())

        holder.menu_more.setOnClickListener {
            Toast.makeText(context, "menu more", Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            context.startActivity(intent)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val thumbnail: ImageView
        val menu_more: ImageView
        val videoName: TextView
        val videoSize: TextView
        val videoDuration: TextView

        init {
            thumbnail = itemView.findViewById(R.id.thumbnail)
            menu_more = itemView.findViewById(R.id.video_menu_more)
            videoName = itemView.findViewById(R.id.video_name)
            videoSize = itemView.findViewById(R.id.video_size)
            videoDuration = itemView.findViewById(R.id.video_duration)
        }

    }

    private fun timeConversion(value: String): String {
        val videoTime: String
        val duration:Int = Integer.parseInt(value)

        var secs: Int = duration / 1000
        val hrs: Int = (secs / 3600)
        secs -= hrs * 3600  // update the secs value
        val min: Int = secs / 60
        secs -= min * 60

        if (hrs > 0) {
            videoTime = String.format("%02d:%02d:%02d", hrs, min, secs)
        } else {
            videoTime = String.format("%02d:%02d", min, secs)
        }
        return videoTime
    }
}