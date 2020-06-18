package piotr.michalkiewicz.mealplannerclient.view.presenters;

import android.util.Log;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.Diet;
import piotr.michalkiewicz.mealplannerclient.recipes.model.enums.RecipeType;
import piotr.michalkiewicz.mealplannerclient.recipes.server_connection.RecipeRepository;
import piotr.michalkiewicz.mealplannerclient.support.Constants;
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableViewWithCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CookbookFragmentPresenter {

    private InitializableViewWithCategory view;

    public CookbookFragmentPresenter(InitializableViewWithCategory view){
        this.view = view;
    }

    public void initWithDefaultCategories(){
        RecipeRepository repository = new RecipeRepository();
        repository.getRecipesForDiet(Diet.VEGETARIAN.getValue(), new Callback<List<MealTimeRecipe>>() {
            @Override
            public void onResponse(Call<List<MealTimeRecipe>> call, Response<List<MealTimeRecipe>> response) {
                view.initWithData(response.body(), Diet.VEGETARIAN.getValue());
            }

            @Override
            public void onFailure(Call<List<MealTimeRecipe>> call, Throwable t) {
                Log.d(Constants.TAG, "CookbookFragmentPresenter::initWithDefaultCategories, failed " +
                        "getRecipesForDiet(vegetarian) call");
            }
        });
        repository.getRecipesForRecipeType(RecipeType.MEAT.getValue(), new Callback<List<MealTimeRecipe>>() {
            @Override
            public void onResponse(Call<List<MealTimeRecipe>> call, Response<List<MealTimeRecipe>> response) {
                view.initWithData(response.body(), RecipeType.MEAT.getValue());
            }

            @Override
            public void onFailure(Call<List<MealTimeRecipe>> call, Throwable t) {
                Log.d(Constants.TAG, "CookbookFragmentPresenter::initWithDefaultCategories, failed " +
                        "getRecipesForType(meat) call");
            }
        });
        repository.getRecipesForDiet(Diet.PALEO.getValue(), new Callback<List<MealTimeRecipe>>() {
            @Override
            public void onResponse(Call<List<MealTimeRecipe>> call, Response<List<MealTimeRecipe>> response) {
                view.initWithData(response.body(), Diet.PALEO.getValue());
            }

            @Override
            public void onFailure(Call<List<MealTimeRecipe>> call, Throwable t) {
                Log.d(Constants.TAG, "CookbookFragmentPresenter::initWithDefaultCategories, failed " +
                        "getRecipesForDiet(paleo) call");
            }
        });
    }
}
