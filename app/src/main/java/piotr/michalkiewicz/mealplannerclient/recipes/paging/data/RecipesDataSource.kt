package piotr.michalkiewicz.mealplannerclient.recipes.paging.data

import androidx.paging.PagingSource
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.paging.api.RecipePagedService
import retrofit2.HttpException
import java.io.IOException

const val RECIPES_STARTING_PAGE_INDEX = 0

class RecipesDataSource(private val recipePagedService: RecipePagedService,
                        private val queryParam: String) :
        PagingSource<Int, MealTimeRecipe>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MealTimeRecipe> {

        val position = params.key ?: RECIPES_STARTING_PAGE_INDEX

        return try {
            val result = recipePagedService.getRecipesForDiet(queryParam, position)
            val resultData = result.recipes

            LoadResult.Page(
                data = resultData,
                prevKey = if (position == RECIPES_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (resultData.isEmpty()) null else position + 1
            )
        } catch (exception: IOException){
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}