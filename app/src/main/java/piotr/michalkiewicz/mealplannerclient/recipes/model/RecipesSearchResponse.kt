package piotr.michalkiewicz.mealplannerclient.recipes.model

import com.google.gson.annotations.SerializedName

data class RecipesSearchResponse(

        @SerializedName("total_count") val total: Int = 0,
        @SerializedName("recipes") val recipes: List<piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe> = emptyList(),
        val nextPage: Int? = null
)