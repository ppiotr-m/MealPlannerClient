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

    private val _recipeFetchErrorOccurred = MutableLiveData(false)
    val recipeFeatchErrorOccurred: LiveData<Boolean> = _recipeFetchErrorOccurred

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

    private val _navigateBackIngredientsToRecipe = MutableLiveData<Event>()
    val navigateBackIngredientsToRecipe: LiveData<Event> = _navigateBackIngredientsToRecipe

    private val _noIngredientsSelectedAddition = MutableLiveData<Event>()
    val noIngredientsSelectedAddition: LiveData<Event> = _noIngredientsSelectedAddition

    private val _cookingModeNotAvailable = MutableLiveData<Event>()
    val cookingModeNotAvailable: LiveData<Event> = _cookingModeNotAvailable

    private var currentStepIndex = 0
    private val _currentCookingStep = MutableLiveData<InstructionStep>()
    val currentCookingStep: LiveData<InstructionStep> = _currentCookingStep
    private val _lastStepReached = MutableLiveData<Event>()
    val lastStepReached: LiveData<Event> = _lastStepReached
    private val _firstStepReached = MutableLiveData<Event>()
    val firstStepReached: LiveData<Event> = _firstStepReached
    private val _middleStepReached = MutableLiveData<Event>()
    val middleStepReached: LiveData<Event> = _middleStepReached

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

    private fun prepareThisViewModelForIngredientsFragment() {
        selectedIngredients.addAll(recipeData.value!!.recipeIngredients)
    }

    private fun prepareThisViewModelForCookingModeFragment() {
        if (recipeData.value!!.instructionSteps.size >= MINIMUM_COOKING_STEPS_COUNT_FOR_COOKING_MODE) {
            currentStepIndex = 0
            _currentCookingStep.value =
                recipeData.value!!.instructionSteps[currentStepIndex]  //  TODO Consider handling this (IndexOufOfBoundsException)
        } else {
            disableCookingMode()
        }
    }

    private fun disableCookingMode() {
        _cookingModeNotAvailable.value = Event()
    }

    fun getInstructionSteps(): List<InstructionStep> {
        return recipeData.value!!.instructionSteps
    }

    fun getRecipeIngredients(): List<RecipeIngredient> {
        return recipeData.value!!.recipeIngredients
    }

    fun saveItemsToShoppingListAndReturn() {
        if (selectedIngredients.isEmpty()) {
            noIngredientsSelected()
            return
        }
        val shoppingListManager = ShoppingListManager()
        shoppingListManager.saveIngredientsToStoredShoppingList(selectedIngredients)
        navigateBackIngredientsToRecipe()
    }

    private fun noIngredientsSelected() {
        _noIngredientsSelectedAddition.value = Event()
    }

    fun recipeIngredientListCheckboxClicked(item: RecipeIngredient, isChecked: Boolean) {
        if (isChecked) {
            selectedIngredients.add(item)
        } else {
            selectedIngredients.remove(item)
        }
    }

    private fun navigateBackIngredientsToRecipe() {
        _navigateBackIngredientsToRecipe.value = Event()
    }

    fun navigateToIngredients() {
        _navigateToIngredientsFragment.value = Event()
    }

    fun navigateToCookingSteps() {
        _navigateToCookingStepsFragment.value = Event()
    }

    fun navigateToCookingMode() {
        _navigateToCookingModeFragment.value = Event()
    }

    fun finishCookingMode() {
        _cookingModeFinished.value = Event()
        prepareThisViewModelForCookingModeFragment()
    }

    fun goToNextStep() {
        setNextStepIfExists()
    }

    private fun setNextStepIfExists() {
        currentStepIndex += 1
        if (!isLastStepReached()) {
            _currentCookingStep.value = recipeData.value!!.instructionSteps[currentStepIndex]
            setMiddleStep()

        } else {
            _currentCookingStep.value = recipeData.value!!.instructionSteps[currentStepIndex]
            setLastStep()
        }
    }

    private fun setFirstStep() {
        _firstStepReached.value = Event()
    }

    private fun setMiddleStep() {
        _middleStepReached.value = Event()
    }

    private fun setLastStep() {
        _lastStepReached.value = Event()
    }

    private fun isCurrentStepInMiddle(): Boolean {
        return ((0 < currentStepIndex) && (currentStepIndex < (recipeData.value!!.instructionSteps.size - 1)))
    }

    private fun isLastStepReached(): Boolean {
        return currentStepIndex >= (recipeData.value!!.instructionSteps.size - 1)
    }

    fun goToPreviousStep() {
        setPreviousStepIfExists()
    }

    private fun setPreviousStepIfExists() {
        currentStepIndex -= 1
        if (isFirstStepReached()) {
            _currentCookingStep.value = recipeData.value!!.instructionSteps[currentStepIndex]
            setFirstStep()
        } else {
            _currentCookingStep.value = recipeData.value!!.instructionSteps[currentStepIndex]
            setMiddleStep()
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
