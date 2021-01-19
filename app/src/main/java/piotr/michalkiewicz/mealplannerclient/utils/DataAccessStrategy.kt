package piotr.michalkiewicz.mealplannerclient.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import piotr.michalkiewicz.mealplannerclient.view.MainActivity

//  Local storage query zwraca
fun performGetOperation(
    localStorageQuery: () -> NutritionUiModel?,
    networkCall: suspend () -> Resource<NutritionUiModel>?, //  TODO Remove nullables, this and the rest
    saveResults: (NutritionUiModel) -> Unit,
): LiveData<Resource<NutritionUiModel>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = localStorageQuery.invoke()
        Log.d(TAG, "DataAccessStrategy:: source null: " + (source == null))
        if (source != null) {
            emit(Resource.success(source))
        }
        val responseStatus = networkCall.invoke()
        Log.i(TAG, "DataAccessStrategy::Response status data null?: " + (responseStatus == null))
        if (responseStatus!!.status == Resource.Status.SUCCESS) {
            Log.i(TAG, "DataAccessStrategy::Success")
            saveResults(responseStatus.data!!)    //  TODO Take or of "!!"
            emit(responseStatus)
        } else if (responseStatus.status == Resource.Status.ERROR) {
            Log.i(TAG, "DataAccessStrategy::No success")
            emit(Resource.error(MainActivity.getMainContext().resources.getString(R.string.nutrition_fetch_error)))
        }
    }