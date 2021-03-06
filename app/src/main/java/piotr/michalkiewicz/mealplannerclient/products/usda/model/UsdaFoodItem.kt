package piotr.michalkiewicz.mealplannerclient.products.usda.model

data class UsdaFoodItem(
    val fdcId: Int,
    val description: String,
    val foodPortions: List<FoodPortion>
)