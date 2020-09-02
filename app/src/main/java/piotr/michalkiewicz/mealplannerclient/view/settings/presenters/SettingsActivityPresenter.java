package piotr.michalkiewicz.mealplannerclient.view.settings.presenters;

import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount;
import piotr.michalkiewicz.mealplannerclient.user.service_generator.UserServiceGenerator;
import piotr.michalkiewicz.mealplannerclient.view.settings.SettingsActivity;
import piotr.michalkiewicz.mealplannerclient.view.utils.InitializableView;

public class SettingsActivityPresenter {

    private final int PORTIONS_PER_MEAL_LIMIT = 20;
    private final int MEALS_PER_MEAL_PLAN_LIMIT = 10;
    private final int COOKING_TIME_SMALL_STEP = 5;
    private final int COOKING_TIME_MEDIUM_STEP = 15;
    private final int COOKING_TIME_BIG_STEP = 30;
    private final int COOKIG_TIME_LIMIT = 600;
    private InitializableView view;
    private UserServiceGenerator repository;
    private UserAccount data;

    public SettingsActivityPresenter(SettingsActivity activity) {
        this.view = activity;
        if (activity == null) {
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

    public int getPreferredCookingTime() {
        return data.getUserSettings().getUserPreference().getCookingTimePreference();
    }

    public int getPortionsPerMeal() {
        return data.getUserSettings().getUserPreference().getPortionPreferences();
    }

    public int getMealsPerMealPlan() {
        return data.getUserSettings().getUserPreference().getMealsPerMealPlanPreference();
    }

    public void increasePreferredCookingTime() {
        if (data.getUserSettings().getUserPreference().getCookingTimePreference() < 60) {
            data.getUserSettings().getUserPreference().setCookingTimePreference(data.getUserSettings().getUserPreference().getCookingTimePreference() + COOKING_TIME_SMALL_STEP);
            return;
        }
        if (data.getUserSettings().getUserPreference().getCookingTimePreference() < 120) {
            data.getUserSettings().getUserPreference().setCookingTimePreference(data.getUserSettings().getUserPreference().getCookingTimePreference() + COOKING_TIME_MEDIUM_STEP);
            return;
        }
        if (data.getUserSettings().getUserPreference().getCookingTimePreference() < COOKIG_TIME_LIMIT) {
            data.getUserSettings().getUserPreference().setCookingTimePreference(data.getUserSettings().getUserPreference().getCookingTimePreference() + COOKING_TIME_BIG_STEP);
        }
    }

    public void decreasePreferredCookingTime() {
        if (data.getUserSettings().getUserPreference().getCookingTimePreference() <= 5) return;
        if (data.getUserSettings().getUserPreference().getCookingTimePreference() <= 60) {
            data.getUserSettings().getUserPreference().setCookingTimePreference(data.getUserSettings().getUserPreference().getCookingTimePreference() - COOKING_TIME_SMALL_STEP);
            return;
        }
        if (data.getUserSettings().getUserPreference().getCookingTimePreference() <= 120) {
            data.getUserSettings().getUserPreference().setCookingTimePreference(data.getUserSettings().getUserPreference().getCookingTimePreference() - COOKING_TIME_MEDIUM_STEP);
            return;
        }
        data.getUserSettings().getUserPreference().setCookingTimePreference(data.getUserSettings().getUserPreference().getCookingTimePreference() - COOKING_TIME_BIG_STEP);
    }

    public void increasePortionsPerMeal() {
        if (data.getUserSettings().getUserPreference().getPortionPreferences() >= PORTIONS_PER_MEAL_LIMIT)
            return;

        data.getUserSettings().getUserPreference().setPortionPreferences(data.getUserSettings().getUserPreference().getPortionPreferences() + 1);
    }

    public void decreasePortionsPerMeal() {
        if (data.getUserSettings().getUserPreference().getPortionPreferences() < 2) return;

        data.getUserSettings().getUserPreference().setPortionPreferences(data.getUserSettings().getUserPreference().getPortionPreferences() - 1);
    }

    public void increaseMealsPerMealPlan() {
        if (data.getUserSettings().getUserPreference().getMealsPerMealPlanPreference() >= MEALS_PER_MEAL_PLAN_LIMIT)
            return;

        data.getUserSettings().getUserPreference().setMealsPerMealPlanPreference(data.getUserSettings().getUserPreference().getMealsPerMealPlanPreference() + 1);
    }

    public void decreaseMealsPerMealPlan() {
        if (data.getUserSettings().getUserPreference().getMealsPerMealPlanPreference() < 2) return;

        data.getUserSettings().getUserPreference().setMealsPerMealPlanPreference(data.getUserSettings().getUserPreference().getMealsPerMealPlanPreference() - 1);
    }

    public void initSettingsViewWithData() {
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
