package com.famous.paperplane.zhihu.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * A util class for formatting the date between string and long.
 */

fun formatZhihuDailyDateLongToString(date: Long): String {
    val sDate: String
    val d = Date(date + 24 * 60 * 60 * 1000)
    val format = SimpleDateFormat("yyyyMMdd")
    sDate = format.format(d)

    return sDate
}

fun formatZhihuDailyDateStringToLong(date: String): Long {
    var d: Date? = null
    try {
        d = SimpleDateFormat("yyyyMMdd").parse(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return if (d == null) 0 else d.time
}