package piotr.michalkiewicz.mealplannerclient.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionLiveData
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG

fun <A, T> performGetOperation(
    getFromLocalStorage: () -> LiveData<A>?,
    networkCall: suspend () -> Resource<T>,
    saveCallResult: suspend (T) -> Unit
): LiveData<Resource<A>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
   //     val source = getFromLocalStorage.invoke()!!.map{ Resource.success(it) }
   //     emitSource(source)
        Log.i("Strategy", "Strategy, after local fetch")
        //  TODO Retrive data from response
        val responseStatus = networkCall.invoke()
        Log.i("Strategy", "Response status data null?: " + (responseStatus.data==null))
        if (responseStatus.status == Resource.Status.SUCCESS) {
         //   Log.i(TAG, "DataAccessStrategy:: percentagesSize: " + (responseStatus.data as NutritionUiModel).nutrientsPercentages.)
             Log.i("Strategy", "Success")
             saveCallResult(responseStatus.data ?: return@liveData)
   //         emitSource(responseStatus.data)
        } else if (responseStatus.status == Resource.Status.ERROR) {
            Log.i("Strategy", "No success")
            emit(Resource.error(responseStatus.message!!))
     //       emitSource(source)
        }
    }