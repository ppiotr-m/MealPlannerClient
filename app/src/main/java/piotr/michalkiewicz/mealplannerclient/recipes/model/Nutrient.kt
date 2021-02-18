package piotr.michalkiewicz.mealplannerclient.recipes.model

data class Nutrient(
    val mongoId: String,
    val id: Int,
    val number: String,
    val name: String,
    val rank: Int,
    val unitName: String
) {
    override fun toString(): String {
        return "Nutrient(mongoId='$mongoId', id=$id, number='$number', name='$name', rank=$rank, unitName='$unitName')"
    }
}