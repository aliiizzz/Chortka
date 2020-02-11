package ir.aliiz.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.aliiz.domain.model.ErrorInfoDomain
import ir.aliiz.domain.model.HashtagDomain
import ir.aliiz.domain.model.Resource
import ir.aliiz.chortka.repository.TransactionRepo
import javax.inject.Inject

class UseCaseHashtags @Inject constructor(private val transactionRepo: TransactionRepo): UseCaseBase<Unit, List<HashtagDomain>>() {

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