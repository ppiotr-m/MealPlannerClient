package piotr.michalkiewicz.mealplannerclient.recipes.repository;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeService {

    @GET("/recipes/getById")
    Call<MealTimeRecipe> getRecipeForId(@Query("recipeId") String recipeId);

    @GET("/recipes/getByDiet")
    Call<List<MealTimeRecipe>> getRecipeForDiet(@Query("recipeDiet") String dietType);

    @GET("/recipes/getByType")
    Call<List<MealTimeRecipe>> getRecipeForType(@Query("recipeType") String recipeType);
}
