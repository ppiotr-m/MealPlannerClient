package piotr.michalkiewicz.mealplannerclient.view.recipes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import piotr.michalkiewicz.mealplannerclient.databinding.ListItemIngredientBinding
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.REDUNDANT_LAYOUT_ID
import piotr.michalkiewicz.mealplannerclient.view.recipes.interfaces.RecipeIngredientListOnCheckedChangeListener

class IngredientsListAdapter(
    context: Context,
    private val data: List<RecipeIngredient>,
    private val onCheckboxClickListener: RecipeIngredientListOnCheckedChangeListener
) : ArrayAdapter<RecipeIngredient>(context, REDUNDANT_LAYOUT_ID, data) {

    private val checkBoxesStateMaintainerList: MutableList<RecipeIngredient> = data.toMutableList()

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
        binding.recipeIngredientListItemCB.isChecked =
            checkBoxesStateMaintainerList.contains(listItem)
    }

    private fun setCheckboxStateListener(
        listItem: RecipeIngredient,
        binding: ListItemIngredientBinding
    ) {
        binding.recipeIngredient = listItem
        binding.recipeIngredientListItemCB.setOnCheckedChangeListener { checkbox, isChecked ->
            handleCheckboxState(listItem, isChecked)
            onCheckboxClickListener.onCheckboxClicked(
                (checkbox.tag as RecipeIngredient),
                isChecked
            )
        }
    }

    private fun handleCheckboxState(listItem: RecipeIngredient, isChecked: Boolean) {
        if (isChecked) {
            checkBoxesStateMaintainerList.add(listItem)
        } else {
            checkBoxesStateMaintainerList.remove(listItem)
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