package piotr.michalkiewicz.mealplannerclient.recipes.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe

@Dao
interface RecipesDao {

    @Query("SELECT * FROM recipes WHERE suitableForDiet = :dietType ")
    fun getRecipesForDiet(dietType: String): PagingSource<Int, MealTimeRecipe>

    @Query("SELECT * FROM recipes WHERE recipeTag = :recipeTag ")
    fun getRecipesForTag(recipeTag: String): PagingSource<Int, List<MealTimeRecipe>>

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): PagingSource<Int, List<MealTimeRecipe>>

    @Insert
    fun saveRecipes(recipes: List<MealTimeRecipe>): Completable

}