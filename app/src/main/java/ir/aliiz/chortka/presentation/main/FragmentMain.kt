package ir.aliiz.chortka.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import ir.aliiz.chortka.R
import ir.aliiz.chortka.presentation.App
import ir.aliiz.chortka.presentation.transaction.TransactionsFragment
import ir.aliiz.chortka.presentation.ViewModelBase
import ir.aliiz.chortka.presentation.ViewModelFactory
import ir.aliiz.chortka.presentation.hashtag.FragmentBase
import ir.aliiz.chortka.presentation.hashtag.FragmentHashtag
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class FragmentMain: FragmentBase(), MainInnerNavigation {
    @Inject lateinit var factory: ViewModelFactory
    private val viewmodel: ViewModelBase by viewModels { factory }

    override fun getViewModel(): ViewModelBase = viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (context!!.applicationContext as App).component.apply {
            inject(this@FragmentMain)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottom_navigation_main.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_hashtag -> fragmentManager!!.beginTransaction().replace(
                    R.id.inner_holder,
                    FragmentHashtag(), "hashtag").commit().let { true }
                R.id.action_transaction -> fragmentManager!!.beginTransaction().replace(
                    R.id.inner_holder,
                    TransactionsFragment(), "transactions").commit().let { true }
                else -> false
            }
        }
        bottom_navigation_main.selectedItemId = R.id.action_transaction

    }

    override fun navigate(directions: NavDirections) {
        findNavController().navigate(directions)
    }
}

interface MainInnerNavigation {
    fun navigate(directions: NavDirections)
}