package piotr.michalkiewicz.mealplannerclient.user.service_generator

import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {

    @POST("/user/signUp")
    fun signUp(@Body userAccount: UserAccount?): Call<UserAccount?>?
}