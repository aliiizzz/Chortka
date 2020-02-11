package ir.aliiz.chortka.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.aliiz.chortka.domain.model.ErrorInfoDomain
import ir.aliiz.chortka.domain.model.HashtagDomain
import ir.aliiz.chortka.domain.model.Resource
import ir.aliiz.chortka.domain.model.TransactionInfoDomain
import ir.aliiz.chortka.local.TransactionDao
import ir.aliiz.chortka.local.model.Hashtag
import ir.aliiz.chortka.local.model.TransactionHashtag
import ir.aliiz.chortka.local.model.TransactionInfo
import java.util.*
import javax.inject.Inject

class TransactionRepoImpl @Inject constructor(private val transactionDao: TransactionDao): TransactionRepo {
    override suspend fun addTransaction(param: TransactionInfoDomain) {
        val id = UUID.randomUUID().toString()
        transactionDao.addTransaction(TransactionInfo(id, param.amount, System.currentTimeMillis()))
        param.hashtags.forEach {
            transactionDao.addHashtag(Hashtag(it, 0, null))
            transactionDao.addTransactionHashtag(TransactionHashtag(0, it, id))
        }
    }

    override suspend fun getTransactions(): LiveData<List<TransactionInfoDomain>> {
        val result = MutableLiveData<List<TransactionInfoDomain>>()
        val res = transactionDao.getTransactions().map {
            val hashtags = transactionDao.getTransactionHashtags(it.id)
            TransactionInfoDomain(it.id, hashtags, it.amount)
        }
        result.postValue(res)
        return result
    }
    override suspend fun getHashtags(): List<HashtagDomain> = transactionDao.getHashtags().map {
        HashtagDomain(it.title, it.type, null)
    }

    override suspend fun updateHashtag(id: HashtagDomain) {
        transactionDao.updateHashtag(id.let { Hashtag(it.title, it.type, null) })
    }

    override suspend fun addHashtag(param: HashtagDomain): LiveData<Resource<Unit>> {
        val result = MutableLiveData<Resource<Unit>>()
        try {
            transactionDao.addHashtag(param.let { Hashtag(it.title, it.type, it.formula) })
            result.postValue(Resource.success(Unit))
        } catch (e: Exception) {
            result.postValue(Resource.error(ErrorInfoDomain(e)))
        }
        return result
    }
}