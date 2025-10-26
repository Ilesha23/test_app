package com.ilesha.testapp.domain.usecase

import com.ilesha.testapp.domain.model.Entity
import com.ilesha.testapp.domain.model.Result
import com.ilesha.testapp.domain.repository.Repository
import javax.inject.Inject

class GetItemByIdUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(id: Int): Result<Entity> {
        return repository.getItemById(id)
    }

}