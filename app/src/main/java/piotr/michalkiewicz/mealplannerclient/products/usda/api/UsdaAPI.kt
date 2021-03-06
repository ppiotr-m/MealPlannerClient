package piotr.michalkiewicz.mealplannerclient.products.usda.api

import piotr.michalkiewicz.mealplannerclient.products.usda.model.UsdaFoodItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsdaAPI {

    @GET("{full_url}")
    suspend fun getProductDetailData(
        @Path(
            value = "full_url",
            encoded = true
        ) fullUrl: String,
        @Query("api_key") apiKey: String
    ): Response<UsdaFoodItem>
}