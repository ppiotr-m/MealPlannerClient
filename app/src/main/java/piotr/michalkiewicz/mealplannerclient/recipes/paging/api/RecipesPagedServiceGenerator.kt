package piotr.michalkiewicz.mealplannerclient.recipes.paging.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.paging.data.RecipesDataSource

class RecipesPagedServiceGenerator (private val recipeServiceGenerator: RecipesPagedServiceGenerator){

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

    fun getRecipesForDiet(dietType: String): Flow<PagingData<MealTimeRecipe>> {
        return Pager(
                config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false
                ),
                pagingSourceFactory = { RecipesDataSource(recipeServiceGenerator, dietType) }
        ).flow
    }
}