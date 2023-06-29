package com.sicredi.test.eventlist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.text.DecimalFormat
import java.util.*

@Parcelize
data class Event(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("date")
    val date: Long,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("people")
    val people: List<People>
) : Parcelable {
    fun formattedDate(format: String = "dd/MM/yyyy HH:mm"): String {
        val formatter = DateTimeFormatter.ofPattern(format)
        val instant = Instant.ofEpochMilli(date)
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).format(formatter)
    }

    fun formattedPrice(): String {
        return "R$ ${DecimalFormat("#,###.00").format(price)}"
    }
}

