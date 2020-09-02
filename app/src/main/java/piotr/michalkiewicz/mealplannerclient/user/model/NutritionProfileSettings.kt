package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

class NutritionProfileSettings : Serializable {
    var height: Int? = null
    var weight: Int? = null
    var age: Int = 0
    var goal: Int = 0
    var activityLevel: piotr.michalkiewicz.mealplannerclient.user.model.enums.ActivityLevel? = null

    override fun toString(): String {
        return "Height: " + height + "\nWeight: " + weight
    }
}