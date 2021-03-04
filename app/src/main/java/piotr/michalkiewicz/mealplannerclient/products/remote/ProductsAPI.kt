package piotr.michalkiewicz.mealplannerclient.products.remote

import piotr.michalkiewicz.mealplannerclient.products.model.BasicFoodItemData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsAPI {

    @GET("/products/survey/find/name")
    suspend fun getProductsForName(@Query("description") description: String): Response<List<BasicFoodItemData>>
}