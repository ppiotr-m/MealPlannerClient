package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelStore
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.launchFragmentInHiltContainer
import piotr.michalkiewicz.mealplannerclient.user.UserServiceGenerator
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings
import piotr.michalkiewicz.mealplannerclient.utils.DataBindingIdlingResource
import piotr.michalkiewicz.mealplannerclient.utils.EspressoIdlingResource
import piotr.michalkiewicz.mealplannerclient.utils.monitorFragment
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility.AddEspressoIdlingResourceRule
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility.InitMyPreferencesRule

@HiltAndroidTest
@ExperimentalCoroutinesApi
class StartCustomizationFragmentTest {
    /* IMPORTANT!
   * If you want to run the tests log in first as bt30@bt.com user, with password provided
   * by Bartosz L. This will grant the tests token to make operations on our DB.
   * */
    // An idling resource that waits for Data Binding to have no pending bindings.
    private val dataBindingIdlingResource = DataBindingIdlingResource()
    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    private lateinit var userServiceGenerator: UserServiceGenerator

    @get:Rule
    val hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var initMyPreferencesRule: InitMyPreferencesRule = InitMyPreferencesRule()

    @get:Rule
    var addEspressoIdlingResourceRule: AddEspressoIdlingResourceRule =
        AddEspressoIdlingResourceRule()

    private fun prepareUserSettings() {
        runBlocking {
            val helperUserSettings = userServiceGenerator.getUserSettings()
            helperUserSettings?.customizationDone = false
            if (helperUserSettings != null) {
                userServiceGenerator.updateUserSettings(helperUserSettings)
            }
        }
    }

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    private fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    private fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    private fun setupNavController() {
        // This allows fragments to use by navGraphViewModels()
        navController.setViewModelStore(ViewModelStore())
        UiThreadStatement.runOnUiThread {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.startCustomizationFragment)
        }
    }

    private fun launchUserPreferencesFragment() {
        launchFragmentInHiltContainer<StartCustomizationFragment>(
            navHostController = navController
        ) {
            dataBindingIdlingResource.monitorFragment(this)
        }
    }

    @Before
    fun setUp() {
        hiltRule.inject()
        userServiceGenerator = UserServiceGenerator()
        registerIdlingResource()
        setupNavController()
        launchUserPreferencesFragment()
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun tearDown() {
        unregisterIdlingResource()
    }

    @Test
    fun clickOnStartCustomization_navigatedToDietCustomizationFragment() {
        onView(withId(R.id.startCustomizationBtn)).perform(click())
        Truth.assertThat(navController.currentDestination?.id)
            .isEqualTo(R.id.dietCustomizationFragmentInPersonalizationProcess)
    }

    @Test
    fun clickOnSkipCustomization_navigatedToHomeScreenFragment() {
        onView(withId(R.id.skipCustomizationBtn)).perform(click())
        Truth.assertThat(navController.currentDestination?.id)
            .isEqualTo(R.id.homeScreenFragment)
    }

    @Test
    fun clickOnSkipCustomization_markedCustomizationDone() {
        var helperUserSettings: UserSettings?
        prepareUserSettings()
        onView(withId(R.id.skipCustomizationBtn)).perform(click())
        runBlocking {
            helperUserSettings = userServiceGenerator.getUserSettings()
        }
        Truth.assertThat(helperUserSettings?.customizationDone)
            .isEqualTo(true)
    }

    @Test
    fun clickOnDoItLater_navigatedToHomeScreenFragment() {
        onView(withId(R.id.putAwayCustomizationBtn)).perform(click())
        Truth.assertThat(navController.currentDestination?.id)
            .isEqualTo(R.id.homeScreenFragment)
    }
}