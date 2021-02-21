package piotr.michalkiewicz.mealplannerclient.nutrition.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.nutrition_add_product_dialog.*
import piotr.michalkiewicz.mealplannerclient.R

class NutritionAddEatenProductDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.nutrition_add_product_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
    }

    private fun initSpinner() {
        productAmountUnitSpinner.adapter = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.units_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            productAmountUnitSpinner.adapter = adapter
        }
    }

    companion object {
        const val TAG = "NutritionAddProductDialog"
    }
}