package piotr.michalkiewicz.mealplannerclient.nutrition.model

open class DailyEatenFoods(
    val date: String, // TODO Need to change it to LocalDate
    val eatenFoods: List<EatableItem>
)