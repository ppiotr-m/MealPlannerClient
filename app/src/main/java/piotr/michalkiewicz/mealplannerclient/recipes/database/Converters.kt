package piotr.michalkiewicz.mealplannerclient.recipes.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import piotr.michalkiewicz.mealplannerclient.recipes.model.Diet
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
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
        Log.d(TAG, "Converting, list size: " + list.size + " first element: " + list[0])

        return Gson().toJson(list)
    }
}