package ir.aliiz.chortka.repository

import androidx.lifecycle.LiveData
import ir.aliiz.chortka.domain.model.HashtagDomain
import ir.aliiz.chortka.domain.model.HashtagWithAmountDomain
import ir.aliiz.chortka.domain.model.Resource
import ir.aliiz.chortka.domain.model.TransactionInfoDomain
import ir.aliiz.chortka.local.model.Hashtag

interface TransactionRepo {
    suspend fun addTransaction(param: TransactionInfoDomain)
    suspend fun getTransactions(): LiveData<List<TransactionInfoDomain>>
    suspend fun getHashtags(): List<HashtagDomain>
    suspend fun updateHashtag(id: HashtagDomain)
    suspend fun addHashtag(param: HashtagDomain): LiveData<Resource<Unit>>
    suspend fun getHashtagWithAmount(): LiveData<Resource<List<HashtagWithAmountDomain>>>
    suspend fun getHashtagTransactions(hashtag: String): LiveData<Resource<List<TransactionInfoDomain>>>
}