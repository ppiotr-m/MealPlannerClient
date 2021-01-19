package piotr.michalkiewicz.mealplannerclient.nutrition.local

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionScreenViewModel
import piotr.michalkiewicz.mealplannerclient.view.MainActivity

// TODO move to different package
class NutritionLiveData(private val nutrititionSharedPrefsAccess: NutritionSharedPrefsAccess) :
    LiveData<NutritionUiModel>() {

    private val nutritionDataChangedListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences: SharedPreferences?, key: String? ->
            if (key == NutritionScreenViewModel.NUTRITION_DATA) {
                value = nutrititionSharedPrefsAccess.getUiModelFromSharedPrefs()
            }
        }

    override fun onActive() {
        super.onActive()
        value = nutrititionSharedPrefsAccess.getUiModelFromSharedPrefs()
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

    companion object {
        fun getInstance(nutrititionSharedPrefsAccess: NutritionSharedPrefsAccess): NutritionLiveData {
            return NutritionLiveData(nutrititionSharedPrefsAccess)
        }
    }

    class NutritionSharedPrefencesLiveData
}