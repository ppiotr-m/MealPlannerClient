package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatableItem
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.NutritionDataUpdater
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository
import piotr.michalkiewicz.mealplannerclient.recipes.RecipeServiceGenerator
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
        decreaseDateByOneDay()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addMeal() {
        GlobalScope.launch {
            val api = RecipeServiceGenerator().recipeAPI
            val recipe = api.getRecipeForIdCoroutine("5fc1693a7907e07a453ddf4e")

            if (recipe.foodNutrientsSummary != null) {
                val eatableItem = EatableItem(
                    recipe.name,
                    recipe.foodNutrientsSummary.associateBy({ it.nutrient.name }, { it }),
                    "1",
                    "portion"
                )
                val updater = NutritionDataUpdater()

                updater.saveMealToTodayNutrition(eatableItem)
            }
        }
    }
}