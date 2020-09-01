package piotr.michalkiewicz.mealplannerclient.recipes.paging.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe

class RecipeViewHolder(val recipeCardView: View): RecyclerView.ViewHolder(recipeCardView) {
    private val recipeTitle: TextView = recipeCardView.findViewById(R.id.recipeCardTitle)
    private val cookbookThumbnail: ImageView = recipeCardView.findViewById(R.id.recipeCardThumbnail)

    private var recipe: MealTimeRecipe? = null

    init{
        // TODO Implement click listener
    }

    fun bind(recipe: MealTimeRecipe?) {
        if (recipe == null) {
            val resources = itemView.resources
            recipeTitle.text = resources.getString(R.string.loading)
            cookbookThumbnail.visibility = View.GONE
        } else {
            showRepoData(recipe)
        }
    }

    private fun showRepoData(recipe: MealTimeRecipe) {
        this.recipe = recipe
        recipeTitle.text = recipe.name
        cookbookThumbnail.setImageBitmap(recipe.image)

        cookbookThumbnail.visibility = View.VISIBLE
    }

    companion object {
        fun create(parent: ViewGroup): RecipeViewHolder {
            // TODO Change cooking_item to recipe_card_view
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recipe_list_item, parent, false)
            return RecipeViewHolder(view)
        }
    }
}