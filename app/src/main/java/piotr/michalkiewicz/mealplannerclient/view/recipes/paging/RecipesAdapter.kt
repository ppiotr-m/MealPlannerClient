package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeBase

class RecipesAdapter : PagingDataAdapter<MealTimeRecipeBase, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecipeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as RecipeViewHolder).bind(repoItem)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<MealTimeRecipeBase>() {
            override fun areItemsTheSame(oldItem: MealTimeRecipeBase,
                                         newItem: MealTimeRecipeBase): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MealTimeRecipeBase,
                                            newItem: MealTimeRecipeBase): Boolean =
                    oldItem.id == newItem.id
        }
    }
}