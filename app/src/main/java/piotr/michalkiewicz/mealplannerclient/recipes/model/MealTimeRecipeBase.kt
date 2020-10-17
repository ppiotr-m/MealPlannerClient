package piotr.michalkiewicz.mealplannerclient.recipes.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import piotr.michalkiewicz.mealplannerclient.recipes.database.Converters
import piotr.michalkiewicz.mealplannerclient.recipes.database.DietType

@Entity(tableName = "recipes")
open class MealTimeRecipeBase(@PrimaryKey
                              val id: Long,
                              val recipeId: String,
                              val name: String,
                              @TypeConverters(Converters::class)
                              val suitableForDiet: List<DietType>,
                              @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
                              val image: Bitmap)