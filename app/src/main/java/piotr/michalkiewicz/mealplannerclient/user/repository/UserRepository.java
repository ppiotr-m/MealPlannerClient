package piotr.michalkiewicz.mealplannerclient.user.repository;

import android.content.Context;
import android.util.Log;

import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues;
import piotr.michalkiewicz.mealplannerclient.view.login_and_signup.auth.ServiceGenerator;
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount;
import retrofit2.Call;
import retrofit2.Callback;

public class UserRepository {

    private ServiceGenerator apiClient;
    private UserService userService;

    public UserRepository(Context context){
        apiClient = new ServiceGenerator();
    }

    public void getUserAccount(Callback<UserAccount> callback){
        initUserServiceIfNull();
        Call<UserAccount> callAsync = userService.getUserAccount();

        callAsync.enqueue(callback);
    }

    public void signUp(UserAccount userAccount, Callback<UserAccount> callback){
        initUserServiceIfNull();
        Call<UserAccount> callAsync = userService.signUp(userAccount);

        callAsync.enqueue(callback);
    }

    public void saveUserAccountData(UserAccount data, Callback<UserAccount> callback){
        initUserServiceIfNull();
        Call<UserAccount> callAsync = userService.editAccountSettings(data);

        callAsync.enqueue(callback);
    }

    private void initUserServiceIfNull(){
        if(userService==null) {
            userService = apiClient.getUserApi();
        }
    }
}
