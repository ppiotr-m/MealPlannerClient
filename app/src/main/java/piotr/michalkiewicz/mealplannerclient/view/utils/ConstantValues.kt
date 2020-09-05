package piotr.michalkiewicz.mealplannerclient.view.utils

class ConstantValues {

    //if you add new button do it on backend too

    companion object {
        val RECIPE_TYPE_CUSTOMIZATION_BUTTONS = mutableListOf("Soup", "Fish", "Pasta", "Grill")
        val ALLERGIES_CUSTOMIZATION_BUTTONS = mutableListOf("Gluten", "Dairy", "Peanuts", "Tree nuts", "Soy", "Eggs", "Frutti di mare", "Sesame")
        val DIETS_CUSTOMIZATION_BUTTONS = mutableListOf("Standard", "Vegetarian", "Paleo", "Flexitarian", "Pescatarian", "Vegan", "Low carb", "Ketogenic")
        val DIS_LIKE_INGREDIENTS_CUSTOMIZATION_BUTTONS = mutableListOf("produkt1", "produkt2", "produkt3", "produkt4", "produkt5", "produkt6") // toDo from DB
        val CUISINE_CUSTOMIZATION_BUTTONS = mutableListOf("Polish", "Mexican", "Italian")
        val BASIC_PORTION_CUSTOMIZATION_BUTTONS = mutableListOf("1", "2", "3", "4")
        val BASIC_COOKING_TIME_CUSTOMIZATION_BUTTONS = mutableListOf("15", "30", "45", "60")
        val BASIC_MEALS_PER_CUSTOMIZATION_BUTTONS = mutableListOf("1", "2", "3", "4", "5")
    }
}