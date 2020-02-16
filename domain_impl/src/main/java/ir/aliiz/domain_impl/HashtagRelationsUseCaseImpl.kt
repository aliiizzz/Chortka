package ir.aliiz.domain_impl

import androidx.lifecycle.LiveData
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.domain.model.Resource
import ir.aliiz.domain.usecase.HashtagRelationsUseCase
import javax.inject.Inject

class HashtagRelationsUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
): HashtagRelationsUseCase() {
    override suspend fun executeSuspend(param: String): LiveData<Resource<List<String>>> =
        transactionRepo.getHashtagRelations(param)
}