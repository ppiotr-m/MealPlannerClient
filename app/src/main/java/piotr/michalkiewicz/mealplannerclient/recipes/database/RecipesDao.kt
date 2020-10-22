package piotr.michalkiewicz.mealplannerclient.recipes.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeBase


@Dao
abstract class RecipesDao {

    @Query("SELECT * FROM recipes WHERE id in (:idList)")
    abstract fun getRecipesForDiet(idList: List<Long>): PagingSource<Int, MealTimeRecipeBase>

    @Query("SELECT * FROM recipes")
    abstract fun getAllRecipes(): PagingSource<Int, MealTimeRecipeBase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRecipes(recipes: List<MealTimeRecipeBase>)

    @Insert
    abstract fun insertAllDietTypes(dietTypes: List<DietType>)

    @Insert
    abstract fun saveDiets(dietTypes: List<DietType>)

    @Query("SELECT recipeId FROM diet_type WHERE dietTypeName = :dietTypeNameArg")
    abstract fun getDietWithRecipes(dietTypeNameArg: String): Observable<List<MealTimeRecipeBase>>

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