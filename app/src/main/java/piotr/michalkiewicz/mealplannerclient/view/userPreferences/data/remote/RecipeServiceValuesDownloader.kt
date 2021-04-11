package piotr.michalkiewicz.mealplannerclient.view.userPreferences.data.remote

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeServiceApi
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Resource
import javax.inject.Inject

@InstallIn(ActivityComponent::class)
@Module
class RecipeServiceValuesDownloader @Inject constructor(
    private val recipeServiceApi: RecipeServiceApi,
    private val baseDataSourceImpl: BaseDataSource
) {
    suspend fun getAllDietsNames(): Resource<List<String>> =
        baseDataSourceImpl.getResult { recipeServiceApi.getAllDiets() }

    suspend fun getAllRecipeTypesNames(): Resource<List<String>> =
        baseDataSourceImpl.getResult { recipeServiceApi.getAllRecipeTypes() }

    suspend fun getAllCuisinesNames(): Resource<List<String>> =
        baseDataSourceImpl.getResult { recipeServiceApi.getAllCuisines() }

    suspend fun getAllAllergiesNames(): Resource<List<String>> =
        baseDataSourceImpl.getResult { recipeServiceApi.getAllAllergies() }

    suspend fun getAllProductsNames(): Resource<List<String>> =
        baseDataSourceImpl.getResult { recipeServiceApi.getAllProducts() }
}