package piotr.michalkiewicz.mealplannerclient.recipes.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import piotr.michalkiewicz.mealplannerclient.recipes.model.Comment
import piotr.michalkiewicz.mealplannerclient.recipes.model.InstructionStep
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
    fun fromJsonToDietTypeArray(listElementsString: String): List<Diet> {
        return Gson().fromJson(listElementsString, ArrayList<Diet>().javaClass)
    }

    @TypeConverter
    fun listToDietTypeJsonString(list: List<Diet>): String {
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
    fun fromJsonToRecipeIngredientTypeArray(listElementsString: String): List<RecipeIngredient2> {
        return Gson().fromJson(listElementsString, ArrayList<RecipeIngredient2>().javaClass)
    }

    @TypeConverter
    fun recipeIngredientListToJsonString(list: List<RecipeIngredient2>): String {
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