package piotr.michalkiewicz.mealplannerclient.user.api

import io.reactivex.Observable
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreference
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAPI {

    @GET("/user/account")
    fun getUserAccount(): Call<UserAccount>

    @POST("/user/account")
    fun editAccountSettings(@Body userAccount: UserAccount): Call<UserAccount>

    @POST("/user/settings")
    fun updateUserSettings(@Body userSettings: UserSettings): Observable<UserAccount>

    @POST("/user/preference")
    fun updateUserPreference(@Body userPreference: UserPreference): Observable<UserAccount>

    @POST("/user/account")
    fun updateAccount(@Body userAccount: UserAccount): Call<UserAccount>   //toDO delete?

    @POST("/user/settings")
    fun updateSettings(@Body userSettings: UserSettings?): Call<UserSettings> //toDO delete?
}