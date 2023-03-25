package com.tech.videoplayer.utils

class StringTOLong {

    fun stringToLong(size:String):Long{
        var videoSize :Long = 0
        for (i in size){
            val s:Long = i.toLong() - 48

            videoSize = videoSize * 10 + s
        }
        return videoSize
    }
}