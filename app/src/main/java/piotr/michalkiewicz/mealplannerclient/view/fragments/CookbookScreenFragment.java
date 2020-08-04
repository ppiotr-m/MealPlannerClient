package piotr.michalkiewicz.mealplannerclient.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.view.activities.recipes.RecipeActivity;
import piotr.michalkiewicz.mealplannerclient.view.creators.RecipeCategoryViewCreator;
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableViewWithCategory;
import piotr.michalkiewicz.mealplannerclient.view.presenters.CookbookFragmentPresenter;

public class CookbookScreenFragment extends Fragment implements InitializableViewWithCategory<List<MealTimeRecipe>> {

    private ViewGroup recipesLayoutContainer;
    private CookbookFragmentPresenter presenter;

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
        ViewGroup recipesView = RecipeCategoryViewCreator.createRecipeCategoryView(data, category, getContext());
        recipesView.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), RecipeActivity.class);
            startActivity(intent);
        });
        recipesLayoutContainer.addView(recipesView);
    }
}
