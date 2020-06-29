package piotr.michalkiewicz.mealplannerclient.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.recipes.repository.RecipeRepository;
import piotr.michalkiewicz.mealplannerclient.view.adapters.RecipeRecyclerViewAdapter;
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableViewWithCategory;
import piotr.michalkiewicz.mealplannerclient.view.presenters.CookbookFragmentPresenter;

public class CookbookScreenFragment extends Fragment implements InitializableViewWithCategory<List<MealTimeRecipe>> {

 //   private final List<RecyclerView> recipesViewsForCategories = new ArrayList<>();
    private ViewGroup recipesLayoutContainer;
    private RecipeRepository recipeRepository;
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
        presenter = new CookbookFragmentPresenter(this, getContext());
        presenter.initWithDefaultCategories();
    }

    private void assignUIElements(View root){
        recipesLayoutContainer = root.findViewById(R.id.recipesByCategoriesLayoutContainer);
    }

    @Override
    public void initWithData(List<MealTimeRecipe> data, String category) {
        ViewGroup recipesView = createRecipeCategoryView(data, category);
        recipesLayoutContainer.addView(recipesView);
    }

    private ViewGroup createRecipeCategoryView(List<MealTimeRecipe> data, String category){
        LinearLayout container = new LinearLayout(getContext());
        container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        container.setOrientation(LinearLayout.VERTICAL);
        TextView categoryTagTV = new TextView(
                new ContextThemeWrapper(getContext(),R.style.listHeaderText));
        categoryTagTV.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        categoryTagTV.setText(category);
        RecyclerView recipesHorizontalView = new RecyclerView(getContext());
        recipesHorizontalView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        recipesHorizontalView.setAdapter(new RecipeRecyclerViewAdapter(getContext(), data));

        container.addView(categoryTagTV);
        container.addView(recipesHorizontalView);

        return container;
    }
}
