package piotr.michalkiewicz.mealplannerclient.connection.auth

import io.reactivex.Observable
import retrofit2.http.GET

interface TestApiService {

    @GET("/auth/ping")
    fun pingNoAuth(): Observable<String>

    @GET("/auth/ping/admin")
    fun pingAdmin(): Observable<String>

    @GET("/auth/ping/user")
    fun pingUser(): Observable<String>
}