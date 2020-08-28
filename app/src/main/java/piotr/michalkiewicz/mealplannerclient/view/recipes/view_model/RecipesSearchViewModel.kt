package piotr.michalkiewicz.mealplannerclient.view.recipes.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.paging.api.RecipesPagedServiceGenerator

class RecipesSearchViewModel(private val recipesPagedServiceGenerator: RecipesPagedServiceGenerator): ViewModel() {
    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<MealTimeRecipe>>? = null

    fun searchRepo(queryString: String): Flow<PagingData<MealTimeRecipe>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<MealTimeRecipe>> = recipesPagedServiceGenerator
                .getRecipesForDiet(queryString)
                .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}