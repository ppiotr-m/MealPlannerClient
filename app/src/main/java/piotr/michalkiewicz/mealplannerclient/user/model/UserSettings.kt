package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

data class UserSettings(var nutritionProfileSettings: NutritionProfileSettings,
                        var eatenRecipes: List<String>,
                        var language: String,
                        var customizationDone: Boolean,
                        var sex: String,
                        var UserPreference: UserPreference) : Serializable {

        constructor(): this(NutritionProfileSettings(), emptyList(), "", false, "", UserPreference() )
}