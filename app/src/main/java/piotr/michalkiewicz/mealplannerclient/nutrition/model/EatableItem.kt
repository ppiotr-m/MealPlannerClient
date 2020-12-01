package piotr.michalkiewicz.mealplannerclient.nutrition.model

import com.google.gson.annotations.SerializedName
import piotr.michalkiewicz.mealplannerclient.recipes.model.FoodNutrient

data class EatableItem(

    @SerializedName("name")
    val name: String,
    @SerializedName("foodNutrientsSummary")
    val foodNutrientsSummary: List<FoodNutrient>,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("unit")
    val unit: String
)