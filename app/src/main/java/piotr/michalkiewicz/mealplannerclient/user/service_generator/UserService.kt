package piotr.michalkiewicz.mealplannerclient.user.service_generator

import io.reactivex.Observable
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreference
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @GET("/user/userAccount")
    fun getUserAccount(): Call<UserAccount>

    @POST("/user/signUp")
    fun signUp(@Body userAccount: UserAccount): Call<UserAccount>

    @POST("/user/account")
    fun editAccountSettings(@Body userAccount: UserAccount): Call<UserAccount>  //toDo delete ?

    @POST("/user/settings")
    fun updateUserSettings(@Body userSettings: UserSettings): Observable<UserAccount>

    @POST("/user/preference")
    fun updateUserPreference(@Body userPreference: UserPreference): Observable<UserAccount>
}