package com.tech.videoplayer.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.tech.videoplayer.utils.StringTOLong
import com.tech.videoplayer.utils.timeConversion
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
        Log.d("@@@@", "Size " + size)

        val videoSize: Long = StringTOLong().stringToLong(size)
        holder.videoSize.text = android.text.format.Formatter.formatFileSize(context,videoSize)

        val milliSeconds: String = model.getDuration()
        holder.videoDuration.text = timeConversion().timeConvert(milliSeconds)

        Glide.with(context)
            .load(File(model.getPath()))
            .into(holder.thumbnail)
        Log.d("@@@@", model.getPath())

        holder.menu_more.setOnClickListener {
            Toast.makeText(context, "menu more", Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra("position",position)
            intent.putExtra("video_title",model.getDisplayName())

            val bundle:Bundle = Bundle()
            bundle.putParcelableArrayList("videoArrayList",videoList)
            intent.putExtras(bundle)
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
}