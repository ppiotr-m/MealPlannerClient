package piotr.michalkiewicz.mealplannerclient.recipes.paging.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import piotr.michalkiewicz.mealplannerclient.auth.ServiceGenerator
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.paging.api.RecipesPagedServiceGenerator
import piotr.michalkiewicz.mealplannerclient.recipes.paging.data.RecipesDataSource
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues

class RecipesSearchViewModel(private val recipesPagedServiceGenerator: RecipesPagedServiceGenerator) : ViewModel() {
    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<MealTimeRecipe>>? = null

    val apiData = Pager(
            // Configure how data is loaded by passing additional properties to
            // PagingConfig, such as prefetchDistance.
            PagingConfig(pageSize = 20)
    ) {
        RecipesDataSource(recipePagedService = ServiceGenerator.getRecipesPagedApi(), queryParam = "Standard")
    }.flow
            .cachedIn(viewModelScope)

    fun searchRecipe(queryString: String): Flow<PagingData<MealTimeRecipe>> {
        Log.i(ConstantValues.TAG, "RecipesSearchViewModel::searchRecipe")

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