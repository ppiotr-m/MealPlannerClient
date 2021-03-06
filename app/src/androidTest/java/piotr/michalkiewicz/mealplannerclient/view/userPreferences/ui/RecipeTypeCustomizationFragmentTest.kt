package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelStore
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
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
class RecipeTypeCustomizationFragmentTest {
    /* IMPORTANT!
   * If you want to run the tests log in first as bt30@bt.com user, with password provided
   * by Bartosz L. This will grant the tests token to make operations on our DB.
   * */
    // An idling resource that waits for Data Binding to have no pending bindings.
    private val dataBindingIdlingResource = DataBindingIdlingResource()
    private var testUserPreferences: UserPreferences =
        TestUserPreferences.getUserPreferences()
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
                        R.id.recipeTypeCustomizationFragmentInUserPreferencesGraph
                    )
                }
                ConstantValues.PERSONALIZATION_PROCESS -> {
                    navController.setCurrentDestination(
                        R.id.recipeTypeCustomizationFragmentInPersonalizationProcess
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
        launchFragmentInHiltContainer<RecipeTypeCustomizationFragment>(
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
    fun openFragment_displayAllRecipeTypesNames() {
        setupNavController(ConstantValues.USER_PREFERENCES_FRAGMENT)
        launchUserPreferencesFragment(ConstantValues.USER_PREFERENCES_FRAGMENT)
        Espresso.onView(ViewMatchers.withId(R.id.linearLayoutRecipeTypesButtons))
            .check(ViewAssertions.matches(ViewMatchers.hasMinimumChildCount(6)))
    }

    @Test
    fun openFragment_correctRecipesTypesAreDisplayed() {
        setupNavController(ConstantValues.USER_PREFERENCES_FRAGMENT)
        launchUserPreferencesFragment(ConstantValues.USER_PREFERENCES_FRAGMENT)
        Espresso.onView(ViewMatchers.withText("SOUP"))
            .check(ViewAssertions.matches(ViewMatchers.isChecked()))
    }

    @Test
    fun clickRecipeType_updatedRecipeTypesPreference() {
        setupNavController(ConstantValues.USER_PREFERENCES_FRAGMENT)
        launchUserPreferencesFragment(ConstantValues.USER_PREFERENCES_FRAGMENT)
        Espresso.onView(ViewMatchers.withText("FISH"))
            .perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isChecked()))
        Espresso.onView(ViewMatchers.withId(R.id.confirmBtn)).perform(ViewActions.click())
        ViewMatchers.assertThat(
            testViewModel.getUserRecipeTypePreference()?.contains("fish"),
            CoreMatchers.equalTo(true)
        )
    }

    @Test
    fun clickOnApproveAndExitButton_navigatedToUserPreferencesFragment() {
        setupNavController(ConstantValues.USER_PREFERENCES_FRAGMENT)
        launchUserPreferencesFragment(ConstantValues.USER_PREFERENCES_FRAGMENT)
        Espresso.onView(ViewMatchers.withId(R.id.confirmBtn)).perform(ViewActions.click())
        Truth.assertThat(navController.currentDestination?.id)
            .isEqualTo(R.id.userPreferencesFragment)
    }

    @Test
    fun clickOnNextButton_navigatedToNextStepInPersonalization() {
        setupNavController(ConstantValues.PERSONALIZATION_PROCESS)
        launchUserPreferencesFragment(ConstantValues.PERSONALIZATION_PROCESS)
        Espresso.onView(ViewMatchers.withId(R.id.confirmBtn)).perform(ViewActions.click())
        Truth.assertThat(navController.currentDestination?.id)
            .isEqualTo(R.id.cuisineCustomizationFragmentInPersonalizationProcess)
    }
}
