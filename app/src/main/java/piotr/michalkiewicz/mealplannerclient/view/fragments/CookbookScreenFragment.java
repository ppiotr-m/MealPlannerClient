package piotr.michalkiewicz.mealplannerclient.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.view.adapters.RecipeRecyclerViewAdapter;

public class CookbookScreenFragment extends Fragment {

    RecyclerView randomRecipesRV;

    RecyclerView randomRecipesRV2;
    RecyclerView randomRecipesRV3;
    RecyclerView randomRecipesRV4;
    RecyclerView randomRecipesRV5;
    RecyclerView randomRecipesRV6;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cookbook_screen_fragment, container, false);
        randomRecipesRV = (RecyclerView) view.findViewById(R.id.randomRecipesRV);

        randomRecipesRV2 = (RecyclerView) view.findViewById(R.id.randomRecipesRV2);
        randomRecipesRV3 = (RecyclerView) view.findViewById(R.id.randomRecipesRV3);
        randomRecipesRV4 = (RecyclerView) view.findViewById(R.id.randomRecipesRV4);
        randomRecipesRV5 = (RecyclerView) view.findViewById(R.id.randomRecipesRV5);
        randomRecipesRV6 = (RecyclerView) view.findViewById(R.id.randomRecipesRV6);

        randomRecipesRV.setAdapter(new RecipeRecyclerViewAdapter(container.getContext(), getTemporaryRecipes()));
        randomRecipesRV.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        randomRecipesRV2.setAdapter(new RecipeRecyclerViewAdapter(container.getContext(), getTemporaryRecipes()));
        randomRecipesRV2.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        randomRecipesRV3.setAdapter(new RecipeRecyclerViewAdapter(container.getContext(), getTemporaryRecipes()));
        randomRecipesRV3.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        randomRecipesRV4.setAdapter(new RecipeRecyclerViewAdapter(container.getContext(), getTemporaryRecipes()));
        randomRecipesRV4.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        randomRecipesRV5.setAdapter(new RecipeRecyclerViewAdapter(container.getContext(), getTemporaryRecipes()));
        randomRecipesRV5.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        randomRecipesRV6.setAdapter(new RecipeRecyclerViewAdapter(container.getContext(), getTemporaryRecipes()));
        randomRecipesRV6.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        return view;
    }

    private List<MealTimeRecipe> getTemporaryRecipes(){
        List recipeList = new LinkedList<>();

        recipeList.add(new MealTimeRecipe("Kasza z warzywami"));
        recipeList.add(new MealTimeRecipe("Pierogi z mięsem"));
        recipeList.add(new MealTimeRecipe("Tuńczyk z kukurydzą"));
        recipeList.add(new MealTimeRecipe("Kus kus z wołowiną"));
        recipeList.add(new MealTimeRecipe("Zupa pomidorowa"));
        recipeList.add(new MealTimeRecipe("Filety z cipek"));
        recipeList.add(new MealTimeRecipe("Rodzynki z baraniej dupy"));
        recipeList.add(new MealTimeRecipe("Jaja, męskie niegolone"));
        recipeList.add(new MealTimeRecipe("Wywar z gumijagód"));
        recipeList.add(new MealTimeRecipe("Pokrzywy z kamieniami"));
        recipeList.add(new MealTimeRecipe("Piasek polany spermą"));
        return recipeList;
    }
}
