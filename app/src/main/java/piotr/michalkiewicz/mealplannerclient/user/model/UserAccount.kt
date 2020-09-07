package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

class UserAccount : Serializable {

    lateinit var email: String
    lateinit var username: String
    lateinit var password: String
    lateinit var userSettings: UserSettings
    lateinit var eatenRecipes: List<String>

    companion object {

        @JvmStatic
        fun createMockUserAccount(): UserAccount {
            val account = UserAccount()
            account.email = "fajny_email@gmail.com"
            account.password = "aaa"
            account.userSettings = createMockSettings()
            return account
        }

        @JvmStatic
        fun createUserAccount(email: String, password: String, username: String): UserAccount {
            val account = UserAccount()
            account.username = username
            account.email = email
            account.password = password
            //        account.userSettings = createMockSettings()
            return account
        }

        @JvmStatic
        fun createMockSettings(): UserSettings {
            val settings = UserSettings()
            val userPreference = UserPreference()
            userPreference.allergies = listOf("Nuts", "Soy")
            userPreference.cookingTimePreference = 60
            userPreference.mealsPerMealPlanPreference = 5
            userPreference.portionPreferences =4
            val nutritionProfileSettings = NutritionProfileSettings()
            nutritionProfileSettings.height = 178
            nutritionProfileSettings.weight = 75
            settings.nutritionProfileSettings = nutritionProfileSettings

            return settings
        }
    }
}