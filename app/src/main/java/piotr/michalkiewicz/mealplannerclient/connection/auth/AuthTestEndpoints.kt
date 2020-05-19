package piotr.michalkiewicz.mealplannerclient.connection.auth

import io.reactivex.Observable
import retrofit2.http.GET

interface AuthTestEndpoints {

    @GET("/auth/ping")
    fun pingNoAuth(): Observable<String>
}