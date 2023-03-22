@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.tech.videoplayer.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tech.videoplayer.R
import com.tech.videoplayer.adapter.VideoFolderAdapter
import com.tech.videoplayer.databinding.ActivityMainBinding
import com.tech.videoplayer.model.MediaFilesModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mediaFiles: ArrayList<MediaFilesModel> = ArrayList()
    private var allFolderList: ArrayList<String> = ArrayList()
    private lateinit var videoFolderAdapter: VideoFolderAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Click on permissions and allow storage.", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showFolder()
            binding.swipeRefresh.setColorSchemeColors(Color.BLUE, Color.YELLOW, Color.BLUE);
            binding.swipeRefresh.setOnRefreshListener {

                showFolder()
                binding.swipeRefresh.isRefreshing = false
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    private fun showFolder() {
        mediaFiles = fetchMedia()
        videoFolderAdapter = VideoFolderAdapter(mediaFiles, allFolderList, this)
        binding.foldersRv.adapter = videoFolderAdapter
        binding.foldersRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        videoFolderAdapter.notifyDataSetChanged()
    }

    @SuppressLint("Recycle")
    @RequiresApi(Build.VERSION_CODES.O)
     fun fetchMedia(): ArrayList<MediaFilesModel> {
        val mediaFilesArrayList: ArrayList<MediaFilesModel> = ArrayList()
        val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//
        val cursor: Cursor? = contentResolver.query(uri, null, null, null)

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id: Int =
                    cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID) as Int)
                val title: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE) as Int)
                val displayName: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME) as Int)
                val size: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE) as Int)
                val duration: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION) as Int)
                val path: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA) as Int)
                val dateAdded: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED) as Int)

                val mediaFilesModel: MediaFilesModel =
                    MediaFilesModel(id,title,displayName,size,duration,path,dateAdded)
                val index: Int = path.lastIndexOf("/")
                val substring: String = path.substring(0, index)
                if (!allFolderList.contains(substring)) {
                    allFolderList.add(substring)
                }
                mediaFilesArrayList.add(mediaFilesModel)
            }
        }
        Log.d("@@@@", "AllFolderMain" + allFolderList.size.toString())
        Log.d("@@@@", "MediaMain" + mediaFilesArrayList.size.toString())
        return mediaFilesArrayList
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.folder_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.rateUs -> {
                val uri: Uri =
                    Uri.parse("https://play.google.com/store/apps/details?id=" + this.packageName)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            R.id.refresh_folders -> {
                finish()
                startActivity(intent)
            }
            R.id.share_app -> {
                val shareIntent: Intent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Check this app via\n" + "https://play.google.com/store/apps/details?id=" + this.packageName
                )
                shareIntent.type = "text/plain"
                startActivity(Intent.createChooser(shareIntent, "Share app via"))
            }
        }
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }
}