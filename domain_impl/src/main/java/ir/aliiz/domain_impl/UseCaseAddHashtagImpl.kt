package ir.aliiz.domain_impl

import androidx.lifecycle.LiveData
import ir.aliiz.domain.model.HashtagDomain
import ir.aliiz.domain.model.Resource
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.domain.usecase.UseCaseAddHashtag
import javax.inject.Inject

class UseCaseAddHashtagImpl @Inject constructor(private val transactionRepo: TransactionRepo): UseCaseAddHashtag() {
    override suspend fun executeSuspend(param: HashtagDomain): LiveData<Resource<Unit>> = transactionRepo.addHashtag(param)
}