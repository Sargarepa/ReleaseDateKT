package com.example.android.releasedatekt.network

import com.squareup.moshi.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter : JsonAdapter<Date>() {

    private val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.US)

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            dateFormat.parse(dateAsString)
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if(value != null) {
            writer.value(value.toString())
        }
    }

    companion object {
        const val SERVER_FORMAT = "yyyy-MM-dd"
    }
}