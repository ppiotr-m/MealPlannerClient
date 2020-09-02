package piotr.michalkiewicz.mealplannerclient.auth

import android.graphics.Bitmap
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.AuthAuthenticator
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.AuthInterceptor
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.SignUpInterceptor
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipesPagedService
import piotr.michalkiewicz.mealplannerclient.user.service_generator.SignUpService
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Class build ready service with auth
 */

class ServiceGenerator {

    private lateinit var recipeService: piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeService
    private lateinit var userService: piotr.michalkiewicz.mealplannerclient.user.service_generator.UserService
    private lateinit var signUpService: SignUpService

    fun getRecipeApi(): piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeService {
        if (!::recipeService.isInitialized) {
            val retrofit = retrofitBuilder()
            recipeService = retrofit.create(piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeService::class.java)
        }
        return recipeService
    }

    fun getUserApi(): piotr.michalkiewicz.mealplannerclient.user.service_generator.UserService {
        if (!::userService.isInitialized) {
            val retrofit = retrofitBuilder()
            userService = retrofit.create(piotr.michalkiewicz.mealplannerclient.user.service_generator.UserService::class.java)
        }
        return userService
    }

    fun getSignUpApi(): SignUpService {
        if (!::signUpService.isInitialized) {
            val retrofit = signUpRetrofitBuilder()
            signUpService = retrofit.create(SignUpService::class.java)
        }
        return signUpService
    }

    private fun signUpRetrofitBuilder(): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(signUpOkHttpClient())
                .build()
    }

    private fun signUpOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(SignUpInterceptor())
                .build()
    }

    companion object{

        private val gson: Gson = GsonBuilder()
                .registerTypeAdapter(Bitmap::class.java, piotr.michalkiewicz.mealplannerclient.recipes.model.conversion.BinaryToBitmapConverter())
                .setLenient()
                .create()

        private fun okHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor())
                    .authenticator(AuthAuthenticator())
                    .build()
        }

        private fun retrofitBuilder(): Retrofit {
            return Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASE_URL)
                    .client(okHttpClient())
                    .build()
        }

        fun getRecipesPagedApi(): RecipesPagedService {
            return retrofitBuilder().create(RecipesPagedService::class.java)
        }
    }
}