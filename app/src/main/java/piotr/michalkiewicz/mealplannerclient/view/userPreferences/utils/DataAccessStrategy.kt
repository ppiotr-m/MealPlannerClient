package piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

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
        if(dbResponse != null) {
            emit(Resource.success(dbResponse))
        } else {
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
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
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
