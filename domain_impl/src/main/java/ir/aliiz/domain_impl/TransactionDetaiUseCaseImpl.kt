package ir.aliiz.domain_impl

import androidx.lifecycle.LiveData
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.domain.model.Resource
import ir.aliiz.domain.model.TransactionInfoDomain
import ir.aliiz.domain.usecase.TransactionDetailUseCase
import javax.inject.Inject

class TransactionDetaiUseCaseImpl @Inject constructor(
    private val transactionRepo: TransactionRepo
): TransactionDetailUseCase() {
    override suspend fun executeSuspend(param: String): LiveData<Resource<TransactionInfoDomain>> = transactionRepo.getTransaction(param)
}