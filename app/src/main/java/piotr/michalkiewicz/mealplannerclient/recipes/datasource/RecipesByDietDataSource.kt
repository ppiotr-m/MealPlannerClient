package piotr.michalkiewicz.mealplannerclient.recipes.data_source

import androidx.paging.PagingSource
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.recipes.database.RecipesDao
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeBase
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.OnPrependDataLoadedListener
import retrofit2.HttpException
import java.io.IOException

const val RECIPES_STARTING_PAGE_INDEX = 1

/*
class RecipesByDietDataSource(private val recipesDao: RecipesDao,
                              private val queryParam: String,
                              private val onPrependDataLoadedListener: OnPrependDataLoadedListener) :
        PagingSource<Int, MealTimeRecipeBase>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MealTimeRecipeBase> {

        val position = params.key ?: RECIPES_STARTING_PAGE_INDEX

        return try {
            val resultData = ArrayList<List<MealTimeRecipeBase>>();

            if (position == RECIPES_STARTING_PAGE_INDEX) {
                onPrependDataLoadedListener.onPrependDataLoaded()
            }
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
 */