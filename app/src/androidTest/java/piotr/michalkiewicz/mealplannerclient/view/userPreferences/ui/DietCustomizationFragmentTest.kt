package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelStore
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.launchFragmentInHiltContainer
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreferences
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.utils.DataBindingIdlingResource
import piotr.michalkiewicz.mealplannerclient.utils.monitorFragment
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility.AddEspressoIdlingResourceRule
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility.InitMyPreferencesRule
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility.TestUserPreferences

@HiltAndroidTest
@ExperimentalCoroutinesApi
class DietCustomizationFragmentTest {
    /* IMPORTANT!
   * If you want to run the tests log in first as bt30@bt.com user, with password provided
   * by Bartosz L. This will grant the tests token to make operations on our DB.
   * */
    // An idling resource that waits for Data Binding to have no pending bindings.
    private val dataBindingIdlingResource = DataBindingIdlingResource()
    private var testUserPreferences: UserPreferences = TestUserPreferences.getUserPreferences()
    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    private lateinit var testViewModel: UserPreferencesViewModel

    @get:Rule
    val hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var initMyPreferencesRule: InitMyPreferencesRule = InitMyPreferencesRule()

    @get:Rule
    var addEspressoIdlingResourceRule: AddEspressoIdlingResourceRule =
        AddEspressoIdlingResourceRule()

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    private fun registerDataBindingIdlingResource() {
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    private fun unregisterDataBindingIdlingResource() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    private fun setupNavController(originOfNavigation: String) {
        // This allows fragments to use by navGraphViewModels()
        navController.setViewModelStore(ViewModelStore())
        UiThreadStatement.runOnUiThread {
            navController.setGraph(R.navigation.nav_graph)
            when (originOfNavigation) {
                ConstantValues.USER_PREFERENCES_FRAGMENT -> {
                    navController.setCurrentDestination(
                        R.id.dietCustomizationFragmentInUserPreferencesGraphInUserPreferencesGraph
                    )
                }
                ConstantValues.PERSONALIZATION_PROCESS -> {
                    navController.setCurrentDestination(
                        R.id.dietCustomizationFragmentInPersonalizationProcess
                    )
                }
                else -> {
                    return@runOnUiThread
                }
            }
        }
    }

    private fun prepareArgsBundle(originOfNavigationValue: String): Bundle {
        val argumentOriginOfNavigationName = "originOfNavigation"
        return if (originOfNavigationValue == ConstantValues.USER_PREFERENCES_FRAGMENT) {
            Bundle().apply {
                putString(argumentOriginOfNavigationName, ConstantValues.USER_PREFERENCES_FRAGMENT)
            }
        } else {
            Bundle().apply {
                putString(argumentOriginOfNavigationName, ConstantValues.PERSONALIZATION_PROCESS)
            }
        }
    }

    private fun launchUserPreferencesFragment(originOfNavigationValue: String) {
        launchFragmentInHiltContainer<DietCustomizationFragment>(
            fragmentArgs = prepareArgsBundle(originOfNavigationValue),
            navHostController = navController
        ) {
            dataBindingIdlingResource.monitorFragment(this)
            viewModel.setUserPreferences(testUserPreferences)
            testViewModel = viewModel
        }
    }

    @Before
    fun setUp() {
        hiltRule.inject()
        registerDataBindingIdlingResource()
    }

    @After
    fun tearDown() {
        unregisterDataBindingIdlingResource()
    }

    @Test
    fun openFragment_displayAllDietNames() {
        setupNavController(ConstantValues.USER_PREFERENCES_FRAGMENT)
        launchUserPreferencesFragment(ConstantValues.USER_PREFERENCES_FRAGMENT)
        Espresso.onView(ViewMatchers.withId(R.id.linearButtonsDiet))
            .check(matches(ViewMatchers.hasMinimumChildCount(8)))
    }

    @Test
    fun openFragment_correctDietsAreDisplayed() {
        setupNavController(ConstantValues.USER_PREFERENCES_FRAGMENT)
        launchUserPreferencesFragment(ConstantValues.USER_PREFERENCES_FRAGMENT)
        Espresso.onView(ViewMatchers.withText("PALEO"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun clickDietButton_updatedUserDietPreference() {
        setupNavController(ConstantValues.USER_PREFERENCES_FRAGMENT)
        launchUserPreferencesFragment(ConstantValues.USER_PREFERENCES_FRAGMENT)
        Espresso.onView(ViewMatchers.withText("STANDARD"))
            .perform(ViewActions.click())
        ViewMatchers.assertThat(
            testViewModel.getUserDietPreference(),
            CoreMatchers.equalTo("standard")
        )
    }

    @Test
    fun clickOnDietButton_navigatedToUserPreferencesFragment() {
        setupNavController(ConstantValues.USER_PREFERENCES_FRAGMENT)
        launchUserPreferencesFragment(ConstantValues.USER_PREFERENCES_FRAGMENT)
        Espresso.onView(ViewMatchers.withText("STANDARD"))
            .perform(ViewActions.click())
        Truth.assertThat(navController.currentDestination?.id)
            .isEqualTo(R.id.userPreferencesFragment)
    }

    @Test
    fun clickOnDietButton_navigatedToNextStepInPersonalizationProcess() {
        setupNavController(ConstantValues.PERSONALIZATION_PROCESS)
        launchUserPreferencesFragment(ConstantValues.PERSONALIZATION_PROCESS)
        Espresso.onView(ViewMatchers.withText("STANDARD"))
            .perform(ViewActions.click())
        Truth.assertThat(navController.currentDestination?.id)
            .isEqualTo(R.id.disIngredientsCustomizationFragmentInPersonalizationProcess)
    }
}