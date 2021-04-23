package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.RecipesByTagDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.RecipesByTypeDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.database.RecipesDatabase
import piotr.michalkiewicz.mealplannerclient.recipes.datasource.RecipesByDietRemoteMediator
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.remote.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PAGE_SIZE

class RecipesSearchViewModel(
    private val recipeAPI: RecipeAPI,
    private val recipesDatabase: RecipesDatabase
) : ViewModel() {

    private val PREFETCH_DISTANCE = 5

    @ExperimentalPagingApi
    fun recipesByDietApiData(queryParam: String): Flow<PagingData<MealTimeRecipe>> {
        val pagingSource = { recipesDatabase.recipesDao().getRecipesForDiet(queryParam) }

        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE),
            remoteMediator = RecipesByDietRemoteMediator(recipeAPI, recipesDatabase, queryParam),
            pagingSourceFactory = pagingSource
        ).flow.cachedIn(viewModelScope)
    }

    fun recipesByTypeApiData(queryParam: String): Flow<PagingData<MealTimeRecipe>> {
        return Pager(
            PagingConfig(PAGE_SIZE)
        ) {
            RecipesByTypeDataSource(recipeAPI = recipeAPI, queryParam = queryParam)
        }.flow.cachedIn(viewModelScope)
    }

    fun recipesByTagApiData(queryParam: String): Flow<PagingData<MealTimeRecipe>> {
        return Pager(
            PagingConfig(PAGE_SIZE)
        ) {
            RecipesByTagDataSource(recipeAPI = recipeAPI, queryParam = queryParam)
        }.flow.cachedIn(viewModelScope)
    }
}