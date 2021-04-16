package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentCookingModeBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel.RecipeViewModel

class CookingModeFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var binding: FragmentCookingModeBinding

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
        initViewModel()
        setupObservers()
    }

    private fun initViewModel() {
        recipeViewModel = ViewModelProvider(
            requireActivity(),
            Injection.provideRecipesViewModelFactory(requireContext())
        ).get(RecipeViewModel::class.java)

        binding.viewModel = recipeViewModel
    }

    private fun setupObservers() {
        recipeViewModel.isCurrentStepTheFirst.observe(viewLifecycleOwner, {
            if (it) {
                setButtonsForFirstStep()
            } else {
                binding.previousStepBtn.visibility = View.VISIBLE
            }
        })
        recipeViewModel.isLastStepReached.observe(viewLifecycleOwner, {
            if (it) {
                setButtonsForFinalStep()
            } else {
                showNextBtnAndHideFinishBtn()
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