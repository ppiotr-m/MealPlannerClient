package piotr.michalkiewicz.mealplannerclient.view.personalization.service

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreference

class PersonalizationViewModel : ViewModel() {

    val userPreference: MutableLiveData<UserPreference> = MutableLiveData()

    fun initUserPreference() {
        if (userPreference.value == null) {
            userPreference.value = PersonalizationService().fetchUserPreferences()
        }
    }

    fun clearUserPreference() {
        userPreference.value = null
    }

    fun getUserPreference(): UserPreference? {
        return userPreference.value
    }

    fun getRecipeTypeSettings(): List<String>? {
        return userPreference.value?.recipeTypeSettings
    }

    fun getAllergies(): List<String>? {
        return userPreference.value?.allergies
    }

    fun getDiet(): String? {
        return userPreference.value?.diet
    }

    fun getUnLikeIngredients(): List<String>? {
        return userPreference.value?.unlikeIngredients
    }

    fun getCuisine(): List<String>? {
        return userPreference.value?.cuisine
    }

    fun getPortionPreferences(): Int? {
        return userPreference.value?.portionPreferences
    }

    fun getCookingTimePreferences(): Int? {
        return userPreference.value?.cookingTimePreference
    }

    fun getMealsPerPlanPreferences(): Int? {
        return userPreference.value?.mealsPerMealPlanPreference
    }

    fun setRecipeTypeSettings(recipeTypeSettings: List<String>) {
        userPreference.value?.recipeTypeSettings = recipeTypeSettings
    }

    fun setAllergies(allergies: List<String>) {
        userPreference.value?.allergies = allergies
    }

    fun setDiet(diet: String) {
        userPreference.value?.diet = diet
    }

    fun setUnLikeIngredients(unlikeIngredients: List<String>) {
        userPreference.value?.unlikeIngredients = unlikeIngredients
    }

    fun setCuisine(cuisines: List<String>) {
        userPreference.value?.cuisine = cuisines
    }

    fun setPortionPreferences(portionPreferences: Int) {
        userPreference.value?.portionPreferences = portionPreferences
    }

    fun setCookingTimePreferences(cookingTimePreferences: Int) {
        userPreference.value?.cookingTimePreference = cookingTimePreferences
    }

    fun setMealsPerPlanPreferences(mealsPerPlanPreferences: Int) {
        userPreference.value?.mealsPerMealPlanPreference = mealsPerPlanPreferences
    }
}