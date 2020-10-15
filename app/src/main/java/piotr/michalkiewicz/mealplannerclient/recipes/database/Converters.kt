package piotr.michalkiewicz.mealplannerclient.recipes.database

import android.graphics.Bitmap
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converters {

    private val imageQuality = 100

    @TypeConverter
    fun fromTimestamp(value: ByteArray?): Bitmap? {
        return null // value?.let { Bitmap. }
    }

    @TypeConverter
    fun dateToTimestamp(image: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()

        image.compress(Bitmap.CompressFormat.JPEG, imageQuality, byteArrayOutputStream)

        return byteArrayOutputStream.toByteArray()
    }
}