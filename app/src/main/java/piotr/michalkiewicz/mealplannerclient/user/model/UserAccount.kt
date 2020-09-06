package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

class UserAccount : Serializable {

    var email: String? = null
    var username: String? = null
    var password: String? = null
    var userSettings: UserSettings? = null
    var eatenRecipes: List<String>? = null

    companion object{

        @JvmStatic
        fun createMockUserAccount(): UserAccount{
            val account = UserAccount()
            account.email = "fajny_email@gmail.com"
            account.password = "aaa"
            account.userSettings = createMockSettings()
            return account
        }

        @JvmStatic
        fun createUserAccount(email: String?, password: String?, username: String?): UserAccount {
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