package piotr.michalkiewicz.mealplannerclient.utils

class ConstantValues {
    companion object {
        //DEV local host
        @JvmStatic val BASE_URL = "http://192.168.1.10:8080"
        @JvmStatic val PASSWORD_GRANT_TYPE = "password"
        @JvmStatic val REFRESH_TOKEN_GRANT_TYPE = "refresh_token"
        @JvmStatic val SETTINGS_DATA = "settings_data"
        @JvmStatic val TAG = "MealPlanner"
    }
}