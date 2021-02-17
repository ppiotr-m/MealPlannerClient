package piotr.michalkiewicz.mealplannerclient.user.model

import com.google.gson.annotations.SerializedName
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.io.Serializable

@Module
@InstallIn(ApplicationComponent::class)
data class UserSettings(
    @SerializedName("nutritionProfileSettings") var nutritionProfileSettings: NutritionProfileSettings,
    @SerializedName("eatenRecipes") var eatenRecipes: List<String>,
    @SerializedName("language") var language: String,
    @SerializedName("location") var location: String?,
    @SerializedName("customizationDone") var customizationDone: Boolean,
    @SerializedName("sex") var sex: String?,
    @SerializedName("userPreferences") var userPreferences: UserPreferences?
) : Serializable