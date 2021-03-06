package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.nutrition.model.DailyEatenFoods
import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatableItem
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.NutritionDataUpdater
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository
import piotr.michalkiewicz.mealplannerclient.products.model.BasicFoodItemData
import piotr.michalkiewicz.mealplannerclient.products.repository.ProductsRepository
import piotr.michalkiewicz.mealplannerclient.products.usda.UsdaServiceGenerator
import piotr.michalkiewicz.mealplannerclient.products.usda.api.UsdaAPI
import piotr.michalkiewicz.mealplannerclient.products.usda.model.UsdaFoodItem
import piotr.michalkiewicz.mealplannerclient.utils.Resource
import java.time.LocalDate
import java.util.*

class NutritionSharedViewModel(
    private val repository: NutritionRepository,
    private val productsRepository: ProductsRepository,
    private val usdaService: UsdaAPI
) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val _date = MutableLiveData(LocalDate.now())

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

    private val _productsList: MutableLiveData<List<BasicFoodItemData>> = MutableLiveData()
    val productsList: LiveData<List<BasicFoodItemData>> = _productsList

    private val _selectedProduct: MutableLiveData<UsdaFoodItem> = MutableLiveData()
    val selectedProduct: LiveData<UsdaFoodItem> = _selectedProduct

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

    private fun getSelectedProduct(position: Int): BasicFoodItemData {
        return productsList.value!![position]
    }

    fun getProductDetailData(productPosition: Int) {
        viewModelScope.launch {
            try {
                val response =
                    usdaService.getProductDetailData(
                        UsdaServiceGenerator.getFullUrlForProductId(
                            getSelectedProduct(productPosition).fdcId
                        ),
                        UsdaServiceGenerator.USDA_API_KEY
                    )
                if (response.isSuccessful) {
                    _selectedProduct.value = response.body()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}