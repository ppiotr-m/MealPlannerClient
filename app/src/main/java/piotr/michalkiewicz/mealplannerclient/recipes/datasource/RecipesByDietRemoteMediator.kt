package piotr.michalkiewicz.mealplannerclient.recipes.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.recipes.database.RecipesDatabase
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeBase
import java.io.IOException

@ExperimentalPagingApi
class RecipesByDietRemoteMediator(private val recipeAPI: RecipeAPI,
                                  private val recipeDB: RecipesDatabase,
                                  private val queryParam: String) : RemoteMediator<Int, MealTimeRecipeBase>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MealTimeRecipeBase>): MediatorResult {

        return try {
            val pageNr = state.pages.size // might be state.pages.size * pageSize if current doesnt work
            val response = recipeAPI.getRecipesPageForDiet(queryParam, pageNr)

            val endOfPagingReached = response.recipes.isEmpty()

            if(!endOfPagingReached){
                recipeDB.recipesDao().insertRecipes(response.recipes)
            }

            MediatorResult.Success(
                    endOfPaginationReached = endOfPagingReached
            )
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        }
    }
}