package com.tech.videoplayer.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.tech.videoplayer.model.MediaFilesModel

class FetchMediaFiles{

    companion object {

        fun fetchMedia(folderName: String, context: Context): ArrayList<MediaFilesModel> {
            val videoFiles: ArrayList<MediaFilesModel> = ArrayList()
            val uri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val selection: String = MediaStore.Video.Media.DATA + " like?"
            val selectionArg = arrayOf("%$folderName%")
            val cursor = context.contentResolver.query(uri, null, selection, selectionArg, null)

            if (cursor != null && cursor.moveToNext()) {
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
                        MediaFilesModel(id, title, displayName, size, duration, path, dateAdded)

                    videoFiles.add(mediaFilesModel)  //add the data inside media model class
                }
            }
            return videoFiles
        }
    }
}
