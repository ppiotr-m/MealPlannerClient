package piotr.michalkiewicz.mealplannerclient.nutrition.model

import piotr.michalkiewicz.mealplannerclient.user.model.NutritionProfileSettings

data class NutritionUiModel(
    val nutritionDailyData: NutritionDailyData,
    val nutritionProfileSettings: NutritionProfileSettings
)