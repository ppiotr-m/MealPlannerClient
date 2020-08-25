package piotr.michalkiewicz.mealplannerclient.view.main_menu.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.recipes.service_generator.RecipeServiceGenerator;
import piotr.michalkiewicz.mealplannerclient.view.main_menu.RecipesRecyclerViewScrollListener;
import piotr.michalkiewicz.mealplannerclient.view.recipes.RecipeActivity;
import piotr.michalkiewicz.mealplannerclient.view.recipes.adapters.RecipeRecyclerViewAdapter;
import piotr.michalkiewicz.mealplannerclient.view.utils.InitializableViewWithCategory;
import piotr.michalkiewicz.mealplannerclient.view.main_menu.presenters.CookbookFragmentPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CookbookScreenFragment extends Fragment implements InitializableViewWithCategory<List<MealTimeRecipe>> {

    private ViewGroup recipesLayoutContainer;
    private CookbookFragmentPresenter presenter;
    private final int itemViewCacheSize = 20;
    private final RecipeServiceGenerator recipeServiceGenerator = new RecipeServiceGenerator(getContext());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cookbook_screen, container, false);
        init(view);

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    private void init(View root){
        assignUIElements(root);
        presenter = new CookbookFragmentPresenter(this);
        presenter.initWithDefaultCategories();
    }

    private void assignUIElements(View root){
        recipesLayoutContainer = root.findViewById(R.id.recipesByCategoriesLayoutContainer);
    }

    @Override
    public void initWithData(List<MealTimeRecipe> data, String category) {
        ViewGroup recipesView = createRecipesHorizontalRecyclerView(data);
        recipesView.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), RecipeActivity.class);
            startActivity(intent);
        });
        recipesLayoutContainer.addView(recipesView);
    }

    private ViewGroup createRecipesHorizontalRecyclerView(List<MealTimeRecipe> data){
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(getContext())
                .inflate(R.layout.recipes_list_recycler_view, recipesLayoutContainer, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecipeRecyclerViewAdapter(data));
        recyclerView.addOnScrollListener(new RecipesRecyclerViewScrollListener(getContext()){
            @Override
            public void onLoadMore(int pageNumber, @NotNull RecyclerView recyclerView) {
                recipeServiceGenerator.getRecipesForDiet("standard", new Callback<List<MealTimeRecipe>>() {
                    @Override
                    public void onResponse(Call<List<MealTimeRecipe>> call, Response<List<MealTimeRecipe>> response) {
                  //      recyclerView.getAdapter().
                    }

                    @Override
                    public void onFailure(Call<List<MealTimeRecipe>> call, Throwable t) {

                    }
                }, pageNumber);
            }
        });
        recyclerView.setItemViewCacheSize(itemViewCacheSize);
        return recyclerView;
    }


}
