package piotr.michalkiewicz.mealplannerclient.nutrition.model

import androidx.room.TypeConverters
import piotr.michalkiewicz.mealplannerclient.recipes.database.Converters

data class NutritionUiModel(
    val nutritionDailyData: NutritionDailyData,
    @TypeConverters(Converters::class)
    val foodNutrientRecommendations: List<FoodNutrientRecommendedIntake>,
    val nutrientsPercentages: Map<String, Int>
)