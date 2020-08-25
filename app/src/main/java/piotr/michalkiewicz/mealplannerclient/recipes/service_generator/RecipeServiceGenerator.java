package piotr.michalkiewicz.mealplannerclient.recipes.service_generator;

import android.content.Context;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.auth.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;

public class RecipeServiceGenerator {

    private ServiceGenerator apiClient;
    private RecipeService recipeService;

    public RecipeServiceGenerator(Context context){
        apiClient = new ServiceGenerator();
    }

    public void getRecipesForDiet(String dietType, Callback<List<MealTimeRecipe>> callback){
        initRecipeServiceIfNull();
        Call<List<MealTimeRecipe>> callAsync = recipeService.getRecipeForDiet(dietType);

        callAsync.enqueue(callback);
    }

    public void getRecipeForId(String id, Callback<MealTimeRecipe> callback){
        initRecipeServiceIfNull();
        Call<MealTimeRecipe> callAsync = recipeService.getRecipeForId(id);

        callAsync.enqueue(callback);
    }

    public void getRecipesForRecipeType(String recipeType, Callback<List<MealTimeRecipe>> callback){
        initRecipeServiceIfNull();
        Call<List<MealTimeRecipe>> callAsync = recipeService.getRecipeForType(recipeType);

        callAsync.enqueue(callback);
    }

    public void getRecipesForRecipeType(String recipeType, Callback<List<MealTimeRecipe>> callback,
                                        int pageNr){
        initRecipeServiceIfNull();
        Call<List<MealTimeRecipe>> callAsync = recipeService.getRecipeForType(recipeType, pageNr);

        callAsync.enqueue(callback);
    }

    public void getRecipesForDiet(String dietType, Callback<List<MealTimeRecipe>> callback,
                                        int pageNr){
        initRecipeServiceIfNull();
        Call<List<MealTimeRecipe>> callAsync = recipeService.getRecipeForDiet(dietType, pageNr);

        callAsync.enqueue(callback);
    }

    public void getRecipesForTag(String tag, Callback<List<MealTimeRecipe>> callback,
                                        int pageNr){
        initRecipeServiceIfNull();
        Call<List<MealTimeRecipe>> callAsync = recipeService.getRecipeForTag(tag, pageNr);

        callAsync.enqueue(callback);
    }

    private void initRecipeServiceIfNull(){
        if(recipeService==null) {
            recipeService = apiClient.getRecipeApi();
        }
    }
}

