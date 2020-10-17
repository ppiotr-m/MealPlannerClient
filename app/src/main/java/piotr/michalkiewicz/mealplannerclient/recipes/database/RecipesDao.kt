package piotr.michalkiewicz.mealplannerclient.recipes.database

import androidx.paging.PagingSource
import androidx.room.*
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeBase


@Dao
abstract class RecipesDao {

    /*  TODO Remove if alternate method works
    @Query("SELECT * FROM recipes")
    abstract fun getRecipesForDiet(dietType: String): PagingSource<Int, MealTimeRecipeBase>
     */

    @Query("SELECT * FROM recipes")
    abstract fun getAllRecipes(): PagingSource<Int, MealTimeRecipeBase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRecipes(recipes: List<MealTimeRecipeBase>)

    @Insert
    abstract fun insertAllDietTypes(dietTypes: List<DietType>)

    @Insert
    abstract fun saveDiets(dietTypes: List<DietType>)

    @Transaction
    @Query("SELECT * FROM diet_type WHERE dietTypeName = :dietTypeNameArg")
    abstract fun getDietWithRecipes(dietTypeNameArg: String): PagingSource<Int, DietWithRecipes>

    fun insertAll(recipes: List<MealTimeRecipeBase>) {
        for (recipe in recipes) {
            insertDietsForRecipe(recipe.id, recipe.suitableForDiet)
        }
        insertRecipes(recipes)
    }

    fun insertDietsForRecipe(recipeId: Long, dietTypes: List<DietType>) {
        for (dietType in dietTypes) {
            dietType.recipeId = recipeId
        }
        insertAllDietTypes(dietTypes)
    }
}