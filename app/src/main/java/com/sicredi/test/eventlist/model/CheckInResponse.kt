package com.sicredi.test.eventlist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckInResponse(
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?
): Parcelable
