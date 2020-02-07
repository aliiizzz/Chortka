package ir.aliiz.chortka.navigation.model

import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

sealed class NavigationDirection

class Back: NavigationDirection()
data class To(val direction: NavDirections, val options: NavOptions? = null): NavigationDirection()