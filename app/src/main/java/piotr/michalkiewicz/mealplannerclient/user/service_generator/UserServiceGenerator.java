package piotr.michalkiewicz.mealplannerclient.user.service_generator;

import android.content.Context;

import piotr.michalkiewicz.mealplannerclient.auth.ServiceGenerator;
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount;
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings;
import retrofit2.Call;
import retrofit2.Callback;

public class UserServiceGenerator {

    private ServiceGenerator apiClient;
    private UserService userService;

    public UserServiceGenerator(){
        apiClient = new ServiceGenerator();
    }

    public void getUserAccount(Callback<UserAccount> callback){
        initUserServiceIfNull();
        Call<UserAccount> callAsync = userService.getUserAccount();

        callAsync.enqueue(callback);
    }

    public void signUp(UserAccount userAccount, Callback<UserAccount> callback){
        SignUpService signUpService = apiClient.getSignUpApi();
        Call<UserAccount> callAsync = signUpService.signUp(userAccount);

        callAsync.enqueue(callback);
    }

    public void saveUserAccountData(UserAccount data, Callback<UserAccount> callback){
        initUserServiceIfNull();
        Call<UserAccount> callAsync = userService.updateAccount(data);

        callAsync.enqueue(callback);
    }

    public void saveUserSettings(UserSettings userSettings, Callback<UserAccount> callback){
        initUserServiceIfNull();
        Call<UserAccount> callAsync = userService.updateSettings(userSettings);

        callAsync.enqueue(callback);
    }

    private void initUserServiceIfNull(){
        if(userService==null) {
            userService = apiClient.getUserApi();
        }
    }
}
