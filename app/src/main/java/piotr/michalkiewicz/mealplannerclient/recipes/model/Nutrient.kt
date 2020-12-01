package piotr.michalkiewicz.mealplannerclient.recipes.model

data class Nutrient(
    val mongoId: String,
    val id: Int,
    val number: String,
    val name: String,
    val rank: Int,
    val unitName: String
)