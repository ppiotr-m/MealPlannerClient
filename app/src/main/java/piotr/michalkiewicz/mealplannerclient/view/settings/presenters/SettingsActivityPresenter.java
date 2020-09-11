package piotr.michalkiewicz.mealplannerclient.view.settings.presenters;

import android.util.Log;

import piotr.michalkiewicz.mealplannerclient.user.UserServiceGenerator;
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount;
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues;
import piotr.michalkiewicz.mealplannerclient.view.settings.SettingsActivity;
import piotr.michalkiewicz.mealplannerclient.view.utils.InitializableView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        if (data != null) {
            view.initWithData(data);
            return;
        }
        UserServiceGenerator userServiceGenerator = new UserServiceGenerator();

        userServiceGenerator.getUserAccount(new Callback<UserAccount>() {
            @Override
            public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                data = response.body();
                if (data == null) {
                    view.initWithData(null);
                    return;
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

    public void saveSettingsServerSide() {
        if (data != null) {
            UserServiceGenerator userServiceGenerator = new UserServiceGenerator();
            userServiceGenerator.updateUserSettings(data.getUserSettings());
        }
        //  TODO Implemented action for null data
    }

    public void removeAvoidedIngredient(String ingredientName) {
        data.getUserSettings().getUserPreference().getUnlikeIngredients().remove(ingredientName);
    }

    public void removeAllergy(String allergyName) {
        data.getUserSettings().getUserPreference().getAllergies().remove(allergyName);
    }
}
