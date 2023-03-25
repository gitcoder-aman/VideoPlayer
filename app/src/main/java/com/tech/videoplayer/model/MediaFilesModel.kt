package com.tech.videoplayer.model

import android.os.Parcel
import android.os.Parcelable

class MediaFilesModel(
    private var id: Int,
    private var title: String?,
    private var displayName: String?,
    private var size: String?,
    private var duration: String?,
    private var path: String?,
    private var dateAdded: String?
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    // Getter and setter for the id property
    fun getId(): Int {
        return id
    }
    fun setId(id: Int) {
        this.id = id
    }

    // Getter and setter for the title property
    fun getTitle(): String {
        return title.toString()
    }
    fun setTitle(title: String) {
        this.title = title
    }

    // Getter and setter for the displayName property
    fun getDisplayName(): String {
        return displayName.toString()
    }
    fun setDisplayName(displayName: String) {
        this.displayName = displayName
    }

    // Getter and setter for the size property
    fun getSize(): String {
        return size.toString()
    }
    fun setSize(size: String) {
        this.size = size
    }

    // Getter and setter for the duration property
    fun getDuration(): String {
        return duration.toString()
    }
    fun setDuration(duration: String) {
        this.duration = duration
    }

    // Getter and setter for the path property
    fun getPath(): String {
        return path.toString()
    }
    fun setPath(path: String) {
        this.path = path
    }

    // Getter and setter for the dateAdded property
    fun getDateAdded(): String {
        return dateAdded.toString()
    }
    fun setDateAdded(dateAdded: String) {
        this.dateAdded = dateAdded
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(displayName)
        parcel.writeString(size)
        parcel.writeString(duration)
        parcel.writeString(path)
        parcel.writeString(dateAdded)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MediaFilesModel> {
        override fun createFromParcel(parcel: Parcel): MediaFilesModel {
            return MediaFilesModel(parcel)
        }

        override fun newArray(size: Int): Array<MediaFilesModel?> {
            return arrayOfNulls(size)
        }
    }
}
