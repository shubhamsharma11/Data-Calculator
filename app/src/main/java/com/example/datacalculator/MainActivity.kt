package com.example.datacalculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityViewModel: MainActivityViewModel

    var dataHistoryList: MutableList<DataHistoryModel>? = null

    var dataHistoryListAdapter : DataHistoryListAdapter? = null

    val download : Button by lazy {
        findViewById(R.id.download)
    }
    val dataCalculator : Button by lazy {
        findViewById(R.id.data_calculator)
    }
    val hour : Spinner by lazy {
        findViewById(R.id.hour)
    }
    val minute : Spinner by lazy {
        findViewById(R.id.minute)
    }
    val second : Spinner by lazy {
        findViewById(R.id.second)
    }
    val dataHistoryRecyclerView : RecyclerView by lazy {
        findViewById(R.id.data_history_recycler_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var h : Int = 0
        var m : Int = 0
        var s : Int = 0

        val hours = MainActivityViewModel.hours

        val minutes = MainActivityViewModel.minutes

        val seconds = MainActivityViewModel.seconds

        dataHistoryList = mutableListOf()

        dataHistoryListAdapter = DataHistoryListAdapter(this, dataHistoryList!!)


        dataHistoryRecyclerView.layoutManager = LinearLayoutManager(this)

        dataHistoryRecyclerView.adapter = dataHistoryListAdapter

        val hoursAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, hours)
        val minutesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, minutes)
        val secondsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, seconds)

        hoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        minutesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        secondsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        hour.adapter = hoursAdapter
        minute.adapter = minutesAdapter
        second.adapter = secondsAdapter

        hour.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                h= parent.getItemAtPosition(position).toString().toInt()
                dataCalculator.isEnabled = h != 0 || m!= 0 || s!=0
                Log.i("hour",h.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
// Do nothing
            }
        }

        minute.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                m = parent.getItemAtPosition(position).toString().toInt()
                dataCalculator.isEnabled = h != 0 || m!= 0 || s!=0
                Log.i("minute",m.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
// Do nothing
            }
        }

        second.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                s = parent.getItemAtPosition(position).toString().toInt()
                dataCalculator.isEnabled = h != 0 || m!= 0 || s!=0
                Log.i("second",s.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
// Do nothing
            }
        }

        download.setOnClickListener(View.OnClickListener {
            startDownloading()
        })

        dataCalculator.setOnClickListener(View.OnClickListener {
            startCalculatingData(h, m, s)
        })
    }

    fun hoursToMili(hours: Int): Long{
        return (hours * 60 * 60 * 1000).toLong()
    }

    fun minutesToMili(minutes: Int): Long{
        return (minutes * 60 * 1000).toLong()
    }

    fun secondsToMili(seconds: Int): Long{
        return (seconds * 1000).toLong()
    }

     fun startCalculatingData(hours: Int,minutes: Int, seconds: Int) {
         dataCalculator.isEnabled = false
         hour.setSelection(0)
         minute.setSelection(0)
         second.setSelection(0)
          GlobalScope.launch {
             var data = 0

             var endTime : Long = 0
             endTime = hoursToMili(hours) + minutesToMili(minutes) + secondsToMili(seconds)
             val networkUsage =  NetworkUsage(System.currentTimeMillis(), System.currentTimeMillis() + endTime)

             data = networkUsage.getDataUsage(applicationContext).toString().toInt();

             Log.i("data Used in Bytes", "$data Bytes ")
             Log.i("data Used in KB", (data/1024.00).toString() + " kb ")
             Log.i("data Used in MB", (data/(1024.00*1024.00)).toString() + " mb ")

              withContext(Dispatchers.Main) {
                  updateUI(data, hours, minutes, seconds)
              }
         }
    }

    fun updateUI(data: Int, h: Int, m:Int, s:Int) {
        // Update the UI here
        println("Result: $data")

        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateText = dateFormat.format(currentDate)

        var dataHistoryModel : DataHistoryModel = DataHistoryModel(
            dateText,
            "$h : $m : $s",
            data.toDouble(),
            "%.2f".format(data / 1024.00).toDouble(),
            "%.2f".format(data/(1024.00*1024.00)).toDouble()
        )

        dataHistoryList?.add(dataHistoryModel)
        dataHistoryListAdapter?.notifyDataSetChanged()
    }

    fun startDownloading(){
        GlobalScope.launch {
            try {
                val url = URL("https://www.google.com/imgres?imgurl=https%3A%2F%2Fi.ytimg.com%2Fvi%2Fx1Vk7d509Rk%2Fmaxresdefault.jpg&imgrefurl=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3Dx1Vk7d509Rk&tbnid=6Q2YyYWag4R22M&vet=12ahUKEwjpyKjg8oL9AhVL7XMBHWyLBs0QMygBegUIARDNAQ..i&docid=9_QVfIOcezobLM&w=1280&h=720&q=lyrics%20of%20unstoppable&ved=2ahUKEwjpyKjg8oL9AhVL7XMBHWyLBs0QMygBegUIARDNAQ")
                val input: InputStream = url.openStream()
                val os: OutputStream = FileOutputStream("fileName.jpeg")
                val b = ByteArray(2048)
                var length: Int
                while (input.read(b).also { length = it } != -1) {
                    os.write(b, 0, length)
                }
                input.close()
                os.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}

/**
 *
 */