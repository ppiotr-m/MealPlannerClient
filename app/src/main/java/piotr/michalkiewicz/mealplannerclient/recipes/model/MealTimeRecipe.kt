package piotr.michalkiewicz.mealplannerclient.recipes.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import piotr.michalkiewicz.mealplannerclient.recipes.database.Converters
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.Diet
import java.util.*

@Entity(tableName = "recipes")
open class MealTimeRecipe(
        @PrimaryKey
        val id: String,

        val name: String,

        @TypeConverters(Converters::class)
        val suitableForDiet: List<Diet>,

        @TypeConverters(Converters::class)
        @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
        val image: Bitmap,

        @TypeConverters(Converters::class)
        @SerializedName("recipeType")
        val recipeType: List<String>,

        @TypeConverters(Converters::class)
        @SerializedName("recipeIngredients")
        val recipeIngredients: List<RecipeIngredient>,

        @TypeConverters(Converters::class)
        @SerializedName("instructionSteps")
        val instructionSteps: List<InstructionStep>,

        @SerializedName("level")
        val level: String,

        @SerializedName("recipeCuisines")
        val recipeCuisines: List<String>,

        @SerializedName("description")
        val description: String,

        @SerializedName("recipeYield")
        val recipeYield: String?,

        @SerializedName("from")
        val from: String,

        @SerializedName("madeBY")
        val madeBY: String?,

        @SerializedName("language")
        val language: String?,

        @SerializedName("recipeTag")
        val recipeTag: List<String>,

        @TypeConverters(Converters::class)
        @SerializedName("comments")
        val comments: List<Comment>?,

        @SerializedName("dateAdded")
        val dateAdded: Date?,

        @TypeConverters(Converters::class)
        @SerializedName("dateEdited")
        val dateEdited: Date?,

        @SerializedName("cookTime")
        val cookTime: Int = 0,

        @SerializedName("totalRating")
        val totalRating: Double = 0.0,

        @SerializedName("views")
        val views: Long = 0,

        @SerializedName("totalLikes")
        val totalLikes: Long = 0
)