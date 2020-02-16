package ir.aliiz.domain_impl

import androidx.lifecycle.LiveData
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.domain.model.HashtagWithAmountDomain
import ir.aliiz.domain.model.Resource
import ir.aliiz.domain.usecase.HashtagSumUseCase
import javax.inject.Inject

class HashtagSumUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
) : HashtagSumUseCase() {
    override suspend fun executeSuspend(param: String): LiveData<Resource<HashtagWithAmountDomain>> =
        transactionRepo.getHashtagWithAmount(param)
}