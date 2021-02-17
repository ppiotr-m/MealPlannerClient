package piotr.michalkiewicz.mealplannerclient.user.api

import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreferences
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAPI {

    @GET("/user/userAccount")
    fun getUserAccount(): Call<UserAccount>

    @POST("/user/settings")
    suspend fun updateUserSettings(@Body userSettings: UserSettings): Response<UserSettings>

    @GET("/user/settings")
    suspend fun getUserSettings(): Response<UserSettings>

    @POST("/user/preference")
    suspend fun updateUserPreference(@Body userPreferences: UserPreferences): Response<UserPreferences>

    @GET("/user/preference")
    suspend fun getUserPreference(): Response<UserPreferences>
}