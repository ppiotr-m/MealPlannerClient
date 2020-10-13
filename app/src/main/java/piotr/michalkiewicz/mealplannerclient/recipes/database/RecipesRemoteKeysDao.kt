package piotr.michalkiewicz.mealplannerclient.recipes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipesRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RecipesRemoteKeys>)

    @Query("SELECT * FROM recipes_remote_keys WHERE recipeId = :recipeId")
    suspend fun remoteKeysRepoId(recipeId: String): RecipesRemoteKeys?

    @Query("DELETE FROM recipes_remote_keys")
    suspend fun clearRemoteKeys()
}