package com.example.datacalculator

import java.util.*

class DataHistoryModel(
    var date: String? = null, // data / month / year
    var duration: String?= null, // 00:00:00
    var byte: Double = 0.0,
    var kiloByte: Double = 0.0,
    var megaByte: Double = 0.0,
    )