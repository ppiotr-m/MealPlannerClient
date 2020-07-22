package piotr.michalkiewicz.mealplannerclient.view.presenters;

import android.content.Context;

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.recipes.repository.RecipeRepository;
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableView;
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableViewWithCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreenPresenter {

    private InitializableView view;
    private RecipeRepository recipeRepository;

    public HomeScreenPresenter(InitializableView view, Context context){
        this.view = view;
        recipeRepository = new RecipeRepository(context);
    }

    public void initWithTemporaryRecipes(){
        recipeRepository.getRecipeForId("5e9b0884c18f0c68c680102e", new Callback<MealTimeRecipe>() {
            @Override
            public void onResponse(Call<MealTimeRecipe> call, Response<MealTimeRecipe> response) {
                view.initWithData(response.body(), 1);
            }

            @Override
            public void onFailure(Call<MealTimeRecipe> call, Throwable t) {

            }
        });
        recipeRepository.getRecipeForId("5e9b410dc18f0c68c680102f", new Callback<MealTimeRecipe>() {
            @Override
            public void onResponse(Call<MealTimeRecipe> call, Response<MealTimeRecipe> response) {
                view.initWithData(response.body(), 2);
            }

            @Override
            public void onFailure(Call<MealTimeRecipe> call, Throwable t) {

            }
        });
        recipeRepository.getRecipeForId("5e9b4b20c18f0c68c6801031", new Callback<MealTimeRecipe>() {
            @Override
            public void onResponse(Call<MealTimeRecipe> call, Response<MealTimeRecipe> response) {
                view.initWithData(response.body(), 3);
            }

            @Override
            public void onFailure(Call<MealTimeRecipe> call, Throwable t) {

            }
        });
        recipeRepository.getRecipeForId("5e9b4eaac18f0c68c6801032", new Callback<MealTimeRecipe>() {
            @Override
            public void onResponse(Call<MealTimeRecipe> call, Response<MealTimeRecipe> response) {
                view.initWithData(response.body(), 4);
            }

            @Override
            public void onFailure(Call<MealTimeRecipe> call, Throwable t) {

            }
        });

    }
}
