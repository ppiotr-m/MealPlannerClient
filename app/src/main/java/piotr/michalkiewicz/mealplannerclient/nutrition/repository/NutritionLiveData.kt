package piotr.michalkiewicz.mealplannerclient.nutrition.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import piotr.michalkiewicz.mealplannerclient.nutrition.local.NutritionSharedPrefsDao
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionScreenViewModel
import piotr.michalkiewicz.mealplannerclient.view.MainActivity

class NutritionLiveData(private val nutrititionSharedPrefsDao: NutritionSharedPrefsDao) :
    LiveData<NutritionUiModel>() {

    private val nutritionDataChangedListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences: SharedPreferences?, key: String? ->
            if (key == NutritionScreenViewModel.NUTRITION_DATA) {
                value = nutrititionSharedPrefsDao.getDataFromSharedPrefs()
            }
        }

    override fun onActive() {
        super.onActive()
        value = nutrititionSharedPrefsDao.getDataFromSharedPrefs()
        MainActivity.MY_PREFERENCES.registerOnSharedPreferenceChangeListener(
            nutritionDataChangedListener
        )
    }

    override fun onInactive() {
        super.onInactive()
        MainActivity.MY_PREFERENCES.unregisterOnSharedPreferenceChangeListener(
            nutritionDataChangedListener
        )
    }

    class NutritionSharedPrefencesLiveData
}