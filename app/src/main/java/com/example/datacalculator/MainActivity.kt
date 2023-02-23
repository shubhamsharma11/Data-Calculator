package com.example.datacalculator

import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.TrafficStats
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(){

    var dataHistoryList: MutableList<DataHistoryModel>? = null

    var dataHistoryListAdapter : DataHistoryListAdapter? = null

    val permission : Button by lazy {
        findViewById(R.id.get_permission)
    }
    val dataCalculator : Button by lazy {
        findViewById(R.id.data_calculator)
    }
    val startTimeTV : TextView by lazy {
        findViewById(R.id.start_time_tv)
    }

    val endTimeTV : TextView by lazy {
        findViewById(R.id.end_time_tv)
    }

    val startTime : TextView by lazy {
        findViewById(R.id.start_time)
    }

    val endTime : TextView by lazy {
        findViewById(R.id.end_time)
    }

    val dataHistoryRecyclerView : RecyclerView by lazy {
        findViewById(R.id.data_history_recycler_view)
    }

    var dateFragment : DatePickerFragment?= null
    var timeFragment : TimePickerFragment?= null
    val myDateFormat = MyDateFormat()
    var startTimeInMillis : Long = 0
    var endTimeInMillis : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataHistoryList = mutableListOf()

        dataHistoryListAdapter = DataHistoryListAdapter(this, dataHistoryList!!)


        dataHistoryRecyclerView.layoutManager = LinearLayoutManager(this)

        dataHistoryRecyclerView.adapter = dataHistoryListAdapter

        startTimeTV.setOnClickListener(View.OnClickListener {
            openTimePicker(myDateFormat)
            openDatePicker(myDateFormat)
        })
        endTimeTV.setOnClickListener(View.OnClickListener {
            startTime.setText(myDateFormat.getDate())
            startTimeInMillis = myDateFormat.getDateMillis()
            openTimePicker(myDateFormat)
            openDatePicker(myDateFormat)
        })

        dataCalculator.setOnClickListener(View.OnClickListener {
            endTime.setText(myDateFormat.getDate())
            endTimeInMillis = myDateFormat.getDateMillis()
            Log.i("start", startTimeInMillis.toString())
            Log.i("end", endTimeInMillis.toString())

            val networkStatsManager = this.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager
            val startTime = startTimeInMillis // start time in milliseconds
            val endTime = endTimeInMillis // end time in milliseconds
            val networkType = ConnectivityManager.TYPE_MOBILE // network type (e.g. TYPE_MOBILE or TYPE_WIFI)

            val networkStats = networkStatsManager.querySummary(networkType, null, startTime, endTime)
            val bucket = NetworkStats.Bucket()
            var totalBytes: Long = if (networkStats.getNextBucket(bucket)) bucket.rxBytes + bucket.txBytes else 0

            while (networkStats.hasNextBucket())
            {
                if (networkStats.getNextBucket(bucket))
                    totalBytes += bucket.rxBytes + bucket.txBytes
            }
            Log.i("Total Bytes", totalBytes.toString())

            totalBytes = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes()
            Log.i("Total Bytes", totalBytes.toString())
        })

        permission.setOnClickListener(View.OnClickListener {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        })
    }

    fun openDatePicker(myDateFormat: MyDateFormat){
        dateFragment = DatePickerFragment(myDateFormat)
        dateFragment!!.show(supportFragmentManager, "Date Picker")
    }

    fun openTimePicker(myDateFormat: MyDateFormat){
        timeFragment = TimePickerFragment(myDateFormat)
        timeFragment!!.show(supportFragmentManager, "Time Picker")
    }

    /**
     * fun hoursToMili(hours: Int): Long{
    return (hours * 60 * 60 * 1000).toLong()
    }

    fun minutesToMili(minutes: Int): Long{
    return (minutes * 60 * 1000).toLong()
    }

    fun secondsToMili(seconds: Int): Long{
    return (seconds * 1000).toLong()
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

    fun startCalculatingData(hours: Int,minutes: Int, seconds: Int) {
    dataCalculator.isEnabled = false
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
     */

    /*override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.year = year;
        this.dayOfMonth = dayOfMonth;
        this.month = month + 1;
        var c = Calendar.getInstance()
        h = c.get(Calendar.HOUR);
        m = c.get(Calendar.MINUTE);
        var timePickerDialog = TimePickerDialog(this, this, h, m, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        h = hourOfDay;
        m = minute;

        /// make date object
        //"26-09-1989"
        val date_string = "$dayOfMonth-$month-$year $h:$m:$s"
        //Instantiating the SimpleDateFormat class
        //Instantiating the SimpleDateFormat class
        val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        //Parsing the given String to Date object
        //Parsing the given String to Date object
        newDate = formatter.parse(date_string)
        Log.i("Date time format", newDate.toString())

        val millis = newDate!!.time
        Log.i("Date time Milli", millis.toString())
    }*/

}
