package ir.aliiz.hashtag.di

import androidx.lifecycle.ViewModel
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ir.aliiz.common.ViewModelKey
import ir.aliiz.di.AppComponent
import ir.aliiz.hashtag.ViewModelHashtag
import ir.aliiz.hashtag.add.AddHashtagViewModel
import ir.aliiz.hashtag.result.HashtagResultViewModel

@Component(dependencies = [AppComponent::class], modules = [HashtagModule::class, AddHashtagModule::class])
interface HashtagComponent

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
