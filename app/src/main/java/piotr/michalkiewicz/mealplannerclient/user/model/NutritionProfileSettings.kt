package piotr.michalkiewicz.mealplannerclient.user.model

import piotr.michalkiewicz.mealplannerclient.user.model.enums.ActivityLevel
import piotr.michalkiewicz.mealplannerclient.user.model.enums.Sex

class NutritionProfileSettings {
    var sex: Sex? = null
    var height = 0
    var weight = 0
    var age = 0
    var goal = 0
    var activityLevel: ActivityLevel? = null
}