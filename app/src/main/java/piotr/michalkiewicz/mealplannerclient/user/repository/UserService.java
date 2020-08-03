package piotr.michalkiewicz.mealplannerclient.user.repository;

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("/user/userAccount")
    Call<UserAccount> getUserAccount();

    @POST("/user/signUp")
    Call<UserAccount> signUp(@Body UserAccount userAccount);

    @POST("/user/account")
    Call<UserAccount> editAccountSettings(@Body UserAccount userAccount);

}
