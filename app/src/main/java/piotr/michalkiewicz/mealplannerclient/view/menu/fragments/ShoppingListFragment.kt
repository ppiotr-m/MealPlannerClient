package piotr.michalkiewicz.mealplannerclient.view.menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentShoppingListBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.shoppinglist.viewmodel.ShoppingListViewModel
import piotr.michalkiewicz.mealplannerclient.view.recipes.adapters.RecipeIngredientsListAdapter
import piotr.michalkiewicz.mealplannerclient.view.recipes.interfaces.RecipeIngredientListOnCheckedChangeListener

class ShoppingListFragment : Fragment(), RecipeIngredientListOnCheckedChangeListener {

    private lateinit var shoppingListViewModel: ShoppingListViewModel
    private lateinit var binding: FragmentShoppingListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingListBinding.inflate(inflater)

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

    private fun setupObservers() {
        shoppingListViewModel.shoppingListItems.observe(viewLifecycleOwner, {
            //  TODO Ugly solution
            val conversionList = arrayListOf<RecipeIngredient>()
            conversionList.addAll(it.values)
            initIngedientsRecyclerView(conversionList)
        })
    }

    private fun initViewModel() {
        shoppingListViewModel =
            ViewModelProvider(
                this,
                Injection.provideShoppingListViewModelFactory(requireContext())
            ).get(
                ShoppingListViewModel::class.java
            )
        binding.viewModel = shoppingListViewModel
    }

    private fun initIngedientsRecyclerView(data: List<RecipeIngredient>) {
        binding.recipeIngredientsListRV.layoutManager = LinearLayoutManager(requireContext())
        binding.recipeIngredientsListRV.adapter = RecipeIngredientsListAdapter(data, this)
    }

    private fun showEmptyShoppingList() {
        //  TODO
    }

    private fun deleteProducts() {
    }

    override fun onCheckboxSelected(item: RecipeIngredient, isChecked: Boolean) {
    }

}
