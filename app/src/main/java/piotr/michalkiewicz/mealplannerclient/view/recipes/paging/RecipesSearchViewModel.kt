package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeService
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.AllRecipesDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.RecipesByDietDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.RecipesByTagDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.RecipesByTypeDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeMiniatureData
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PAGE_SIZE

class RecipesSearchViewModel(private val recipeService: RecipeService,
                             private val onPrependDataLoadedListener: OnPrependDataLoadedListener) : ViewModel() {

    fun recipesByDietApiData(queryParam: String): Flow<PagingData<MealTimeRecipeMiniatureData>> {
        return Pager(
                PagingConfig(PAGE_SIZE)
        ) {
            RecipesByDietDataSource(recipeService = recipeService, queryParam = queryParam,
                    onPrependDataLoadedListener = onPrependDataLoadedListener)
        }.flow.cachedIn(viewModelScope)
    }

    fun recipesByTypeApiData(queryParam: String): Flow<PagingData<MealTimeRecipeMiniatureData>> {
        return Pager(
                PagingConfig(PAGE_SIZE)
        ) {
            RecipesByTypeDataSource(recipeService = recipeService, queryParam = queryParam,
                    onPrependDataLoadedListener = onPrependDataLoadedListener)
        }.flow.cachedIn(viewModelScope)
    }

    fun recipesByTagApiData(queryParam: String): Flow<PagingData<MealTimeRecipeMiniatureData>> {
        return Pager(
                PagingConfig(PAGE_SIZE)
        ) {
            RecipesByTagDataSource(recipeService = recipeService, queryParam = queryParam,
                    onPrependDataLoadedListener = onPrependDataLoadedListener)
        }.flow.cachedIn(viewModelScope)
    }

    fun allRecipesApiData(): Flow<PagingData<MealTimeRecipeMiniatureData>> {
        return Pager(
                PagingConfig(PAGE_SIZE)
        ) {
            AllRecipesDataSource(recipeService = recipeService,
                    onPrependDataLoadedListener = onPrependDataLoadedListener)
        }.flow.cachedIn(viewModelScope)
    }
}