package ir.aliiz.hashtag.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ir.aliiz.common.CommonModule
import ir.aliiz.common.ViewModelBuilder
import ir.aliiz.common.ViewModelKey
import ir.aliiz.di.AppComponent
import ir.aliiz.domain_impl.di.UseCaseModule
import ir.aliiz.hashtag.FragmentHashtag
import ir.aliiz.hashtag.ViewModelHashtag
import ir.aliiz.hashtag.add.AddHashtagViewModel
import ir.aliiz.hashtag.result.HashtagResultFragment
import ir.aliiz.hashtag.result.HashtagResultViewModel
import ir.aliiz.local.di.LocalModule
import ir.aliiz.repository.di.RepoModule

@Component(dependencies = [AppComponent::class], modules = [HashtagModule::class,
    AddHashtagModule::class, RepoModule::class, LocalModule::class,
    ViewModelBuilder::class, CommonModule::class, UseCaseModule::class])
interface HashtagComponent {
    fun inject(context: FragmentHashtag)
    fun inject(context: HashtagResultFragment)
    companion object {
        fun create(activity: Activity): HashtagComponent = DaggerHashtagComponent.builder().appComponent(
            AppComponent.getAppComponent(activity)).build()
    }
}

@Module
class HashtagModule {

    @Provides
    @IntoMap
    @ViewModelKey(ViewModelHashtag::class)
    fun provideHashtagViewmodel(vm: ViewModelHashtag): ViewModel = vm

    @Provides
    @IntoMap
    @ViewModelKey(HashtagResultViewModel::class)
    fun provideHashtagResultViewmodel(vm: HashtagResultViewModel): ViewModel = vm
}

@Module
class AddHashtagModule {
    @Provides
    @IntoMap
    @ViewModelKey(AddHashtagViewModel::class)
    fun provideViewMoel(vm: AddHashtagViewModel): ViewModel = vm
}
