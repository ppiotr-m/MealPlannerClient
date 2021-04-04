package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentRecipeBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel.RecipeViewModel

class RecipeFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var binding: FragmentRecipeBinding

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
        recipeViewModel = ViewModelProvider(
            requireActivity(),
            Injection.provideViewModelFactory(requireContext())
        ).get(RecipeViewModel::class.java)

        val recipeId = retriveRecipeIdPassedWithNavigation()

        binding.viewModel = recipeViewModel

        if (recipeId != null) {
            recipeViewModel.initialize(recipeId)
        } else {
            handleNullRecipeId()
        }
    }

    private fun retriveRecipeIdPassedWithNavigation(): String? {
        val bundle = arguments ?: return null
        return RecipeFragmentArgs.fromBundle(bundle).recipeId
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
        recipeViewModel.recipeData.observe(viewLifecycleOwner, {
            //  TODO
        })

        recipeViewModel.recipeFeatchErrorOccurred.observe(viewLifecycleOwner, {
            if (it) {
                handleRecipeFetchingError()
            }
        })

        recipeViewModel.navigateToIngredientsFragment.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(R.id.action_recipeFragment_to_ingredientsFragment)
            }
        })
    }
}