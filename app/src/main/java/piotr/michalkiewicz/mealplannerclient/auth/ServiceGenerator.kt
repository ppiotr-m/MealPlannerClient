package piotr.michalkiewicz.mealplannerclient.auth

import android.graphics.Bitmap
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.AuthAuthenticator
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.AuthInterceptor
import piotr.michalkiewicz.mealplannerclient.recipes.model.conversion.BinaryToBitmapConverter
import piotr.michalkiewicz.mealplannerclient.recipes.nameToDoNoRepo.RecipeService
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Class build ready service with auth
 */

class ServiceGenerator {

    private lateinit var recipeService: RecipeService

    fun getRecipeApi(): RecipeService {
        if (!::recipeService.isInitialized) {
            val retrofit = retrofitBuilder()
            recipeService = retrofit.create(RecipeService::class.java)
        }
        return recipeService
    }

    private fun retrofitBuilder(): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient())
                .build()
    }

    private val gson: Gson = GsonBuilder()
            .registerTypeAdapter(Bitmap::class.java, BinaryToBitmapConverter())
            .setLenient()
            .create()

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .authenticator(AuthAuthenticator())
                .build()
    }
}