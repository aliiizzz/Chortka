package ir.aliiz.domain_impl

import androidx.lifecycle.LiveData
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.domain.model.HashtagInfoDomain
import ir.aliiz.domain.model.Resource
import ir.aliiz.domain.usecase.AddHashtagUseCase
import javax.inject.Inject

class AddHashtagUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
): AddHashtagUseCase() {
    override suspend fun executeSuspend(param: HashtagInfoDomain): LiveData<Resource<Unit>> = transactionRepo.addTransactionHashtag(param)
}