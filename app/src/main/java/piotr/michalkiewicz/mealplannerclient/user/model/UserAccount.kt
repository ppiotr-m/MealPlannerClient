package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

class UserAccount : Serializable {

    var email: String? = null
    var username: String? = null
    var password: String? = null
    var location: String? = "Poland"
    var userSettings: UserSettings? = null

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
        fun createMockUserAccountWithParams(email: String?, password: String?, username: String?): UserAccount{
            val account = UserAccount()
            account.username = username
            account.email = email
            account.password = password
    //        account.userSettings = createMockSettings()
            return account
        }
        @JvmStatic
        fun createMockSettings(): UserSettings{
            val settings = UserSettings()
   //         settings.allergies = listOf("Nuts", "Soy")
            settings.cookingTimePreference = 60
            settings.mealsPerMealPlanPreference = 5
            settings.portionPreferences =4
            val nutritionProfileSettings = NutritionProfileSettings()
            nutritionProfileSettings.height = 178
            nutritionProfileSettings.weight = 75
            settings.nutritionProfileSettings = nutritionProfileSettings

            return settings
        }
    }
}