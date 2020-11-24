package piotr.michalkiewicz.mealplannerclient.recipes.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import piotr.michalkiewicz.mealplannerclient.recipes.model.Diet
import java.io.ByteArrayOutputStream

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
}