package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.view.menu.fragments.cookbook.interfaces.RecipesNavigationListener

class RecipesAdapter(private val recipesNavigationListener: RecipesNavigationListener) :
    PagingDataAdapter<MealTimeRecipe, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecipeViewHolder.create(parent, recipesNavigationListener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as RecipeViewHolder).bind(repoItem)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<MealTimeRecipe>() {
            override fun areItemsTheSame(
                oldItem: MealTimeRecipe,
                newItem: MealTimeRecipe
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MealTimeRecipe,
                newItem: MealTimeRecipe
            ): Boolean =
                oldItem.id == newItem.id
                        && oldItem.comments == newItem.comments
                        && oldItem.cookTime == newItem.cookTime
                        && oldItem.dateAdded == newItem.dateAdded
                        && oldItem.dateEdited == newItem.dateEdited
                        && oldItem.description == newItem.description
                        && oldItem.from == newItem.from
                        && oldItem.image.equals(newItem.image)  //  TODO Make sure this is correct
                        && oldItem.instructionSteps == newItem.instructionSteps
                        && oldItem.language == newItem.language
                        && oldItem.level == newItem.level
                        && oldItem.madeBY == newItem.madeBY
                        && oldItem.name == newItem.name
                        && oldItem.recipeCuisines == newItem.recipeCuisines
                        && oldItem.recipeIngredients == newItem.recipeIngredients
                        && oldItem.recipeTag == newItem.recipeTag
                        && oldItem.recipeType == newItem.recipeType
                        && oldItem.recipeYield == newItem.recipeYield
                        && oldItem.suitableForDiet == newItem.suitableForDiet
                        && oldItem.totalLikes == newItem.totalLikes
                        && oldItem.totalRating == newItem.totalRating
                        && oldItem.views == newItem.views
        }
    }
}