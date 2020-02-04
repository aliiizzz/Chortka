package ir.aliiz.chortka.domain.usecase

import androidx.lifecycle.LiveData
import ir.aliiz.chortka.domain.model.Resource

abstract class UseCaseBase<T, S> {
    abstract suspend fun executeSuspend(param: T): LiveData<Resource<S>>
}