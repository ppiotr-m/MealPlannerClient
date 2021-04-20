package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentRecipeBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel.RecipeSharedViewModel

class RecipeFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBinding
    private lateinit var recipeSharedViewModel: RecipeSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        init()
    }

    private fun init() {
        recipeSharedViewModel = getViewModel()
        setupViewModelForLayout()
        setDataForViewModel()
        setupObservers()
    }

    private fun setDataForViewModel() {
        val recipeId = retriveRecipeIdPassedWithNavigation()
        if (recipeId != null) {
            recipeSharedViewModel.initialize(recipeId)
        } else {
            handleNullRecipeId()
        }
    }

    private fun setupViewModelForLayout() {
        binding.viewModel = recipeSharedViewModel
    }

    private fun getViewModel(): RecipeSharedViewModel {
        val viewModel: RecipeSharedViewModel by navGraphViewModels(R.id.recipeNavGraph) {
            Injection.provideRecipesViewModelFactory(requireContext())
        }
        return viewModel
    }

    private fun retriveRecipeIdPassedWithNavigation(): String? {
        return arguments?.getString("recipeId")
    }

    private fun handleNullRecipeId() {
        Toast.makeText(requireContext(), R.string.general_unexpected_error, Toast.LENGTH_LONG)
            .show()
        findNavController().popBackStack()
    }

    private fun handleRecipeFetchingError() {
        Toast.makeText(requireContext(), R.string.recipe_fetch_error, Toast.LENGTH_LONG)
            .show()
        findNavController().popBackStack()
    }

    private fun setupObservers() {
        setupObserverForRecipeData()
        setupObserverForRecipeFetchError()
        setupObserverForNavigationToIngredientsFragment()
        setupObserverForNavigationToCookingStepsFragment()
    }

    private fun setupObserverForRecipeData() {
        recipeSharedViewModel.recipeData.observe(viewLifecycleOwner, {
            enableButtons()
        })
    }

    private fun setupObserverForRecipeFetchError() {
        recipeSharedViewModel.recipeFetchErrorOccurred.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                handleRecipeFetchingError()
            }
        })
    }

    private fun setupObserverForNavigationToIngredientsFragment() {
        recipeSharedViewModel.navigateToIngredientsFragment.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                recipeSharedViewModel.resetNavigationToIngredientsFragment()
                findNavController().navigate(RecipeFragmentDirections.actionRecipeFragmentToIngredientsFragment())
            }
        })
    }

    private fun setupObserverForNavigationToCookingStepsFragment() {
        recipeSharedViewModel.navigateToCookingStepsFragment.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                recipeSharedViewModel.resetNavigationToCookingStepsFragment()
                findNavController().navigate(RecipeFragmentDirections.actionRecipeFragmentToCookingStepsFragment())
            }
        })
    }

    private fun disableButtons() {
        binding.goToIngredientsBtn.isEnabled = false
        binding.goToCookingStepsBtn.isEnabled = false
    }

    private fun enableButtons() {
        binding.goToIngredientsBtn.isEnabled = true
        binding.goToCookingStepsBtn.isEnabled = true
    }
}