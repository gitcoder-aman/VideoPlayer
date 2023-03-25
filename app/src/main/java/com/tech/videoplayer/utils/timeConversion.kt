package com.tech.videoplayer.utils

class timeConversion {


        fun timeConvert(value: String): String {
            val videoTime: String
            val duration: Int = Integer.parseInt(value)

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