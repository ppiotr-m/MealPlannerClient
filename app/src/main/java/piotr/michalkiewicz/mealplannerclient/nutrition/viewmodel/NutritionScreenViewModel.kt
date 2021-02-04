package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.utils.DateRelatedService
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NutritionScreenViewModel(
    private val nutritionRepository: NutritionRepository,
    private val dateRelatedServices: List<DateRelatedService>
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDateLiveData = MutableLiveData(LocalDate.now().toString())

    companion object {
        val NUTRITION_DATA = "nutrition_data"
        val NUTRITION_RECOMMENDATIONS = "nutrition_recommendations"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun increaseDateByOneDay() {
        val newDate = LocalDate.parse(
            selectedDateLiveData.value,
            DateTimeFormatter.ofPattern(ConstantValues.DATE_FORMAT)
        ).plusDays(1)

        dateRelatedServices.forEach {
            it.updateAccordingToDate(newDate)
        }
        selectedDateLiveData.value = newDate.toString()
    }
}