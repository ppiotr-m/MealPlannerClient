package piotr.michalkiewicz.mealplannerclient.view.recipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import piotr.michalkiewicz.mealplannerclient.databinding.ListItemCookingStepBinding
import piotr.michalkiewicz.mealplannerclient.recipes.model.InstructionStep

class CookingStepsAdapter(private val data: List<InstructionStep>) :
    RecyclerView.Adapter<CookingStepsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CookingStepsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCookingStepBinding.inflate(inflater)

        return CookingStepsAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CookingStepsAdapter.ViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(
        private val binding: ListItemCookingStepBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: InstructionStep) {
            with(binding) {
                cookingStepsNrTV.text = item.stepNumber.toString()
                cookingStepContentTV.text = item.stepInstruction
            }
        }
    }
}