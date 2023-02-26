package com.example.datacalculator

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*


class TimePickerFragment(val myDateFormat: MyDateFormat, val isStartTime: Boolean, val callBack: (isStartTime: Boolean) -> Unit) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val h = c.get(Calendar.HOUR)
        val m = c.get(Calendar.MINUTE)
        return TimePickerDialog(context,this, h,m, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {
        myDateFormat.sethour(hourOfDay)
        myDateFormat.setMinutes(minute)
        callBack(isStartTime)
    }
}