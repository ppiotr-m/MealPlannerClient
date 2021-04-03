package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.interfaces.CookbookItemOnClickListener

class RecipeViewHolder(private val recipeCardView: View) : RecyclerView.ViewHolder(recipeCardView) {
    private val recipeTitle: TextView = recipeCardView.findViewById(R.id.recipeCardTitle)
    private val cookbookThumbnail: ImageView = recipeCardView.findViewById(R.id.recipeCardThumbnail)

    private lateinit var recipe: MealTimeRecipe
    private lateinit var onItemClickListener: CookbookItemOnClickListener

    fun bind(recipe: MealTimeRecipe?, onItemClickListener: CookbookItemOnClickListener) {
        this.onItemClickListener = onItemClickListener
        if (recipe == null) {
            val resources = itemView.resources
            recipeTitle.text = resources.getString(R.string.loading)
            cookbookThumbnail.visibility = View.GONE
        } else {
            showRepoData(recipe)
        }
    }

    private fun addOnClickListener(recipeId: String) {
        recipeCardView.setOnClickListener {
            onItemClickListener.onItemClicked(recipeId)
        }
    }

    private fun showRepoData(recipe: MealTimeRecipe) {
        addOnClickListener(recipe.id)
        this.recipe = recipe
        recipeTitle.text = recipe.name
        cookbookThumbnail.setImageBitmap(recipe.image)
        cookbookThumbnail.visibility = View.VISIBLE
    }

    companion object {
        fun create(parent: ViewGroup): RecipeViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recipe_list_item, parent, false)
            return RecipeViewHolder(view)
        }
    }
}