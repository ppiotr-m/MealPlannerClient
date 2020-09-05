package piotr.michalkiewicz.mealplannerclient.recipes.data_source

import androidx.paging.PagingSource
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeService
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import retrofit2.HttpException
import java.io.IOException

class AllRecipesDataSource(private val recipeService: RecipeService,
                           private val queryParam: String) :
        PagingSource<Int, MealTimeRecipe>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MealTimeRecipe> {

        val position = params.key ?: RECIPES_STARTING_PAGE_INDEX

        return try {
            val resultData = recipeService.getAllRecipes()
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
}