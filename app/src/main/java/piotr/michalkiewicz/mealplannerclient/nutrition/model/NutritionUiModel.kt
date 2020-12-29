package piotr.michalkiewicz.mealplannerclient.nutrition.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverters
import piotr.michalkiewicz.mealplannerclient.recipes.database.Converters
import piotr.michalkiewicz.mealplannerclient.recipes.model.FoodNutrient
import piotr.michalkiewicz.mealplannerclient.user.model.NutritionProfileSettings
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class NutritionUiModel(
    val nutritionDailyData: NutritionDailyData,
    val nutritionProfileSettings: NutritionProfileSettings,
    @TypeConverters(Converters::class)
    val ageNutrientRecommendations: List<AgeNutrientRecommendations>
) {

    val nutrientsPercentages: Map<String, Int>

    init {
        // TODO Wyciągnij z ::ageNutrientRecommendations listy nutrition i sklej w jedną

        val combinedListOfNutrientRecommendations = LinkedList<FoodNutrientRecommendedIntake>()

        ageNutrientRecommendations.forEach {
            combinedListOfNutrientRecommendations.addAll(it.nutrientIntakeRecommendations)
        }

        nutrientsPercentages = calculateNutrientsPercentages(
            nutritionDailyData.dailyNutritionSummary,
            combinedListOfNutrientRecommendations
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

    class Creator(
        private val nutritionDailyData: NutritionDailyData,
        private val nutritionProfileSettings: NutritionProfileSettings
    ) {
        fun createNutritionUiModel(): NutritionUiModel {

            =
    }
}