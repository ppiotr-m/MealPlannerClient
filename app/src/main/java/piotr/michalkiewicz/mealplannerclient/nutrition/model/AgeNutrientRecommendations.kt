package piotr.michalkiewicz.mealplannerclient.nutrition.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import piotr.michalkiewicz.mealplannerclient.nutrition.model.enums.Sex
import piotr.michalkiewicz.mealplannerclient.recipes.database.Converters

// TODO  In Initial JSON, there is max age set to 130, must correspond to the rest of app

@Entity(tableName = "nutrition_recommendation")
data class AgeNutrientRecommendations(
    @PrimaryKey
    val id: Int,
    val minAge: Int,
    val maxAge: Int,
    @TypeConverters(Converters::class)
    val sex: Sex,
    @TypeConverters(Converters::class)
    val nutrientIntakeRecommendations: List<FoodNutrientRecommendedIntake>
)