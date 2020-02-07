package ir.aliiz.chortka.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.aliiz.chortka.navigation.model.NavigationDirection

abstract class ViewModelBase : ViewModel() {
    private val _navigation: MutableLiveData<Switch<NavigationDirection>> = MutableLiveData()
    val navigation = _navigation

    fun navigate(directions: NavigationDirection) {
        _navigation.value = Switch.nullSwitch(directions)
    }
}