package piotr.michalkiewicz.mealplannerclient.view.recipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import piotr.michalkiewicz.mealplannerclient.databinding.IngredientListItemBinding
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.view.recipes.interfaces.RecipeIngredientListOnCheckedChangeListener

class RecipeIngredientsListAdapter(
    private val data: List<RecipeIngredient>,
    private val onCheckedChangeListener: RecipeIngredientListOnCheckedChangeListener
) :
    RecyclerView.Adapter<RecipeIngredientsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = IngredientListItemBinding.inflate(inflater)

        return ViewHolder(binding, onCheckedChangeListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount(): Int {
        return data.size
    }

    //  TODO Handle listener in XML
    class ViewHolder(
        private val binding: IngredientListItemBinding,
        private val onCheckedChangeListener: RecipeIngredientListOnCheckedChangeListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecipeIngredient) {
            binding.recipeIngredient = item
            binding.recipeIngredientListItemCB.setOnCheckedChangeListener { checkbox, isChecked ->
                onCheckedChangeListener.onCheckboxSelected(
                    (checkbox.tag as RecipeIngredient),
                    isChecked
                )
            }

            with(binding) {
                recipeIngredientListItemCB.text = item.name
                recipeIngredientListAmountTV.text = item.amount
                recipeIngredientListItemUnitTV.text = item.unit
            }
        }
    }
}