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

    /*
    @Query("SELECT * FROM recipes WHERE id in (:idList)")
    abstract fun getRecipesForDiet(idList: List<Long>): PagingSource<Int, MealTimeRecipeBase>
     */

    @Query("SELECT * FROM recipes")
    abstract fun getAllRecipes(): PagingSource<Int, MealTimeRecipeBase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRecipes(recipes: List<MealTimeRecipeBase>)

    @Insert
    abstract fun insertAllDietTypes(dietTypes: List<DietType>)

    @Insert
    abstract fun saveDiets(dietTypes: List<DietType>)

    @Insert
    abstract fun insertDietsRecipesCrossRef(dietsCrossRef: RecipesDietsCrossRef)

    @Query("SELECT * FROM diet_type WHERE id = (:dietId)")
    abstract fun getDietWithRecipes(dietId: Long): DietWithRecipes

    fun saveRecipes(recipes: List<MealTimeRecipeBase>) {
        insertRecipes(recipes)

        recipes.forEach { recipe ->
            recipe.suitableForDiet.forEach { (id) ->
                insertDietsRecipesCrossRef(RecipesDietsCrossRef(recipe.recipeId, id))
            }
        }
    }
}