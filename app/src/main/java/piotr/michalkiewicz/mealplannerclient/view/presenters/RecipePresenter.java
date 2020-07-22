package piotr.michalkiewicz.mealplannerclient.view.presenters;

import android.content.Context;
import android.util.Log;

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.recipes.repository.RecipeRepository;
import piotr.michalkiewicz.mealplannerclient.support.Constants;
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableViewWithCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipePresenter {
    private InitializableViewWithCategory<MealTimeRecipe> view;
    RecipeRepository recipeRepository;

    public RecipePresenter(InitializableViewWithCategory<MealTimeRecipe> view, Context context) {
        this.view = view;
        recipeRepository = new RecipeRepository(context);
    }

    public void fetchRecipe(String recipeId){

        recipeRepository.getRecipeForId(recipeId, new Callback<MealTimeRecipe>() {
            @Override
            public void onResponse(Call<MealTimeRecipe> call, Response<MealTimeRecipe> response) {
                Log.i(Constants.TAG, "RecipePresenter::fetchRecipe()::onResponse(), responseCode: "
                        + response.code() + "\nresponse.toString():" + response.toString());
                view.initWithData(response.body(), "");
            }

            @Override
            public void onFailure(Call<MealTimeRecipe> call, Throwable t) {
                Log.i(Constants.TAG, "RecipePresenter::fetchRecipe()::onFailure(), msg: " + t.getMessage());
            }
        });
    }
}
