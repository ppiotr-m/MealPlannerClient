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
    private lateinit var viewModel: RecipeSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupDataBinding(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = getViewModel()
        setViewModelForLayout()
        initCookingStepsRecyclerView(viewModel.getInstructionSteps())
        setupObservers()
    }

    private fun setupDataBinding(inflater: LayoutInflater) {
        binding = FragmentCookingStepsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
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
        viewModel.navigateToCookingModeFragment.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(R.id.action_cookingStepsFragment_to_cookingModeFragment)
            }
        })
    }

    private fun setupCookingModeNotAvailableObserver() {
        viewModel.cookingModeNotAvailable.observe(viewLifecycleOwner, {
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
        binding.viewModel = viewModel
    }

    private fun setUiToNoCookingMode() {
        binding.cookingModeBtn.visibility = View.GONE
    }
}
