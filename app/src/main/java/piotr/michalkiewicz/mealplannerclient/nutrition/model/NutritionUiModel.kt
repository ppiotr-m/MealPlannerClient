package piotr.michalkiewicz.mealplannerclient.nutrition.model

import android.os.Build
import androidx.annotation.RequiresApi
import piotr.michalkiewicz.mealplannerclient.recipes.model.FoodNutrient
import piotr.michalkiewicz.mealplannerclient.user.model.NutritionProfileSettings
import java.time.LocalDate

class NutritionUiModel(
    val nutritionDailyData: NutritionDailyData,
    val nutritionProfileSettings: NutritionProfileSettings,
    val recommendedNutrients: List<AgeNutrientRecommendations>
) {

    val nutrientsPercentages: Map<String, Int>

    init {
        nutrientsPercentages = calculateNutrientsPercentages(
            nutritionDailyData.dailyNutritionSummary,
            recommendedNutrients,
            nutritionProfileSettings.age
        )
    }

    private fun calculateNutrientsPercentages(
        nutritionSummary: List<FoodNutrient>,
        recommendedNutrients: List<FoodNutrientRecommendedIntake>,
        age: Int
    ): Map<String, Int> {

        val nutrientPercentageMap = HashMap<String, Int>()

        nutritionSummary.forEach { nutrient ->
            recommendedNutrients.forEach { recommendedIntake ->

            }
        }

        return nutrientPercentageMap
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun getEmptyInstance(): NutritionUiModel {
            return NutritionUiModel(
                NutritionDailyData(
                    LocalDate.now().toString(), ArrayList()
                ), NutritionProfileSettings()
            )
        }
    }
}