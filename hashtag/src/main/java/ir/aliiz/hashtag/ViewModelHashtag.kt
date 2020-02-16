package ir.aliiz.hashtag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import ir.aliiz.chortka.presentation.main.MainFragmentDirections
import ir.aliiz.domain.model.HashtagDomain
import ir.aliiz.common.Switch
import ir.aliiz.common.ViewModelBase
import ir.aliiz.domain.TransactionRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ViewModelHashtag @Inject constructor(private val transactionRepo: TransactionRepo): ViewModelBase() {

    private val items: MediatorLiveData<List<HashtagDomain>> = MediatorLiveData()
    private val _loadItems: MutableLiveData<Switch<List<HashtagDomain>>> = MutableLiveData()
    val loadItems: LiveData<Switch<List<HashtagDomain>>> = _loadItems

    private val _innerNav : MutableLiveData<Switch<NavDirections>> = MutableLiveData()
    val innerNav : LiveData<Switch<NavDirections>> = _innerNav

    fun onResume() {
        CoroutineScope(Dispatchers.Main).launch {
            var hashtags = listOf<HashtagDomain>()
            withContext(Dispatchers.IO) {
                hashtags = transactionRepo.getHashtags()
            }
            items.value = hashtags
            _loadItems.value = Switch.nullSwitch(hashtags)
        }
    }

    fun updateHashtag(title: String, type: Int) {
        if (type == 3) {
            _innerNav.value = Switch(MainFragmentDirections.actionMainToHashtagResult(title), null)
            return
        }
        CoroutineScope(Dispatchers.Main).launch {

            items.value?.first { it.title == title }?.copy(type = type)?.also { item ->
                withContext(Dispatchers.IO) {
                    transactionRepo.updateHashtag(item)
                }
                val tempItems = items.value?.toMutableList()?.apply {
                    this[this.indexOfFirst { it.title == title }] = item
                }?.toList()
                tempItems?.also {
                    items.value = it
                    _loadItems.value = Switch.nullSwitch(it)
                }
            }
        }
    }

    fun addClicked() {
        _innerNav.value = Switch.nullSwitch(MainFragmentDirections.actionMainToAddHashtag())
    }


}