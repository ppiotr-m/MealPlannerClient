package piotr.michalkiewicz.mealplannerclient.nutrition.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverters
import piotr.michalkiewicz.mealplannerclient.recipes.database.Converters
import piotr.michalkiewicz.mealplannerclient.recipes.model.FoodNutrient
import piotr.michalkiewicz.mealplannerclient.user.model.NutritionProfileSettings
import java.time.LocalDate

class NutritionUiModel(
    val nutritionDailyData: NutritionDailyData,
    val nutritionProfileSettings: NutritionProfileSettings,
    @TypeConverters(Converters::class)
    val recommendedNutrients: List<FoodNutrientRecommendedIntake>
) {

    val nutrientsPercentages: Map<String, Int>

    init {
        nutrientsPercentages = calculateNutrientsPercentages(
            nutritionDailyData.dailyNutritionSummary,
            recommendedNutrients
        )
    }

    private fun calculateNutrientsPercentages(
        nutritionSummary: List<FoodNutrient>,
        recommendedNutrients: List<FoodNutrientRecommendedIntake>
    ): Map<String, Int> {

        val nutrientPercentageMap = HashMap<String, Int>()

        nutritionSummary.forEach { nutrient ->
            recommendedNutrients.forEach { (name, amount) ->
                if (nutrient.nutrient.name == name) {
                    var recommendedIntakePercentage = 0F
                    if (nutrient.amount != 0F) {
                        recommendedIntakePercentage = (amount / nutrient.amount) * 100
                    }
                    nutrientPercentageMap[nutrient.nutrient.name] =
                        recommendedIntakePercentage.toInt()
                }
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
                ), NutritionProfileSettings(), ArrayList()
            )
        }
    }
}