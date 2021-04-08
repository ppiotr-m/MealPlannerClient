package piotr.michalkiewicz.mealplannerclient.shoppinglist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.shoppinglist.utils.ShoppingListManager

class ShoppingListViewModel : ViewModel() {

    private val shoppingListManager = ShoppingListManager()

    private val _shoppingListItems = MutableLiveData<Map<String, RecipeIngredient>>()
    val shoppingListItems = _shoppingListItems

    init {
        _shoppingListItems.value = shoppingListManager.getShoppingListMapFromSharedPrefs()
    }
}