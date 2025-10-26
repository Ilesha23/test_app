package com.ilesha.testapp.domain.usecase

import com.ilesha.testapp.domain.model.Entity
import com.ilesha.testapp.domain.model.Result
import com.ilesha.testapp.domain.repository.Repository

class GetAllIdsUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(): Result<List<Entity>> {
        return repository.getAllIds()
    }

}