package com.example.android.releasedatekt.database

import androidx.room.TypeConverter
import java.util.*

object Converters {

    @TypeConverter
    fun intListToString(integers: List<Int>?): String {
        var result = ""
        integers?.let {
            integers.forEachIndexed { index, element ->
                result += element.toString()
                if (index != integers.lastIndex) {
                    result += ","
                }
            }
        }
        return result
    }

    @TypeConverter
    fun stringToIntList(string: String?): List<Int> {
        var result = mutableListOf<Int>()
        string?.let {
            val array = string.split(",")
            for (s in array) {
                result.add(s.toInt())
            }
        }
        return result
    }

    @TypeConverter
    fun dateToLong(date: Date?): Long {

        return date?.time ?: 0

    }

    @TypeConverter
    fun longToDate(long: Long): Date {
        return Date(long)
    }
}