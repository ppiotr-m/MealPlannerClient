package piotr.michalkiewicz.mealplannerclient.recipes.service_generator;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeService {

    @GET("/recipes/getById")
    Call<MealTimeRecipe> getRecipeForId(@Query("id") String recipeId);

    @GET("/recipes/getByDiet")
    Call<List<MealTimeRecipe>> getRecipeForDiet(@Query("recipeDiet") String dietType);

    @GET("/recipes/getByType")
    Call<List<MealTimeRecipe>> getRecipeForType(@Query("recipeType") String recipeType);

    @GET("/recipes/getByDiet/{pageNr}")
    Call<List<MealTimeRecipe>> getRecipeForDiet(@Query("recipeDiet") String dietType, @Path("pageNr") int pageNr);

    @GET("/recipes/getByType/{pageNr}")
    Call<List<MealTimeRecipe>> getRecipeForType(@Query("recipeType") String recipeType, @Path("pageNr") int pageNr);

    @GET("/recipes/getByTag{pageNr}")
    Call<List<MealTimeRecipe>> getRecipeForTag(@Query("tag") String recipeType, @Path("pageNr") int pageNr);
}
