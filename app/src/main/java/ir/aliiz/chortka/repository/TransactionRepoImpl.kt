package ir.aliiz.chortka.repository

import ir.aliiz.chortka.domain.model.HashtagDomain
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
        transactionDao.addTransaction(TransactionInfo(id, param.amount))
        param.hashtags.forEach {
            transactionDao.addHashtag(Hashtag(it, 0))
            transactionDao.addTransactionHashtag(TransactionHashtag(0, it, id))
        }
    }

    override suspend fun getTransactions(): List<TransactionInfoDomain> = transactionDao.getTransactions().map {
        val hashtags = transactionDao.getTransactionHashtags(it.id)
        TransactionInfoDomain(hashtags, it.amount)
    }

    override suspend fun getHashtags(): List<HashtagDomain> = transactionDao.getHashtags().map {
        HashtagDomain(it.title, it.type)
    }

    override suspend fun updateHashtag(id: HashtagDomain) {
        transactionDao.updateHashtag(id.let { Hashtag(it.title, it.type) })
    }
}