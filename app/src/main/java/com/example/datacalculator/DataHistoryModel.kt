package com.example.datacalculator

class DataHistoryModel(
    var date: String? = null, // date / month / year
    var from: String?= null, // 00:00:00
    var to: String? = null,
    var byte: Double = 0.0,
    var kiloByte: Double = 0.0,
    var megaByte: Double = 0.0,
    var gigaByte: Double = 0.0,
    )