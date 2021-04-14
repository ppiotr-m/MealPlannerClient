package piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.recipes.database.RecipesDatabase
import piotr.michalkiewicz.mealplannerclient.recipes.model.InstructionStep
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.shoppinglist.utils.ShoppingListManager

class RecipeViewModel(
    private val recipeAPI: RecipeAPI,
    private val recipesDatabase: RecipesDatabase
) : ViewModel() {

    private val _recipeData = MutableLiveData<MealTimeRecipe>()
    val recipeData: LiveData<MealTimeRecipe> = _recipeData

    private val _recipeFetchErrorOccurred = MutableLiveData(false)
    val recipeFeatchErrorOccurred: LiveData<Boolean> = _recipeFetchErrorOccurred

    //  TODO Should somehow bind it to checkboxes so their state is wired to this list from the beginning
    private val selectedIngredients = mutableListOf<RecipeIngredient>()

    private val _navigateToIngredientsFragment = MutableLiveData(false)
    val navigateToIngredientsFragment: LiveData<Boolean> = _navigateToIngredientsFragment

    private val _navigateToCookingStepsFragment = MutableLiveData(false)
    val navigateToCookingStepsFragment: LiveData<Boolean> = _navigateToCookingStepsFragment

    private val _lastStepReached = MutableLiveData(false)
    val lastStepReached: LiveData<Boolean> = _lastStepReached
    private var currentStepIndex = 0
    private val _currentCookingStep = MutableLiveData<InstructionStep>()
    val currentCookingStep: LiveData<InstructionStep> = _currentCookingStep

    fun initialize(recipeId: String) {
        viewModelScope.launch {
            val response = recipeAPI.getRecipeForId(recipeId)
            if (response.isSuccessful) {
                _recipeData.value = response.body()
                prepareThisViewModelForIngredientsFragment()
            } else {
                _recipeFetchErrorOccurred.value = true
            }
        }
    }

    fun saveItemsToShoppingListAndReturn() {
        val shoppingListManager =
            ShoppingListManager()     //  TODO When HILT added, move to constructor
        shoppingListManager.saveIngredientsToStoredShoppingList(selectedIngredients)
    }

    private fun prepareThisViewModelForIngredientsFragment() {
        selectedIngredients.addAll(recipeData.value!!.recipeIngredients)
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

    private fun prepareThisViewModelForCookingModeFragment() {
        _currentCookingStep.value =
            recipeData.value!!.instructionSteps[currentStepIndex]  //  TODO Consider handling this (NoSuchElementException)
    }

    fun navigateToCookingSteps() {
        _navigateToCookingStepsFragment.value = true
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

    fun goToNextStep() {
        setNextStepIfExists()
    }

    private fun setNextStepIfExists() {
        currentStepIndex += 1
        if (!isLastStepReached()) {
            _currentCookingStep.value = recipeData.value!!.instructionSteps[currentStepIndex]
        } else {
            _lastStepReached.value = true
        }
    }

    private fun isLastStepReached(): Boolean {
        return currentStepIndex == (recipeData.value!!.instructionSteps.size - 1)
    }

    fun goToPreviousStep() {
        setPreviousStepIfExists()
    }

    private fun setPreviousStepIfExists() {
        currentStepIndex -= 1
        if (!isFirstStepReached()) {
            _currentCookingStep.value = recipeData.value!!.instructionSteps[currentStepIndex]
        } else {
            _lastStepReached.value = true
        }
    }

    private fun isFirstStepReached(): Boolean {
        return currentStepIndex == 0
    }
}