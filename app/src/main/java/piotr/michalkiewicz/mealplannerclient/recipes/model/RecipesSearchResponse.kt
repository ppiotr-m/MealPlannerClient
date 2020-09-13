package piotr.michalkiewicz.mealplannerclient.recipes.model

import com.google.gson.annotations.SerializedName

data class RecipesSearchResponse(

        @SerializedName("totalPagesCount") val total: Int = 0,
        @SerializedName("result") val recipes: List<MealTimeRecipeMiniatureData> = emptyList(),
        val nextPage: Int? = null
)