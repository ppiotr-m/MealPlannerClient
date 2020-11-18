package piotr.michalkiewicz.mealplannerclient.user.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserSettings(@SerializedName("nutritionProfileSettings") var nutritionProfileSettings: NutritionProfileSettings,
                        @SerializedName("eatenRecipes") var eatenRecipes: List<String>,
                        @SerializedName("language") var language: String,
                        @SerializedName("location") var location: String?,
                        @SerializedName("customizationDone") var customizationDone: Boolean,
                        @SerializedName("sex") var sex: String?,
                        @SerializedName("userPreference") val userPreference: UserPreference) : Serializable {

    constructor() : this(NutritionProfileSettings(), emptyList(), "Not specified",
            "", false, "", UserPreference())
}