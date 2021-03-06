package piotr.michalkiewicz.mealplannerclient.nutrition.model

import piotr.michalkiewicz.mealplannerclient.products.usda.model.FoodPortion

data class EatenProductWithDate(
    val mongoId: String,
    val amount: Float,
    val portion: FoodPortion,
    val date: String
)