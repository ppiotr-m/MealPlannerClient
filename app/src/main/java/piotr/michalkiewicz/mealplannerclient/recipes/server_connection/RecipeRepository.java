package piotr.michalkiewicz.mealplannerclient.recipes.server_connection;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import retrofit2.Call;
import retrofit2.Callback;

public class RecipeRepository {

    public void getRecipesForDiet(String dietType, Callback<List<MealTimeRecipe>> callback){
        RecipeService recipeService = RecipeApiClient.createService(RecipeService.class);
        Call<List<MealTimeRecipe>> callAsync = recipeService.getRecipeForDiet(dietType);

        callAsync.enqueue(callback);
    }

    public void getRecipesForId(String id, Callback<MealTimeRecipe> callback){
        RecipeService recipeService = RecipeApiClient.createService(RecipeService.class);
        Call<MealTimeRecipe> callAsync = recipeService.getRecipeForId(id);

        callAsync.enqueue(callback);
    }

    public void getRecipesForRecipeType(String recipeType, Callback<List<MealTimeRecipe>> callback){
        RecipeService recipeService = RecipeApiClient.createService(RecipeService.class);
        Call<List<MealTimeRecipe>> callAsync = recipeService.getRecipeForDiet(recipeType);

        callAsync.enqueue(callback);
    }
}
