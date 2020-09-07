package piotr.michalkiewicz.mealplannerclient.view.settings.presenters;

import android.util.Log;

import piotr.michalkiewicz.mealplannerclient.user.model.NutritionProfileSettings;
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount;
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreference;
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings;
import piotr.michalkiewicz.mealplannerclient.user.service_generator.UserServiceGenerator;
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues;
import piotr.michalkiewicz.mealplannerclient.view.settings.SettingsActivity;
import piotr.michalkiewicz.mealplannerclient.view.utils.InitializableView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SettingsActivityPresenter {

    private InitializableView view;
    private UserAccount data;

    public SettingsActivityPresenter(SettingsActivity activity){
        this.view = activity;
        if(activity==null) {
            return;
        }
    }

    public UserAccount getData() {
        return data;
    }

    public void setData(UserAccount data) {
        this.data = data;
    }

    public void initSettingsViewWithData(){
        UserServiceGenerator userServiceGenerator = new UserServiceGenerator();

        userServiceGenerator.getUserAccount(new Callback<UserAccount>() {
            @Override
            public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                data = response.body();
                Log.i(TAG, response.message() + "\n" + response.toString());
                if(data==null){
                    view.initWithData(null);
                    return;
                }
                if (data.getUserSettings() != null) {
                    if (data.getUserSettings().getNutritionProfileSettings() == null) {
                        data.getUserSettings().setNutritionProfileSettings(new NutritionProfileSettings());
                    }
                    if (data.getUserSettings().getUserPreference() == null) {
                        data.getUserSettings().setUserPreference(new UserPreference());
                    }
                } else {
                    data.setUserSettings(new UserSettings());
                }

                view.initWithData(data);
            }

            @Override
            public void onFailure(Call<UserAccount> call, Throwable t) {
                Log.i(ConstantValues.TAG, "SettingsActivityPresenter::initSettingsViewWithData failed\n " +
                        t.getLocalizedMessage());
            }
        });
    }

    public void saveSettingsServerSide(){
        if(data != null) {
            UserServiceGenerator userServiceGenerator = new UserServiceGenerator();
            userServiceGenerator.updateUserSettings(data.getUserSettings());
        }
        //  TODO Implemented action for null data
    }
}
