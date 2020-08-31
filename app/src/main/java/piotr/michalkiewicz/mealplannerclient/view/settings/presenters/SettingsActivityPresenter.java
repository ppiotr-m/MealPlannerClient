package piotr.michalkiewicz.mealplannerclient.view.settings.presenters;

import android.content.Context;
import android.util.Log;

import piotr.michalkiewicz.mealplannerclient.user.model.NutritionProfileSettings;
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

                if(data.getUserSettings().getNutritionProfileSettings() == null){
                    data.getUserSettings().setNutritionProfileSettings(new NutritionProfileSettings());
                }

                view.initWithData(data, 1);
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
        data.getUserSettings().setDiet("Standard");
        Log.d(ConstantValues.TAG, "Saving settings to server:\n" + data.getUserSettings().toString());
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
