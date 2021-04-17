package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.shoppinglist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import piotr.michalkiewicz.mealplannerclient.databinding.ListItemShoppingListBinding
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.view.recipes.interfaces.RecipeIngredientListOnCheckedChangeListener

class ShoppingListViewAdapter(
    context: Context,
    layoutResId: Int,
    private val data: List<RecipeIngredient>,
    private val onCheckboxClickListener: RecipeIngredientListOnCheckedChangeListener
) : ArrayAdapter<RecipeIngredient>(context, layoutResId, data) {

    private val checkBoxesStateMaintainerList: MutableList<RecipeIngredient> = mutableListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            ListItemShoppingListBinding.inflate(LayoutInflater.from(context), parent, false)
        val listItem = data[position]

        if (checkBoxesStateMaintainerList.contains(listItem)) {
            binding.shoppingListItemCB.isChecked = true
        }

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
        with(binding) {
            shoppingListItemCB.text = listItem.name
            shoppingListAmountTV.text = listItem.amount
            shoppingListItemUnitTV.text = listItem.unit
        }
        return binding.root
    }
}