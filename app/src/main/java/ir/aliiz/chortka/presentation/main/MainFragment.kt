package ir.aliiz.chortka.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import ir.aliiz.chortka.R
import ir.aliiz.chortka.di.AppModuleComponent
import ir.aliiz.chortka.presentation.App
import ir.aliiz.common.BaseFragment
import ir.aliiz.common.MainInnerNavigation
import ir.aliiz.common.ViewModelBase
import ir.aliiz.hashtag.FragmentHashtag
import ir.aliiz.hashtag.result.HashtagResultFragment
import ir.aliiz.transaction.TransactionsFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment: BaseFragment(), MainInnerNavigation {
    @Inject lateinit var factory: ir.aliiz.common.ViewModelFactory
    private val viewmodel: ViewModelMain by viewModels { factory }

    override fun getViewModel(): ViewModelBase = viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppModuleComponent.create(activity!!).inject(this)
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
                R.id.action_hashtag_amount -> fragmentManager!!.beginTransaction().replace(
                    R.id.inner_holder,
                    HashtagResultFragment().apply {
                        setTargetFragment(this@MainFragment, 123)
                    }, "hashtagAmount").commit().let { true }
                else -> false
            }
        }
        bottom_navigation_main.selectedItemId = R.id.action_transaction

    }

    override fun navigate(directions: NavDirections) {
        findNavController().navigate(directions)
    }
}