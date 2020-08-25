package piotr.michalkiewicz.mealplannerclient.view.settings.presenters;

import android.content.Context;
import android.util.Log;

import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount;
import piotr.michalkiewicz.mealplannerclient.user.service_generator.UserServiceGenerator;
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues;
import piotr.michalkiewicz.mealplannerclient.view.settings.SettingsActivity;
import piotr.michalkiewicz.mealplannerclient.view.utils.InitializableView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SettingsActivityPresenter {

    private final int PORTIONS_PER_MEAL_LIMIT = 20;
    private final int MEALS_PER_MEAL_PLAN_LIMIT = 10;
    private final int COOKING_TIME_SMALL_STEP = 5;
    private final int COOKING_TIME_MEDIUM_STEP = 15;
    private final int COOKING_TIME_BIG_STEP = 30;
    private final int COOKIG_TIME_LIMIT = 600;
    private InitializableView view;
    private UserAccount data;

    public SettingsActivityPresenter(SettingsActivity activity){
        this.view = activity;
        if(activity==null) {
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

    public int getPreferredCookingTime(){
        return data.getUserSettings().getCookingTimePreference();
    }

    public int getPortionsPerMeal(){
        return data.getUserSettings().getPortionPreferences();
    }

    public int getMealsPerMealPlan(){
        return data.getUserSettings().getMealsPerMealPlanPreference();
    }

    public void increasePreferredCookingTime(){
        if(data.getUserSettings().getCookingTimePreference()<60) {
            data.getUserSettings().setCookingTimePreference(data.getUserSettings().getCookingTimePreference()+COOKING_TIME_SMALL_STEP);
            return;
        }
        if(data.getUserSettings().getCookingTimePreference()<120){
            data.getUserSettings().setCookingTimePreference(data.getUserSettings().getCookingTimePreference()+COOKING_TIME_MEDIUM_STEP);
            return;
        }
        if(data.getUserSettings().getCookingTimePreference()<COOKIG_TIME_LIMIT) {
            data.getUserSettings().setCookingTimePreference(data.getUserSettings().getCookingTimePreference() + COOKING_TIME_BIG_STEP);
        }
    }

    public void decreasePreferredCookingTime(){
        if(data.getUserSettings().getCookingTimePreference()<=5) return;
        if(data.getUserSettings().getCookingTimePreference()<=60) {
            data.getUserSettings().setCookingTimePreference(data.getUserSettings().getCookingTimePreference()-COOKING_TIME_SMALL_STEP);
            return;
        }
        if(data.getUserSettings().getCookingTimePreference()<=120){
            data.getUserSettings().setCookingTimePreference(data.getUserSettings().getCookingTimePreference()-COOKING_TIME_MEDIUM_STEP);
            return;
        }
        data.getUserSettings().setCookingTimePreference(data.getUserSettings().getCookingTimePreference()-COOKING_TIME_BIG_STEP);
    }

    public void increasePortionsPerMeal(){
        if(data.getUserSettings().getPortionPreferences()>=PORTIONS_PER_MEAL_LIMIT) return;

        data.getUserSettings().setPortionPreferences(data.getUserSettings().getPortionPreferences()+1);
    }

    public void decreasePortionsPerMeal(){
        if(data.getUserSettings().getPortionPreferences()<2) return;

        data.getUserSettings().setPortionPreferences(data.getUserSettings().getPortionPreferences()-1);
    }

    public void increaseMealsPerMealPlan(){
        if(data.getUserSettings().getMealsPerMealPlanPreference()>=MEALS_PER_MEAL_PLAN_LIMIT) return;

        data.getUserSettings().setMealsPerMealPlanPreference(data.getUserSettings().getMealsPerMealPlanPreference()+1);
    }

    public void decreaseMealsPerMealPlan(){
        if(data.getUserSettings().getMealsPerMealPlanPreference()<2) return;

        data.getUserSettings().setMealsPerMealPlanPreference(data.getUserSettings().getMealsPerMealPlanPreference()-1);
    }

    public void initSettingsViewWithData(){
        UserServiceGenerator userServiceGenerator = new UserServiceGenerator();

        userServiceGenerator.getUserAccount(new Callback<UserAccount>() {
            @Override
            public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                view.initWithData(response.body(), 1);
                data = response.body();
                Log.d(ConstantValues.TAG, "Settings meals: " + response.body().getUserSettings().getMealsPerMealPlanPreference());
            }

            @Override
            public void onFailure(Call<UserAccount> call, Throwable t) {
                Log.i(ConstantValues.TAG, "SettingsActivityPresenter::initSettingsViewWithData failed\n " +
                        t.getLocalizedMessage());
            }
        });
    }

    public void saveSettingsServerSide(){
        UserServiceGenerator userServiceGenerator = new UserServiceGenerator();
        Log.d(ConstantValues.TAG, "Settings portions: " + data.getUserSettings().getPortionPreferences());
        data.getUserSettings().setPortionPreferences(3);
        userServiceGenerator.saveUserSettings(data.getUserSettings(), new Callback<UserAccount>() {
            @Override
            public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                Log.i(ConstantValues.TAG, "SettingsActivityPresenter::saveSettingsServerSide response:" + response.message());
            }

            @Override
            public void onFailure(Call<UserAccount> call, Throwable t) {
                Log.i(ConstantValues.TAG, "SettingsActivityPresenter::saveSettingsServerSide failure:" + t.getLocalizedMessage());
            }
        });
    }
}
