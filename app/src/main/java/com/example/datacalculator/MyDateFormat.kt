package com.example.datacalculator

import java.text.SimpleDateFormat

class MyDateFormat {

    private var year:Int?= 0
    private var month:Int?= 0
    private var day:Int?= 0
    private var hour:Int?= 0
    private var minute:Int?= 0
    private var second:Int?= 0


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

    fun getDateMillis(): Long {
        val dateString = "$day-$month-$year $hour:$minute:$second"
        val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        val newDate = formatter.parse(dateString)
        return newDate!!.time
    }

}