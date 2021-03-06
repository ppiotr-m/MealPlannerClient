package piotr.michalkiewicz.mealplannerclient.shoppinglist.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import piotr.michalkiewicz.mealplannerclient.view.MainActivity

class ShoppingListManager {

    private val formatHandler = AmountFormatHandler()

    fun saveIngredientsToStoredShoppingList(ingredientsList: List<RecipeIngredient>) {
        val currentShoppingMap = getShoppingListMapFromSharedPrefs()
        val updatedShoppingListMap = mutableMapOf<String, RecipeIngredient>()

        if (currentShoppingMap != null) {
            updatedShoppingListMap.putAll(currentShoppingMap)

            //  TODO Clean this
            ingredientsList.forEach {
                try {
                    if (currentShoppingMap.containsKey(it.name)) {
                        val storedShoppingListItem = currentShoppingMap[it.name]
                        if (storedShoppingListItem!!.unit == it.unit) {
                            val amountSum =
                                formatHandler.normalizeAmount(storedShoppingListItem.amount) + formatHandler.normalizeAmount(
                                    it.amount
                                )
                            val updatedShoppingListItem =
                                storedShoppingListItem.copy(amount = amountSum.toString())
                            updatedShoppingListMap[it.name] =
                                updatedShoppingListItem
                        } else {
                            updatedShoppingListMap[(it.name + " " + it.unit)] = it
                        }
                    } else {
                        updatedShoppingListMap[it.name] = it
                    }
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
            }
        } else {
            updatedShoppingListMap.putAll(ingredientsList.associateBy { it.name })
        }

        saveShoppingListToSharedPrefs(updatedShoppingListMap)
    }

    fun getShoppingListMapFromSharedPrefs(): Map<String, RecipeIngredient> {
        val json =
            MainActivity.MY_PREFERENCES.getString(ConstantValues.SHOPPING_LIST_SHARED_PREF, "")

        if (json!!.isEmpty()) return emptyMap()
        try {
            val storedShoppingList =
                Gson().fromJson(
                    json,
                    object : TypeToken<Map<String, RecipeIngredient>>() {}.type
                ) as Map<String, RecipeIngredient>
            return storedShoppingList
        } catch (e: ClassCastException) {
            Log.e(TAG, e.localizedMessage)
            e.printStackTrace()
        }
        return emptyMap()
    }

    fun getShoppingListFromSharedPrefs(): MutableList<RecipeIngredient> {
        return getShoppingListMapFromSharedPrefs().values.toMutableList()
    }

    fun deleteIngredientsFromStoredShoppingList(ingredientsList: List<RecipeIngredient>) {
        val currentShoppingMap = getShoppingListMapFromSharedPrefs()
        val updatedShoppingListMap = mutableMapOf<String, RecipeIngredient>()

        if (!currentShoppingMap.isEmpty()) {
            updatedShoppingListMap.putAll(currentShoppingMap)
            ingredientsList.forEach {
                if (currentShoppingMap.containsKey(it.name)) {
                    updatedShoppingListMap.remove(it.name)
                }
            }
        }
        saveShoppingListToSharedPrefs(updatedShoppingListMap)
    }

    private fun saveShoppingListToSharedPrefs(recipeIngredientListMap: Map<String, RecipeIngredient>) {
        val dataInJson = Gson().toJson(recipeIngredientListMap)

        MainActivity.MY_PREFERENCES.edit()
            .putString(ConstantValues.SHOPPING_LIST_SHARED_PREF, dataInJson).commit()
    }

}