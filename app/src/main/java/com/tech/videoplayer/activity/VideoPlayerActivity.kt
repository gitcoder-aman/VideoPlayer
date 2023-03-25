package com.tech.videoplayer.activity

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnClickListener
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tech.videoplayer.R
import com.tech.videoplayer.databinding.ActivityVideoPlayerBinding
import com.tech.videoplayer.model.MediaFilesModel
import com.tech.videoplayer.utils.timeConversion


class VideoPlayerActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityVideoPlayerBinding
    private lateinit var mVideoFiles: ArrayList<MediaFilesModel>
    private lateinit var title: TextView

    private var position: Int = -1
    private lateinit var videoTitle: String
    private lateinit var toolbarLinearLayout: LinearLayout
    private lateinit var seekbarLinearLayout: LinearLayout
    private lateinit var bottomLinearLayout: LinearLayout
    private lateinit var rootLayout: RelativeLayout
    private var isOpen: Boolean = true

    //video controller ids
    private lateinit var videoBack: ImageView
    private lateinit var unlock: ImageView
    private lateinit var exo_rewind: ImageView
    private lateinit var exo_prev: ImageView
    private lateinit var exo_pause: ImageView
    private lateinit var exo_next: ImageView
    private lateinit var exo_fastForward: ImageView
    private lateinit var scaling: ImageView
    private lateinit var exo_position: TextView
    private lateinit var exo_duration: TextView
    private lateinit var seekbar: SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        position = intent.getIntExtra("position", 1)
        videoTitle = intent.getStringExtra("video_title").toString()

        mVideoFiles = intent.extras?.getParcelableArrayList("videoArrayList")!!

        title = findViewById(R.id.video_title)
        toolbarLinearLayout = findViewById(R.id.toolbarLinearLayout)
        seekbarLinearLayout = findViewById(R.id.seekbarLayout)
        bottomLinearLayout = findViewById(R.id.bottom_icons_Layout)
        rootLayout = findViewById(R.id.root_layout)
        videoBack = findViewById(R.id.video_back)
        unlock = findViewById(R.id.unlock)
        exo_rewind = findViewById(R.id.exo_rewind)
        exo_pause = findViewById(R.id.exo_pause)
        exo_fastForward = findViewById(R.id.exo_fastForward)
        exo_next = findViewById(R.id.exo_next)
        exo_prev = findViewById(R.id.exo_prev)
        scaling = findViewById(R.id.scaling)
        exo_duration = findViewById(R.id.exo_duration)
        exo_position = findViewById(R.id.exo_position)
        seekbar = findViewById(R.id.seekBar)

        title.text = videoTitle

        binding.frameMainLayout.setOnClickListener {
            if (isOpen) {
                hideDefaultControls()
                isOpen = false
            } else {
                showDefaultControls()
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                isOpen = true
            }
        }
        videoBack.setOnClickListener(this)
        exo_rewind.setOnClickListener(this)
        exo_pause.setOnClickListener(this)
        exo_prev.setOnClickListener(this)
        exo_next.setOnClickListener(this)
        exo_fastForward.setOnClickListener(this)

        playVideo()
    }

    private fun getSeekBarStatus() {
        Thread(Runnable { // mp is your MediaPlayer
            // progress is your ProgressBar
            var currentPosition = 0
            val total: Int = binding.videoView.duration
            seekbar.max = total
            while (currentPosition < total && binding.videoView.isPlaying) {
                currentPosition = try {
                    Thread.sleep(1000)
                    binding.videoView.currentPosition
                } catch (e: InterruptedException) {
                    return@Runnable
                }
                Log.d("@@@@d",currentPosition.toString())
                seekbar.progress = currentPosition
            }
        }).start()

        seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(
                seekBar: SeekBar,
                ProgressValue: Int,
                fromUser: Boolean
            ) {
                if (fromUser) {
                    binding.videoView.seekTo(ProgressValue) //if user drags the seekbar, it gets the position and updates in textView.
                }
                exo_position.text = timeConversion().timeConvert(ProgressValue.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
    override fun onResume() {
        binding.videoView.start()
        super.onResume()
    }

    override fun onPause() {
        binding.videoView.pause()
        super.onPause()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

    private fun playVideo() {
        val path: String = mVideoFiles[position].getPath()

        binding.videoView.setVideoPath(path)

        binding.videoView.setOnPreparedListener {
            seekbar.max = binding.videoView.duration
            exo_duration.text = timeConversion().timeConvert(binding.videoView.duration.toString())
            binding.videoView.start()
            getSeekBarStatus();
        }
    }

    private fun hideDefaultControls() {
        toolbarLinearLayout.visibility = View.GONE
        seekbarLinearLayout.visibility = View.GONE
        bottomLinearLayout.visibility = View.GONE

        //we have also hide status and navigation when we click on screen
        val window: Window = this.window
        if (window == null) {
            return
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }

    private fun showDefaultControls() {
        toolbarLinearLayout.visibility = View.VISIBLE
        seekbarLinearLayout.visibility = View.VISIBLE
        bottomLinearLayout.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.video_back -> {
                onBackPressed()
            }
            R.id.exo_rewind -> {
                //10000 - 10 secs
                binding.videoView.seekTo(binding.videoView.currentPosition - 10000)
            }
            R.id.exo_fastForward -> {
                binding.videoView.seekTo(binding.videoView.currentPosition + 10000)
            }
            R.id.exo_pause -> {
                if (binding.videoView.isPlaying) {
                    binding.videoView.pause()
                    exo_pause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play))
                } else {
                    binding.videoView.start()
                    exo_pause.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pause))
                }
            }
        }
    }
}