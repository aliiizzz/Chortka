package ir.aliiz.domain.usecase

import androidx.lifecycle.LiveData
import ir.aliiz.domain.model.HashtagDomain
import ir.aliiz.domain.model.Resource
import ir.aliiz.domain.TransactionRepo
import javax.inject.Inject

class UseCaseAddHashtag @Inject constructor(private val transactionRepo: TransactionRepo): UseCaseBase<HashtagDomain, Unit>() {
    override suspend fun executeSuspend(param: HashtagDomain): LiveData<Resource<Unit>> = transactionRepo.addHashtag(param)
}