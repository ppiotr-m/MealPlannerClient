package piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.recipes.database.RecipesDatabase
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG

class RecipeViewModel(
    private val recipeAPI: RecipeAPI,
    private val recipesDatabase: RecipesDatabase
) : ViewModel() {

    private val _recipeData = MutableLiveData<MealTimeRecipe>()
    val recipeData: LiveData<MealTimeRecipe> = _recipeData

    private val _recipeFetchErrorOccurred = MutableLiveData(false)
    val recipeFeatchErrorOccurred: LiveData<Boolean> = _recipeFetchErrorOccurred

    private val _navigateToIngredientsFragment = MutableLiveData(false)
    val navigateToIngredientsFragment: LiveData<Boolean> = _navigateToIngredientsFragment

    private val _navigateToCookingStepsFragment = MutableLiveData(false)
    val navigateToCookingStepsFragment: LiveData<Boolean> = _navigateToCookingStepsFragment

    fun initialize(recipeId: String) {
        viewModelScope.launch {
            val response = recipeAPI.getRecipeForId(recipeId)

            if (response.isSuccessful) {
                _recipeData.value = response.body()
                Log.d(TAG, "Recipe description: " + recipeData.value!!.totalLikes)
            } else {
                _recipeFetchErrorOccurred.value = true
            }
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:imageBitmap")
        fun setImageBitmap(imageView: ImageView, imageBitmap: Bitmap?) {
            imageView.setImageBitmap(imageBitmap)
        }
    }
}