package piotr.michalkiewicz.mealplannerclient.user.service_generator

import io.reactivex.Observable
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SignUpAPI {

    @FormUrlEncoded
    @POST("user/signUpPhoneMemory")
    fun signUpPhoneMemory(@Field("fakeUsername") fakeUsername: String): Observable<UserAccount>

    @POST("/user/signUp")
    fun signUp(@Body userAccount: UserAccount): Call<UserAccount>
}