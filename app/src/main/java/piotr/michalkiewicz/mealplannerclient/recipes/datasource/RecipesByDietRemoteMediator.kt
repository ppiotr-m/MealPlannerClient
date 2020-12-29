package piotr.michalkiewicz.mealplannerclient.recipes.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeItemRemoteKeys
import piotr.michalkiewicz.mealplannerclient.utils.MealTimeDatabase
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class RecipesByDietRemoteMediator(
    private val recipeAPI: RecipeAPI,
    private val recipeDB: MealTimeDatabase,
    private val queryParam: String,
    private val initialPage: Int = 0
) : RemoteMediator<Int, MealTimeRecipe>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MealTimeRecipe>
    ): MediatorResult {

        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: initialPage
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                        ?: throw InvalidObjectException("Result is empty")
                    remoteKeys.nextKey ?: return MediatorResult.Success(true)
                }
            }

            val pageNr =
                state.pages.size // might be state.pages.size * pageSize if current doesnt work
            val response = recipeAPI.getRecipesPageForDiet(queryParam, pageNr)

            val endOfPagingReached = response.recipes.isEmpty()

            recipeDB.recipesDao().insertRecipes(response.recipes.sortedBy {
                it.id
            })

            recipeDB.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    recipeDB.recipesRemoteKeysDao().clearRemoteKeys()
                    recipeDB.recipesDao().deleteAllRecipes()
                }
                val prevKey = if (page == initialPage) null else page - 1
                val nextKey = if (endOfPagingReached) null else page + 1
                val keys = response.recipes.map {
                    RecipeItemRemoteKeys(recipeId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                recipeDB.recipesRemoteKeysDao().insertAll(keys)
                recipeDB.recipesDao().insertRecipes(response.recipes)
            }

            MediatorResult.Success(
                endOfPaginationReached = endOfPagingReached
            )
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MealTimeRecipe>): RecipeItemRemoteKeys? {
        return state.lastItemOrNull()?.let { recipe ->
            recipeDB.withTransaction {
                recipeDB.recipesRemoteKeysDao().remoteKeysByNewsId(recipe.id)
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MealTimeRecipe>): RecipeItemRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                recipeDB.withTransaction { recipeDB.recipesRemoteKeysDao().remoteKeysByNewsId(id) }
            }
        }
    }
}