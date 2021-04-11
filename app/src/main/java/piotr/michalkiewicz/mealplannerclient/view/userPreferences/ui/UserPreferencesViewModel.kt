package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.runBlocking
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreferences
import piotr.michalkiewicz.mealplannerclient.utils.EspressoIdlingResource
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.data.repository.UserPreferencesRepository
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Event
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Resource

class UserPreferencesViewModel
@ViewModelInject
constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _userPreferences: LiveData<Resource<UserPreferences?>> =
        userPreferencesRepository.getUserPreferences()
    private val loadTrigger = MutableLiveData(Unit)

    private val allDietsLiveData: LiveData<Resource<List<String>?>> =
        userPreferencesRepository.getAllDietsNames()

    val userPreferencesResource: LiveData<Resource<UserPreferences?>> = loadTrigger.switchMap {
        _userPreferences
    }

    private val allProductsLiveData: LiveData<Resource<List<String>?>> =
        userPreferencesRepository.getAllProductNames()

    private val allRecipeTypeLiveData: LiveData<Resource<List<String>?>> =
        userPreferencesRepository.getAllRecipeTypeNames()

    private val allAllergiesLiveData: LiveData<Resource<List<String>?>> =
        userPreferencesRepository.getAllAllergiesNames()

    private val allCuisinesLiveData: LiveData<Resource<List<String>?>> =
        userPreferencesRepository.getAllCuisinesNames()

    val dietsButtonsDataReady: MediatorLiveData<Event> = MediatorLiveData()
    val allergyButtonsDataReady: MediatorLiveData<Event> = MediatorLiveData()
    val cuisineButtonsDataReady: MediatorLiveData<Event> = MediatorLiveData()
    val dislikedButtonsDataReady: MediatorLiveData<Event> = MediatorLiveData()
    val recipeTypeCustomizationButtonsDataReady: MediatorLiveData<Event> = MediatorLiveData()
    val mealsNumbersCustomizationButtonsDataReady: MediatorLiveData<Event> = MediatorLiveData<Event>()

    private var dietsButtonsInitEventOccurred: Event? = Event()
    private var allergyButtonsInitEventOccurred: Event? = Event()
    private var cuisineButtonsInitEventOccurred: Event? = Event()
    private var dislikedIngredientsButtonsInitEventOccurred: Event? = Event()
    private var recipeTypeButtonsEventOccurred: Event? = Event()
    private var mealsNumbersButtonsEventOccurred: Event? = Event()

    private val _navigateToDiets = MutableLiveData<Event>()
    val navigateToDiets: LiveData<Event>
        get() = _navigateToDiets

    private val _navigateToRecipeTypes = MutableLiveData<Event>()
    val navigateToRecipeTypes: LiveData<Event>
        get() = _navigateToRecipeTypes

    private val _navigateToCookingPreferences = MutableLiveData<Event>()
    val navigateToCookingPreferences: LiveData<Event>
        get() = _navigateToCookingPreferences

    private val _navigateToDislikedIngredients = MutableLiveData<Event>()
    val navigateToDislikedIngredients: LiveData<Event>
        get() = _navigateToDislikedIngredients

    private val _navigateToAllergies = MutableLiveData<Event>()
    val navigateToAllergies: LiveData<Event>
        get() = _navigateToAllergies

    private val _navigateToCuisines = MutableLiveData<Event>()
    val navigateToCuisines: LiveData<Event>
        get() = _navigateToCuisines

    private fun refresh() {
        loadTrigger.value = Unit
    }

    fun setUserPreferences(userPreferences: UserPreferences) {
        _userPreferences =
            userPreferencesRepository.setUserPreferences(userPreferences)
        refresh()
    }

    fun getUserPreferences(): UserPreferences? {
        return userPreferencesResource.value?.data
    }

    fun getUserRecipeTypePreference(): List<String>? {
        return userPreferencesResource.value?.data?.recipeTypes
    }

    fun getUserAllergies(): List<String>? {
        return userPreferencesResource.value?.data?.allergies
    }

    fun getUserDislikedIngredients(): List<String>? {
        return userPreferencesResource.value?.data?.unlikeIngredients
    }

    fun getUserCuisines(): List<String>? {
        return userPreferencesResource.value?.data?.cuisine
    }

    fun getUserPortionPreferences(): Int? {
        return userPreferencesResource.value?.data?.portionPreferences
    }

    fun getUserCookingTimePreferences(): Int? {
        return userPreferencesResource.value?.data?.cookingTimePreference
    }

    fun getUserMealsPerPlanPreferences(): Int? {
        return userPreferencesResource.value?.data?.mealsPerMealPlanPreference
    }

    fun getUserDietPreference(): String? {
        return userPreferencesResource.value?.data?.diet
    }

    fun getAllDietsDataResource(): Resource<List<String>?>? {
        return allDietsLiveData.value
    }

    fun getAllAllergiesDataResource(): Resource<List<String>?>? {
        return allAllergiesLiveData.value
    }

    fun getAllCuisineDataResource(): Resource<List<String>?>? {
        return allCuisinesLiveData.value
    }

    fun getAllProductsDataResource(): Resource<List<String>?>? {
        return allProductsLiveData.value
    }

    fun getAllRecipeTypeDataResource(): Resource<List<String>?>? {
        return allRecipeTypeLiveData.value
    }

    fun markCustomizationDone() {
        if (getUserPreferences() != null) {
            runBlocking {
                val helperUserSettings = userPreferencesRepository.getUserSettings()
                helperUserSettings?.customizationDone = true
                helperUserSettings?.userPreferences = getUserPreferences()
                if (helperUserSettings != null) {
                    userPreferencesRepository.updateUserSettings(helperUserSettings)
                }
            }
        } else {
            Log.i("error", "no settings in db !!")
        }
    }

    fun initDietsButtonsData() {
        if (dietsButtonsInitEventOccurred == null) {
            dietsButtonsInitEventOccurred = Event()
        } else {
            dietsButtonsDataReady.addSource(userPreferencesResource) {
                triggerDietButtonsCreation()
            }
            dietsButtonsDataReady.addSource(allDietsLiveData) {
                triggerDietButtonsCreation()
            }
        }
    }

    private fun triggerDietButtonsCreation() {
        if (userPreferencesResource.value?.status == Resource.Status.SUCCESS) {
            if (allDietsLiveData.value?.status == Resource.Status.SUCCESS) {
                if (dietsButtonsInitEventOccurred != null) {
                    dietsButtonsDataReady.value = dietsButtonsInitEventOccurred
                    dietsButtonsInitEventOccurred = null
                }
            }
        }
    }

    fun initAllergyButtonsData() {
        if (allergyButtonsInitEventOccurred == null) {
            allergyButtonsInitEventOccurred = Event()
        } else {
            allergyButtonsDataReady.addSource(userPreferencesResource) {
                triggerAllergyButtonsCreation()
            }
            allergyButtonsDataReady.addSource(allAllergiesLiveData) {
                triggerAllergyButtonsCreation()
            }
        }
    }

    private fun triggerAllergyButtonsCreation() {
        if (userPreferencesResource.value?.status == Resource.Status.SUCCESS) {
            if (allAllergiesLiveData.value?.status == Resource.Status.SUCCESS) {
                if (allergyButtonsInitEventOccurred != null) {
                    allergyButtonsDataReady.value = allergyButtonsInitEventOccurred
                    allergyButtonsInitEventOccurred = null
                }
            }
        }
    }

    fun initCuisineCustomizationButtonsData() {
        if (cuisineButtonsInitEventOccurred == null) {
            cuisineButtonsInitEventOccurred = Event()
        } else {
            cuisineButtonsDataReady.addSource(userPreferencesResource) {
                triggerCuisineButtonsCreation()
            }
            cuisineButtonsDataReady.addSource(allCuisinesLiveData) {
                triggerCuisineButtonsCreation()
            }
        }
    }

    private fun triggerCuisineButtonsCreation() {
        if (userPreferencesResource.value?.status == Resource.Status.SUCCESS) {
            if (allCuisinesLiveData.value?.status == Resource.Status.SUCCESS) {
                if (cuisineButtonsInitEventOccurred != null) {
                    cuisineButtonsDataReady.value = cuisineButtonsInitEventOccurred
                    cuisineButtonsInitEventOccurred = null
                }
            }
        }
    }

    fun initDislikedButtonsData() {
        if (dislikedIngredientsButtonsInitEventOccurred == null) {
            dislikedIngredientsButtonsInitEventOccurred = Event()
        } else {
            dislikedButtonsDataReady.addSource(userPreferencesResource) {
                triggerDislikedButtonsCreation()
            }
            dislikedButtonsDataReady.addSource(allProductsLiveData) {
                triggerDislikedButtonsCreation()
            }
        }
    }

    private fun triggerDislikedButtonsCreation() {
        if (userPreferencesResource.value?.status == Resource.Status.SUCCESS) {
            if (allProductsLiveData.value?.status == Resource.Status.SUCCESS) {
                if (dislikedIngredientsButtonsInitEventOccurred != null) {
                    dislikedButtonsDataReady.value = dislikedIngredientsButtonsInitEventOccurred
                    dislikedIngredientsButtonsInitEventOccurred = null
                }
            }
        }
    }

    fun initRecipeTypeCustomizationButtonsData() {
        if (recipeTypeButtonsEventOccurred == null) {
            recipeTypeButtonsEventOccurred = Event()
        } else {
            recipeTypeCustomizationButtonsDataReady.addSource(userPreferencesResource) {
                triggerRecipeTypeButtonsCreation()
            }
            recipeTypeCustomizationButtonsDataReady.addSource(allRecipeTypeLiveData) {
                triggerRecipeTypeButtonsCreation()
            }
        }
    }

    private fun triggerRecipeTypeButtonsCreation() {
        if (userPreferencesResource.value?.status == Resource.Status.SUCCESS) {
            if (allRecipeTypeLiveData.value?.status == Resource.Status.SUCCESS) {
                if (recipeTypeButtonsEventOccurred != null) {
                    recipeTypeCustomizationButtonsDataReady.value = recipeTypeButtonsEventOccurred
                    recipeTypeButtonsEventOccurred = null
                }
            }
        }
    }

    fun initMealsNumbersCustomizationButtonsData() {
        if (mealsNumbersButtonsEventOccurred == null) {
            mealsNumbersButtonsEventOccurred = Event()
        } else {
            mealsNumbersCustomizationButtonsDataReady.addSource(userPreferencesResource) {
                triggerMealsNumbersButtonsCreation()
            }
        }
    }

    private fun triggerMealsNumbersButtonsCreation() {
        if (userPreferencesResource.value?.status == Resource.Status.SUCCESS) {
            if (mealsNumbersButtonsEventOccurred != null) {
                mealsNumbersCustomizationButtonsDataReady.value = mealsNumbersButtonsEventOccurred
                mealsNumbersButtonsEventOccurred = null
            }
        }
    }

    fun setCuisines(
        userCuisinesList: List<String>
    ) {
        val helperUserPreferences = getUserPreferences()?.copy(cuisine = userCuisinesList)
        if (helperUserPreferences != null) {
            setUserPreferences(helperUserPreferences)
        }
    }

    fun removeCuisine(
        cuisine: String
    ) {
        EspressoIdlingResource.increment()
        val helperUserCuisineList = getUserCuisines()?.toMutableList()
        if (helperUserCuisineList?.remove(cuisine) == true) {
            val helperUserPreferences = getUserPreferences()?.copy(cuisine = helperUserCuisineList)
            if (helperUserPreferences != null) {
                setUserPreferences(helperUserPreferences)
            }
        }
    }

    fun setDislikedIngredients(
        dislikedIngredientsList: List<String>
    ) {
        val helperUserPreferences =
            getUserPreferences()?.copy(unlikeIngredients = dislikedIngredientsList)
        if (helperUserPreferences != null) {
            setUserPreferences(helperUserPreferences)
        }
    }

    fun removeDislikedIngredient(
        dislikedIngredientName: String
    ) {
        EspressoIdlingResource.increment()
        val helperUserDislikedIngredients = getUserDislikedIngredients()?.toMutableList()
        if (helperUserDislikedIngredients?.remove(dislikedIngredientName) == true) {
            val helperUserPreferences =
                getUserPreferences()?.copy(unlikeIngredients = helperUserDislikedIngredients)
            if (helperUserPreferences != null) {
                setUserPreferences(helperUserPreferences)
            }
        }
    }

    fun setAllergies(
        allergiesList: List<String>
    ) {
        val helperUserPreferences = getUserPreferences()?.copy(allergies = allergiesList)
        if (helperUserPreferences != null) {
            setUserPreferences(helperUserPreferences)
        }
    }

    fun removeAllergy(
        allergyName: String?
    ) {
        EspressoIdlingResource.increment()
        val helperUserAllergies = getUserAllergies()?.toMutableList()
        if (helperUserAllergies?.remove(allergyName) == true) {
            val helperUserPreferences = getUserPreferences()?.copy(allergies = helperUserAllergies)
            if (helperUserPreferences != null) {
                setUserPreferences(helperUserPreferences)
            }
        }
    }

    fun setUserMealsPerPlanPreferences(userCookingPreferencesValues: Map<String, Int>) {
        var helperUserPreferences = getUserPreferences()
        if (helperUserPreferences != null) {
            for ((key, value) in userCookingPreferencesValues) {
                when (key) {
                    UserPreferences::portionPreferences.name -> {
                        helperUserPreferences =
                            (helperUserPreferences?.copy(portionPreferences = value) ?: return)
                    }
                    UserPreferences::cookingTimePreference.name -> {
                        helperUserPreferences =
                            (helperUserPreferences?.copy(cookingTimePreference = value) ?: return)
                    }
                    UserPreferences::mealsPerMealPlanPreference.name ->
                        helperUserPreferences =
                            (helperUserPreferences?.copy(mealsPerMealPlanPreference = value)
                                ?: return)
                }
            }
            if (helperUserPreferences != null) {
                setUserPreferences(helperUserPreferences)
            }
        }
    }

    fun setUserDietPreference(diet: String) {
        val helperUserPreferences = getUserPreferences()?.copy(diet = diet)
        if (helperUserPreferences != null) {
            setUserPreferences(helperUserPreferences)
        }
    }

    fun setUserRecipeTypes(
        recipeTypeList: List<String>
    ) {
        val helperUserPreferences = getUserPreferences()?.copy(recipeTypes = recipeTypeList)
        if (helperUserPreferences != null) {
            setUserPreferences(helperUserPreferences)
        }
    }

    fun removeRecipeType(
        recipeTypeName: String
    ) {
        EspressoIdlingResource.increment()
        val helperUserRecipeTypes = getUserRecipeTypePreference()?.toMutableList()
        if (helperUserRecipeTypes?.remove(recipeTypeName) == true) {
            val helperUserPreferences =
                getUserPreferences()?.copy(recipeTypes = helperUserRecipeTypes)
            if (helperUserPreferences != null) {
                setUserPreferences(helperUserPreferences)
            }
        }
    }

    fun navigateToDiets() {
        _navigateToDiets.value = Event()
    }

    fun navigateToCuisines() {
        _navigateToCuisines.value = Event()
    }

    fun navigateToCookingPreferences() {
        _navigateToCookingPreferences.value = Event()
    }

    fun navigateToDislikedIngredients() {
        _navigateToDislikedIngredients.value = Event()
    }

    fun navigateToAllergies() {
        _navigateToAllergies.value = Event()
    }

    fun navigateToRecipeTypes() {
        _navigateToRecipeTypes.value = Event()
    }

}