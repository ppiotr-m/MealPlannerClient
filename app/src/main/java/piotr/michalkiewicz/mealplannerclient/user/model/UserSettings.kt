package piotr.michalkiewicz.mealplannerclient.user.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class UserSettings: Serializable {
  var nutritionProfileSettings: NutritionProfileSettings? = null
  var allergies: List<String>? = LinkedList()
  var unlikeIngredients: List<String>? = LinkedList()
  var diet: String? = "Standard"
  var language: String? = null
  var sex: String? = "Male"

  override fun toString(): String {
    return "Diet: " + diet + "\nLanguage: " + language + "\nSex: " + sex +
            "\n" + nutritionProfileSettings.toString() + "\n" + "Allergies: " + allergies.toString() +
            "\nDisliked ingredients: " + unlikeIngredients.toString()
  }
}