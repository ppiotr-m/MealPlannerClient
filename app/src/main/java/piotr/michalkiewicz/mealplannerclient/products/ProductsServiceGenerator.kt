package piotr.michalkiewicz.mealplannerclient.products

import piotr.michalkiewicz.mealplannerclient.auth.AuthServiceGenerator
import piotr.michalkiewicz.mealplannerclient.products.remote.ProductsAPI

class ProductsServiceGenerator : AuthServiceGenerator() {

    //  TODO Change access
    lateinit var productsAPI: ProductsAPI

    init {
        if (!::productsAPI.isInitialized) {
            val retrofit = retrofitBuilder()
            productsAPI = retrofit.create(ProductsAPI::class.java)
        }
    }
}