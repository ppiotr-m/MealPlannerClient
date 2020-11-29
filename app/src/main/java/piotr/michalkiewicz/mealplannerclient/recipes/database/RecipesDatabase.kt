package piotr.michalkiewicz.mealplannerclient.recipes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeItemRemoteKeys

@Database(
    entities = [MealTimeRecipe::class, RecipeItemRemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
    abstract fun recipesRemoteKeysDao(): RecipesRemoteKeysDao

    companion object {
        private const val dbName = "recipes_database"
        private var instance: RecipesDatabase? = null

        @Synchronized
        fun getInstance(context: Context): RecipesDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipesDatabase::class.java,
                    dbName
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance as RecipesDatabase
        }
    }
}