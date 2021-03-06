package piotr.michalkiewicz.mealplannerclient.auth.api

import io.reactivex.Observable
import piotr.michalkiewicz.mealplannerclient.auth.model.Token
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OAuth2API {

    @FormUrlEncoded
    @POST("/auth/token")
    fun login(@Field("username") username: String,
              @Field("password") password: String,
              @Field("grant_type") grant_type: String
    ): Observable<Token>

    @FormUrlEncoded
    @POST("/auth/token")
    fun refreshToken(@Field("grant_type") grant_type: String,
                     @Field("refresh_token") refresh_token: String
    ): Call<Token>
}