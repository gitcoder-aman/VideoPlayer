package com.tech.videoplayer.activity

    fun main(){

//        val arr = arrayOf(10,20,30,40,"loop")
//        for((index, value) in arr.withIndex()){
//            println(value)
//            println(index)
//        }

        val duration = 7898128
        var secs:Int = duration/1000
        val hrs:Int = (secs/3600)
        secs -= hrs*3600  // update the secs value
        val mins:Int = secs/60
        secs -= mins*60

        println(hrs)
        println(mins)
        println(secs)
    }