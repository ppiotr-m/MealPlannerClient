package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.shoppinglist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import piotr.michalkiewicz.mealplannerclient.databinding.ListItemShoppingListBinding
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.view.recipes.interfaces.RecipeIngredientListOnCheckedChangeListener

class ShoppingListAdapter(
    private val data: List<RecipeIngredient>,
    private val onCheckedChangeListener: RecipeIngredientListOnCheckedChangeListener
) :
    RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemShoppingListBinding.inflate(inflater)

        return ViewHolder(binding, onCheckedChangeListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int {
        return data.size
    }

    //  TODO Handle listener in XML
    class ViewHolder(
        private val binding: ListItemShoppingListBinding,
        private val onCheckedChangeListener: RecipeIngredientListOnCheckedChangeListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecipeIngredient) {
            binding.recipeIngredient = item
            binding.shoppingListItemCB.setOnCheckedChangeListener { checkbox, isChecked ->
                onCheckedChangeListener.onCheckboxSelected(
                    (checkbox.tag as RecipeIngredient),
                    isChecked
                )
            }

            with(binding) {
                shoppingListItemCB.text = item.name
                shoppingListAmountTV.text = item.amount
                shoppingListItemUnitTV.text = item.unit
            }
        }
    }
}