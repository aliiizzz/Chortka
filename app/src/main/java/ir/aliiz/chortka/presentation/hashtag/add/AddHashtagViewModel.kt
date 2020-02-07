package ir.aliiz.chortka.presentation.hashtag.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.aliiz.chortka.domain.model.HashtagDomain
import ir.aliiz.chortka.domain.model.Resource
import ir.aliiz.chortka.domain.usecase.UseCaseAddHashtag
import ir.aliiz.chortka.domain.usecase.UseCaseHashtags
import ir.aliiz.chortka.navigation.model.Back
import ir.aliiz.chortka.presentation.AppDispatchers
import ir.aliiz.chortka.presentation.Switch
import ir.aliiz.chortka.presentation.ViewModelBase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddHashtagViewModel @Inject constructor(
    private val useCaseHashtags: UseCaseHashtags,
    private val appDispatchers: AppDispatchers,
    private val useCaseAddHashtag: UseCaseAddHashtag
): ViewModelBase() {

    private var addHashtagResult: LiveData<Resource<Unit>> = MutableLiveData()
    private var hashtags: LiveData<Resource<List<HashtagDomain>>> = MutableLiveData()
    private val _hashtag: MutableLiveData<List<String>> = MutableLiveData()
    val hashtag: LiveData<List<String>> = _hashtag
    private val _result: MediatorLiveData<List<HashtagDomain>> = MediatorLiveData()
    val result: LiveData<List<HashtagDomain>> = _result

    fun init() {
        viewModelScope.launch {
            _result.removeSource(hashtags)
            withContext(appDispatchers.io) {
                hashtags = useCaseHashtags.executeSuspend(Unit)
            }
            _result.addSource(hashtags) {
                if (it.status == Resource.Status.SUCCESS) {
                    _result.value = it.data
                }
            }
        }
    }

    fun addHashtag(title: String) {
        val value = (_hashtag.value ?: listOf()).toMutableList()
        if (value.contains(title).not()) {
            value.add(title)
        }
        _hashtag.value = value.toList()
    }

    fun addClicked(hashtag: String) {
        if (hashtag.contains("#") || hashtag.contains(" ")) {
            return
        }

        viewModelScope.launch {
            withContext(appDispatchers.io) {
                val value = (_hashtag.value ?: listOf()).joinToString(",")
                addHashtagResult = useCaseAddHashtag.executeSuspend(HashtagDomain(hashtag, 0, value))
            }
            navigate(Back())
        }
    }


}