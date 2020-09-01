package piotr.michalkiewicz.mealplannerclient.recipes.paging

import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.auth.ServiceGenerator
import piotr.michalkiewicz.mealplannerclient.recipes.paging.api.RecipesPagedServiceGenerator
import piotr.michalkiewicz.mealplannerclient.recipes.paging.ui.RecipesViewModelFactory

object Injection {

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return RecipesViewModelFactory(RecipesPagedServiceGenerator(ServiceGenerator.getRecipesPagedApi()))
    }
}