package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.AllRecipesDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.RecipesByDietDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.RecipesByTagDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.RecipesByTypeDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeBase
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PAGE_SIZE

class RecipesSearchViewModel(private val recipeAPI: RecipeAPI,
                             private val onPrependDataLoadedListener: OnPrependDataLoadedListener) : ViewModel() {

    fun recipesByDietApiData(queryParam: String): Flow<PagingData<MealTimeRecipeBase>> {
        return Pager(
                PagingConfig(PAGE_SIZE)
        ) {
            RecipesByDietDataSource(recipeAPI = recipeAPI, queryParam = queryParam,
                    onPrependDataLoadedListener = onPrependDataLoadedListener)
        }.flow.cachedIn(viewModelScope)
    }

    fun recipesByTypeApiData(queryParam: String): Flow<PagingData<MealTimeRecipeBase>> {
        return Pager(
                PagingConfig(PAGE_SIZE)
        ) {
            RecipesByTypeDataSource(recipeAPI = recipeAPI, queryParam = queryParam,
                    onPrependDataLoadedListener = onPrependDataLoadedListener)
        }.flow.cachedIn(viewModelScope)
    }

    fun recipesByTagApiData(queryParam: String): Flow<PagingData<MealTimeRecipeBase>> {
        return Pager(
                PagingConfig(PAGE_SIZE)
        ) {
            RecipesByTagDataSource(recipeAPI = recipeAPI, queryParam = queryParam,
                    onPrependDataLoadedListener = onPrependDataLoadedListener)
        }.flow.cachedIn(viewModelScope)
    }

    fun allRecipesApiData(): Flow<PagingData<MealTimeRecipeBase>> {
        return Pager(
                PagingConfig(PAGE_SIZE)
        ) {
            AllRecipesDataSource(recipeAPI = recipeAPI,
                    onPrependDataLoadedListener = onPrependDataLoadedListener)
        }.flow.cachedIn(viewModelScope)
    }
}