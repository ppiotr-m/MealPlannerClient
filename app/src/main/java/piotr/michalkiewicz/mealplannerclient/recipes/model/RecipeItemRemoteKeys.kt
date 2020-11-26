package piotr.michalkiewicz.mealplannerclient.recipes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeItemRemoteKeys(
        @PrimaryKey
        val recipeId: String,
        val prevKey: Int?,
        val nextKey: Int?
)