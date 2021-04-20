package piotr.michalkiewicz.mealplannerclient.shoppinglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.shoppinglist.utils.ShoppingListManager
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Event

class ShoppingListViewModel : ViewModel() {

    private val shoppingListManager = ShoppingListManager()

    private val _shoppingListItems = MutableLiveData<MutableList<RecipeIngredient>>()
    val shoppingListItems: LiveData<MutableList<RecipeIngredient>> = _shoppingListItems

    private val selectedIngredients = mutableListOf<RecipeIngredient>()

    private val _ingredientsDeletedNotifier = MutableLiveData<Event>()
    val ingredientsDeletedNotifier: LiveData<Event> = _ingredientsDeletedNotifier

    init {
        _shoppingListItems.value =
            shoppingListManager.getShoppingListFromSharedPrefs()
    }

    fun ingredientCheckboxClicked(item: RecipeIngredient, isChecked: Boolean) {
        if (isChecked) {
            selectedIngredients.add(item)
        } else {
            selectedIngredients.remove(item)
        }
    }

    fun deleteSelectedIngredients() {
        shoppingListManager.deleteIngredientsFromStoredShoppingList(selectedIngredients)
        selectedIngredients.forEach {
            _shoppingListItems.value!!.remove(it)
        }
        selectedIngredients.clear()
        _ingredientsDeletedNotifier.value = Event()
    }

    fun resetIngredientsDeletedNotifier() {
        _ingredientsDeletedNotifier.value = Event()
    }

    fun isShoppingListEmpty(): Boolean {
        return _shoppingListItems.value!!.isEmpty()
    }
}