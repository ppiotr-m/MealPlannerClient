package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility

import piotr.michalkiewicz.mealplannerclient.user.model.UserPreferences

object TestUserPreferences {

    private val userPreferences: UserPreferences = UserPreferences(
        listOf("dairy", "soup"),
        listOf("Peanuts", "Dairy"),
        "paleo",
        listOf("majeranek", "kiwi"),
        listOf("Italian", "Polish"),
        1,
        15,
        1
    )

    @JvmName("getUserPreferences1")
    fun getUserPreferences(): UserPreferences {
        return userPreferences.copy()
    }
}
