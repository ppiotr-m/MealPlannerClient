package piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import piotr.michalkiewicz.mealplannerclient.utils.EspressoIdlingResource

fun <A> performGetOperationNoDB(
    networkCall: suspend () -> Resource<A>
): LiveData<Resource<A?>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            emit(Resource.success(responseStatus.data))
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message ?: return@liveData))
        }
    }

fun <T> performGetOperation(
    databaseQuery: () -> T?,
    networkCall: suspend () -> Resource<T>,
    saveCallResult: suspend (T?) -> Unit
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        val dbResponse = databaseQuery.invoke()
        if (dbResponse != null) {
            emit(Resource.success(dbResponse))
        } else if (dbResponse == null) {
            emit(Resource.loading())
        }

        val networkResponse = networkCall.invoke()
        if (networkResponse.status == Resource.Status.SUCCESS) {
            saveCallResult(networkResponse.data ?: return@liveData)
            emit(networkResponse)
        } else if (networkResponse.status == Resource.Status.ERROR) {
            emit(Resource.error(networkResponse.message ?: return@liveData))
        }
    }

fun <T> performSetOperation(
    data: T,
    databaseQuery: () -> T,
    networkCall: suspend () -> Resource<T>,
    saveCallResult: suspend (T) -> Unit
): LiveData<Resource<T>> = liveData(Dispatchers.IO) {
    if (data != null) {
        emit(Resource.success(data))
    }

    val networkResponse = networkCall.invoke()
    if (networkResponse.status == Resource.Status.SUCCESS) {
        saveCallResult(networkResponse.data ?: return@liveData)
        emit(networkResponse)
    } else if (networkResponse.status == Resource.Status.ERROR) {
        emit(Resource.error(networkResponse.message ?: return@liveData))
        val dbResponse = databaseQuery.invoke()
        emit(Resource.success(dbResponse))
    }
}
