package piotr.michalkiewicz.mealplannerclient.recipes.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.remote.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.RECIPES_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class RecipesByTagDataSource(
    private val recipeAPI: RecipeAPI,
    private val queryParam: String
) : PagingSource<Int, MealTimeRecipe>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MealTimeRecipe> {

        val position = params.key ?: RECIPES_STARTING_PAGE_INDEX

        return try {
            val resultData = recipeAPI.getRecipesPageForTag("light", position)
            LoadResult.Page(
                data = resultData.recipes,
                prevKey = if (position == RECIPES_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (resultData.recipes.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MealTimeRecipe>): Int? {
        return state.anchorPosition
    }
}