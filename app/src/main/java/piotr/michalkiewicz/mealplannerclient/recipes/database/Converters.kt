package piotr.michalkiewicz.mealplannerclient.recipes.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import piotr.michalkiewicz.mealplannerclient.nutrition.model.AgeNutrientRecommendations
import piotr.michalkiewicz.mealplannerclient.nutrition.model.FoodNutrientRecommendedIntake
import piotr.michalkiewicz.mealplannerclient.nutrition.model.enums.Sex
import piotr.michalkiewicz.mealplannerclient.recipes.model.Comment
import piotr.michalkiewicz.mealplannerclient.recipes.model.FoodNutrient
import piotr.michalkiewicz.mealplannerclient.recipes.model.InstructionStep
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.Diet
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Converters {

    private val imageQuality = 100

    @TypeConverter
    fun bitmapFromByteArray(value: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(value, 0, value.size)
    }

    @TypeConverter
    fun bitmapToByteArray(image: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, imageQuality, byteArrayOutputStream)

        return byteArrayOutputStream.toByteArray()
    }

    @TypeConverter
    fun sexEnumFromString(value: String): Sex {
        return Sex.valueOf(value)
    }

    @TypeConverter
    fun sexEnumToString(sex: Sex): String {
        return sex.value
    }

    @TypeConverter
    fun fromJsonToDietTypeArray(listElementsString: String): List<Diet> {
        return Gson().fromJson(listElementsString, ArrayList<Diet>().javaClass)
    }

    @TypeConverter
    fun listToDietTypeJsonString(list: List<Diet>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToAgeNutrientRecommendations(jsonString: String): List<AgeNutrientRecommendations> {
        return Gson().fromJson(jsonString, ArrayList<AgeNutrientRecommendations>().javaClass)
    }

    @TypeConverter
    fun listToNutrientsRecommendationsJsonString(list: List<AgeNutrientRecommendations>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToNutrientsRecommendedIntake(jsonString: String): List<FoodNutrientRecommendedIntake> {
        return Gson().fromJson(jsonString, ArrayList<FoodNutrientRecommendedIntake>().javaClass)
    }

    @TypeConverter
    fun listToNutrientsRecommendedIntakeJsonString(list: List<FoodNutrientRecommendedIntake>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToStringTypeArray(listElementsString: String): List<String> {
        return Gson().fromJson(listElementsString, ArrayList<String>().javaClass)
    }

    @TypeConverter
    fun stringListToJsonString(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToRecipeIngredientTypeArray(listElementsString: String): List<RecipeIngredient> {
        return Gson().fromJson(listElementsString, ArrayList<RecipeIngredient>().javaClass)
    }

    @TypeConverter
    fun recipeIngredientListToJsonString(list: List<RecipeIngredient>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToFoodNutrientTypeArray(listElementsString: String?): List<FoodNutrient>? {
        return Gson().fromJson(listElementsString, ArrayList<FoodNutrient>().javaClass)
    }

    @TypeConverter
    fun foodNutrientListToJsonString(list: List<FoodNutrient>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToInstructionStepTypeArray(listElementsString: String): List<InstructionStep> {
        return Gson().fromJson(listElementsString, ArrayList<InstructionStep>().javaClass)
    }

    @TypeConverter
    fun instructionStepListToJsonString(list: List<InstructionStep>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToCommentTypeArray(listElementsString: String?): List<Comment> {
        if (listElementsString == null) {
            return ArrayList()
        }
        return Gson().fromJson(listElementsString, ArrayList<Comment>().javaClass)
    }

    @TypeConverter
    fun commentListToJsonString(list: List<Comment>?): String {
        // TODO Should null be allowed? Not only an empty list? Check if data passes correctly
        if (list == null) {
            return "[]"
        }

        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromJsonToDateType(dateString: String?): Date {
        if (dateString == null) {
            return SimpleDateFormat("yyyy-MM-dd").parse("2012-07-14")
        }

        return Gson().fromJson(dateString, Date::class.java)
            ?: SimpleDateFormat("yyyy-MM-dd").parse("2012-07-14")
    }

    @TypeConverter
    fun dateToJsonString(date: Date?): String {
        if (date == null) {
            return ""
        }
        return Gson().toJson(date)
    }
}