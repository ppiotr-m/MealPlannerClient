package piotr.michalkiewicz.mealplannerclient.view.personalization.service

import piotr.michalkiewicz.mealplannerclient.auth.AuthServiceGenerator
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeServiceApi

class RecipeServiceValuesDownloader : AuthServiceGenerator() {

    lateinit var recipeServiceApi: RecipeServiceApi

    init {
        if (!::recipeServiceApi.isInitialized) {
            val retrofit = retrofitBuilder()
            recipeServiceApi = retrofit.create(RecipeServiceApi::class.java)
        }
    }

    fun getAllDietsNames(): List<String> {
        var result: List<String> = emptyList()

        val getProductsThread = Thread {
            result = (recipeServiceApi.getAllDiets().execute().body() ?: return@Thread)
        }
        getProductsThread.start()
        getProductsThread.join()
        return result
    }

    fun getAllRecipeTypesNames(): List<String> {
        var result: List<String> = emptyList()

        val getProductsThread = Thread {
            result = (recipeServiceApi.getAllRecipeTypes().execute().body() ?: return@Thread)
        }
        getProductsThread.start()
        getProductsThread.join()
        return result
    }

    fun getAllCuisinesNames(): List<String> {
        var result: List<String> = emptyList()

        val getProductsThread = Thread {
            result = (recipeServiceApi.getAllCuisines().execute().body() ?: return@Thread)
        }
        getProductsThread.start()
        getProductsThread.join()
        return result
    }

    fun getAllAllergiesNames(): List<String> {
        var result: List<String> = emptyList()

        val getProductsThread = Thread {
            result = (recipeServiceApi.getAllAllergies().execute().body() ?: return@Thread)
        }
        getProductsThread.start()
        getProductsThread.join()
        return result
    }

    fun getAllProductsNames(): List<String> {
        var result: List<String> = emptyList()

        val getProductsThread = Thread {
            result = (recipeServiceApi.getAllProducts().execute().body() ?: return@Thread)
        }
        getProductsThread.start()
        getProductsThread.join()
        return result
    }

    fun getAllProductsByName(name: String): List<String> {
        var result: List<String> = emptyList()

        val getProductsThread = Thread {
            result = (recipeServiceApi.getAllProductsByName(name).execute().body() ?: return@Thread)
        }
        getProductsThread.start()
        getProductsThread.join()
        return result
    }
}