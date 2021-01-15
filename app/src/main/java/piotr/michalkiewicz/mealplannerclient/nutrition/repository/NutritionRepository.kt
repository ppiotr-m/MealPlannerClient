package piotr.michalkiewicz.mealplannerclient.nutrition.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import piotr.michalkiewicz.mealplannerclient.nutrition.NutritionServiceGenerator
import piotr.michalkiewicz.mealplannerclient.nutrition.local.NutritionSharedPrefsAccess
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.NutritionRemoteDataSource
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import piotr.michalkiewicz.mealplannerclient.utils.Resource
import piotr.michalkiewicz.mealplannerclient.utils.performGetOperation

//  TODO Move properties to constructor and add HILT
class NutritionRepository {
    val nutritionSharedPrefsAccessor = NutritionSharedPrefsAccess()

    // TODO add HILT or implement it but think again about whole idea with combining local and remote sources here
    val nutritionRemoteDataSource = NutritionRemoteDataSource(
        NutritionServiceGenerator().nutritionAPI
    )

    suspend fun getNutritionUiModelData(date: String) = {
//            MutableLiveData<NutritionUiModel> {
//        val response = nutritionRemoteDataSource.getNutritionUiModel("2021-01-11")
//        if(response.status.equals(Resource.Status.SUCCESS)) {
//            Log.d(TAG, "NutritionRepositrory::getNutritionUiModelData, response SUCCESS")
//        } else if (response.status.equals(Resource.Status.ERROR)) {
//            Log.d(TAG, "NutritionRepositrory::getNutritionUiModelData, response ERROR")
//        }
//        //  TODO Why program thinks there might be null here? Work it out
//        return response.data!!
//    }

        performGetOperation(networkCall = { nutritionRemoteDataSource.getNutritionUiModel(date) })
    }


    /*
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getNutritionUiModelData(date: String?): NutritionUiModel {
        // todo implement date arg processing
        val nutritionUiModelData = nutritionSharedPrefsAccessor.getDataFromSharedPrefs()
        return if (nutritionUiModelData != null) {
            if (nutritionUiModelData.nutritionDailyData.date == LocalDate.now().toString()) {
                nutritionUiModelData
            } else {
                val dataFromRemote = getDataFromRemoteOrMakeInstance()

                nutritionSharedPrefsAccessor.saveDataToSharedPrefs(dataFromRemote)
                nutritionSharedPrefsAccessor.getDataFromSharedPrefs()!!
            }
        } else {
            val dataFromRemote = getDataFromRemoteOrMakeInstance()

            nutritionSharedPrefsAccessor.saveDataToSharedPrefs(dataFromRemote)
            nutritionSharedPrefsAccessor.getDataFromSharedPrefs()!!
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getDataFromRemoteOrMakeInstance(): NutritionUiModel {
        return nutritionAPI.getNutritionForDate(LocalDate.now().toString())
            ?: NutritionUiModel.getEmptyInstance()
    }

     */
}
