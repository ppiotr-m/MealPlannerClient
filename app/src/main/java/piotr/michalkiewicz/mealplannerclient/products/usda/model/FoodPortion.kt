package piotr.michalkiewicz.mealplannerclient.products.usda.model

data class FoodPortion(val modifier: String, val amount: Float, val gramWeight: Float) {
    override fun toString(): String {
        return modifier
    }
}