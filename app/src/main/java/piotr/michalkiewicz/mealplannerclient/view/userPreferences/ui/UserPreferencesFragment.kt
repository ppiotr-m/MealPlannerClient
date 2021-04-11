package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentUserPreferencesBinding
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreferences
import piotr.michalkiewicz.mealplannerclient.utils.EspressoIdlingResource
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Resource

@AndroidEntryPoint
class UserPreferencesFragment : Fragment() {

    private lateinit var binding: FragmentUserPreferencesBinding

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel: UserPreferencesViewModel by navGraphViewModels(R.id.UserPreferencesGraph) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpDataBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userPreferencesViewModel = viewModel
        initAllLiveDataObservers()
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_preferences,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initAllLiveDataObservers() {
        startObservingUserPreferencesLiveData()

        startObservingNavigateToCookingPreferencesLiveData()
        startObservingNavigateToDietsLiveData()
        startObservingNavigateToAllergiesLiveData()
        startObservingNavigateToDislikedIngredientsLiveData()
        startObservingNavigateToRecipeTypeLiveData()
        startObservingNavigateToCuisinesLiveData()
    }

    private fun startObservingNavigateToRecipeTypeLiveData() {
        viewModel.navigateToRecipeTypes.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    UserPreferencesFragmentDirections.actionUserPreferencesFragmentToRecipeTypeCustomizationFragment()
                )
            }
        })
    }

    private fun startObservingNavigateToCuisinesLiveData() {
        viewModel.navigateToCuisines.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    UserPreferencesFragmentDirections.actionUserPreferencesFragmentToCuisineCustomizationFragment2()
                )
            }
        })
    }

    private fun startObservingNavigateToDietsLiveData() {
        viewModel.navigateToDiets.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    UserPreferencesFragmentDirections.actionUserPreferencesFragmentToDietCustomizationFragment2()
                )
            }
        })
    }

    private fun startObservingNavigateToCookingPreferencesLiveData() {
        viewModel.navigateToCookingPreferences.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    UserPreferencesFragmentDirections.actionUserPreferencesFragmentToMealsNumberCustomizationFragment()
                )
            }
        })
    }

    private fun startObservingNavigateToAllergiesLiveData() {
        viewModel.navigateToAllergies.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    UserPreferencesFragmentDirections.actionUserPreferencesFragmentToAllergyCustomizationFragment2()
                )
            }
        })
    }

    private fun startObservingNavigateToDislikedIngredientsLiveData() {
        viewModel.navigateToDislikedIngredients.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    UserPreferencesFragmentDirections.actionUserPreferencesFragmentToDislikedIngredientsCustomizationFragment()
                )
            }
        })
    }

    private fun startObservingUserPreferencesLiveData() {
        EspressoIdlingResource.increment()
        viewModel.userPreferencesResource.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Log.i("UserPreferencesFragment", "Success!")
                    if (it != null) {
                        inflateAllChipGroups(it)
                        EspressoIdlingResource.decrement()
                    }
                }
                Resource.Status.ERROR -> {
                    Log.i("UserPreferencesFragment", "Error!")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    EspressoIdlingResource.decrement()
                }
                Resource.Status.LOADING ->
                    Log.i("UserPreferencesFragment", "Loading")
            }
        })
    }

    private fun inflateAllChipGroups(it: Resource<UserPreferences?>) {
        inflateDislikedIngredientsChipGroup(it.data?.unlikeIngredients)
        inflateAllergiesChipGroup(it.data?.allergies)
        inflateRecipeTypeChipGroup(it.data?.recipeTypes)
        inflateCuisineTypeChipGroup(it.data?.cuisine)
    }

    private fun inflateDislikedIngredientsChipGroup(dislikedIngredientsList: List<String>?) {
        if (dislikedIngredientsList != null) {
            addDislikedIngredientsChips(dislikedIngredientsList)
            addPlusButtonToDislikedIngredientsChips()
        }
    }

    private fun addDislikedIngredientsChips(dislikedIngredientsList: List<String>?) {
        val chipGroup = binding.avoidedIngredientsChipGroup
        val inflator = LayoutInflater.from(chipGroup.context)

        chipGroup.removeAllViews()
        val children = dislikedIngredientsList?.map { dislikedIngredientName ->
            val chip =
                inflator.inflate(R.layout.chip_element_layout, chipGroup, false) as Chip
            chip.text = dislikedIngredientName
            chip.tag = dislikedIngredientName
            addOnClickListenerToDislikedIngredientChip(chip, dislikedIngredientName)
            chip
        }
        if (children != null) {
            for (chip in children) {
                chipGroup.addView(chip)
            }
        }
    }

    private fun addOnClickListenerToDislikedIngredientChip(
        chip: Chip,
        dislikedIngredientName: String
    ) {
        chip.setOnCloseIconClickListener {
            viewModel.removeDislikedIngredient(
                dislikedIngredientName
            )
        }
    }

    private fun addPlusButtonToDislikedIngredientsChips() {
        val chipGroup = binding.avoidedIngredientsChipGroup
        val inflator = LayoutInflater.from(chipGroup.context)

        val chip =
            inflator.inflate(R.layout.add_chip_element_layout, chipGroup, false) as Chip
        chip.text = resources.getString(R.string.add_chip)
        chip.setOnClickListener {
            viewModel.navigateToDislikedIngredients()
        }
        chipGroup.addView(chip)
    }

    private fun inflateAllergiesChipGroup(allergiesList: List<String>?) {

        if (allergiesList != null) {
            addAllergiesChips(allergiesList)
            addPlusButtonToAllergiesChips()
        }
    }

    private fun addAllergiesChips(allergiesList: List<String>?) {
        val chipGroup = binding.allergiesChipGroup
        val inflator = LayoutInflater.from(chipGroup.context)

        chipGroup.removeAllViews()
        val children = allergiesList?.map { allergyName ->
            val chip =
                inflator.inflate(R.layout.chip_element_layout, chipGroup, false) as Chip
            chip.text = allergyName
            chip.tag = allergyName
            chip.setOnCloseIconClickListener {
                viewModel.removeAllergy(allergyName)
            }
            chip
        }
        if (children != null) {
            for (chip in children) {
                chipGroup.addView(chip)
            }
        }
    }

    private fun addPlusButtonToAllergiesChips() {
        val chipGroup = binding.allergiesChipGroup
        val inflator = LayoutInflater.from(chipGroup.context)

        val chip =
            inflator.inflate(R.layout.add_chip_element_layout, chipGroup, false) as Chip
        chip.text = resources.getString(R.string.add_chip)
        addOnClickListenerToAllergiesChipsAddButton(chip)
        chipGroup.addView(chip)
    }

    private fun addOnClickListenerToAllergiesChipsAddButton(chip: Chip) {
        chip.setOnClickListener {
            viewModel.navigateToAllergies()
        }
    }

    private fun inflateRecipeTypeChipGroup(recipeTypes: List<String>?) {
        addRecipeTypeChips(recipeTypes)
        addPlusButtonToRecipeTypeChips()
    }

    private fun addRecipeTypeChips(recipeTypes: List<String>?) {

        val chipGroup = binding.recipeTypeChipGroup
        val inflator = LayoutInflater.from(chipGroup.context)

        if (recipeTypes != null) {
            val children = recipeTypes.map { recipeTypeName ->
                val chip =
                    inflator.inflate(R.layout.chip_element_layout, chipGroup, false) as Chip
                chip.text = recipeTypeName
                chip.tag = recipeTypeName
                addOnCloseIconClickListenerToRecipeTypeChips(chip, recipeTypeName)
                chip
            }
            chipGroup.removeAllViews()
            for (chip in children) {
                chipGroup.addView(chip)
            }
        }
    }

    private fun addOnCloseIconClickListenerToRecipeTypeChips(chip: Chip, recipeTypeName: String) {
        chip.setOnCloseIconClickListener {
            viewModel.removeRecipeType(recipeTypeName)
        }
    }

    private fun addPlusButtonToRecipeTypeChips() {
        val chipGroup = binding.recipeTypeChipGroup
        val inflator = LayoutInflater.from(chipGroup.context)

        val chip =
            inflator.inflate(R.layout.add_chip_element_layout, chipGroup, false) as Chip
        chip.text = resources.getString(R.string.add_chip)
        chip.setOnClickListener {
            viewModel.navigateToRecipeTypes()
        }
        chipGroup.addView(chip)
    }

    private fun inflateCuisineTypeChipGroup(cuisineTypes: List<String>?) {
        addCuisineTypeChips(cuisineTypes)
        addPlusButtonToCuisineTypeChips()
    }

    private fun addCuisineTypeChips(cuisineTypes: List<String>?) {
        if (cuisineTypes != null) {

            val chipGroup = binding.cuisineTypesChipGroup
            val inflator = LayoutInflater.from(chipGroup.context)

            val children = cuisineTypes.map { cuisineTypeName ->
                val chip =
                    inflator.inflate(R.layout.chip_element_layout, chipGroup, false) as Chip
                chip.text = cuisineTypeName
                chip.tag = cuisineTypeName
                chip.setOnCloseIconClickListener {
                    viewModel.removeCuisine(cuisineTypeName)
                }
                chip
            }
            chipGroup.removeAllViews()

            for (chip in children) {
                chipGroup.addView(chip)
            }
        }
    }

    private fun addPlusButtonToCuisineTypeChips() {
        val chipGroup = binding.cuisineTypesChipGroup
        val inflator = LayoutInflater.from(chipGroup.context)

        val chip =
            inflator.inflate(R.layout.add_chip_element_layout, chipGroup, false) as Chip
        chip.text = resources.getString(R.string.add_chip)
        chip.setOnClickListener {
            viewModel.navigateToCuisines()
        }
        chipGroup.addView(chip)
    }
}
