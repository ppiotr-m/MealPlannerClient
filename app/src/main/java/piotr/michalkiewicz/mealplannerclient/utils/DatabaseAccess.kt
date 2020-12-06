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
abstract class DatabaseAccess : RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
    abstract fun recipesRemoteKeysDao(): RecipesRemoteKeysDao
    abstract fun nutritionDao(): NutritionDao

    companion object {
        private const val dbName = "meal_time_local"
        private var instance: piotr.michalkiewicz.mealplannerclient.utils.DatabaseAccess? = null

        @Synchronized
        fun getInstance(context: Context): piotr.michalkiewicz.mealplannerclient.utils.DatabaseAccess {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseAccess::class.java,
                    dbName
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance as piotr.michalkiewicz.mealplannerclient.utils.DatabaseAccess
        }
    }
}