package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.shoppinglist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import piotr.michalkiewicz.mealplannerclient.databinding.ListItemShoppingListBinding
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.view.recipes.interfaces.RecipeIngredientListOnCheckedChangeListener

class ShoppingListAdapter(
    context: Context,
    private val data: List<RecipeIngredient>,
    private val onCheckboxClickListener: RecipeIngredientListOnCheckedChangeListener
) : ArrayAdapter<RecipeIngredient>(context, -1, data) {

    private val checkBoxesStateMaintainerList: MutableList<RecipeIngredient> = mutableListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            ListItemShoppingListBinding.inflate(LayoutInflater.from(context), parent, false)
        val listItem = data[position]

        handleCheckboxState(listItem, binding)
        setCheckboxStateListener(listItem, binding)
        setViewWithData(listItem, binding)
        return binding.root
    }

    private fun handleCheckboxState(
        listItem: RecipeIngredient,
        binding: ListItemShoppingListBinding
    ) {
        if (checkBoxesStateMaintainerList.contains(listItem)) {
            binding.shoppingListItemCB.isChecked = true
        }
    }

    private fun setCheckboxStateListener(
        listItem: RecipeIngredient,
        binding: ListItemShoppingListBinding
    ) {
        binding.recipeIngredient = listItem
        binding.shoppingListItemCB.setOnCheckedChangeListener { checkbox, isChecked ->
            if (isChecked) {
                checkBoxesStateMaintainerList.add(listItem)
            } else {
                checkBoxesStateMaintainerList.remove(listItem)
            }
            onCheckboxClickListener.onCheckboxClicked(
                (checkbox.tag as RecipeIngredient),
                isChecked
            )
        }
    }

    private fun setViewWithData(listItem: RecipeIngredient, binding: ListItemShoppingListBinding) {
        with(binding) {
            shoppingListItemCB.text = listItem.name
            shoppingListAmountTV.text = listItem.amount
            shoppingListItemUnitTV.text = listItem.unit
        }
    }
}