package piotr.michalkiewicz.mealplannerclient.recipes.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.recipes.database.RecipesDatabase
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeBase

@ExperimentalPagingApi
class RecipesByDietRemoteMediator(private val recipeAPI: RecipeAPI,
                                  private val recipesDatabase: RecipesDatabase,
                                  private val queryParam: String) : RemoteMediator<Int, MealTimeRecipeBase>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MealTimeRecipe>): MediatorResult {

    }
}