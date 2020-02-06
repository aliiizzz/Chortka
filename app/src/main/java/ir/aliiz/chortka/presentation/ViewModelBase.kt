package ir.aliiz.chortka.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections

abstract class ViewModelBase : ViewModel() {
    private val _navigation: MutableLiveData<Switch<NavDirections>> = MutableLiveData()
    val navigation = _navigation

    fun navigate(directions: NavDirections) {
        _navigation.value = Switch.nullSwitch(directions)
    }
}