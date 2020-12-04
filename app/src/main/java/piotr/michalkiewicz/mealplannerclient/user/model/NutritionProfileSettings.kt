package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

data class NutritionProfileSettings( // TODO Bez nulli, jesli ktos nie ustawi tych ustawien to caly obiekt ma byc nullem
    var height: Int?,
    var weight: Int?,
    var age: Int?,
    var goal: WeightGoal?,
    var activityLevel: String?
) : Serializable {
    constructor() : this(null, null, null, null, null)
}


