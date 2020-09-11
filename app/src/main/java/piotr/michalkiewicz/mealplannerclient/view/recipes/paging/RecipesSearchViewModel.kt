package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.recipes.datasource.AllRecipesDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.datasource.RecipesByDietDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.datasource.RecipesByTagDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.datasource.RecipesByTypeDataSource
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PAGE_SIZE

class RecipesSearchViewModel(private val recipeAPI: RecipeAPI) : ViewModel() {

    fun recipesByDietApiData(queryParam: String): Flow<PagingData<MealTimeRecipe>>{
        return Pager(
                PagingConfig(PAGE_SIZE)
        ) {
            RecipesByDietDataSource(recipeAPI = recipeAPI, queryParam = queryParam)
        }.flow.cachedIn(viewModelScope)
    }

    fun recipesByTypeApiData(queryParam: String): Flow<PagingData<MealTimeRecipe>>{
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

    fun allRecipesApiData(): Flow<PagingData<MealTimeRecipe>> {
        return Pager(
                PagingConfig(PAGE_SIZE)
        ) {
            AllRecipesDataSource(recipeAPI = recipeAPI)
        }.flow.cachedIn(viewModelScope)
    }
}