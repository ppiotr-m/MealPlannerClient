package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository
import java.time.LocalDate

class NutritionGeneralTabViewModel(
    val repository: NutritionRepository
) : ViewModel() {

    //  TODO Handle nulabilty
    @RequiresApi(Build.VERSION_CODES.O)
    val uiModelLiveData = repository.getNutritionUiModelDataResource(LocalDate.now().toString())
}