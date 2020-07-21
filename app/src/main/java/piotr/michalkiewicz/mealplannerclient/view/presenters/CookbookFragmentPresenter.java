package piotr.michalkiewicz.mealplannerclient.view.presenters;

import android.content.Context;
import android.util.Log;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.Diet;
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.RecipeType;
import piotr.michalkiewicz.mealplannerclient.recipes.repository.RecipeRepository;
import piotr.michalkiewicz.mealplannerclient.support.Constants;
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableViewWithCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CookbookFragmentPresenter {

    private InitializableViewWithCategory view;
    private RecipeRepository recipeRepository;

    public CookbookFragmentPresenter(InitializableViewWithCategory view, Context context){
        this.view = view;
        recipeRepository = new RecipeRepository(context);
    }

    public void initWithDefaultCategories(){
        getRecipesForType(RecipeType.MEAT.getValue());
        getRecipesForDiet(Diet.PALEO.getValue());
        getRecipesForType(RecipeType.SOUP.getValue());
        getRecipesForDiet(Diet.VEGETARIAN.getValue());
        getRecipesForDiet(Diet.STANDARD.getValue());
    }

    private void getRecipesForDiet(String dietType){
        recipeRepository.getRecipesForDiet(dietType, new Callback<List<MealTimeRecipe>>() {
            @Override
            public void onResponse(Call<List<MealTimeRecipe>> call, Response<List<MealTimeRecipe>> response) {
                Log.i(Constants.TAG, "CookbookPresenter:getRecipesForDiet(), response code: " +
                        response.code());
                view.initWithData(response.body(), "Diet type: " + dietType);
            }

            @Override
            public void onFailure(Call<List<MealTimeRecipe>> call, Throwable t) {
                Log.d(Constants.TAG, "CookbookFragmentPresenter::getRecipesForDiet, failed " +
                        "getRecipesForDiet call. URL: " + call.request().url() + "\nMessage: " +
                        t.getMessage() + "\nLocalized message: " + t.getLocalizedMessage());
            }
        });
    }
    private void getRecipesForType(String recipeType){
        recipeRepository.getRecipesForRecipeType(recipeType, new Callback<List<MealTimeRecipe>>() {
            @Override
            public void onResponse(Call<List<MealTimeRecipe>> call, Response<List<MealTimeRecipe>> response) {
                Log.i(Constants.TAG, "CookbookPresenter:getRecipesForType(), response body: " + response.body());
                view.initWithData(response.body(), "Recipe type: " + recipeType);
            }

            @Override
            public void onFailure(Call<List<MealTimeRecipe>> call, Throwable t) {
                Log.d(Constants.TAG, "CookbookFragmentPresenter::getRecipesForType\nMessage: "
                        + t.getMessage() + "\nLocalized message: " + t.getLocalizedMessage());
            }
        });
    }

    private void getRecipesForId(String id){
        recipeRepository.getRecipesForId(id, new Callback<MealTimeRecipe>() {
            @Override
            public void onResponse(Call<MealTimeRecipe> call, Response<MealTimeRecipe> response) {
      //          view.initWithData(response.body(), RecipeType.MEAT.getValue());
            }

            @Override
            public void onFailure(Call<MealTimeRecipe> call, Throwable t) {
                Log.d(Constants.TAG, "CookbookFragmentPresenter::getRecipesForId\nMessage: "
                + t.getMessage() + "\nLocalized message: " + t.getLocalizedMessage());
            }
        });
    }
}
