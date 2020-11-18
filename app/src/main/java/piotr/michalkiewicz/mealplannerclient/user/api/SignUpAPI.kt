package piotr.michalkiewicz.mealplannerclient.user.api

import piotr.michalkiewicz.mealplannerclient.user.model.SingUpUserAccount
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SignUpAPI {

    @FormUrlEncoded
    @POST("user/signUpPhoneMemory")
    fun signUpPhoneMemory(@Field("tempUsername") tempUsername: String): Call<UserAccount>

    @POST("/user/signUp")
    fun signUp(@Body userAccount: SingUpUserAccount): Call<UserAccount>
}