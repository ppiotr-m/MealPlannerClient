package piotr.michalkiewicz.mealplannerclient.nutrition.model

import android.os.Build
import androidx.annotation.RequiresApi
import piotr.michalkiewicz.mealplannerclient.user.model.NutritionProfileSettings
import java.time.LocalDate

data class NutritionUiModel(
    val nutritionDailyData: NutritionDailyData,
    val nutritionProfileSettings: NutritionProfileSettings
) {
    val dailyTargets =
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