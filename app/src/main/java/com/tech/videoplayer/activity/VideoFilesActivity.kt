@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.tech.videoplayer.activity

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tech.videoplayer.adapter.VideoFilesAdapter
import com.tech.videoplayer.databinding.ActivityVideoFilesBinding
import com.tech.videoplayer.model.MediaFilesModel
import com.tech.videoplayer.utils.FetchMediaFiles

class VideoFilesActivity : AppCompatActivity() {
    private lateinit var binding:ActivityVideoFilesBinding
    private var videoFileArrayList:ArrayList<MediaFilesModel> = ArrayList()
    private lateinit var folderName:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoFilesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        folderName = intent.getStringExtra("folderName").toString()
        supportActionBar?.title = folderName

        showVideoFiles()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showVideoFiles() {

        videoFileArrayList = FetchMediaFiles.fetchMedia(folderName,this)
        val videoFilesAdapter:VideoFilesAdapter = VideoFilesAdapter(videoFileArrayList,this)
        binding.videosRv.adapter = videoFilesAdapter
        binding.videosRv.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        videoFilesAdapter.notifyDataSetChanged()
    }


}