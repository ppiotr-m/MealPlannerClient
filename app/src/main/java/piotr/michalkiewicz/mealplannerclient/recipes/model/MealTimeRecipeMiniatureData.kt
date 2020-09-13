package piotr.michalkiewicz.mealplannerclient.recipes.model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class MealTimeRecipeMiniatureData(

        @SerializedName("id")
        val id: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("image")
        val image: Bitmap
)