package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipesPagedService
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.RecipesDataSource
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PAGE_SIZE

class RecipesSearchViewModel(private val recipesPagedService: RecipesPagedService) : ViewModel() {
    val apiData = Pager(
            PagingConfig(PAGE_SIZE)
    ) {
        RecipesDataSource(recipePagedService = recipesPagedService, queryParam = "Standard")
    }.flow.cachedIn(viewModelScope)
}