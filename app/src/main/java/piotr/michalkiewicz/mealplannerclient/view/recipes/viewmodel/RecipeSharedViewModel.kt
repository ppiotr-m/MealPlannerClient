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
import piotr.michalkiewicz.mealplannerclient.recipes.model.InstructionStep
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.shoppinglist.utils.ShoppingListManager
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG

class RecipeSharedViewModel(
    private val recipeAPI: RecipeAPI,
    private val recipesDatabase: RecipesDatabase
) : ViewModel() {

    private val MINIMUM_COOKING_STEPS_COUNT_FOR_COOKING_MODE = 3

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

    private val _navigateToCookingModeFragment = MutableLiveData(false)
    val navigateToCookingModeFragment: LiveData<Boolean> = _navigateToCookingModeFragment

    private val _cookingModeFinished = MutableLiveData(false)
    val cookingModeFinished: LiveData<Boolean> = _cookingModeFinished

    private val _navigateBack = MutableLiveData(false)
    val navigateBack: LiveData<Boolean> = _navigateBack

    private val _addingEmptyIngredientsList = MutableLiveData(false)
    val addingEmptyIngredientsList: LiveData<Boolean> = _addingEmptyIngredientsList

    private val _cookingModeNotAvailable = MutableLiveData<Boolean>(false)
    val cookingModeNotAvailable = _cookingModeNotAvailable

    private val _isLastStepReached = MutableLiveData(false)
    val isLastStepReached: LiveData<Boolean> = _isLastStepReached
    private var currentStepIndex = 0
    private val _currentCookingStep = MutableLiveData<InstructionStep>()
    val currentCookingStep: LiveData<InstructionStep> = _currentCookingStep
    private val _isCurrentStepTheFirst = MutableLiveData(true)
    val isCurrentStepTheFirst: LiveData<Boolean> = _isCurrentStepTheFirst

    fun initialize(recipeId: String) {
        viewModelScope.launch {
            val response = recipeAPI.getRecipeForId(recipeId)
            if (response.isSuccessful) {
                _recipeData.value = response.body()
                prepareThisViewModelForIngredientsFragment()
                prepareThisViewModelForCookingModeFragment()
            } else {
                _recipeFetchErrorOccurred.value = true
            }
        }
    }

    fun getInstructionSteps(): List<InstructionStep> {
        return recipeData.value!!.instructionSteps
    }

    fun getRecipeIngredients(): List<RecipeIngredient> {
        return recipeData.value!!.recipeIngredients
    }

    fun saveItemsToShoppingListAndReturn() {
        if (selectedIngredients.isEmpty()) {
            _addingEmptyIngredientsList.value = true
            return
        }
        val shoppingListManager = ShoppingListManager()
        shoppingListManager.saveIngredientsToStoredShoppingList(selectedIngredients)
        _navigateBack.value = true
    }

    private fun prepareThisViewModelForIngredientsFragment() {
        selectedIngredients.addAll(recipeData.value!!.recipeIngredients)
    }

    private fun prepareThisViewModelForCookingModeFragment() {
        if (recipeData.value!!.instructionSteps.size >= MINIMUM_COOKING_STEPS_COUNT_FOR_COOKING_MODE) {
            currentStepIndex = 0
            _currentCookingStep.value =
                recipeData.value!!.instructionSteps[currentStepIndex]  //  TODO Consider handling this (IndexOufOfBoundsException)
        } else {
            _cookingModeNotAvailable.value = true
        }
    }

    fun recipeIngredientListCheckboxClicked(item: RecipeIngredient, isChecked: Boolean) {
        if (isChecked) {
            selectedIngredients.add(item)
        } else {
            selectedIngredients.remove(item)
        }
    }

    fun resetNavigateBack() {
        _navigateBack.value = false
    }

    fun navigateToIngredients() {
        _navigateToIngredientsFragment.value = true
    }

    fun resetNavigationToIngredientsFragment() {
        _navigateToIngredientsFragment.value = false
    }

    fun resetAddingToEmptyShoppingList() {
        _addingEmptyIngredientsList.value = false
    }

    fun navigateToCookingSteps() {
        _navigateToCookingStepsFragment.value = true
    }

    fun resetNavigationToCookingStepsFragment() {
        _navigateToCookingStepsFragment.value = false
    }

    fun navigateToCookingMode() {
        _navigateToCookingModeFragment.value = true
    }

    fun resetNavigationToCookingModeFragment() {
        _navigateToCookingModeFragment.value = false
    }

    fun finishCookingMode() {
        _cookingModeFinished.value = true
        prepareThisViewModelForCookingModeFragment()
    }

    fun resetFinishCooking() {
        _cookingModeFinished.value = false
    }

    fun resetCookingModeFinished() {
        _cookingModeFinished.value = false
    }

    fun goToNextStep() {
        setNextStepIfExists()
        _isCurrentStepTheFirst.value = false
    }

    private fun setNextStepIfExists() {
        Log.d(TAG, "Setting next step if exists, current step index: " + currentStepIndex)
        currentStepIndex += 1
        if (!isLastStepReached()) {
            _currentCookingStep.value = recipeData.value!!.instructionSteps[currentStepIndex]

        } else {
            _currentCookingStep.value = recipeData.value!!.instructionSteps[currentStepIndex]
            _isLastStepReached.value = true
        }
    }

    private fun isLastStepReached(): Boolean {
        return currentStepIndex >= (recipeData.value!!.instructionSteps.size - 1)
    }

    fun resetIsFirstStep() {
        _isCurrentStepTheFirst.value = true
    }

    fun resetLastStepReached() {
        _isLastStepReached.value = false
    }

    fun goToPreviousStep() {
        setPreviousStepIfExists()
        _isLastStepReached.value = false
    }

    private fun setPreviousStepIfExists() {
        currentStepIndex -= 1
        if (isFirstStepReached()) {
            _currentCookingStep.value = recipeData.value!!.instructionSteps[currentStepIndex]
            _isCurrentStepTheFirst.value = true
        } else {
            _currentCookingStep.value = recipeData.value!!.instructionSteps[currentStepIndex]
        }
    }

    private fun isFirstStepReached(): Boolean {
        return currentStepIndex == 0
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:imageBitmap")
        fun setImageBitmap(imageView: ImageView, imageBitmap: Bitmap?) {
            imageView.setImageBitmap(imageBitmap)
        }
    }
}