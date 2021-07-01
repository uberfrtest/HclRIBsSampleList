package com.hcl.uberpoc.data.network.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader

internal class NullToEmptyStringAdapter {

    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}