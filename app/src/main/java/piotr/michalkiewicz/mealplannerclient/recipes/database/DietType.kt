package piotr.michalkiewicz.mealplannerclient.recipes.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diet_type")
data class DietType(
        @PrimaryKey
        val id: Long,
        val dietTypeName: String
)