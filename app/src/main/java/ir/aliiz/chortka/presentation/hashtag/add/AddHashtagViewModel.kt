package ir.aliiz.chortka.presentation.hashtag.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import ir.aliiz.chortka.domain.model.HashtagDomain
import ir.aliiz.chortka.domain.model.Resource
import ir.aliiz.chortka.domain.usecase.UseCaseHashtags
import ir.aliiz.chortka.presentation.AppDispatchers
import ir.aliiz.chortka.presentation.ViewModelBase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddHashtagViewModel @Inject constructor(
    private val useCaseHashtags: UseCaseHashtags,
    private val appDispatchers: AppDispatchers
): ViewModelBase() {

    private lateinit var hashtags: LiveData<Resource<List<HashtagDomain>>>
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


}