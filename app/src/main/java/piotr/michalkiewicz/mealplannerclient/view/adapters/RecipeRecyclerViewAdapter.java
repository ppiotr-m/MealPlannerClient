package piotr.michalkiewicz.mealplannerclient.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.view.activities.recipes.RecipeActivity;

import static piotr.michalkiewicz.mealplannerclient.support.Constants.RECIPE_ID;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeViewHolder> {

    private Context context;
    private List<MealTimeRecipe> recipeList;

    public RecipeRecyclerViewAdapter(Context context, List<MealTimeRecipe> recipeList){
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View recipeView = inflater.inflate(R.layout.cookbook_item, parent, false);
        RecipeViewHolder holder = new RecipeViewHolder(recipeView);
        return holder;
    }

    /**
     * 
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        MealTimeRecipe recipe = recipeList.get(position);

        holder.viewsNrTV.setText(String.valueOf(recipe.getViews()));
        holder.recipeTitleTV.setText(recipe.getName());
        holder.gradeTV.setText(String.valueOf(recipe.getTotalLikes()));
        holder.container.setOnClickListener(v -> {
            Intent intent = new Intent(this.context, RecipeActivity.class);
            intent.putExtra(RECIPE_ID, recipe.getId());
            this.context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{

        View container;
        TextView gradeTV;
        TextView viewsNrTV;
        Button addToMealPlanBtn;
        Button likeRecipeBtn;
        TextView recipeTitleTV;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.cookbookThumbnailContainer);
            gradeTV = itemView.findViewById(R.id.gradeTV);
            viewsNrTV = itemView.findViewById(R.id.viewsNrTV);
            addToMealPlanBtn = itemView.findViewById(R.id.addToMealPlanBtn);
            likeRecipeBtn = itemView.findViewById(R.id.likeRecipeBtn);
            recipeTitleTV = itemView.findViewById(R.id.recipeTitleTV);
        }
    }
}
