package ir.aliiz.hashtag.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.aliiz.domain.model.HashtagWithAmountDomain
import ir.aliiz.domain.model.Resource
import ir.aliiz.common.AppDispatchers
import ir.aliiz.common.ViewModelBase
import ir.aliiz.domain.TransactionRepo
import ir.aliiz.domain.usecase.HashtagRelationsUseCase
import ir.aliiz.domain.usecase.HashtagSumUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HashtagResultViewModel @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val hashtagRelationsUseCase: HashtagRelationsUseCase,
    private val hashtagSumUseCase: HashtagSumUseCase
): ViewModelBase() {

    private var relations: LiveData<Resource<List<String>>> = MutableLiveData()
    private val _items: MediatorLiveData<List<HashtagWithAmountDomain>> = MediatorLiveData()
    val items: LiveData<List<HashtagWithAmountDomain>> = _items
    private val _sum: MediatorLiveData<HashtagWithAmountDomain> = MediatorLiveData()
    val sum: LiveData<HashtagWithAmountDomain> = _sum

    fun getResult(id: String) {
        viewModelScope.launch {
            withContext(appDispatchers.io) {
                val sum = hashtagSumUseCase.executeSuspend(id)
                withContext(appDispatchers.main) {
                    _sum.addSource(sum) {
                        if (it.status == Resource.Status.SUCCESS) {
                            _sum.value = it.data ?: HashtagWithAmountDomain(id, 0, 0)
                        }
                    }
                }
                relations = hashtagRelationsUseCase.executeSuspend(id)
                val test = relations.value
                if (test?.status == Resource.Status.SUCCESS && (test.data?.count() ?: 0) > 0) {
                    test.data?.forEach {
                        val sum = hashtagSumUseCase.executeSuspend(it)
                        _items.addSource(sum) {
                            if (it.status == Resource.Status.SUCCESS) {
                                _sum.value = _sum.value?.let { hash ->
                                    val amount = hash.amount + (it.data?.amount ?: 0)
                                    hash.copy(amount = amount)
                                }
                                _items.value = _items.value?.toMutableList()?.apply {
                                    add(it.data!!)
                                }?.toList()
                            }
                        }
                    }
                }
            }
        }
    }
}