package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeBase
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.RECIPE_ID
import piotr.michalkiewicz.mealplannerclient.view.recipes.RecipeActivity

class RecipeViewHolder(private val recipeCardView: View) : RecyclerView.ViewHolder(recipeCardView) {
    private val recipeTitle: TextView = recipeCardView.findViewById(R.id.recipeCardTitle)
    private val cookbookThumbnail: ImageView = recipeCardView.findViewById(R.id.recipeCardThumbnail)

    private lateinit var recipe: MealTimeRecipeBase

    fun bind(recipe: MealTimeRecipeBase?) {
        if (recipe == null) {
            val resources = itemView.resources
            recipeTitle.text = resources.getString(R.string.loading)
            cookbookThumbnail.visibility = View.GONE
        } else {
            showRepoData(recipe)
        }
    }

    private fun addOnClickListener(recipeId: String){
        recipeCardView.setOnClickListener {
            val intent = Intent(recipeCardView.context, RecipeActivity::class.java)
            intent.putExtra(RECIPE_ID, recipeId)
            recipeCardView.context.startActivity(intent)
        }
    }

    private fun showRepoData(recipe: MealTimeRecipeBase) {
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