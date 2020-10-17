package piotr.michalkiewicz.mealplannerclient.recipes.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import java.io.ByteArrayOutputStream

class Converters {

    private val imageQuality = 100

    @TypeConverter
    fun fromByteArray(value: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(value, 0, value.size)
    }

    @TypeConverter
    fun bitmapToByteArray(image: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()

        image.compress(Bitmap.CompressFormat.JPEG, imageQuality, byteArrayOutputStream)

        return byteArrayOutputStream.toByteArray()
    }

    @TypeConverter
    fun fromJsonToDietTypeArray(dietTypesList: String): List<DietType> {
        return Gson().fromJson(dietTypesList, ArrayList<DietType>().javaClass)
    }

    @TypeConverter
    fun listToDietTypeJsonString(dietTypesList: List<DietType>): String {
        return Gson().toJson(dietTypesList)
    }
}