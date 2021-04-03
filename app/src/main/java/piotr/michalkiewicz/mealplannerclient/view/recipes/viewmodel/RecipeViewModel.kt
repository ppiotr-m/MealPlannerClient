package piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.recipes.database.RecipesDatabase
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe

class RecipeViewModel(
    private val recipeAPI: RecipeAPI,
    private val recipesDatabase: RecipesDatabase
) : ViewModel() {

    private val _recipeData = MutableLiveData<MealTimeRecipe>()
    val recipeData = _recipeData

    private val _recipeFetchErrorOccurred = MutableLiveData(false)
    val recipeFeatchErrorOccurred = _recipeFetchErrorOccurred

    fun initialize(recipeId: String) {
        viewModelScope.launch {
            val response = recipeAPI.getRecipeForId(recipeId)
            if (response.isSuccessful) {
                _recipeData.value = response.body()
            } else {
                _recipeFetchErrorOccurred.value = true
            }
        }
    }

//    @BindingAdapter("android:imageBitmap")
//    fun setImageBitmap(imageView: ImageView, imageBitmap: Bitmap) {
//        imageView.setImageBitmap(imageBitmap)
//    }
}