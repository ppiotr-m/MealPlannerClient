package piotr.michalkiewicz.mealplannerclient.recipes.paging.api

import com.google.gson.annotations.SerializedName
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe

data class RecipeSearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("recipes") val recipes: List<MealTimeRecipe> = emptyList(),
    val nextPage: Int? = null
)