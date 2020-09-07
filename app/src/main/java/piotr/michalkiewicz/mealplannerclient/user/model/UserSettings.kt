package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

data class UserSettings(var nutritionProfileSettings: NutritionProfileSettings?,
                        var eatenRecipes: List<String>,
                        var language: String,
                        var location: String?,
                        var customizationDone: Boolean,
                        var sex: String?,
                        var userPreference: UserPreference) : Serializable {

        constructor() : this(NutritionProfileSettings(), emptyList(), "Not specified",
                "", false, "", UserPreference())
}