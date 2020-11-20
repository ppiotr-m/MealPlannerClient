package piotr.michalkiewicz.mealplannerclient.recipes.database

import androidx.room.Entity

@Entity(tableName = "recipes_diets_cross_ref", primaryKeys = ["recipeId", "dietTypeId"])
data class RecipesDietsCrossRef(
        val recipeId: String,
        val dietTypeId: Long
)