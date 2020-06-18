package piotr.michalkiewicz.mealplannerclient.view.presenters;

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.recipes.server_connection.RecipeRepository;
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableViewWithCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipePresenter {
    private InitializableViewWithCategory<MealTimeRecipe> view;

    public RecipePresenter(InitializableViewWithCategory<MealTimeRecipe> view) {
        this.view = view;
    }

    public void fetchRecipe(String recipeId){
        RecipeRepository recipeRepository = new RecipeRepository();
        recipeRepository.getRecipesForId(recipeId, new Callback<MealTimeRecipe>() {
            @Override
            public void onResponse(Call<MealTimeRecipe> call, Response<MealTimeRecipe> response) {
                view.initWithData(response.body(), "");
            }

            @Override
            public void onFailure(Call<MealTimeRecipe> call, Throwable t) {

            }
        });
    }
}
