package piotr.michalkiewicz.mealplannerclient.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetOperation(
    getFromLocalStorage: () -> LiveData<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = getFromLocalStorage.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            saveCallResult(responseStatus.data ?: return@liveData)
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }