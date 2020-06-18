package piotr.michalkiewicz.mealplannerclient.connection;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/randomRecipes")
    Call<List<MealTimeRecipe>> getRandomRecipes();
}
