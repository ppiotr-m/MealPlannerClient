package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.nutrition.model.DailyEatenFoods
import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatableItem
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.NutritionDataUpdater
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository
import piotr.michalkiewicz.mealplannerclient.products.model.BasicFoodItemData
import piotr.michalkiewicz.mealplannerclient.products.repository.ProductsRepository
import piotr.michalkiewicz.mealplannerclient.recipes.RecipeServiceGenerator
import piotr.michalkiewicz.mealplannerclient.utils.Resource
import java.time.LocalDate
import java.util.*

class NutritionSharedViewModel(
    val repository: NutritionRepository,
    val productsRepository: ProductsRepository
) : ViewModel() {

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

    fun refreshData() {
        _date.postValue(_date.value)
    }

    fun getEatableItemList(): List<EatableItem>? {
        return uiModelLiveData.value?.data?.nutritionDailyData?.eatenFoods   //  TODO Handle nulls
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteItemWithGivenPosition(position: Int) {
        val currentList =
            _uiModelLiveData.value?.data?.nutritionDailyData?.eatenFoods  // TODO Handle nulls
        val newList = LinkedList<EatableItem>()
        val listLength = currentList!!.size
        for (i in 0..(listLength - 1)) {
            if (i == position) {
                continue
            }
            newList.add(currentList[i])
        }

        viewModelScope.launch {
            NutritionDataUpdater().setNewDailyEatenFoods(
                DailyEatenFoods(
                    date.value.toString(),
                    newList
                )
            )
            refreshData()
        }
    }

    val user = MutableLiveData("")
    val _productsList: MutableLiveData<List<BasicFoodItemData>> = MutableLiveData()
    val productsList: LiveData<List<BasicFoodItemData>> = _productsList

    val valid = MediatorLiveData<Boolean>().apply {
        addSource(user) {
            searchProduct(it)
            value = true
        }
    }

    fun searchProduct(name: String) {
        viewModelScope.launch {
            try {
                val response = productsRepository.searchProduct(name)
                if (response!!.isSuccessful) {
                    _productsList.value = response.body()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}