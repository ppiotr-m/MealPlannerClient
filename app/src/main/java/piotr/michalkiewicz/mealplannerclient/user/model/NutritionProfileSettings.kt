package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

data class NutritionProfileSettings(var height: Int?,
                                    var weight: Int?,
                                    var age: Int?,
                                    var goal: WeightGoal?,
                                    var activityLevel: String?) : Serializable {
    constructor() : this(null, null, null, null, null)
}


