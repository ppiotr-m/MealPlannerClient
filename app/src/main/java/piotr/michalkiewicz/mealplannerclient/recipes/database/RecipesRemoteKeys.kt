package piotr.michalkiewicz.mealplannerclient.recipes.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes_remote_keys")
data class RecipesRemoteKeys(
        @PrimaryKey
        val recipeId: Long,
        val prevKey: Int?,
        val nextKey: Int?
)