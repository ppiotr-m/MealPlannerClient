package piotr.michalkiewicz.mealplannerclient.recipes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeBase

@Database(entities = [MealTimeRecipeBase::class], version = 1, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
    abstract fun recipesRemoteKeysDao(): RecipesRemoteKeysDao

    companion object {
        private const val dbName = "recipes_database"
        private lateinit var instance: RecipesDatabase

        fun getInstance(context: Context): RecipesDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        RecipesDatabase::class.java,
                        dbName).fallbackToDestructiveMigration().build()
            }
            return instance
        }
    }
}