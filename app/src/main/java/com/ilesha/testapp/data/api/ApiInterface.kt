package com.ilesha.testapp.data.api

import com.ilesha.testapp.data.dto.IdListDto
import com.ilesha.testapp.data.dto.ItemDto
import retrofit2.http.GET

interface ApiInterface {

    @GET("v1/entities/getAllIds")
    suspend fun getAllIds(): IdListDto

    @GET("v1/object/{id}")
    suspend fun getItemById(id: Int): ItemDto

}