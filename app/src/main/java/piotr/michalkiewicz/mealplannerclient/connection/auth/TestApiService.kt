package piotr.michalkiewicz.mealplannerclient.connection.auth

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TestApiService {

    @GET("/auth/ping/admin")
    fun pingAdmin(): Observable<String>

    @GET("/auth/ping/user")
    fun pingUser(): Observable<String>

    @GET("/recipes/random")
    fun getRandom(@Query("amount")amount: Number): Observable<Any>
}