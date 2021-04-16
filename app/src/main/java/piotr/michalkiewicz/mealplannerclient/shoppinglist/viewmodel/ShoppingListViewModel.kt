package piotr.michalkiewicz.mealplannerclient.shoppinglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.shoppinglist.utils.ShoppingListManager

class ShoppingListViewModel : ViewModel() {

    private val shoppingListManager = ShoppingListManager()

    private val _shoppingListItems = MutableLiveData<MutableMap<String, RecipeIngredient>>()
    val shoppingListItems: LiveData<MutableMap<String, RecipeIngredient>> = _shoppingListItems

    private val selectedIngredients = mutableListOf<RecipeIngredient>()

    val checker = MutableLiveData(false)

    init {
        _shoppingListItems.value =
            shoppingListManager.getShoppingListMapFromSharedPrefs().toMutableMap()
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
            _shoppingListItems.value!!.remove(it.name)
        }

        checker.value = true
    }
}