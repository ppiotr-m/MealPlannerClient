package piotr.michalkiewicz.mealplannerclient.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.view.MainActivity

//  TODO Make it generic
fun performGetOperation(
    localStorageQuery: () -> NutritionUiModel?,
    networkCall: suspend () -> Resource<NutritionUiModel>?, //  TODO Remove nullables, this and the rest
    saveResults: (NutritionUiModel) -> Unit,
): LiveData<Resource<NutritionUiModel>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = localStorageQuery.invoke()
        if (source != null) {
            emit(Resource.success(source))
        }
        val responseStatus = networkCall.invoke()
        if (responseStatus!!.status == Resource.Status.SUCCESS) {
            saveResults(responseStatus.data!!)    //  TODO Take or of "!!"
            emit(responseStatus)
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(MainActivity.getMainContext().resources.getString(R.string.nutrition_fetch_error)))
        }
    }