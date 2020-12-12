package piotr.michalkiewicz.mealplannerclient.nutrition.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import piotr.michalkiewicz.mealplannerclient.nutrition.model.AgeNutrientRecommendations

@Dao
interface NutritionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendations(recommendations: List<AgeNutrientRecommendations>)

    @Query("SELECT * FROM nutrition_recommendation WHERE minAge <= :age AND maxAge >= :age")
    suspend fun selectRecommendationsForAge(age: Int): List<AgeNutrientRecommendations>
}