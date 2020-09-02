package piotr.michalkiewicz.mealplannerclient.recipes.data_source

import androidx.paging.PagingSource
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipesPagedService
import retrofit2.HttpException
import java.io.IOException

const val RECIPES_STARTING_PAGE_INDEX = 0

class RecipesDataSource(private val recipePagedService: RecipesPagedService,
                        private val queryParam: String) :
        PagingSource<Int, piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe> {

        val position = params.key ?: RECIPES_STARTING_PAGE_INDEX

        return try {

            val resultData = recipePagedService.getRecipesPageForDiet("STANDARD", position)
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