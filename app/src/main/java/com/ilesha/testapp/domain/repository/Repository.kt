package com.ilesha.testapp.domain.repository

import com.ilesha.testapp.domain.model.Entity
import com.ilesha.testapp.domain.model.Result

interface Repository {

    suspend fun getAllIds(): Result<List<Int>>

    suspend fun getItemById(id: Int): Result<Entity>

}