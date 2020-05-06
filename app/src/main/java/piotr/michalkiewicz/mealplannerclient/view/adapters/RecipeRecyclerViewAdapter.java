package piotr.michalkiewicz.mealplannerclient.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.model.MealTimeRecipe;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeViewHolder> {

    Context context;
    List<MealTimeRecipe> recipeList;

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

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        MealTimeRecipe recipe = recipeList.get(position);

    //    holder.container.setBackground(TemporaryImageProvider.getRandomImage());
        holder.recipeTitleTV.setText(recipe.getName());
        holder.container.setBackground(context.getResources().getDrawable(R.mipmap.random_dish));

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

            container = itemView.findViewById(R.id.recipeImgAndDataLayout);
            gradeTV = itemView.findViewById(R.id.gradeTV);
            viewsNrTV = itemView.findViewById(R.id.viewsNrTV);
            addToMealPlanBtn = itemView.findViewById(R.id.addRecipeToMealPlanBtn);
            likeRecipeBtn = itemView.findViewById(R.id.likeRecipeBtn);
            recipeTitleTV = itemView.findViewById(R.id.recipeTitleTV);
        }
    }
}
