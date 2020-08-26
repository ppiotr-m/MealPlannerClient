package piotr.michalkiewicz.mealplannerclient.user.service_generator;

import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount;
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @GET("/user/userAccount")
    Call<UserAccount> getUserAccount();

    @POST("/user/singUp")
    Call<UserAccount> signUp(@Body UserAccount userAccount);

    @POST("/user/userAccount")
    Call<UserAccount> updateAccount(@Body UserAccount userAccount);

    @POST("/user/settings")
    Call<UserAccount> updateSettings(@Body UserSettings userSettings);

}
