package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.RecipesByTagDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.data_source.RecipesByTypeDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.datasource.RecipesByDietRemoteMediator
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PAGE_SIZE
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PREFETCH_DISTANCE
import piotr.michalkiewicz.mealplannerclient.utils.MealTimeDatabase

class RecipesSearchViewModel(
    private val recipeAPI: RecipeAPI,
    private val mealTimeDatabase: MealTimeDatabase
) : ViewModel() {

    @ExperimentalPagingApi
    fun recipesByDietApiData(queryParam: String): Flow<PagingData<MealTimeRecipe>> {
        val pagingSource = { mealTimeDatabase.recipesDao().getRecipesForDiet(queryParam) }

        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE),
            remoteMediator = RecipesByDietRemoteMediator(recipeAPI, mealTimeDatabase, queryParam),
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