package piotr.michalkiewicz.mealplannerclient.recipes.repository;

import android.content.Context;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.connection.AuthorizedApiClient;
import piotr.michalkiewicz.mealplannerclient.connection.SingletonApiClient;
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class RecipeRepository {

    private AuthorizedApiClient apiClient;

    public RecipeRepository(Context context){
        apiClient = new AuthorizedApiClient(context);
    }

    public void getRecipesForDiet(String dietType, Callback<List<MealTimeRecipe>> callback){
        Call<List<MealTimeRecipe>> callAsync = getRecipeService().getRecipeForDiet(dietType);

        callAsync.enqueue(callback);
    }

    public void getRecipesForId(String id, Callback<MealTimeRecipe> callback){
        Call<MealTimeRecipe> callAsync = getRecipeService().getRecipeForId(id);

        callAsync.enqueue(callback);
    }

    public void getRecipesForRecipeType(String recipeType, Callback<List<MealTimeRecipe>> callback){
        Call<List<MealTimeRecipe>> callAsync = getRecipeService().getRecipeForDiet(recipeType);

        callAsync.enqueue(callback);
    }

    private RecipeService getRecipeService(){
        return apiClient.createService(RecipeService.class);
    }
}
