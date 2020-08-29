package piotr.michalkiewicz.mealplannerclient.view.main_menu.presenters;

import android.util.Log;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.recipes.service_generator.RecipeService;
import piotr.michalkiewicz.mealplannerclient.recipes.service_generator.RecipeServiceGenerator;
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues;
import piotr.michalkiewicz.mealplannerclient.view.utils.InitializableViewWithCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CookbookFragmentPresenter {

    private InitializableViewWithCategory view;
    private final RecipeServiceGenerator serviceGenerator = new RecipeServiceGenerator();
    private final RecipeService recipeService = serviceGenerator.recipeService;

    public CookbookFragmentPresenter(InitializableViewWithCategory view) {
        this.view = view;
    }

    public void initWithDefaultCategories() {

        getRecipesForType("meat");
        getRecipesForDiet("paleo");
        getRecipesForType("soup");
        getRecipesForDiet("vegetarian");
        getRecipesForDiet("standart");
    }

    private void getRecipesForDiet(String dietType) {
        recipeService.getRecipeForDiet(dietType).enqueue(new Callback<List<MealTimeRecipe>>() {
            @Override
            public void onResponse(Call<List<MealTimeRecipe>> call, Response<List<MealTimeRecipe>> response) {
                Log.i(ConstantValues.TAG, "CookbookPresenter:getRecipesForDiet(), response code: " +
                        response.code());
                view.initWithData(response.body(), "Diet type: " + dietType);
            }

            @Override
            public void onFailure(Call<List<MealTimeRecipe>> call, Throwable t) {
                Log.d(ConstantValues.TAG, "CookbookFragmentPresenter::getRecipesForDiet, failed " +
                        "getRecipesForDiet call. URL: " + call.request().url() + "\nMessage: " +
                        t.getMessage() + "\nLocalized message: " + t.getLocalizedMessage());
            }
        });
    }

    private void getRecipesForType(String recipeType) {
        recipeService.getRecipeForType(recipeType).enqueue(new Callback<List<MealTimeRecipe>>() {
            @Override
            public void onResponse(Call<List<MealTimeRecipe>> call, Response<List<MealTimeRecipe>> response) {
                Log.i(ConstantValues.TAG, "CookbookPresenter:getRecipesForType(), response body: " + response.body());
                view.initWithData(response.body(), "Recipe type: " + recipeType);
            }

            @Override
            public void onFailure(Call<List<MealTimeRecipe>> call, Throwable t) {
                Log.d(ConstantValues.TAG, "CookbookFragmentPresenter::getRecipesForType\nMessage: "
                        + t.getMessage() + "\nLocalized message: " + t.getLocalizedMessage());
            }
        });
    }

    private void getRecipesForId(String id) {
        recipeService.getRecipeForId(id).enqueue(new Callback<MealTimeRecipe>() {
            @Override
            public void onResponse(Call<MealTimeRecipe> call, Response<MealTimeRecipe> response) {
                //          view.initWithData(response.body(), RecipeType.MEAT.getValue());
            }

            @Override
            public void onFailure(Call<MealTimeRecipe> call, Throwable t) {
                Log.d(ConstantValues.TAG, "CookbookFragmentPresenter::getRecipeForId\nMessage: "
                        + t.getMessage() + "\nLocalized message: " + t.getLocalizedMessage());
            }
        });
    }
}
