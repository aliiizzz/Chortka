package ir.aliiz.chortka.domain.usecase

import androidx.lifecycle.LiveData
import ir.aliiz.chortka.domain.model.HashtagDomain
import ir.aliiz.chortka.domain.model.Resource
import ir.aliiz.chortka.repository.TransactionRepo
import javax.inject.Inject

class UseCaseAddHashtag @Inject constructor(private val transactionRepo: TransactionRepo): UseCaseBase<HashtagDomain, Unit>() {
    override suspend fun executeSuspend(param: HashtagDomain): LiveData<Resource<Unit>> = transactionRepo.addHashtag(param)
}