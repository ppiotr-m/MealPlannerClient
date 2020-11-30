package piotr.michalkiewicz.mealplannerclient.recipes.model

import piotr.michalkiewicz.mealplannerclient.nutrition.model.FoodNutrient

abstract class EatableItem{
    abstract val name: String
    abstract val foodNutrientsSummary: List<FoodNutrient>
}
