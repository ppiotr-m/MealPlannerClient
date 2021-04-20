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
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Event

class RecipeSharedViewModel(
    private val recipeAPI: RecipeAPI,
    private val recipesDatabase: RecipesDatabase
) : ViewModel() {

    private val MINIMUM_COOKING_STEPS_COUNT_FOR_COOKING_MODE = 3

    private val _recipeData = MutableLiveData<MealTimeRecipe>()
    val recipeData: LiveData<MealTimeRecipe> = _recipeData

    private val _recipeFetchErrorOccurred = MutableLiveData<Event>()
    val recipeFetchErrorOccurred: LiveData<Event> = _recipeFetchErrorOccurred

    //  TODO Should somehow bind it to checkboxes so their state is wired to this list from the beginning
    private val selectedIngredients = mutableListOf<RecipeIngredient>()

    private val _navigateToIngredientsFragment = MutableLiveData<Event>()
    val navigateToIngredientsFragment: LiveData<Event> = _navigateToIngredientsFragment

    private val _navigateToCookingStepsFragment = MutableLiveData<Event>()
    val navigateToCookingStepsFragment: LiveData<Event> = _navigateToCookingStepsFragment

    private val _navigateToCookingModeFragment = MutableLiveData<Event>()
    val navigateToCookingModeFragment: LiveData<Event> = _navigateToCookingModeFragment

    private val _cookingModeFinished = MutableLiveData<Event>()
    val cookingModeFinished: LiveData<Event> = _cookingModeFinished

    private val _navigateBack = MutableLiveData<Event>()
    val navigateBack: LiveData<Event> = _navigateBack

    private val _addingEmptyIngredientsList = MutableLiveData<Event>()
    val addingEmptyIngredientsList: LiveData<Event> = _addingEmptyIngredientsList

    private val _cookingModeNotAvailable = MutableLiveData<Event>()
    val cookingModeNotAvailable: LiveData<Event> = _cookingModeNotAvailable

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
                _recipeFetchErrorOccurred.value = Event()
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
            _addingEmptyIngredientsList.value = Event()
            return
        }
        val shoppingListManager = ShoppingListManager()
        shoppingListManager.saveIngredientsToStoredShoppingList(selectedIngredients)
        _navigateBack.value = Event()
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
            _cookingModeNotAvailable.value = Event()
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
        _navigateBack.value = Event()
    }

    fun navigateToIngredients() {
        _navigateToIngredientsFragment.value = Event()
    }

    fun resetNavigationToIngredientsFragment() {
        _navigateToIngredientsFragment.value = Event()
    }

    fun resetAddingToEmptyShoppingList() {
        _addingEmptyIngredientsList.value = Event()
    }

    fun navigateToCookingSteps() {
        _navigateToCookingStepsFragment.value = Event()
    }

    fun resetNavigationToCookingStepsFragment() {
        _navigateToCookingStepsFragment.value = Event()
    }

    fun navigateToCookingMode() {
        _navigateToCookingModeFragment.value = Event()
    }

    fun resetNavigationToCookingModeFragment() {
        _navigateToCookingModeFragment.value = Event()
    }

    fun finishCookingMode() {
        _cookingModeFinished.value = Event()
        prepareThisViewModelForCookingModeFragment()
    }

//    fun resetFinishCooking() {
//        _cookingModeFinished.value = Event()
//    }

//    fun resetCookingModeFinished() {
//        _cookingModeFinished.value = Event()
//    }

    fun goToNextStep() {
        setNextStepIfExists()
        _isCurrentStepTheFirst.value = false
    }

    private fun setNextStepIfExists() {
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