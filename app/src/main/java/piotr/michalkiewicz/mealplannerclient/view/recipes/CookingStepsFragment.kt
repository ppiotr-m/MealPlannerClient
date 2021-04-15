package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentCookingStepsBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.model.InstructionStep
import piotr.michalkiewicz.mealplannerclient.view.recipes.adapters.CookingStepsAdapter
import piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel.RecipeViewModel

class CookingStepsFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var binding: FragmentCookingStepsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCookingStepsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        init()
    }

    private fun init() {
        initViewModel()
        initCookingStepsRecyclerView(getCookingStepsListFromViewModel())
        setupObservers()
    }

    private fun setupObservers() {
        recipeViewModel.navigateToCookingModeFragment.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(R.id.action_cookingStepsFragment_to_cookingModeFragment)
                recipeViewModel.resetNavigationToCookingModeFragment()
            }
        })
    }

    private fun getCookingStepsListFromViewModel(): List<InstructionStep> {
        return recipeViewModel.recipeData.value!!.instructionSteps
    }

    private fun initCookingStepsRecyclerView(data: List<InstructionStep>) {
        binding.cookingStepsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.cookingStepsRV.adapter = CookingStepsAdapter(data)
    }

    private fun initViewModel() {
        recipeViewModel = ViewModelProvider(
            requireActivity(),
            Injection.provideRecipesViewModelFactory(requireContext())
        ).get(RecipeViewModel::class.java)

        binding.viewModel = recipeViewModel
    }
}