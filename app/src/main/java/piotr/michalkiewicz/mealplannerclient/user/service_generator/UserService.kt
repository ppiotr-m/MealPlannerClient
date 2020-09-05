package piotr.michalkiewicz.mealplannerclient.user.service_generator

import io.reactivex.Observable
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreference
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("/user/account")
    fun getUserAccount(): Call<UserAccount>

    @POST("/user/signUp")
    fun signUp(@Body userAccount: UserAccount): Call<UserAccount>

    @POST("/user/account")
    fun editAccountSettings(@Body userAccount: UserAccount): Call<UserAccount>

    @POST("/user/settings")
    fun updateUserSettings(@Body userSettings: UserSettings): Observable<UserAccount>

    @POST("/user/preference")
    fun updateUserPreference(@Body userPreference: UserPreference): Observable<UserAccount>

    @POST("/user/singUp")
    @FormUrlEncoded
    fun signUp(@Field("username") username: String, @Field("email") email: String,
               @Field("password") password: String,
               @Field("grant_type") grantType: String):
            Call<UserAccount>

    @POST("/user/userAccount")
    fun updateAccount(@Body userAccount: UserAccount): Call<UserAccount>

    @POST("/user/settings")
    fun updateSettings(@Body userSettings: UserSettings?): Call<UserSettings>
}