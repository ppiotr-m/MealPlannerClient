package piotr.michalkiewicz.mealplannerclient.recipes.model

import java.io.Serializable
import java.util.*

data class RecipeIngredient(
    val amount: String,
    val unit: String,
    val originalName: String,
    val kind: String,
    val name: String
) : Serializable {
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as RecipeIngredient
        return name == that.name
    }

    override fun hashCode(): Int {
        return Objects.hash(name)
    }
}