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
    private lateinit var recipeSharedViewModel: RecipeSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCookingModeBinding.inflate(inflater)

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
        setupObservers()
    }

    private fun getViewModel(): RecipeSharedViewModel {
        val viewModel: RecipeSharedViewModel by navGraphViewModels(R.id.recipeNavGraph) {
            Injection.provideRecipesViewModelFactory(requireContext())
        }
        return viewModel
    }

    private fun setViewModelForLayout() {
        binding.viewModel = recipeSharedViewModel
    }

    private fun setupObservers() {
        setupObserverForIsCurrentStepTheFirst()
        setupObserverForIsLastStepReached()
        setupObserverForCookingModeFinished()
    }

    private fun setupObserverForIsCurrentStepTheFirst() {
        recipeSharedViewModel.isCurrentStepTheFirst.observe(viewLifecycleOwner, {
            if (it) {
                setButtonsForFirstStep()
            } else {
                binding.previousStepBtn.visibility = View.VISIBLE
            }
        })
    }

    private fun setupObserverForIsLastStepReached() {
        recipeSharedViewModel.isLastStepReached.observe(viewLifecycleOwner, {
            if (it) {
                setButtonsForFinalStep()
            } else {
                showNextBtnAndHideFinishBtn()
            }
        })
    }

    private fun setupObserverForCookingModeFinished() {
        recipeSharedViewModel.cookingModeFinished.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                findNavController().popBackStack()
                setButtonsForFirstStep()
                recipeSharedViewModel.resetLastStepReached()
                recipeSharedViewModel.resetIsFirstStep()
//                recipeSharedViewModel.resetFinishCooking()
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

    private fun showNextBtnAndHideFinishBtn() {
        binding.nextStepBtn.visibility = View.VISIBLE
        binding.cookingModeFinishBtn.visibility = View.GONE
    }
}