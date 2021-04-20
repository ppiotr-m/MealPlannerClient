package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentCookingModeBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel.RecipeSharedViewModel

class CookingModeFragment : Fragment() {

    private lateinit var binding: FragmentCookingModeBinding
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
        setupObservers()
    }

    private fun setupDataBinding(inflater: LayoutInflater) {
        binding = FragmentCookingModeBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun getViewModel(): RecipeSharedViewModel {
        val viewModel: RecipeSharedViewModel by navGraphViewModels(R.id.recipeNavGraph) {
            Injection.provideRecipesViewModelFactory(requireContext())
        }
        return viewModel
    }

    private fun setViewModelForLayout() {
        binding.viewModel = viewModel
    }

    private fun setupObservers() {
        setupObserverForIsCurrentStepTheFirst()
        setupObserverForIsLastStepReached()
        setupObserverForCookingModeFinished()
        setupObserverForMiddleStep()
    }

    private fun setupObserverForIsCurrentStepTheFirst() {
        viewModel.firstStepReached.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                setButtonsForFirstStep()
            }
        })
    }

    private fun setupObserverForIsLastStepReached() {
        viewModel.lastStepReached.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                setButtonsForFinalStep()
            }
        })
    }

    private fun setupObserverForMiddleStep() {
        viewModel.middleStepReached.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                setButtonsForMiddleStep()
            }
        })
    }

    private fun setupObserverForCookingModeFinished() {
        viewModel.cookingModeFinished.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                findNavController().popBackStack()
                setButtonsForFirstStep()
            }
        })
    }

    private fun setButtonsForFinalStep() {
        binding.nextStepBtn.visibility = View.GONE
        binding.cookingModeFinishBtn.visibility = View.VISIBLE
    }

    private fun setButtonsForFirstStep() {
        binding.previousStepBtn.visibility = View.GONE
    }

    private fun setButtonsForMiddleStep() {
        binding.nextStepBtn.visibility = View.VISIBLE
        binding.previousStepBtn.visibility = View.VISIBLE
        binding.cookingModeFinishBtn.visibility = View.GONE
    }
}
