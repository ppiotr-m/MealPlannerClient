package piotr.michalkiewicz.mealplannerclient.utils

class ConstantValues {
    companion object {
        //DEV cieniu local host
//        @JvmStatic
//        val BASE_URL = "http://192.168.1.10:8080"

        //Kolej localhost
        @JvmStatic
        val BASE_URL = "http://10.0.2.2:8080/"

        @JvmStatic
        val PASSWORD_GRANT_TYPE = "password"

        @JvmStatic
        val REFRESH_TOKEN_GRANT_TYPE = "refresh_token"

        const val MY_PREFERENCE_NAME = "mealTime"
    }
}