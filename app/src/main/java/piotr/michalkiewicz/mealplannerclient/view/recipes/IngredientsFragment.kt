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
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentIngredientsBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.view.recipes.adapters.IngredientsListAdapter
import piotr.michalkiewicz.mealplannerclient.view.recipes.interfaces.RecipeIngredientListOnCheckedChangeListener
import piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel.RecipeSharedViewModel

class IngredientsFragment : Fragment(), RecipeIngredientListOnCheckedChangeListener {

    private lateinit var binding: FragmentIngredientsBinding
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
        initIngedientsRecyclerView(viewModel.getRecipeIngredients())
        setupObservers()
    }

    private fun setupDataBinding(inflater: LayoutInflater) {
        binding = FragmentIngredientsBinding.inflate(inflater)
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
        setupObserverForNavigateBack()
        setupObserverForAddingEmptyIngredientsList()
    }

    private fun setupObserverForNavigateBack() {
        viewModel.navigateBackIngredientsToRecipe.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                findNavController().popBackStack()
            }
        })
    }

    private fun setupObserverForAddingEmptyIngredientsList() {
        viewModel.noIngredientsSelectedAddition.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                showNoItemSelectedToast()
            }
        })
    }

    private fun initIngedientsRecyclerView(data: List<RecipeIngredient>) {
        binding.recipeIngredientsListView.adapter =
            IngredientsListAdapter(requireContext(), data, this)
    }

    private fun showNoItemSelectedToast() {
        Toast.makeText(requireContext(), R.string.no_ingredient_selected, Toast.LENGTH_SHORT).show()
    }

    override fun onCheckboxClicked(item: RecipeIngredient, isChecked: Boolean) {
        viewModel.recipeIngredientListCheckboxClicked(item, isChecked)
    }
}
