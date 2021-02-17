package piotr.michalkiewicz.mealplannerclient.view.userPreferences.data.remote

import android.util.Log
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Resource
import retrofit2.Response

interface BaseDataSource {
    suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun <T> setData(data: T, call: suspend (T) -> Response<T>?): Resource<T> {
        try {
            val response = call(data)
            if (response?.isSuccessful == true) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response?.code()} ${response?.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.e("remoteDataSource", message)
        return Resource.error("Network call has failed for a following reason: $message")
    }
}