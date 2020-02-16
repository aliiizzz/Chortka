package ir.aliiz.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.domain.model.*
import ir.aliiz.local.TransactionDao
import ir.aliiz.local.model.Hashtag
import ir.aliiz.local.model.HashtagHashtag
import ir.aliiz.local.model.TransactionHashtag
import ir.aliiz.local.model.TransactionInfo
import java.util.*
import javax.inject.Inject

class TransactionRepoImpl @Inject constructor(private val transactionDao: TransactionDao): TransactionRepo {
    override suspend fun addTransaction(param: TransactionInfoDomain) {
        val id = UUID.randomUUID().toString()
        transactionDao.addTransaction(
            TransactionInfo(
                id,
                param.amount,
                System.currentTimeMillis()
            )
        )
        param.hashtags.forEach {
            transactionDao.addHashtag(Hashtag(it, 0))
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
        HashtagDomain(it.title, it.type, listOf())
    }

    override suspend fun updateHashtag(id: HashtagDomain) {
        transactionDao.updateHashtag(id.let {
            Hashtag(
                it.title,
                it.type
            )
        })
    }

    override suspend fun addHashtag(param: HashtagDomain): LiveData<Resource<Unit>> {
        val result = MutableLiveData<Resource<Unit>>()
        try {
            transactionDao.addHashtag(param.let {
                Hashtag(
                    it.title,
                    it.type
                )
            })
            param.childs.forEach {
                transactionDao.addHashtagHashtag(HashtagHashtag(0, it.title, param.title))
            }
            result.postValue(Resource.success(Unit))
        } catch (e: Exception) {
            result.postValue(Resource.error(ErrorInfoDomain(e)))
        }
        return result
    }

    override suspend fun getHashtagWithAmount(id: String): LiveData<Resource<HashtagWithAmountDomain>> {
        val result = MutableLiveData<Resource<HashtagWithAmountDomain>>()
        try {
            val res = transactionDao.getHashtagsWithAmount(id)
            result.postValue(Resource.success<HashtagWithAmountDomain>(
                res.let {
                    HashtagWithAmountDomain(it.title, it.type, it.amount)
                }
            ))
        } catch (e: Exception) {
            e.printStackTrace()
            result.postValue(Resource.error(ErrorInfoDomain(e)))
        }
        return result
    }

    override suspend fun getHashtagTransactions(hashtag: String): LiveData<Resource<List<TransactionInfoDomain>>> {
        val result = MutableLiveData<Resource<List<TransactionInfoDomain>>>()
        try {
            val res = transactionDao.getHashtagTransactions(hashtag)
            result.postValue(Resource.success(res.map {
                TransactionInfoDomain(it.id, listOf(), it.amount)
            }))
        } catch (e: Exception) {
            e.printStackTrace()
            result.postValue(Resource.error(ErrorInfoDomain(e)))
        }
        return result
    }

    override suspend fun getTransaction(param: String): LiveData<Resource<TransactionInfoDomain>> {
        val result = MutableLiveData<Resource<TransactionInfoDomain>>()
        try {
            val tr = transactionDao.getTransaction(param)
            val hashs = transactionDao.getTransactionHashtags(param)
            result.postValue(Resource.success(tr.let {
                TransactionInfoDomain(it.id, hashs, it.amount)
            }))
        } catch (e: Exception) {
            result.postValue(Resource.error(ErrorInfoDomain(e)))
        }
        return result
    }

    override suspend fun removeHashtag(param: HashtagInfoDomain): LiveData<Resource<Unit>> = MutableLiveData<Resource<Unit>>().apply {
        try {
            transactionDao.removeHashtag(param.transactionId, param.hashtag)
            postValue(Resource.success(Unit))
        } catch (e: Exception) {
            postValue(Resource.error(ErrorInfoDomain(e)))
        }
    }

    override suspend fun addTransactionHashtag(param: HashtagInfoDomain): LiveData<Resource<Unit>> = MutableLiveData<Resource<Unit>>().apply {
        try {
            transactionDao.addHashtag(Hashtag(param.hashtag, 0))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            transactionDao.addTransactionHashtag(TransactionHashtag(0, param.hashtag, param.transactionId))
            postValue(Resource.success(Unit))
        } catch (e: Exception) {
            postValue(Resource.error(ErrorInfoDomain(e)))
        }
    }

    override suspend fun getHashtagRelations(param: String): LiveData<Resource<List<String>>> =
        MutableLiveData<Resource<List<String>>>().apply {
            try {
                val res = transactionDao.getHashtagRelations(param)
                postValue(Resource.success(res))
            } catch (e: Exception) {
                postValue(Resource.error(ErrorInfoDomain(e)))
            }
        }
}