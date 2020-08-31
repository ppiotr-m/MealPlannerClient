package piotr.michalkiewicz.mealplannerclient.user.model

import piotr.michalkiewicz.mealplannerclient.user.model.enums.ActivityLevel
import piotr.michalkiewicz.mealplannerclient.user.model.enums.Sex
import java.io.Serializable

class NutritionProfileSettings : Serializable {
    var height: Int? = null
    var weight: Int? = null
    var age: Int = 0
    var goal: Int = 0
    var activityLevel: ActivityLevel? = null

    override fun toString(): String {
        return "Height: " + height + "\nWeight: " + weight
    }
}