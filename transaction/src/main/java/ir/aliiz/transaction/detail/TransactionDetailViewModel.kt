package ir.aliiz.transaction.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import ir.aliiz.common.AppDispatchers
import ir.aliiz.common.ViewModelBase
import ir.aliiz.domain.model.Resource
import ir.aliiz.domain.model.TransactionInfoDomain
import ir.aliiz.domain.usecase.TransactionDetailUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionDetailViewModel @Inject constructor(
    private val transactionDetailUseCase: TransactionDetailUseCase,
    private val appDispatchers: AppDispatchers
) : ViewModelBase() {
    private lateinit var result: LiveData<Resource<TransactionInfoDomain>>

    private val _detail: MediatorLiveData<TransactionInfoDomain> = MediatorLiveData()
    val detail: LiveData<TransactionInfoDomain> = _detail

    fun getDetail(id: String) {
        viewModelScope.launch {
            withContext(appDispatchers.io) {
                result = transactionDetailUseCase.executeSuspend(id)
            }
            _detail.addSource(result) {
                if (it.status == Resource.Status.SUCCESS) {
                    _detail.value = it.data
                }
            }
        }

    }

}