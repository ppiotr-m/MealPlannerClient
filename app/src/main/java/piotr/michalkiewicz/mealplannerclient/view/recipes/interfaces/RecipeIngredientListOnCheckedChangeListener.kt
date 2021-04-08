package piotr.michalkiewicz.mealplannerclient.view.recipes.interfaces

import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient

interface RecipeIngredientListOnCheckedChangeListener {

    fun onCheckboxSelected(item: RecipeIngredient, isChecked: Boolean)
}