package piotr.michalkiewicz.mealplannerclient.recipes.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeServiceApi {

    @GET("/recipes/service/diets")
    fun getAllDiets(): Call<List<String>>

    @GET("/recipes/service/recipeTypes")
    fun getAllRecipeTypes(): Call<List<String>>

    @GET("/recipes/service/cuisines")
    fun getAllCuisines(): Call<List<String>>

    @GET("/recipes/service/allergies")
    fun getAllAllergies(): Call<List<String>>

    @GET("/recipes/service/products")
    fun getAllProducts(): Call<List<String>>

    @GET("/recipes/service/productsByName")
    fun getAllProductsByName(@Query("name") name: String): Call<List<String>>
}