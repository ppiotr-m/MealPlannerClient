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
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.shoppinglist.utils.ShoppingListManager
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

    //  TODO Should somehow bind it to checkboxes so their state is wired to this list from the beginning
    //  Passing viewmodel to ingredient list item and checking if contains tag model might work
    private val selectedIngredients = mutableListOf<RecipeIngredient>()

    fun initialize(recipeId: String) {
        viewModelScope.launch {
            val response = recipeAPI.getRecipeForId(recipeId)

            if (response.isSuccessful) {
                _recipeData.value = response.body()
                selectedIngredients.addAll(recipeData.value!!.recipeIngredients)
            } else {
                _recipeFetchErrorOccurred.value = true
            }
        }
    }

    fun saveItemsToShoppingListAndReturn() {
        val shoppingListManager =
            ShoppingListManager()     //  TODO When HILT added, move to constructor

        shoppingListManager.saveIngredientsToStoredShoppingList(selectedIngredients)

        val map = shoppingListManager.getShoppingListMapFromSharedPrefs()

        Log.d(TAG, "Shopping list item count: " + map.values.size)
    }

    fun recipeIngredientListCheckboxClicked(item: RecipeIngredient, isChecked: Boolean) {
        if (isChecked) {
            selectedIngredients.add(item)
        } else {
            selectedIngredients.remove(item)
        }
    }

    fun navigateToIngredients() {
        _navigateToIngredientsFragment.value = true
    }

    fun resetNavigationToIngredientsFragment() {
        _navigateToIngredientsFragment.value = false
    }

    fun resetNavigationToCookingStepsFragment() {
        _navigateToIngredientsFragment.value = false
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:imageBitmap")
        fun setImageBitmap(imageView: ImageView, imageBitmap: Bitmap?) {
            imageView.setImageBitmap(imageBitmap)
        }
    }
}