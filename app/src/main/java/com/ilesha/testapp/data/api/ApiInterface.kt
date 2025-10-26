package com.ilesha.testapp.data.api

import com.ilesha.testapp.data.dto.IdListDto
import com.ilesha.testapp.data.dto.ItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("v1/entities/getAllIds")
    suspend fun getAllIds(): IdListDto

    @GET("v1/object/{id}")
    suspend fun getItemById(
        @Path("id") id: Int
    ): ItemDto

}