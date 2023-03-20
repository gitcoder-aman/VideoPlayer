package com.tech.videoplayer.activity

    fun main(){

        val arr = arrayOf(10,20,30,40,"loop")
        for((index, value) in arr.withIndex()){
            println(value)
            println(index)
        }
    }