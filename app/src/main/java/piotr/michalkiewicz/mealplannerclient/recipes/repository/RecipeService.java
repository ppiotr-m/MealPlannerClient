package piotr.michalkiewicz.mealplannerclient.recipes.repository;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RecipeService {

    @GET("/recipes/getById/{recipeId}")
    Call<MealTimeRecipe> getRecipeForId(@Path("recipeId") String recipeId);

    @GET("/recipes/getByDiet/{dietType}")
    Call<List<MealTimeRecipe>> getRecipeForDiet(@Path("dietType") String dietType);

    @GET("/recipes/getByType/{recipeType}")
    Call<MealTimeRecipe> getRecipeForType(@Path("recipeType") String recipeType);
}
