package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository
import java.time.LocalDate

class NutritionSharedViewModel(val repository: NutritionRepository) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val uiModelLiveData = repository.getNutritionUiModelDataResource(LocalDate.now().toString())

    fun increaseDateByOneDay() {

    }
}