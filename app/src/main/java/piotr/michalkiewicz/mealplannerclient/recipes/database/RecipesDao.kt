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
    @Query("SELECT * FROM recipes WHERE suitableForDiet LIKE '%' || :dietType || '%'")
    abstract fun getRecipesForDiet(dietType: String): PagingSource<Int, MealTimeRecipeBase>

    @Query("SELECT * FROM recipes")
    abstract fun getAllRecipes(): PagingSource<Int, MealTimeRecipeBase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRecipes(recipes: List<MealTimeRecipeBase>)
}