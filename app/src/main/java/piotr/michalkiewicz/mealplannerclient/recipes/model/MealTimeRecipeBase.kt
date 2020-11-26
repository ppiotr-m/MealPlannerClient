package piotr.michalkiewicz.mealplannerclient.recipes.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import piotr.michalkiewicz.mealplannerclient.recipes.database.Converters

@Entity(tableName = "recipes")
open class MealTimeRecipeBase(
        @PrimaryKey
        val id: String,
        val name: String,
        @TypeConverters(Converters::class)
        val suitableForDiet: List<Diet>,
        @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
        val image: Bitmap
)