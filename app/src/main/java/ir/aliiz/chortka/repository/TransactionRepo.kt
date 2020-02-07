package ir.aliiz.chortka.repository

import androidx.lifecycle.LiveData
import ir.aliiz.chortka.domain.model.HashtagDomain
import ir.aliiz.chortka.domain.model.Resource
import ir.aliiz.chortka.domain.model.TransactionInfoDomain

interface TransactionRepo {
    suspend fun addTransaction(param: TransactionInfoDomain)
    suspend fun getTransactions(): List<TransactionInfoDomain>
    suspend fun getHashtags(): List<HashtagDomain>
    suspend fun updateHashtag(id: HashtagDomain)
    suspend fun addHashtag(param: HashtagDomain): LiveData<Resource<Unit>>
}