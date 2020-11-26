package piotr.michalkiewicz.mealplannerclient.recipes.data_source

import androidx.paging.PagingSource
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeBase
import retrofit2.HttpException
import java.io.IOException

class RecipesByTypeDataSource(private val recipeAPI: RecipeAPI,
                              private val queryParam: String) :
        PagingSource<Int, MealTimeRecipeBase>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MealTimeRecipeBase> {

        val position = params.key ?: RECIPES_STARTING_PAGE_INDEX

        return try {
            val resultData = recipeAPI.getRecipesPageForType("VEGETARIAN", position)

            LoadResult.Page(
                    data = resultData.recipes,
                    prevKey = if (position == RECIPES_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (resultData.recipes.isEmpty()) null else position + 1
            )
        } catch (exception: IOException){
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}