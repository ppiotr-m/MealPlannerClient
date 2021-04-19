package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentCookingStepsBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.model.InstructionStep
import piotr.michalkiewicz.mealplannerclient.view.recipes.adapters.CookingStepsAdapter
import piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel.RecipeSharedViewModel

class CookingStepsFragment : Fragment() {

    private lateinit var binding: FragmentCookingStepsBinding
    private val recipeSharedViewModel: RecipeSharedViewModel by navGraphViewModels(R.id.recipeNavGraph) {
        Injection.provideRecipesViewModelFactory(requireContext())
    }

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
        setViewModelForLayout()
        initCookingStepsRecyclerView(recipeSharedViewModel.getInstructionSteps())
        setupObservers()
    }

    private fun setupObservers() {
        setupNavigateToCookingModeObserver()
        setupCookingModeNotAvailableObserver()
    }

    private fun setupNavigateToCookingModeObserver() {
        recipeSharedViewModel.navigateToCookingModeFragment.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(R.id.action_cookingStepsFragment_to_cookingModeFragment)
                recipeSharedViewModel.resetNavigationToCookingModeFragment()
            }
        })
    }

    private fun setupCookingModeNotAvailableObserver() {
        recipeSharedViewModel.cookingModeNotAvailable.observe(viewLifecycleOwner, {
            if (it) {
                setUiToNoCookingMode()
            }
        })
    }

    private fun initCookingStepsRecyclerView(data: List<InstructionStep>) {
        binding.cookingStepsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.cookingStepsRV.adapter = CookingStepsAdapter(data)
    }

    private fun setViewModelForLayout() {
        binding.viewModel = recipeSharedViewModel
    }

    private fun setUiToNoCookingMode() {
        binding.cookingModeBtn.visibility = View.GONE
    }
}