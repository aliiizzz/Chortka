package ir.aliiz.domain_impl

import androidx.lifecycle.LiveData
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.domain.model.HashtagInfoDomain
import ir.aliiz.domain.model.Resource
import ir.aliiz.domain.usecase.RemoveHashtagUseCase
import javax.inject.Inject

class RemoveHashtagUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
): RemoveHashtagUseCase() {
    override suspend fun executeSuspend(param: HashtagInfoDomain): LiveData<Resource<Unit>> = transactionRepo.removeHashtag(param)
}