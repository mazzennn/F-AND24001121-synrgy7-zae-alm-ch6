package com.example.chapter_5.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponseGeneral(
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("status_message")
    val statusMessage: String?,
    @SerializedName("success")
    val success: Boolean?
)

