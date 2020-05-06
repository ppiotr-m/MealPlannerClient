package piotr.michalkiewicz.mealplannerclient.connection;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.model.MealTimeRecipe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeFetcher {

    private ApiInterface apiService;

    public RecipeFetcher(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public void getRandomRecipes(){
        Call<List<MealTimeRecipe>> call = apiService.getRandomRecipes();
        call.enqueue(new Callback<List<MealTimeRecipe>>() {
            @Override
            public void onResponse(Call<List<MealTimeRecipe>> call, Response<List<MealTimeRecipe>> response) {

            }

            @Override
            public void onFailure(Call<List<MealTimeRecipe>> call, Throwable t) {

            }
        });
    }
}
