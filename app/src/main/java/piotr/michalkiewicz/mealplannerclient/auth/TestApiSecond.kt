package piotr.michalkiewicz.mealplannerclient.auth

import io.reactivex.Observable
import retrofit2.http.GET

interface TestApiSecond {

    @GET("/auth/ping")
    fun pingNoAuth(): Observable<String>

}