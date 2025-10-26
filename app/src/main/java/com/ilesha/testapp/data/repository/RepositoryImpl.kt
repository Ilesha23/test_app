package com.ilesha.testapp.data.repository

import com.ilesha.testapp.data.api.ApiInterface
import com.ilesha.testapp.data.mapper.toDomainModel
import com.ilesha.testapp.domain.model.Entity
import com.ilesha.testapp.domain.model.Result
import com.ilesha.testapp.domain.repository.Repository
import okio.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : Repository {

    override suspend fun getAllIds(): Result<List<Int>> {
        return try {
            val idDto = api.getAllIds()
            val idList = idDto.data?.let { dtoList ->
                dtoList.mapNotNull { dtoItem ->
                    dtoItem.id
                }
            } ?: emptyList()
            Result.Success(idList)
        } catch (e: IOException) {
            Result.Error(e)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getItemById(id: Int): Result<Entity> {
        return try {
            val item = api.getItemById(id).toDomainModel()
            Result.Success(item)
        } catch (e: IOException) {
            Result.Error(e)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}