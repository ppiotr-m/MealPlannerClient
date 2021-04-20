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
    private lateinit var recipeSharedViewModel: RecipeSharedViewModel

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
        recipeSharedViewModel = getViewModel()
        setViewModelForLayout()
        initCookingStepsRecyclerView(recipeSharedViewModel.getInstructionSteps())
        setupObservers()
    }

    private fun getViewModel(): RecipeSharedViewModel {
        val viewModel: RecipeSharedViewModel by navGraphViewModels(R.id.recipeNavGraph) {
            Injection.provideRecipesViewModelFactory(requireContext())
        }
        return viewModel
    }

    private fun setupObservers() {
        setupNavigateToCookingModeObserver()
        setupCookingModeNotAvailableObserver()
    }

    private fun setupNavigateToCookingModeObserver() {
        recipeSharedViewModel.navigateToCookingModeFragment.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(CookingStepsFragmentDirections.actionCookingStepsFragmentToCookingModeFragment())
                recipeSharedViewModel.resetNavigationToCookingModeFragment()
            }
        })
    }

    private fun setupCookingModeNotAvailableObserver() {
        recipeSharedViewModel.cookingModeNotAvailable.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
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