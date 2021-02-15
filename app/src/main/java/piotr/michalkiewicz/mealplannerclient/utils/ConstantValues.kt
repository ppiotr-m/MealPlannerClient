package piotr.michalkiewicz.mealplannerclient.utils

import android.graphics.Color

class ConstantValues {
    companion object {
        //DEV cieniu local host
        const val BASE_URL = "http://192.168.1.10:8080"

        //DEV cieniu-work local host
        //const val BASE_URL = "http://10.0.0.6:8080"

        //Bartu localhost
//        const val BASE_URL = "http://192.168.0.39:8080"

        //Kolej localhost
//        const val BASE_URL = "http://10.0.2.2:8080/"
        const val PASSWORD_GRANT_TYPE = "password"
        const val REFRESH_TOKEN = "refresh_token"
        const val REFRESH_TOKEN_SHARED_PREF = "REFRESH_TOKEN"
        const val SHOPPING_LIST_SHARED_PREF = "SHOPPING_LIST"
        const val GRANT_TYPE = "grant_type"
        const val MY_PREFERENCE_NAME = "mealTime"
        const val REFRESH_TOKEN_GRANT_TYPE = "refresh_token"
        const val SETTINGS_DATA = "settings_data"
        const val TAG = "mealtime"
        const val RECIPE_ID = "recipe_id"
        const val INGREDIENTS_DATA = "ingredients"
        const val COOKING_STEPS_DATA = "cooking_steps"
        const val RECORD_AUDIO_REQUEST_CODE = 433

        const val CHECKED_BUTTON_COLOR = Color.LTGRAY
        const val DEFAULT_BUTTON_COLOR = Color.RED
        const val PAGE_SIZE = 5
        const val PREFETCH_DISTANCE = 5
        const val RECIPES_STARTING_PAGE_INDEX = 0
        const val DATE_FORMAT = "yyyy/MM/d"
    }
}