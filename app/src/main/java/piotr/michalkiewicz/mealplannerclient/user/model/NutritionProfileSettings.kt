package piotr.michalkiewicz.mealplannerclient.user.model

import piotr.michalkiewicz.mealplannerclient.user.model.enums.ActivityLevel
import piotr.michalkiewicz.mealplannerclient.user.model.enums.Sex
import java.io.Serializable

class NutritionProfileSettings : Serializable {
    var height: Int = 0
    var weight: Int = 0
    var age: Int = 0
    var goal: Int = 0
    var activityLevel: ActivityLevel? = null
}