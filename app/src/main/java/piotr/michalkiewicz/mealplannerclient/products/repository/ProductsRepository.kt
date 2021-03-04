package piotr.michalkiewicz.mealplannerclient.products.repository

import piotr.michalkiewicz.mealplannerclient.products.ProductsServiceGenerator
import piotr.michalkiewicz.mealplannerclient.products.model.BasicFoodItemData
import retrofit2.Response

class ProductsRepository {

    val productsService = ProductsServiceGenerator().productsAPI

    suspend fun searchProduct(name: String): Response<List<BasicFoodItemData>>? {
        return productsService.getProductsForName(name)
    }
}