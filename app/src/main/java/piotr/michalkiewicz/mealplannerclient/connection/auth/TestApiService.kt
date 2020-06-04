package piotr.michalkiewicz.mealplannerclient.connection.auth

import io.reactivex.Observable
import piotr.michalkiewicz.mealplannerclient.model.MealTimeRecipe
import retrofit2.http.GET

interface TestApiService {

    @GET("/auth/ping/admin")
    fun pingAdmin(): Observable<String>

    @GET("/auth/ping/user")
    fun pingUser(): Observable<String>

    @GET("/recipes/random")
    fun getRandom(): Observable<MealTimeRecipe>
}