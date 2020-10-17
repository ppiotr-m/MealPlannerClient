package piotr.michalkiewicz.mealplannerclient.recipes.model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import piotr.michalkiewicz.mealplannerclient.recipes.database.DietType
import java.util.*

class MealTimeRecipe(id: Long, recipeId: String, name: String, image: Bitmap, suitableForDiet: List<DietType>) :
        MealTimeRecipeBase(id, recipeId, name, suitableForDiet, image) {

    @SerializedName("recipeType")
    lateinit var recipeType: List<String>

    @SerializedName("recipeIngredients")
    lateinit var recipeIngredients: List<RecipeIngredient>

    @SerializedName("instructionSteps")
    lateinit var instructionSteps: List<InstructionStep>

    @SerializedName("level")
    lateinit var level: String

    @SerializedName("recipeCuisines")
    lateinit var recipeCuisines: List<String>

    @SerializedName("description")
    lateinit var description: String

    @SerializedName("recipeYield")
    lateinit var recipeYield: String

    @SerializedName("from")
    lateinit var from: String

    @SerializedName("madeBY")
    lateinit var madeBY: String

    @SerializedName("language")
    lateinit var language: String

    @SerializedName("recipeTag")
    lateinit var recipeTag: List<String>

    @SerializedName("comments")
    lateinit var comments: List<Comment>

    @SerializedName("dateAdded")
    lateinit var dateAdded: Date

    @SerializedName("dateEdited")
    lateinit var dateEdited: Date

    @SerializedName("cookTime")
    var cookTime: Int = 0

    @SerializedName("totalRating")
    var totalRating: Double = 0.0

    @SerializedName("views")
    var views: Long = 0

    @SerializedName("totalLikes")
    var totalLikes: Long = 0
}