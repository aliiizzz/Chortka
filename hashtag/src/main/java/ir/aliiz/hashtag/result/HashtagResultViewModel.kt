package ir.aliiz.hashtag.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.aliiz.domain.model.HashtagWithAmountDomain
import ir.aliiz.domain.model.Resource
import ir.aliiz.common.AppDispatchers
import ir.aliiz.domain.TransactionRepo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HashtagResultViewModel @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val transactionRepo: TransactionRepo
): ir.aliiz.common.ViewModelBase() {

    private var result: LiveData<Resource<List<HashtagWithAmountDomain>>> = MutableLiveData()
    private val _items: MediatorLiveData<List<HashtagWithAmountDomain>> = MediatorLiveData()
    val items: LiveData<List<HashtagWithAmountDomain>> = _items

    fun getResult() {
        viewModelScope.launch {
            _items.removeSource(result)
            withContext(appDispatchers.io) {
                result = transactionRepo.getHashtagWithAmount()
            }
            _items.addSource(result) {
                if (it.status == Resource.Status.SUCCESS) {
                    _items.value = it.data
                }
            }
        }
    }
}