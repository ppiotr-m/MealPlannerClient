package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentIngredientsBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.view.recipes.adapters.RecipeIngredientsListAdapter
import piotr.michalkiewicz.mealplannerclient.view.recipes.interfaces.RecipeIngredientListOnCheckedChangeListener
import piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel.RecipeViewModel

class IngredientsFragment : Fragment(), RecipeIngredientListOnCheckedChangeListener {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var binding: FragmentIngredientsBinding

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
    }

    private fun initViewModel() {
        recipeViewModel = ViewModelProvider(
            requireActivity(),
            Injection.provideRecipesViewModelFactory(requireContext())
        ).get(RecipeViewModel::class.java)

        binding.viewModel = recipeViewModel
    }

    private fun getRecipeIngredientsListFromViewModel(): List<RecipeIngredient> {
        return recipeViewModel.recipeData.value!!.recipeIngredients
    }

    private fun initIngedientsRecyclerView(data: List<RecipeIngredient>) {
        binding.recipeIngredientsListRV.layoutManager = LinearLayoutManager(requireContext())
        binding.recipeIngredientsListRV.adapter = RecipeIngredientsListAdapter(data, this)
    }

    //  TODO Wire it to rest of code
    private fun showNoItemSelectedToast() {
        Toast.makeText(requireContext(), R.string.no_ingredient_selected, Toast.LENGTH_SHORT).show()

    }

    override fun onCheckboxSelected(item: RecipeIngredient, isChecked: Boolean) {
        recipeViewModel.recipeIngredientListCheckboxClicked(item, isChecked)
    }
}