package piotr.michalkiewicz.mealplannerclient.recipes.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe


@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipes WHERE suitableForDiet LIKE '%' || :dietType || '%' ORDER BY id")
    fun getRecipesForDiet(dietType: String): PagingSource<Int, MealTimeRecipe>

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): PagingSource<Int, MealTimeRecipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<MealTimeRecipe>)

    @Query("DELETE FROM recipes")
    fun deleteAllRecipes(): Int
}