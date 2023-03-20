package com.tech.videoplayer.model

class MediaFilesModel(
    private var id: Int,
    private var title: String,
    private var displayName: String,
    private var size: String,
    private var duration: String,
    private var path: String,
    private var dateAdded: String
) {
    // Getter and setter for the id property
    fun getId(): Int {
        return id
    }
    fun setId(id: Int) {
        this.id = id
    }

    // Getter and setter for the title property
    fun getTitle(): String {
        return title
    }
    fun setTitle(title: String) {
        this.title = title
    }

    // Getter and setter for the displayName property
    fun getDisplayName(): String {
        return displayName
    }
    fun setDisplayName(displayName: String) {
        this.displayName = displayName
    }

    // Getter and setter for the size property
    fun getSize(): String {
        return size
    }
    fun setSize(size: String) {
        this.size = size
    }

    // Getter and setter for the duration property
    fun getDuration(): String {
        return duration
    }
    fun setDuration(duration: String) {
        this.duration = duration
    }

    // Getter and setter for the path property
    fun getPath(): String {
        return path
    }
    fun setPath(path: String) {
        this.path = path
    }

    // Getter and setter for the dateAdded property
    fun getDateAdded(): String {
        return dateAdded
    }
    fun setDateAdded(dateAdded: String) {
        this.dateAdded = dateAdded
    }
}
