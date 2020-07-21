package piotr.michalkiewicz.mealplannerclient.view.creators;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.view.adapters.RecipeRecyclerViewAdapter;

public final class RecipeCategoryViewCreator {

    private RecipeCategoryViewCreator(){}

    public static ViewGroup createRecipeCategoryView(List<MealTimeRecipe> data, String category, Context context){
        LinearLayout container = new LinearLayout(context);
        container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        container.setOrientation(LinearLayout.VERTICAL);
        TextView categoryTagTV = new TextView(
                new ContextThemeWrapper(context, R.style.listHeaderText));
        categoryTagTV.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        categoryTagTV.setText(category);
        RecyclerView recipesHorizontalView = new RecyclerView(context);
        recipesHorizontalView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        recipesHorizontalView.setAdapter(new RecipeRecyclerViewAdapter(context, data));
        recipesHorizontalView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        container.addView(categoryTagTV);
        container.addView(recipesHorizontalView);

        return container;
    }
}
