package piotr.michalkiewicz.mealplannerclient.view.recipes.interfaces

import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient

interface RecipeIngredientListOnCheckedChangeListener {

    fun onCheckboxClicked(item: RecipeIngredient, isChecked: Boolean)
}