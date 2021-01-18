package piotr.michalkiewicz.mealplannerclient.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionLiveData
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import retrofit2.Response

fun performGetOperation(
    networkCall: suspend () -> Response<NutritionUiModel>
): LiveData<NutritionUiModel> =
    liveData(Dispatchers.IO) {
        val responseStatus = networkCall.invoke()
        Log.i(TAG, "DataAccessStrategy::Response status data null?: " + (responseStatus.body()==null))
        if (responseStatus.isSuccessful) {
            Log.i(TAG, "DataAccessStrategy::Success")
            responseStatus.body()!!.nutrientsPercentages.forEach{(p1, p2) ->
                Log.i(TAG, "Key: " + p1 + " Value: " + p2)
            }
            emit(responseStatus.body()!!)
        } else if (!responseStatus.isSuccessful) {
            Log.i(TAG, "DataAccessStrategy::No success")
        }
    }