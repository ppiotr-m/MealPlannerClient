package piotr.michalkiewicz.mealplannerclient.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.view.MainActivity

//  TODO Make it generic
fun performGetOperation(
    networkCall: suspend () -> Resource<NutritionUiModel>?, //  TODO Remove nullables, this and the rest
): MutableLiveData<Resource<NutritionUiModel>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val responseStatus = networkCall.invoke()
        if (responseStatus!!.status == Resource.Status.SUCCESS) {
            emit(responseStatus)
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(MainActivity.getMainContext().resources.getString(R.string.nutrition_fetch_error)))
        }
    } as MutableLiveData<Resource<NutritionUiModel>>