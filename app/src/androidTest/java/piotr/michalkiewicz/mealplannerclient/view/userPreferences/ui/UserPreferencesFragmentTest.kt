package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelStore
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.google.android.material.chip.Chip
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.launchFragmentInHiltContainer
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreferences
import piotr.michalkiewicz.mealplannerclient.utils.DataBindingIdlingResource
import piotr.michalkiewicz.mealplannerclient.utils.monitorFragment
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility.AddEspressoIdlingResourceRule
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility.InitMyPreferencesRule
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility.TestUserPreferences

@HiltAndroidTest
@ExperimentalCoroutinesApi
class UserPreferencesFragmentTest {
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

    private fun setupNavController() {
        // This allows fragments to use by navGraphViewModels()
        navController.setViewModelStore(ViewModelStore())
        runOnUiThread {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.userPreferencesFragment)
        }
    }

    private fun launchUserPreferencesFragment() {
        launchFragmentInHiltContainer<UserPreferencesFragment>(
            navHostController = navController
        ) {
            dataBindingIdlingResource.monitorFragment(this)
            testViewModel = viewModel
            viewModel.setUserPreferences(testUserPreferences)
        }
    }

    @Before
    fun setUp() {
        hiltRule.inject()
        registerDataBindingIdlingResource()
        setupNavController()
        launchUserPreferencesFragment()
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun tearDown() {
        unregisterDataBindingIdlingResource()
    }

    @Test
    fun clickEditCookingPreferencesBtn_navigatedToCookingPreferences() {
        onView(withId(R.id.editCookingPreferencesBtn)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.mealsNumberCustomizationFragmentInUserPreferencesGraph)
    }

    @Test
    fun clickDiet_navigateToDietPreferences() {
        onView(withId(R.id.dietTV)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.dietCustomizationFragmentInUserPreferencesGraphInUserPreferencesGraph)
    }

    @Test
    fun clickAddChipAllergies_navigateToAllergyPreferences() {
        onView(
            allOf(
                withParent(withId(R.id.allergiesChipGroup)),
                withText(R.string.add_chip)
            )
        ).perform(
            click()
        )
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.allergyCustomizationFragmentInUserPreferencesGraph)
    }

    @Test
    fun clickAddChipCuisineType_navigateToCuisineTypePreferences() {
        onView(
            allOf(
                withParent(withId(R.id.cuisineTypesChipGroup)),
                withText(R.string.add_chip)
            )
        )
            .perform(ViewActions.scrollTo())
            .perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.cuisineCustomizationFragmentInUserPreferencesGraph)
    }

    @Test
    fun clickAddChipDislikedIngredients_navigateToDislikedIngredientsPreferences() {
        onView(
            allOf(
                withParent(withId(R.id.avoidedIngredientsChipGroup)),
                withText(R.string.add_chip)
            )
        ).perform(
            click()
        )
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.dislikedIngredientsCustomizationFragmentInUserPreferencesGraph)
    }

    @Test
    fun clickAddChipRecipeTypes_navigateToRecipeTypePreferences() {
        onView(
            allOf(
                withParent(withId(R.id.recipeTypeChipGroup)),
                withText(R.string.add_chip)
            )
        )
            .perform(ViewActions.scrollTo())
            .perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.recipeTypeCustomizationFragmentInUserPreferencesGraph)
    }

    @Test
    fun updateUserPreferences_displayUpdatedDiet() {
        onView(withText("paleo")).check(matches(isDisplayed()))
    }

    @Test
    fun updateUserPreferences_displayUpdatedPortionPreference() {
        onView(withId(R.id.portionPreferenceTV)).check(matches(withText("1")))
    }

    @Test
    fun updateUserPreferences_displayUpdatedMaxCookingTime() {
        onView(withId(R.id.cookingTimePreferenceTV)).check(matches(withText("15")))
    }

    @Test
    fun updateUserPreferences_displayUpdatedMealPerMealplan() {
        onView(withId(R.id.portionPreferenceTV)).check(matches(withText("1")))
    }

    @Test
    fun updateUserPreferences_displayUpdatedAllergy1() {
        onView(allOf(withParent(withId(R.id.allergiesChipGroup)), withText("Dairy"))).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun updateUserPreferences_displayUpdatedDislikedIngredient1() {
        onView(
            allOf(
                withParent(withId(R.id.avoidedIngredientsChipGroup)),
                withText("majeranek")
            )
        ).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun updateUserPreferences_displayUpdatedDislikedIngredient2() {
        onView(
            allOf(
                withParent(withId(R.id.avoidedIngredientsChipGroup)),
                withText("kiwi")
            )
        ).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun updateUserPreferences_displayUpdatedRecipeType1() {
        onView(allOf(withParent(withId(R.id.recipeTypeChipGroup)), withText("dairy")))
            .perform(ViewActions.scrollTo())
            .check(
                matches(isDisplayed())
            )
    }

    @Test
    fun updateUserPreferences_displayUpdatedRecipeType2() {
        onView(allOf(withParent(withId(R.id.recipeTypeChipGroup)), withText("soup")))
            .perform(ViewActions.scrollTo())
            .check(
                matches(isDisplayed())
            )
    }

    @Test
    fun updateUserPreferences_displayUpdatedCuisineType1() {
        onView(allOf(withParent(withId(R.id.cuisineTypesChipGroup)), withText("Italian")))
            .perform(ViewActions.scrollTo())
            .check(
                matches(isDisplayed())
            )
    }

    @Test
    fun updateUserPreferences_displayUpdatedCuisineType2() {
        onView(allOf(withParent(withId(R.id.cuisineTypesChipGroup)), withText("Polish")))
            .perform(ViewActions.scrollTo())
            .check(
                matches(isDisplayed())
            )
    }

    @Test
    fun clickOnAllergy1RemoveButton_allergy1Removed() {
        onView(allOf(withParent(withId(R.id.allergiesChipGroup)), withText("Peanuts")))
            .perform(ClickCloseIconAction())
            .check(doesNotExist())
    }

    @Test
    fun clickOnDislikedIngredient1RemoveButton_dislikedIngredient1Removed() {
        onView(
            allOf(
                withParent(withId(R.id.avoidedIngredientsChipGroup)),
                withText("majeranek")
            )
        )
            .perform(ClickCloseIconAction())
            .check(doesNotExist())
    }

    @Test
    fun clickOnRecipeType1RemoveButton_recipeType1Removed() {
        onView(allOf(withParent(withId(R.id.recipeTypeChipGroup)), withText("dairy")))
            .perform(ViewActions.scrollTo())
            .perform(ClickCloseIconAction())
            .check(doesNotExist())
    }

    @Test
    fun clickOnCuisineTypeRemoveButton_cuisineType1Removed() {
        onView(
            allOf(
                withParent(withId(R.id.cuisineTypesChipGroup)),
                withText("Italian")
            )
        )
            .perform(ViewActions.scrollTo())
            .perform(ClickCloseIconAction())
            .check(doesNotExist())
    }
}

class ClickCloseIconAction : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return isAssignableFrom(Chip::class.java)
    }

    override fun getDescription(): String {
        return "click drawable "
    }

    override fun perform(uiController: UiController, view: View) {
        val chip = view as Chip//we matched
        chip.performCloseIconClick()
    }
}
