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

    val myDateFormat = MyDateFormat()
    var string = MutableLiveData<String>().apply {
        value = ""
    }
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

}