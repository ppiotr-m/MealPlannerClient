package piotr.michalkiewicz.mealplannerclient.shoppinglist.model

import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient

data class ShoppingList(val mapOfIngredients: Map<String, RecipeIngredient>)