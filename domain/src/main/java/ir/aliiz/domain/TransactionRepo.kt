package ir.aliiz.domain

import androidx.lifecycle.LiveData
import ir.aliiz.domain.model.HashtagDomain
import ir.aliiz.domain.model.HashtagWithAmountDomain
import ir.aliiz.domain.model.Resource
import ir.aliiz.domain.model.TransactionInfoDomain

interface TransactionRepo {
    suspend fun addTransaction(param: TransactionInfoDomain)
    suspend fun getTransactions(): LiveData<List<TransactionInfoDomain>>
    suspend fun getHashtags(): List<HashtagDomain>
    suspend fun updateHashtag(id: HashtagDomain)
    suspend fun addHashtag(param: HashtagDomain): LiveData<Resource<Unit>>
    suspend fun getHashtagWithAmount(): LiveData<Resource<List<HashtagWithAmountDomain>>>
    suspend fun getHashtagTransactions(hashtag: String): LiveData<Resource<List<TransactionInfoDomain>>>
}