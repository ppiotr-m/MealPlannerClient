package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentIngredientsBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.MainActivity
import piotr.michalkiewicz.mealplannerclient.view.recipes.adapters.RecipeIngredientsListAdapter
import piotr.michalkiewicz.mealplannerclient.view.recipes.interfaces.RecipeIngredientListOnCheckedChangeListener
import piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel.RecipeViewModel
import java.util.*

class IngredientsFragment : Fragment(), RecipeIngredientListOnCheckedChangeListener {

    private var data: ArrayList<RecipeIngredient>? = null
    private val productsOriginalNames = ArrayList<String>()
    private val selectedIngredients = ArrayList<RecipeIngredient>()

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
        initRecyclerView(getRecipeIngredientsListFromViewModel())
    }

    private fun initViewModel() {
        recipeViewModel = ViewModelProvider(
            requireActivity(),
            Injection.provideViewModelFactory(requireContext())
        ).get(RecipeViewModel::class.java)

        binding.viewModel = recipeViewModel
    }

    private fun getRecipeIngredientsListFromViewModel(): List<RecipeIngredient> {
        return recipeViewModel.recipeData.value!!.recipeIngredients
    }

    private fun initRecyclerView(data: List<RecipeIngredient>) {
        binding.recipeIngredientsListRV.layoutManager = LinearLayoutManager(requireContext())
        binding.recipeIngredientsListRV.adapter = RecipeIngredientsListAdapter(data, this)
    }

    private fun initProductsOriginalNamesList() {
        for (ingredient in data!!) {
            productsOriginalNames.add(ingredient.originalName)
        }
    }

    private fun setOnClickListeners() {
        binding.addProductsToShoppingListBtn.setOnClickListener { v: View? ->
            if (checkIfAnyIngredientSelected()) {
                showNoItemSelectedToast()
                return@setOnClickListener
            }
        }
    }

    private fun showNoItemSelectedToast() {
        Toast.makeText(requireContext(), R.string.no_ingredient_selected, Toast.LENGTH_SHORT).show()

    }

    private val shoppingListFromSharedPrefs: Array<RecipeIngredient>?
        private get() {
            val json =
                MainActivity.MY_PREFERENCES.getString(ConstantValues.SHOPPING_LIST_SHARED_PREF, "")
            if (json!!.isEmpty()) return null
            try {
                return Gson().fromJson(json, Array<RecipeIngredient>::class.java)
            } catch (e: ClassCastException) {
                Log.e(ConstantValues.TAG, e.localizedMessage)
                e.printStackTrace()
            }
            return null
        }

    private fun saveShoppingListToSharedPrefs(recipeIngredientList: List<RecipeIngredient>) {
        val dataInJson = Gson().toJson(recipeIngredientList)
        MainActivity.MY_PREFERENCES.edit()
            .putString(ConstantValues.SHOPPING_LIST_SHARED_PREF, dataInJson).commit()
    }

    private fun checkIfAnyIngredientSelected(): Boolean {
        return !selectedIngredients.isEmpty()
    }

    override fun onCheckboxSelected(item: RecipeIngredient, isChecked: Boolean) {
        recipeViewModel.recipeIngredientListCheckboxClicked(item, isChecked)
    }
}