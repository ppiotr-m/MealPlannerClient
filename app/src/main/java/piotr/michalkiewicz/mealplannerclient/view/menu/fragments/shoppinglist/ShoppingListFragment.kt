package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentShoppingListBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.shoppinglist.viewmodel.ShoppingListViewModel
import piotr.michalkiewicz.mealplannerclient.view.menu.fragments.shoppinglist.adapters.ShoppingListAdapter
import piotr.michalkiewicz.mealplannerclient.view.recipes.interfaces.RecipeIngredientListOnCheckedChangeListener

class ShoppingListFragment : Fragment(), RecipeIngredientListOnCheckedChangeListener {

    private lateinit var shoppingListViewModel: ShoppingListViewModel
    private lateinit var binding: FragmentShoppingListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupDataBinding(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun setupDataBinding(inflater: LayoutInflater) {
        binding = FragmentShoppingListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun init() {
        initViewModel()
        setupObservers()
    }

    private fun setupObservers() {
        setupObserverForShoppingListItems()
        setupObserverForIngredientsDeletion()
        setupObserverForNothingToDelete()
    }

    private fun setupObserverForShoppingListItems() {
        shoppingListViewModel.shoppingListItems.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                showEmptyShoppingList()
                return@observe
            }
            initIngedientsRecyclerView(it)
        })
    }

    private fun setupObserverForIngredientsDeletion() {
        shoppingListViewModel.ingredientsDeletedNotifier.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                (binding.recipeIngredientsListView.adapter as BaseAdapter).notifyDataSetChanged()
                if (shoppingListViewModel.isShoppingListEmpty()) {
                    showEmptyShoppingList()
                }
            }
        })
    }

    private fun setupObserverForNothingToDelete() {
        shoppingListViewModel.nothingToDeleteNotifier.observe(viewLifecycleOwner, {
            showNothingToDeleteToast()
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
            ShoppingListAdapter(requireContext(), data, this)
    }

    private fun showEmptyShoppingList() {
        binding.emptyShoppingListTV.visibility = View.VISIBLE
    }

    override fun onCheckboxClicked(item: RecipeIngredient, isChecked: Boolean) {
        shoppingListViewModel.ingredientCheckboxClicked(item, isChecked)
    }

    private fun showNothingToDeleteToast() {
        Toast.makeText(requireContext(), R.string.no_ingredient_selected, Toast.LENGTH_SHORT).show()
    }
}

