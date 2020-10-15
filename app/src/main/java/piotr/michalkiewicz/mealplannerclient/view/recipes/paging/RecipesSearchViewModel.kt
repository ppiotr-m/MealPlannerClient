package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.AllRecipesDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.RecipesByTagDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.RecipesByTypeDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.database.RecipesDatabase
import piotr.michalkiewicz.mealplannerclient.recipes.datasource.RecipesByDietRemoteMediator
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeBase
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PAGE_SIZE

class RecipesSearchViewModel(private val recipeAPI: RecipeAPI,
                             private val recipesDatabase: RecipesDatabase?,
                             private val onPrependDataLoadedListener: OnPrependDataLoadedListener) : ViewModel() {

    @ExperimentalPagingApi
    fun recipesByDietApiData(queryParam: String): Flow<PagingData<MealTimeRecipeBase>> {
        val pagingSource = recipesDatabase?.recipesDao()?.getRecipesForDiet(queryParam)

        return Pager(
                config = PagingConfig(pageSize = PAGE_SIZE),
                remoteMediator = RecipesByDietRemoteMediator(recipeAPI, queryParam)
        ) {
            pagingSource!!
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