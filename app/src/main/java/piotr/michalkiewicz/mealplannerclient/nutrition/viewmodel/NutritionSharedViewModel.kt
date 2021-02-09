package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository
import piotr.michalkiewicz.mealplannerclient.utils.Resource
import java.time.LocalDate

class NutritionSharedViewModel(val repository: NutritionRepository) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val _date = MutableLiveData(LocalDate.now())

    @RequiresApi(Build.VERSION_CODES.O)
    val date: LiveData<LocalDate> = _date

    @RequiresApi(Build.VERSION_CODES.O)
    val _uiModelLiveData = _date.switchMap { date ->
        repository.getNutritionUiModelDataResource(date.toString())
    }

    val uiModelLiveData: LiveData<Resource<NutritionUiModel>> = _uiModelLiveData

    @RequiresApi(Build.VERSION_CODES.O)
    private fun increaseDateByOneDay() {
        _date.value = _date.value!!.plusDays(1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun decreaseDateByOneDay() {
        _date.value = _date.value!!.minusDays(1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun goToNextDay() {
        increaseDateByOneDay()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun goToPreviousDay() {
        _date.value = null
        decreaseDateByOneDay()
    }
}