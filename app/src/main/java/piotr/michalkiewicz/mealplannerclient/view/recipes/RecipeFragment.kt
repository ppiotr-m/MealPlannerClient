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
    private val recipeSharedViewModel: RecipeSharedViewModel by navGraphViewModels(R.id.recipeNavGraph) {
        Injection.provideRecipesViewModelFactory(requireContext())
    }

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
        initViewModel()
        setupObservers()
    }

    private fun initViewModel() {
//        recipeViewModel = ViewModelProvider(
//            this,
//            Injection.provideRecipesViewModelFactory(requireContext())
//        ).get(RecipeViewModel::class.java)
        binding.viewModel = recipeSharedViewModel

        val recipeId = retriveRecipeIdPassedWithNavigation()
        if (recipeId != null) {
            recipeSharedViewModel.initialize(recipeId)
        } else {
            handleNullRecipeId()
        }
    }

    private fun retriveRecipeIdPassedWithNavigation(): String? {
        return arguments?.getString("recipeId")
    }

    private fun handleNullRecipeId() {
        Toast.makeText(requireContext(), R.string.general_unexpected_error, Toast.LENGTH_LONG)
            .show()
        findNavController().popBackStack()
    }

    //  TODO Implement specific logic
    private fun handleRecipeFetchingError() {
        Toast.makeText(requireContext(), R.string.recipe_fetch_error, Toast.LENGTH_LONG)
            .show()
        findNavController().popBackStack()
    }

    private fun setupObservers() {
        recipeSharedViewModel.recipeData.observe(viewLifecycleOwner, {
            //  TODO
            enableButtons()
        })

        recipeSharedViewModel.recipeFeatchErrorOccurred.observe(viewLifecycleOwner, {
            if (it) {
                handleRecipeFetchingError()
            }
        })

        recipeSharedViewModel.navigateToIngredientsFragment.observe(viewLifecycleOwner, {
            if (it) {
                recipeSharedViewModel.resetNavigationToIngredientsFragment()
                findNavController().navigate(R.id.action_recipeFragment_to_ingredientsFragment)
            }
        })

        recipeSharedViewModel.navigateToCookingStepsFragment.observe(viewLifecycleOwner, {
            if (it) {
                recipeSharedViewModel.resetNavigationToCookingStepsFragment()
                findNavController().navigate(R.id.action_recipeFragment_to_cookingStepsFragment)
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