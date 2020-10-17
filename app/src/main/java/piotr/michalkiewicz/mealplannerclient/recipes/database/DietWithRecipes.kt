package piotr.michalkiewicz.mealplannerclient.recipes.database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeBase

data class DietWithRecipes(
        @Embedded val dietType: DietType,
        @Relation(
                parentColumn = "dietTypeId",
                entityColumn = "id",
                associateBy = Junction(RecipesDietsCrossRef::class)
        )
        val recipes: List<MealTimeRecipeBase>
)