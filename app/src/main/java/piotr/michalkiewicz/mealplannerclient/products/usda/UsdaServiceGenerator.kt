package piotr.michalkiewicz.mealplannerclient.products.usda

import piotr.michalkiewicz.mealplannerclient.auth.AuthServiceGenerator
import piotr.michalkiewicz.mealplannerclient.products.usda.api.UsdaAPI

class UsdaServiceGenerator : AuthServiceGenerator() {

    //  TODO Change access
    lateinit var usdaAPI: UsdaAPI

    init {
        if (!::usdaAPI.isInitialized) {
            val retrofit = retrofitBuilder(usdaUrl)
            usdaAPI = retrofit.create(UsdaAPI::class.java)
        }
    }

    companion object {
        const val USDA_API_KEY = "MA0iCV6SnlsKquutw13UwlJcANx0LRLVpgihW9hj"
        val usdaUrl = "https://api.nal.usda.gov/fdc/v1/food/"

        fun getFullUrlForProductId(fdcId: Int): String {
            val stringBuilder = StringBuilder()
            val result = stringBuilder.append(usdaUrl).append(fdcId)
                .toString()
            return result
        }
    }
}