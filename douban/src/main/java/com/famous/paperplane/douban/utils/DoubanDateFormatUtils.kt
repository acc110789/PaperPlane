package com.famous.paperplane.douban.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun formatDoubanMomentDateLongToString(date: Long): String {
    val sDate: String
    val d = Date(date)
    val format = SimpleDateFormat("yyyy-MM-dd")
    sDate = format.format(d)

    return sDate
}

fun formatDoubanMomentDateStringToLong(date: String): Long {
    var d: Date? = null
    try {
        d = SimpleDateFormat("yyyy-MM-dd").parse(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return if (d == null) 0 else d.time
}