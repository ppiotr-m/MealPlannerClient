package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelStore
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.launchFragmentInHiltContainer
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreferences
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PERSONALIZATION_PROCESS
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.USER_PREFERENCES_FRAGMENT
import piotr.michalkiewicz.mealplannerclient.utils.DataBindingIdlingResource
import piotr.michalkiewicz.mealplannerclient.utils.monitorFragment
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility.AddEspressoIdlingResourceRule
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility.InitMyPreferencesRule
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility.TestUserPreferences

@HiltAndroidTest
@ExperimentalCoroutinesApi
class DislikedIngredientsCustomizationFragmentTest {
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

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    private fun unregisterDataBindingIdlingResource() {
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    private fun setupNavController(originOfNavigation: String) {
        // This allows fragments to use by navGraphViewModels()
        navController.setViewModelStore(ViewModelStore())
        UiThreadStatement.runOnUiThread {
            navController.setGraph(R.navigation.nav_graph)
            when (originOfNavigation) {
                USER_PREFERENCES_FRAGMENT -> {
                    navController.setCurrentDestination(
                        R.id.dislikedIngredientsCustomizationFragmentInUserPreferencesGraph
                    )
                }
                PERSONALIZATION_PROCESS -> {
                    navController.setCurrentDestination(
                        R.id.disIngredientsCustomizationFragmentInPersonalizationProcess
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
        return if (originOfNavigationValue == USER_PREFERENCES_FRAGMENT) {
            Bundle().apply {
                putString(argumentOriginOfNavigationName, USER_PREFERENCES_FRAGMENT)
            }
        } else {
            Bundle().apply {
                putString(argumentOriginOfNavigationName, PERSONALIZATION_PROCESS)
            }
        }
    }

    private fun launchUserPreferencesFragment(originOfNavigationValue: String) {
        launchFragmentInHiltContainer<DislikedIngredientsCustomizationFragment>(
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
    fun openFragment_displayAllIngredientsNames() {
        setupNavController(USER_PREFERENCES_FRAGMENT)
        launchUserPreferencesFragment(USER_PREFERENCES_FRAGMENT)
        onView(withId(R.id.linearLayoutIngredientsButtons))
            .check(matches(hasMinimumChildCount(15)))
    }

    @Test
    fun openFragment_correctIngredientNameIsChecked() {
        setupNavController(USER_PREFERENCES_FRAGMENT)
        launchUserPreferencesFragment(USER_PREFERENCES_FRAGMENT)
        onView(withText("MAJERANEK")).check(matches(isChecked()))
    }

    @Test
    fun clickIngredient_updatedUserDislikedIngredientsList() {
        setupNavController(USER_PREFERENCES_FRAGMENT)
        launchUserPreferencesFragment(USER_PREFERENCES_FRAGMENT)
        onView(withText("MAJERANEK")).perform(click()).check(matches(isNotChecked()))
        onView(withId(R.id.confirmBtn)).perform(click())
        assertThat(
            testViewModel.getUserDislikedIngredients()?.contains("majeranek"),
            equalTo(false)
        )
    }

    @Test
    fun clickOnApproveAndExitButton_navigatedToUserPreferencesFragment() {
        setupNavController(USER_PREFERENCES_FRAGMENT)
        launchUserPreferencesFragment(USER_PREFERENCES_FRAGMENT)
        onView(withId(R.id.confirmBtn)).perform(click())
        Truth.assertThat(navController.currentDestination?.id)
            .isEqualTo(R.id.userPreferencesFragment)
    }

    @Test
    fun clickOnNext_navigatedToNextStepInPersonalization() {
        setupNavController(PERSONALIZATION_PROCESS)
        launchUserPreferencesFragment(PERSONALIZATION_PROCESS)
        onView(withId(R.id.confirmBtn)).perform(click())
        Truth.assertThat(navController.currentDestination?.id)
            .isEqualTo(R.id.recipeTypeCustomizationFragmentInPersonalizationProcess)
    }
}