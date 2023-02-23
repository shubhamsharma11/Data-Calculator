package com.example.datacalculator

import android.util.Log
import java.text.SimpleDateFormat

class MyDateFormat {
    constructor()

    var year:Int?= 0
    var month:Int?= 0
    var day:Int?= 0
    var hour:Int?= 0
    var minute:Int?= 0
    var second:Int?= 0


    fun setYear(i:Int){
        year = i
    }

    fun setMonth(i:Int){
        month = i
    }

    fun setDay(i:Int){
        day = i
    }
    fun sethour(i:Int){
        hour = i
    }
    fun setMinutes(i:Int){
        minute = i
    }

    fun getDate(): String{
        return "$day-$month-$year $hour:$minute:$second"
    }

    fun getDateMillis(): Long{
        val date_string = "$day-$month-$year $hour:$minute:$second"
        val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        var newDate = formatter.parse(date_string)
        val millis = newDate!!.time
        return millis
    }

}