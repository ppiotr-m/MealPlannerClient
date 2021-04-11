package piotr.michalkiewicz.mealplannerclient.view.userPreferences.di

import android.graphics.Bitmap
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import piotr.michalkiewicz.mealplannerclient.auth.authenticator.AuthAuthenticator
import piotr.michalkiewicz.mealplannerclient.auth.interceptor.AuthInterceptor
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeServiceApi
import piotr.michalkiewicz.mealplannerclient.recipes.model.conversion.BinaryToBitmapConverter
import piotr.michalkiewicz.mealplannerclient.user.api.UserAPI
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.data.remote.BaseDataSource
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.data.remote.BaseDataSourceImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(ConstantValues.BASE_URL)
        .client(okHttpClient())
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .registerTypeAdapter(Bitmap::class.java, BinaryToBitmapConverter())
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserAPI = retrofit.create(UserAPI::class.java)

    @Provides
    @Singleton
    fun provideBaseDataSource(): BaseDataSource = BaseDataSourceImpl()

    @Provides
    @Singleton
    fun provideRecipeServiceApi(retrofit: Retrofit): RecipeServiceApi =
        retrofit.create(RecipeServiceApi::class.java)

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .authenticator(AuthAuthenticator())
        .build()

}