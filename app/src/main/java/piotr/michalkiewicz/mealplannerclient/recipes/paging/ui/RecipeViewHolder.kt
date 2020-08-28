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
    private val recipeTitle: TextView = recipeCardView.findViewById(R.id.recipeTitleTV)
    private val grade: TextView = recipeCardView.findViewById(R.id.gradeTV)
    private val cookbookThumbnail: ImageView = recipeCardView.findViewById(R.id.cookbookThumbnailImgView)

    private var recipe: MealTimeRecipe? = null

    init{
        // TODO Implement click listener
    }

    fun bind(recipe: MealTimeRecipe?) {
        if (recipe == null) {
            val resources = itemView.resources
            recipeTitle.text = resources.getString(R.string.loading)
            grade.visibility = View.GONE
            cookbookThumbnail.visibility = View.GONE
        } else {
            showRepoData(recipe)
        }
    }

    private fun showRepoData(recipe: MealTimeRecipe) {
        this.recipe = recipe
        recipeTitle.text = recipe.name
        grade.text = recipe.totalRating.toString()
        cookbookThumbnail.setImageBitmap(recipe.image)

        grade.visibility = View.VISIBLE
        cookbookThumbnail.visibility = View.VISIBLE
    }

    companion object {
        fun create(parent: ViewGroup): RecipeViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recipe_card_view, parent, false)
            return RecipeViewHolder(view)
        }
    }
}