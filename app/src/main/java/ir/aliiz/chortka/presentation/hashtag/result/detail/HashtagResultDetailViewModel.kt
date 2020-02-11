package ir.aliiz.chortka.presentation.hashtag.result.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.aliiz.chortka.domain.model.Resource
import ir.aliiz.chortka.domain.model.TransactionInfoDomain
import ir.aliiz.chortka.presentation.AppDispatchers
import ir.aliiz.chortka.presentation.ViewModelBase
import ir.aliiz.chortka.repository.TransactionRepo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HashtagResultDetailViewModel @Inject constructor(
    private val transactionRepo: TransactionRepo,
    private val appDispatchers: AppDispatchers
) : ViewModelBase() {

    private var trs: LiveData<Resource<List<TransactionInfoDomain>>> = MutableLiveData()
    private val _result: MediatorLiveData<List<TransactionInfoDomain>> = MediatorLiveData()
    val result: LiveData<List<TransactionInfoDomain>> = _result

    fun getHashtagTransactions(hashtag: String) {
        viewModelScope.launch {
            _result.removeSource(trs)
            withContext(appDispatchers.io) {
                trs = transactionRepo.getHashtagTransactions(hashtag)
            }
            _result.addSource(trs) {
                if (it.status == Resource.Status.SUCCESS) {
                    _result.value = it.data
                }
            }
        }
    }

}