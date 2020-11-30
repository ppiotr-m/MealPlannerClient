package piotr.michalkiewicz.mealplannerclient.recipes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeItemRemoteKeys

@Dao
interface RecipesRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RecipeItemRemoteKeys>)

    @Query("SELECT * FROM RecipeItemRemoteKeys WHERE recipeId = :recipeId")
    fun remoteKeysByNewsId(recipeId: String): RecipeItemRemoteKeys

    @Query("DELETE FROM RecipeItemRemoteKeys")
    fun clearRemoteKeys()
}