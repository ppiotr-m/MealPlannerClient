package piotr.michalkiewicz.mealplannerclient.view.presenters;

import piotr.michalkiewicz.mealplannerclient.auth.ServiceGenerator;
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.recipes.nameToDoNoRepo.RecipeService;
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreenPresenter {

    private InitializableView view;
    private ServiceGenerator serviceGenerator;

    public HomeScreenPresenter(InitializableView view) {
        this.view = view;
        this.serviceGenerator = new ServiceGenerator();
    }

    public void initWithTemporaryRecipes() {
        RecipeService recipeService = serviceGenerator.getRecipeApi();


        recipeService.getRecipeForId("5e9b0884c18f0c68c680102e").enqueue(new Callback<MealTimeRecipe>() {
            @Override
            public void onResponse(Call<MealTimeRecipe> call, Response<MealTimeRecipe> response) {
                view.initWithData(response.body(), 1);

            }

            @Override
            public void onFailure(Call<MealTimeRecipe> call, Throwable t) {

            }
        });

        recipeService.getRecipeForId("5e9b410dc18f0c68c680102f").enqueue(new Callback<MealTimeRecipe>() {
            @Override
            public void onResponse(Call<MealTimeRecipe> call, Response<MealTimeRecipe> response) {
                view.initWithData(response.body(), 2);
            }

            @Override
            public void onFailure(Call<MealTimeRecipe> call, Throwable t) {

            }
        });
        recipeService.getRecipeForId("5e9b4b20c18f0c68c6801031").enqueue(new Callback<MealTimeRecipe>() {
            @Override
            public void onResponse(Call<MealTimeRecipe> call, Response<MealTimeRecipe> response) {
                view.initWithData(response.body(), 3);
            }

            @Override
            public void onFailure(Call<MealTimeRecipe> call, Throwable t) {

            }
        });
        recipeService.getRecipeForId("5e9b4eaac18f0c68c6801032").enqueue(new Callback<MealTimeRecipe>() {
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
