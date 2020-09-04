package piotr.michalkiewicz.mealplannerclient.user.service_generator

import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import retrofit2.Call
import retrofit2.http.*

interface SignUpService {

    @POST("/user/singUp")
    @FormUrlEncoded
    fun signUp(@Field("username") username: String, @Field("email") email: String,
               @Field("password") password: String,
               @Field("grant_type") grantType: String):
            Call<UserAccount?>?
}