package piotr.michalkiewicz.mealplannerclient.view.main_menu.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.view.utils.InitializableView;
import piotr.michalkiewicz.mealplannerclient.view.main_menu.presenters.HomeScreenPresenter;

public class HomeScreenFragment extends Fragment implements InitializableView<MealTimeRecipe> {

    private FrameLayout frame1;
    private FrameLayout frame2;
    private FrameLayout frame3;
    private FrameLayout frame4;

    private HomeScreenPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        init(view);

        return view;
    }

    private void init(View view){
        assignUIElements(view);
        presenter = new HomeScreenPresenter(this);
        presenter.initWithTemporaryRecipes();
    }

    private void assignUIElements(View view){
        frame1 = view.findViewById(R.id.firstPropFrame);
        frame2 = view.findViewById(R.id.secondPropFrame);
        frame3 = view.findViewById(R.id.thirdPropFrame);
        frame4 = view.findViewById(R.id.fourthPropFrame);
    }

    @Override
    public void initWithData(MealTimeRecipe data, int frameNr) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View recipeView;

        switch (frameNr){
            case 1: recipeView = inflater.inflate(R.layout.cookbook_item, frame1, false);
                    frame1.addView(fillViewWithRecipeData(recipeView, data));
                    break;
            case 2: recipeView = inflater.inflate(R.layout.cookbook_item, frame2, false);
                    frame2.addView(fillViewWithRecipeData(recipeView, data));
                    break;
            case 3: recipeView = inflater.inflate(R.layout.cookbook_item, frame3, false);
                    frame3.addView(fillViewWithRecipeData(recipeView, data));
                break;
            case 4: recipeView = inflater.inflate(R.layout.cookbook_item, frame4, false);
                    frame4.addView(fillViewWithRecipeData(recipeView, data));
                break;
        }
    }

    private View fillViewWithRecipeData(View view, MealTimeRecipe recipe){

        /*
        ((TextView)view.findViewById(R.id.viewsNrTV)).setText(String.valueOf(recipe.getViews()));
        ((TextView)view.findViewById(R.id.recipeTitleTV)).setText(recipe.getName());
        ((TextView)view.findViewById(R.id.gradeTV)).setText(String.valueOf(recipe.getTotalLikes()));
        view.findViewById(R.id.cookbookThumbnailContainer).setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), RecipeActivity.class);
            intent.putExtra(RECIPE_ID, recipe.getId());
            getContext().startActivity(intent);
        });
 */
        return view;
    }
}
