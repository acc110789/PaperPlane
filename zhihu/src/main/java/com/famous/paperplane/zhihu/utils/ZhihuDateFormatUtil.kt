package com.famous.paperplane.zhihu.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lizhaotailang on 2017/5/21.
 *
 * A util class for formatting the date between string and long.
 */

fun formatZhihuDailyDateToTomorrowToString(date: Long): String =
    formatZhihuDailyDateToString(date + 24 * 60 * 60 * 1000)

fun formatZhihuDailyDateToTodayToString(date: Long): String =
    formatZhihuDailyDateToString(date)

private fun formatZhihuDailyDateToString(date: Long): String {
    val d = Date(date)
    val format = SimpleDateFormat("yyyyMMdd")
    return format.format(d)
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