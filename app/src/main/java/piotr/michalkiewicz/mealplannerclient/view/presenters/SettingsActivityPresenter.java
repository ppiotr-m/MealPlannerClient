package piotr.michalkiewicz.mealplannerclient.view.presenters;

import android.content.Context;
import android.util.Log;

import piotr.michalkiewicz.mealplannerclient.support.Constants;
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount;
import piotr.michalkiewicz.mealplannerclient.user.repository.UserRepository;
import piotr.michalkiewicz.mealplannerclient.view.activities.settings.SettingsActivity;
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivityPresenter {

    private InitializableView view;
    private UserRepository repository;
    private UserAccount data;

    public SettingsActivityPresenter(SettingsActivity activity){
        this.view = activity;
        if(activity==null) {
            Log.d(Constants.TAG, "Null context at SettingsActivityPresenter constructor");
            return;
        }
    //  Commented until settings repo connection is implemented
//        repository = new UserRepository(activity);
    }

    public UserAccount getData() {
        return data;
    }

    public void setData(UserAccount data) {
        this.data = data;
    }

    public void increasePreferredCookingTime(){
        data.getUserSettings().setCookingTimePreference(data.getUserSettings().getCookingTimePreference()+5);
    }

    public void decreasePreferredCookingTime(){
        data.getUserSettings().setCookingTimePreference(data.getUserSettings().getCookingTimePreference()-5);
    }

    public void increasePortionsPerMeal(){
        data.getUserSettings().setPortionPreferences(data.getUserSettings().getPortionPreferences()+1);
    }

    public void decreasePortionsPerMeal(){
        data.getUserSettings().setPortionPreferences(data.getUserSettings().getPortionPreferences()-1);
    }

    public void increaseMealsPerMealPlan(){
        data.getUserSettings().setMealsPerMealPlanPreference(data.getUserSettings().getMealsPerMealPlanPreference()+1);
    }

    public void decreaseMealsPerMealPlan(){
        data.getUserSettings().setMealsPerMealPlanPreference(data.getUserSettings().getMealsPerMealPlanPreference()-1);
    }

    public void initSettingsViewWithData(){
        data = UserAccount.createMockUserAccount();
        view.initWithData(data, 0);
/*
        repository.getUserAccount(new Callback<UserAccount>() {
            @Override
            public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                view.initWithData(response.body(), 1);
            }

            @Override
            public void onFailure(Call<UserAccount> call, Throwable t) {
                Log.i(Constants.TAG, "SettingsActivityPresenter::initSettingsViewWithData failed");
            }
        });

 */

    }
}
