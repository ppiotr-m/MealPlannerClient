package piotr.michalkiewicz.mealplannerclient.recipes.data_source

import androidx.paging.PagingSource
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeService
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeMiniatureData
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.OnPrependDataLoadedListener
import retrofit2.HttpException
import java.io.IOException

class AllRecipesDataSource(private val recipeService: RecipeService,
                           private val onPrependDataLoadedListener: OnPrependDataLoadedListener) :
        PagingSource<Int, MealTimeRecipeMiniatureData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MealTimeRecipeMiniatureData> {

        val position = params.key ?: RECIPES_STARTING_PAGE_INDEX

        return try {
            val resultData = recipeService.getAllRecipes()
            if (position == RECIPES_STARTING_PAGE_INDEX) {
                onPrependDataLoadedListener.onPrependDataLoaded()
            }
            LoadResult.Page(
                    data = resultData,
                    prevKey = if (position == RECIPES_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (resultData.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}