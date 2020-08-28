package piotr.michalkiewicz.mealplannerclient.recipes.paging.data

import androidx.paging.PagingSource
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.paging.api.RecipesPagedServiceGenerator
import piotr.michalkiewicz.mealplannerclient.recipes.service_generator.RecipeServiceGenerator
import retrofit2.HttpException
import java.io.IOException

class RecipesDataSource(private val recipeServiceGenerator: RecipesPagedServiceGenerator,
                        private val queryParam: String) :
        PagingSource<Int, MealTimeRecipe>() {

    const val RECIPES_STARTING_PAGE_INDEX = 0;

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MealTimeRecipe> {

        val position = params.key ?: RECIPES_STARTING_PAGE_INDEX

        return try {
            val result = recipeServiceGenerator.getRecipesForDiet(queryParam)
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