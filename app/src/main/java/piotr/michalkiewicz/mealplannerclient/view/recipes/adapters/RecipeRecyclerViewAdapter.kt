package piotr.michalkiewicz.mealplannerclient.view.recipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recipe_card_view.view.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe

class RecipeRecyclerViewAdapter(private val dataSet: List<MealTimeRecipe>): RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_card_view, parent, false) as CardView)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.recipeCardView.cookbookThumbnailImgView.setImageBitmap(dataSet[position].image)
        holder.recipeCardView.recipeTitleTV.text = dataSet[position].name
        holder.recipeCardView.gradeTV.text = dataSet[position].totalRating.toString()
    }

     class RecipeViewHolder( val recipeCardView: CardView): RecyclerView.ViewHolder(recipeCardView)
}