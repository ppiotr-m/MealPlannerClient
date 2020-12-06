package piotr.michalkiewicz.mealplannerclient.nutrition.model

import androidx.room.Entity
import piotr.michalkiewicz.mealplannerclient.nutrition.model.enums.Sex

// TODO  In Initial JSON, there is max age set to 130, must correspond to the rest of app

@Entity(tableName = "nutrition_recommendation")
data class AgeNutrientRecommendations(
    val id: Int,
    val minAge: Int,
    val maxAge: Int,
    val sex: Sex,
    val nutrientIntakeRecommendations: List<FoodNutrientRecommendedIntake>
)