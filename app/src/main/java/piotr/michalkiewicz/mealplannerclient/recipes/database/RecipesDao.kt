package piotr.michalkiewicz.mealplannerclient.recipes.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeBase


@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipes WHERE suitableForDiet LIKE '%' || :dietType || '%'")
    fun getRecipesForDiet(dietType: String): PagingSource<Int, MealTimeRecipeBase>

    @Query("SELECT * FROM recipes WHERE suitableForDiet LIKE '%' || :dietType || '%'")
    suspend fun getRecipesForDiet2(dietType: String): List<MealTimeRecipeBase>

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): PagingSource<Int, MealTimeRecipeBase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<MealTimeRecipeBase>)
}