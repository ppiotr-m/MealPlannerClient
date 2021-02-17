package piotr.michalkiewicz.mealplannerclient.view.userPreferences.di

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import piotr.michalkiewicz.mealplannerclient.R

@Module
@InstallIn(ActivityComponent::class)
object NavModule {

    @Provides
    fun provideNavController(activity: Activity): NavController {
        return activity.findNavController(R.id.nav_host_fragment_container)
    }
}