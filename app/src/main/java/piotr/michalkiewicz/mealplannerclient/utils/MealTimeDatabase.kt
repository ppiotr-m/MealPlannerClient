package piotr.michalkiewicz.mealplannerclient.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import piotr.michalkiewicz.mealplannerclient.nutrition.local.NutritionDao
import piotr.michalkiewicz.mealplannerclient.nutrition.model.AgeNutrientRecommendations
import piotr.michalkiewicz.mealplannerclient.recipes.database.Converters
import piotr.michalkiewicz.mealplannerclient.recipes.database.RecipesDao
import piotr.michalkiewicz.mealplannerclient.recipes.database.RecipesRemoteKeysDao
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeItemRemoteKeys

@Database(
    entities = [MealTimeRecipe::class, RecipeItemRemoteKeys::class, AgeNutrientRecommendations::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MealTimeDatabase : RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
    abstract fun recipesRemoteKeysDao(): RecipesRemoteKeysDao
    abstract fun nutritionDao(): NutritionDao

    companion object {
        private const val dbName = "meal_time_local"
        private var instance: MealTimeDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MealTimeDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealTimeDatabase::class.java,
                    dbName
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance as MealTimeDatabase
        }
    }
}