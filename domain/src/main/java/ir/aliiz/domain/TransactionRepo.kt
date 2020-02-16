package ir.aliiz.domain

import androidx.lifecycle.LiveData
import ir.aliiz.domain.model.*

interface TransactionRepo {
    suspend fun addTransaction(param: TransactionInfoDomain)
    suspend fun getTransactions(): LiveData<List<TransactionInfoDomain>>
    suspend fun getHashtags(): List<HashtagDomain>
    suspend fun updateHashtag(id: HashtagDomain)
    suspend fun addHashtag(param: HashtagDomain): LiveData<Resource<Unit>>
    suspend fun getHashtagWithAmount(id: String): LiveData<Resource<HashtagWithAmountDomain>>
    suspend fun getHashtagTransactions(hashtag: String): LiveData<Resource<List<TransactionInfoDomain>>>
    suspend fun getTransaction(param: String): LiveData<Resource<TransactionInfoDomain>>
    suspend fun removeHashtag(param: HashtagInfoDomain): LiveData<Resource<Unit>>
    suspend fun addTransactionHashtag(param: HashtagInfoDomain): LiveData<Resource<Unit>>
    suspend fun getHashtagRelations(param: String): LiveData<Resource<List<String>>>
}