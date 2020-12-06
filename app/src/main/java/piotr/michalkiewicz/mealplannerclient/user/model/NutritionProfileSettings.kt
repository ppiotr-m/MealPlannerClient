package piotr.michalkiewicz.mealplannerclient.user.model

data class NutritionProfileSettings( // TODO Bez nulli, jesli ktos nie ustawi tych ustawien to caly obiekt ma byc nullem
    val height: Int,
    val weight: Int,
    val age: Int,
    val goal: WeightGoal,
    val activityLevel: String
) {
    constructor() : this(
        -1,
        -1,
        -1,
        WeightGoal(80f, false),
        "Sedentary"
    )
}


