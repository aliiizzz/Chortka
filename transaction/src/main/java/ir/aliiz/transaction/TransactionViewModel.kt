package ir.aliiz.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.aliiz.domain.model.TransactionInfoDomain
import ir.aliiz.common.AppDispatchers
import ir.aliiz.common.ViewModelBase
import ir.aliiz.domain.TransactionRepo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionViewModel @Inject constructor(private val transactionRepo: TransactionRepo,
                                               private val appDispatchers: AppDispatchers
): ViewModelBase() {

    private var hashtags: LiveData<List<TransactionInfoDomain>> = MutableLiveData()
    private val _updateItems: MediatorLiveData<List<TransactionInfoDomain>> = MediatorLiveData()
    val updateItems: LiveData<List<TransactionInfoDomain>> = _updateItems

    fun getTransactions() {
        viewModelScope.launch {
            _updateItems.removeSource(hashtags)
            withContext(appDispatchers.io) {
                hashtags = transactionRepo.getTransactions()
            }
            _updateItems.addSource(hashtags) {
                _updateItems.value = it
            }
        }
    }

}