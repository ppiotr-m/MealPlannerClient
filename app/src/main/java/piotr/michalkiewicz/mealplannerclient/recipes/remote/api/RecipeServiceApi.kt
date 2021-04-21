package piotr.michalkiewicz.mealplannerclient.recipes.remote.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeServiceApi {

    @GET("/recipes/service/diets")
    suspend fun getAllDiets(): Response<List<String>>

    @GET("/recipes/service/recipeTypes")
    suspend fun getAllRecipeTypes(): Response<List<String>>

    @GET("/recipes/service/cuisines")
    suspend fun getAllCuisines(): Response<List<String>>

    @GET("/recipes/service/allergies")
    suspend fun getAllAllergies(): Response<List<String>>

    @GET("/recipes/service/products")
    suspend fun getAllProducts(): Response<List<String>>

    @GET("/recipes/service/productsByName")
    fun getAllProductsByName(@Query("name") name: String): Call<List<String>>
}