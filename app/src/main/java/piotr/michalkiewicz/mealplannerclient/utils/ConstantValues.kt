package piotr.michalkiewicz.mealplannerclient.utils

class ConstantValues {
    companion object {
        //DEV cieniu local host
//      const val BASE_URL = "http://192.168.1.10:8080"

        //Kolej localhost
        const val BASE_URL = "http://10.0.2.2:8080/"
        const val PASSWORD_GRANT_TYPE = "password"
        const val REFRESH_TOKEN = "refresh_token"
        const val REFRESH_TOKEN_SHARED_PREF = "REFRESH_TOKEN"
        const val GRANT_TYPE = "grant_type"
        const val MY_PREFERENCE_NAME = "mealTime"
        @JvmStatic
        val REFRESH_TOKEN_GRANT_TYPE = "refresh_token"
        @JvmStatic
        val SETTINGS_DATA = "settings_data"
        @JvmStatic
        val TAG = "MealPlanner"
    }
}