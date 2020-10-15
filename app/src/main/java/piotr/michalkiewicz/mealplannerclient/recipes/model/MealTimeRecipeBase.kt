package piotr.michalkiewicz.mealplannerclient.recipes.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
open class MealTimeRecipeBase(@PrimaryKey
                              val id: String,
                              val name: String,
                              @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
                              val image: Bitmap)