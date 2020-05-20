package piotr.michalkiewicz.mealplannerclient.connection.auth

import io.reactivex.Observable
import piotr.michalkiewicz.mealplannerclient.connection.auth.model.Token
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Authorization {

    @FormUrlEncoded
    @POST("/auth/token")
    fun getToken(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("grant_type") grant_type: String
    ): Observable<Token>
}