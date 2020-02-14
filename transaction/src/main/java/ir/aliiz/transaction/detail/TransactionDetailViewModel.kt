package ir.aliiz.transaction.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.aliiz.common.AppDispatchers
import ir.aliiz.common.ViewModelBase
import ir.aliiz.domain.model.HashtagInfoDomain
import ir.aliiz.domain.model.Resource
import ir.aliiz.domain.model.TransactionInfoDomain
import ir.aliiz.domain.usecase.AddHashtagUseCase
import ir.aliiz.domain.usecase.RemoveHashtagUseCase
import ir.aliiz.domain.usecase.TransactionDetailUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionDetailViewModel @Inject constructor(
    private val transactionDetailUseCase: TransactionDetailUseCase,
    private val appDispatchers: AppDispatchers,
    private val removeHashtagUseCase: RemoveHashtagUseCase,
    private val addHashtagUseCase: AddHashtagUseCase
) : ViewModelBase() {
    private var addResult: LiveData<Resource<Unit>> = MutableLiveData()
    private var remove: LiveData<Resource<Unit>> = MutableLiveData()
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

    fun removeHashtag(id: String) {
        viewModelScope.launch {
            val transaction = _detail.value
            _detail.removeSource(remove)
            withContext(appDispatchers.io) {
                remove = removeHashtagUseCase.executeSuspend(HashtagInfoDomain(transaction!!.id, id))
            }

            _detail.addSource(remove) {
                if (it.status == Resource.Status.SUCCESS) {
                    _detail.value = _detail.value!!.let {
                        it.copy(hashtags = it.hashtags.toMutableList().apply {
                            remove(id)
                        }.toList())
                    }
                }
            }
        }
    }

    fun addHashtag(id: String, hashtag: String) {
        if (hashtag.isEmpty()) return
        viewModelScope.launch {
            _detail.removeSource(addResult)
            withContext(appDispatchers.io) {
                addResult = addHashtagUseCase.executeSuspend(HashtagInfoDomain(id, hashtag))
            }

            _detail.addSource(addResult) {
                if (it.status == Resource.Status.SUCCESS) {
                    _detail.value = _detail.value!!.let {
                        it.copy(hashtags = it.hashtags.toMutableList().apply {
                            add(hashtag)
                        }.toList())
                    }
                }
            }
        }
    }

}