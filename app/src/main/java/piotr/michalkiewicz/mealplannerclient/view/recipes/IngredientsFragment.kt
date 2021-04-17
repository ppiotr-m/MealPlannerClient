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
    private val recipeSharedViewModel: RecipeSharedViewModel by navGraphViewModels(R.id.recipeNavGraph) {
        Injection.provideRecipesViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIngredientsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        init()
    }

    private fun init() {
        initViewModel()
        initIngedientsRecyclerView(getRecipeIngredientsListFromViewModel())
        setupObservers()
    }

    private fun initViewModel() {
        binding.viewModel = recipeSharedViewModel
    }

    private fun getRecipeIngredientsListFromViewModel(): List<RecipeIngredient> {
        return recipeSharedViewModel.recipeData.value!!.recipeIngredients
    }

    private fun setupObservers() {
        recipeSharedViewModel.navigateBack.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().popBackStack()
                recipeSharedViewModel.resetNavigateBack()   //  TODO Not sure if this is the right solution
            }
        })
        recipeSharedViewModel.addingEmptyIngredientsList.observe(viewLifecycleOwner, {
            if (it) {
                showNoItemSelectedToast()
                recipeSharedViewModel.resetAddingToEmptyShoppingList()
            }
        })
    }

    private fun initIngedientsRecyclerView(data: List<RecipeIngredient>) {
        binding.recipeIngredientsListView.adapter =
            IngredientsListAdapter(requireContext(), data, this)
    }

    //  TODO Wire it to rest of code
    private fun showNoItemSelectedToast() {
        Toast.makeText(requireContext(), R.string.no_ingredient_selected, Toast.LENGTH_SHORT).show()

    }

    override fun onCheckboxClicked(item: RecipeIngredient, isChecked: Boolean) {
        recipeSharedViewModel.recipeIngredientListCheckboxClicked(item, isChecked)
    }
}