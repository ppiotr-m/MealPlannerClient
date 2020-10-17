package piotr.michalkiewicz.mealplannerclient.recipes.database

import androidx.room.Entity

@Entity(tableName = "recipes_diets_cross_ref", primaryKeys = ["id", "dietTypeId"])
data class RecipesDietsCrossRef(
        val id: Long,
        val dietTypeId: Long
)