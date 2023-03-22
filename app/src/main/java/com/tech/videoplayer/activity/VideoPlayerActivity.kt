package com.tech.videoplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tech.videoplayer.R

class VideoPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
    }
}