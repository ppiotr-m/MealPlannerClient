package piotr.michalkiewicz.mealplannerclient.user.api

import io.reactivex.Observable
import piotr.michalkiewicz.mealplannerclient.user.model.NutritionProfileSettings
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreference
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
    fun updateUserSettings(@Body userSettings: UserSettings): Observable<UserSettings>

    @GET("/user/settings")
    fun getUserSettings(): Call<UserSettings>

    @POST("/user/preference")
    fun updateUserPreference(@Body userPreference: UserPreference): Observable<UserPreference>

    @GET("/user/preference")
    fun getUserPreference(): Call<UserPreference>

    @GET("/user/nutritionProfileSettings")
    suspend fun getUserNutritionProfileSettings(): Response<NutritionProfileSettings>
}