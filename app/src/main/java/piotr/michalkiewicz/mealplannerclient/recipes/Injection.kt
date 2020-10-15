package piotr.michalkiewicz.mealplannerclient.recipes

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.recipes.database.RecipesDatabase
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.OnPrependDataLoadedListener
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.RecipesViewModelFactory

object Injection {

    fun provideViewModelFactory(
            onPrependDataLoadedListener: OnPrependDataLoadedListener, context: Context): ViewModelProvider.Factory {
        return RecipesViewModelFactory(
                RecipeServiceGenerator().recipeAPI,
                RecipesDatabase.getInstance(context),
                onPrependDataLoadedListener)
    }
}