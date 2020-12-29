package piotr.michalkiewicz.mealplannerclient.nutrition

import piotr.michalkiewicz.mealplannerclient.auth.AuthServiceGenerator
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.api.NutritionAPI

class NutritionServiceGenerator : AuthServiceGenerator() {

    //  TODO Change access
    lateinit var nutritionAPI: NutritionAPI

    init {
        if (!::nutritionAPI.isInitialized) {
            val retrofit = retrofitBuilder()
            nutritionAPI = retrofit.create(NutritionAPI::class.java)
        }
    }
}

