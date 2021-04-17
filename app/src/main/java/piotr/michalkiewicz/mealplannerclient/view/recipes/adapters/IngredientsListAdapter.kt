package piotr.michalkiewicz.mealplannerclient.view.recipes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import piotr.michalkiewicz.mealplannerclient.databinding.ListItemIngredientBinding
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.view.recipes.interfaces.RecipeIngredientListOnCheckedChangeListener

class IngredientsListAdapter(
    context: Context,
    private val data: List<RecipeIngredient>,
    private val onCheckboxClickListener: RecipeIngredientListOnCheckedChangeListener
) : ArrayAdapter<RecipeIngredient>(context, -1, data) {

    private val checkBoxesStateMaintainerList: MutableList<RecipeIngredient> = mutableListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding =
            ListItemIngredientBinding.inflate(LayoutInflater.from(context), parent, false)
        val listItem = data[position]

        handleCheckboxState(listItem, binding)
        setCheckboxStateListener(listItem, binding)
        setViewWithData(listItem, binding)
        return binding.root
    }

    private fun handleCheckboxState(
        listItem: RecipeIngredient,
        binding: ListItemIngredientBinding
    ) {
        if (checkBoxesStateMaintainerList.contains(listItem)) {
            binding.recipeIngredientListItemCB.isChecked = true
        }
    }

    private fun setCheckboxStateListener(
        listItem: RecipeIngredient,
        binding: ListItemIngredientBinding
    ) {
        binding.recipeIngredient = listItem
        binding.recipeIngredientListItemCB.setOnCheckedChangeListener { checkbox, isChecked ->
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

    private fun setViewWithData(listItem: RecipeIngredient, binding: ListItemIngredientBinding) {
        with(binding) {
            recipeIngredientListItemCB.text = listItem.name
            recipeIngredientListAmountTV.text = listItem.amount
            recipeIngredientListItemUnitTV.text = listItem.unit
        }
    }
}