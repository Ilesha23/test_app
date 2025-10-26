package com.ilesha.testapp.data.dto

import com.google.gson.annotations.SerializedName

data class ItemDto(
    @SerializedName("type")
    val type: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("url")
    val url: String?
)
