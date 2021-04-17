package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentShoppingListBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.shoppinglist.viewmodel.ShoppingListViewModel
import piotr.michalkiewicz.mealplannerclient.view.menu.fragments.shoppinglist.adapters.ShoppingListViewAdapter
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
            if (it.isEmpty()) {
                showEmptyShoppingList()
                return@observe
            }
            initIngedientsRecyclerView(it)
        })
        shoppingListViewModel.ingredientsDeletedNotifier.observe(viewLifecycleOwner, {
            if (it) {
                (binding.recipeIngredientsListView.adapter as BaseAdapter).notifyDataSetChanged()
                shoppingListViewModel.resetIngredientsDeletedNotifier()
                if (shoppingListViewModel.isShoppingListEmpty()) {
                    showEmptyShoppingList()
                }
            }
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
        binding.recipeIngredientsListView.adapter =
            ShoppingListViewAdapter(requireContext(), R.layout.list_item_shopping_list, data, this)
    }

    private fun showEmptyShoppingList() {
        binding.emptyShoppingListTV.visibility = View.VISIBLE
    }

    override fun onCheckboxClicked(item: RecipeIngredient, isChecked: Boolean) {
        shoppingListViewModel.ingredientCheckboxClicked(item, isChecked)
    }
}
