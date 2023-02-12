package com.example.datacalculator

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivityViewModel(private val context: Context) : ViewModel() {

    companion object{

        val hours = arrayOf("00", "01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20",
            "21","22","23")

        val minutes = arrayOf("00", "01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20",
            "21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40",
            "41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59")

        val seconds = arrayOf("00", "01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20",
            "21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40",
            "41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59")

    }

    private var _dataHistoryList = MutableLiveData<MutableList<DataHistoryModel>>().apply {
        value = mutableListOf()
    }
    var dataHistoryList: LiveData<MutableList<DataHistoryModel>> = _dataHistoryList

    private val _data = MutableLiveData<Int>().apply {
        value = 0
    }
    val data: LiveData<Int> = _data

    private val _h = MutableLiveData<Int>().apply {
        value = 0
    }
    val h: LiveData<Int> = _h

    private val _m = MutableLiveData<Int>().apply {
        value = 0
    }
    val m: LiveData<Int> = _m

    private val _s = MutableLiveData<Int>().apply {
        value = 0
    }
    val s: LiveData<Int> = _s

    fun hoursToMili(hours: Int): Long{
        return (hours * 60 * 60 * 1000).toLong()
    }

    fun minutesToMili(minutes: Int): Long{
        return (minutes * 60 * 1000).toLong()
    }

    fun secondsToMili(seconds: Int): Long{
        return (seconds * 1000).toLong()
    }

    fun startCalculatingData() {
        viewModelScope.launch {
            var endTime : Long = 0
            endTime = hoursToMili(_h.value!!) + minutesToMili(_m.value!!) + secondsToMili(_s.value!!)
            val networkUsage =  NetworkUsage(System.currentTimeMillis(), System.currentTimeMillis() + endTime)

            _data.value = networkUsage.getDataUsage(context).toString().toInt();
        }
    }

    fun setHours(hours: Int){
        _h.value = hours
    }

    fun setMinutes(minutes: Int){
        _m.value = minutes
    }

    fun setSeconds(seconds: Int){
        _s.value = seconds
    }

    fun addItemInList() {

        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateText = dateFormat.format(currentDate)

        var dataHistoryModel : DataHistoryModel = DataHistoryModel(
            dateText,
            "${h.value} : ${m.value} : ${s.value}",
            _data.value!!.toDouble(),
            "%.2f".format(data.value!!.div(1024.00)).toDouble(),
            "%.2f".format(_data.value!!/(1024.00*1024.00)).toDouble()
        )

        _dataHistoryList.value?.add(dataHistoryModel)
    }





}