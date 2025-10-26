package com.ilesha.testapp.data.dto

import com.google.gson.annotations.SerializedName

data class IdListDto(
    @SerializedName("data")
    val data: List<IdItemDto>?
)

data class IdItemDto(
    @SerializedName("id")
    val id: Int?
)