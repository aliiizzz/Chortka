package ir.aliiz.domain_impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.domain.model.ErrorInfoDomain
import ir.aliiz.domain.model.HashtagDomain
import ir.aliiz.domain.model.Resource
import ir.aliiz.domain.usecase.UseCaseHashtags
import javax.inject.Inject

class UseCaseHashtagsImpl @Inject constructor(private val transactionRepo: TransactionRepo): UseCaseHashtags() {

    override suspend fun executeSuspend(param: Unit): LiveData<Resource<List<HashtagDomain>>> =
        MutableLiveData<Resource<List<HashtagDomain>>>().apply {
            postValue(Resource.loading())
            try {
                val result = transactionRepo.getHashtags()
                postValue(Resource.success(result))
            } catch (e: Exception) {
                e.printStackTrace()
                postValue(Resource.error(ErrorInfoDomain(e)))
            }
        }
}