package piotr.michalkiewicz.mealplannerclient.view.recipes.presenters;

import android.util.Log;

import piotr.michalkiewicz.mealplannerclient.recipes.RecipeServiceGenerator;
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues;
import piotr.michalkiewicz.mealplannerclient.view.utils.InitializableViewWithCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipePresenter {
    private InitializableViewWithCategory<MealTimeRecipe> view;
    private final RecipeServiceGenerator serviceGenerator;

    public RecipePresenter(InitializableViewWithCategory<MealTimeRecipe> view) {
        this.view = view;
        this.serviceGenerator = new RecipeServiceGenerator();
    }

    public void fetchRecipe(String recipeId){

        serviceGenerator.getRecipeForId(recipeId, new Callback<MealTimeRecipe>() {
            @Override
            public void onResponse(Call<MealTimeRecipe> call, Response<MealTimeRecipe> response) {
                Log.i(ConstantValues.TAG, "RecipePresenter::fetchRecipe()::onResponse(), responseCode: "
                        + response.code() + "\nresponse.toString():" + response.toString());
                view.initWithData(response.body(), "");
            }

            @Override
            public void onFailure(Call<MealTimeRecipe> call, Throwable t) {
                Log.i(ConstantValues.TAG, "RecipePresenter::fetchRecipe()::onFailure(), msg: " + t.getMessage());

            }
        });
    }
}
