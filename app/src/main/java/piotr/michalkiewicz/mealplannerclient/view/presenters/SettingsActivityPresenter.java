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

    public SettingsActivityPresenter(SettingsActivity activity){
        this.view = activity;
        if(activity==null) Log.d(Constants.TAG, "Null context at SettingsActivityPresenter constructor");
        repository = new UserRepository(activity);
    }

    public void initSettingsViewWithData(){
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
    }
}
