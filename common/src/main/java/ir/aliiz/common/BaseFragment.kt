package ir.aliiz.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ir.aliiz.navigation.model.Back
import ir.aliiz.navigation.model.To

abstract class BaseFragment : Fragment() {
    abstract fun getViewModel(): ViewModelBase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewModel().navigation.observe(this, Observer {
            it.get()?.also {
                when (it) {
                    is To -> findNavController().navigate(it.direction, it.options)
                    is Back -> findNavController().popBackStack()
                }
            }
        })
    }
}