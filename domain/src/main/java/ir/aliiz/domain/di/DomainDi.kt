package ir.aliiz.domain.di

import dagger.Component
import ir.aliiz.di.AppComponent

@Component(dependencies = [AppComponent::class])
interface DomainComponent