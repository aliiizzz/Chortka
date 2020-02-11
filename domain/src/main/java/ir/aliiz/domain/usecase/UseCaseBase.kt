package ir.aliiz.domain.usecase

import androidx.lifecycle.LiveData
import ir.aliiz.domain.model.Resource

abstract class UseCaseBase<T, S> {
    abstract suspend fun executeSuspend(param: T): LiveData<Resource<S>>
}