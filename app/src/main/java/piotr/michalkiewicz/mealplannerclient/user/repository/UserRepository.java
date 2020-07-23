package piotr.michalkiewicz.mealplannerclient.user.repository;

import android.content.Context;

import piotr.michalkiewicz.mealplannerclient.connection.AuthorizedApiClient;
import piotr.michalkiewicz.mealplannerclient.recipes.repository.RecipeService;
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount;
import retrofit2.Call;
import retrofit2.Callback;

public class UserRepository {

    private AuthorizedApiClient apiClient;
    private UserService userService;

    public UserRepository(Context context){
        apiClient = new AuthorizedApiClient(context);
    }

    public void getUserAccount(Callback<UserAccount> callback){
        initUserServiceIfNull();
        Call<UserAccount> callAsync = userService.getUserAccount();

        callAsync.enqueue(callback);
    }

    public void signUp(UserAccount userAccount, Callback<UserAccount> callback){
        initUserServiceIfNull();
        Call<UserAccount> callAsyns = userService.signUp(userAccount);

        callAsyns.enqueue(callback);
    }

    private void initUserServiceIfNull(){
        if(userService==null) {
            userService = apiClient.createService(UserService.class);
        }
    }
}
